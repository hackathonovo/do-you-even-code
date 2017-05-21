package eu.hackathonovo.injection.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import eu.hackathonovo.injection.scope.ForActivity;
import eu.hackathonovo.ui.base.activities.BaseActivity;

@Module
public final class ActivityModule {

    private final BaseActivity baseActivity;

    public ActivityModule(final BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @ForActivity
    @Provides
    public Activity provideActivity() {
        return baseActivity;
    }
}
