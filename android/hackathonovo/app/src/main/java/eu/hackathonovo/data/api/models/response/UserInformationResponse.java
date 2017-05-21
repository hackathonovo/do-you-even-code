package eu.hackathonovo.data.api.models.response;

import com.google.gson.annotations.SerializedName;

public final class UserInformationResponse {

    @SerializedName("token")
    public final String token;

    @SerializedName("id")
    public final long id;

    @SerializedName("role")
    public final String role;

    public UserInformationResponse(final String token, final long id, final String role) {
        this.token = token;
        this.id = id;
        this.role = role;
    }
}
