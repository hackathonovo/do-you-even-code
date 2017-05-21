package eu.hackathonovo.data.api.models.request;

public class FirebasePojo {
    String fcm_token;

    public FirebasePojo(final String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(final String fcm_token) {
        this.fcm_token = fcm_token;
    }
}
