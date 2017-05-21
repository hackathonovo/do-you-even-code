package eu.hackathonovo.ui.base.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import eu.hackathonovo.application.HackathonovoApplication;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.activities.BaseActivity;

public abstract class BaseFragment extends Fragment {

    private ActivityComponent activityComponent;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final HackathonovoApplication hackathonovoApplication = (HackathonovoApplication) getActivity().getApplication();

        inject(((BaseActivity) getActivity()).getActivityComponent(hackathonovoApplication));
    }

    protected abstract void inject(ActivityComponent activityComponent);
}
