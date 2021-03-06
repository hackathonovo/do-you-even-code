package models

import (
	"encoding/json"
	"github.com/paulmach/go.geo"
	"log"
	"net/http"
	"net/url"
	"time"
)

type CheckResponse struct {
	Areas []uint `json:"areas"`
}

func (CheckResponse) Render(w http.ResponseWriter, r *http.Request) error {
	return nil
}

type BorderPoint struct {
	Lat float64 `json:"lat"`
	Lng float64 `json:"lng"`
}

func (BorderPoint) Render(w http.ResponseWriter, r *http.Request) error {
	return nil
}

func (BorderPoint) Bind(r *http.Request) error {
	return nil
}

func (e *Env) MakePointOnAddress(address string, actionId uint, table string) {

	var Url *url.URL
	Url, err := url.Parse("https://maps.googleapis.com/maps/api/geocode/json")
	if err != nil {
		log.Println(err.Error())
		return
	}

	parameters := url.Values{}
	parameters.Add("address", address)
	parameters.Add("key", "AIzaSyBWKV70XZt6subVpYBhu1YGi5I3QKFgFDI ")
	Url.RawQuery = parameters.Encode()
	resp, err := http.Get(Url.String())

	decoder := json.NewDecoder(resp.Body)
	var t AddressResponse
	if err := decoder.Decode(&t); err != nil {
		panic(err)
	}
	defer resp.Body.Close()

	if len(t.Results) != 0 {
		point := &Point{Geo: geo.Point{t.Results[0].Geometry.Location.Lng, t.Results[0].Geometry.Location.Lat}}
		if table == "action" {
			point.Type = "base"
		} else {
			point.Type = "last"
		}

		sql := "insert into points values(default, ?, ?, null, ?, ?, ?, ?, ST_GeomFromText(?, 4326))"
		if err := e.DB.Exec(sql, time.Now(), time.Now(), point.Type, point.Data, point.Label, point.Draggable, point.Geo.ToWKT()).Error; err != nil {
			log.Println(err.Error())
			return
		}

		idSql := "select MAX(id) from points"

		rows, err := e.DB.Raw(idSql).Rows()
		if err != nil {
			log.Println(err.Error())
			return
		}

		for rows.Next() {
			rows.Scan(&point.ID)
		}

		sql2 := ""
		if table == "action" {
			sql2 = "insert into action_points values(default, ?, ?, ?)"
		} else {
			sql2 = "insert into user_points values(default, ?, ?, ?)"

		}

		if err := e.DB.Exec(sql2, actionId, point.ID, time.Now()).Error; err != nil {
			log.Println(err.Error())
			return
		}
	}

}

type GoogleGeometry struct {
	Location BorderPoint `json:"location"`
}

type GoogleResults struct {
	Geometry GoogleGeometry `json:"geometry"`
}

type AddressResponse struct {
	Results []GoogleResults `json:"results"`
}
