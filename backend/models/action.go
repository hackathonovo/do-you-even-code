package models

import (
	"context"
	"errors"
	h "github.com/hackathonovo/do-you-even-code/backend/helpers"
	"github.com/pressly/chi"
	"github.com/pressly/chi/render"
	"net/http"
	"strconv"
	"time"
)

type Action struct {
	DBModel
	TimeReported   time.Time      `json:"time_reported"`
	TimeMeeting    time.Time      `json:"time_meeting"`
	MeetingAddress string         `json:"meeting_address"`
	TimeFrom       time.Time      `json:"time_from"`
	TimeTo         time.Time      `json:"time_to"`
	Type           string         `json:"type"`
	Data           string         `json:"data"`
	Injury         string         `json:"injury"`
	Address        string         `json:"address"`
	Urgency        string         `json:"urgency"`
	Suicidal       bool           `json:"suicidal"`
	Points         []*Point       `gorm:"-" json:"-"`
	Polygons       []*Polygon     `gorm:"-" json:"-"`
	ActionNotifs   []ActionNotif  `json:"-"`
}

type ActionRequest struct {
	*Action
	UserId uint `json:"user_id"`
}

func (ActionRequest) Bind(r *http.Request) error {
	return nil
}

type ActionResponse struct {
	*Action
	Polygons []*PolygonResponse `json:"polygons"`
	Points   []*PointResponse   `json:"points"`
}

func (rd *ActionResponse) Render(w http.ResponseWriter, r *http.Request) error {
	// Pre-processing before a response is marshalled and sent across the wire
	return nil
}

func (e *Env) ListActions(rw http.ResponseWriter, req *http.Request) {
	var Actions = []*Action{}
	e.DB.Preload("ActionNotifs").Find(&Actions)

	if err := render.RenderList(rw, req, e.NewActionListResponse(Actions)); err != nil {
		render.Render(rw, req, h.ErrRender(err))
		return
	}
}

func (e *Env) CreateAction(rw http.ResponseWriter, req *http.Request) {
	data := &ActionRequest{}

	if err := render.Bind(req, data); err != nil {
		render.Render(rw, req, h.ErrInvalidRequest(err))
		return
	}

	if err := e.DB.Create(data.Action).Error; err != nil {
		render.Render(rw, req, h.ErrRender(err))
		return
	}

	go e.MakePointOnAddress(data.Address, data.Action.ID)

	render.Status(req, http.StatusCreated)
	render.Render(rw, req, e.NewActionResponse(data.Action))
}

func (e *Env) GetAction(w http.ResponseWriter, r *http.Request) {
	Action := r.Context().Value("action").(*Action)

	if err := render.Render(w, r, e.NewActionResponse(Action)); err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}
}

func (e *Env) SearchActions(w http.ResponseWriter, r *http.Request) {
	urgency := r.URL.Query().Get("urgency")
	injury := r.URL.Query().Get("injury")
	suicidal := r.URL.Query().Get("suicidal")

	query := ""
	if urgency != "" {
		query += " urgency ilike '%" + urgency + "%' "
	}

	if injury != "" {
		if query != "" {
			query += " AND "
		}

		query += " actions.injury ILIKE '%" + injury + "%' "
	}

	if suicidal == "true" {
		if query != "" {
			query += " AND "
		}

		query += " actions.suicidal IS TRUE "
	}


	if suicidal == "false" {
		if query != "" {
			query += " AND "
		}

		query += " actions.suicidal IS NOT TRUE "
	}


	actions := []Action{}
	if err := e.DB.Where(query).Find(&actions).Error; err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}

	polygons := []*Polygon{}
	ids := []uint{}
	for _, a := range actions {
		ids = append(ids, a.ID)
	}

	for _, n := range ids {
		pols := e.GetRelatedPolygonList(n, "action")
		polygons = append(polygons, pols...)
	}


	if err := render.RenderList(w, r, e.NewListPolygonResponse(polygons)); err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}
}

func (e *Env) GetActionPolygonList(w http.ResponseWriter, r *http.Request) {
	action := r.Context().Value("action").(*Action)

	polygons := e.GetRelatedPolygonList(action.ID, "action")

	if err := render.RenderList(w, r, e.NewListPolygonResponse(polygons)); err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}
}

func (e *Env) UpdateAction(w http.ResponseWriter, r *http.Request) {
	action := r.Context().Value("action").(*Action)

	p := &ActionRequest{Action: action}
	if err := render.Bind(r, p); err != nil {
		render.Render(w, r, h.ErrInvalidRequest(err))
		return
	}

	if err := e.DB.Save(action).Error; err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}

	if err := render.Render(w, r, e.NewActionResponse(action)); err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}
}

func (e *Env) DeleteAction(w http.ResponseWriter, r *http.Request) {
	action := r.Context().Value("action").(*Action)

	if err := e.DB.Delete(action).Error; err != nil {
		render.Render(w, r, h.ErrInvalidRequest(err))
		return
	}

	render.Render(w, r, h.SucDelete)
}

func (e *Env) GetActionUsers(w http.ResponseWriter, r *http.Request) {
	action := r.Context().Value("action").(*Action)

	sql := "SELECT user_id FROM action_users WHERE action_id = ?"
	rows, err := e.DB.Raw(sql, action.ID).Rows()
	if err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}

	var ids []int
	for rows.Next() {
		var id int
		rows.Scan(&id)

		ids = append(ids, id)
	}

	var users = []*User{}
	e.DB.Where("id IN (?)", ids).Find(&users)

	if err := render.RenderList(w, r, e.NewUserListReponse(users)); err != nil {
		render.Render(w, r, h.ErrServer)
		return
	}

}

func (e *Env) GetActionUsersById(w http.ResponseWriter, r *http.Request) {
	action := r.Context().Value("action").(*Action)

	var users = []*User{}
	e.DB.Where("action_id = ?", action.ID).Find(&users)

	if err := render.RenderList(w, r, e.NewUserListReponse(users)); err != nil {
		render.Render(w, r, h.ErrServer)
		return
	}

}

func (e *Env) SearchActionUsers(w http.ResponseWriter, r *http.Request) {
	action := r.Context().Value("action").(*Action)

	name := r.URL.Query().Get("name")
	role := r.URL.Query().Get("role")
	typ := r.URL.Query().Get("type")

	if name == "" && typ == "" && role == "" {
		render.Render(w, r, h.ErrInvalidRequest(errors.New("Empty query")))
		return
	}

	query := ""
	if name != "" {
		query += " users.name || ' ' || users.surname ILIKE '%" + name + "%' "
	}

	if role != "" {
		if query != "" {
			query += " AND "
		}

		query += " users.role ILIKE '%" + role + "%' "
	}

	if typ != "" {
		if query != "" {
			query += " AND "
		}

		query += " users.type ILIKE '%" + typ + "%' "
	}

	if query != "" {
		query += " AND "
	}

	query += " users.action_id = " + strconv.Itoa(int(action.ID))

	var users = []*User{}

	if err := e.DB.Where(query).Find(&users).Error; err != nil {
		render.Render(w, r, h.ErrServer)
		return
	}

	if err := render.RenderList(w, r, e.NewUserListReponse(users)); err != nil {
		render.Render(w, r, h.ErrServer)
		return
	}

}

func (e *Env) PushAction(w http.ResponseWriter, r *http.Request) {
	action := r.Context().Value("action").(*Action)

	var users = []*User{}
	e.DB.Where("action_id = ?", action.ID).Find(&users)

	var tokens []string
	for _, user := range users {
		tokens = append(tokens, user.Fcm)
	}

	go e.PushActionNotification(*action, tokens)

	render.Status(r, http.StatusCreated)
	render.Render(w, r, h.SucDone)
}

func (e *Env) AddActionUser(w http.ResponseWriter, r *http.Request) {
	action := r.Context().Value("action").(*Action)

	data := &ActionRequest{}

	if err := render.Bind(r, data); err != nil {
		render.Render(w, r, h.ErrInvalidRequest(err))
		return
	}

	sql2 := "insert into action_users values(default, ?, ?, ?)"
	if err := e.DB.Exec(sql2, action.ID, data.UserId, time.Now()).Error; err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}

	render.Render(w, r, h.SucCreate)
}

func (e *Env) ActionCtx(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		ActionId, err := strconv.Atoi(chi.URLParam(r, "actionId"))
		if err != nil {
			render.Render(w, r, h.ErrRender(err))
			return
		}

		Action := Action{}
		if err := e.DB.Preload("ActionNotifs").First(&Action, ActionId).Error; err != nil {
			render.Render(w, r, h.ErrNotFound)
			return
		}

		ctx := context.WithValue(r.Context(), "action", &Action)
		next.ServeHTTP(w, r.WithContext(ctx))
	})
}

func (e *Env) NewActionListResponse(Actions []*Action) []render.Renderer {
	list := []render.Renderer{}
	for _, Action := range Actions {
		list = append(list, e.NewActionResponse(Action))
	}
	return list
}

func (e *Env) NewActionResponse(p *Action) *ActionResponse {
	resp := &ActionResponse{Action: p}

	return resp
}
