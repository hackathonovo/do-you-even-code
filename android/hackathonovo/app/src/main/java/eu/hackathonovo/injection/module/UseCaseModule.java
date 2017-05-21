package eu.hackathonovo.injection.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.hackathonovo.data.service.NetworkService;
import eu.hackathonovo.data.storage.TemplatePreferences;
import eu.hackathonovo.domain.usecase.LocalImagesUseCase;
import eu.hackathonovo.domain.usecase.LocalImagesUseCaseImpl;

@Module
public final class UseCaseModule {

    @Provides
    @Singleton
    LocalImagesUseCase provideLocalImagesUseCase(final TemplatePreferences preferences, final NetworkService networkService) {
        return new LocalImagesUseCaseImpl();
    }
}
