package eu.hackathonovo.ui.filter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.hackathonovo.R;
import eu.hackathonovo.data.api.models.response.FilterUsers;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.activities.BaseActivity;

public class FilterActivity extends BaseActivity implements FilterView {

    @Inject
    FilterPresenter presenter;

    @BindView(R.id.home_activity_tabs)
    protected TabLayout tabs;

    @BindView(R.id.home_activity_viewpager)
    protected ViewPager viewPager;

    FilterViewAdapter filterViewAdapter;

    public static Intent createIntent(final Context context) {
        return new Intent(context, FilterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        filterViewAdapter = new FilterViewAdapter(getSupportFragmentManager());

        ButterKnife.bind(this);
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupViewPager(final ViewPager viewPager) {
        filterViewAdapter.addFragment(MapFragment.newInstance(), "karta");
        filterViewAdapter.addFragment(FilterFragment.newInstance(), "filter");
        viewPager.setAdapter(filterViewAdapter);
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

    @Override
    public void renderView(final List<FilterUsers> filterUserses) {

    }
}
