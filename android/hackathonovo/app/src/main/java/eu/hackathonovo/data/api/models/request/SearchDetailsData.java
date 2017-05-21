package eu.hackathonovo.data.api.models.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class SearchDetailsData implements Serializable {

    @SerializedName("time_reported")
    public String vrijemeNestanka = null;

    @SerializedName("injury")
    public String ozlijeden;

    @SerializedName("address")
    public String lokacija = null;

    @SerializedName("urgency")
    public String hitnost;
    @SerializedName("suicidal")
    public boolean suicidalnost;
    @SerializedName("meeting_address")
    public String mjestoSastanka = null;
    @SerializedName("time_meeting")
    public String vrijemeSastanka = null;
    public String vrijemeOd;
    public String vrijemeDo;
    public String type;
    public String data;
    public int id;

    public String getVrijemeNestanka() {
        return vrijemeNestanka;
    }

    public void setVrijemeNestanka(final String vrijemeNestanka) {
        this.vrijemeNestanka = vrijemeNestanka;
    }

    public String isOzlijeden() {
        return ozlijeden;
    }

    public void setOzlijeden(final String ozlijeden) {
        this.ozlijeden = ozlijeden;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(final String lokacija) {
        this.lokacija = lokacija;
    }

    public String getHitnost() {
        return hitnost;
    }

    public void setHitnost(final String hitnost) {
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

    public String getVrijemeOd() {
        return vrijemeOd;
    }

    public void setVrijemeOd(final String vrijemeOd) {
        this.vrijemeOd = vrijemeOd;
    }

    public String getVrijemeDo() {
        return vrijemeDo;
    }

    public void setVrijemeDo(final String vrijemeDo) {
        this.vrijemeDo = vrijemeDo;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }
}
