package eu.hackathonovo.ui.home_rescuer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.hackathonovo.R;
import eu.hackathonovo.data.api.models.request.SearchDetailsData;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.actions.EditActionPresenter;
import eu.hackathonovo.ui.actions.EditActionView;
import eu.hackathonovo.ui.base.fragments.BaseFragment;

public class DetaljiFragment extends BaseFragment implements EditActionView {

    @Inject
    EditActionPresenter presenter;

    @BindView(R.id.switch1) Switch aSwitch1;
    @BindView(R.id.switch2) Switch aSwitch2;
    @BindView(R.id.switch3) Switch aSwitch3;

    @BindView(R.id.tv_meeting_place) EditText et1;
    @BindView(R.id.tv_meeting_place2) EditText et2;

    public static DetaljiFragment newInstance() {
        return new DetaljiFragment();
    }

    @Override
    public void renderView(final SearchDetailsData searchDetailsData) {
        et1.setText(searchDetailsData.getLokacija());
        et2.setText(searchDetailsData.getMjestoSastanka());
        if (searchDetailsData.ozlijeden.equals("true")) {
            aSwitch1.setChecked(true);
        }
        if (searchDetailsData.hitnost.equals("true")) {
            aSwitch2.setChecked(true);
        }
        aSwitch3.setChecked(true);
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_edit_action_parameters2, container, false);

        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.setView(this);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}