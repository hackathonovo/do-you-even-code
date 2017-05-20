package models

import (
	"context"
	"crypto/sha512"
	"errors"
	h "github.com/hackathonovo/do-you-even-code/backend/helpers"
	"github.com/pressly/chi"
	"github.com/pressly/chi/render"
	"log"
	"net/http"
	"regexp"
	"strconv"
)

type User struct {
	DBModel
	Username   string     `json:"username"`
	PassDigest []byte     `json:"-"`
	Name       string     `json:"name"`
	Surname    string     `json:"surname"`
	Role       string     `json:"role"`
	Data       string     `json:"data"`
	Address    string     `json:"address"`
	Points     []*Point   `gorm:"-" json:"-"`
	Polygons   []*Polygon `gorm:"-" json:"-"`
	Phone      string     `json:"phone"`
	Fcm        string     `json:"fcm"`
	ActionId   uint       `json:"action_id"`
}

type NewUserRequest struct {
	*User
	Password string `json:"password"`
}

type UserRequest struct {
	*User
	LastLocation *Point `json:"last_location"`
}

func (UserRequest) Bind(r *http.Request) error {
	return nil
}

type UserRespose struct {
	*User
	Polygons []*PolygonResponse `json:"polygons"`
	Points   []*PointResponse   `json:"points"`
}

func (u *NewUserRequest) Bind(r *http.Request) error {
	if u.User == nil {
		return errors.New("Invalid request")
	}

	rx, err := regexp.Compile(".{3,}")
	if err != nil {
		return err
	}

	if rx.MatchString(u.Username) != true {
		return errors.New("Name too short (3 min)")
	}

	if rx.MatchString(u.Password) != true {
		return errors.New("Password too short (3 min)")
	}

	return nil
}

func (e *Env) GetUser(w http.ResponseWriter, r *http.Request) {
	user := r.Context().Value("user").(*User)

	if err := render.Render(w, r, e.NewUserReponse(user)); err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}
}

func (e *Env) CreateUser(rw http.ResponseWriter, req *http.Request) {
	data := &NewUserRequest{}

	if err := render.Bind(req, data); err != nil {
		render.Render(rw, req, h.ErrInvalidRequest(err))
		return
	}

	user := data.User

	hash := sha512.New()
	hash.Sum([]byte(data.Password))
	user.PassDigest = hash.Sum(nil)

	if err := e.DB.Create(&user).Error; err != nil {
		render.Render(rw, req, h.ErrRender(err))
		return
	}

	render.Status(req, http.StatusOK)
	render.Render(rw, req, e.NewUserReponse(user))
}

func (e *Env) UpdateUser(w http.ResponseWriter, r *http.Request) {
	user := r.Context().Value("user").(*User)

	p := &UserRequest{User: user}
	if err := render.Bind(r, p); err != nil {
		render.Render(w, r, h.ErrInvalidRequest(err))
		return
	}

	if err := e.DB.Save(&p.User).Error; err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}

	render.Status(r, http.StatusOK)
	render.Render(w, r, h.SucUpdate)
}

func (e *Env) GetLastLocation(w http.ResponseWriter, r *http.Request) {
	user := r.Context().Value("user").(*User)

	p := &UserRequest{User: user}
	if err := render.Bind(r, p); err != nil {
		render.Render(w, r, h.ErrInvalidRequest(err))
		return
	}

	var points = []*Point{}

	sql := "select id, created_at, updated_at, type, data, label, draggable, ST_AsBinary(geom) " +
		"from points where deleted_at IS NULL and id IN (select MAX(created_at) from user_points WHERE user_id = ? AND type = 'last');"
	rows, err := e.DB.Raw(sql, p.ID).Rows()
	if err != nil {
		log.Printf(err.Error())
		return
	}

	for rows.Next() {
		p := Point{}
		rows.Scan(&p.ID, &p.CreatedAt, &p.UpdatedAt, &p.Type, &p.Data, &p.Label, &p.Draggable, &p.Geo)

		points = append(points, &p)
	}

	if err := render.Render(w, r, NewPointResponse(points[0])); err != nil {
		render.Render(w, r, &PointResponse{})
		return
	}
}

func (e *Env) DeleteUser(w http.ResponseWriter, r *http.Request) {
	user := r.Context().Value("user").(*User)

	if err := e.DB.Delete(user).Error; err != nil {
		render.Render(w, r, h.ErrInvalidRequest(err))
		return
	}

	render.Status(r, http.StatusOK)
	render.Render(w, r, h.SucDelete)
}

func (e *Env) ListUsers(rw http.ResponseWriter, req *http.Request) {
	var users = []*User{}
	e.DB.Find(&users)

	if err := render.RenderList(rw, req, e.NewUserListReponse(users)); err != nil {
		render.Render(rw, req, h.ErrServer)
		return
	}
}

func (e *Env) NewUserListReponse(users []*User) []render.Renderer {
	list := []render.Renderer{}
	for _, user := range users {
		list = append(list, e.NewUserReponse(user))
	}
	return list
}

func (rd *UserRespose) Render(w http.ResponseWriter, r *http.Request) error {
	return nil
}

func (e *Env) UserCtx(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		userId, err := strconv.Atoi(chi.URLParam(r, "userId"))
		if err != nil {
			render.Render(w, r, h.ErrRender(err))
			return
		}

		user := User{}
		if err := e.DB.First(&user, userId).Error; err != nil {
			render.Render(w, r, h.ErrNotFound)
			return
		}

		user.Points = e.GetRelatedPointsList(user.ID, "user")
		user.Polygons = e.GetRelatedPolygonList(user.ID, "user")

		ctx := context.WithValue(r.Context(), "user", &user)
		next.ServeHTTP(w, r.WithContext(ctx))
	})
}

func (e *Env) NewUserReponse(p *User) *UserRespose {
	resp := &UserRespose{User: p}

	p.Points = e.GetRelatedPointsList(p.ID, "user")
	resp.Points = make([]*PointResponse, 0)
	resp.Polygons = make([]*PolygonResponse, 0)
	for _, p := range p.Points {
		resp.Points = append(resp.Points, NewPointResponse(p))
	}

	p.Polygons = e.GetRelatedPolygonList(p.ID, "user")
	for _, p := range p.Polygons {
		resp.Polygons = append(resp.Polygons, NewPolygonReponse(p))
	}

	return resp
}
