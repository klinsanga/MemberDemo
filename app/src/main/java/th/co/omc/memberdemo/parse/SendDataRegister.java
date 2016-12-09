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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import th.co.omc.memberdemo.model.signup.SignupDataModel;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by teera-s on 11/25/2016 AD.
 */

public class SendDataRegister {
    public static final String TAG = SendDataRegister.class.getSimpleName();

    private String url;
    private List<SignupDataModel> signupDataModelList = new ArrayList<SignupDataModel>();
    public SendDataRegister(String url, List<SignupDataModel> signupDataModels) {
        this.url = url;
        this.signupDataModelList = signupDataModels;
    }

    public void postDataRegisterRequest(final sendDataRegisterCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response register data : " + response);
                        JSONObject obj;
                        try {
                            obj = new JSONObject(response);
                            if (obj.getString("STATUS").equals("SUCCESS")) {
                                callback.onsendDataRegisterCallbackSuccess(obj.getString("STATUS"));
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

                for (SignupDataModel model : signupDataModelList) {
                    params.put("sp_code", model.getSponsorsID());
                    params.put("sp_name", model.getSponsorsName());
                    params.put("upa_code", model.getUplineID());
                    params.put("upa_name", model.getUplineName());
                    params.put("upa_side", model.getSideid());
                    params.put("prefix_name", model.getPrefixID());
                    params.put("name", model.getName());
                    params.put("gender", model.getGenderID());
                    params.put("birthday", model.getBirthDate());
                    params.put("nationality", model.getNationality());
                    params.put("identification", model.getIdentification());
                    params.put("mobile", model.getMobile());
                    params.put("email", model.getEmail());
                    params.put("address", model.getAddress());
                    params.put("province", model.getProvince());
                    params.put("district", model.getDistrict());
                    params.put("sub_district", model.getSubDistrict());
                    params.put("zip", model.getZip());
                }
                return params;
            }

        };
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    public interface sendDataRegisterCallback{
        void onFailed(String result);
        void onsendDataRegisterCallbackSuccess(String mcode);
    }
}
