package eu.hackathonovo.ui.home;

import org.json.JSONObject;

import eu.hackathonovo.data.api.models.request.LostPerson;
import eu.hackathonovo.data.service.NetworkService;
import eu.hackathonovo.manager.StringManager;
import eu.hackathonovo.ui.base.presenters.BasePresenter;
import io.reactivex.Scheduler;
import timber.log.Timber;

public final class HomePresenterImpl extends BasePresenter implements HomePresenter {

    private final Scheduler subscribeScheduler;
    private final Scheduler observeScheduler;
    private final StringManager stringManager;
    private final NetworkService networkService;

    private HomeView view;

    public HomePresenterImpl(final Scheduler subscribeScheduler, final Scheduler observeScheduler, final StringManager stringManager,
                             final NetworkService networkService) {
        this.subscribeScheduler = subscribeScheduler;
        this.observeScheduler = observeScheduler;
        this.stringManager = stringManager;
        this.networkService = networkService;
    }

    @Override
    public void setView(final HomeView view) {
        this.view = view;
    }

    @Override
    public void lostPerson() {
        addDisposable(networkService.lostPerson(new LostPerson((float)46.20264638061022, (float) 18.08624267578125))
                                    .observeOn(observeScheduler)
                                    .subscribeOn(subscribeScheduler)
                                    .subscribe(this::onLostSuccess, this::onLostFailure));
    }

    private void onLostFailure(final Throwable throwable) {
        Timber.e(throwable);
    }

    private void onLostSuccess(final JSONObject jsonObject) {

    }
}
