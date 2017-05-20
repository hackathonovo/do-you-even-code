package eu.hackathonovo.ui.home_leader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import eu.hackathonovo.R;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.activities.BaseActivity;
import eu.hackathonovo.ui.login.HGSSLoginActivity;

public class HomeLeaderActivity extends BaseActivity {

    public static Intent createIntent(final Context context) {
        return new Intent(context, HomeLeaderActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_leader);
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }
}
