package eu.hackathonovo.injection.component;

import eu.hackathonovo.ui.actions.EditActionsActivity;
import eu.hackathonovo.ui.actions.HistoryActionsActivity;
import eu.hackathonovo.ui.filter.ActionEditFragment;
import eu.hackathonovo.ui.filter.FilterActivity;
import eu.hackathonovo.ui.filter.FilterFragment;
import eu.hackathonovo.ui.filter.MapFragment;
import eu.hackathonovo.ui.filter.UsersMapFragment;
import eu.hackathonovo.ui.home.HomeActivity;
import eu.hackathonovo.ui.home_leader.HomeLeaderActivity;
import eu.hackathonovo.ui.home_leader.MeetingTimeFragment;
import eu.hackathonovo.ui.home_rescuer.DetaljiFragment;
import eu.hackathonovo.ui.home_rescuer.HomeRescuerActivity;
import eu.hackathonovo.ui.home_rescuer.RescuerMapFragment;
import eu.hackathonovo.ui.login.HGSSLoginActivity;
import eu.hackathonovo.ui.login.LoginActivity;
import eu.hackathonovo.ui.map.MapActivity;
import eu.hackathonovo.ui.map.MapActivity2;
import eu.hackathonovo.ui.photo.PhotoDetailsActivity;
import eu.hackathonovo.ui.photo.TakeOrPickAPhotoActivity;

public interface ActivityComponentActivityInjects {

    void inject(HomeActivity homeActivity);
    void inject(DetaljiFragment homeActivity);

    void inject(RescuerMapFragment homeActivity);

    void inject(ActionEditFragment homeActivity);

    void inject(MapActivity2 homeActivity);

    void inject(UsersMapFragment homeActivity);

    void inject(HistoryActionsActivity homeActivity);

    void inject(LoginActivity loginActivity);

    void inject(PhotoDetailsActivity photoDetailsActivity);

    void inject(TakeOrPickAPhotoActivity takeOrPickAPhotoActivity);

    void inject(HGSSLoginActivity hgssLoginActivity);

    void inject(HomeLeaderActivity homeLeaderActivity);

    void inject(MapActivity mapActivity);

    void inject(HomeRescuerActivity Activity);

    void inject(MeetingTimeFragment Activity);

    void inject(EditActionsActivity Activity);

    void inject(FilterActivity Activity);

    void inject(MapFragment Activity);

    void inject(FilterFragment Activity);
}
