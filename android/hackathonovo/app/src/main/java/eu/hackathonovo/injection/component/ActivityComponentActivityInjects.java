package eu.hackathonovo.injection.component;

import eu.hackathonovo.ui.home.HomeActivity;
import eu.hackathonovo.ui.login.LoginActivity;
import eu.hackathonovo.ui.photo.PhotoDetailsActivity;
import eu.hackathonovo.ui.photo.TakeOrPickAPhotoActivity;

public interface ActivityComponentActivityInjects {

    void inject(HomeActivity homeActivity);

    void inject(LoginActivity loginActivity);

    void inject(PhotoDetailsActivity photoDetailsActivity);

    void inject(TakeOrPickAPhotoActivity takeOrPickAPhotoActivity);
}
