package eu.hackathonovo.ui.home_leader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.hackathonovo.R;
import eu.hackathonovo.data.api.models.request.SearchDetailsData;
import eu.hackathonovo.ui.BundleConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeetingPlaceFragment extends Fragment {
    @BindView(R.id.tv_meeting_place)
    TextView tv_meeting_place;

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Bundle bundle;
    private SearchDetailsData searchDetailsData;
    private String meetingPlace;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meeting_place, container, false);
        ButterKnife.bind(this, v);
        Bundle bundle = getArguments();
        searchDetailsData = (SearchDetailsData) bundle.getSerializable(BundleConstants.BUNDLE_KEY);
        return v;
    }

    @OnClick(R.id.meeting_place_btn)
    public void injuryPlaceClicked() {
        meetingPlace= tv_meeting_place.getText().toString();
        nextFrag();
    }

    private void nextFrag() {
        searchDetailsData.setMjestoSastanka(meetingPlace);
        bundle = new Bundle();
        fragmentManager = getActivity().getSupportFragmentManager();
        //FragmentUtils.clearFragmentBackStack(fragmentManager);
        bundle.putSerializable(BundleConstants.BUNDLE_KEY, searchDetailsData);
        fragment = new MeetingTimeFragment();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                       .replace(R.id.fragment_create_action, fragment).addToBackStack(null)
                       .commit();
    }
}
