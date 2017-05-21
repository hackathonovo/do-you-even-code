package eu.hackathonovo.ui.actions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Switch;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import eu.hackathonovo.R;
import eu.hackathonovo.data.api.models.request.SearchDetailsData;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.activities.BaseActivity;
import eu.hackathonovo.ui.home_leader.adapter.ActionParametersAdapter;

public class EditActionsActivity extends BaseActivity implements EditActionView, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    @Inject
    EditActionPresenter presenter;
    public static String DATE_PICKER_DIALOG_TAG = "date_picker";
    private RecyclerView mRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private ActionParametersAdapter adapter;
    private int whichTime;
    private SearchDetailsData searchDetailsData;
    private String date;
    private String time;
    private boolean sw1, sw2, sw3;

    @BindView(R.id.switch1) Switch aSwitch1;
    @BindView(R.id.switch2) Switch aSwitch2;
    @BindView(R.id.switch3) Switch aSwitch3;

    @BindView(R.id.tv_meeting_place) EditText et1;
    @BindView(R.id.tv_meeting_place2) EditText et2;

    public static Intent createIntent(final Context context) {
        return new Intent(context, EditActionsActivity.class);
    }

    @Override
    public void onDateSet(final DatePickerDialog view, final int year, final int monthOfYear, final int dayOfMonth) {

        date = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth + "T";

        if (whichTime == 1) {
            searchDetailsData.setVrijemeNestanka(date + time);
        } else {
            searchDetailsData.setVrijemeSastanka(date + time);
        }
    }

    @Override
    public void onTimeSet(final TimePickerDialog view, final int hourOfDay, final int minute, final int second) {
        String hour;
        String min;
        String sec;
        if (hourOfDay < 10) {
            hour = String.valueOf(0) + hourOfDay;
        } else {
            hour = String.valueOf(hourOfDay);
        }
        if (minute < 10) {
            min = String.valueOf(0) + minute;
        } else {
            min = String.valueOf(minute);
        }
        if (second < 10) {
            sec = String.valueOf(0) + second;
        } else {
            sec = String.valueOf(second);
        }
        time = hour + ":" + min + ":" + sec + ".0Z";
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {

        activityComponent.inject(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_action_parameters);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.setView(this);
        presenter.getAction();
    }

    @OnClick(R.id.change_time1_btn)
    public void change_time1_btnClicked() {
        setDateTimePicker();
        whichTime = 1;
    }

    @OnClick(R.id.change_time2_btn)
    public void change_time2_btnClicked() {
        setDateTimePicker();
        whichTime = 2;
    }

    @OnCheckedChanged(R.id.switch1)
    public void switch1btnClicked() {
        if (aSwitch1 != null) {
            sw1 = aSwitch1.isChecked();

            searchDetailsData.setOzlijeden(String.valueOf(sw1));
        }
    }

    @OnCheckedChanged(R.id.switch2)
    public void switch2btnClicked() {
        if (aSwitch2 != null) {
            sw2 = aSwitch2.isChecked();
            searchDetailsData.setHitnost(String.valueOf(sw2));
        }
    }

    @OnCheckedChanged(R.id.switch3)
    public void switch3btnClicked() {
        if (aSwitch3 != null) {
            sw3 = aSwitch3.isChecked();

            searchDetailsData.setHitnost(String.valueOf(sw3));
        }
    }

    @OnClick(R.id.change_time3_btn)
    public void change_time3_btnClicked() {
        sendData();
    }

    private void sendData() {
        if (!et1.getText().equals("")) {
            searchDetailsData.setLokacija(et1.getText().toString());
        }
        if (!et2.getText().equals("")) {
            searchDetailsData.setMjestoSastanka(et2.getText().toString());
        }
        searchDetailsData.ozlijeden = String.valueOf(aSwitch1.isChecked());
        searchDetailsData.hitnost = String.valueOf(aSwitch2.isChecked());
        searchDetailsData.suicidalnost = aSwitch3.isChecked();

        presenter.updateData(searchDetailsData);
    }

    @Override
    public void renderView(final SearchDetailsData searchDetailsData) {
        et1.setText(searchDetailsData.getLokacija());
        et2.setText(searchDetailsData.getMjestoSastanka());
        if (searchDetailsData.ozlijeden.equals("true")) {
            aSwitch1.setChecked(true);
        }
        if (searchDetailsData.hitnost.equals("true")) {
            aSwitch2.setChecked(true);
        }
        aSwitch3.setChecked(true);

        this.searchDetailsData = searchDetailsData;
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
        if (whichTime == 1) {
            datePickerDialog.setTitle("Odaberite datum nesreće");
        } else {
            datePickerDialog.setTitle("Odaberite datum sastanka");
        }
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
        if (whichTime == 1) {
            tpd.setTitle("Odaberite vrijeme nesreće");
        } else {
            tpd.setTitle("Odaberite vrijeme sastanka");
        }
        //datePickerDialog.setMinDate(calendar);
    }
}
