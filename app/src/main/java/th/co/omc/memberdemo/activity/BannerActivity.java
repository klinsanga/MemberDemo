package th.co.omc.memberdemo.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.adapter.ImageSlideAdapter;
import th.co.omc.memberdemo.network.ConnectionDetector;
import th.co.omc.memberdemo.utils.AnimateButton;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by teera-s on 9/7/2016 AD.
 */
public class BannerActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = BannerActivity.class.getSimpleName();

    Typeface thin;
    private static int NUM_PAGES = 0;
    private static int currentPage = 0;

    private static final int REQUEST_ERROR = 0;

    @Bind(R.id.pager) ViewPager pager;
    @Bind(R.id.btn_home) Button btnHome;
    @Bind(R.id.indicator) CirclePageIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_banner);
        ButterKnife.bind(this);
        loginSession();
        boolean isNetworkAvailable = ConnectionDetector.isConnectingToInternet(this);
        if (isNetworkAvailable) {
            initWidget();
            bannerslider();
        } else {
            startActivityForResult(new Intent(BannerActivity.this, NoInternetActivity.class), REQUEST_ERROR);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ERROR) {
            if (resultCode == RESULT_OK) {
                reload();
                initWidget();
                bannerslider();
            }
        }
    }

    private void initWidget() {
        //----- load fonts from assets folder -----//
        thin = Typeface.createFromAsset(getAssets(), "fonts/helvethaica_ext.ttf");

        btnHome.setTypeface(thin);
        btnHome.setOnClickListener(this);
    }

    private void bannerslider() {

        pager.setAdapter(new ImageSlideAdapter(BannerActivity.this));
        indicator.setViewPager(pager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        NUM_PAGES = 2;

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES)
                    currentPage = 0;
                pager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }

    private void loginSession() {
        long loginTime = new Date().getTime() - MyApplication.getInstance().getPrefManager().getUserLoginTime();
        int minutes = (int) ((loginTime / (1000*60)) % 60);
        if (minutes > 15) {
            MyApplication.getInstance().getPrefManager().setUserLoginStatus("", 0, false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home :
                clickButton();
                break;
            default: break;
        }
    }

    private void clickButton() {
        btnHome.startAnimation(new AnimateButton().animbutton());
        startActivity(new Intent(BannerActivity.this, MainActivity.class));
        finish();
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}
