package models

import (
	"net/http"
	"github.com/pressly/chi/render"
	"github.com/paulmach/go.geo"
	"time"
	"strconv"
	"github.com/pressly/chi"
	h "github.com/hackathonovo/do-you-even-code/backend/helpers"
	"context"
)

type Polygon struct {
	DBModel
	Type string `json:"type"`
	Data string `json:"data"`
	Color string       `json:"color"`
	Geo   geo.PointSet `gorm:"-" json:"-"`
}

type PolygonRequest struct {
	*Polygon
	ExtraColor string        `json:"color"`
	Border     []BorderPoint `json:"polygon"`
}

func (PolygonRequest) Bind(r *http.Request) error {
	return nil
}

type PolygonResponse struct {
	*Polygon
	Border []BorderPoint `json:"polygon"`
}

type BorderPoint struct {
	Lat float64 `json:"lat"`
	Lng float64 `json:"lng"`
}

func (p *PolygonResponse) Render(w http.ResponseWriter, r *http.Request) error {
	return nil
}

func (e *Env) CreatePolygon(w http.ResponseWriter, r *http.Request) {
	data := &PolygonRequest{}

	if err := render.Bind(r, data); err != nil {
		render.Render(w, r, h.ErrInvalidRequest(err))
		return
	}

	data.Polygon = &Polygon{}
	data.Geo = geo.PointSet{}
	for _, p := range data.Border {
		gp := geo.Point{p.Lng, p.Lat}
		data.Geo.Push(&gp)
	}

	sql := "insert into polygons values(default, ?, ?, null, ?, ?, ?, ST_PolygonFromText(?, 4326), ?)"
	if err := e.DB.Exec(sql, time.Now(), time.Now(), data.Type, data.Data, data.Color, h.ToPolygonWKT(data.Geo), data.ExtraColor).Error; err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}

	//e.PolygonAdd <- *data.Polygon

	render.Status(r, http.StatusCreated)
	render.Render(w, r, h.SucCreate)

}

func (e *Env) GetPolygonList(w http.ResponseWriter, r *http.Request) {
	var polygons = []*Polygon{}

	sql := "SELECT id, created_at, updated_at, type, data, color, ST_AsBinary(geom) FROM polygons WHERE deleted_at IS NULL"
	rows, err := e.DB.Raw(sql).Rows()
	if err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}

	for rows.Next() {
		var p Polygon
		rows.Scan(&p.ID, &p.CreatedAt, &p.UpdatedAt, &p.Type, &p.Data, &p.Color, &p.Geo)

		polygons = append(polygons, &p)
	}

	if err := render.RenderList(w, r, NewListPolygonResponse(polygons)); err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}
}

func (e *Env) GetPolygon(w http.ResponseWriter, r *http.Request) {
	poly := r.Context().Value("poly").(*Polygon)

	if err := render.Render(w, r, NewPolygonReponse(poly)); err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}
}

func (e *Env) UpdatePolygon(w http.ResponseWriter, r *http.Request) {
	poly := r.Context().Value("poly").(*Polygon)

	data := &PolygonRequest{Polygon:poly, ExtraColor: poly.Color}
	if err := render.Bind(r, data); err != nil {
		render.Render(w, r, h.ErrInvalidRequest(err))
		return
	}

	data.Geo = geo.PointSet{}
	for _, p := range data.Border {
		gp := geo.Point{p.Lng, p.Lat}
		data.Geo.Push(&gp)
	}

	sql := "update polygons set type = ?, set data = ?, color = ?, geom = ST_PolygonFromText(?, 4326), updated_at = ? WHERE id = ?"
	if err := e.DB.Exec(sql,  data.Type, data.Data, data.ExtraColor, h.ToPolygonWKT(data.Geo), time.Now(), poly.ID).Error; err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}

	render.Status(r, http.StatusOK)
	render.Render(w, r, h.SucUpdate)
}

func (e *Env) DeletePolygon(w http.ResponseWriter, r *http.Request) {
	poly := r.Context().Value("poly").(*Polygon)

	sql := "update polygons set deleted_at = ? WHERE id = ?"
	if err := e.DB.Exec(sql, time.Now(), poly.ID).Error; err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}

	render.Status(r, http.StatusOK)
	render.Render(w, r, h.SucDelete)
}

func (e *Env) PolygonCtx(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		pointId, err := strconv.Atoi(chi.URLParam(r, "polygonId"))
		if err != nil {
			render.Render(w, r, h.ErrRender(err))
			return
		}

		p := Polygon{}

		sql := "SELECT id, created_at, updated_at, type, data, color, ST_AsBinary(geom) FROM polygons WHERE id = ? AND deleted_at IS NULL"
		rows, err := e.DB.Raw(sql, pointId).Rows()
		if err != nil {
			render.Render(w, r, h.ErrRender(err))
			return
		}

		for rows.Next() {
			//fmt.Printf("ERROR: %s",)
			rows.Scan(&p.ID, &p.CreatedAt, &p.UpdatedAt, &p.Type, &p.Data, &p.Color, &p.Geo)
		}

		if p.ID == 0 {
			render.Render(w, r, h.ErrNotFound)
			return
		}

		ctx := context.WithValue(r.Context(), "poly", &p)
		next.ServeHTTP(w, r.WithContext(ctx))
	})
}

func NewPolygonReponse(p *Polygon) *PolygonResponse {
	resp := &PolygonResponse{Polygon: p}

	resp.Border = make([]BorderPoint, 0)
	for _, p := range p.Geo {
		resp.Border = append(resp.Border, BorderPoint{Lng: p.Lng(), Lat: p.Lat()})
	}

	return resp
}

func NewListPolygonResponse(polygons []*Polygon) []render.Renderer {
	list := []render.Renderer{}
	for _, polygon := range polygons {
		list = append(list, NewPolygonReponse(polygon))
	}
	return list
}

func (e *Env) CheckPointInPoly(w http.ResponseWriter, r *http.Request) {
	lat := r.URL.Query().Get("lat")
	lon := r.URL.Query().Get("lng")

	geoPoint := "point(" + lon + " " + lat + ")"

	sql := "select id, st_contains( geom, st_geomfromtext(?, 4326)) from polygons WHERE deleted_at IS NULL;"
	rows, err := e.DB.Raw(sql, geoPoint).Rows()
	if err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}

	var response CheckResponse
	response.Areas = make([]uint, 0)

	for rows.Next() {
		var id uint
		var state bool
		rows.Scan(&id, &state)

		if state {
			response.Areas = append(response.Areas, id)
		}
	}

	if err := render.Render(w, r, response); err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}
}

