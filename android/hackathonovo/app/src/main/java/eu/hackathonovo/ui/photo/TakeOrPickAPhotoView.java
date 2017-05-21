package eu.hackathonovo.ui.photo;

import java.util.List;
import java.util.Map;

public interface TakeOrPickAPhotoView {

    void showImagesPath(List<String> imageList);

    void goToPhotoDetails(String url);

    void showDialog(Map<String, Double> map);
}
