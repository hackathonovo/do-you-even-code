package eu.hackathonovo.data.api.models.request;

import java.io.Serializable;

public final class SearchDetailsData implements Serializable{
    public  String vrijemeNestanka=null;
    public  boolean ozlijeden;
    public  String lokacija=null;
    public  boolean hitnost;
    public  boolean suicidalnost;
    public  String mjestoSastanka=null;
    public  String vrijemeSastanka=null;

    public String getVrijemeNestanka() {
        return vrijemeNestanka;
    }

    public void setVrijemeNestanka(final String vrijemeNestanka) {
        this.vrijemeNestanka = vrijemeNestanka;
    }

    public boolean getOzlijeden() {
        return ozlijeden;
    }

    public void setOzlijeden(final boolean ozlijeden) {
        this.ozlijeden = ozlijeden;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(final String lokacija) {
        this.lokacija = lokacija;
    }

    public boolean getHitnost() {
        return hitnost;
    }

    public void setHitnost(final boolean hitnost) {
        this.hitnost = hitnost;
    }

    public boolean getSuicidalnost() {
        return suicidalnost;
    }

    public void setSuicidalnost(final boolean suicidalnost) {
        this.suicidalnost = suicidalnost;
    }

    public String getMjestoSastanka() {
        return mjestoSastanka;
    }

    public void setMjestoSastanka(final String mjestoSastanka) {
        this.mjestoSastanka = mjestoSastanka;
    }

    public String getVrijemeSastanka() {
        return vrijemeSastanka;
    }

    public void setVrijemeSastanka(final String vrijemeSastanka) {
        this.vrijemeSastanka = vrijemeSastanka;
    }
}
