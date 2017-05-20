package eu.hackathonovo.ui.home_leader;

import org.json.JSONObject;

import eu.hackathonovo.data.api.models.request.SearchDetailsData;
import eu.hackathonovo.data.service.NetworkService;
import eu.hackathonovo.manager.StringManager;
import eu.hackathonovo.ui.base.presenters.BasePresenter;
import io.reactivex.Scheduler;
import timber.log.Timber;

public class HomeLeaderPresenterImpl extends BasePresenter implements HomeLeaderPresenter {

    private HomeLeaderView view;

    private final Scheduler subscribeScheduler;
    private final Scheduler observeScheduler;
    private final StringManager stringManager;
    private final NetworkService networkService;

    public HomeLeaderPresenterImpl(final Scheduler subscribeScheduler, final Scheduler observeScheduler, final StringManager stringManager, final NetworkService networkService) {
        this.subscribeScheduler = subscribeScheduler;
        this.observeScheduler = observeScheduler;
        this.stringManager = stringManager;
        this.networkService = networkService;
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

    private void onSendSuccess(final JSONObject jsonObject) {

    }

    private void onSendFailure(final Throwable throwable) {
        Timber.e(throwable);
    }
}
