package models

import (
	"context"
	h "github.com/hackathonovo/do-you-even-code/backend/helpers"
	"github.com/pressly/chi"
	"github.com/pressly/chi/render"
	"net/http"
	"strconv"
)

type Log struct {
	DBModel
	Key    string `json:"key"`
	Value  string `json:"value"`
	User   User   `json:"-"`
	UserID uint   `json:"user_id"`
}

type LogRequest struct {
	*Log
}

func (LogRequest) Bind(r *http.Request) error {
	return nil
}

type LogResponse struct {
	*Log
}

func (rd *LogResponse) Render(w http.ResponseWriter, r *http.Request) error {
	// Pre-processing before a response is marshalled and sent across the wire
	return nil
}

func (e *Env) ListLogs(rw http.ResponseWriter, req *http.Request) {
	var logs = []*Log{}
	e.DB.Find(&logs)

	if err := render.RenderList(rw, req, NewLogListResponse(logs)); err != nil {
		render.Render(rw, req, h.ErrRender(err))
		return
	}
}

func (e *Env) CreateLog(rw http.ResponseWriter, req *http.Request) {
	data := &LogRequest{}

	if err := render.Bind(req, data); err != nil {
		render.Render(rw, req, h.ErrInvalidRequest(err))
		return
	}

	if err := e.DB.Create(data.Log).Error; err != nil {
		render.Render(rw, req, h.ErrRender(err))
		return
	}

	render.Status(req, http.StatusCreated)
	render.Render(rw, req, NewLogResponse(data.Log))
}

func (e *Env) GetLog(w http.ResponseWriter, r *http.Request) {
	log := r.Context().Value("log").(*Log)

	if err := render.Render(w, r, NewLogResponse(log)); err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}
}

func (e *Env) UpdateLog(w http.ResponseWriter, r *http.Request) {
	Log := r.Context().Value("log").(*Log)

	p := &LogRequest{Log: Log}
	if err := render.Bind(r, p); err != nil {
		render.Render(w, r, h.ErrInvalidRequest(err))
		return
	}

	if err := e.DB.Save(Log).Error; err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}

	if err := render.Render(w, r, NewLogResponse(Log)); err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}
}

func (e *Env) DeleteLog(w http.ResponseWriter, r *http.Request) {
	log := r.Context().Value("log").(*Log)

	if err := e.DB.Delete(log).Error; err != nil {
		render.Render(w, r, h.ErrInvalidRequest(err))
		return
	}

	render.Render(w, r, h.SucDelete)
}

func (e *Env) LogCtx(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		logId, err := strconv.Atoi(chi.URLParam(r, "logId"))
		if err != nil {
			render.Render(w, r, h.ErrRender(err))
			return
		}

		Log := Log{}
		if err := e.DB.First(&Log, logId).Error; err != nil {
			render.Render(w, r, h.ErrNotFound)
			return
		}

		ctx := context.WithValue(r.Context(), "log", &Log)
		next.ServeHTTP(w, r.WithContext(ctx))
	})
}

func NewLogListResponse(Logs []*Log) []render.Renderer {
	list := []render.Renderer{}
	for _, Log := range Logs {
		list = append(list, NewLogResponse(Log))
	}
	return list
}

func NewLogResponse(p *Log) *LogResponse {
	resp := &LogResponse{Log: p}
	return resp
}
