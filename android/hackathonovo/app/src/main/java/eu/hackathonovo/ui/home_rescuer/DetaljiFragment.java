package eu.hackathonovo.ui.home_rescuer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

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

    @BindView(R.id.tv_time) TextView tv_time;
    @BindView(R.id.tv_injury) TextView tv_injury;
    @BindView(R.id.tv_meeting_place) TextView tv_meeting_place;
    @BindView(R.id.tv_emergancy) TextView tv_emergancy;
    @BindView(R.id.tv_suic) TextView tv_suic;
    @BindView(R.id.tv_meeting_place2) TextView tv_meeting_place2;
    @BindView(R.id.tv_time2) TextView tv_time2;

    public static DetaljiFragment newInstance() {
        return new DetaljiFragment();
    }

    @Override
    public void renderView(final SearchDetailsData searchDetailsData) {
        tv_time.setText(searchDetailsData.getVrijemeNestanka());
        tv_injury.setText(searchDetailsData.isOzlijeden());
        tv_meeting_place.setText(searchDetailsData.getLokacija());
        tv_emergancy.setText(searchDetailsData.getHitnost());
        tv_suic.setText(String.valueOf(searchDetailsData.getSuicidalnost()));
        tv_meeting_place2.setText(searchDetailsData.getMjestoSastanka());
        tv_time2.setText(searchDetailsData.getVrijemeSastanka());
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
        presenter.getAction();
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}