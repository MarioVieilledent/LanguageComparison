package main

import (
	"fmt"
	"net/http"
)

func main() {
	port := "3000"

	fs := http.FileServer(http.Dir("./display"))
	http.Handle("/", fs)
	fmt.Println("Listening on", port)
	err := http.ListenAndServe(":"+port, nil)
	if err != nil {
		fmt.Println("Error serving static files")
	}
}
