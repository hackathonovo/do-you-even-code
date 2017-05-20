package eu.hackathonovo.ui.photo;

import android.app.Activity;

import java.io.File;

public interface TakeOrPickAPhotoPresenter {

    void setView(TakeOrPickAPhotoView view);

    void getImagesPath(Activity activity);

    void dispose();

    void scanImage(File file);
}
