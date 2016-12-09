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

import th.co.omc.memberdemo.model.shopping.AddressModel;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by teera-s on 11/10/2016 AD.
 */

public class ParseMemberAddress {

    public static final String TAG = ParseMemberAddress.class.getSimpleName();

    private String url;
    private String id;
    AddressModel addressModel;
    List<AddressModel> addressModelList = new ArrayList<AddressModel>();
    public ParseMemberAddress(String url, String id) {
        this.url = url;
        this.id = id;
    }

    public void postMemberAddress(final ParseMemberAddressCallback callback) {
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
                                    addressModel = new AddressModel(
                                            jsonObject.getString("mcode"),
                                            jsonObject.getString("name_t") + " " + jsonObject.getString("name_e"),
                                            jsonObject.getString("cmobile"),
                                            jsonObject.getString("cemail"),
                                            jsonObject.getString("czip"),
                                            jsonObject.getString("caddress"),
                                            jsonObject.getString("cbuilding"),
                                            jsonObject.getString("cvillage"),
                                            jsonObject.getString("cstreet"),
                                            jsonObject.getString("csoi"),
                                            jsonObject.getString("cdistrictId"),
                                            jsonObject.getString("camphurId"),
                                            jsonObject.getString("cprovinceId"),
                                            jsonObject.getString("locationbase")
                                    );
                                    addressModelList.add(addressModel);
                                }
                                MyApplication.getInstance().getPrefManager().storeSendingAddress(addressModel);
                                callback.onParseMemberAddressSuccess(addressModelList);
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
                params.put(EndPoints.KEY_USERNAME, id);
                params.put(EndPoints.KEY_API, EndPoints.API_KEYCODE);
                return params;
            }
        };
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    public interface ParseMemberAddressCallback{
        void onFailed(String result);
        void onParseMemberAddressSuccess(List<AddressModel> addressModelList);
    }
}
