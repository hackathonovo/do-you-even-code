package eu.hackathonovo.injection.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import eu.hackathonovo.injection.scope.ForActivity;
import eu.hackathonovo.ui.home.HomeRouter;
import eu.hackathonovo.ui.home.HomeRouterImpl;

@Module
public final class RouterModule {

    @ForActivity
    @Provides
    HomeRouter provideHomeRouter(final Activity activity) {
        return new HomeRouterImpl(activity);
    }
}
