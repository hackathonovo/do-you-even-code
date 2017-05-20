package helpers

import (
	"net/http"
	"github.com/pressly/chi/render"
)

type ErrResponse struct {
	Err            error `json:"-"`
	HTTPStatusCode int   `json:"code"`

	StatusText string `json:"status"`
	ErrorText  string `json:"error,omitempty"`
}

func (e *ErrResponse) Render(w http.ResponseWriter, r *http.Request) error {
	render.Status(r, e.HTTPStatusCode)
	return nil
}

func ErrInvalidRequest(err error) render.Renderer {
	return &ErrResponse{
		Err:            err,
		HTTPStatusCode: 400,
		StatusText:     "You fucked up",
		ErrorText:      err.Error(),
	}
}

func ErrRender(err error) render.Renderer {
	return &ErrResponse{
		Err:            err,
		HTTPStatusCode: 422,
		StatusText:     "Error rendering response.",
		ErrorText:      err.Error(),
	}
}

var ErrNotFound = &ErrResponse{HTTPStatusCode: 404, StatusText: "Resource not found."}
var ErrServer = &ErrResponse{HTTPStatusCode: 500, StatusText: "I fucked up"}
var ErrAuth = &ErrResponse{HTTPStatusCode: 401, StatusText: "Unauthorized"}
var ErrNotAllowed = &ErrResponse{HTTPStatusCode: 405, StatusText: "Method not allowed"}
