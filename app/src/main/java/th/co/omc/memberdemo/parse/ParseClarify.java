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

import th.co.omc.memberdemo.model.ClarifyHoldItem;
import th.co.omc.memberdemo.model.ClarifyItem;
import th.co.omc.memberdemo.model.ClarifyRecieveItem;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by teera-s on 10/5/2016 AD.
 */

public class ParseClarify {
    public static final String TAG = ParseClarify.class.getSimpleName();

    private String url;
    private String month;

    ClarifyItem clarifyItem;
    List<ClarifyItem> clarifyItemList = new ArrayList<ClarifyItem>();

    ClarifyHoldItem clarifyHoldItem;
    List<ClarifyHoldItem> clarifyHoldItemList = new ArrayList<ClarifyHoldItem>();

    ClarifyRecieveItem clarifyRecieveItem;
    List<ClarifyRecieveItem> clarifyRecieveItemList = new ArrayList<ClarifyRecieveItem>();

    public ParseClarify(String url, String month) {
        this.url = url;
        this.month = month;
    }

    public void postClarifyRequest(final ClarifyCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response clarify : " + response);
                        JSONObject obj;
                        JSONArray jsonArray;
                        JSONObject jsonObject;
                        try {
                            if (response.startsWith("[")) {
                                jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    clarifyItem = new ClarifyItem(
                                            jsonObject.getString("bid"),
                                            jsonObject.getString("sano"),
                                            jsonObject.getString("sadate"),
                                            jsonObject.getString("hdate"),
                                            jsonObject.getString("price"),
                                            jsonObject.getString("pv"),
                                            jsonObject.getString("qty")
                                    );
                                    clarifyItemList.add(clarifyItem);
                                }
                                callback.onParseClarifySuccess(clarifyItemList);
                            } else {
                                obj = new JSONObject(response);
                                if (obj.getString("STATUS").equals("FAIL")) {
                                    Log.e(TAG, obj.getString("MESSAGE"));
                                    callback.onFailed(obj.getString("MESSAGE"));
                                }
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "json clarify parsing error: " + e.getMessage());
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

    public interface ClarifyCallback{
        void onFailed(String result);
        void onParseClarifySuccess(List<ClarifyItem> list);
    }

    public void postClarifyHoldRequest(final ClarifyHoldCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response hold : " + response);
                        JSONObject obj;
                        JSONArray jsonArray;
                        JSONObject jsonObject;
                        try {
                            if (response.startsWith("[")) {
                                jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    clarifyHoldItem = new ClarifyHoldItem(
                                            jsonObject.getString("hono"),
                                            jsonObject.getString("preserve"),
                                            jsonObject.getString("smcode"),
                                            jsonObject.getString("name_t"),
                                            jsonObject.getString("sadate"),
                                            jsonObject.getString("tot_pv"),
                                            jsonObject.getString("total"),
                                            jsonObject.getString("ssano")
                                    );
                                    clarifyHoldItemList.add(clarifyHoldItem);
                                }
                                callback.onParseClarifyHoldSuccess(clarifyHoldItemList);
                            } else {
                                obj = new JSONObject(response);
                                if (obj.getString("STATUS").equals("FAIL")) {
                                    Log.e(TAG, obj.getString("MESSAGE"));
                                    callback.onFailed(obj.getString("MESSAGE"));
                                }
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "json hold parsing error: " + e.getMessage());
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

    public interface ClarifyHoldCallback{
        void onFailed(String result);
        void onParseClarifyHoldSuccess(List<ClarifyHoldItem> list);
    }

    public void postClarifyRecieveRequest(final ClarifyRecieveCallback callback) {
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
                                    clarifyRecieveItem = new ClarifyRecieveItem(
                                            jsonObject.getString("hono"),
                                            jsonObject.getString("preserve"),
                                            jsonObject.getString("sadate"),
                                            jsonObject.getString("tot_pv"),
                                            jsonObject.getString("total"),
                                            jsonObject.getString("ssano"),
                                            jsonObject.getString("smcode1"),
                                            jsonObject.getString("sname_t"),
                                            jsonObject.getString("uid")
                                    );
                                    clarifyRecieveItemList.add(clarifyRecieveItem);
                                }
                                callback.onParseClarifyRecieveSuccess(clarifyRecieveItemList);
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

    public interface ClarifyRecieveCallback{
        void onFailed(String result);
        void onParseClarifyRecieveSuccess(List<ClarifyRecieveItem> list);
    }
}
