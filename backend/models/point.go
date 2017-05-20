package models

import (
	"context"
	"fmt"
	h "github.com/hackathonovo/do-you-even-code/backend/helpers"
	"github.com/paulmach/go.geo"
	"github.com/pressly/chi"
	"github.com/pressly/chi/render"
	"net/http"
	"strconv"
	"time"
	"log"
)

type Point struct {
	DBModel
	Type      string    `json:"type"`
	Data      string    `json:"data"`
	Label     string    `json:"label"`
	Draggable bool      `json:"draggable"`
	Geo       geo.Point `gorm:"-" json:"-"`
}

type PointRequest struct {
	*Point
	Lat float64 `json:"lat"`
	Lng float64 `json:"lng"`
	UserId uint `json:"user_id"`
}

func (p *PointRequest) Bind(r *http.Request) error {

	return nil
}

type PointResponse struct {
	*Point
	Lat float64 `json:"lat"`
	Lng float64 `json:"lng"`
}

func (rd *PointResponse) Render(w http.ResponseWriter, r *http.Request) error {
	// Pre-processing before a response is marshalled and sent across the wire
	return nil
}

func (e *Env) ListPoints(rw http.ResponseWriter, req *http.Request) {
	var points = []*Point{}

	rows, err := e.DB.Raw("select id, created_at, updated_at, type, data, label, draggable, ST_AsBinary(geom) from points where deleted_at IS NULL;").Rows()
	if err != nil {
		render.Render(rw, req, h.ErrRender(err))
		return
	}

	for rows.Next() {
		p := Point{}
		rows.Scan(&p.ID, &p.CreatedAt, &p.UpdatedAt, &p.Type, &p.Data, &p.Label, &p.Draggable, &p.Geo)

		if rows.Err() != nil {
			render.Render(rw, req, h.ErrRender(err))
			return
		}
		points = append(points, &p)
	}

	if err := render.RenderList(rw, req, NewPointListResponse(points)); err != nil {
		render.Render(rw, req, h.ErrRender(err))
		return
	}
}

func (e *Env) CreatePoint(rw http.ResponseWriter, req *http.Request) {
	data := &PointRequest{}

	if err := render.Bind(req, data); err != nil {
		render.Render(rw, req, h.ErrInvalidRequest(err))
		return
	}

	//user, ok := req.Context().Value("user").(*User)
	//fmt.Printf("Current user: %s %s\n", user, ok)
	//if ok != true {
	//	render.Render(rw, req, h.ErrServer)
	//	return
	//}

	data.Geo = geo.Point{data.Lng, data.Lat}
	fmt.Printf("Point WKT: %s\n", data.Geo.ToWKT())
	var pointId uint

	//sql := "insert into points values(default, ?, ?, null, ?, ?, ?, ?, ST_GeomFromText(?, 4326))"
	//if err := e.DB.Exec(sql, time.Now(), time.Now(), data.Type, data.Data, data.Label, data.Draggable, data.Geo.ToWKT()).Error; err != nil {
	//	render.Render(rw, req, h.ErrRender(err))
	//	return
	//}
	//
	sql := "insert into points values(default, ?, ?, null, ?, ?, ?, ?, ST_GeomFromText(?, 4326)) RETURNING id"
	e.DB.Exec(sql, time.Now(), time.Now(), data.Type, data.Data, data.Label, data.Draggable, data.Geo.ToWKT()).Scan(&pointId)

	if data.UserId != 0 {
		sql2 := "insert into user_points values(default, ?, ?)"
		if err := e.DB.Exec(sql2, data.UserId, pointId).Error; err != nil {
			render.Render(rw, req, h.ErrRender(err))
			return
		}
	}

	render.Status(req, http.StatusCreated)
	render.Render(rw, req, h.SucCreate)
}

func (e *Env) GetPoint(w http.ResponseWriter, r *http.Request) {
	point := r.Context().Value("point").(*Point)

	if err := render.Render(w, r, NewPointResponse(point)); err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}
}

func (e *Env) UpdatePoint(w http.ResponseWriter, r *http.Request) {
	point := r.Context().Value("point").(*Point)

	p := &PointRequest{Point: point}
	if err := render.Bind(r, p); err != nil {
		render.Render(w, r, h.ErrInvalidRequest(err))
		return
	}
	p.Geo = geo.Point{p.Lng, p.Lat}

	sql := "update points set updated_at = ?, type = ?, data = ?, label = ?, draggable = ?, geom = ST_GeomFromText(?, 4326) WHERE id = ?"
	if err := e.DB.Exec(sql, time.Now(), p.Type, p.Data, p.Label, p.Draggable, p.Geo.ToWKT(), point.ID).Error; err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}

	if err := render.Render(w, r, NewPointResponse(point)); err != nil {
		render.Render(w, r, h.SucUpdate)
		return
	}
}

func (e *Env) DeletePoint(w http.ResponseWriter, r *http.Request) {
	point := r.Context().Value("point").(*Point)

	if err := e.DB.Delete(point).Error; err != nil {
		render.Render(w, r, h.ErrInvalidRequest(err))
		return
	}

	render.Render(w, r, h.SucDelete)
}

func (e *Env) PointCtx(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		pointId, err := strconv.Atoi(chi.URLParam(r, "pointId"))
		if err != nil {
			render.Render(w, r, h.ErrRender(err))
			return
		}

		sql := "select id, created_at, updated_at, type, data, label, draggable, ST_AsBinary(geom) from points where id = ? and deleted_at IS NULL;"
		rows, err := e.DB.Raw(sql, pointId).Rows()
		if err != nil {
			render.Render(w, r, h.ErrRender(err))
			return
		}

		p := Point{}
		for rows.Next() {
			rows.Scan(&p.ID, &p.CreatedAt, &p.UpdatedAt, &p.Type, &p.Data, &p.Label, &p.Draggable, &p.Geo)
		}

		if p.ID == 0 {
			render.Render(w, r, h.ErrNotFound)
			return
		}

		ctx := context.WithValue(r.Context(), "point", &p)
		next.ServeHTTP(w, r.WithContext(ctx))
	})
}

func NewPointListResponse(points []*Point) []render.Renderer {
	list := []render.Renderer{}
	for _, point := range points {
		list = append(list, NewPointResponse(point))
	}
	return list
}


func (e *Env) GetRelatedPointList(entityId uint) []*PointResponse {
	var points = []*Point{}

	sql := "select id, created_at, updated_at, type, data, label, draggable, ST_AsBinary(geom) " +
		"from points where deleted_at IS NULL and id = (select points_id from user_points WHERE user_id = ?);"
	rows, err := e.DB.Raw(sql, entityId).Rows()
	if err != nil {
		log.Printf(err.Error())
		return nil
	}

	for rows.Next() {
		p := Point{}
		rows.Scan(&p.ID, &p.CreatedAt, &p.UpdatedAt, &p.Type, &p.Data, &p.Label, &p.Draggable, &p.Geo)

		if rows.Err() != nil {
			log.Printf(err.Error())
			return nil
		}
		points = append(points, &p)
	}


	list := []*PointResponse{}
	for _, point := range points {
		list = append(list, NewPointResponse(point))
	}

	return list
}

func NewPointResponse(p *Point) *PointResponse {
	resp := &PointResponse{Point: p, Lat: p.Geo.Lat(), Lng: p.Geo.Lng()}
	fmt.Printf("%s", p.Geo.ToWKT())
	return resp
}

