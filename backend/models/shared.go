package models

import "net/http"

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
