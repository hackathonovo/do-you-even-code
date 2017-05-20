package eu.hackathonovo.ui.filter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.hackathonovo.R;
import eu.hackathonovo.data.api.models.response.FilterUsers;
import eu.hackathonovo.injection.component.ActivityComponent;
import eu.hackathonovo.ui.base.fragments.BaseFragment;

public class FilterFragment extends BaseFragment implements FilterView {

    @BindView(R.id.et_name_surname)
    TextView et_name_surname;

    @BindView(R.id.et_radius)
    TextView et_radius;

    @BindView(R.id.checkbox1)
    CheckBox checkBox1;

    @BindView(R.id.checkbox2)
    CheckBox checkBox2;

    @Inject
    FilterPresenter presenter;

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.filter_fragment, container, false);

        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.setView(this);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       /* checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (checkBox2.isChecked() && isChecked){
                presenter.filterData("", et_name_surname.getText().toString());
            }else if(checkBox2.isChecked() && !isChecked){
                presenter.filterData("VODITELJ", et_name_surname.getText().toString());
            }
            presenter.filterData("pripravnik", );
        });

        checkBox2.setOnCheckedChangeListener((buttonView, isChecked) -> {

        });*/

        et_name_surname.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (!et_radius.getText().toString().matches("")) {
                    presenter.filterData(et_name_surname.getText().toString(), Integer.valueOf(et_radius.getText().toString()));
                }
            }
        });

        et_radius.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (!et_radius.getText().toString().matches("")) {
                    presenter.filterData(et_name_surname.getText().toString(), Integer.valueOf(et_radius.getText().toString()));
                }
            }
        });
        presenter.setView(this);
    }

    @Override
    public void renderView(final List<FilterUsers> filterUserses) {

    }

    private void addType() {}
}
