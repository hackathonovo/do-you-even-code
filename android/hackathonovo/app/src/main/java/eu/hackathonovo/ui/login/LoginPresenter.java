package eu.hackathonovo.ui.login;

import eu.hackathonovo.data.api.models.request.UserInformation;

public interface LoginPresenter {

    void setView(LoginView view);

    void login(UserInformation userInformation);

}
