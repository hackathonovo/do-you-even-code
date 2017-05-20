package eu.hackathonovo.data.api.models.request;

import com.google.gson.annotations.SerializedName;

public final class UserInformation {

    @SerializedName("token")
    public final String token;

    public UserInformation(final String token) {
        this.token = token;
    }
}
