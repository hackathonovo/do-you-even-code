package eu.hackathonovo.data.api.models.request;

import com.google.gson.annotations.SerializedName;

public final class ScanImage {

    @SerializedName("Url")
    public final String url;

    public ScanImage(final String url) {
        this.url = url;
    }
}
