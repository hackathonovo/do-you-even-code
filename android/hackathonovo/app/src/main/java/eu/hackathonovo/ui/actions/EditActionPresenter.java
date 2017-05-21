package eu.hackathonovo.ui.actions;

import eu.hackathonovo.data.api.models.request.SearchDetailsData;

public interface EditActionPresenter {

    void setView(EditActionView view);

    void getAction();

    void updateData(SearchDetailsData searchDetailsData);
}
