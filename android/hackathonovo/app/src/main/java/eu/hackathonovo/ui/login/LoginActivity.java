package eu.hackathonovo.ui.login;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.hackathonovo.R;
import eu.hackathonovo.data.api.models.request.UserInformation;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.activities.BaseActivity;
import eu.hackathonovo.ui.photo.TakeOrPickAPhotoActivity;
import timber.log.Timber;

public class LoginActivity extends BaseActivity implements LoginView {

    @Inject
    LoginPresenter presenter;

    /*@BindView(R.id.facebook_button)
    LoginButton facebookButton;*/


    private static final int RC_SIGN_IN = 9001;
    private static final int AUDIO_PERMISSION_CODE = 10;

    private static final String CLIENT_ID = "1046962736770-02go8vpf211hjehb949ljq8umtb67tsj.apps.googleusercontent.com";

    private CallbackManager callbackManager;

    private GoogleApiClient mGoogleApiClient;

    public String token;

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
/*
        if (presenter.checkIfLoggedIn() != 0) {
            startActivity(HomeActivity.createIntent(this));
        }*/

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //facebookButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        //facebookButton.registerCallback(callbackManager, mCallback);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestProfile().requestIdToken(CLIENT_ID).build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //requestAudioPermission();
        presenter.setView(this);
    }
/*
    @Override
    public void onPermissionsGranted(final int requestCode, final List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(final int requestCode, final List<String> perms) {
        finish();
    }

    @AfterPermissionGranted(AUDIO_PERMISSION_CODE)
    private void requestAudioPermission() {
        final String[] perms = {Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this, perms)) {
        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.audio_permission_request),
                                               AUDIO_PERMISSION_CODE, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }*/

    @OnClick(R.id.google_button)
    public void googleClicked() {
        googleLogin();
    }

    @OnClick(R.id.login_btn)
    public void hgssLoginClicked() {
        goToHGSSLogin();
    }

    @Override
    public void goToHomeScreen() {
        startActivity(TakeOrPickAPhotoActivity.createIntent(this));
    }

    @Override
    public void goToHGSSLogin() {
        startActivity(HGSSLoginActivity.createIntent(this));
    }

    private void googleLogin() {
        String[] accountTypes = new String[]{"com.google", "com.google.android.legacyimap"};
        Intent signInIntent = AccountPicker.newChooseAccountIntent(null, null,
                                                                   accountTypes, false, null, null, null, null);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {

        @Override
        public void onSuccess(final LoginResult loginResult) {

            final AccessToken accessToken = loginResult.getAccessToken();
            Timber.e("access token: " + accessToken.getToken());
            presenter.login(new UserInformation(accessToken.getToken()));
            /*GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), (object, response) -> {
                try {

                } catch (JSONException e) {
                    Timber.e(e);
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email");
            request.setParameters(parameters);
            request.executeAsync();*/

            if (accessToken.getToken() == null) {
                Timber.e("Neuspješna prijava");
            } else {/*
                NetworkService networkService = new NetworkService();
                Call<LoginResponse> call = networkService.getAPI().getApiKey("facebook", accessToken.getToken());
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(final Call<LoginResponse> call, final Response<LoginResponse> response) {
                        if (response.body() == null) {
                            Log.i("TAG", "Greška na serveru");
                        } else {
                            String APIKEY = response.body().getData().getApi_key();
                            API_KEY = APIKEY;
                            ID = response.body().getData().getId();
                            Log.i("TAG", "on response " + APIKEY);
                            startActivity(new Intent(FoodActivity.this, RecyclerViewActivity.class));
                            finish();
                        }
                    }
                    @Override
                    public void onFailure(final Call<LoginResponse> call, final Throwable t) {
                        Log.i("TAG", "on failure " + t.getMessage());
                        Log.i("TAG", "Neuspješno povezivanje sa serverom");
                    }
                });*/
            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {
            Timber.e("Neuspješna prijava");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
        String mEmail;

        if (requestCode == RC_SIGN_IN) {
            if (data != null) {
                if (data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME) == null) {
                    Timber.e("Neuspješna priajva");
                } else {
                    mEmail = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    Timber.e(mEmail);
                    new GetGoogleToken(LoginActivity.this).execute(mEmail);
                }
            }
        }
    }

    private static final class GetGoogleToken extends AsyncTask<String, Void, Boolean> {

        public final LoginActivity loginActivity;

        public GetGoogleToken(final LoginActivity loginFragment) {
            this.loginActivity = loginFragment;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                loginActivity.token = GoogleAuthUtil
                        .getToken(loginActivity, params[0], "oauth2:" + Scopes.PROFILE + " " + Scopes.EMAIL + " " + Scopes.PLUS_LOGIN);
                Timber.e(" google token " + loginActivity.token);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                Timber.e(e.getMessage());
                return false;
            } catch (UserRecoverableAuthException e) {
                loginActivity.startActivityForResult(e.getIntent(), RC_SIGN_IN);
                return false;
            } catch (GoogleAuthException e) {
                e.printStackTrace();
                Timber.e(e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                if (loginActivity.token != null) {
                    loginActivity.presenter.login(new UserInformation(loginActivity.token));

                    /*
                    NetworkService networkService = new NetworkService();
                    Call<LoginResponse> call = networkService.getAPI().getApiKey("google", loginActivity.token);
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(final Call<LoginResponse> call, final Response<LoginResponse> response) {
                            if (response.body() != null) {
                                Log.i("TAG", "google apykey " + response.body().getData().getApi_key());
                                API_KEY =  response.body().getData().getApi_key();
                                ID = response.body().getData().getId();
                                loginActivity.startActivity(new Intent(loginActivity.getApplicationContext(), RecyclerViewActivity.class));
                                loginActivity.finish();
                            } else {
                                Log.i("TAG", "Neuspješno povezivanje s serverom");
                            }
                        }
                        @Override
                        public void onFailure(final Call<LoginResponse> call, final Throwable t) {
                            Log.i("TAG", "Neuspješno povezivanje s serverom");
                        }
                    });*/
                } else {
                    Timber.e("Neuspješna prijava");
                }
            }
        }
    }
}
