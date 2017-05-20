package eu.hackathonovo.ui.actions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import eu.hackathonovo.R;
import eu.hackathonovo.data.api.models.request.SearchDetailsData;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.activities.BaseActivity;
import eu.hackathonovo.ui.home_leader.adapter.ActionParametersAdapter;
import eu.hackathonovo.ui.home_leader.adapter.ChildItem;

public class EditActionsActivity extends BaseActivity implements EditActionView{

    @Inject
    EditActionPresenter presenter;

    private RecyclerView mRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private ActionParametersAdapter adapter;
    List<SearchDetailsData> searchDetailsDatas = new ArrayList<>();

    public static Intent createIntent(final Context context) {
        return new Intent(context, EditActionsActivity.class);
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {

        activityComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_action_parameters);
        SearchDetailsData searchDetailsData;
        for (int i = 0; i < 10; i++) {
            searchDetailsData = new SearchDetailsData();
            searchDetailsData.setVrijemeSastanka("dsadas");
            searchDetailsData.setMjestoSastanka("fsdf");
            searchDetailsData.setVrijemeNestanka("dfssfd");
            searchDetailsData.setHitnost("dsfsdf");
            searchDetailsData.setSuicidalnost(true);
            searchDetailsDatas.add(searchDetailsData);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_edit_action_parameters);
        gridLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ActionParametersAdapter(gridLayoutManager);
        mRecyclerView.setAdapter(adapter);
        addRecyclerItems();
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.setView(this);
        presenter.getAction();
    }

    private void addRecyclerItems() {
        for (int i = 0; i < searchDetailsDatas.size(); i++) {
            adapter.addItem(new ChildItem(searchDetailsDatas.get(i)));
        }
    }

    @Override
    public void renderView(final SearchDetailsData searchDetailsData) {

    }
}
