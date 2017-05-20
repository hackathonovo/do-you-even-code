package eu.hackathonovo.data.api.models.request;

public final class SearchDetailsData {

    public final String vrijemeNestanka;
    public final String ozlijeden;
    public final String lokacija;
    public final String hitnost;
    public final String suicidalnost;
    public final String mjestoSastanka;
    public final String vrijemeSastanka;

    public SearchDetailsData(final String vrijemeNestanka, final String ozlijeden, final String lokacija, final String hitnost, final String suicidalnost,
                             final String mjestoSastanka, final String vrijemeSastanka) {
        this.vrijemeNestanka = vrijemeNestanka;
        this.ozlijeden = ozlijeden;
        this.lokacija = lokacija;
        this.hitnost = hitnost;
        this.suicidalnost = suicidalnost;
        this.mjestoSastanka = mjestoSastanka;
        this.vrijemeSastanka = vrijemeSastanka;
    }
}
