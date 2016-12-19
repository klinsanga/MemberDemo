package th.co.omc.memberdemo.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.adapter.FaststartAdapter;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.FaststartItem;
import th.co.omc.memberdemo.model.TotalItem;
import th.co.omc.memberdemo.parse.ParseCommission;
import th.co.omc.memberdemo.utils.AnimateButton;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.setCalendarToMultipleLanguage;

/**
 * A simple {@link Fragment} subclass.
 */
public class FaststartFragment extends Fragment implements View.OnClickListener, FaststartAdapter.ClickListener {
    private static final String TAG = FaststartFragment.class.getSimpleName();

    ParseCommission parseJson;
    FragmentManager manager;
    FragmentTransaction transaction;

    private int month, year;
    private Calendar _calendar;
    private String parameterMonth;

    FaststartAdapter adapter;
    List<FaststartItem> faststartItemList = new ArrayList<FaststartItem>();

    @Bind(R.id.btn_next) LinearLayout next;
    @Bind(R.id.btn_previous) LinearLayout previous;
    @Bind(R.id.text_month) CustomTextview textviewMonth;
    @Bind(R.id.back_btn) RelativeLayout back_button;
    @Bind(R.id.recyclerview_faststart) RecyclerView recyclerView;
    @Bind(R.id.progressBarLayout) RelativeLayout progressBar;
    @Bind(R.id.not_found) RelativeLayout no_data;
    public FaststartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faststart, container, false);
        ButterKnife.bind(this, view);
        back_button.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        initCalendar();
        initWidget();
        return view;
    }

    private void initWidget() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new FaststartAdapter(getActivity(), faststartItemList);
        adapter.setClickListener(this);

        if (faststartItemList.size() > 0) {
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            prepareItem();
        }
    }

    private void prepareItem() {
        faststartItemList.clear();
        adapter = new FaststartAdapter(getActivity(), faststartItemList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.GONE);
        no_data.setVisibility(View.GONE);
        new DoParsTask().execute(EndPoints.API_FASTSTART);
    }

    private void initCalendar() {
        _calendar = Calendar.getInstance();
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);
        textviewMonth.setText(new setCalendarToMultipleLanguage().calendarToString(getActivity(), month) + " " + year);

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

        textviewMonth.setText(new setCalendarToMultipleLanguage().calendarToString(getActivity(), month) + " " + year);
        if (month < 10) {
            parameterMonth = year + "-0" + month;
        } else {
            parameterMonth = year + "-" + month;
        }
        Log.e(TAG, "Parameter : " + parameterMonth);
        prepareItem();
    }

    private void previousMonth() {
        if (month <= 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        textviewMonth.setText(new setCalendarToMultipleLanguage().calendarToString(getActivity(), month) + " " + year);
        if (month < 10) {
            parameterMonth = year + "-0" + month;
        } else {
            parameterMonth = year + "-" + month;
        }
        Log.e(TAG, "Parameter : " + parameterMonth);
        prepareItem();
    }

    @Override
    public void onClick(View view) {
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.back_btn :
                back_button.startAnimation(new AnimateButton().animbutton());
                transaction.replace(R.id.content_frame, new CommissionTabFragment(), "CommissionTabFragment").addToBackStack(null).commit();
                break;
            case R.id.btn_next:
                next.startAnimation(new AnimateButton().animbutton());
                nextMonth();
                break;
            case R.id.btn_previous :
                previous.startAnimation(new AnimateButton().animbutton());
                previousMonth();
                break;
            default:break;
        }
    }

    @Override
    public void setMenuClickListener(View view, int position) {
        FaststartItem item = faststartItemList.get(position);
    }

    public class DoParsTask extends AsyncTask<String, Void, Void> implements ParseCommission.VolleyCallback {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            for (String urlVal : params) {
                parseJson = new ParseCommission(urlVal, parameterMonth);
            }
            parseJson.postFaststartRequest(this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onFailed(String result) {
            faststartItemList.clear();
            adapter = new FaststartAdapter(getActivity(), faststartItemList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onParseFaststartSuccess(List<FaststartItem> list) {
            adapter = new FaststartAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onParseTotalIncomeSuccess(List<TotalItem> list) {

        }
    }
}
