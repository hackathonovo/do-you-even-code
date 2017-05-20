package models

import (
	"github.com/pressly/chi/render"
	"net/http"
	"strconv"
	"github.com/pressly/chi"
	"regexp"
	"crypto/sha512"
	"errors"
	"context"
	h "github.com/hackathonovo/do-you-even-code/backend/helpers"
)

type User struct {
	DBModel
	Username      string `json:"username"`
	PassDigest []byte `json:"-"`
	Name string `json:"name"`
	Surname string `json:"surname"`
	Role string `json:"role"`
	Data string `json:"data"`
	Address string `json:"address"`

}

type NewUserRequest struct {
	*User
	Password string `json:"password"`
}

type UserRespose struct {
	*User
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

	render.Status(req, http.StatusCreated)
	render.Render(rw, req, h.SucCreate)
}

func (e *Env) ListUsers(rw http.ResponseWriter, req *http.Request) {
	var users = []*User{}
	e.DB.Find(&users)

	if err := render.RenderList(rw, req, NewUserListReponse(users)); err != nil {
		render.Render(rw, req, h.ErrServer)
		return
	}
}

func NewUserListReponse(users []*User) []render.Renderer {
	list := []render.Renderer{}
	for _, user := range users {
		list = append(list, &UserRespose{user})
	}
	return list
}

func (rd *UserRespose) Render(w http.ResponseWriter, r *http.Request) error {
	// Pre-processing before a response is marshalled and sent across the wire
	return nil
}

func (e *Env) UserCtx(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		pointId, err := strconv.Atoi(chi.URLParam(r, "id"))
		if err != nil {
			render.Render(w, r, h.ErrRender(err))
			return
		}

		user := User{}
		if err := e.DB.First(&user, pointId).Error; err != nil {
			render.Render(w, r, h.ErrNotFound)
			return
		}

		ctx := context.WithValue(r.Context(), "targetUser", &user)
		next.ServeHTTP(w, r.WithContext(ctx))
	})
}