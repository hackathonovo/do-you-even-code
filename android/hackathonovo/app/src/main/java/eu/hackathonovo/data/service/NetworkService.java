package eu.hackathonovo.data.service;

import org.json.JSONObject;

import java.util.List;

import eu.hackathonovo.data.api.models.request.AddUsers;
import eu.hackathonovo.data.api.models.request.HGSSUserInformation;
import eu.hackathonovo.data.api.models.request.RescuerLocation;
import eu.hackathonovo.data.api.models.request.ScanImage;
import eu.hackathonovo.data.api.models.request.SearchDetailsData;
import eu.hackathonovo.data.api.models.request.UserInformation;
import eu.hackathonovo.data.api.models.response.ActionResponse;
import eu.hackathonovo.data.api.models.response.FilterUsers;
import eu.hackathonovo.data.api.models.response.ScanImageResponse;
import eu.hackathonovo.data.api.models.response.UserInformationResponse;
import io.reactivex.Single;
import okhttp3.MultipartBody;

public interface NetworkService {

    Single<UserInformation> login(UserInformation userInformation);

    Single<UserInformationResponse> loginHGSS(HGSSUserInformation userInformation);

    Single<JSONObject> scanImage(MultipartBody.Part file);

    Single<ScanImageResponse> scanImageCustom(ScanImage url);

    Single<ActionResponse> sendDetailsData(SearchDetailsData data);

    Single<JSONObject> sendRescuerLocation(RescuerLocation rescuerLocation);

    Single<SearchDetailsData> getActions(int id);

    Single<List<FilterUsers>> filterUsers(String name, int id, int buffer);
    Single<JSONObject> updateUser(int id, AddUsers addUsers);
}
