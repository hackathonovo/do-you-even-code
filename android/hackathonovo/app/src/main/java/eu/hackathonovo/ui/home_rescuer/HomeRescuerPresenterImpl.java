package eu.hackathonovo.ui.home_rescuer;

import org.json.JSONObject;

import eu.hackathonovo.data.api.models.request.RescuerLocation;
import eu.hackathonovo.data.service.NetworkService;
import eu.hackathonovo.data.storage.TemplatePreferences;
import eu.hackathonovo.manager.StringManager;
import eu.hackathonovo.ui.base.presenters.BasePresenter;
import io.reactivex.Scheduler;
import timber.log.Timber;

public class HomeRescuerPresenterImpl extends BasePresenter implements HomeRescuerPresenter {

    private HomeRescuerView view;

    private final Scheduler subscribeScheduler;
    private final Scheduler observeScheduler;
    private final StringManager stringManager;
    private final NetworkService networkService;
    private final TemplatePreferences templatePreferences;

    public HomeRescuerPresenterImpl(final Scheduler subscribeScheduler, final Scheduler observeScheduler, final StringManager stringManager, final NetworkService networkService,
                                    final TemplatePreferences templatePreferences) {
        this.subscribeScheduler = subscribeScheduler;
        this.observeScheduler = observeScheduler;
        this.stringManager = stringManager;
        this.networkService = networkService;
        this.templatePreferences = templatePreferences;
    }

    @Override
    public void setView(final HomeRescuerView view) {
        this.view = view;
    }

    @Override
    public void sendRescuersLocation(final RescuerLocation rescuerLocation) {
        addDisposable(networkService.sendRescuerLocation(new RescuerLocation(rescuerLocation.latitude, rescuerLocation.longitude, templatePreferences.getUserId()))
                                    .subscribeOn(subscribeScheduler)
                                    .observeOn(observeScheduler)
                                    .subscribe(this::onSendRescuerLocationSuccess, this::onSendRescuerLocationFailure));
    }

    private void onSendRescuerLocationSuccess(final JSONObject jsonObject) {

    }

    private void onSendRescuerLocationFailure(final Throwable throwable) {
        Timber.e(throwable);
    }
}
