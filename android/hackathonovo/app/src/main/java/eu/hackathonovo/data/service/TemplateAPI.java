package eu.hackathonovo.data.service;

import org.json.JSONObject;

import eu.hackathonovo.data.api.models.request.HGSSUserInformation;
import eu.hackathonovo.data.api.models.request.SearchDetailsData;
import eu.hackathonovo.data.api.models.request.UserInformation;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import static eu.hackathonovo.data.api.APIConstants.PATH_ADMIN_LOGIN;
import static eu.hackathonovo.data.api.APIConstants.PATH_IMAGES;
import static eu.hackathonovo.data.api.APIConstants.PATH_LOGIN;
import static eu.hackathonovo.data.api.APIConstants.PATH_SEND_DETAILS;

public interface TemplateAPI {

    @POST(PATH_LOGIN)
    Single<UserInformation> login(@Body UserInformation userInformation);

    @POST(PATH_ADMIN_LOGIN)
    Single<UserInformation> loginHGSS(@Body HGSSUserInformation userInformation);

    @Multipart
    @POST(PATH_IMAGES)
    Single<JSONObject> uploadImage(@Part MultipartBody.Part img);

    @POST(PATH_SEND_DETAILS)
    Single<JSONObject> sendDetails(@Body SearchDetailsData searchDetailsData);
    /*
    @GET(PATH_HOTEL)
    Single<List<Hotel>> getHotel(@Path("id") long id);*/
}
