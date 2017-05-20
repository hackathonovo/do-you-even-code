package eu.hackathonovo.ui.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import eu.hackathonovo.R;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.activities.BaseActivity;

public class MapActivity extends BaseActivity {

    public static Intent createIntent(final Context context) {
        return new Intent(context, MapActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        new WebChromeLoader("http://46.101.148.24/webviews/map/view", this);
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }
}
