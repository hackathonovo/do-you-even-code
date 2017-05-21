package eu.hackathonovo.ui.filter;

import org.json.JSONObject;

import java.util.List;

import eu.hackathonovo.data.api.models.request.AddUsers;
import eu.hackathonovo.data.api.models.response.FilterUsers;
import eu.hackathonovo.data.service.NetworkService;
import eu.hackathonovo.data.storage.TemplatePreferences;
import eu.hackathonovo.manager.StringManager;
import eu.hackathonovo.ui.base.presenters.BasePresenter;
import io.reactivex.Scheduler;
import timber.log.Timber;

public class FilterPresenterImpl extends BasePresenter implements FilterPresenter {

    private FilterView view;

    private final Scheduler subscribeScheduler;
    private final Scheduler observeScheduler;
    private final StringManager stringManager;
    private final NetworkService networkService;
    private final TemplatePreferences templatePreferences;

    public FilterPresenterImpl(final Scheduler subscribeScheduler, final Scheduler observeScheduler, final StringManager stringManager, final NetworkService networkService,
                               final TemplatePreferences templatePreferences) {
        this.subscribeScheduler = subscribeScheduler;
        this.observeScheduler = observeScheduler;
        this.stringManager = stringManager;
        this.networkService = networkService;
        this.templatePreferences = templatePreferences;
    }

    @Override
    public void setView(final FilterView view) {
        this.view = view;
    }

    @Override
    public void filterData(final String name, final int buffer) {
        addDisposable(networkService.filterUsers(name, templatePreferences.getActionId(), buffer)
                                    .subscribeOn(subscribeScheduler)
                                    .observeOn(observeScheduler)
                                    .subscribe(this::onGetUsersSuccess, this::onGetUsersFailure));
    }

    private void onGetUsersSuccess(final List<FilterUsers> filterUserses) {
        if (view != null) {
            view.renderView(filterUserses);
        }
    }

    private void onGetUsersFailure(final Throwable throwable) {
        Timber.e(throwable);
    }

    @Override
    public void updateUser(int id) {
        addDisposable(networkService.updateUser(id, new AddUsers(templatePreferences.getActionId()))
                                    .subscribeOn(subscribeScheduler)
                                    .observeOn(observeScheduler)
                                    .subscribe(this::onUpdateUsersSuccess, this::onUpdateUsersFailure));
    }

    private void onUpdateUsersSuccess(final JSONObject jsonObject) {

    }

    private void onUpdateUsersFailure(final Throwable throwable) {
        Timber.e(throwable);
    }
}
