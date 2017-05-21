package models

import (
	"github.com/jinzhu/gorm"
	"time"
)

type Env struct {
	DB *gorm.DB
	Help BorderPoint
}

var (
	SERVER_IP = "46.101.106.208"
	API_KEY   = "AIzaSyAIREfXCIPuaM4uvt8ash1qJBMgYOdhTGE"
	FCM_KEY = "AAAANeqapyY:APA91bGmPqLnmXaaHxXD1K55QE28r-QSKE8xcXPuHmIcjQ-9KeEl3qUbkPSzj3ojplxbgpQm-yvPWaltDMC9CTNxyVh3jjwNkelCJLRWwLhD3labFxsjt6EVF7lbT94skKnnIgK-jze8"
	SENDER_ID = "AAAANeqapyY:APA91bGmPqLnmXaaHxXD1K55QE28r-QSKE8xcXPuHmIcjQ-9KeEl3qUbkPSzj3ojplxbgpQm-yvPWaltDMC9CTNxyVh3jjwNkelCJLRWwLhD3labFxsjt6EVF7lbT94skKnnIgK-jze8"
)

func NewEnviroment(db *gorm.DB) *Env {
	env := &Env{DB: db}
	return env
}

type DBModel struct {
	ID        uint       `gorm:"primary_key" json:"id"`
	CreatedAt time.Time  `json:"created_at"`
	UpdatedAt time.Time  `json:"updated_at"`
	DeletedAt *time.Time `sql:"index" json:"deleted_at"`
}
