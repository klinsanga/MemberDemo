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

import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.model.FaststartItem;
import th.co.omc.memberdemo.model.ItemMember;
import th.co.omc.memberdemo.model.ReportSponsorsItem;
import th.co.omc.memberdemo.model.ReportUplineItem;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by Teera-s.me on 17/9/2559.
 */
public class ParseJson {
    public static final String TAG = ParseJson.class.getSimpleName();

    private String url;
    private String username, password;
    private ItemMember itemMember;

    private ReportUplineItem reportUplineItem;
    private List<ReportUplineItem> reportUplineItemArrayList = new ArrayList<ReportUplineItem>();

    private ReportSponsorsItem reportSponsorsItem;
    private List<ReportSponsorsItem> reportSponsorsItemList = new ArrayList<ReportSponsorsItem>();

    private FaststartItem faststartItem;
    private List<FaststartItem> faststartItemList = new ArrayList<FaststartItem>();
    public ParseJson(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public ParseJson(String url) {
        this.url = url;
    }

    public void postLoginRequest(final VolleyCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response: " + response);
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
                                            jsonObject.isNull("DOWNLINE") ? "" : jsonObject.getJSONObject("DOWNLINE").isNull("left") ? "" : jsonObject.getJSONObject("DOWNLINE").getJSONObject("left").getString("UPA_NAME"),
                                            jsonObject.isNull("DOWNLINE") ? "" : jsonObject.getJSONObject("DOWNLINE").isNull("left") ? "" : jsonObject.getJSONObject("DOWNLINE").getJSONObject("left").getString("UPA_MCODE"),
                                            jsonObject.isNull("DOWNLINE") ? "" : jsonObject.getJSONObject("DOWNLINE").isNull("left") ? "" : jsonObject.getJSONObject("DOWNLINE").getJSONObject("left").getString("ALL_POINT"),
                                            jsonObject.isNull("DOWNLINE") ? "" : jsonObject.getJSONObject("DOWNLINE").isNull("left") ? "" : jsonObject.getJSONObject("DOWNLINE").getJSONObject("left").getString("PROFILE_IMG"),
                                            jsonObject.isNull("DOWNLINE") ? "" : jsonObject.getJSONObject("DOWNLINE").isNull("right") ? "" : jsonObject.getJSONObject("DOWNLINE").getJSONObject("right").getString("UPA_NAME"),
                                            jsonObject.isNull("DOWNLINE") ? "" : jsonObject.getJSONObject("DOWNLINE").isNull("right") ? "" : jsonObject.getJSONObject("DOWNLINE").getJSONObject("right").getString("UPA_MCODE"),
                                            jsonObject.isNull("DOWNLINE") ? "" : jsonObject.getJSONObject("DOWNLINE").isNull("right") ? "" : jsonObject.getJSONObject("DOWNLINE").getJSONObject("right").getString("ALL_POINT"),
                                            jsonObject.isNull("DOWNLINE") ? "" : jsonObject.getJSONObject("DOWNLINE").isNull("right") ? "" : jsonObject.getJSONObject("DOWNLINE").getJSONObject("right").getString("PROFILE_IMG"),
                                            jsonObject.getString("locationbase")
                                    );
                                }
                                MyApplication.getInstance().getPrefManager().storeUser(itemMember);
                                callback.onSuccess(EndPoints.COMPLETE);
                            } else {
                                obj = new JSONObject(response);
                                if (obj.getString("STATUS").equals("FAIL")) {
                                    Log.e(TAG, obj.getString("MESSAGE"));
                                    callback.onFailed(obj.getString("MESSAGE"));
                                }
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "json parsing error: " + e.getMessage());
                            callback.onFailed("Fatal error exception.");
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
                params.put(EndPoints.KEY_USERNAME, username);
                params.put(EndPoints.KEY_PASSWORD, password);
                params.put(EndPoints.KEY_API, EndPoints.API_KEYCODE);
                return params;
            }
        };
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    public void postUplineRequest(final VolleyCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response upline : " + response);
                        JSONObject obj;
                        JSONArray jsonArray;
                        JSONObject jsonObject;
                        try {
                            if (response.startsWith("[")) {
                                jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    reportUplineItem = new ReportUplineItem(
                                            jsonObject.getString("mcode"),
                                            jsonObject.getString("name_t"),
                                            jsonObject.getString("mdate"),
                                            jsonObject.getString("pos_cur"),
                                            jsonObject.getString("pos_cur2"),
                                            jsonObject.getString("pvall"),
                                            jsonObject.getString("sp_code"),
                                            jsonObject.getString("sp_name"),
                                            jsonObject.getString("level"),
                                            "",
                                            convertSideToText(jsonObject.getString("lr"))
                                    );
                                    reportUplineItemArrayList.add(reportUplineItem);
                                }

                                callback.onParseUplineSuccess(reportUplineItemArrayList);
                            } else {
                                obj = new JSONObject(response);
                                if (obj.getString("STATUS").equals("FAIL")) {
                                    Log.e(TAG, obj.getString("MESSAGE"));
                                    callback.onFailed(obj.getString("MESSAGE"));
                                }
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "json upline parsing error: " + e.getMessage());
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
                params.put("start", username);
                params.put("end", password);
                return params;
            }

        };
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    public void postSponsorsRequest(final VolleyCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response sponsors : " + response);
                        JSONObject obj;
                        JSONArray jsonArray;
                        JSONObject jsonObject;
                        try {
                            if (response.startsWith("[")) {
                                jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    reportSponsorsItem = new ReportSponsorsItem(
                                            jsonObject.getString("mcode"),
                                            jsonObject.getString("name_t"),
                                            jsonObject.getString("mdate"),
                                            jsonObject.getString("pos_cur"),
                                            jsonObject.getString("pos_cur2"),
                                            jsonObject.getString("totpv"),
                                            jsonObject.getString("sp_code"),
                                            jsonObject.getString("sp_name"),
                                            jsonObject.getString("lv_sp"),
                                            ""
                                    );
                                    reportSponsorsItemList.add(reportSponsorsItem);
                                }

                                callback.onParseSponsorsSuccess(reportSponsorsItemList);
                            } else {
                                obj = new JSONObject(response);
                                if (obj.getString("STATUS").equals("FAIL")) {
                                    Log.e(TAG, obj.getString("MESSAGE"));
                                    callback.onFailed(obj.getString("MESSAGE"));
                                }
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "json sponsors parsing error: " + e.getMessage());
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
                return params;
            }

        };
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    public interface VolleyCallback{
        void onSuccess(String result);
        void onFailed(String result);
        void onParseUplineSuccess(List<ReportUplineItem> list);
        void onParseSponsorsSuccess(List<ReportSponsorsItem> list);
    }

    private int convertSideToText(String side){
        switch (side) {
            case "0" :
                return 0;
            case "1" :
                return R.string.left_side;
            case "2" :
                return R.string.right_side;
            default:break;
        }
        return 0;
    }
}
