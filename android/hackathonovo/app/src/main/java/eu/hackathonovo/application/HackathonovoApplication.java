package eu.hackathonovo.application;

import android.app.Application;

import eu.hackathonovo.injection.ComponentFactory;
import eu.hackathonovo.injection.component.ApplicationComponent;
import timber.log.Timber;

public final class HackathonovoApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = ComponentFactory.createApplicationComponent(this);
        applicationComponent.inject(this);
        Timber.plant(new Timber.DebugTree());
        //FacebookSdk.sdkInitialize(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
