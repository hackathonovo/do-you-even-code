package models

import (
	"context"
	h "github.com/hackathonovo/do-you-even-code/backend/helpers"
	"github.com/pressly/chi"
	"github.com/pressly/chi/render"
	"net/http"
	"strconv"
)

type Timetable struct {
	DBModel
	From   string `json:"from"`
	To     string `json:"to"`
	Type   string `json:"type"`
	User   User   `json:"-"`
	UserID uint   `json:"user_id"`
}

type TimetableRequest struct {
	*Timetable
}

func (TimetableRequest) Bind(r *http.Request) error {
	return nil
}

type TimetableResponse struct {
	*Timetable
}

func (rd *TimetableResponse) Render(w http.ResponseWriter, r *http.Request) error {
	// Pre-processing before a response is marshalled and sent across the wire
	return nil
}

func (e *Env) ListTimetables(rw http.ResponseWriter, req *http.Request) {
	var timetables = []*Timetable{}
	e.DB.Find(&timetables)

	if err := render.RenderList(rw, req, NewTimetableListResponse(timetables)); err != nil {
		render.Render(rw, req, h.ErrRender(err))
		return
	}
}

func (e *Env) ListUserTimetables(w http.ResponseWriter, r *http.Request) {
	user := r.Context().Value("user").(*User)
	var timetables = []*Timetable{}
	e.DB.Where("user_id = ?", user.ID).Find(&timetables)

	if err := render.RenderList(w, r, NewTimetableListResponse(timetables)); err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}
}

func (e *Env) CreateTimetable(rw http.ResponseWriter, req *http.Request) {
	data := &TimetableRequest{}

	if err := render.Bind(req, data); err != nil {
		render.Render(rw, req, h.ErrInvalidRequest(err))
		return
	}

	if err := e.DB.Create(data.Timetable).Error; err != nil {
		render.Render(rw, req, h.ErrRender(err))
		return
	}

	render.Status(req, http.StatusCreated)
	render.Render(rw, req, NewTimetableResponse(data.Timetable))
}

func (e *Env) GetTimetable(w http.ResponseWriter, r *http.Request) {
	timetable := r.Context().Value("timetable").(*Timetable)

	if err := render.Render(w, r, NewTimetableResponse(timetable)); err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}
}

func (e *Env) UpdateTimetable(w http.ResponseWriter, r *http.Request) {
	Timetable := r.Context().Value("timetable").(*Timetable)

	p := &TimetableRequest{Timetable: Timetable}
	if err := render.Bind(r, p); err != nil {
		render.Render(w, r, h.ErrInvalidRequest(err))
		return
	}

	if err := e.DB.Save(Timetable).Error; err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}

	if err := render.Render(w, r, NewTimetableResponse(Timetable)); err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}
}

func (e *Env) DeleteTimetable(w http.ResponseWriter, r *http.Request) {
	timetable := r.Context().Value("timetable").(*Timetable)

	if err := e.DB.Delete(timetable).Error; err != nil {
		render.Render(w, r, h.ErrInvalidRequest(err))
		return
	}

	render.Render(w, r, h.SucDelete)
}

func (e *Env) TimetableCtx(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		timetableId, err := strconv.Atoi(chi.URLParam(r, "timetableId"))
		if err != nil {
			render.Render(w, r, h.ErrRender(err))
			return
		}

		Timetable := Timetable{}
		if err := e.DB.First(&Timetable, timetableId).Error; err != nil {
			render.Render(w, r, h.ErrNotFound)
			return
		}

		ctx := context.WithValue(r.Context(), "timetable", &Timetable)
		next.ServeHTTP(w, r.WithContext(ctx))
	})
}

func NewTimetableListResponse(Timetables []*Timetable) []render.Renderer {
	list := []render.Renderer{}
	for _, Timetable := range Timetables {
		list = append(list, NewTimetableResponse(Timetable))
	}
	return list
}

func NewTimetableResponse(p *Timetable) *TimetableResponse {
	resp := &TimetableResponse{Timetable: p}
	return resp
}
