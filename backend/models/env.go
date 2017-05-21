package models

import (
	"github.com/jinzhu/gorm"
	"time"
)

type Env struct {
	DB *gorm.DB
}

var (
	SERVER_IP = "46.101.106.208"
	API_KEY   = " AIzaSyA7OumbS43G5QPJuUjgr11pfMZiP8GO2z4 "
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
