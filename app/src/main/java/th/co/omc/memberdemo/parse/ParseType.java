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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import th.co.omc.memberdemo.model.shopping.Model;
import th.co.omc.memberdemo.model.shopping.ModelTypeWithKey;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by teera-s on 11/16/2016 AD.
 */

public class ParseType {
    public static final String TAG = ParseProduct.class.getSimpleName();

    private String url;
    ModelTypeWithKey modelTypeWithKey;
    List<ModelTypeWithKey> modelTypeWithKeyList = new ArrayList<ModelTypeWithKey>();
    ArrayList<Model> OrderModelArrayList = new ArrayList<Model>();

    public ParseType(String url) {
        this.url = url;
    }

    public void postOrderTypeRequest(final parseOrderTypeCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response product : " + response);
                        JSONObject obj;
                        JSONArray jsonArray = new JSONArray();
                        JSONObject jsonObject;
                        try {
                            if (response.startsWith("{")) {
                                HashMap<String,String> hm = new HashMap<String,String>();
                                jsonObject = new JSONObject(response);
                                Iterator iterator = jsonObject.keys();
                                ModelTypeWithKey modelTypeWithKey0 = new ModelTypeWithKey( "0" );
                                modelTypeWithKeyList.add(modelTypeWithKey0);
                                while (iterator.hasNext()) {
                                    String key = (String) iterator.next();
                                    if (key.equals("billtype")) {
                                        obj = jsonObject.getJSONObject("billtype");
                                        String a = obj.getString("A");
                                        String b = obj.getString("B");
                                        String c = obj.getString("C");
                                        String q = obj.getString("Q");
                                        String h = obj.getString("H");

                                        Model model0 = new Model();
                                        model0.setItemName("Please select order type");
                                        OrderModelArrayList.add(model0);

                                        for (int i = 0; i < obj.length(); i++) {
                                            hm.put(obj.names().getString(i), obj.getString(obj.names().getString(i)));

                                        }

                                        Map<String, String> treeMap = new TreeMap<String, String>(hm);
                                        for (String str : treeMap.keySet()) {
                                            Log.e("sort array", str);
                                            modelTypeWithKey = new ModelTypeWithKey( str );
                                            modelTypeWithKeyList.add(modelTypeWithKey);
                                        }


                                        Model modelA = new Model();
                                        modelA.setItemName(a);
                                        OrderModelArrayList.add(modelA);
                                        Model modelB = new Model();
                                        modelB.setItemName(b);
                                        OrderModelArrayList.add(modelB);
                                        Model modelC = new Model();
                                        modelC.setItemName(c);
                                        OrderModelArrayList.add(modelC);
                                        Model modelH = new Model();
                                        modelH.setItemName(h);
                                        OrderModelArrayList.add(modelH);
                                        Model modelQ = new Model();
                                        modelQ.setItemName(q);
                                        OrderModelArrayList.add(modelQ);
                                        callback.onParseOrderTypeCallbackSuccess(OrderModelArrayList, modelTypeWithKeyList);
                                    } else {
                                        obj = jsonObject.getJSONObject("payment");
                                        /*String a = obj.getString("1");
                                        String b = obj.getString("2");
                                        String c = obj.getString("3");
                                        String q = obj.getString("4");
                                        String h = obj.getString("5");*/

                                        Model model0 = new Model();
                                        model0.setItemName("Please select pay type");
                                        OrderModelArrayList.add(model0);

                                        for (int i = 0; i < obj.length(); i++) {
                                            hm.put(obj.names().getString(i), obj.getString(obj.names().getString(i)));
                                            Model model = new Model();
                                            model.setItemName(obj.getString(obj.names().getString(i)));
                                            OrderModelArrayList.add(model);
                                        }

                                        Map<String, String> treeMap = new TreeMap<String, String>(hm);
                                        for (String str : treeMap.keySet()) {
                                            Log.e("sort array", str);
                                            modelTypeWithKey = new ModelTypeWithKey( str );
                                            modelTypeWithKeyList.add(modelTypeWithKey);
                                        }

                                        /*Model model = new Model();
                                        model.setItemName("Please select pay type");
                                        OrderModelArrayList.add(model);

                                        Model modelA = new Model();
                                        modelA.setItemName(a);
                                        OrderModelArrayList.add(modelA);
                                        Model modelB = new Model();
                                        modelB.setItemName(b);
                                        OrderModelArrayList.add(modelB);
                                        Model modelC = new Model();
                                        modelC.setItemName(c);
                                        OrderModelArrayList.add(modelC);
                                        Model modelQ = new Model();
                                        modelQ.setItemName(q);
                                        OrderModelArrayList.add(modelQ);
                                        Model modelH = new Model();
                                        modelH.setItemName(h);
                                        OrderModelArrayList.add(modelH);*/
                                        callback.onParsePaymentTypeCallbackSuccess(OrderModelArrayList, modelTypeWithKeyList);
                                    }
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

    public interface parseOrderTypeCallback {
        void onFailed(String result);

        void onParseOrderTypeCallbackSuccess(ArrayList<Model> OrderModelArrayList, List<ModelTypeWithKey> modelKey);

        void onParsePaymentTypeCallbackSuccess(ArrayList<Model> PayModelArrayList, List<ModelTypeWithKey> modelKey);
    }
}
