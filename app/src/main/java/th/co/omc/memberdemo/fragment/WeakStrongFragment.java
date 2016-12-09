package th.co.omc.memberdemo.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import th.co.omc.memberdemo.adapter.WeakstrongAdapter;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.WeakstrongItem;
import th.co.omc.memberdemo.parse.ParseCommission;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.setCalendarToMultipleLanguage;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeakStrongFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = WeakStrongFragment.class.getSimpleName();

    FragmentManager manager;
    FragmentTransaction transaction;

    ParseCommission parseCommission;
    private int month, year;
    private Calendar _calendar;
    private String parameterMonth;

    WeakstrongAdapter adapter;
    WeakstrongItem weakstrongItem;
    List<WeakstrongItem> weakstrongItemList = new ArrayList<WeakstrongItem>();

    @Bind(R.id.weakstrong_btn_next) LinearLayout next;
    @Bind(R.id.weakstrong_back_btn) RelativeLayout back;
    @Bind(R.id.weakstrong_btn_previous) LinearLayout previous;
    @Bind(R.id.recyclerview_weakstrong) RecyclerView recyclerView;
    @Bind(R.id.weakstrong_text_month) CustomTextview textviewMonth;
    @Bind(R.id.weakstrongProgressbarLayout) RelativeLayout progressbar;
    @Bind(R.id.not_found) RelativeLayout no_data;
    public WeakStrongFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weak_strong, container, false);
        ButterKnife.bind(this, view);
        initWidget();
        initCalendar();
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        return view;
    }

    private void initWidget() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new WeakstrongAdapter(getActivity(), weakstrongItemList);
        recyclerView.setAdapter(adapter);

        prepareItem();
    }

    private void prepareItem() {
        weakstrongItemList.clear();
        adapter = new WeakstrongAdapter(getActivity(), weakstrongItemList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.GONE);
        no_data.setVisibility(View.GONE);
        new DoParsTask().execute(EndPoints.API_WEAKSTRONG);

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
        prepareItem();
    }

    @Override
    public void onClick(View view) {
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.weakstrong_back_btn:
                transaction.replace(R.id.content_frame, new CommissionTabFragment(), "CommissionTabFragment").addToBackStack(null).commit();
                break;
            case R.id.weakstrong_btn_next:
                nextMonth();
                break;
            case R.id.weakstrong_btn_previous:
                previousMonth();
                break;
            default:break;
        }
    }

    public class DoParsTask extends AsyncTask<String, Void, Void> implements ParseCommission.WeakStrongCallback {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            for (String urlVal : params) {
                parseCommission = new ParseCommission(urlVal, parameterMonth);
            }
            parseCommission.postWeakStrongRequest(this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onFailed(String result) {
            weakstrongItemList.clear();
            adapter = new WeakstrongAdapter(getActivity(), weakstrongItemList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
            progressbar.setVisibility(View.GONE);
        }

        @Override
        public void onParseWeakStrongSuccess(List<WeakstrongItem> list) {
            adapter = new WeakstrongAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            progressbar.setVisibility(View.GONE);
        }
    }
}
