package eu.hackathonovo.injection.component;

import dagger.Component;
import eu.hackathonovo.injection.module.ActivityModule;
import eu.hackathonovo.injection.module.PresenterModule;
import eu.hackathonovo.injection.module.RouterModule;
import eu.hackathonovo.injection.scope.ForActivity;
import eu.hackathonovo.ui.actions.EditActionPresenter;
import eu.hackathonovo.ui.actions.HistoryPresenter;
import eu.hackathonovo.ui.base.activities.BaseActivity;
import eu.hackathonovo.ui.filter.FilterPresenter;
import eu.hackathonovo.ui.home.HomePresenter;
import eu.hackathonovo.ui.home.HomeRouter;
import eu.hackathonovo.ui.home_leader.HomeLeaderPresenter;
import eu.hackathonovo.ui.home_rescuer.HomeRescuerPresenter;
import eu.hackathonovo.ui.login.HGSSLoginPresenter;
import eu.hackathonovo.ui.login.LoginPresenter;
import eu.hackathonovo.ui.photo.TakeOrPickAPhotoPresenter;

@ForActivity
@Component(
        dependencies = {
                ApplicationComponent.class
        },
        modules = {
                ActivityModule.class,
                PresenterModule.class,
                RouterModule.class
        }
)
public interface ActivityComponent extends ActivityComponentActivityInjects, ActivityComponentFragmentsInjects {

    final class Initializer {

        private Initializer() {
        }

        public static ActivityComponent init(final ApplicationComponent applicationComponent, final BaseActivity activity) {
            return DaggerActivityComponent.builder()
                                          .applicationComponent(applicationComponent)
                                          .activityModule(new ActivityModule(activity))
                                          .build();
        }
    }

    HomeRouter getHomeRouter();

    HomePresenter getHomePresenter();

    LoginPresenter getLoginPresenter();

    TakeOrPickAPhotoPresenter getTakeOrPickAPhotoPresenter();

    HGSSLoginPresenter getHgssLoginPresenter();

    HomeRescuerPresenter getHomeRescuerPresenter();

    HomeLeaderPresenter getHomeLeaderPresenter();

    EditActionPresenter getEditActionPresenter();

    FilterPresenter getFilterPresenter();

    HistoryPresenter getHistoryPresenter();
}
