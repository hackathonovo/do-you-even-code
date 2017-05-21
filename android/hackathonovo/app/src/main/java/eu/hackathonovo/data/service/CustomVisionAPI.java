package eu.hackathonovo.data.service;

import eu.hackathonovo.data.api.models.request.ScanImage;
import eu.hackathonovo.data.api.models.response.ScanImageResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

import static eu.hackathonovo.data.api.APIConstants.PATH_CUSTOM_VISION;

public interface CustomVisionAPI {

    @POST(PATH_CUSTOM_VISION)
    Single<ScanImageResponse> scanImage(@Header("Prediction-Key") String predictionKey, @Body ScanImage file);
}
