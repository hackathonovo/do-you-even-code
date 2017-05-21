package eu.hackathonovo.ui.login;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import eu.hackathonovo.data.api.models.request.HGSSUserInformation;
import eu.hackathonovo.data.api.models.response.UserInformationResponse;
import eu.hackathonovo.data.service.NetworkService;
import eu.hackathonovo.data.storage.TemplatePreferences;
import eu.hackathonovo.manager.StringManager;
import eu.hackathonovo.ui.base.presenters.BasePresenter;
import io.reactivex.Scheduler;
import timber.log.Timber;

public class HGSSLoginPresenterImpl extends BasePresenter implements HGSSLoginPresenter {

    private HGSSLoginView view;

    private final Scheduler subscribeScheduler;
    private final Scheduler observeScheduler;
    private final StringManager stringManager;
    private final NetworkService networkService;
    private final TemplatePreferences templatePreferences;

    public HGSSLoginPresenterImpl(final Scheduler subscribeScheduler, final Scheduler observeScheduler, final StringManager stringManager, final NetworkService networkService,
                                  TemplatePreferences templatePreferences) {
        this.subscribeScheduler = subscribeScheduler;
        this.observeScheduler = observeScheduler;
        this.stringManager = stringManager;
        this.networkService = networkService;
        this.templatePreferences = templatePreferences;
    }

    @Override
    public void setView(final HGSSLoginView view) {
        this.view = view;
    }

    @Override
    public void loginHGSS(final HGSSUserInformation userInformation) {
        addDisposable(networkService.loginHGSS(userInformation)
                                    .subscribeOn(subscribeScheduler)
                                    .observeOn(observeScheduler)
                                    .subscribe(this::onLoginHGSSSuccess, this::onLoginHGSSFailure));
    }

    private void onLoginHGSSFailure(final Throwable throwable) {
        Timber.e(throwable);
    }

    private void onLoginHGSSSuccess(final UserInformationResponse userInformation) {
        templatePreferences.setUserId(userInformation.id);

        addDisposable(networkService.sendToken((int) userInformation.id, FirebaseInstanceId.getInstance().getToken())
                                    .subscribeOn(subscribeScheduler)
                                    .observeOn(observeScheduler)
                                    .subscribe(this::onFirebaseSuccess, this::onFirebaseFailure));


        if (view != null) {
            view.goToSaverHome();
        }
    }

    private void onFirebaseSuccess(final JSONObject jsonObject) {

    }

    private void onFirebaseFailure(final Throwable throwable) {
        Timber.e(throwable);
    }
}
