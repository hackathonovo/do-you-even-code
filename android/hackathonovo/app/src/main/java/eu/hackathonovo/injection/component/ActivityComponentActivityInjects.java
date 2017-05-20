package eu.hackathonovo.injection.component;

import eu.hackathonovo.ui.home.HomeActivity;
import eu.hackathonovo.ui.home_leader.HomeLeaderActivity;
import eu.hackathonovo.ui.home_rescuer.HomeRescuerActivity;
import eu.hackathonovo.ui.login.HGSSLoginActivity;
import eu.hackathonovo.ui.login.LoginActivity;
import eu.hackathonovo.ui.map.MapActivity;
import eu.hackathonovo.ui.photo.PhotoDetailsActivity;
import eu.hackathonovo.ui.photo.TakeOrPickAPhotoActivity;

public interface ActivityComponentActivityInjects {

    void inject(HomeActivity homeActivity);

    void inject(LoginActivity loginActivity);

    void inject(PhotoDetailsActivity photoDetailsActivity);

    void inject(TakeOrPickAPhotoActivity takeOrPickAPhotoActivity);

    void inject(HGSSLoginActivity hgssLoginActivity);

    void inject(HomeLeaderActivity homeLeaderActivity);

    void inject(MapActivity mapActivity);

    void inject(HomeRescuerActivity Activity);
}
