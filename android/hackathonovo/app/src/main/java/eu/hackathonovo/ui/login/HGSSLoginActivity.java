package eu.hackathonovo.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.hackathonovo.R;
import eu.hackathonovo.data.api.models.request.HGSSUserInformation;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.activities.BaseActivity;
import eu.hackathonovo.ui.home_leader.HomeLeaderActivity;

public class HGSSLoginActivity extends BaseActivity implements HGSSLoginView {

    @Inject
    HGSSLoginPresenter presenter;

    @BindView(R.id.login_email_text)
    TextView usernameTextView;

    @BindView(R.id.login_password_text)
    TextView passwordTextView;

    public static Intent createIntent(final Context context) {
        return new Intent(context, HGSSLoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hgsslogin);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.setView(this);
    }

    @OnClick(R.id.login_button)
    public void login() {
        presenter.loginHGSS(new HGSSUserInformation(usernameTextView.getText().toString(), passwordTextView.getText().toString()));
    }

    @Override
    public void goToSaverHome() {
        startActivity(HomeLeaderActivity.createIntent(this));
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }
}
