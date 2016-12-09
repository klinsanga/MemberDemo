package th.co.omc.memberdemo.parse;

import android.content.Context;
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

import th.co.omc.memberdemo.model.ItemMember;
import th.co.omc.memberdemo.model.UpdateModel;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by teera-s on 11/30/2016 AD.
 */

public class SendUpdateToServer {
    public static final String TAG = SendUpdateToServer.class.getSimpleName();

    private String url;
    private ItemMember itemMember;
    private Context context;
    private List<UpdateModel> updateModelList = new ArrayList<UpdateModel>();
    public SendUpdateToServer(Context context, String url, List<UpdateModel> updateModels) {
        this.context = context;
        this.url = url;
        this.updateModelList = updateModels;
    }

    public SendUpdateToServer(String url) {
        this.url = url;
    }

    public void postUpdateRequest(final sendDataUpdateCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response update data : " + response);
                        JSONObject obj;
                        try {
                            obj = new JSONObject(response);
                            if (obj.getString("STATUS").equals("SUCCESS")) {
                                callback.onsendDataUpdateCallbackSuccess(obj.getString("STATUS"));
                            }
                        } catch (JSONException e) {
                            Log.e("JsonException", e.toString());
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
                params.put(EndPoints.KEY_USERNAME, MyApplication.getInstance().getPrefManager().getUser().getMemberCode());
                for (UpdateModel model : updateModelList) {
                    params.put("mobile", model.getMobile());
                    params.put("email", model.getEmail());
                    params.put("address", model.getAddress());
                    params.put("building", model.getBuilding());
                    params.put("village", model.getVillage());
                    params.put("soi", model.getSoi());
                    params.put("street", model.getStreet());
                    params.put("district", model.getSubDistrict());
                    params.put("amphor", model.getDistrict());
                    params.put("province", model.getProvince());
                    params.put("zip", model.getZip());
                    params.put("image", model.getImageName());
                }
                return params;
            }
        };
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    public interface sendDataUpdateCallback{
        void onFailed(String result);
        void onsendDataUpdateCallbackSuccess(String mcode);
    }

    public void requestUpdate(final requestUpdateCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response update data : " + response);
                        JSONObject obj;
                        JSONArray jsonArray;
                        JSONObject jsonObject;
                        try {
                            if (response.startsWith("[")) {
                                jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    itemMember = new ItemMember(
                                            jsonObject.getString("mcode"),
                                            jsonObject.getString("name_t") + " " + (jsonObject.isNull("name_e") ? "" : jsonObject.getString("name_e")),
                                            jsonObject.getString("mdate"),
                                            jsonObject.getString("birthday"),
                                            jsonObject.getString("sex"),
                                            jsonObject.getString("mobile"),
                                            jsonObject.getString("email"),
                                            jsonObject.getString("sp_name"),
                                            jsonObject.getString("upa_name"),
                                            jsonObject.getString("ewallet"),
                                            jsonObject.getString("profile_img"),
                                            jsonObject.isNull("pos_cur") ? "" : jsonObject.getJSONObject("pos_cur").getString("POS_NAME"),
                                            jsonObject.isNull("pos_cur2") ? "" : jsonObject.getJSONObject("pos_cur2").getString("POS_NAME"),
                                            jsonObject.isNull("pos_cur2") ? "" : jsonObject.getJSONObject("pos_cur2").getString("POS_IMAGE"),
                                            jsonObject.getString("ALL_POINT"),
                                            jsonObject.getString("AUTOSHIP_POINT"),
                                            jsonObject.getJSONObject("DOWNLINE").isNull("left") ? "" : jsonObject.getJSONObject("DOWNLINE").getJSONObject("left").getString("UPA_NAME"),
                                            jsonObject.getJSONObject("DOWNLINE").isNull("left") ? "" : jsonObject.getJSONObject("DOWNLINE").getJSONObject("left").getString("UPA_MCODE"),
                                            jsonObject.getJSONObject("DOWNLINE").isNull("left") ? "" : jsonObject.getJSONObject("DOWNLINE").getJSONObject("left").getString("ALL_POINT"),
                                            jsonObject.getJSONObject("DOWNLINE").isNull("left") ? "" : jsonObject.getJSONObject("DOWNLINE").getJSONObject("left").getString("PROFILE_IMG"),
                                            jsonObject.getJSONObject("DOWNLINE").isNull("right") ? "" : jsonObject.getJSONObject("DOWNLINE").getJSONObject("right").getString("UPA_NAME"),
                                            jsonObject.getJSONObject("DOWNLINE").isNull("right") ? "" : jsonObject.getJSONObject("DOWNLINE").getJSONObject("right").getString("UPA_MCODE"),
                                            jsonObject.getJSONObject("DOWNLINE").isNull("right") ? "" : jsonObject.getJSONObject("DOWNLINE").getJSONObject("right").getString("ALL_POINT"),
                                            jsonObject.getJSONObject("DOWNLINE").isNull("right") ? "" : jsonObject.getJSONObject("DOWNLINE").getJSONObject("right").getString("PROFILE_IMG"),
                                            jsonObject.getString("locationbase")
                                    );
                                }
                                MyApplication.getInstance().getPrefManager().storeUser(itemMember);
                                callback.onrequestCallbackSuccess();
                            } else {
                                obj = new JSONObject(response);
                                if (obj.getString("STATUS").equals("FAIL")) {
                                    Log.e(TAG, obj.getString("MESSAGE"));
                                    callback.onFailed(obj.getString("MESSAGE"));
                                }
                            }
                        } catch (JSONException e) {
                            Log.e("JsonException", e.toString());
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
                params.put(EndPoints.KEY_USERNAME, MyApplication.getInstance().getPrefManager().getUser().getMemberCode());
                params.put(EndPoints.KEY_API, EndPoints.API_KEYCODE);
                return params;
            }
        };
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    public interface requestUpdateCallback{
        void onFailed(String result);
        void onrequestCallbackSuccess();
    }
}
