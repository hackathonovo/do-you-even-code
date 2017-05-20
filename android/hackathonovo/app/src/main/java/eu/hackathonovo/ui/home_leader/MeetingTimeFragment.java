package eu.hackathonovo.ui.home_leader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import butterknife.ButterKnife;
import eu.hackathonovo.R;
import eu.hackathonovo.data.api.models.request.SearchDetailsData;
import eu.hackathonovo.ui.BundleConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeetingTimeFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{
    public static String DATE_PICKER_DIALOG_TAG = "date_picker";
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Bundle bundle;
    private SearchDetailsData searchDetailsData;
    private String date;
    private String time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meeting_time, container, false);
        ButterKnife.bind(this, v);
        Bundle bundle = getArguments();
        searchDetailsData = (SearchDetailsData) bundle.getSerializable(BundleConstants.BUNDLE_KEY);
        setDateTimePicker();
        return v;
    }

    private void setDateTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                MeetingTimeFragment.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(getActivity().getFragmentManager(), DATE_PICKER_DIALOG_TAG);
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
        datePickerDialog.setMinDate(calendar);
        //datePickerDialog.setOnCancelListener(this);

        TimePickerDialog tpd = TimePickerDialog.newInstance(
                MeetingTimeFragment.this,
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                true
        );
        tpd.show(getActivity().getFragmentManager(), DATE_PICKER_DIALOG_TAG);
        tpd.setVersion(TimePickerDialog.Version.VERSION_2);
        datePickerDialog.setMinDate(calendar);
    }

    @Override
    public void onDateSet(final DatePickerDialog view, final int year, final int monthOfYear, final int dayOfMonth) {
        date = year+"-0"+monthOfYear+1+"-"+dayOfMonth;
        searchDetailsData.setVrijemeSastanka(date+time);
        sendData();
    }

    private void sendData() {
        Log.d("podaci",searchDetailsData.getLokacija());
        Log.d("","");
    }

    @Override
    public void onTimeSet(final TimePickerDialog view, final int hourOfDay, final int minute, final int second) {
        time = hourOfDay+":"+minute+":"+second;
    }

}
