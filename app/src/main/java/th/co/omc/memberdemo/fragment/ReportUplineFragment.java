package th.co.omc.memberdemo.fragment;


import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.adapter.ReportUplineAdapter;
import th.co.omc.memberdemo.adapter.ViewAdapter;
import th.co.omc.memberdemo.customview.OnLoadMoreListener;
import th.co.omc.memberdemo.model.ReportUplineItem;
import th.co.omc.memberdemo.parse.HttpRequest;
import th.co.omc.memberdemo.parse.ParseJson;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportUplineFragment extends Fragment implements ViewAdapter.ClickListener {

    private ParseJson parseJson;
    private ReportUplineAdapter reportUplineAdapter;
    private ReportUplineItem reportUplineItem;
    private List<ReportUplineItem> reportUplineItemList = new ArrayList<ReportUplineItem>();

    ViewAdapter viewAdapter;

    HttpRequest httpRequest;

    JSONObject jsonObj;
    JSONObject object;
    JSONArray jsonArr;

    //@Bind(R.id.text_filter) EditText filter;
    @Bind(R.id.report_upline_layout_progress) RelativeLayout layoutprogress;
    @Bind(R.id.report_upline_not_found) RelativeLayout notfoundLayout;
    @Bind(R.id.recyclerview_report_upline) RecyclerView recyclerView;
    public ReportUplineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_upline, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/helvethaica_ext.ttf");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parseReport();
    }

    private void parseReport() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DoParseJson().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_UPLINE);
        } else {
            new DoParseJson().execute(EndPoints.API_UPLINE);
        }
    }

    @Override
    public void itemClicked(View view, int position) {
    }

    public class DoParseJson extends AsyncTask<String, Void, String> {
        String str = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(reportUplineItemList.size() > 0) {
                layoutprogress.setVisibility(View.GONE);
                notfoundLayout.setVisibility(View.GONE);
            } else {
                layoutprogress.setVisibility(View.VISIBLE);
                notfoundLayout.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            for (String u : params) {
                httpRequest = new HttpRequest(u);
            }
            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair(EndPoints.KEY_USERNAME, MyApplication.getInstance().getPrefManager().getUsername()));
            parameter.add(new BasicNameValuePair(EndPoints.KEY_API, EndPoints.API_KEYCODE));
            parameter.add(new BasicNameValuePair("start", "0"));
            parameter.add(new BasicNameValuePair("end", "5"));
            str = httpRequest.getHttpPost(parameter);
            return str;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            layoutprogress.setVisibility(View.GONE);
            notfoundLayout.setVisibility(View.GONE);
            if (s != null) {
                parseObject(s);
            }
        }
    }

    private void parseObject(String obj) {
        try {
            jsonArr = new JSONArray(obj);
            for (int i = 0; i < 5; i++) {
                JSONObject c = jsonArr.getJSONObject(i);
                reportUplineItem = new ReportUplineItem(
                        c.getString("mcode"),
                        c.getString("name_t"),
                        c.getString("mdate"),
                        c.getString("pos_cur"),
                        c.getString("pos_cur2"),
                        c.getString("pvall"),
                        c.getString("sp_code"),
                        c.getString("sp_name"),
                        c.getString("level"),
                        "",
                        convertSideToText(c.getString("lr"))
                );
                reportUplineItemList.add(reportUplineItem);
            }
        } catch (JSONException e) {
            Log.e("ReportUplineFragment", e.toString());
        }

        viewAdapter = new ViewAdapter(getActivity(), reportUplineItemList, recyclerView);
        recyclerView.setAdapter(viewAdapter);
        viewAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    new DoLoadmore().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_UPLINE);
                } else {
                    new DoLoadmore().execute(EndPoints.API_UPLINE);
                }

            }
        });
        viewAdapter.setClickListener(this);
        viewAdapter.setLoaded();
    }

    public class DoLoadmore extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            reportUplineItemList.add(null);
        }

        String str = null;
        @Override
        protected String doInBackground(String... params) {
            int index = reportUplineItemList.size();
            int end = index + 5;
            for (String u : params) {
                httpRequest = new HttpRequest(u);
            }
            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair(EndPoints.KEY_USERNAME, MyApplication.getInstance().getPrefManager().getUsername()));
            parameter.add(new BasicNameValuePair(EndPoints.KEY_API, EndPoints.API_KEYCODE));
            parameter.add(new BasicNameValuePair("start", index + ""));
            parameter.add(new BasicNameValuePair("end", end + ""));
            str = httpRequest.getHttpPost(parameter);
            return str;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setData(s);
        }
    }

    private void setData(String str) {
        reportUplineItemList.remove(reportUplineItemList.size() -1);
        viewAdapter.notifyItemRemoved(reportUplineItemList.size());

        if (str != null) {
            try {
                jsonArr = new JSONArray(str);
                for (int i = 0; i < 5; i++) {
                    JSONObject c = jsonArr.getJSONObject(i);
                    reportUplineItem = new ReportUplineItem(
                            c.getString("mcode"),
                            c.getString("name_t"),
                            c.getString("mdate"),
                            c.getString("pos_cur"),
                            c.getString("pos_cur2"),
                            c.getString("pvall"),
                            c.getString("sp_code"),
                            c.getString("sp_name"),
                            c.getString("level"),
                            "",
                            convertSideToText(c.getString("lr"))
                    );
                    reportUplineItemList.add(reportUplineItem);
                }
            } catch (JSONException e) {
                Log.e("ReportUplineLoadMore", e.toString());
            }
            viewAdapter.notifyDataSetChanged();
            viewAdapter.setLoaded();
            viewAdapter.setClickListener(this);
            layoutprogress.setVisibility(View.GONE);
        }
    }

    /*private void filter(){
        doParseJson.cancel(true);
        doLoadmore.cancel(true);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }*/

    private int convertSideToText(String side){
        switch (side) {
            case "0" :
                return 0;
            case "1" :
                return R.string.left_side;
            case "2" :
                return R.string.right_side;
            default:break;
        }
        return 0;
    }
}