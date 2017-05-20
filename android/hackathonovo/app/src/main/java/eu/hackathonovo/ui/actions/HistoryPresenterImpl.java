package eu.hackathonovo.ui.actions;

import eu.hackathonovo.data.service.NetworkService;
import eu.hackathonovo.data.storage.TemplatePreferences;
import eu.hackathonovo.manager.StringManager;
import eu.hackathonovo.ui.base.presenters.BasePresenter;
import io.reactivex.Scheduler;

public class HistoryPresenterImpl extends BasePresenter implements HistoryPresenter {

    private HistoryView view;

    private final Scheduler subscribeScheduler;
    private final Scheduler observeScheduler;
    private final StringManager stringManager;
    private final NetworkService networkService;
    private final TemplatePreferences templatePreferences;

    public HistoryPresenterImpl(final Scheduler subscribeScheduler, final Scheduler observeScheduler, final StringManager stringManager, final NetworkService networkService,
                                final TemplatePreferences templatePreferences) {
        this.subscribeScheduler = subscribeScheduler;
        this.observeScheduler = observeScheduler;
        this.stringManager = stringManager;
        this.networkService = networkService;
        this.templatePreferences = templatePreferences;
    }

    @Override
    public void setView(final HistoryView view) {
        this.view = view;
    }
}
