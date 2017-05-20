package eu.hackathonovo.data.service;

import org.json.JSONObject;

import eu.hackathonovo.data.api.models.request.ScanImage;
import eu.hackathonovo.data.api.models.request.UserInformation;
import eu.hackathonovo.data.api.models.response.ScanImageResponse;
import io.reactivex.Single;
import okhttp3.MultipartBody;

public interface NetworkService {

    Single<UserInformation> login(UserInformation userInformation);

    Single<JSONObject> scanImage(MultipartBody.Part file);

    Single<ScanImageResponse> scanImageCustom(ScanImage url);
}
