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

import th.co.omc.memberdemo.model.shopping.Model;
import th.co.omc.memberdemo.model.shopping.ProvinceModel;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by teera-s on 11/16/2016 AD.
 */

public class ParseProvince {

    public static final String TAG = ParseProvince.class.getSimpleName();

    private String url;
    private ProvinceModel provinceModel;
    List<ProvinceModel> ProvinceModelArrayList = new ArrayList<ProvinceModel>();
    ArrayList<Model> ProvinceModelArray = new ArrayList<Model>();

    public ParseProvince(String url) {
        this.url = url;
    }

    public void postProvinceRequest(final parseProvinceCallback callback) {
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
                                jsonObject = new JSONObject(response);
                                obj = jsonObject.getJSONObject("province");
                                Log.e(TAG, obj.length() + "");
                                for (int i = 0 ; i < obj.length() + 1; i++) {
                                    String key = "" + i;

                                    Model model = new Model();
                                    if (key.equals("0")) {
                                        model.setItemName("Please select province");
                                        ProvinceModelArray.add(model);
                                    } else {
                                        JSONObject result = obj.getJSONObject(key);
                                        model.setItemName(result.getString("provinceName"));
                                        ProvinceModelArray.add(model);
                                        provinceModel = new ProvinceModel(
                                                result.getString("provinceId"),
                                                result.getString("provinceName"),
                                                result.getString("provinceNameEng")
                                        );
                                        ProvinceModelArrayList.add(provinceModel);
                                    }
                                }
                            }
                            callback.onParseProvinceCallbackSuccess(ProvinceModelArray, ProvinceModelArrayList);
                        } catch (JSONException e) {
                            Log.e(TAG, "json province parsing error: " + e.getMessage());
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

    public interface parseProvinceCallback {
        void onFailed(String result);

        void onParseProvinceCallbackSuccess(ArrayList<Model> provinceModels, List<ProvinceModel> modelList);
    }
}
