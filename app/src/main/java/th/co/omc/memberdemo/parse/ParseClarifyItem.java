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
 * Created by teera-s on 11/21/2016 AD.
 */

public class ParseClarifyItem {
    public static final String TAG = ParseClarifyItem.class.getSimpleName();

    private String url, bid;
    Product product;
    List<Product> productList = new ArrayList<Product>();

    public ParseClarifyItem(String url, String bid) {
        this.url = url;
        this.bid = bid;
    }

    public void postClarifyItem(final parseClarifyCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response product : " + response);
                        JSONObject obj;
                        JSONArray jsonArray = new JSONArray();
                        JSONObject jsonObject;
                        try {
                            if (response.startsWith("[")) {
                                jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    product = new Product(
                                            jsonObject.getString("pcode"),
                                            jsonObject.getString("pdesc"),
                                            BigDecimal.valueOf(jsonObject.getLong("price")),
                                            jsonObject.getString("pv"),
                                            jsonObject.getString("qty"),
                                            jsonObject.getString("id"),
                                            jsonObject.getString("picture")
                                    );
                                    productList.add(product);
                                }
                            }
                            callback.onParseClarifyItemCallbackSuccess(productList);
                        } catch (JSONException e) {
                            Log.e(TAG, "json clarify product parsing error: " + e.getMessage());
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
                params.put(EndPoints.KEY_USERNAME, MyApplication.getInstance().getPrefManager().getUser().getMemberCode());
                params.put(EndPoints.KEY_API, EndPoints.API_KEYCODE);
                params.put("bid", bid);
                return params;
            }

        };
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    public interface parseClarifyCallback {
        void onFailed(String result);
        void onParseClarifyItemCallbackSuccess(List<Product> list);
    }
}
