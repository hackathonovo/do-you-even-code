package eu.hackathonovo.ui.filter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.hackathonovo.R;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.fragments.BaseFragment;
import timber.log.Timber;

import static eu.hackathonovo.ui.home_leader.HomeLeaderPresenterImpl.ACTION_ID;

public class UsersMapFragment extends BaseFragment {

    public static UsersMapFragment newInstance() {
        return new UsersMapFragment();
    }

    @BindView(R.id.WebView)
    WebView webView;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_map, container, false);

        ButterKnife.bind(this, v);
        return v;
    }
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        //new WebChromeLoader("http://46.101.148.24/webviews/map/view", this);
    }*/

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
