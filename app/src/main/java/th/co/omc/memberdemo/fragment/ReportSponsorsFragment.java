package th.co.omc.memberdemo.fragment;


import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import th.co.omc.memberdemo.adapter.ReportSponsorsAdapter;
import th.co.omc.memberdemo.adapter.ViewAdapterSponsors;
import th.co.omc.memberdemo.customview.OnLoadMoreListener;
import th.co.omc.memberdemo.model.ReportSponsorsItem;
import th.co.omc.memberdemo.parse.HttpRequest;
import th.co.omc.memberdemo.parse.ParseJson;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportSponsorsFragment extends Fragment implements ViewAdapterSponsors.ClickListener {

    JSONObject jsonObject;
    JSONObject object;
    JSONArray jsonArr;
    ViewAdapterSponsors viewAdapter;
    HttpRequest httpRequest;
    private ReportSponsorsItem reportSponsorsItem;

    private ParseJson parseJson;
    private ReportSponsorsAdapter reportSponsorsAdapter;
    private List<ReportSponsorsItem> reportSponsorsItemsList = new ArrayList<>();

    @Bind(R.id.recyclerview_sponsors) RecyclerView recyclerViewSponsors;
    public ReportSponsorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_sponsors, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        parseReport();
    }

    private void parseReport() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewSponsors.setLayoutManager(layoutManager);
        recyclerViewSponsors.setHasFixedSize(true);
        recyclerViewSponsors.setItemAnimator(new DefaultItemAnimator());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new DoParseJson().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EndPoints.API_SPONSORS);
        } else {
            new DoParseJson().execute(EndPoints.API_SPONSORS);
        }
    }

    @Override
    public void itemClicked(View view, int position) {

    }

    public class DoParseJson extends AsyncTask<String, Void, String> {
        String str = null;

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
                reportSponsorsItem = new ReportSponsorsItem(
                        c.getString("mcode"),
                        c.getString("name_t"),
                        c.getString("mdate"),
                        c.getString("pos_cur"),
                        c.getString("pos_cur2"),
                        c.getString("totpv"),
                        c.getString("sp_code"),
                        c.getString("sp_name"),
                        c.getString("lv_sp"),
                        ""
                );
                reportSponsorsItemsList.add(reportSponsorsItem);
            }
        } catch (JSONException e) {
            Log.e("ReportSponsorsFragment", e.toString());
        }

        viewAdapter = new ViewAdapterSponsors(getActivity(), reportSponsorsItemsList, recyclerViewSponsors);
        recyclerViewSponsors.setAdapter(viewAdapter);
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
            reportSponsorsItemsList.add(null);
        }

        String str = null;
        @Override
        protected String doInBackground(String... params) {
            int index = reportSponsorsItemsList.size();
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
        reportSponsorsItemsList.remove(reportSponsorsItemsList.size() -1);
        viewAdapter.notifyItemRemoved(reportSponsorsItemsList.size());

        if (str != null) {
            try {
                jsonArr = new JSONArray(str);
                for (int i = 0; i < 5; i++) {
                    JSONObject c = jsonArr.getJSONObject(i);
                    reportSponsorsItem = new ReportSponsorsItem(
                            c.getString("mcode"),
                            c.getString("name_t"),
                            c.getString("mdate"),
                            c.getString("pos_cur"),
                            c.getString("pos_cur2"),
                            c.getString("totpv"),
                            c.getString("sp_code"),
                            c.getString("sp_name"),
                            c.getString("lv_sp"),
                            ""
                    );
                    reportSponsorsItemsList.add(reportSponsorsItem);
                }
            } catch (JSONException e) {
                Log.e("ReportSponsorsLoadMore", e.toString());
            }
            viewAdapter.notifyDataSetChanged();
            viewAdapter.setLoaded();
            viewAdapter.setClickListener(this);
        }
    }
}
