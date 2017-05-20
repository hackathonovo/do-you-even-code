package eu.hackathonovo.ui.home_leader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import eu.hackathonovo.R;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.activities.BaseActivity;

public class HomeLeaderActivity extends BaseActivity implements HomeLeaderView {

    @Inject
    HomeLeaderPresenter presenter;

    public static Intent createIntent(final Context context) {
        return new Intent(context, HomeLeaderActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_leader);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.setView(this);
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }
}
