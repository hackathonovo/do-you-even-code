package eu.hackathonovo.injection.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import eu.hackathonovo.data.service.NetworkService;
import eu.hackathonovo.data.storage.TemplatePreferences;
import eu.hackathonovo.domain.usecase.LocalImagesUseCase;
import eu.hackathonovo.injection.scope.ForActivity;
import eu.hackathonovo.manager.StringManager;
import eu.hackathonovo.ui.home.HomePresenter;
import eu.hackathonovo.ui.home.HomePresenterImpl;
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
                                         @Named(OBSERVE_SCHEDULER) Scheduler observeScheduler, StringManager stringManager) {
        return new LoginPresenterImpl(subscribeScheduler, observeScheduler, stringManager, networkService);
    }

    @ForActivity
    @Provides
    TakeOrPickAPhotoPresenter provideTakeOrPickAPhotoPresenter(@Named(SUBSCRIBE_SCHEDULER) final Scheduler subscribeScheduler,
                                                               @Named(OBSERVE_SCHEDULER) final Scheduler observeScheduler, final LocalImagesUseCase localImagesUseCase,
                                                               final StringManager stringManager, final NetworkService networkService, final TemplatePreferences templatePreferences) {
        return new TakeOrPickAPhotoPresenterImpl(subscribeScheduler, observeScheduler, stringManager, localImagesUseCase, networkService, templatePreferences);
    }
}
