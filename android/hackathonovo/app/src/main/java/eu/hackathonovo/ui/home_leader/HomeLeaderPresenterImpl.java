package eu.hackathonovo.ui.home_leader;

import eu.hackathonovo.data.api.models.request.SearchDetailsData;
import eu.hackathonovo.data.api.models.response.ActionResponse;
import eu.hackathonovo.data.service.NetworkService;
import eu.hackathonovo.data.storage.TemplatePreferences;
import eu.hackathonovo.manager.StringManager;
import eu.hackathonovo.ui.base.presenters.BasePresenter;
import io.reactivex.Scheduler;
import timber.log.Timber;

public class HomeLeaderPresenterImpl extends BasePresenter implements HomeLeaderPresenter {

    private HomeLeaderView view;

    public static int ACTION_ID;
    private final Scheduler subscribeScheduler;
    private final Scheduler observeScheduler;
    private final StringManager stringManager;
    private final NetworkService networkService;
    private final TemplatePreferences templatePreferences;

    public HomeLeaderPresenterImpl(final Scheduler subscribeScheduler, final Scheduler observeScheduler, final StringManager stringManager, final NetworkService networkService,
                                   TemplatePreferences templatePreferences) {
        this.subscribeScheduler = subscribeScheduler;
        this.observeScheduler = observeScheduler;
        this.stringManager = stringManager;
        this.networkService = networkService;
        this.templatePreferences = templatePreferences;
    }

    @Override
    public void setView(final HomeLeaderView view) {
        this.view = view;
    }

    @Override
    public void sendDetails(final SearchDetailsData searchDetailsData) {
        addDisposable(networkService.sendDetailsData(searchDetailsData)
                                    .subscribeOn(subscribeScheduler)
                                    .observeOn(observeScheduler)
                                    .subscribe(this::onSendSuccess, this::onSendFailure));
    }

    private void onSendSuccess(final ActionResponse jsonObject) {
        templatePreferences.setActionId(jsonObject.id);
        ACTION_ID = jsonObject.id;
        if (view!= null){
            view.goToEditScreen();
        }
        Timber.e(String.valueOf(jsonObject.id));
    }

    private void onSendFailure(final Throwable throwable) {
        Timber.e(throwable);
    }
}
