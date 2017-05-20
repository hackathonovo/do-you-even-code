package eu.hackathonovo.injection.module;

import android.provider.Settings;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.hackathonovo.application.HackathonovoApplication;
import eu.hackathonovo.data.storage.PreferenceRepository;
import eu.hackathonovo.data.storage.SecureSharedPreferences;
import eu.hackathonovo.data.storage.TemplatePreferences;

@Module
public final class DataModule {

    private static final String PREFS_NAME = "templateSecureStorage";

    @Singleton
    @Provides
    TemplatePreferences provideTemplatePreferences(final SecureSharedPreferences securePreferences) {
        return TemplatePreferences.create(securePreferences);
    }

    @Provides
    @Singleton
    PreferenceRepository providePreferenceRepository(final TemplatePreferences templatePreferences) {
        return templatePreferences;
    }

    @Provides
    @Singleton
    public SecureSharedPreferences provideSecureSharedPreferences(final HackathonovoApplication context) {
        final String androidSecret = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return new SecureSharedPreferences(context, context.getSharedPreferences(PREFS_NAME, 0), androidSecret);
    }
}
