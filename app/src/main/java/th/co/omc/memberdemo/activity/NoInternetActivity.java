package th.co.omc.memberdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.customview.CustomTextview;

public class NoInternetActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_SETTINGS = 0;

    @Bind(R.id.toSettings) CustomTextview settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_no_internet);
        ButterKnife.bind(this);

        settings.setOnClickListener(this);
    }

    public static NoInternetActivity getInstance() {
        return new NoInternetActivity();
    }

    public void detectWifiConnected(final String state) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                if (state.equals("connect")){
                    setResult(RESULT_OK, null);
                    finish();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.toSettings) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
            startActivityForResult(intent, REQUEST_SETTINGS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_SETTINGS) {
            setResult(RESULT_OK, null);
            finish();
        }
    }
}
