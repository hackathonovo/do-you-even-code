package eu.hackathonovo.ui.photo;

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

public class PhotoDetailsActivity extends BaseActivity {

    @BindView(R.id.photoWebView)
    WebView photoWebView;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);

        ButterKnife.bind(this);
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    public static Intent createIntent(final Context context, final String url) {
        return new Intent(context, PhotoDetailsActivity.class).putExtra("url", url);
    }

    @Override
    protected void onResume() {
        super.onResume();

        url = getIntent().getStringExtra("url");
        setWebView();
    }

    private void setWebView() {
        photoWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        photoWebView.getSettings().setUseWideViewPort(true);
        photoWebView.setWebViewClient(new WebViewClient());
        photoWebView.requestFocus();
        photoWebView.loadUrl("https://en.wikipedia.org/wiki/" + url);
    }
}
