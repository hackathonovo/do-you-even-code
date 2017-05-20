package eu.hackathonovo.ui.filter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.hackathonovo.R;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.fragments.BaseFragment;

public class FilterFragment extends BaseFragment implements FilterView {
    @BindView(R.id.et_name_surname)
    TextView et_name_surname;

    @BindView(R.id.et_radius)
    TextView et_radius;


    @Inject
    FilterPresenter presenter;

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.filter_fragment, container, false);

        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.setView(this);
    }

    @OnClick(R.id.pripravnik_btn)
    public void pripravnikClicked() {
        addType();
    }



    @OnClick(R.id.spasavatelj_btn)
    public void spasavateljClicked() {
        addType();
    }

    private void addType() {}
}
