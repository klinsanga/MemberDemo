package th.co.omc.memberdemo.parse;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import th.co.omc.memberdemo.model.shopping.CartItem;
import th.co.omc.memberdemo.model.shopping.InformationModel;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by teera-s on 11/21/2016 AD.
 */

public class SendDataToServer {
    public static final String TAG = SendDataToServer.class.getSimpleName();
    private String url;
    List<CartItem> cartItemList = Collections.emptyList();
    private List<InformationModel> informationModelList = new ArrayList<InformationModel>();

    public SendDataToServer(String url, List<CartItem> productList, List<InformationModel> informationModelList) {
        this.url = url;
        this.cartItemList = productList;
        this.informationModelList = informationModelList;
    }

    public void postDataRequest(final sendDataCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response data server : " + response);
                        JSONObject obj;
                        try {
                            obj = new JSONObject(response);
                            if (obj.getString("STATUS").equals("SUCCESS")) {
                                callback.onsendDataCallbackSuccess(obj.getString("MESSAGE"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put(EndPoints.KEY_API, EndPoints.API_KEYCODE);

                for (InformationModel model : informationModelList) {
                    if (model.getSend().equals("รับของด้วยตัวเอง")) {
                        params.put("uid", model.getUid());
                        params.put("mcode", model.getMcode());
                        params.put("locationbase", model.getLocationbase());
                        params.put("total", model.getTotalPrice());
                        params.put("tot_pv", model.getTotalPv());
                        params.put("tot_weight", model.getTotalWeight());
                        params.put("sa_type", model.getSaTypeId());
                        params.put("pa_type", model.getPahymentTypeId());
                        params.put("send", model.getSendId());
                        params.put("inv_code", model.getBranch());
                    } else {
                        params.put("uid", model.getUid());
                        params.put("mcode", model.getMcode());
                        params.put("locationbase", model.getLocationbase());
                        params.put("total", model.getTotalPrice());
                        params.put("tot_pv", model.getTotalPv());
                        params.put("tot_weight", model.getTotalWeight());
                        params.put("sa_type", model.getSaTypeId());
                        params.put("pa_type", model.getPahymentTypeId());
                        params.put("send", model.getSendId());
                        params.put("caddress", model.getcAddress());
                        params.put("cdistrictId", model.getcDistrictId());
                        params.put("camphurId", model.getcAmphurId());
                        params.put("cprovinceId", model.getcProvinceId());
                        params.put("czip", model.getcZip());
                    }
                }

                CartItem cartItem = null;
                for (int i = 0; i < cartItemList.size(); i++) {
                    cartItem = cartItemList.get(i);
                    int itemQty = cartItem.getQuantity();
                    String itemPrice = cartItem.getProduct().getPrice() + "";
                    params.put("pcode["+ i +"]", cartItem.getProduct().getProductCode());
                    params.put("price["+ i +"]", cartItem.getProduct().getPrice() + "");
                    params.put("pv["+ i +"]", cartItem.getProduct().getProductPV());
                    params.put("weight["+ i +"]", cartItem.getProduct().getProductWeight());
                    params.put("qty["+ i +"]", cartItem.getQuantity() + "");
                    params.put("amt["+ i +"]", (itemQty * Integer.parseInt(itemPrice)) + "");
                }
                return params;
            }

        };
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    public interface sendDataCallback{
        void onFailed(String result);
        void onsendDataCallbackSuccess(String msg);
    }
}
