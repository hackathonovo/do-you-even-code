package main

import (
	"github.com/pressly/chi/render"
	"net/http"
	"os"
	"os/signal"
	"fmt"
	"github.com/pressly/chi"
	"net/http/httputil"
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

	router := chi.NewRouter()

	router.Get("/", func(w http.ResponseWriter, r *http.Request) {
		request, err := httputil.DumpRequest(r, true)
		if err != nil {
			panic(err)
		}
		fmt.Printf("%s \n", request)
		w.Write([]byte("I am root"))
	})

	http.ListenAndServe(":3000", router)

}


