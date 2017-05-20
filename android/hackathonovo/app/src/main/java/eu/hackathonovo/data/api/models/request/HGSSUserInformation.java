package eu.hackathonovo.data.api.models.request;

import com.google.gson.annotations.SerializedName;

public class HGSSUserInformation {

    @SerializedName("username")
    public final String username;

    @SerializedName("password")
    public final String password;

    public HGSSUserInformation(final String username, final String password) {
        this.username = username;
        this.password = password;
    }
}
