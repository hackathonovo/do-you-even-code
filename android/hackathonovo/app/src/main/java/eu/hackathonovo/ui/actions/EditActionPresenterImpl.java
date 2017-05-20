package eu.hackathonovo.ui.actions;

import java.util.List;

import eu.hackathonovo.data.api.models.request.SearchDetailsData;
import eu.hackathonovo.data.service.NetworkService;
import eu.hackathonovo.data.storage.TemplatePreferences;
import eu.hackathonovo.manager.StringManager;
import eu.hackathonovo.ui.base.presenters.BasePresenter;
import io.reactivex.Scheduler;
import timber.log.Timber;

public class EditActionPresenterImpl extends BasePresenter implements EditActionPresenter {

    private EditActionView view;

    private final Scheduler subscribeScheduler;
    private final Scheduler observeScheduler;
    private final StringManager stringManager;
    private final NetworkService networkService;
    private final TemplatePreferences templatePreferences;

    public EditActionPresenterImpl(final Scheduler subscribeScheduler, final Scheduler observeScheduler, final StringManager stringManager, final NetworkService networkService,
                                   final TemplatePreferences templatePreferences) {
        this.subscribeScheduler = subscribeScheduler;
        this.observeScheduler = observeScheduler;
        this.stringManager = stringManager;
        this.networkService = networkService;
        this.templatePreferences = templatePreferences;
    }

    @Override
    public void setView(final EditActionView view) {
        this.view = view;
    }

    @Override
    public void getAction() {
        addDisposable(networkService.getActions(templatePreferences.getActionId())
                                    .subscribeOn(subscribeScheduler)
                                    .observeOn(observeScheduler)
                                    .subscribe(this::onGetActionSuccess, this::onGetActionFailure));
    }

    private void onGetActionFailure(final Throwable throwable) {
        Timber.e(throwable);
    }

    private void onGetActionSuccess(final SearchDetailsData searchDetailsDatas) {
        if (view != null){
            view.renderView(searchDetailsDatas);
        }
    }
}
