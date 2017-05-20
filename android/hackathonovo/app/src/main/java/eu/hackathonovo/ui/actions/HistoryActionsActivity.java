package eu.hackathonovo.ui.actions;

import android.os.Bundle;

import javax.inject.Inject;

import butterknife.ButterKnife;
import eu.hackathonovo.R;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.activities.BaseActivity;

public class HistoryActionsActivity extends BaseActivity implements HistoryView {

    @Inject
    HistoryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_actions);

        ButterKnife.bind(this);
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.setView(this);
    }
}
