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
import th.co.omc.memberdemo.adapter.AllsaleAdapter;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.AllsaleItem;
import th.co.omc.memberdemo.parse.ParseCommission;
import th.co.omc.memberdemo.utils.AnimateButton;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.setCalendarToMultipleLanguage;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllsaleFragment extends Fragment implements View.OnClickListener {

    ParseCommission parseCommission;
    FragmentManager manager;
    FragmentTransaction transaction;

    private int month, year;
    private Calendar _calendar;
    private String parameterMonth;

    AllsaleAdapter adapter;
    List<AllsaleItem> allsaleItems = new ArrayList<AllsaleItem>();

    @Bind(R.id.not_found) RelativeLayout data_not_found;
    @Bind(R.id.allsale_back_btn) RelativeLayout back;
    @Bind(R.id.allsale_btn_next) LinearLayout next;
    @Bind(R.id.allsale_btn_previous) LinearLayout previous;
    @Bind(R.id.allsale_text_month) CustomTextview txtmonth;
    @Bind(R.id.recyclerview_allsale) RecyclerView recyclerView;
    @Bind(R.id.allsaleProgressBarLayout) RelativeLayout progressBar;
    public AllsaleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_allsale, container, false);
        ButterKnife.bind(this, view);
        initWidget();
        initCalendar();
        return view;
    }

    private void initWidget() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AllsaleAdapter(getActivity(), allsaleItems);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
    }

    private void prepareItem() {
        allsaleItems.clear();
        adapter = new AllsaleAdapter(getActivity(), allsaleItems);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        data_not_found.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        new DoParsTask().execute(EndPoints.API_ALLSALE);
    }

    private void initCalendar() {
        _calendar = Calendar.getInstance();
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);
        txtmonth.setText(new setCalendarToMultipleLanguage().calendarToString(getActivity(), month) + " " + year);

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

        txtmonth.setText(new setCalendarToMultipleLanguage().calendarToString(getActivity(), month) + " " + year);
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
        txtmonth.setText(new setCalendarToMultipleLanguage().calendarToString(getActivity(), month) + " " + year);
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
            case R.id.allsale_back_btn :
                back.startAnimation(new AnimateButton().animbutton());
                CommissionTabFragment tabFragment = new CommissionTabFragment();
                Bundle bundle=new Bundle();
                bundle.putString("FragmentActivity", "month");
                tabFragment.setArguments(bundle);
                transaction.replace(R.id.content_frame, tabFragment, "CommissionTabFragment").addToBackStack(null).commit();
                break;
            case R.id.allsale_btn_next:
                next.startAnimation(new AnimateButton().animbutton());
                nextMonth();
                break;
            case R.id.allsale_btn_previous :
                previous.startAnimation(new AnimateButton().animbutton());
                previousMonth();
                break;
            default:break;
        }
    }

    public class DoParsTask extends AsyncTask<String, Void, Void> implements ParseCommission.allSaleCallback {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            for (String urlVal : params) {
                parseCommission = new ParseCommission(urlVal, parameterMonth);
            }
            parseCommission.postAllsaleRequest(this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onFailed(String result) {
            allsaleItems.clear();
            adapter = new AllsaleAdapter(getActivity(), allsaleItems);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.GONE);
            data_not_found.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onParseAllsaleSuccess(List<AllsaleItem> list) {
            adapter = new AllsaleAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }
    }
}
