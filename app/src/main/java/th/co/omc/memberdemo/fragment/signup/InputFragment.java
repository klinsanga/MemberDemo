package th.co.omc.memberdemo.fragment.signup;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bit.szw.widget.StepView;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.activity.signup.ApplyActivity;
import th.co.omc.memberdemo.activity.signup.SignUpActivity;
import th.co.omc.memberdemo.adapter.shopping.SpinnerCustomAdapter;
import th.co.omc.memberdemo.adapter.signup.SpinnerDateAdapter;
import th.co.omc.memberdemo.customview.CustomEdittext;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.shopping.DistrictModel;
import th.co.omc.memberdemo.model.shopping.Model;
import th.co.omc.memberdemo.model.shopping.ProvinceModel;
import th.co.omc.memberdemo.model.shopping.SubDistrictModel;
import th.co.omc.memberdemo.model.signup.SignupDataModel;
import th.co.omc.memberdemo.parse.ParseDistrict;
import th.co.omc.memberdemo.parse.ParseNationality;
import th.co.omc.memberdemo.parse.ParseProvince;
import th.co.omc.memberdemo.parse.ParseSPandUP;
import th.co.omc.memberdemo.parse.ParseSubDistrict;
import th.co.omc.memberdemo.utils.ActivityResultBus;
import th.co.omc.memberdemo.utils.ActivityResultEvent;
import th.co.omc.memberdemo.utils.AnimateButton;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.setCalendarToMultipleLanguage;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_APPLY = 5555;

    EditText[] fields;
    Spinner[] spinners;
    TextView[] textViews;
    RadioButton[] radioButtons;

    SignupDataModel signModel;
    ParseSPandUP parseSPandUP;
    ParseProvince parseProvince;
    ParseDistrict parseDistrict;
    ParseSubDistrict parseSubDistrict;
    ParseNationality parseNationality;
    SpinnerDateAdapter spinnerDateAdapter;
    SpinnerCustomAdapter spinnerCustomAdapter;

    List<Model> nationalityModel = new ArrayList<Model>();
    List<DistrictModel> districtModelList = new ArrayList<DistrictModel>();
    List<ProvinceModel> provinceModelList = new ArrayList<ProvinceModel>();
    List<SubDistrictModel> subDistrictModelList = new ArrayList<SubDistrictModel>();
    final List<SignupDataModel> signupDataModelList = new ArrayList<SignupDataModel>();

    private String bdate;
    private String d, m, y;
    private String nationality;
    private String sideid, sidename;
    private String gender, gendername;
    private String prefixid, prefixname;
    private boolean sideleft, sideright;
    private String provincename, districtname, subdistrictname;
    private String spid, spname, upid, upname, name, id_card, mobile, email, address, zip;

    @Bind(R.id.stepview2) StepView stepView;
    @Bind(R.id.button_accept) CustomTextview buttonSave;
    @Bind(R.id.button_decline) CustomTextview buttonClear;
    @Bind(R.id.btn_search_sponsors_id) ImageView searchSponsorsID;
    @Bind(R.id.btn_search_upline_id) ImageView searchUplineID;
    @Bind(R.id.sponsors_id) CustomEdittext sponsorsID;
    @Bind(R.id.upline_id) CustomEdittext uplineID;
    @Bind(R.id.sponsors_name) CustomEdittext sponsorsName;
    @Bind(R.id.upline_name) CustomEdittext uplineName;
    @Bind(R.id.prefix_name) Spinner spinnerPrefixName;
    @Bind(R.id.register_name) CustomEdittext registerName;
    @Bind(R.id.radio_group) RadioGroup radioGroup;
    @Bind(R.id.male) RadioButton radioMale;
    @Bind(R.id.female) RadioButton radioFemale;
    @Bind(R.id.forErrorGender) TextView genderError;
    @Bind(R.id.radio_left) RadioButton radioLeft;
    @Bind(R.id.radio_right) RadioButton radioRight;
    @Bind(R.id.forErrorSide) TextView sideError;
    @Bind(R.id.register_birthdate) Spinner spinnerBirthDate;
    @Bind(R.id.register_birthmonth) Spinner spinnerBirthMonth;
    @Bind(R.id.register_birthyear) Spinner spinnerBirthYear;
    @Bind(R.id.dateError) TextView dateError;
    @Bind(R.id.monthError) TextView monthError;
    @Bind(R.id.yearError) TextView yearError;
    @Bind(R.id.register_nationality) Spinner spinnerNationality;
    @Bind(R.id.register_id) CustomEdittext registerID;
    @Bind(R.id.register_mobile) CustomEdittext registerMobile;
    @Bind(R.id.register_email) CustomEdittext registerEmail;
    @Bind(R.id.register_address) CustomEdittext registerAddress;
    @Bind(R.id.register_province) Spinner spinnerProvince;
    @Bind(R.id.register_amphor) Spinner spinnerAmphor;
    @Bind(R.id.register_district) Spinner spinnerDistrict;
    @Bind(R.id.register_zip) CustomEdittext registerZip;
    @Bind(R.id.textviewForErrorProvince) TextView provinceError;
    @Bind(R.id.textviewForErrorDistrict) TextView districtError;
    @Bind(R.id.textviewForErrorSubDistrict) TextView subdistrictError;
    @Bind(R.id.nationalityError) TextView nationError;

    public InputFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input, container, false);
        ButterKnife.bind(this, view);
        SignUpActivity signUpActivity = (SignUpActivity) getActivity();
        signUpActivity.setToolbarTitle(R.string.signup_second_page);
        stepView();
        initWidget();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        ActivityResultBus.getInstance().register(mActivityResultSubscriber);
    }

    @Override
    public void onStop() {
        super.onStop();
        ActivityResultBus.getInstance().unregister(mActivityResultSubscriber);
    }

    private Object mActivityResultSubscriber = new Object() {
        @Subscribe
        public void onActivityResultReceived(ActivityResultEvent event) {
            int requestCode = event.getRequestCode();
            int resultCode = event.getResultCode();
            Intent data = event.getData();
            onActivityResult(requestCode, resultCode, data);
        }
    };

    private void stepView() {
        List<String> lables = new ArrayList<>();
        lables.add("");
        lables.add("");
        lables.add("");
        stepView.setStepText(lables);
        stepView.setVerticalSpace(10);
        stepView.setDrawableSize(55);
        stepView.setCurrentStep(2);
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
        initProvince();
        initPrefixName();
        initNationality();
        initBirthDateSpinner();
        buttonSave.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        searchSponsorsID.setOnClickListener(this);
        searchUplineID.setOnClickListener(this);

        radioButtons = new RadioButton[] {radioLeft, radioRight, radioMale, radioFemale};
        textViews = new TextView[] {dateError, monthError, yearError, provinceError, districtError, subdistrictError, nationError};
        spinners = new Spinner[] {spinnerBirthDate, spinnerBirthMonth, spinnerBirthYear, spinnerProvince, spinnerAmphor, spinnerDistrict, spinnerNationality};
        fields = new EditText[] {sponsorsID, sponsorsName, uplineID, uplineName, registerName, registerID, registerMobile, registerEmail, registerAddress, registerZip};
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            previousStep();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void nextStep(SignupDataModel model) {
        Intent intent = new Intent(getActivity(), ApplyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("signupmodel", model);
        intent.putExtras(bundle);
        startActivityForResult(intent , REQUEST_APPLY);
        getActivity().overridePendingTransition(0, 0);
    }

    private void previousStep() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.register_container, new TemrsAndConditionsFragment(), "TemrsAndConditionsFragment").addToBackStack(null).commit();
    }

    private void checkEmpty() {

        if (validateRadio(radioGroup, genderError)) {
            Log.e("Radio button validate", "Check!");
            if (radioMale.isChecked()) {
                gender = "1";
                gendername = getActivity().getResources().getString(R.string.signup_input_hint_register_gender_male);
            } else if (radioFemale.isChecked()) {
                gender = "2";
                gendername = getActivity().getResources().getString(R.string.signup_input_hint_register_gender_female);
            }
        } else {
            Log.e("Radio button validate", "Not check!");
        }

        if (radioLeft.isChecked() || radioRight.isChecked()) {
            if (radioLeft.isChecked() && sideleft ) {
                sideid = "1";
                sidename = getActivity().getResources().getString(R.string.left);
            } else if (radioRight.isChecked() && sideright) {
                sideid = "2";
                sidename = getActivity().getResources().getString(R.string.right);
            }
        } else {
            sideError.setError(getActivity().getResources().getString(R.string.side_empty));
        }

        if (sideleft && sideright) {
            if (radioLeft.isChecked()) {
                sideid = "1";
                sidename = getActivity().getResources().getString(R.string.left);
            } else if (radioRight.isChecked()) {
                sideid = "2";
                sidename = getActivity().getResources().getString(R.string.right);
            }
        }

        if (validateSpinner(spinners, textViews) && validateRadio(radioGroup, genderError) && validateForm(fields) && isValidEmail(registerEmail.getText().toString())) {
            spid = sponsorsID.getText().toString();
            spname = sponsorsName.getText().toString();
            upid = uplineID.getText().toString();
            upname = uplineName.getText().toString();
            name = registerName.getText().toString();
            id_card = registerID.getText().toString();
            mobile = registerMobile.getText().toString();
            email = registerEmail.getText().toString();
            address = registerAddress.getText().toString();
            zip = registerZip.getText().toString();
            bdate = y + "-" + m + "-" +d;

            signModel = new SignupDataModel(
                    spid,
                    spname,
                    upid,
                    upname,
                    sideid,
                    sidename,
                    prefixid,
                    prefixname,
                    name,
                    gender,
                    gendername,
                    bdate,
                    nationality,
                    id_card,
                    mobile,
                    email,
                    address,
                    provincename,
                    districtname,
                    subdistrictname,
                    zip
            );

            nextStep(signModel);
        }
    }

    private void initPrefixName() {
        final ArrayList<Model> prefix = new ArrayList<Model>();
        for (int i = 0; i <= 4; i++) {
            Model model = new Model();
            switch (i) {
                case 0 :
                    model.setItemName(getActivity().getResources().getString(R.string.prefix));
                    break;
                case 1 :
                    model.setItemName(getActivity().getResources().getString(R.string.prefix_name_male));
                    break;
                case 2 :
                    model.setItemName(getActivity().getResources().getString(R.string.prefix_name_female_married));
                    break;
                case 3 :
                    model.setItemName(getActivity().getResources().getString(R.string.prefix_name_female_single));
                    break;
                case 4 :
                    model.setItemName(getActivity().getResources().getString(R.string.prefix_name_other));
                    break;
                default:break;
            }
            prefix.add(model);
        }
        spinnerDateAdapter = new SpinnerDateAdapter(getActivity(), R.layout.spinner_item, prefix, getResources(), "Prefix");
        spinnerPrefixName.setAdapter(spinnerDateAdapter);
        spinnerPrefixName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    ((TextView) view.findViewById(R.id.row_item)).setTextColor(getResources().getColor(R.color.colorAccent));
                    prefixid = "" + position;
                    prefixname = prefix.get(position).getItemName();
                } else {
                    prefixname = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    ArrayList<Model> date;
    private void initBirthDateSpinner() {
        date = new ArrayList<Model>();
        for (int i = 0; i <= 31; i++) {
            Model model = new Model();
            model.setItemName(i+"");
            date.add(model);
        }

        spinnerDateAdapter = new SpinnerDateAdapter(getActivity(), R.layout.spinner_item, date, getResources(), "Date");
        spinnerBirthDate.setAdapter(spinnerDateAdapter);
        spinnerBirthDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    ((TextView) view.findViewById(R.id.row_item)).setTextColor(getResources().getColor(R.color.colorAccent));
                    if (position < 10) {
                        d = "0" + date.get(position).getItemName();
                    } else {
                        d = date.get(position).getItemName();
                    }
                } else {
                    d = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final ArrayList<Model> month = new ArrayList<Model>();
        for (int i = 0; i <= 12; i++) {
            Model model = new Model();
            model.setItemName(new setCalendarToMultipleLanguage().calendarToString(getActivity(), i));
            month.add(model);
        }
        spinnerDateAdapter = new SpinnerDateAdapter(getActivity(), R.layout.spinner_item, month, getResources(), "Month");
        spinnerBirthMonth.setAdapter(spinnerDateAdapter);
        spinnerBirthMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    ((TextView) view.findViewById(R.id.row_item)).setTextColor(getResources().getColor(R.color.colorAccent));
                    if (position < 10) {
                        m = "0" + position;
                    } else {
                        m = "" + position;
                    }
                } else {
                    m = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final ArrayList<Model> years = new ArrayList<Model>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1951; i <= thisYear; i++) {
            Model model = new Model();
            model.setItemName(Integer.toString(i));
            years.add(model);
        }

        spinnerDateAdapter = new SpinnerDateAdapter(getActivity(), R.layout.spinner_item, years, getResources(), "Year");
        spinnerBirthYear.setAdapter(spinnerDateAdapter);
        spinnerBirthYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    ((TextView) view.findViewById(R.id.row_item)).setTextColor(getResources().getColor(R.color.colorAccent));
                    y = years.get(position).getItemName();
                } else {
                    y = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_accept :
                buttonSave.startAnimation(new AnimateButton().animbutton());
                checkEmpty();
                break;
            case R.id.button_decline :
                clearForm(fields, spinners, radioButtons);
                break;
            case R.id.btn_search_sponsors_id :
                searchSponsorsID.startAnimation(new AnimateButton().animbutton());
                checkSponsors();
                break;
            case R.id.btn_search_upline_id :
                searchUplineID.startAnimation(new AnimateButton().animbutton());
                checkUpline();
                break;
            default: break;
        }
    }

    private void clearForm(EditText[] fields, Spinner[] spinners, RadioButton[] radioButtons) {
        for (int i = 0; i < fields.length; i++) {
            EditText current = fields[i];
            current.setText("");
        }

        for (int i = 0; i < spinners.length; i++) {
            Spinner spinner1 = spinners[i];
            spinner1.setSelection(0);
        }

        for (int i = 0; i < radioButtons.length; i++) {
            RadioButton radioButton = radioButtons[i];
            radioButton.setChecked(false);
            if (!radioButton.isEnabled()) {
                radioButton.setEnabled(true);
            }
        }
    }

    private void checkSponsors() {
        spid = sponsorsID.getText().toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DoParseSponsors().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_CHECK_SPONSORS);
        } else {
            new DoParseSponsors().execute(EndPoints.API_CHECK_SPONSORS);
        }
    }

    private void checkUpline() {
        if (sponsorsID.getText().toString().equals("")) {
            sponsorsID.setError(getActivity().getResources().getString(R.string.sponsors_id_empty));
        } else {
            upid = uplineID.getText().toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                new DoParseUPA().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_CHECK_UPLINE);
            } else {
                new DoParseUPA().execute(EndPoints.API_CHECK_UPLINE);
            }
        }
    }

    private void initProvince() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DoParseProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_PROVINCE);
        } else {
            new DoParseProvince().execute(EndPoints.API_PROVINCE);
        }
    }

    private boolean validateForm(EditText[] fields) {
        boolean validate = false;
        for (int i = 0; i < fields.length; i++) {
            EditText current = fields[i];
            if (current.getText().toString().length() == 0) {
                current.setError(getActivity().getResources().getString(R.string.form_empty));
                validate = false;
            } else {
                current.setError(null);
                validate = true;
            }
        }
        return validate;
    }

    private boolean validateSpinner(Spinner[] spinner, TextView[] textViews) {
        boolean spin = false;
        for (int i = 0; i < spinner.length; i++) {
            Spinner spinner1 = spinner[i];
            TextView textView = textViews[i];
            if (spinner1.getSelectedItemPosition() == 0 || spinner1.getSelectedItemPosition() < 0) {
                textView.setError(getActivity().getResources().getString(R.string.form_spinner_not_select));
                spin = false;
            } else {
                textView.setError(null);
                spin = true;
            }
        }
        return spin;
    }

    private boolean validateRadio(RadioGroup radioGroup, TextView textView) {
        if (radioGroup.getCheckedRadioButtonId() == -1 || radioGroup.getCheckedRadioButtonId() < 0 ) {
            textView.setError(getResources().getString(R.string.form_spinner_not_select));
            return false;
        } else {
            textView.setError(null);
            return true;
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public class DoParseProvince extends AsyncTask<String, Void, Void> implements ParseProvince.parseProvinceCallback {

        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                parseProvince = new ParseProvince(url);
            }
            parseProvince.postProvinceRequest(this);
            return null;
        }

        @Override
        public void onFailed(String result) {

        }

        @Override
        public void onParseProvinceCallbackSuccess(ArrayList<Model> provinceModels, final List<ProvinceModel> modelList) {
            provinceModelList = modelList;
            setProvince(provinceModels);
        }
    }

    String provinceID;
    private void setProvince(ArrayList<Model> models) {
        spinnerCustomAdapter = new SpinnerCustomAdapter(getActivity(), R.layout.spinner_item, models, getResources(), "province");

        spinnerProvince.setAdapter(spinnerCustomAdapter);
        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    ((TextView) view.findViewById(R.id.row_item)).setTextColor(getResources().getColor(R.color.colorAccent));
                    provinceID = provinceModelList.get(position - 1).getProvicneId();
                    provincename = provinceModelList.get(position - 1).getProvinceTh();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        new DoParseDistrict().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_DISTRICT);
                    } else {
                        new DoParseDistrict().execute(EndPoints.API_DISTRICT);
                    }
                } else {
                    provincename = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public class DoParseDistrict extends AsyncTask<String, Void, Void> implements ParseDistrict.parseDistrictCallback {

        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                parseDistrict = new ParseDistrict(url, provinceID);
            }
            parseDistrict.postDistrictRequest(this);
            return null;
        }

        @Override
        public void onFailed(String result) {

        }

        @Override
        public void onParseDistrictCallbackSuccess(ArrayList<Model> districtModels, List<DistrictModel> modelList) {
            districtModelList = modelList;
            setDistrict(districtModels);
        }
    }

    String districtID;
    private void setDistrict(ArrayList<Model> models) {
        spinnerCustomAdapter = new SpinnerCustomAdapter(getActivity(), R.layout.spinner_item, models, getResources(), "district");

        spinnerAmphor.setAdapter(spinnerCustomAdapter);
        spinnerAmphor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    ((TextView) view.findViewById(R.id.row_item)).setTextColor(getResources().getColor(R.color.colorAccent));
                    districtID = districtModelList.get(position).getDistrictId();
                    districtname = districtModelList.get(position).getDistrictTh();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        new DoParseSubDistrict().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_SUB_DISTRICT);
                    } else {
                        new DoParseSubDistrict().execute(EndPoints.API_SUB_DISTRICT, districtID);
                    }
                } else {
                    districtname = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public class DoParseSubDistrict extends AsyncTask<String, Void, Void> implements ParseSubDistrict.parseSubDistrictCallback {
        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                parseSubDistrict = new ParseSubDistrict(url, districtID);
            }
            parseSubDistrict.postSubDistrictRequest(this);
            return null;
        }

        @Override
        public void onFailed(String result) {

        }

        @Override
        public void onParseSubDistrictCallbackSuccess(ArrayList<Model> subdistrictModels, List<SubDistrictModel> modelList) {
            subDistrictModelList = modelList;
            setSubDistrict(subdistrictModels);
        }
    }

    String subdistrictID;
    private void setSubDistrict(ArrayList<Model> models) {
        spinnerCustomAdapter = new SpinnerCustomAdapter(getActivity(), R.layout.spinner_item, models, getResources(), "sub-district");

        spinnerDistrict.setAdapter(spinnerCustomAdapter);
        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    ((TextView) view.findViewById(R.id.row_item)).setTextColor(getResources().getColor(R.color.colorAccent));
                    subdistrictID = subDistrictModelList.get(position).getSubdistrictId();
                    subdistrictname = subDistrictModelList.get(position).getSubdistrictTh();
                } else {
                    subdistrictname = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initNationality() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DoParseNationality().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_NATIONALITY);
        } else {
            new DoParseNationality().execute(EndPoints.API_NATIONALITY);
        }
    }

    public class DoParseNationality extends AsyncTask<String, Void, Void> implements ParseNationality.parseNationalityCallback{

        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                parseNationality = new ParseNationality(url);
            }
            parseNationality.postNationalityRequest(this);
            return null;
        }

        @Override
        public void onFailed(String result) {

        }

        @Override
        public void onParseNationalityCallbackSuccess(ArrayList<Model> nation) {
            setNationalitySpinner(nation);
            for (int i = 0; i < nation.size(); i++) {
                Model model = new Model();
                model.setItemName(nation.get(i).getItemName());
                nationalityModel.add(model);
            }
        }
    }

    private void setNationalitySpinner(final ArrayList<Model> nation) {
        spinnerDateAdapter = new SpinnerDateAdapter(getActivity(), R.layout.spinner_item, nation, getResources(), "Nationality");
        spinnerNationality.setAdapter(spinnerDateAdapter);
        spinnerNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    ((TextView) view.findViewById(R.id.row_item)).setTextColor(getResources().getColor(R.color.colorAccent));
                    nationality = nation.get(position).getItemName();
                } else {
                    nationality = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public class DoParseSponsors extends AsyncTask<String, Void, Void> implements ParseSPandUP.parseSponsorsCallback {

        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                parseSPandUP = new ParseSPandUP(url, spid, "");
            }
            parseSPandUP.postSponsorsRequest(this);
            return null;
        }

        @Override
        public void onFailed(String result) {
            sponsorsID.setError(result);
        }

        @Override
        public void onParseSponsorsCallbackSuccess(String sponsorsname) {
            setSponsorsName(sponsorsname);
        }
    }

    private void setSponsorsName(String name) {
        sponsorsName.setText(name);
    }

    public class DoParseUPA extends AsyncTask<String, Void, Void> implements ParseSPandUP.parseUPACallback {

        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                parseSPandUP = new ParseSPandUP(url, spid, upid);
            }
            parseSPandUP.postUPARequest(this);
            return null;
        }

        @Override
        public void onFailed(String result) {
            uplineID.setError(result);
        }

        @Override
        public void onParseUPACallbackSuccess(String uplinename, boolean left, boolean right) {
            setRadioSide(uplinename, left, right);
        }
    }

    private void setRadioSide(String uplinename, boolean left, boolean right) {
        uplineName.setText(uplinename);
        sideleft = left;
        sideright = right;
        if (!left) {
            radioLeft.setChecked(true);
            radioLeft.setEnabled(false);
            radioRight.setOnClickListener(OptionClickListener);
        } else {
            radioLeft.setChecked(false);
            radioLeft.setEnabled(true);
            radioLeft.setOnClickListener(OptionClickListener);
        }

        if (!right) {
            radioRight.setChecked(true);
            radioRight.setEnabled(false);
            radioLeft.setOnClickListener(OptionClickListener);
        } else {
            radioRight.setChecked(false);
            radioRight.setEnabled(true);
            radioRight.setOnClickListener(OptionClickListener);
        }

        if (left && right) {
            radioLeft.setChecked(false);
            radioLeft.setEnabled(true);
            radioRight.setChecked(false);
            radioLeft.setEnabled(true);
            radioLeft.setOnClickListener(OptionClickListener2);
            radioRight.setOnClickListener(OptionClickListener2);
        }
    }

    RadioButton.OnClickListener OptionClickListener = new RadioButton.OnClickListener() {

        int i = 0;
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.radio_left :
                    if (i == 0) {
                        i++;
                        radioLeft.setChecked(true);
                    } else {
                        radioLeft.setChecked(false);
                        i = 0;
                    }
                    break;
                case R.id.radio_right :
                    if (i == 0) {
                        i++;
                        radioRight.setChecked(true);
                    } else {
                        radioRight.setChecked(false);
                        i = 0;
                    }
                    break;
                default: break;
            }
        }
    };

    RadioButton.OnClickListener OptionClickListener2 = new RadioButton.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.radio_left :
                    if (radioRight.isChecked()) {
                        radioRight.setChecked(false);
                    }
                    break;
                case R.id.radio_right :
                    if (radioLeft.isChecked()) {
                        radioLeft.setChecked(false);
                    }
                    break;
                default: break;
            }
        }
    };
}
