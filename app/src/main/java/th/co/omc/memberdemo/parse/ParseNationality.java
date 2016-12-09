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
import java.util.Map;

import th.co.omc.memberdemo.model.shopping.Model;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by teera-s on 11/24/2016 AD.
 */

public class ParseNationality {
    public static final String TAG = ParseNationality.class.getSimpleName();

    Model model;
    private String url;
    ArrayList<Model> nationalityModelArrayList = new ArrayList<Model>();
    public ParseNationality(String url) {
        this.url = url;
    }

    public void postNationalityRequest(final parseNationalityCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response nationality : " + response);
                        JSONObject obj;
                        JSONArray jsonArray = new JSONArray();
                        JSONObject jsonObject;
                        try {
                            model = new Model();
                            model.setItemName("Please select nationality");
                            nationalityModelArrayList.add(model);
                            if (response.startsWith("[")) {
                                jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    model = new Model();
                                    model.setItemName(jsonObject.getString("nation"));
                                    nationalityModelArrayList.add(model);
                                }
                            }
                            callback.onParseNationalityCallbackSuccess(nationalityModelArrayList);
                        } catch (JSONException e) {
                            Log.e(TAG, "json nationality parsing error: " + e.getMessage());
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
                return params;
            }

        };
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }
    public interface parseNationalityCallback {
        void onFailed(String result);
        void onParseNationalityCallbackSuccess(ArrayList<Model> nation);
    }
}
