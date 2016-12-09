package th.co.omc.memberdemo.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import th.co.omc.memberdemo.adapter.WeekAdapter;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.WeekItem;
import th.co.omc.memberdemo.parse.ParseWeek;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.setCalendarToMultipleLanguage;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeekCommissionFragment extends Fragment implements View.OnClickListener {

    private int month, year;
    private Calendar _calendar;
    private String parameterMonth;

    ParseWeek parseWeek;
    WeekAdapter adapter;
    WeekItem weekItem;
    List<WeekItem> weekItemArrayList = new ArrayList<WeekItem>();

    @Bind(R.id.week_btn_next)
    LinearLayout next;
    @Bind(R.id.week_btn_previous)
    LinearLayout previous;
    @Bind(R.id.recyclerview_week)
    RecyclerView recyclerView;
    @Bind(R.id.week_text_month)
    CustomTextview textviewMonth;
    @Bind(R.id.weekProgressbarLayout)
    RelativeLayout progressbar;
    @Bind(R.id.not_found) RelativeLayout data_not_found;
    public WeekCommissionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_week_commission, container, false);
        ButterKnife.bind(this, view);
        initWidger();
        initCalendar();
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        return view;
    }

    private void initWidger() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new WeekAdapter(getActivity(), weekItemArrayList);
        recyclerView.setAdapter(adapter);

        prepareItem();
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
        weekItemArrayList.clear();
        adapter = new WeekAdapter(getActivity(), weekItemArrayList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
        prepareItem();
    }

    private void previousMonth() {
        weekItemArrayList.clear();
        adapter = new WeekAdapter(getActivity(), weekItemArrayList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
        prepareItem();
    }

    private void prepareItem() {
        data_not_found.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        new DoParsTask().execute(EndPoints.API_WEEK);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.week_btn_next:
                nextMonth();
                break;
            case R.id.week_btn_previous:
                previousMonth();
                break;
            default:
                break;
        }
    }

    public class DoParsTask extends AsyncTask<String, Void, Void> implements ParseWeek.VolleyCallback {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            for (String urlVal : params) {
                parseWeek = new ParseWeek(urlVal, parameterMonth);
            }
            parseWeek.postWeekRequest(this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onFailed(String result) {
            weekItemArrayList.clear();
            adapter = new WeekAdapter(getActivity(), weekItemArrayList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.GONE);
            data_not_found.setVisibility(View.VISIBLE);
            progressbar.setVisibility(View.GONE);
        }

        @Override
        public void onParseWeekSuccess(List<WeekItem> list) {
            adapter = new WeekAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            progressbar.setVisibility(View.GONE);
        }
    }
}
