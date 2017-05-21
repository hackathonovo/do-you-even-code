package eu.hackathonovo.injection;

import eu.hackathonovo.application.HackathonovoApplication;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.injection.component.ApplicationComponent;
import eu.hackathonovo.ui.base.activities.BaseActivity;

public final class ComponentFactory {

    private ComponentFactory() { }

    public static ApplicationComponent createApplicationComponent(final HackathonovoApplication application) {
        return ApplicationComponent.Initializer.init(application);
    }

    public static ActivityComponent createActivityComponent(final HackathonovoApplication application, final BaseActivity activity) {
        return ActivityComponent.Initializer.init(application.getApplicationComponent(), activity);
    }
}
