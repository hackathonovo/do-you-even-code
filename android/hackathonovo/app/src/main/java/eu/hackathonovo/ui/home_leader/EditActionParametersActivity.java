package eu.hackathonovo.ui.home_leader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import eu.hackathonovo.R;
import eu.hackathonovo.data.api.models.request.SearchDetailsData;
import eu.hackathonovo.ui.home_leader.adapter.ActionParametersAdapter;
import eu.hackathonovo.ui.home_leader.adapter.ChildItem;

public class EditActionParametersActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private ActionParametersAdapter adapter;
    List<SearchDetailsData> searchDetailsDatas= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_action_parameters);
        SearchDetailsData searchDetailsData;
        for (int i=0;i<10;i++) {
            searchDetailsData = new SearchDetailsData();
            searchDetailsData.setVrijemeSastanka("");
            searchDetailsData.setMjestoSastanka("");
            searchDetailsData.setVrijemeNestanka("");
            searchDetailsData.setHitnost("");
            searchDetailsData.setSuicidalnost(true);
            searchDetailsDatas.add(searchDetailsData);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_edit_action_parameters);
        gridLayoutManager = new GridLayoutManager(this,1);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ActionParametersAdapter(gridLayoutManager);

        addRecyclerItems();

    }

    private void addRecyclerItems() {
        for (int i = 0; i<searchDetailsDatas.size();i++){
            adapter.addItem(new ChildItem(searchDetailsDatas.get(i)));
        }
    }
}
