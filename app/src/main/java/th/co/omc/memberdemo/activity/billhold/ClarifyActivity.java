package th.co.omc.memberdemo.activity.billhold;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.tonyvu.sc.model.Cart;
import com.android.tonyvu.sc.util.CartHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.activity.MainActivity;
import th.co.omc.memberdemo.adapter.ClarifyAdapter;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.ClarifyItem;
import th.co.omc.memberdemo.parse.ParseClarify;
import th.co.omc.memberdemo.utils.AnimateButton;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.setCalendarToMultipleLanguage;

public class ClarifyActivity extends AppCompatActivity implements View.OnClickListener, ClarifyAdapter.ClickListener {

    ClarifyAdapter adapter;
    ParseClarify parseClarify;

    private int month, year;
    private Calendar _calendar;
    private String parameterMonth;

    Cart cart = CartHelper.getCart();
    List<ClarifyItem> clarifyItemList = new ArrayList<ClarifyItem>();

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.recyclerview) RecyclerView recyclerView;
    @Bind(R.id.Clarify_not_found) RelativeLayout itemNotFound;
    @Bind(R.id.ClarifyProgressBarLayout) RelativeLayout progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_clarify);
        ButterKnife.bind(this);
        initWidget();
        prepareItem();
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
        customTextview.setText(R.string.clarify_title);
        customTextview.setTextColor(getResources().getColor(R.color.White));
        customTextview.setTextSize(26);
        customTextview.setTypeface(bold, Typeface.BOLD);
        customTextview.setGravity(Gravity.CENTER);
        customTextview.setLayoutParams(layoutParams);
        toolbar.addView(customTextview);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cart.clear();
    }

    private void prepareItem() {
        clarifyItemList.clear();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ClarifyActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        /*adapter = new ClarifyAdapter(ClarifyActivity.this, clarifyItemList);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(ClarifyActivity.this);
        adapter.notifyDataSetChanged();*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DoParseTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_CLARIFY);
        } else {
            new DoParseTask().execute(EndPoints.API_CLARIFY);
        }

        if (clarifyItemList.size() < 0) {
            itemNotFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.btn_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_name) {
            startActivity(new Intent(ClarifyActivity.this, MainActivity.class));
            finish();
            return true;
        }

        if (id == android.R.id.home) {
            startActivity(new Intent(ClarifyActivity.this, MainActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*private void initCalendar() {
        _calendar = Calendar.getInstance();
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);
        textviewMonth.setText(new setCalendarToMultipleLanguage().calendarToString(ClarifyActivity.this, month) + " " + year);

        if (month < 10) {
            parameterMonth = year + "-0" + month;
        } else {
            parameterMonth = year + "-" + month;
        }

        prepareItem();
    }

    private void nextMonth() {
        if (month > 11) {
            month = 1;
            year++;
        } else {
            month++;
        }

        textviewMonth.setText(new setCalendarToMultipleLanguage().calendarToString(ClarifyActivity.this, month) + " " + year);
        if (month < 10) {
            parameterMonth = year + "-0" + month;
        } else {
            parameterMonth = year + "-" + month;
        }
        prepareItem();
    }

    private void previousMonth() {

        if (month <= 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        textviewMonth.setText(new setCalendarToMultipleLanguage().calendarToString(ClarifyActivity.this, month) + " " + year);
        if (month < 10) {
            parameterMonth = year + "-0" + month;
        } else {
            parameterMonth = year + "-" + month;
        }
        prepareItem();
    }*/

    @Override
    public void onClick(View view) {
        /*switch (view.getId()) {
            case R.id.clarify_hold_btn_next :
                next.startAnimation(new AnimateButton().animbutton());
                nextMonth();
                break;
            case R.id.clarify_hold_btn_previous :
                previous.startAnimation(new AnimateButton().animbutton());
                previousMonth();
                break;
            default: break;
        }*/
    }

    @Override
    public void itemClicked(View view, int position) {
        ClarifyItem item = clarifyItemList.get(position);
        Log.e("pass bid", item.getBid().toString());
        Intent intent = new Intent(ClarifyActivity.this, ClarifyItemListActivity.class);
        intent.putExtra("bid", item.getBid().toString());
        intent.putExtra("billnumber", item.getNumber().toString());
        startActivity(intent);
    }

    public class DoParseTask extends AsyncTask<String, Void, Void> implements ParseClarify.ClarifyCallback {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            itemNotFound.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... strings) {
            for (String url : strings) {
                parseClarify = new ParseClarify(url, "");
            }
            parseClarify.postClarifyRequest(this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }

        @Override
        public void onFailed(String result) {
            progressBar.setVisibility(View.GONE);
            itemNotFound.setVisibility(View.VISIBLE);
        }

        @Override
        public void onParseClarifySuccess(List<ClarifyItem> list) {
            clarifyItemList = list;
            adapter = new ClarifyAdapter(ClarifyActivity.this, clarifyItemList);
            recyclerView.setAdapter(adapter);
            adapter.setClickListener(ClarifyActivity.this);
            adapter.notifyDataSetChanged();

            progressBar.setVisibility(View.GONE);
            itemNotFound.setVisibility(View.GONE);
        }
    }
}
