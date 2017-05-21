package eu.hackathonovo.injection.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.hackathonovo.application.HackathonovoApplication;
import eu.hackathonovo.manager.StringManager;
import eu.hackathonovo.manager.StringManagerImpl;

@Module
public final class ManagerModule {

    @Provides
    @Singleton
    StringManager provideStringManager(final HackathonovoApplication application) {
        return new StringManagerImpl(application.getResources());
    }
}
