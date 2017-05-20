package eu.hackathonovo.ui.filter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.hackathonovo.R;
import eu.hackathonovo.data.api.models.response.FilterUsers;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.fragments.BaseFragment;

import static eu.hackathonovo.ui.home_leader.HomeLeaderPresenterImpl.ACTION_ID;

public class MapFragment extends BaseFragment implements FilterView {

    @Inject
    FilterPresenter presenter;

    @BindView(R.id.WebView)
    WebView webView;

    private LinearLayoutManager linearLayoutManager;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.map_fragment, container, false);

        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.setView(this);
        setWebView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void renderView(final List<FilterUsers> filterUserses) {

    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    private void setWebView() {
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.requestFocus();
        webView.loadUrl("http://46.101.148.24/webviews/map/areas-edit/"+ ACTION_ID);
    }

    @Override
    public void renderView(final List<FilterUsers> filterUserses) {

    }
}
