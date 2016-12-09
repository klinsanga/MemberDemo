package th.co.omc.memberdemo.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Locale;

import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.utils.LanguageHelper;
import th.co.omc.memberdemo.utils.MyApplication;

public class SplashScreenActivity extends AppCompatActivity {
    public static final String TAG = SplashScreenActivity.class.getSimpleName();

    String DEFUALT_LANGUAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_splash_screen);

        loadLanguageSettings();

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                startActivity(new Intent(SplashScreenActivity.this, BannerActivity.class));
                finish();
            }

        }.start();
    }

    private void loadLanguageSettings() {
        try {
            if (MyApplication.getInstance().getPrefManager().getApplicationLanguage().equals("TH")) {
                languageThai();
            } else if (MyApplication.getInstance().getPrefManager().getApplicationLanguage().equals("EN")) {
                languageEnglish();
            }
        } catch (Exception ex) {
            Log.e(TAG, "Get settings : " + ex.toString() + ", " + MyApplication.getInstance().getPrefManager().getApplicationLanguage());
            initDefualtLanguage();
        }
    }

    private void languageEnglish() {
        /*Configuration configEN = new Configuration();
        configEN.locale = Locale.ENGLISH;
        getResources().updateConfiguration(configEN, null);*/
        LanguageHelper.changeLocale(this.getResources(), "en");
    }

    private void languageThai() {
        /*Configuration configTH = new Configuration();
        configTH.locale = new Locale("th_TH");
        getResources().updateConfiguration(configTH, null);*/
        LanguageHelper.changeLocale(this.getResources(), "th");
    }

    private void initDefualtLanguage() {
        DEFUALT_LANGUAGE = Locale.getDefault().getDisplayLanguage();
        Configuration configTH = new Configuration();
        configTH.setToDefaults();
        getResources().updateConfiguration(configTH, null);
        if (DEFUALT_LANGUAGE.equals("ไทย")) {
            MyApplication.getInstance().getPrefManager().setApplicationLanguage("TH");
        } else if (DEFUALT_LANGUAGE.equals("English")) {
            MyApplication.getInstance().getPrefManager().setApplicationLanguage("EN");
        }
    }
}
