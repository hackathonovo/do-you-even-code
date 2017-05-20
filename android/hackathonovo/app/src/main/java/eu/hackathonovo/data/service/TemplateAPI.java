package eu.hackathonovo.data.service;

import org.json.JSONObject;

import eu.hackathonovo.data.api.models.request.HGSSUserInformation;
import eu.hackathonovo.data.api.models.request.UserInformation;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import static eu.hackathonovo.data.api.APIConstants.PATH_ADMIN_LOGIN;
import static eu.hackathonovo.data.api.APIConstants.PATH_LOGIN;

public interface TemplateAPI {

    @POST(PATH_LOGIN)
    Single<UserInformation> login(@Body UserInformation userInformation);

    @POST(PATH_ADMIN_LOGIN)
    Single<UserInformation> loginHGSS(@Body HGSSUserInformation userInformation);

    @Multipart
    @POST("imgs")
    Single<JSONObject> uploadImage(@Part MultipartBody.Part img);
/*
    @GET(PATH_HOTEL)
    Single<List<Hotel>> getHotel(@Path("id") long id);*/
}
