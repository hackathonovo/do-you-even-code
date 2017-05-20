package eu.hackathonovo.injection.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import eu.hackathonovo.data.service.NetworkService;
import eu.hackathonovo.data.storage.TemplatePreferences;
import eu.hackathonovo.domain.usecase.LocalImagesUseCase;
import eu.hackathonovo.injection.scope.ForActivity;
import eu.hackathonovo.manager.StringManager;
import eu.hackathonovo.ui.actions.EditActionPresenter;
import eu.hackathonovo.ui.actions.EditActionPresenterImpl;
import eu.hackathonovo.ui.actions.HistoryPresenter;
import eu.hackathonovo.ui.actions.HistoryPresenterImpl;
import eu.hackathonovo.ui.filter.FilterPresenter;
import eu.hackathonovo.ui.filter.FilterPresenterImpl;
import eu.hackathonovo.ui.home.HomePresenter;
import eu.hackathonovo.ui.home.HomePresenterImpl;
import eu.hackathonovo.ui.home_leader.HomeLeaderPresenter;
import eu.hackathonovo.ui.home_leader.HomeLeaderPresenterImpl;
import eu.hackathonovo.ui.home_rescuer.HomeRescuerPresenter;
import eu.hackathonovo.ui.home_rescuer.HomeRescuerPresenterImpl;
import eu.hackathonovo.ui.login.HGSSLoginPresenter;
import eu.hackathonovo.ui.login.HGSSLoginPresenterImpl;
import eu.hackathonovo.ui.login.LoginPresenter;
import eu.hackathonovo.ui.login.LoginPresenterImpl;
import eu.hackathonovo.ui.photo.TakeOrPickAPhotoPresenter;
import eu.hackathonovo.ui.photo.TakeOrPickAPhotoPresenterImpl;
import io.reactivex.Scheduler;

import static eu.hackathonovo.injection.module.ThreadingModule.OBSERVE_SCHEDULER;
import static eu.hackathonovo.injection.module.ThreadingModule.SUBSCRIBE_SCHEDULER;

@Module
public final class PresenterModule {

    @ForActivity
    @Provides
    HomePresenter provideHomePresenter(@Named(SUBSCRIBE_SCHEDULER) Scheduler subscribeScheduler, final NetworkService networkService,
                                       @Named(OBSERVE_SCHEDULER) Scheduler observeScheduler, StringManager stringManager) {
        return new HomePresenterImpl(subscribeScheduler, observeScheduler, stringManager, networkService);
    }

    @ForActivity
    @Provides
    LoginPresenter provideLoginPresenter(@Named(SUBSCRIBE_SCHEDULER) Scheduler subscribeScheduler, final NetworkService networkService,
                                         @Named(OBSERVE_SCHEDULER) Scheduler observeScheduler, StringManager stringManager, final TemplatePreferences templatePreferences) {
        return new LoginPresenterImpl(subscribeScheduler, observeScheduler, stringManager, networkService, templatePreferences);
    }

    @ForActivity
    @Provides
    TakeOrPickAPhotoPresenter provideTakeOrPickAPhotoPresenter(@Named(SUBSCRIBE_SCHEDULER) final Scheduler subscribeScheduler,
                                                               @Named(OBSERVE_SCHEDULER) final Scheduler observeScheduler, final LocalImagesUseCase localImagesUseCase,
                                                               final StringManager stringManager, final NetworkService networkService,
                                                               final TemplatePreferences templatePreferences) {
        return new TakeOrPickAPhotoPresenterImpl(subscribeScheduler, observeScheduler, stringManager, localImagesUseCase, networkService, templatePreferences);
    }

    @ForActivity
    @Provides
    HGSSLoginPresenter provideHgssLoginPresenter(@Named(SUBSCRIBE_SCHEDULER) final Scheduler subscribeScheduler, @Named(OBSERVE_SCHEDULER) final Scheduler observeScheduler,
                                                 final StringManager stringManager, final NetworkService networkService, final TemplatePreferences templatePreferences) {
        return new HGSSLoginPresenterImpl(subscribeScheduler, observeScheduler, stringManager, networkService, templatePreferences);
    }

    @ForActivity
    @Provides
    HomeLeaderPresenter provideHomeLeaderPresenter(@Named(SUBSCRIBE_SCHEDULER) final Scheduler subscribeScheduler, @Named(OBSERVE_SCHEDULER) final Scheduler observeScheduler,
                                                   final StringManager stringManager, final NetworkService networkService, final TemplatePreferences templatePreferences) {
        return new HomeLeaderPresenterImpl(subscribeScheduler, observeScheduler, stringManager, networkService, templatePreferences);
    }

    @ForActivity
    @Provides
    EditActionPresenter getEditActionPresenter(@Named(SUBSCRIBE_SCHEDULER) final Scheduler subscribeScheduler, @Named(OBSERVE_SCHEDULER) final Scheduler observeScheduler,
                                               final StringManager stringManager, final NetworkService networkService, final TemplatePreferences templatePreferences) {
        return new EditActionPresenterImpl(subscribeScheduler, observeScheduler, stringManager, networkService, templatePreferences);
    }

    @ForActivity
    @Provides
    FilterPresenter getFilterPresenter(@Named(SUBSCRIBE_SCHEDULER) final Scheduler subscribeScheduler, @Named(OBSERVE_SCHEDULER) final Scheduler observeScheduler,
                                       final StringManager stringManager, final NetworkService networkService, final TemplatePreferences templatePreferences) {
        return new FilterPresenterImpl(subscribeScheduler, observeScheduler, stringManager, networkService, templatePreferences);
    }

    @ForActivity
    @Provides
    HomeRescuerPresenter provideHomeRescuerPresenter(@Named(SUBSCRIBE_SCHEDULER) final Scheduler subscribeScheduler, @Named(OBSERVE_SCHEDULER) final Scheduler observeScheduler,
                                                     final StringManager stringManager, final NetworkService networkService, final TemplatePreferences templatePreferences) {
        return new HomeRescuerPresenterImpl(subscribeScheduler, observeScheduler, stringManager, networkService, templatePreferences);
    }

    @ForActivity
    @Provides
    HistoryPresenter provideHistoryPresenter(@Named(SUBSCRIBE_SCHEDULER) final Scheduler subscribeScheduler, @Named(OBSERVE_SCHEDULER) final Scheduler observeScheduler,
                                                 final StringManager stringManager, final NetworkService networkService, final TemplatePreferences templatePreferences) {
        return new HistoryPresenterImpl(subscribeScheduler, observeScheduler, stringManager, networkService, templatePreferences);
    }
}
