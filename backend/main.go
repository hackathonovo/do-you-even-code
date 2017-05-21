package main

import (
	"flag"
	"fmt"
	h "github.com/hackathonovo/do-you-even-code/backend/helpers"
	m "github.com/hackathonovo/do-you-even-code/backend/models"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"github.com/pressly/chi"
	"github.com/pressly/chi/docgen"
	"github.com/pressly/chi/middleware"
	"github.com/pressly/chi/render"
	"log"
	"net/http"
	"net/http/httputil"
	"net/url"
	"os"
	"os/signal"
	"path/filepath"
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
	db.LogMode(true)
	db.AutoMigrate(&m.Point{}, &m.User{}, &m.Polygon{}, &m.Session{}, &m.Action{}, &m.Log{}, &m.Timetable{}, &m.ActionNotif{})

	router := chi.NewRouter()
	e := m.NewEnviroment(db)

	router.Use(middleware.RequestID)
	router.Use(middleware.Logger)
	router.Use(middleware.Recoverer)
	router.Use(ReqLogger)
	router.Use(OptionsAllowed)
	router.Use(render.SetContentType(render.ContentTypeJSON))

	router.MethodNotAllowed(NotAllowedHandler)
	router.NotFound(NotFoundHandler)

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

		r.Get("/search", e.SearchUsers)

		r.Route("/:userId", func(r2 chi.Router) {
			r2.Use(e.UserCtx)
			//r2.Get("/lastLocation", e.GetLastLocation)
			r2.Get("/", e.GetUser)
			r2.Delete("/", e.DeleteUser)
			r2.Put("/", e.UpdateUser)

			r2.Route("/location", func(r3 chi.Router) {
				r2.Get("/", e.GetUser)
			})

			r2.Route("/timetable", func(r3 chi.Router) {
				r3.Get("/", e.ListUserTimetables)
			})

			r2.Route("/fcm", func(r3 chi.Router) {
				r3.Post("/", e.RegisterFCM)
			})
		})

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

	router.Route("/timetables", func(router chi.Router) {
		router.Get("/", e.ListTimetables)
		//router.Options("/", Dummy)
		router.Post("/", e.CreateTimetable)

		router.Route("/:timetableId", func(r chi.Router) {
			r.Use(e.TimetableCtx)
			r.Get("/", e.GetTimetable)
			r.Put("/", e.UpdateTimetable)
			r.Delete("/", e.CreateTimetable)
		})
	})

	router.Route("/logger", func(router chi.Router) {
		router.Get("/", e.ListLogs)
		//router.Options("/", Dummy)
		router.Post("/", e.CreateLog)

		router.Route("/:logId", func(r chi.Router) {
			r.Use(e.LogCtx)
			r.Get("/", e.GetLog)
			r.Put("/", e.UpdateLog)
			r.Delete("/", e.DeleteLog)
		})
	})

	router.Route("/actions", func(router chi.Router) {
		router.Get("/", e.ListActions)
		//router.Options("/", Dummy)
		router.Post("/", e.CreateAction)

		router.Route("/:actionId", func(r chi.Router) {
			r.Use(e.ActionCtx)
			r.Get("/", e.GetAction)
			r.Put("/", e.UpdateAction)
			r.Delete("/", e.DeletePoint)

			r.Route("/polygons", func(r2 chi.Router) {
				r2.Get("/", e.GetActionPolygonList)
			})

			r.Route("/points", func(r2 chi.Router) {
				r2.Get("/", e.GetActionPointsList)
			})

			r.Route("/allUsers", func(r2 chi.Router) {
				r2.Get("/", e.GetActionUsers)
				r2.Post("/", e.AddActionUser)
			})

			r.Route("/users", func(r2 chi.Router) {
				r2.Get("/", e.GetActionUsersById)
			})

			r.Route("/search", func(r2 chi.Router) {
				r2.Get("/", e.SearchActionUsers)
			})

			r.Route("/push", func(r2 chi.Router) {
				r2.Get("/", e.PushAction)
			})

			r.Route("/notifications", func(r2 chi.Router) {
				r2.Get("/", e.ListActionNotifsByAction)
				r2.Post("/", e.CreateActionNotif)

				r2.Route("/:actionNotifId", func(r3 chi.Router) {
					r3.Use(e.ActionNotifCtx)
					r3.Get("/", e.GetActionNotif)
					r3.Put("/", e.UpdateActionNotif)
					r3.Delete("/", e.DeleteActionNotif)
				})
			})
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

	router.Get("/address", func(w http.ResponseWriter, r *http.Request) {
		var Url *url.URL
		Url, err := url.Parse("https://maps.googleapis.com/maps/api/geocode/json")
		if err != nil {
			panic("boom")
		}

		parameters := url.Values{}
		parameters.Add("address", chi.URLParam(r, "address"))
		parameters.Add("key", "AIzaSyD8S_Sh6t797n-W9z8SGaqTh0h4k0eaOtU")
		Url.RawQuery = parameters.Encode()

		resp, err := http.Get(Url.String())

		log.Println(resp.Body)
	})

	router.Post("/login", e.LoginUser)

	router.Get("/googleLogin", e.GoogleLogin)
	router.Get("/oauth2", e.GoogleCallback)
	router.Post("/glogin", e.GOAuthLogin)

	router.Get("/push", e.PushSimpleToken)

	workDir, _ := os.Getwd()
	logDir := filepath.Join(workDir, "logs")
	router.FileServer("/logs", http.Dir(logDir))

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

func OptionsAllowed(handler http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		if r.Method == "OPTIONS" {
			w.Header().Set("Access-Control-Allow-Origin", "*")
			w.Header().Set("Access-Control-Allow-Methods", "GET,HEAD,POST,OPTIONS,PUT,DELETE")
			w.Header().Set("Access-Control-Allow-Headers", "Authorization, Content-Type")
			w.Header().Set("Content-Type", "httpd/unix-directory")
			return
		}

		handler.ServeHTTP(w, r)
	})
}

func ReqLogger(handler http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		request, err := httputil.DumpRequest(r, true)
		if err != nil {
			panic(err)
		}
		fmt.Printf("%s \n", request)

		handler.ServeHTTP(w, r)
	})
}

func NotFoundHandler(w http.ResponseWriter, r *http.Request) {
	render.Render(w, r, h.ErrNotFound)
	return
}

func NotAllowedHandler(w http.ResponseWriter, r *http.Request) {
	render.Render(w, r, h.ErrNotAllowed)
	return
}
