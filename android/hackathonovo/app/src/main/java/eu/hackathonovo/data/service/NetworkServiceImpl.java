package eu.hackathonovo.data.service;

import org.json.JSONObject;

import java.util.List;

import eu.hackathonovo.data.api.APIConstants;
import eu.hackathonovo.data.api.models.request.AddUsers;
import eu.hackathonovo.data.api.models.request.FirebasePojo;
import eu.hackathonovo.data.api.models.request.HGSSUserInformation;
import eu.hackathonovo.data.api.models.request.LostPerson;
import eu.hackathonovo.data.api.models.request.RescuerLocation;
import eu.hackathonovo.data.api.models.request.ScanImage;
import eu.hackathonovo.data.api.models.request.SearchDetailsData;
import eu.hackathonovo.data.api.models.request.UserInformation;
import eu.hackathonovo.data.api.models.response.ActionResponse;
import eu.hackathonovo.data.api.models.response.FilterUsers;
import eu.hackathonovo.data.api.models.response.ImageResponse;
import eu.hackathonovo.data.api.models.response.ScanImageResponse;
import eu.hackathonovo.data.api.models.response.UserInformationResponse;
import io.reactivex.Single;
import okhttp3.MultipartBody;

public final class NetworkServiceImpl implements NetworkService {

    private final TemplateAPI templateAPI;
    private final CustomVisionAPI customVisionAPI;

    public NetworkServiceImpl(final TemplateAPI templateAPI, final CustomVisionAPI customVisionAPI) {
        this.templateAPI = templateAPI;
        this.customVisionAPI = customVisionAPI;
    }

    @Override
    public Single<UserInformation> login(final UserInformation userInformation) {
        return Single.defer(() -> templateAPI.login(userInformation));
    }

    @Override
    public Single<UserInformationResponse> loginHGSS(final HGSSUserInformation userInformation) {
        return Single.defer(() -> templateAPI.loginHGSS(userInformation));
    }

    @Override
    public Single<ImageResponse> scanImage(final MultipartBody.Part file) {
        return Single.defer(() -> templateAPI.uploadImage(file));
    }

    @Override
    public Single<ScanImageResponse> scanImageCustom(final ScanImage scanImage) {
        return Single.defer(() -> customVisionAPI.scanImage(APIConstants.PREDICTION_KEY, scanImage));
    }

    @Override
    public Single<ActionResponse> sendDetailsData(final SearchDetailsData data) {
        return Single.defer(() -> templateAPI.sendDetails(data));
    }

    @Override
    public Single<JSONObject> sendRescuerLocation(final RescuerLocation rescuerLocation) {
        return Single.defer(() -> templateAPI.sendRescuerLocation(rescuerLocation));
    }

    @Override
    public Single<SearchDetailsData> getActions(final int id) {
        return Single.defer(() -> templateAPI.getActions(id));
    }

    @Override
    public Single<List<FilterUsers>> filterUsers(final String name, final int id, final int buffer) {
        return Single.defer(() -> templateAPI.filterUsers(name, buffer, id));
    }

    @Override
    public Single<JSONObject> updateUser(final int id, final AddUsers addUsers) {
        return Single.defer(() -> templateAPI.updateUser(id, addUsers));
    }


    @Override
    public Single<JSONObject> sendToken(final int id, final String token) {
        return Single.defer(() -> templateAPI.senGcmRegistration(new FirebasePojo(token),id));
    }


    @Override
    public Single<JSONObject> updateAction(final int id, final SearchDetailsData addUsers) {
        return Single.defer(() -> templateAPI.updateAction(id, addUsers));
    }


    @Override
    public Single<JSONObject> lostPerson(LostPerson lostPerson) {
        return Single.defer(() -> templateAPI.lostPerson(lostPerson));
    }
}
