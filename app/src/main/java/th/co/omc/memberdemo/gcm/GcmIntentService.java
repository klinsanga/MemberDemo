package th.co.omc.memberdemo.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by teera-s on 5/19/2016 AD.
 */
public class GcmIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private static final String TAG = GcmIntentService.class.getSimpleName();
    public GcmIntentService() {
        super(TAG);
    }

    public static final String KEY = "key";

    private String key, name, email;

    @Override
    protected void onHandleIntent(Intent intent) {
        key = intent.getStringExtra(KEY);
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        GCM(key, name, email);
    }

    /**
     * Registering with GCM and obtaining the gcm registration id
     */
    private void GCM(final String key, final String name, final String email) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = null;

        /*if (key.equals("register")) {
            try {
                Log.e("GCM Scope", GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                InstanceID instanceID = InstanceID.getInstance(this);
                token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                Log.e(TAG, "GCM Registration Token: " + token);
                sendRegistrationToServer(name, email, token);
                sharedPreferences.edit().putBoolean(Config.SENT_TOKEN_TO_SERVER, true).apply();
            } catch (Exception e) {
                Log.e(TAG, "Failed to complete token refresh", e);
                sharedPreferences.edit().putBoolean(Config.SENT_TOKEN_TO_SERVER, false).apply();
            }

            Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
            registrationComplete.putExtra("token", token);
            LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
        } else {
            try {
                InstanceID instanceID = InstanceID.getInstance(this);
                token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                Log.e(TAG, "GCM Update Token: " + token);
                updateTokenToServer(name, email, token);
                sharedPreferences.edit().putBoolean(Config.UPDATE_TOKEN_TO_SERVER, true).apply();
            } catch (Exception e) {
                Log.e(TAG, "Failed to complete token refresh", e);
                sharedPreferences.edit().putBoolean(Config.UPDATE_TOKEN_TO_SERVER, false).apply();
            }

            Intent registrationComplete = new Intent(Config.UPDATE_TOKEN_TO_SERVER);
            registrationComplete.putExtra("token", token);
            LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
        }*/
    }

    /*private void sendRegistrationToServer(final String name, final String email, final String token) {

        Log.e("Sent to server", "Name : "+ name + " Email : " + email + " Token : " + token);
        Log.e("URL", EndPoints.REGISTER);
        StringRequest strReq = new StringRequest(Request.Method.POST, EndPoints.REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "response: " + response);

                try {
                    JSONObject obj = new JSONObject(response);

                    if (obj.getBoolean("error") == false) {

                        JSONObject userObj = obj.getJSONObject("user");
                        Users user = new Users(userObj.getString("name"),
                                userObj.getString("email"),
                                userObj.getString("token"));

                        MyApplication.getInstance().getPrefManager().storeUser(user);

                        Intent sendToServerComplete = new Intent(Config.SENT_TOKEN_TO_SERVER);
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(sendToServerComplete);

                        MyApplication.getInstance().getPrefManager().setStatus("Registed");

                    } else {
                        //Toast.makeText(getApplicationContext(), "" + obj.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                        Log.e(TAG, "Unable to send gcm registration id to our sever. " + obj.getJSONObject("error").getString("message"));
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "json parsing error: " + e.getMessage());
                    //Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                Log.e(TAG, "Volley error register: " + error.getMessage() + ", code: " + networkResponse);
                //Toast.makeText(getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("token", token);

                Log.e(TAG, "Register params: " + params.toString());
                return params;
            }
        };

        //Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);
    }

    private void updateTokenToServer(final String name, final String email, final String token) {

        StringRequest strReq = new StringRequest(Request.Method.POST, EndPoints.UPDATE_TOKEN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "response: " + response);

                try {
                    JSONObject obj = new JSONObject(response);

                    // check for error
                    if (obj.getBoolean("error") == false) {

                        JSONObject userObj = obj.getJSONObject("user");
                        Users user = new Users(userObj.getString("name"),
                                userObj.getString("email"),
                                userObj.getString("token"));

                        MyApplication.getInstance().getPrefManager().storeUser(user);
                        // broadcasting token sent to server
                        Intent registrationComplete = new Intent(Config.UPDATE_TOKEN_TO_SERVER);
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(registrationComplete);
                    } else {
                        Log.e(TAG, "Unable to send gcm registration id to our sever. " + obj.getJSONObject("error").getString("message"));
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "error when update: " + e.getMessage());
                    //Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                Log.e(TAG, "Volley error update: " + error.getMessage() + ", code: " + networkResponse);
                //Toast.makeText(getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("token", token);

                Log.e(TAG, "Update params: " + params.toString());
                return params;
            }
        };

        //Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);
    }*/
}
