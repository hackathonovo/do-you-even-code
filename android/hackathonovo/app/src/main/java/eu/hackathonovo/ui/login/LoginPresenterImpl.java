package eu.hackathonovo.ui.login;

import eu.hackathonovo.data.api.models.request.UserInformation;
import eu.hackathonovo.data.api.models.response.ScanImageResponse;
import eu.hackathonovo.data.service.NetworkService;
import eu.hackathonovo.manager.StringManager;
import eu.hackathonovo.ui.base.presenters.BasePresenter;
import io.reactivex.Scheduler;
import timber.log.Timber;

public class LoginPresenterImpl extends BasePresenter implements LoginPresenter {

    private LoginView view;

    private final Scheduler subscribeScheduler;
    private final Scheduler observeScheduler;
    private final StringManager stringManager;
    private final NetworkService networkService;

    public LoginPresenterImpl(final Scheduler subscribeScheduler, final Scheduler observeScheduler, final StringManager stringManager, final NetworkService networkService) {
        this.subscribeScheduler = subscribeScheduler;
        this.observeScheduler = observeScheduler;
        this.stringManager = stringManager;
        this.networkService = networkService;
    }

    @Override
    public void setView(final LoginView view) {
        this.view = view;
    }

    @Override
    public void login(final UserInformation userInformation) {
        addDisposable(networkService.login(userInformation)
                                    .subscribeOn(subscribeScheduler)
                                    .observeOn(observeScheduler)
                                    .subscribe(this::onLoginSuccess, this::onLoginFailure));
    }

    private void onLoginFailure(final Throwable throwable) {
        Timber.e(throwable);
    }

    private void onLoginSuccess(final UserInformation userInformation) {
        Timber.e(userInformation.token);
        if (view != null) {
            view.goToHomeScreen();
        }
    }


}
