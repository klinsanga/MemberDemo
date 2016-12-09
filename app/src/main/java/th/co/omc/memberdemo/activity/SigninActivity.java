package th.co.omc.memberdemo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.loadImage.ImageLoader;
import th.co.omc.memberdemo.model.ReportSponsorsItem;
import th.co.omc.memberdemo.model.ReportUplineItem;
import th.co.omc.memberdemo.parse.ParseJson;
import th.co.omc.memberdemo.utils.AnimateButton;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.LanguageHelper;
import th.co.omc.memberdemo.utils.MyApplication;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = SigninActivity.class.getSimpleName();

    private static final int REQUEST_ERROR = 0;

    Typeface thin;

    String DEFUALT_LANGUAGE;
    private ParseJson parseJson;
    private String username = null;
    private String password = null;

    ImageLoader imageLoader;

    @Bind(R.id.btn_login) AppCompatButton signin;
    @Bind(R.id.input_username) EditText editText_username;
    @Bind(R.id.input_password) EditText editText_password;
    @Bind(R.id.buttonChangeLanguage) Button buttonChangeLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);
        initWidget();
        imageLoader = new ImageLoader(this);
        imageLoader.clearCache();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadLanguageSettings();
    }

    private void initWidget() {
        //----- load fonts from assets folder -----//
        thin = Typeface.createFromAsset(getAssets(), "fonts/helvethaica_ext.ttf");

        //----- set fonts to widget -----//
        signin.setTypeface(thin);
        editText_username.setTypeface(thin);
        editText_password.setTypeface(thin);
        buttonChangeLanguage.setTypeface(thin);

        //----- set button click listener -----//
        signin.setOnClickListener(this);
        buttonChangeLanguage.setOnClickListener(this);
    }

    private void loadLanguageSettings() {
        try {
            if (MyApplication.getInstance().getPrefManager().getApplicationLanguage().equals("TH")) {
                setButtonTH();
            } else if (MyApplication.getInstance().getPrefManager().getApplicationLanguage().equals("EN")) {
                setButtonEN();
            }
        } catch (Exception ex) {
            Log.e(TAG, "Get settings : " + ex.toString());
            initDefualtLanguage();
        }
    }

    private void initDefualtLanguage() {
        DEFUALT_LANGUAGE = Locale.getDefault().getDisplayLanguage();
        if (DEFUALT_LANGUAGE.equals("ไทย")) {
            setButtonTH();
            MyApplication.getInstance().getPrefManager().setApplicationLanguage("TH");
        } else if (DEFUALT_LANGUAGE.equals("English")) {
            setButtonEN();
            MyApplication.getInstance().getPrefManager().setApplicationLanguage("EN");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.buttonChangeLanguage:
                changeButtonLanguage(view);
                break;
            default: break;
        }
    }

    private void changeButtonLanguage(View v) {
        final int status =(Integer) v.getTag();
        if (status == 1) {
            setButtonTH();
            MyApplication.getInstance().getPrefManager().setApplicationLanguage("TH");
            setLocale("th");
        } else if (status == 0) {
            setButtonEN();
            MyApplication.getInstance().getPrefManager().setApplicationLanguage("EN");
            setLocale("en");
        }
    }

    private void setButtonTH() {
        buttonChangeLanguage.setText("TH");
        buttonChangeLanguage.setTextColor(getApplication().getResources().getColor(R.color.White));
        buttonChangeLanguage.setBackgroundResource(R.drawable.language_shape);
        buttonChangeLanguage.setTag(0);
    }

    private void setButtonEN() {
        buttonChangeLanguage.setText("EN");
        buttonChangeLanguage.setTextColor(getApplication().getResources().getColor(R.color.colorAccent));
        buttonChangeLanguage.setBackgroundResource(R.drawable.language_shape2);
        buttonChangeLanguage.setTag(1);
    }

    public void setLocale(String lang) {
        switch (lang) {
            case "en":
                languageEnglish();
                reload();
                break;
            case "th":
                languageThai();
                reload();
                break;
            default:break;
        }
    }

    private void languageEnglish() {
        LanguageHelper.changeLocale(this.getResources(), "en");
    }

    private void languageThai() {
        LanguageHelper.changeLocale(this.getResources(), "th");
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void onLoginSuccess() {
        Log.e(TAG, new Date().getTime() + "");
        MyApplication.getInstance().getPrefManager().setUserLoginStatus(username, new Date().getTime(), true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onLoginFailed(String _errMessage) {
        editText_username.setError(_errMessage);
        editText_password.setError(_errMessage);
        //Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
    }

    public boolean validate(String username, String password) {
        boolean valid = true;

        if (username.isEmpty()) {
            editText_username.setError("Username empty!");
            valid = false;
        } else {
            editText_username.setError(null);
        }

        if (password.isEmpty()) {
            editText_password.setError("Password empty!");
            valid = false;
        } else {
            editText_password.setError(null);
        }
        return valid;
    }

    public void login() {
        Log.e(TAG, "Login");

        signin.startAnimation(new AnimateButton().animbutton());

        username = editText_username.getText().toString();
        password = editText_password.getText().toString();

        if (!validate(username, password)) {
            return;
        }

        new DoParsTask().execute(EndPoints.API_URL);
    }

    public class DoParsTask extends AsyncTask<String, Void, Void> implements ParseJson.VolleyCallback {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SigninActivity.this, R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            for (String urlVal : params) {
                parseJson = new ParseJson(urlVal, username, password);
            }
            parseJson.postLoginRequest(this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        @Override
        public void onSuccess(String result) {
            if (result.equals(EndPoints.COMPLETE))
                onLoginSuccess();
        }

        @Override
        public void onFailed(String result) {
            onLoginFailed(result);
        }

        @Override
        public void onParseUplineSuccess(List<ReportUplineItem> list) {

        }

        @Override
        public void onParseSponsorsSuccess(List<ReportSponsorsItem> list) {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)){
            return false;
        }
        return true;
    }
}