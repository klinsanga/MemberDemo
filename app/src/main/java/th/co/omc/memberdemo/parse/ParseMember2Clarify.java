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

import th.co.omc.memberdemo.model.ItemMember;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * Created by teera-s on 12/2/2016 AD.
 */

public class ParseMember2Clarify {
    public static final String TAG = ParseMember2Clarify.class.getSimpleName();

    private ItemMember itemMember;
    private String url, memberid;

    private List<ItemMember> itemMemberList = new ArrayList<ItemMember>();

    public ParseMember2Clarify(String url, String id) {
        this.url = url;
        this.memberid = id;
    }

    public void postMemberClarifyRequest(final parseMemberCallback callback) {
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
                                            jsonObject.getString("name_f") + " " + jsonObject.getString("name_t") + " " + jsonObject.getString("name_e"),
                                            jsonObject.getString("mdate"),
                                            jsonObject.getString("birthday"),
                                            jsonObject.getString("sex"),
                                            jsonObject.getString("mobile"),
                                            jsonObject.getString("email"),
                                            jsonObject.getString("sp_name"),
                                            jsonObject.getString("upa_name"),
                                            jsonObject.getString("ewallet"),
                                            jsonObject.getString("profile_img"),
                                            jsonObject.getJSONObject("pos_cur").getString("POS_NAME"),
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
                                            jsonObject.getString("locationbase"));
                                    itemMemberList.add(itemMember);
                                }
                                callback.onParseMemberSuccess(itemMemberList);
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
                params.put(EndPoints.KEY_USERNAME, memberid);
                params.put(EndPoints.KEY_API, EndPoints.API_KEYCODE);
                return params;
            }
        };
        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

    public interface parseMemberCallback{
        void onFailed(String result);
        void onParseMemberSuccess(List<ItemMember> itemList);
    }
}
