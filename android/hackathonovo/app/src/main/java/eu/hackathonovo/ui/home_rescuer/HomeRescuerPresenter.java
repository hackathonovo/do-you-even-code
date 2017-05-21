package eu.hackathonovo.ui.home_rescuer;

import eu.hackathonovo.data.api.models.request.RescuerLocation;

public interface HomeRescuerPresenter {

    void setView(HomeRescuerView view);

    void sendRescuersLocation(RescuerLocation rescuerLocation);

    void setActivityId(String id);
}
