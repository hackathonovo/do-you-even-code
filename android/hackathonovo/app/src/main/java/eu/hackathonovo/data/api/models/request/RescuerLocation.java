package eu.hackathonovo.data.api.models.request;

public final class RescuerLocation {

    public final String latitude;
    public final String longitude;
    public final long id;

    public RescuerLocation(final String latitude, final String longitude, final long id) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
    }
}
