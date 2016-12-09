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

import th.co.omc.memberdemo.model.AllsaleItem;
import th.co.omc.memberdemo.model.FaststartItem;
import th.co.omc.memberdemo.model.TotalItem;
import th.co.omc.memberdemo.model.TotalMonthItem;
import th.co.omc.memberdemo.model.WeakstrongItem;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by teera-s on 9/28/2016 AD.
 */

public class ParseCommission {

    public static final String TAG = ParseCommission.class.getSimpleName();

    private String url;
    private String month;
    private FaststartItem faststartItem;
    private List<FaststartItem> faststartItemList = new ArrayList<FaststartItem>();

    private TotalItem totalItem;
    private List<TotalItem> totalItemList = new ArrayList<TotalItem>();

    private AllsaleItem allsaleItem;
    private List<AllsaleItem> allsaleItemList = new ArrayList<AllsaleItem>();

    private WeakstrongItem weakstrongItem;
    private List<WeakstrongItem> weakstrongItemList = new ArrayList<WeakstrongItem>();

    private TotalMonthItem totalMonthItem;
    private List<TotalMonthItem> totalMonthItemList = new ArrayList<TotalMonthItem>();

    public ParseCommission(String url, String month) {
        this.url = url;
        this.month = month;
    }

    public void postFaststartRequest(final VolleyCallback callback) {
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
                                    faststartItem = new FaststartItem(
                                            jsonObject.getString("date"),
                                            jsonObject.getString("mcode"),
                                            jsonObject.getString("name"),
                                            jsonObject.getString("total")
                                    );
                                    faststartItemList.add(faststartItem);
                                }

                                callback.onParseFaststartSuccess(faststartItemList);
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

    public void postTotalIncomeRequest(final VolleyCallback callback) {
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
                                    totalItem = new TotalItem(
                                            jsonObject.getString("date"),
                                            jsonObject.getString("ambonus"),
                                            jsonObject.getString("bmbonus"),
                                            jsonObject.getString("dmbonus"),
                                            jsonObject.getString("fmbonus"),
                                            jsonObject.getString("alltotal"),
                                            jsonObject.getString("ato"),
                                            jsonObject.getString("total")
                                    );
                                    totalItemList.add(totalItem);
                                }

                                callback.onParseTotalIncomeSuccess(totalItemList);
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
        void onParseFaststartSuccess(List<FaststartItem> list);
        void onParseTotalIncomeSuccess(List<TotalItem> list);
    }

    public void postAllsaleRequest(final allSaleCallback callback) {
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
                                    allsaleItem = new AllsaleItem(
                                            jsonObject.getString("start_date"),
                                            jsonObject.getString("end_date"),
                                            jsonObject.getString("mcode"),
                                            jsonObject.getString("total")
                                    );
                                    allsaleItemList.add(allsaleItem);
                                }

                                callback.onParseAllsaleSuccess(allsaleItemList);
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

    public interface allSaleCallback{
        void onFailed(String result);
        void onParseAllsaleSuccess(List<AllsaleItem> list);
    }

    public void postWeakStrongRequest(final WeakStrongCallback callback) {
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
                                    weakstrongItem = new WeakstrongItem(
                                            jsonObject.getString("date"),
                                            jsonObject.getString("pcrry_l"),
                                            jsonObject.getString("pcrry_c"),
                                            jsonObject.getString("ro_l"),
                                            jsonObject.getString("ro_c"),
                                            jsonObject.getString("pcrry_ll"),
                                            jsonObject.getString("pcrry_cc"),
                                            jsonObject.getString("balance"),
                                            jsonObject.getString("carry_l"),
                                            jsonObject.getString("carry_c"),
                                            jsonObject.getString("total")
                                    );
                                    weakstrongItemList.add(weakstrongItem);
                                }

                                callback.onParseWeakStrongSuccess(weakstrongItemList);
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

    public interface WeakStrongCallback{
        void onFailed(String result);
        void onParseWeakStrongSuccess(List<WeakstrongItem> list);
    }

    public void postIncomeMonthRequest(final totalIncomeMonthCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response : " + response);
                        JSONObject obj;
                        JSONArray jsonArray;
                        JSONObject jsonObject;
                        try {
                            if (response.startsWith("[")) {
                                jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    totalMonthItem = new TotalMonthItem(
                                            jsonObject.getString("rcode"),
                                            jsonObject.getString("start_date"),
                                            jsonObject.getString("end_date"),
                                            jsonObject.getString("mmbonus"),
                                            jsonObject.getString("embonus"),
                                            jsonObject.getString("total")
                                    );
                                    totalMonthItemList.add(totalMonthItem);
                                }

                                callback.onParseIncomeMonthSuccess(totalMonthItemList);
                            } else {
                                obj = new JSONObject(response);
                                if (obj.getString("STATUS").equals("FAIL")) {
                                    Log.e(TAG, obj.getString("MESSAGE"));
                                    callback.onFailed(obj.getString("MESSAGE"));
                                }
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "json parsing error: " + e.getMessage());
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

    public interface totalIncomeMonthCallback{
        void onFailed(String result);
        void onParseIncomeMonthSuccess(List<TotalMonthItem> list);
    }
}
