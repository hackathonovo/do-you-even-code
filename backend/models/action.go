package models

import (
	"context"
	h "github.com/hackathonovo/do-you-even-code/backend/helpers"
	"github.com/pressly/chi"
	"github.com/pressly/chi/render"
	"net/http"
	"strconv"
	"time"
)

type Action struct {
	DBModel
	TimeReported   time.Time  `json:"time_reported"`
	TimeMeeting    time.Time  `json:"time_meeting"`
	MeetingAddress string     `json:"meeting_address"`
	TimeFrom       time.Time  `json:"time_from"`
	TimeTo         time.Time  `json:"time_to"`
	Type           string     `json:"type"`
	Data           string     `json:"data"`
	Injury         string     `json:"injury"`
	Address        string     `json:"address"`
	Urgency        string     `json:"urgency"`
	Suicidal       bool       `json:"suicidal"`
	Points         []*Point   `gorm:"-" json:"-"`
	Polygons       []*Polygon `gorm:"-" json:"-"`
}

type ActionRequest struct {
	*Action
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
	e.DB.Find(&Actions)

	if err := render.RenderList(rw, req, NewActionListResponse(Actions)); err != nil {
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

	render.Status(req, http.StatusCreated)
	render.Render(rw, req, NewActionResponse(data.Action))
}

func (e *Env) GetAction(w http.ResponseWriter, r *http.Request) {
	Action := r.Context().Value("action").(*Action)

	if err := render.Render(w, r, NewActionResponse(Action)); err != nil {
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

	if err := render.Render(w, r, NewActionResponse(action)); err != nil {
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

func (e *Env) ActionCtx(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		ActionId, err := strconv.Atoi(chi.URLParam(r, "actionId"))
		if err != nil {
			render.Render(w, r, h.ErrRender(err))
			return
		}

		Action := Action{}
		if err := e.DB.First(&Action, ActionId).Error; err != nil {
			render.Render(w, r, h.ErrNotFound)
			return
		}

		ctx := context.WithValue(r.Context(), "action", &Action)
		next.ServeHTTP(w, r.WithContext(ctx))
	})
}

func NewActionListResponse(Actions []*Action) []render.Renderer {
	list := []render.Renderer{}
	for _, Action := range Actions {
		list = append(list, NewActionResponse(Action))
	}
	return list
}

func NewActionResponse(p *Action) *ActionResponse {
	resp := &ActionResponse{Action: p}
	return resp
}
