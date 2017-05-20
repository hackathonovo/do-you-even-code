package eu.hackathonovo.ui.home_leader;

import android.content.Context;
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
public class SearchPriorityFragment extends Fragment {

    private boolean isPriority;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Bundle bundle;
    private SearchDetailsData searchDetailsData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search_priority, container, false);

        ButterKnife.bind(this, v);
        Bundle bundle = getArguments();
        searchDetailsData = (SearchDetailsData) bundle.getSerializable(BundleConstants.BUNDLE_KEY);

        return v;
    }

    @OnClick(R.id.priority_yes_btn)
    public void suicidalYesClicked() {
        isPriority = true;
        nextFrag();
    }

    @OnClick(R.id.priority_no_btn)
    public void suicidalNoClicked() {
        isPriority = false;
        nextFrag();
    }

    private void nextFrag() {
        searchDetailsData.setHitnost(String.valueOf(isPriority));
        bundle = new Bundle();
        fragmentManager = getActivity().getSupportFragmentManager();
        //FragmentUtils.clearFragmentBackStack(fragmentManager);
        bundle.putSerializable(BundleConstants.BUNDLE_KEY, searchDetailsData);
        fragment = new IsSuicidalFragment();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                       .replace(R.id.fragment_create_action, fragment).addToBackStack(null)
                       .commit();
    }
}
