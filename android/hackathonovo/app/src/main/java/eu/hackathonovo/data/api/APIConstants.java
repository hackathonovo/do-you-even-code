package eu.hackathonovo.data.api;

public interface APIConstants {

    String BASE_URL = "http:/46.101.106.208:3000/";
    String BASE_URL2 = "https://southcentralus.api.cognitive.microsoft.com/";

    String PATH_LOGIN = "glogin";
    String PATH_CUSTOM_VISION = "customvision/v1.0/Prediction/0398b65c-3fcb-42e2-aa5b-ff5f1e8f1f3d/url?iterationId=389714f7-800c-4b5d-b3c0-b8fb414035c5";

    String PREDICTION_KEY = "f26374bf1fdd4206af544f4477cfd312";

    String PATH_ADMIN_LOGIN = "login";
    String PATH_IMAGES = "imgs";

    String PATH_SEND_DETAILS = "/actions";
    String PATH_GET_DETAILS = "/actions/{id}";
    String PATH_SEND_RESCUER_LOCATION = "/points";
    String PATH_SEARCH = "users/search";


    String CONTENT_TYPE_OCTET_STREAM = "Content-Type: application/octet-stream";

}
