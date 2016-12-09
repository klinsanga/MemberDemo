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
import th.co.omc.memberdemo.model.shopping.SubDistrictModel;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by teera-s on 11/16/2016 AD.
 */

public class ParseSubDistrict {

    public static final String TAG = ParseSubDistrict.class.getSimpleName();

    Model model;
    private String url, subdistrictid;
    private SubDistrictModel subdistrictModel;
    List<SubDistrictModel> subdistrictModelList = new ArrayList<SubDistrictModel>();
    ArrayList<Model> subdistrictModelArrayList = new ArrayList<Model>();
    public ParseSubDistrict(String url, String subdistrictid) {
        this.url = url;
        this.subdistrictid = subdistrictid;
    }

    public void postSubDistrictRequest(final parseSubDistrictCallback callback) {
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
                            model.setItemName("Please select sub district");
                            subdistrictModelArrayList.add(model);
                            subdistrictModel = new SubDistrictModel(
                                    "0",
                                    "0",
                                    "0"
                            );
                            subdistrictModelList.add(subdistrictModel);
                            if (response.startsWith("[")) {
                                jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    model = new Model();
                                    model.setItemName(jsonObject.getString("districtName"));
                                    subdistrictModel = new SubDistrictModel(
                                            jsonObject.getString("districtId"),
                                            jsonObject.getString("districtName"),
                                            jsonObject.getString("districtNameEng")
                                    );
                                    subdistrictModelArrayList.add(model);
                                    subdistrictModelList.add(subdistrictModel);
                                }
                            }
                            callback.onParseSubDistrictCallbackSuccess(subdistrictModelArrayList, subdistrictModelList);
                        } catch (JSONException e) {
                            Log.e(TAG, "json sub-district parsing error: " + e.getMessage());
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
                params.put("amphurId", subdistrictid);
                return params;
            }

        };
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    public interface parseSubDistrictCallback {
        void onFailed(String result);

        void onParseSubDistrictCallbackSuccess(ArrayList<Model> subdistrictModels, List<SubDistrictModel> modelList);
    }
}
