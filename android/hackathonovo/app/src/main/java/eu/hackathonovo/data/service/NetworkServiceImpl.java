package eu.hackathonovo.data.service;

import org.json.JSONObject;

import eu.hackathonovo.data.api.APIConstants;
import eu.hackathonovo.data.api.models.request.HGSSUserInformation;
import eu.hackathonovo.data.api.models.request.RescuerLocation;
import eu.hackathonovo.data.api.models.request.ScanImage;
import eu.hackathonovo.data.api.models.request.SearchDetailsData;
import eu.hackathonovo.data.api.models.request.UserInformation;
import eu.hackathonovo.data.api.models.response.ActionResponse;
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
    public Single<JSONObject> scanImage(final MultipartBody.Part file) {
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
}
