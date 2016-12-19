package th.co.omc.memberdemo.fragment.signup;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bit.szw.widget.StepView;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.activity.MainActivity;
import th.co.omc.memberdemo.activity.signup.SignUpActivity;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.utils.AnimateButton;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemrsAndConditionsFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.stepview2) StepView stepView;
    @Bind(R.id.checkbox_layout) LinearLayout checkboxLayout;
    @Bind(R.id.checkbox_terms) CheckBox checkBox;
    @Bind(R.id.textviewError) CustomTextview textviewerror;
    @Bind(R.id.button_decline) CustomTextview buttonDecline;
    @Bind(R.id.button_accept) CustomTextview buttonAccept;
    @Bind(R.id.checkbox_string) CustomTextview checkboxString;
    public TemrsAndConditionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register1, container, false);
        ButterKnife.bind(this, view);
        stepView();
        initWidget();
        return view;
    }

    private void stepView() {
        List<String> lables = new ArrayList<>();
        lables.add("");
        lables.add("");
        lables.add("");
        stepView.setStepText(lables);
        stepView.setVerticalSpace(10);
        stepView.setDrawableSize(55);
        stepView.setCurrentStep(1);
        stepView.setLineHeight(8);

        stepView.setCurrentDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.current_step));
        stepView.setDrawable(getResources().getDrawable(R.drawable.current_step));

        stepView.setLineColor(getResources().getColor(R.color.colorAccent));
        stepView.setReachedLineColor(getResources().getColor(R.color.colorAccent));
        stepView.setReachedDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.step_completed));

        stepView.setUnreachedLineColor(getResources().getColor(R.color.colorPrimary));
        stepView.setUnreachedDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.step_uncomplete_black));
    }

    private void initWidget() {
        SignUpActivity signUpActivity = (SignUpActivity) getActivity();
        signUpActivity.setToolbarTitle(R.string.signup_first_page);
        buttonDecline.setOnClickListener(this);
        buttonAccept.setOnClickListener(this);
        checkboxString.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (MyApplication.getInstance().getPrefManager().getAcceptTerms()) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_decline :
                buttonDecline.startAnimation(new AnimateButton().animbutton());
                decline();
                break;
            case R.id.button_accept :
                buttonAccept.startAnimation(new AnimateButton().animbutton());
                isChecked();
                break;
            case R.id.checkbox_string :
                checkbox();
                break;
            default: break;
        }
    }

    private void checkbox() {
        if (checkBox.isChecked()) {
            checkBox.setChecked(false);
        } else {
            checkBox.setChecked(true);
        }
    }

    private void isChecked() {
        if (checkBox.isChecked()) {
            nextStep();
            MyApplication.getInstance().getPrefManager().storeAcceptTerms(true);
            textviewerror.setError(null);
        } else {
            textviewerror.setError("Please check accept");
        }
    }

    private void decline() {
        checkBox.setChecked(false);
        MyApplication.getInstance().getPrefManager().storeAcceptTerms(false);
        getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

    private void nextStep() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.register_container, new InputFragment(), "InputFragment").addToBackStack(null).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
