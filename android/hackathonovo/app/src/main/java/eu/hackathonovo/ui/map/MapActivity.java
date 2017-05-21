package eu.hackathonovo.ui.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.hackathonovo.R;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.activities.BaseActivity;
import timber.log.Timber;

import static eu.hackathonovo.ui.home_leader.HomeLeaderPresenterImpl.ACTION_ID;

public class MapActivity extends BaseActivity {

    public static Intent createIntent(final Context context) {
        return new Intent(context, MapActivity.class);
    }

    @BindView(R.id.WebView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        //new WebChromeLoader("http://46.101.148.24/webviews/map/view", this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setWebView();
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    private void setWebView() {
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.requestFocus();
        Timber.e(String.valueOf(ACTION_ID));
        webView.loadUrl("http://46.101.148.24/webviews/map/users-edit/" + ACTION_ID);
    }
}
