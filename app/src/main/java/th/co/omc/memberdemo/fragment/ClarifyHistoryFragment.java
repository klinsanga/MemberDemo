package th.co.omc.memberdemo.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import th.co.omc.memberdemo.adapter.ClarifyHoldAdapter;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.ClarifyHoldItem;
import th.co.omc.memberdemo.parse.ParseClarify;
import th.co.omc.memberdemo.utils.AnimateButton;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.setCalendarToMultipleLanguage;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClarifyHistoryFragment extends Fragment implements View.OnClickListener {

    ParseClarify parseClarify;

    private int month, year;
    private Calendar _calendar;
    private String parameterMonth;

    ClarifyHoldAdapter adapter;
    List<ClarifyHoldItem> clarifyHoldItemList = new ArrayList<ClarifyHoldItem>();

    @Bind(R.id.clarify_hold_not_found) RelativeLayout no_data;
    @Bind(R.id.clarify_hold_btn_next) LinearLayout next;
    @Bind(R.id.clarify_hold_btn_previous) LinearLayout previous;
    @Bind(R.id.clarify_hold_text_month) CustomTextview textviewMonth;
    @Bind(R.id.recyclerview_clarify_hold) RecyclerView recyclerView;
    @Bind(R.id.clarifyHoldProgressbarLayout) RelativeLayout progressbar;
    public ClarifyHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clarify_history, container, false);
        ButterKnife.bind(this, view);
        initWidget();
        initCalendar();
        return view;
    }

    private void initWidget() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
    }

    private void prepareItem() {
        clarifyHoldItemList.clear();
        adapter = new ClarifyHoldAdapter(getActivity(), clarifyHoldItemList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        no_data.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        new DoParsTask().execute(EndPoints.API_CLARIFY_HOLD);
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
        switch (view.getId()) {
            case R.id.clarify_hold_btn_next :
                next.startAnimation(new AnimateButton().animbutton());
                nextMonth();
                break;
            case R.id.clarify_hold_btn_previous :
                previous.startAnimation(new AnimateButton().animbutton());
                previousMonth();
                break;
            default: break;
        }
    }

    public class DoParsTask extends AsyncTask<String, Void, Void> implements ParseClarify.ClarifyHoldCallback {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            for (String urlVal : params) {
                parseClarify = new ParseClarify(urlVal, parameterMonth);
            }
            parseClarify.postClarifyHoldRequest(this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onFailed(String result) {
            clarifyHoldItemList.clear();
            adapter = new ClarifyHoldAdapter(getActivity(), clarifyHoldItemList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
            progressbar.setVisibility(View.GONE);
        }

        @Override
        public void onParseClarifyHoldSuccess(List<ClarifyHoldItem> list) {
            adapter = new ClarifyHoldAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            progressbar.setVisibility(View.GONE);
        }
    }
}