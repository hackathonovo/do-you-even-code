package eu.hackathonovo.ui.login;

import eu.hackathonovo.data.api.models.request.HGSSUserInformation;

public interface HGSSLoginPresenter {

    void setView(HGSSLoginView view);

    void loginHGSS(HGSSUserInformation userInformation);
}
