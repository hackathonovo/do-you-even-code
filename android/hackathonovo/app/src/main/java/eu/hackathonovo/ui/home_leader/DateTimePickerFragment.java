package eu.hackathonovo.ui.home_leader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import eu.hackathonovo.R;
import eu.hackathonovo.data.api.models.request.SearchDetailsData;
import eu.hackathonovo.ui.BundleConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateTimePickerFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_date_time_picker, container, false);
        searchDetailsData = new SearchDetailsData();
        setDateTimePicker();
        return v;
    }

    @Override
    public void onDateSet(final DatePickerDialog view, final int year, final int monthOfYear, final int dayOfMonth) {
        date = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth + "T";
        searchDetailsData.setVrijemeNestanka(date + time);
        callAnotherFrag();
    }

    private void callAnotherFrag() {
        bundle = new Bundle();
        fragmentManager = getActivity().getSupportFragmentManager();
        //FragmentUtils.clearFragmentBackStack(fragmentManager);
        bundle.putSerializable(BundleConstants.BUNDLE_KEY, searchDetailsData);
        fragment = new InjuryDescriptionFragment();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                       .replace(R.id.fragment_create_action, fragment).addToBackStack(null)
                       .commit();
    }

    @Override
    public void onTimeSet(final TimePickerDialog view, final int hourOfDay, final int minute, final int second) {
        String hour;
        String min;
        String sec ;
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
        time = hour + ":" + min + ":" + sec+ ".0Z";
    }

    private void setDateTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                DateTimePickerFragment.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(getActivity().getFragmentManager(), DATE_PICKER_DIALOG_TAG);
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
        datePickerDialog.setTitle("Odaberite datum nesreće");
        datePickerDialog.setMinDate(calendar);
        //datePickerDialog.setOnCancelListener(this);

        TimePickerDialog tpd = TimePickerDialog.newInstance(
                DateTimePickerFragment.this,
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                true
        );
        tpd.show(getActivity().getFragmentManager(), DATE_PICKER_DIALOG_TAG);
        tpd.setVersion(TimePickerDialog.Version.VERSION_2);
        tpd.setTitle("Odaberite vrijeme nesreće");
        //tpd.setMinDate(calendar);
    }
}
