package eu.hackathonovo.injection.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.hackathonovo.application.HackathonovoApplication;

@Module
public final class ApplicationModule {

    private final HackathonovoApplication hackathonovoApplication;

    public ApplicationModule(final HackathonovoApplication hackathonovoApplication) {
        this.hackathonovoApplication = hackathonovoApplication;
    }

    @Provides
    @Singleton
    HackathonovoApplication provideApplication() {
        return hackathonovoApplication;
    }
}
