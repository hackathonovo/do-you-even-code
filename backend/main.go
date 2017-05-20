package main

import (
	"github.com/pressly/chi/render"
	"net/http"
	"os"
	"os/signal"
	"fmt"
	"github.com/pressly/chi"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"net/http/httputil"
	m "github.com/hackathonovo/do-you-even-code/backend/models"
	"github.com/jinzhu/gorm"
	"flag"
	"github.com/pressly/chi/docgen"
)

var (
	db        *gorm.DB
	genRoutes = flag.Bool("routes", false, "Generate router documentation")
)

func init() {
	render.Respond = func(w http.ResponseWriter, r *http.Request, v interface{}) {
		w.Header().Set("Access-Control-Allow-Origin", "*")
		w.Header().Set("Access-Control-Allow-Headers", "Authorization, Content-Type")

		render.DefaultResponder(w, r, v)
	}

	c := make(chan os.Signal, 1)
	signal.Notify(c, os.Interrupt)
	go func() {
		for sig := range c {
			fmt.Printf("Recieved sig %s \n", sig.String())
			os.Exit(0)
		}
	}()
}

func main() {
	var err error
	db, err = gorm.Open("postgres", "host=127.0.0.1 port=5432 user=postgres dbname=postgres sslmode=disable password=postgres123")
	if err != nil {
		panic(err)
	}
	defer db.Close()

	db.AutoMigrate(&m.Point{}, &m.User{}, &m.Polygon{})

	router := chi.NewRouter()
	e := m.NewEnviroment(db)

	router.Get("/", func(w http.ResponseWriter, r *http.Request) {
		request, err := httputil.DumpRequest(r, true)
		if err != nil {
			panic(err)
		}
		fmt.Printf("%s \n", request)
		w.Write([]byte("I am root"))
	})

	router.Route("/users", func(r chi.Router) {
		r.Get("/", e.ListUsers)
		r.Post("/", e.CreateUser)
	})

	router.Route("/points", func(router chi.Router) {
		router.Get("/", e.ListPoints)
		//router.Options("/", Dummy)
		router.Post("/", e.CreatePoint)

		router.Route("/:pointId", func(r chi.Router) {
			r.Use(e.PointCtx)
			r.Get("/", e.GetPoint)
			r.Put("/", e.UpdatePoint)
			r.Delete("/", e.DeletePoint)
		})
	})


	router.Route("/polygons", func(r chi.Router) {
		r.Post("/", e.CreatePolygon)
		r.Get("/", e.GetPolygonList)
		r.Get("/check", e.CheckPointInPoly)
		r.Route("/:polygonId", func(r2 chi.Router) {
			r2.Use(e.PolygonCtx)
			r2.Get("/", e.GetPolygon)
			r2.Put("/", e.UpdatePolygon)
			r2.Delete("/", e.DeletePolygon)
		})
	})

	if *genRoutes {
		// fmt.Println(docgen.JSONRoutesDoc(r))
		fmt.Println(docgen.MarkdownRoutesDoc(router, docgen.MarkdownOpts{
			ProjectPath: "github.com/TopHatCroat/awesomeProject",
			Intro:       "Awesome generated docs.",
		}))
		return
	}

	http.ListenAndServe(":3000", router)

}


