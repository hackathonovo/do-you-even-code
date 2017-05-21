package eu.hackathonovo.ui.home_leader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.hackathonovo.R;
import eu.hackathonovo.data.api.models.request.SearchDetailsData;
import eu.hackathonovo.ui.BundleConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class IsSuicidalFragment extends Fragment {

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Bundle bundle;
    private SearchDetailsData searchDetailsData;
    private boolean isSudicidal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_is_suicidal, container, false);
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        ButterKnife.bind(this, v);
        Bundle bundle = getArguments();
        searchDetailsData = (SearchDetailsData) bundle.getSerializable(BundleConstants.BUNDLE_KEY);
        return v;
    }

    @OnClick(R.id.suicidal_yes_btn)
    public void suicidalYesClicked() {
        isSudicidal=true;
        nextFrag();
    }

    @OnClick(R.id.suicidal_no_btn)
    public void suicidalNoClicked() {
        isSudicidal=false;
        nextFrag();
    }

    private void nextFrag() {
        searchDetailsData.setSuicidalnost(isSudicidal);
        bundle = new Bundle();
        fragmentManager = getActivity().getSupportFragmentManager();
        //FragmentUtils.clearFragmentBackStack(fragmentManager);
        bundle.putSerializable(BundleConstants.BUNDLE_KEY, searchDetailsData);
        fragment = new MeetingPlaceFragment();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                       .replace(R.id.fragment_create_action, fragment).addToBackStack(null)
                       .commit();
    }
}
