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

import th.co.omc.memberdemo.model.shopping.DistrictModel;
import th.co.omc.memberdemo.model.shopping.Model;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by teera-s on 11/16/2016 AD.
 */

public class ParseDistrict {

    public static final String TAG = ParseDistrict.class.getSimpleName();

    Model model;
    private String url, provinceid;
    private DistrictModel districtModel;
    List<DistrictModel> districtModelList = new ArrayList<DistrictModel>();
    ArrayList<Model> districtModelArrayList = new ArrayList<Model>();
    public ParseDistrict(String url, String provinceid) {
        this.url = url;
        this.provinceid = provinceid;
    }

    public void postDistrictRequest(final parseDistrictCallback callback) {
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
                            model.setItemName("Please select district");
                            districtModelArrayList.add(model);
                            districtModel = new DistrictModel(
                                    "0",
                                    "0",
                                    "0"
                            );
                            districtModelList.add(districtModel);
                            if (response.startsWith("[")) {
                                jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    model = new Model();
                                    model.setItemName(jsonObject.getString("amphurName"));
                                    districtModel = new DistrictModel(
                                            jsonObject.getString("amphurId"),
                                            jsonObject.getString("amphurName"),
                                            jsonObject.getString("amphurNameEng")
                                    );
                                    districtModelArrayList.add(model);
                                    districtModelList.add(districtModel);
                                }
                            }
                            callback.onParseDistrictCallbackSuccess(districtModelArrayList, districtModelList);
                        } catch (JSONException e) {
                            Log.e(TAG, "json district parsing error: " + e.getMessage());
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
                params.put("provinceId", provinceid);
                return params;
            }

        };
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    public interface parseDistrictCallback {
        void onFailed(String result);

        void onParseDistrictCallbackSuccess(ArrayList<Model> districtModels, List<DistrictModel> modelList);
    }
}
