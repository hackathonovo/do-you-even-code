package eu.hackathonovo.data.service;

import org.json.JSONObject;

import java.util.List;

import eu.hackathonovo.data.api.models.request.AddUsers;
import eu.hackathonovo.data.api.models.request.FirebasePojo;
import eu.hackathonovo.data.api.models.request.HGSSUserInformation;
import eu.hackathonovo.data.api.models.request.LostPerson;
import eu.hackathonovo.data.api.models.request.RescuerLocation;
import eu.hackathonovo.data.api.models.request.SearchDetailsData;
import eu.hackathonovo.data.api.models.request.UserInformation;
import eu.hackathonovo.data.api.models.response.ActionResponse;
import eu.hackathonovo.data.api.models.response.FilterUsers;
import eu.hackathonovo.data.api.models.response.ImageResponse;
import eu.hackathonovo.data.api.models.response.UserInformationResponse;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static eu.hackathonovo.data.api.APIConstants.PATH_ADMIN_LOGIN;
import static eu.hackathonovo.data.api.APIConstants.PATH_FCM_REG;
import static eu.hackathonovo.data.api.APIConstants.PATH_GET_DETAILS;
import static eu.hackathonovo.data.api.APIConstants.PATH_IMAGES;
import static eu.hackathonovo.data.api.APIConstants.PATH_LOGIN;
import static eu.hackathonovo.data.api.APIConstants.PATH_LOST_PERSON;
import static eu.hackathonovo.data.api.APIConstants.PATH_SEARCH;
import static eu.hackathonovo.data.api.APIConstants.PATH_SEND_DETAILS;
import static eu.hackathonovo.data.api.APIConstants.PATH_SEND_RESCUER_LOCATION;
import static eu.hackathonovo.data.api.APIConstants.PATH_UPDATE_USER;

public interface TemplateAPI {

    @POST(PATH_LOGIN)
    Single<UserInformation> login(@Body UserInformation userInformation);

    @POST(PATH_ADMIN_LOGIN)
    Single<UserInformationResponse> loginHGSS(@Body HGSSUserInformation userInformation);

    @Multipart
    @POST(PATH_IMAGES)
    Single<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    @POST(PATH_SEND_DETAILS)
    Single<ActionResponse> sendDetails(@Body SearchDetailsData searchDetailsData);

    @POST(PATH_SEND_RESCUER_LOCATION)
    Single<JSONObject> sendRescuerLocation(@Body RescuerLocation rescuerLocation);

    @GET(PATH_GET_DETAILS)
    Single<SearchDetailsData> getActions(@Path("id") int id);

    @PUT(PATH_UPDATE_USER)
    Single<JSONObject> updateUser(@Path("id") int id, @Body AddUsers addUsers);

    @PUT(PATH_UPDATE_USER)
    Single<JSONObject> updateAction(@Path("id") int id, @Body SearchDetailsData data);

    @GET(PATH_SEARCH)
    Single<List<FilterUsers>> filterUsers(@Query("name") String name, @Query("buffer") int buffer, @Query("actionId") int id);

    @POST(PATH_FCM_REG)
    Single<JSONObject> senGcmRegistration(@Body FirebasePojo fcm, @Path("id") int id);


     @POST(PATH_LOST_PERSON)
    Single<JSONObject> lostPerson(@Body LostPerson lostPerson);


}
