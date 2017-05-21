package eu.hackathonovo.data.api.models.request;

import com.google.gson.annotations.SerializedName;

public final class RescuerLocation {

    @SerializedName("lat")
    public final float latitude;
    @SerializedName("lng")
    public final float longitude;
    @SerializedName("user_id")
    public final long id;
    public final boolean draggable;
    public final String type;
    public final String data;

    public RescuerLocation(final float latitude, final float longitude, final long id, final boolean draggable, final String type, final String data) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.draggable = draggable;
        this.type = type;
        this.data = data;
    }
}
