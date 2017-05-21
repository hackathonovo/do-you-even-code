package eu.hackathonovo.data.api.models.request;

public class LostPerson {

    public final float lat;
    public final float lng;

    public LostPerson(final float lat, final float lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
