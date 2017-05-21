package models

import (
	"github.com/pressly/chi/render"
	"os"
	"path/filepath"
	"io"
	"net/http"
	"errors"
	h "github.com/hackathonovo/do-you-even-code/backend/helpers"
	"github.com/twinj/uuid"
)

type Image struct {
	DBModel
	Path   string `json:"path"`
	User   User   `json:"-"`
	UserID uint   `json:"user_id"`
}

func (e *Env) ImageUpload(w http.ResponseWriter, r *http.Request) {
	err := r.ParseMultipartForm(5 * 1024 * 1024) //5 MB
	if err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}

	m := r.MultipartForm

	files := m.File["img"]

	if len(files) == 0 {
		render.Render(w, r, h.ErrRender(errors.New("No file with name 'img'")))
		return
	}

	for i, _ := range files {
		//for each fileheader, get a handle to the actual file
		file, err := files[i].Open()
		defer file.Close()
		if err != nil {
			render.Render(w, r, h.ErrRender(err))
			return
		}
		//create destination file making sure the path is writeable.
		workDir, _ := os.Getwd()
		filesDir := filepath.Join(workDir, "imgs")

		//dst, err := os.Create(filepath.Join(filesDir, files[i].Filename))
		fileName := uuid.NewV4().String() + filepath.Ext(files[i].Filename)
		dst, err := os.Create(filepath.Join(filesDir, fileName))
		defer dst.Close()
		if err != nil {
			render.Render(w, r, h.ErrRender(err))
			return
		}
		//copy the uploaded file to the destination file
		if _, err := io.Copy(dst, file); err != nil {
			render.Render(w, r, h.ErrRender(err))
			return
		}

		img := Image{UserID: 6, Path: "http://" + SERVER_IP + ":3000/imgs/" + fileName}
		if err := e.DB.Create(&img).Error; err != nil {
			render.Render(w, r, h.ErrRender(err))
			return
		}

		if err := render.Render(w, r, NewImageResponse(&img)); err != nil {
			render.Render(w, r, h.ErrRender(err))
			return
		}
		return
	}

	render.Status(r, http.StatusBadRequest)
	render.Render(w, r, h.ErrInvalidRequest(errors.New("Something went wrong")))
}

type ImageRequest struct {
	UserID uint `json:"user_id"`
}

func (ImageRequest) Bind(r *http.Request) error {
	return nil
}

func (e *Env) ListImages(w http.ResponseWriter, r *http.Request) {
	var images = []*Image{}
	if err := e.DB.Where("user_id = ?", 6).Find(&images).Error; err != nil {
		render.Render(w, r, h.ErrRender(err))
		return
	}

	if err := render.RenderList(w, r, NewImageListReponse(images)); err != nil {
		render.Render(w, r, h.ErrServer)
		return
	}
}

func NewImageListReponse(images []*Image) []render.Renderer {
	list := []render.Renderer{}
	for _, image := range images {
		list = append(list, NewImageResponse(image))
	}
	return list
}

type ImageResponse struct {
	*Image
}

func (ImageResponse) Render(w http.ResponseWriter, r *http.Request) error {
	return nil
}

func NewImageResponse(p *Image) *ImageResponse {
	resp := &ImageResponse{Image: p}
	return resp
}

