package th.co.omc.memberdemo.activity.shopping;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.adapter.shopping.SpinnerCustomAdapter;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.ItemMember;
import th.co.omc.memberdemo.model.shopping.AddressModel;
import th.co.omc.memberdemo.model.shopping.ConfirmItem;
import th.co.omc.memberdemo.model.shopping.DistrictModel;
import th.co.omc.memberdemo.model.shopping.InformationModel;
import th.co.omc.memberdemo.model.shopping.InventModel;
import th.co.omc.memberdemo.model.shopping.Model;
import th.co.omc.memberdemo.model.shopping.ModelTypeWithKey;
import th.co.omc.memberdemo.model.shopping.ProvinceModel;
import th.co.omc.memberdemo.model.shopping.SubDistrictModel;
import th.co.omc.memberdemo.model.shopping.TypeModel;
import th.co.omc.memberdemo.parse.ParseDistrict;
import th.co.omc.memberdemo.parse.ParseInvent;
import th.co.omc.memberdemo.parse.ParseMemberAddress;
import th.co.omc.memberdemo.parse.ParseProvince;
import th.co.omc.memberdemo.parse.ParseSubDistrict;
import th.co.omc.memberdemo.parse.ParseType;
import th.co.omc.memberdemo.utils.AnimateButton;
import th.co.omc.memberdemo.utils.ConvertToCurrency;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

public class InputMethodActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_APPLY = 55;


    ConfirmItem item;
    TypeModel typeModel;
    ItemMember itemMember;
    ConvertToCurrency convertToCurrency;
    SpinnerCustomAdapter spinnerCustomAdapter;

    ParseType parseType;
    ParseInvent parseInvent;
    ParseProvince parseProvince;
    ParseDistrict parseDistrict;
    ParseSubDistrict parseSubDistrict;
    ParseMemberAddress parseMemberAddress;

    List<ModelTypeWithKey> modelKeySaType = new ArrayList<ModelTypeWithKey>();
    List<ModelTypeWithKey> modelKeyPaymentType = new ArrayList<ModelTypeWithKey>();
    List<TypeModel> orderTypeModel = new ArrayList<TypeModel>();
    List<TypeModel> payTypeModel = new ArrayList<TypeModel>();
    List<AddressModel> addressModelList = new ArrayList<AddressModel>();
    List<DistrictModel> districtModelList = new ArrayList<DistrictModel>();
    List<ProvinceModel> provinceModelList = new ArrayList<ProvinceModel>();
    List<InventModel> inventModelList = new ArrayList<InventModel>();
    List<SubDistrictModel> subDistrictModelList = new ArrayList<SubDistrictModel>();

    RadioGroup radioGroup;
    View inputForm, selfReceive, send;
    EditText inputId, inputName, selfNote;
    Spinner orderType, payType, branchRecieve;
    RadioButton sendProduct, notsendProduct, existAddress;
    Spinner spinnerProvince, spinnerAmphor, spinnerDistrict;
    EditText recieveName, recieveTel, recieveAddress, recieveZip, recieveNote;
    TextView textViewForError1, textViewForError2, textViewForError3, textViewForErrorBranch;
    TextView textViewForErrorProvince, textViewForErrorDistrict, textViewForErrorSubDistrict;

    /*---------- Variable for get data ---------- */
    String id, pid, did, sdid, sendId;
    String key_sa_type, key_payment_type;
    String provinceName, districtName, subdistictName;
    String recieve_name, recieve_mobile, recieve_address, recieve_zip, branch, note;
    String member_id, member_name, order_type, payment_type, send_product, province_name, district_name, sub_district_name, branchName;
    /*------------------------------------------- */

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btn_search_id)
    ImageView searchId;
    @Bind(R.id.total_pv)
    CustomTextview totalPv;
    @Bind(R.id.total_price)
    CustomTextview totalPrice;
    @Bind(R.id.total_item)
    CustomTextview totalItem;
    @Bind(R.id.you_ewallet)
    CustomTextview youEwallet;
    @Bind(R.id.button_verify_item)
    CustomTextview verifyItem;
    @Bind(R.id.button_data_confrim)
    CustomTextview dataConfrim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_method);
        ButterKnife.bind(this);
        initWidget();
        initView();
        getPrefData();
    }

    private void initWidget() {
        Typeface bold = Typeface.createFromAsset(this.getAssets(), "fonts/helvethaica_ext_bold.ttf");
        toolbar.setTitle("");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.White));
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.icon);
        imageView.setImageDrawable(null);

        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 8, 0);
        CustomTextview customTextview = new CustomTextview(this);
        customTextview.setText(getResources().getString(R.string.form_imformation_title));
        customTextview.setTextColor(getResources().getColor(R.color.White));
        customTextview.setTextSize(22);
        customTextview.setTypeface(bold, Typeface.BOLD);
        customTextview.setGravity(Gravity.CENTER);
        customTextview.setLayoutParams(layoutParams);
        toolbar.addView(customTextview);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        verifyItem.setOnClickListener(this);
        dataConfrim.setOnClickListener(this);
        searchId.setOnClickListener(this);
    }

    private void getPrefData() {
        convertToCurrency = new ConvertToCurrency();
        try {
            item = MyApplication.getInstance().getCartPrefManager().getCartItem();
            itemMember = MyApplication.getInstance().getPrefManager().getUser();

            totalPv.setText(item.getTotalPv());
            totalItem.setText(item.getTotalItem());
            totalPrice.setText(convertToCurrency.Currency(item.getTotalPrice()));
            youEwallet.setText(convertToCurrency.Currency(itemMember.getEwallet().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        // Connect view
        inputForm = findViewById(R.id.input_form);
        send = inputForm.findViewById(R.id.send_product);
        selfReceive = inputForm.findViewById(R.id.self_receive);
        // End //

        radioGroup = (RadioGroup) inputForm.findViewById(R.id.radio_group);

        textViewForError1 = (TextView) inputForm.findViewById(R.id.textviewForError1);
        textViewForError2 = (TextView) inputForm.findViewById(R.id.textviewForError2);
        textViewForError3 = (TextView) inputForm.findViewById(R.id.textviewForError3);
        inputId = (EditText) inputForm.findViewById(R.id.member_id);
        inputName = (EditText) inputForm.findViewById(R.id.member_name);
        orderType = (Spinner) inputForm.findViewById(R.id.order_type);
        payType = (Spinner) inputForm.findViewById(R.id.pay_type);
        sendProduct = (RadioButton) inputForm.findViewById(R.id.send);
        notsendProduct = (RadioButton) inputForm.findViewById(R.id.not_send);
        existAddress = (RadioButton) inputForm.findViewById(R.id.radio_exist_address);

        recieveName = (EditText) send.findViewById(R.id.recieve_name);
        recieveTel = (EditText) send.findViewById(R.id.recieve_tel);
        recieveAddress = (EditText) send.findViewById(R.id.recieve_address);
        recieveZip = (EditText) send.findViewById(R.id.recieve_zip);
        recieveNote = (EditText) send.findViewById(R.id.recieve_note);
        spinnerProvince = (Spinner) send.findViewById(R.id.spinner_province);
        spinnerAmphor = (Spinner) send.findViewById(R.id.spinner_amphor);
        spinnerDistrict = (Spinner) send.findViewById(R.id.spinner_district);
        textViewForErrorProvince = (TextView) send.findViewById(R.id.textviewForErrorProvince);
        textViewForErrorDistrict = (TextView) send.findViewById(R.id.textviewForErrorDistrict);
        textViewForErrorSubDistrict = (TextView) send.findViewById(R.id.textviewForErrorSubDistrict);

        selfNote = (EditText) selfReceive.findViewById(R.id.note);
        branchRecieve = (Spinner) selfReceive.findViewById(R.id.branch_recieve);
        textViewForErrorBranch = (TextView) selfReceive.findViewById(R.id.textviewForErrorBranchRecieve);

        // init button listener //
        existAddress.setOnClickListener(OptionClickListener);
        sendProduct.setOnClickListener(myOptionOnClickListener);
        notsendProduct.setOnClickListener(myOptionOnClickListener);
        // End //

        initOrderType();
        initPayType();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopping_cart, menu);
        MenuItem item = menu.findItem(R.id.action_cart);
        item.setIcon(null);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(InputMethodActivity.this, ShoppingActivity.class));
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_verify_item:
                verifyItem();
                break;
            case R.id.button_data_confrim:
                conFrimData();
                break;
            case R.id.btn_search_id:
                searchId();
            default:
                break;
        }
    }

    private void conFrimData() {
        dataConfrim.startAnimation(new AnimateButton().animbutton());

        if (validateForm(inputId)) {
            member_id = inputId.getText().toString();
        }

        if (validateForm(inputName)) {
            member_name = inputName.getText().toString();
        }

        if (validateSpinner(orderType, textViewForError1)) {
            key_sa_type = modelKeySaType.get(orderType.getSelectedItemPosition()).getKeyName();
            order_type = orderTypeModel.get(orderType.getSelectedItemPosition() - 1).getTypename();
            Log.e("key sa type", key_sa_type);
        }

        if (validateSpinner(payType, textViewForError2)) {
            key_payment_type = modelKeyPaymentType.get(payType.getSelectedItemPosition()).getKeyName();
            payment_type = payTypeModel.get(payType.getSelectedItemPosition() - 1).getTypename();
            Log.e("key payment", key_payment_type);
        }

        if (validateRadio(radioGroup, textViewForError3)) {

            if (sendProduct.isChecked() || existAddress.isChecked()) {
                if (validateForm(recieveName))
                    recieve_name = recieveName.getText().toString();

                if (validateForm(recieveTel))
                    recieve_mobile = recieveTel.getText().toString();

                if (validateForm(recieveAddress))
                    recieve_address = recieveAddress.getText().toString();

                if (validateSpinner(spinnerProvince, textViewForErrorProvince)) {
                    pid = provinceModelList.get(spinnerProvince.getSelectedItemPosition() - 1).getProvicneId();
                    province_name = provinceModelList.get(spinnerProvince.getSelectedItemPosition() - 1).getProvinceTh();
                }

                if (validateSpinner(spinnerAmphor, textViewForErrorDistrict)) {
                    did = districtModelList.get(spinnerAmphor.getSelectedItemPosition()).getDistrictId();
                    district_name = districtModelList.get(spinnerAmphor.getSelectedItemPosition()).getDistrictTh();
                }

                if (validateSpinner(spinnerDistrict, textViewForErrorSubDistrict)) {
                    sdid = subDistrictModelList.get(spinnerDistrict.getSelectedItemPosition()).getSubdistrictId();
                    sub_district_name = subDistrictModelList.get(spinnerDistrict.getSelectedItemPosition()).getSubdistrictTh();
                }

                if (validateForm(recieveZip))
                    recieve_zip = recieveZip.getText().toString();

                note = recieveNote.getText().toString();

                if(existAddress.isChecked()) {
                    send_product = "จัดส่งตามที่อยู่ที่แจ้งไว้";
                    sendId = "1";
                } else {
                    sendId = "1";
                    send_product = "ส่งสินค้า";
                }
                if (validateForm(inputId) && validateForm(inputName) && validateSpinner(orderType, textViewForError1) && validateSpinner(payType, textViewForError2)
                        && validateForm(recieveName) && validateForm(recieveTel) && validateForm(recieveAddress) && validateSpinner(spinnerProvince, textViewForErrorProvince)
                        && validateSpinner(spinnerAmphor, textViewForErrorDistrict) && validateSpinner(spinnerDistrict, textViewForErrorSubDistrict) && validateForm(recieveZip) ) {
                    confirmData();
                }
            } else if (notsendProduct.isChecked()) {
                if (validateSpinner(branchRecieve, textViewForErrorBranch)) {
                    branch = inventModelList.get(branchRecieve.getSelectedItemPosition() - 1).getInventCode();
                    branchName = inventModelList.get(branchRecieve.getSelectedItemPosition() - 1).getInventDesc();
                }
                note = selfNote.getText().toString();
                send_product = "รับของด้วยตัวเอง";
                sendId = "2";

                if (validateForm(inputId) && validateForm(inputName) && validateSpinner(orderType, textViewForError1) && validateSpinner(payType, textViewForError2) && validateSpinner(branchRecieve, textViewForErrorBranch)) {
                    confirmData();
                }
            }
        }
    }

    private void confirmData() {
        InformationModel informationModel = new InformationModel(
                itemMember.getMemberCode(),
                member_id,
                member_name,
                recieve_mobile,
                itemMember.getLocationbase(),
                item.getTotalPrice(),
                item.getTotalPv(),
                "0",
                order_type,
                key_sa_type,
                payment_type,
                key_payment_type,
                send_product,
                sendId,
                recieve_address,
                sub_district_name,
                district_name,
                province_name,
                recieve_zip,
                provinceName,
                districtName,
                sub_district_name,
                branch,
                branchName,
                note
        );
        Intent intent = new Intent(InputMethodActivity.this, ConfirmationInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("information", informationModel);
        intent.putExtras(bundle);
        startActivityForResult(intent , REQUEST_APPLY);
    }

    private void verifyItem() {
        setResult(RESULT_OK);
        finish();
        verifyItem.startAnimation(new AnimateButton().animbutton());
    }

    private void searchId() {
        searchId.startAnimation(new AnimateButton().animbutton());

        if (validateForm(inputId)) {
            id = inputId.getText().toString();
            new DoParsTask().execute(EndPoints.API_DATA);
        }
    }

    private void initSelfBranch() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DoParseInvent().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_INVENT);
        } else {
            new DoParseInvent().execute(EndPoints.API_INVENT);
        }
    }

    public class DoParsTask extends AsyncTask<String, Void, Void> implements ParseMemberAddress.ParseMemberAddressCallback {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            for (String url : params) {
                parseMemberAddress = new ParseMemberAddress(url, id);
            }
            parseMemberAddress.postMemberAddress(this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        public void onFailed(String result) {
            inputId.setError(result);
        }

        @Override
        public void onParseMemberAddressSuccess(List<AddressModel> addressModels) {
            addressModelList = addressModels;
            for (int i = 0; i < addressModelList.size(); i++) {
                AddressModel addressModel = addressModelList.get(i);
                inputId.setText(addressModel.getMemberCode().toString());
                inputName.setText(addressModel.getMemberName().toString());
            }
            setAddress();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return false;
        }
        return true;
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

    private boolean validateForm(EditText fields) {
        if (fields.getText().toString().length() == 0) {
            fields.setError(InputMethodActivity.this.getResources().getString(R.string.form_empty));
            return false;
        } else {
            fields.setError(null);
            return true;
        }
    }

    private boolean validateSpinner(Spinner spinner, TextView textViews) {
        if (spinner.getSelectedItemPosition() == 0 || spinner.getSelectedItemPosition() < 0) {
            textViews.setError(InputMethodActivity.this.getResources().getString(R.string.form_spinner_not_select));
            return false;
        } else {
            textViews.setError(null);
            return true;
        }
    }

    private void setAddress() {
        for (int i = 0; i < addressModelList.size(); i++) {
            AddressModel item = addressModelList.get(i);
            recieveName.setText(item.getMemberName().toString());
            recieveTel.setText(item.getMemberMobile().toString());
            recieveAddress.setText(item.getMemberAddress().toString()
                    + (item.getMemberBuilding().toString().equals("") ? "" : " " + getResources().getString(R.string.building) + " : " + item.getMemberBuilding().toString())
                    + (item.getMemberVillage().toString().equals("") ? "" : " " + getResources().getString(R.string.village) + " : " + item.getMemberVillage().toString())
                    + (item.getMemberStreet().toString().equals("") ? "" : " " + getResources().getString(R.string.street) + " : " + item.getMemberStreet().toString())
                    + (item.getMemberSoi().toString().equals("") ? "" : " " + getResources().getString(R.string.soi) + " : " + item.getMemberSoi().toString())
            );
            recieveZip.setText(item.getMemberZip().toString());

            provinceName = item.getMemberProvince().toString();
            districtName = item.getMemberAmphur().toString();
            subdistictName = item.getMemberDistrict().toString();
        }

        for (int j = 0; j < provinceModelList.size(); j++) {
            ProvinceModel model = provinceModelList.get(j);
            if (model.getProvinceTh().equals(provinceName)) {
                //Log.e("Province", model.getProvinceTh() + ": " + model.getProvicneId());
                spinnerProvince.setSelection(Integer.parseInt(model.getProvicneId()));
            }
        }
    }

    private void clearAddress() {
        recieveName.setText("");
        recieveTel.setText("");
        recieveAddress.setText("");
        recieveZip.setText("");
    }

    private void notSearch() {
        id = itemMember.getMemberCode().toString();
        new DoParsTask().execute(EndPoints.API_DATA);
    }

    RadioButton.OnClickListener myOptionOnClickListener = new RadioButton.OnClickListener() {
        public void onClick(View v) {
            if (sendProduct.isChecked()) {
                existAddress.setVisibility(View.VISIBLE);
                send.setVisibility(View.VISIBLE);
                selfReceive.setVisibility(View.GONE);
                initProvince();
                if (existAddress.isChecked()) {
                    setAddress();
                } else {
                    clearAddress();
                }
            } else {
                existAddress.setChecked(false);
                existAddress.setVisibility(View.GONE);
                send.setVisibility(View.GONE);
                selfReceive.setVisibility(View.VISIBLE);
                initSelfBranch();
            }
        }
    };

    RadioButton.OnClickListener OptionClickListener = new RadioButton.OnClickListener() {

        int i = 0;

        @Override
        public void onClick(View view) {
            if (i == 0) {
                i++;
                existAddress.setChecked(true);
                if (addressModelList.size() > 0) {
                    setAddress();
                } else {
                    notSearch();
                }
            } else {
                existAddress.setChecked(false);
                clearAddress();
                initProvince();
                i = 0;
            }
        }
    };

    private void initProvince() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DoParseProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_PROVINCE);
        } else {
            new DoParseProvince().execute(EndPoints.API_PROVINCE);
        }
    }

    private void initPayType() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DoParseType().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_PAYMENT_TYPE);
        } else {
            new DoParseType().execute(EndPoints.API_PAYMENT_TYPE);
        }
    }

    private void initOrderType() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DoParseType().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_BILL_TYPE);
        } else {
            new DoParseType().execute(EndPoints.API_BILL_TYPE);
        }
    }

    public class DoParseType extends AsyncTask<String, Void, Void> implements ParseType.parseOrderTypeCallback {

        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                parseType = new ParseType(url);
            }
            parseType.postOrderTypeRequest(this);
            return null;
        }

        @Override
        public void onFailed(String result) {

        }

        @Override
        public void onParseOrderTypeCallbackSuccess(ArrayList<Model> OrderArrayList, List<ModelTypeWithKey> modelKey) {
            modelKeySaType = modelKey;
            spinnerCustomAdapter = new SpinnerCustomAdapter(InputMethodActivity.this, R.layout.spinner_item, OrderArrayList, getResources(), "ordertype");

            orderType.setAdapter(spinnerCustomAdapter);
            orderType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    if (position > 0) {
                        ((TextView) view.findViewById(R.id.row_item)).setTextColor(getResources().getColor(R.color.colorAccent));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            for (int i = 0; i < OrderArrayList.size(); i++) {
                if (i > 0) {
                    typeModel = new TypeModel(
                            OrderArrayList.get(i).getItemName()
                    );
                    orderTypeModel.add(typeModel);
                }
            }
        }

        @Override
        public void onParsePaymentTypeCallbackSuccess(ArrayList<Model> PayModelArrayList, List<ModelTypeWithKey> modelKey) {
            modelKeyPaymentType = modelKey;
            spinnerCustomAdapter = new SpinnerCustomAdapter(InputMethodActivity.this, R.layout.spinner_item, PayModelArrayList, getResources(), "paytype");

            payType.setAdapter(spinnerCustomAdapter);
            payType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    if (position > 0) {
                        ((TextView) view.findViewById(R.id.row_item)).setTextColor(getResources().getColor(R.color.colorAccent));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            for (int i = 0; i < PayModelArrayList.size(); i++) {
                if (i > 0) {
                    typeModel = new TypeModel(
                            PayModelArrayList.get(i).getItemName()
                    );
                    payTypeModel.add(typeModel);
                }
            }
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
        spinnerCustomAdapter = new SpinnerCustomAdapter(InputMethodActivity.this, R.layout.spinner_item, models, getResources(), "province");

        spinnerProvince.setAdapter(spinnerCustomAdapter);
        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    ((TextView) view.findViewById(R.id.row_item)).setTextColor(getResources().getColor(R.color.colorAccent));
                    provinceID = provinceModelList.get(position - 1).getProvicneId();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        new DoParseDistrict().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_DISTRICT);
                    } else {
                        new DoParseDistrict().execute(EndPoints.API_DISTRICT);
                    }
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
        spinnerCustomAdapter = new SpinnerCustomAdapter(InputMethodActivity.this, R.layout.spinner_item, models, getResources(), "district");

        spinnerAmphor.setAdapter(spinnerCustomAdapter);
        spinnerAmphor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    ((TextView) view.findViewById(R.id.row_item)).setTextColor(getResources().getColor(R.color.colorAccent));
                    districtID = districtModelList.get(position).getDistrictId();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        new DoParseSubDistrict().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_SUB_DISTRICT);
                    } else {
                        new DoParseSubDistrict().execute(EndPoints.API_SUB_DISTRICT, districtID);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (existAddress.isChecked()) {
            for (int k = 0; k < models.size(); k++) {
                Model m = models.get(k);
                if (m.getItemName().equals(districtName)) {
                    spinnerAmphor.setSelection(k);
                }
            }
        }
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

    private void setSubDistrict(ArrayList<Model> models) {
        spinnerCustomAdapter = new SpinnerCustomAdapter(InputMethodActivity.this, R.layout.spinner_item, models, getResources(), "sub-district");

        spinnerDistrict.setAdapter(spinnerCustomAdapter);
        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    ((TextView) view.findViewById(R.id.row_item)).setTextColor(getResources().getColor(R.color.colorAccent));
                    districtID = subDistrictModelList.get(position).getSubdistrictId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (existAddress.isChecked()) {
            for (int k = 0; k < models.size(); k++) {
                Model m = models.get(k);
                if (m.getItemName().equals(subdistictName)) {
                    spinnerDistrict.setSelection(k);
                }
            }
        }
    }

    public class DoParseInvent extends AsyncTask<String, Void, Void> implements ParseInvent.parseInventCallback{

        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                parseInvent = new ParseInvent(url);
            }
            parseInvent.postInventRequest(this);
            return null;
        }

        @Override
        public void onFailed(String result) {

        }

        @Override
        public void onParseInventCallbackSuccess(ArrayList<Model> inventModels, List<InventModel> inventList) {
            inventModelList = inventList;
            setBranch(inventModels);
        }
    }

    private void setBranch(ArrayList<Model> inventModels) {

        spinnerCustomAdapter = new SpinnerCustomAdapter(InputMethodActivity.this, R.layout.spinner_item, inventModels, getResources(), "branch");

        branchRecieve.setAdapter(spinnerCustomAdapter);
        branchRecieve.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    ((TextView) view.findViewById(R.id.row_item)).setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
