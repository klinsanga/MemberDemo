package th.co.omc.memberdemo.activity.signup;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bit.szw.widget.StepView;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.activity.MainActivity;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.signup.SignupDataModel;
import th.co.omc.memberdemo.parse.SendDataRegister;
import th.co.omc.memberdemo.utils.AnimateButton;
import th.co.omc.memberdemo.utils.EndPoints;

public class ApplyActivity extends AppCompatActivity implements View.OnClickListener {

    SendDataRegister sendDataRegister;
    DoSendDataToServer sendDataToServer;

    private CustomTextview customTextview;
    private Toolbar.LayoutParams layoutParams;

    private SignupDataModel signupDataModel;
    final List<SignupDataModel> signupDataModelList = new ArrayList<SignupDataModel>();

    @Bind(R.id.apply_sponsors_id) CustomTextview spID;
    @Bind(R.id.apply_sponsors_name) CustomTextview spName;
    @Bind(R.id.apply_upline_id) CustomTextview upaID;
    @Bind(R.id.apply_upline_name) CustomTextview upaName;
    @Bind(R.id.apply_upline_side) CustomTextview upaSide;
    @Bind(R.id.apply_register_name_with_prefix) CustomTextview regNameWithPrefix;
    @Bind(R.id.apply_register_gender) CustomTextview regGender;
    @Bind(R.id.apply_register_birthdate) CustomTextview regBirthDate;
    @Bind(R.id.apply_register_nationality) CustomTextview regNationality;
    @Bind(R.id.apply_register_identification) CustomTextview regIdentification;
    @Bind(R.id.apply_register_mobile) CustomTextview regMobile;
    @Bind(R.id.apply_register_email) CustomTextview regEmail;
    @Bind(R.id.apply_register_address) CustomTextview regAddress;
    @Bind(R.id.button_edit) CustomTextview buttonEdit;
    @Bind(R.id.button_apply) CustomTextview buttonApply;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.stepview2) StepView stepView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_apply);
        ButterKnife.bind(this);
        initWidget();
        stepView();
        readIntent();
    }

    private void initWidget() {
        Typeface bold = Typeface.createFromAsset(this.getAssets(), "fonts/helvethaica_ext_bold.ttf");
        toolbar.setTitle("");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.White));
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.icon);
        imageView.setImageDrawable(null);

        layoutParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 0, 0);
        customTextview = new CustomTextview(this);
        customTextview.setTextColor(getResources().getColor(R.color.White));
        customTextview.setTextSize(26);
        customTextview.setTypeface(bold, Typeface.BOLD);
        customTextview.setGravity(Gravity.CENTER);
        customTextview.setLayoutParams(layoutParams);
        customTextview.setText(R.string.signup_thrid_page);


        toolbar.addView(customTextview);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonEdit.setOnClickListener(this);
        buttonApply.setOnClickListener(this);

    }

    private void stepView() {
        List<String> lables = new ArrayList<>();
        lables.add("");
        lables.add("");
        lables.add("");
        stepView.setStepText(lables);
        stepView.setVerticalSpace(10);
        stepView.setDrawableSize(55);
        stepView.setCurrentStep(3);
        stepView.setLineHeight(8);

        stepView.setCurrentDrawable(ContextCompat.getDrawable(this, R.drawable.current_step));
        stepView.setDrawable(getResources().getDrawable(R.drawable.current_step));

        stepView.setLineColor(getResources().getColor(R.color.colorAccent));
        stepView.setReachedLineColor(getResources().getColor(R.color.colorAccent));
        stepView.setReachedDrawable(ContextCompat.getDrawable(this, R.drawable.step_completed));

        stepView.setUnreachedLineColor(getResources().getColor(R.color.colorPrimary));
        stepView.setUnreachedDrawable(ContextCompat.getDrawable(this, R.drawable.step_uncomplete_black));
    }

    private void readIntent() {
        try {
            Bundle data = this.getIntent().getExtras();
            signupDataModel = (SignupDataModel) data.getSerializable("signupmodel");
            signupDataModelList.add(signupDataModel);
            setDataToUI(signupDataModelList);
        } catch (Exception e) {
            Log.e("Null item", e.toString());
        }
    }

    private void setDataToUI(List<SignupDataModel> signupDataModelList) {
        for (int i = 0; i < signupDataModelList.size(); i++) {
            spID.setText(signupDataModelList.get(i).getSponsorsID());
            spName.setText(signupDataModelList.get(i).getSponsorsName());
            upaID.setText(signupDataModelList.get(i).getUplineID());
            upaName.setText(signupDataModelList.get(i).getUplineName());
            upaSide.setText(signupDataModelList.get(i).getSidename());
            regNameWithPrefix.setText(signupDataModelList.get(i).getPrefixName() + " " + signupDataModelList.get(i).getName());
            regGender.setText(signupDataModelList.get(i).getGenderName());
            regBirthDate.setText(signupDataModelList.get(i).getBirthDate());
            regNationality.setText(signupDataModelList.get(i).getNationality());
            regIdentification.setText(signupDataModelList.get(i).getIdentification());
            regMobile.setText(signupDataModelList.get(i).getMobile());
            regEmail.setText(signupDataModelList.get(i).getEmail());
            regAddress.setText(signupDataModelList.get(i).getAddress() + " " + this.getResources().getString(R.string.sub_district) + signupDataModelList.get(i).getSubDistrict() + " "
                    + this.getResources().getString(R.string.district) + signupDataModelList.get(i).getDistrict() + "\n" + this.getResources().getString(R.string.province) + signupDataModelList.get(i).getProvince() + " "
                    + this.getResources().getString(R.string.form_send_product_zip) + signupDataModelList.get(i).getZip());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.btn_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_name) {
            startActivity(new Intent(ApplyActivity.this, MainActivity.class));
            finish();
            return true;
        } else if (id == android.R.id.home) {
            Intent intent = new Intent(ApplyActivity.this, SignUpActivity.class);
            intent.putExtra("Activity", "Apply");
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_edit :
                buttonEdit.startAnimation(new AnimateButton().animbutton());
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.button_apply :
                buttonApply.startAnimation(new AnimateButton().animbutton());
                sendToServer();
                break;
            default:break;
        }
    }

    private void sendToServer() {
        sendDataToServer = new DoSendDataToServer();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            sendDataToServer.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_REGISTER);
        } else {
            sendDataToServer.execute(EndPoints.API_REGISTER);
        }
    }

    public class DoSendDataToServer extends AsyncTask<String, Void, Void> implements SendDataRegister.sendDataRegisterCallback {

        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                sendDataRegister = new SendDataRegister(url, signupDataModelList);
            }
            sendDataRegister.postDataRegisterRequest(this);
            return null;
        }

        @Override
        public void onFailed(String result) {

        }

        @Override
        public void onsendDataRegisterCallbackSuccess(String msg) {
            Log.e("Status register", msg);
            if (msg.equals("SUCCESS")) {
                alertSuccess();
            } else {
                alertFail();
            }
        }
    }

    private void alertSuccess() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ApplyActivity.this);
        final LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_alert, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                stepView.setCurrentDrawable(ContextCompat.getDrawable(ApplyActivity.this, R.drawable.step_completed));
                alertDialog.dismiss();
                backToHome();
            }
        }.start();
    }

    private void backToHome() {
        new CountDownTimer(2500, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                sendDataToServer.cancel(true);
                startActivity(new Intent(ApplyActivity.this, MainActivity.class));
                finish();
            }
        }.start();
    }

    private void alertFail() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_alert_failed, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        new CountDownTimer(1500, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                alertDialog.dismiss();
            }
        }.start();
    }
}
