package th.co.omc.memberdemo.parse;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import th.co.omc.memberdemo.model.WeekItem;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by teera-s on 10/4/2016 AD.
 */

public class ParseWeek {

    public static final String TAG = ParseWeek.class.getSimpleName();

    private String url;
    private String month;
    private WeekItem weekItem;
    private List<WeekItem> weekItemArrayList = new ArrayList<WeekItem>();

    public ParseWeek(String url, String month) {
        this.url = url;
        this.month = month;
    }

    public void postWeekRequest(final VolleyCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response upline : " + response);
                        JSONObject obj;
                        JSONArray jsonArray;
                        JSONObject jsonObject;
                        try {
                            if (response.startsWith("[")) {
                                jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    weekItem = new WeekItem(
                                            jsonObject.getString("rcode"),
                                            jsonObject.getString("start_date"),
                                            jsonObject.getString("end_date"),
                                            jsonObject.getString("pv"),
                                            jsonObject.getString("ecom"),
                                            jsonObject.getString("onetime"),
                                            jsonObject.getString("thiscom"),
                                            jsonObject.getString("pvh"),
                                            jsonObject.getString("totaly"),
                                            jsonObject.getString("tax"),
                                            jsonObject.getString("oon"),
                                            jsonObject.getString("ttttt"),
                                            jsonObject.getString("paydate")
                                    );
                                    weekItemArrayList.add(weekItem);
                                }

                                callback.onParseWeekSuccess(weekItemArrayList);
                            } else {
                                obj = new JSONObject(response);
                                if (obj.getString("STATUS").equals("FAIL")) {
                                    Log.e(TAG, obj.getString("MESSAGE"));
                                    callback.onFailed(obj.getString("MESSAGE"));
                                }
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "json upline parsing error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        Log.e(TAG, "Volley error: " + error.getMessage() + ", code: " + networkResponse);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(EndPoints.KEY_USERNAME, MyApplication.getInstance().getPrefManager().getUsername());
                params.put(EndPoints.KEY_API, EndPoints.API_KEYCODE);
                params.put(EndPoints.KEY_MONTH, month);
                return params;
            }

        };
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    public interface VolleyCallback{
        void onFailed(String result);
        void onParseWeekSuccess(List<WeekItem> list);
    }
}
