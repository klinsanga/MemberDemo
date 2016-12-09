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

import th.co.omc.memberdemo.model.shopping.InventModel;
import th.co.omc.memberdemo.model.shopping.Model;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by teera-s on 11/17/2016 AD.
 */

public class ParseInvent {
    public static final String TAG = ParseInvent.class.getSimpleName();

    private String url;
    Model model;
    InventModel inventModel;
    ArrayList<Model> inventModelArray = new ArrayList<Model>();
    List<InventModel> inventModelList = new ArrayList<InventModel>();

    public ParseInvent(String url) {
        this.url = url;
    }

    public void postInventRequest(final parseInventCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response product : " + response);
                        JSONObject obj;
                        JSONArray jsonArray = new JSONArray();
                        JSONObject jsonObject;
                        try {
                                model = new Model();
                                model.setItemName("Please select branch");
                                inventModelArray.add(model);
                                inventModel = new InventModel(
                                        "0",
                                        "0"
                                );
                            if (response.startsWith("[")) {
                                jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    model = new Model();
                                    model.setItemName(jsonObject.getString("inv_desc") + "(" + jsonObject.getString("inv_code") +")");
                                    inventModelArray.add(model);
                                    inventModel = new InventModel(
                                            jsonObject.getString("inv_code"),
                                            jsonObject.getString("inv_desc")
                                    );
                                    inventModelList.add(inventModel);
                                }
                            }
                            callback.onParseInventCallbackSuccess(inventModelArray, inventModelList);
                        } catch (JSONException e) {
                            Log.e(TAG, "json invent parsing error: " + e.getMessage());
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

    public interface parseInventCallback {
        void onFailed(String result);

        void onParseInventCallbackSuccess(ArrayList<Model> inventModels, List<InventModel> inventModelsList);
    }
}
