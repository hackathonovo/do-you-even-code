package eu.hackathonovo.ui.base.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import eu.hackathonovo.application.HackathonovoApplication;
import eu.hackathonovo.injection.ComponentFactory;
import eu.hackathonovo.injection.component.ActivityComponent;

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final HackathonovoApplication hackathonovoApplication = (HackathonovoApplication) getApplication();
        initActivityComponent(hackathonovoApplication);
        inject(activityComponent);
    }

    public final ActivityComponent getActivityComponent(final HackathonovoApplication hackathonovoApplication) {
        if (activityComponent == null) {
            initActivityComponent(hackathonovoApplication);
        }
        return activityComponent;
    }

    private void initActivityComponent(final HackathonovoApplication hackathonovoApplication) {
        activityComponent = ComponentFactory.createActivityComponent(hackathonovoApplication, this);
    }

    protected abstract void inject(ActivityComponent activityComponent);
}
