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

import th.co.omc.memberdemo.model.Order2MemberItem;
import th.co.omc.memberdemo.model.OrderHistoryItem;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by teera-s on 10/5/2016 AD.
 */

public class ParseOrder {
    public static final String TAG = ParseOrder.class.getSimpleName();

    private String url;
    private String month;

    OrderHistoryItem orderHistoryItem;
    List<OrderHistoryItem> orderHistoryItemList = new ArrayList<OrderHistoryItem>();

    Order2MemberItem order2MemberItem;
    List<Order2MemberItem> order2MemberItemList = new ArrayList<Order2MemberItem>();

    public ParseOrder(String url, String month) {
        this.url = url;
        this.month = month;
    }

    public void postOrderHistoryRequest(final OrderHistoryCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response order history : " + response);
                        JSONObject obj;
                        JSONArray jsonArray;
                        JSONObject jsonObject;
                        try {
                            if (response.startsWith("[")) {
                                jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    orderHistoryItem = new OrderHistoryItem(
                                            jsonObject.getString("date"),
                                            jsonObject.getString("sano"),
                                            //jsonObject.getString("ability"),
                                            (jsonObject.isNull("ability") ? "" : jsonObject.getString("ability")),
                                            jsonObject.getString("tot_pv"),
                                            jsonObject.getString("total"),
                                            jsonObject.getString("uid"),
                                            jsonObject.getBoolean("sendsend"),
                                            jsonObject.getBoolean("sender"),
                                            jsonObject.getBoolean("receive"),
                                            jsonObject.getString("remark"),
                                            jsonObject.getString("lid")
                                    );
                                    orderHistoryItemList.add(orderHistoryItem);
                                }
                                callback.onParseOrderHistorySuccess(orderHistoryItemList);
                            } else {
                                obj = new JSONObject(response);
                                if (obj.getString("STATUS").equals("FAIL")) {
                                    Log.e(TAG, obj.getString("MESSAGE"));
                                    callback.onFailed(obj.getString("MESSAGE"));
                                }
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "json order history parsing error: " + e.getMessage());
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

    public interface OrderHistoryCallback{
        void onFailed(String result);
        void onParseOrderHistorySuccess(List<OrderHistoryItem> list);
    }

    public void postOrder2MemberRequest(final Order2MemberCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response order to member : " + response);
                        JSONObject obj;
                        JSONArray jsonArray;
                        JSONObject jsonObject;
                        try {
                            if (response.startsWith("[")) {
                                jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    order2MemberItem = new Order2MemberItem(
                                            jsonObject.getString("date"),
                                            jsonObject.getString("bill"),
                                            jsonObject.getString("mcode"),
                                            jsonObject.getString("name"),
                                            (jsonObject.isNull("ability") ? "" : jsonObject.getString("ability")),
                                            //jsonObject.getString("ability"),
                                            jsonObject.getString("tot_pv"),
                                            jsonObject.getString("total"),
                                            jsonObject.getBoolean("sendsend"),
                                            jsonObject.getBoolean("sender"),
                                            jsonObject.getBoolean("receive"),
                                            jsonObject.getString("remark")
                                    );
                                    order2MemberItemList.add(order2MemberItem);
                                }
                                callback.onParseOrder2MemberSuccess(order2MemberItemList);
                            } else {
                                obj = new JSONObject(response);
                                if (obj.getString("STATUS").equals("FAIL")) {
                                    Log.e(TAG, obj.getString("MESSAGE"));
                                    callback.onFailed(obj.getString("MESSAGE"));
                                }
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "json order to member parsing error: " + e.getMessage());
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

    public interface Order2MemberCallback{
        void onFailed(String result);
        void onParseOrder2MemberSuccess(List<Order2MemberItem> list);
    }
}
