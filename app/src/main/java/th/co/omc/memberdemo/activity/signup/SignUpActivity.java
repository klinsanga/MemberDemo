package th.co.omc.memberdemo.activity.signup;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.activity.MainActivity;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.fragment.signup.InputFragment;
import th.co.omc.memberdemo.fragment.signup.TemrsAndConditionsFragment;
import th.co.omc.memberdemo.utils.ActivityResultBus;
import th.co.omc.memberdemo.utils.ActivityResultEvent;

public class SignUpActivity extends AppCompatActivity{

    private CustomTextview customTextview;
    private Toolbar.LayoutParams layoutParams;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.register_container) FrameLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        initWidget();
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

        loadFormRegister();

        toolbar.addView(customTextview);
        setSupportActionBar(toolbar);
    }

    private void loadFormRegister() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.register_container, new TemrsAndConditionsFragment()).addToBackStack(null).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.btn_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.register_container);
        switch (item.getItemId()) {
            case android.R.id.home:
                if (currentFragment instanceof InputFragment) {
                    transaction.replace(R.id.register_container, new TemrsAndConditionsFragment()).addToBackStack(null).commit();
                }
                break;
            case R.id.action_name :
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                finish();
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

     public void setToolbarTitle(int title) {
         Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.register_container);
         if (currentFragment instanceof TemrsAndConditionsFragment) {
             String t = "\t\t\t\t\t" + this.getResources().getString(title);
             customTextview.setText(t);
             getSupportActionBar().setDisplayHomeAsUpEnabled(false);
         } else if (currentFragment instanceof InputFragment) {
             customTextview.setText(title);
             getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActivityResultBus.getInstance().postQueue(new ActivityResultEvent(requestCode, resultCode, data));
    }

    @Override
    protected void onResume() {
        super.onResume();
        readIntent();
    }

    private void readIntent() {
        try {
            Bundle data = this.getIntent().getExtras();
            if (data != null) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.register_container, new InputFragment()).addToBackStack(null).commit();
            }
        } catch (Exception e) {
            Log.e("Null item", e.toString());
        }
    }
}
