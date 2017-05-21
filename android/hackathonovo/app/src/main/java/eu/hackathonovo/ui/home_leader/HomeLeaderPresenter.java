package eu.hackathonovo.ui.home_leader;

import eu.hackathonovo.data.api.models.request.SearchDetailsData;

public interface HomeLeaderPresenter {

    void setView(HomeLeaderView view);

    void sendDetails(SearchDetailsData searchDetailsData);
}
