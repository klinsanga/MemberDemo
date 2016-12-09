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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import th.co.omc.memberdemo.model.shopping.Product;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by teera-s on 11/16/2016 AD.
 */

public class ParseProduct {
    public static final String TAG = ParseProduct.class.getSimpleName();

    Product product;
    List<Product> productList = new ArrayList<Product>();

    private String url;
    private String locationbase;
    public ParseProduct(String url, String locationbase) {
        this.url = url;
        this.locationbase = locationbase;
    }

    public void postProductRequest(final parseProductCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response product : " + response);
                        JSONObject obj;
                        JSONArray jsonArray;
                        JSONObject jsonObject;
                        try {
                            if (response.startsWith("[")) {
                                jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    product = new Product(
                                            jsonObject.getString("pcode"),
                                            jsonObject.getString("pdesc"),
                                            BigDecimal.valueOf(Long.parseLong(jsonObject.getString("price"))),
                                            jsonObject.getString("pv"),
                                            jsonObject.getString("qty"),
                                            jsonObject.getString("weight"),
                                            jsonObject.getString("picture")
                                    );
                                    productList.add(product);
                                }
                                callback.onParseProductCallbackSuccess(productList);
                            } else {
                                obj = new JSONObject(response);
                                if (obj.getString("STATUS").equals("FAIL")) {
                                    Log.e(TAG, obj.getString("MESSAGE"));
                                    callback.onFailed(obj.getString("MESSAGE"));
                                }
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "json product parsing error: " + e.getMessage());
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
                params.put(EndPoints.KEY_API, EndPoints.API_KEYCODE);
                params.put("locationbase", locationbase);
                return params;
            }

        };
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    public interface parseProductCallback{
        void onFailed(String result);
        void onParseProductCallbackSuccess(List<Product> list);
    }

    public void postPromotionRequest(final parsePromotionCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response promotion : " + response);
                        JSONObject obj;
                        JSONArray jsonArray;
                        JSONObject jsonObject;
                        try {
                            if (response.startsWith("[")) {
                                jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    product = new Product(
                                            jsonObject.getString("pcode"),
                                            jsonObject.getString("pdesc"),
                                            BigDecimal.valueOf(Long.parseLong(jsonObject.getString("price"))),
                                            jsonObject.getString("pv"),
                                            jsonObject.getString("qty"),
                                            jsonObject.getString("weight"),
                                            jsonObject.getString("picture")
                                    );
                                    productList.add(product);
                                }
                                callback.onParsePromotionCallbackSuccess(productList);
                            } else {
                                obj = new JSONObject(response);
                                if (obj.getString("STATUS").equals("FAIL")) {
                                    Log.e(TAG, obj.getString("MESSAGE"));
                                    callback.onFailed(obj.getString("MESSAGE"));
                                }
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "json promotion parsing error: " + e.getMessage());
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
                params.put(EndPoints.KEY_API, EndPoints.API_KEYCODE);
                params.put("locationbase", locationbase);
                return params;
            }

        };
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    public interface parsePromotionCallback{
        void onFailed(String result);
        void onParsePromotionCallbackSuccess(List<Product> list);
    }
}
