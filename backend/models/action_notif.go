package models

import (
	"context"
	h "github.com/hackathonovo/do-you-even-code/backend/helpers"
	"github.com/pressly/chi"
	"github.com/pressly/chi/render"
	"net/http"
	"strconv"
)

type ActionNotif struct {
	DBModel
	Title    string `json:"title"`
	Content  string `json:"content"`
	User     User	`json:"-"`
	UserID   uint   `json:"user_id"`
	ActionID uint   `json:"action_id"`
}

type ActionNotifRequest struct {
	*ActionNotif
}

func (ActionNotifRequest) Bind(r *http.Request) error {
	return nil
}

type ActionNotifResponse struct {
	*ActionNotif
}

func (rd *ActionNotifResponse) Render(w http.ResponseWriter, r *http.Request) error {
	// Pre-processing before a response is marshalled and sent across the wire
	return nil
}

func (e *Env) ListActionNotifs(rw http.ResponseWriter, req *http.Request) {
	var actionNotifs = []*ActionNotif{}
	e.DB.Find(&actionNotifs)

	if err := render.RenderList(rw, req, NewActionNotifListResponse(actionNotifs)); err != nil {
		render.Render(rw, req, h.ErrRender(err))
		return
	}
}

func (e *Env) ListActionNotifsByAction(w http.ResponseWriter, r *http.Request) {
	action := r.Context().Value("action").(*Action)

	var actionNotifs = []*ActionNotif{}
	e.DB.Where("action_id = ?", action.ID).Find(&actionNotifs)

	if err := render.RenderList(w, r, NewActionNotifListResponse(actionNotifs)); err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}
}

func (e *Env) CreateActionNotif(rw http.ResponseWriter, req *http.Request) {
	action := req.Context().Value("action").(*Action)
	data := &ActionNotifRequest{}

	if err := render.Bind(req, data); err != nil {
		render.Render(rw, req, h.ErrInvalidRequest(err))
		return
	}
	data.ActionID = action.ID

	if err := e.DB.Create(data.ActionNotif).Error; err != nil {
		render.Render(rw, req, h.ErrRender(err))
		return
	}

	go e.PushActionUserNotification(*data.ActionNotif, int(data.UserID))

	render.Status(req, http.StatusCreated)
	render.Render(rw, req, NewActionNotifResponse(data.ActionNotif))
}

func (e *Env) GetActionNotif(w http.ResponseWriter, r *http.Request) {
	actionNotif := r.Context().Value("actionNotif").(*ActionNotif)

	if err := render.Render(w, r, NewActionNotifResponse(actionNotif)); err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}
}

func (e *Env) UpdateActionNotif(w http.ResponseWriter, r *http.Request) {
	ActionNotif := r.Context().Value("actionNotif").(*ActionNotif)

	p := &ActionNotifRequest{ActionNotif: ActionNotif}
	if err := render.Bind(r, p); err != nil {
		render.Render(w, r, h.ErrInvalidRequest(err))
		return
	}

	if err := e.DB.Save(ActionNotif).Error; err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}

	if err := render.Render(w, r, NewActionNotifResponse(ActionNotif)); err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}
}

func (e *Env) DeleteActionNotif(w http.ResponseWriter, r *http.Request) {
	actionNotif := r.Context().Value("actionNotif").(*ActionNotif)

	if err := e.DB.Delete(actionNotif).Error; err != nil {
		render.Render(w, r, h.ErrInvalidRequest(err))
		return
	}

	render.Render(w, r, h.SucDelete)
}

func (e *Env) ActionNotifCtx(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		actionNotifId, err := strconv.Atoi(chi.URLParam(r, "actionNotifId"))
		if err != nil {
			render.Render(w, r, h.ErrRender(err))
			return
		}

		ActionNotif := ActionNotif{}
		if err := e.DB.First(&ActionNotif, actionNotifId).Error; err != nil {
			render.Render(w, r, h.ErrNotFound)
			return
		}

		ctx := context.WithValue(r.Context(), "actionNotif", &ActionNotif)
		next.ServeHTTP(w, r.WithContext(ctx))
	})
}

func NewActionNotifListResponse(ActionNotifs []*ActionNotif) []render.Renderer {
	list := []render.Renderer{}
	for _, ActionNotif := range ActionNotifs {
		list = append(list, NewActionNotifResponse(ActionNotif))
	}
	return list
}

func NewActionNotifResponse(p *ActionNotif) *ActionNotifResponse {
	resp := &ActionNotifResponse{ActionNotif: p}
	return resp
}
