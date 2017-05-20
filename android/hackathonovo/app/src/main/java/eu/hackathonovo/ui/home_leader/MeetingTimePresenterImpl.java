package eu.hackathonovo.ui.home_leader;

import eu.hackathonovo.ui.base.presenters.BasePresenter;

public class MeetingTimePresenterImpl extends BasePresenter implements MeetingTimePresenter {

    private HomeLeaderView view;

    @Override
    public void setView(final HomeLeaderView view) {
        this.view = view;
    }

    @Override
    public void sendDetailsData() {


    }
}
