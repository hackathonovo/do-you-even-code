package eu.hackathonovo.injection.component;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import eu.hackathonovo.application.HackathonovoApplication;
import eu.hackathonovo.data.service.NetworkService;
import eu.hackathonovo.data.storage.TemplatePreferences;
import eu.hackathonovo.device.ApplicationInformation;
import eu.hackathonovo.device.DeviceInformation;
import eu.hackathonovo.domain.usecase.LocalImagesUseCase;
import eu.hackathonovo.injection.module.ApiModule;
import eu.hackathonovo.injection.module.ApplicationModule;
import eu.hackathonovo.injection.module.DataModule;
import eu.hackathonovo.injection.module.DeviceModule;
import eu.hackathonovo.injection.module.ManagerModule;
import eu.hackathonovo.injection.module.ThreadingModule;
import eu.hackathonovo.injection.module.UseCaseModule;
import eu.hackathonovo.manager.StringManager;
import io.reactivex.Scheduler;
import okhttp3.OkHttpClient;

import static eu.hackathonovo.injection.module.ThreadingModule.OBSERVE_SCHEDULER;
import static eu.hackathonovo.injection.module.ThreadingModule.SUBSCRIBE_SCHEDULER;

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                ApiModule.class,
                ManagerModule.class,
                DataModule.class,
                ThreadingModule.class,
                UseCaseModule.class,
                DeviceModule.class
        }
)

public interface ApplicationComponent extends ApplicationComponentInjects {

    final class Initializer {

        private Initializer() {
        }

        public static ApplicationComponent init(final HackathonovoApplication hackathonovoApplication) {
            return DaggerApplicationComponent.builder()
                                             .applicationModule(new ApplicationModule(hackathonovoApplication))
                                             .apiModule(new ApiModule())
                                             .build();
        }
    }

    @Named(OBSERVE_SCHEDULER)
    Scheduler getObserveScheduler();

    @Named(SUBSCRIBE_SCHEDULER)
    Scheduler getSubscribeScheduler();

    StringManager getStringManager();

    OkHttpClient getOkHttpClient();

    DeviceInformation getDeviceInformation();

    ApplicationInformation getApplicationInformation();

    TemplatePreferences getTemplatePreferences();

    NetworkService getNetworkService();

    LocalImagesUseCase getLocalImagesUseCase();
}
