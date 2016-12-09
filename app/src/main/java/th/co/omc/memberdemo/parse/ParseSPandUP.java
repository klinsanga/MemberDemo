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

import java.util.HashMap;
import java.util.Map;

import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by teera-s on 11/25/2016 AD.
 */

public class ParseSPandUP {
    public static final String TAG = ParseSPandUP.class.getSimpleName();

    private String url;
    private String spCode;
    private String upCode;
    private String spName;
    private String upName;
    private boolean upLeft;
    private boolean upRight;
    public ParseSPandUP(String url, String spCode, String upCode) {
        this.url = url;
        this.spCode = spCode;
        this.upCode = upCode;
    }

    public void postSponsorsRequest(final parseSponsorsCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response sponsors : " + response);
                        JSONObject obj;
                        JSONArray jsonArray;
                        JSONObject jsonObject;
                        try {
                            if (response.startsWith("[")) {
                                jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    spName = jsonObject.getString("sp_name");
                                }
                                callback.onParseSponsorsCallbackSuccess(spName);
                            } else {
                                obj = new JSONObject(response);
                                if (obj.getString("STATUS").equals("FAIL")) {
                                    Log.e(TAG, obj.getString("MESSAGE"));
                                    callback.onFailed(obj.getString("MESSAGE"));
                                }
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "json sponsors parsing error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        Log.e(TAG, "Volley error: " + error.getMessage() + ", code: " + networkResponse);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(EndPoints.KEY_LOCATIONBASE, MyApplication.getInstance().getPrefManager().getUser().getLocationbase());
                params.put(EndPoints.KEY_API, EndPoints.API_KEYCODE);
                params.put("sp_code", spCode);
                return params;
            }

        };
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    public interface parseSponsorsCallback {
        void onFailed(String result);

        void onParseSponsorsCallbackSuccess(String sponsorsname);
    }

    public void postUPARequest(final parseUPACallback callback) {
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
                                    upName = jsonObject.getString("upa_name");
                                    upLeft = jsonObject.getBoolean("left");
                                    upRight = jsonObject.getBoolean("right");
                                }
                                callback.onParseUPACallbackSuccess(upName, upLeft, upRight);
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
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(EndPoints.KEY_API, EndPoints.API_KEYCODE);
                params.put(EndPoints.KEY_LOCATIONBASE, MyApplication.getInstance().getPrefManager().getUser().getLocationbase());
                params.put("sp_code", spCode);
                params.put("upa_code", upCode);
                return params;
            }

        };
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    public interface parseUPACallback {
        void onFailed(String result);

        void onParseUPACallbackSuccess(String uplinename, boolean left, boolean right);
    }
}
