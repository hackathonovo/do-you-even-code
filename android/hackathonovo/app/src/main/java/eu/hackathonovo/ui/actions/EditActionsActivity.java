package eu.hackathonovo.ui.actions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import eu.hackathonovo.R;
import eu.hackathonovo.data.api.models.request.SearchDetailsData;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.activities.BaseActivity;
import eu.hackathonovo.ui.home_leader.DateTimePickerFragment;
import eu.hackathonovo.ui.home_leader.adapter.ActionParametersAdapter;
import eu.hackathonovo.ui.home_leader.adapter.ChildItem;

public class EditActionsActivity extends BaseActivity implements EditActionView,TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    @Inject
    EditActionPresenter presenter;
    public static String DATE_PICKER_DIALOG_TAG = "date_picker";
    private RecyclerView mRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private ActionParametersAdapter adapter;
    List<SearchDetailsData> searchDetailsDatas = new ArrayList<>();

    public static Intent createIntent(final Context context) {
        return new Intent(context, EditActionsActivity.class);
    }

    @Override
    public void onDateSet(final DatePickerDialog view, final int year, final int monthOfYear, final int dayOfMonth) {

    }

    @Override
    public void onTimeSet(final TimePickerDialog view, final int hourOfDay, final int minute, final int second) {

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

    private void setDateTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                EditActionsActivity.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(getFragmentManager(), DATE_PICKER_DIALOG_TAG);
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
        datePickerDialog.setTitle("Odaberite datum nesreće");
        //datePickerDialog.set(calendar);
        //datePickerDialog.setOnCancelListener(this);

        TimePickerDialog tpd = TimePickerDialog.newInstance(
                EditActionsActivity.this,
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                true
        );
        tpd.show(getFragmentManager(), DATE_PICKER_DIALOG_TAG);
        tpd.setVersion(TimePickerDialog.Version.VERSION_2);
        datePickerDialog.setTitle("Odaberite vrijeme nesreće");
        //datePickerDialog.setMinDate(calendar);
    }
}
