package th.co.omc.memberdemo.gcm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by teera-s on 5/30/2016 AD.
 */
public class GcmBroadcastService extends IntentService {

    private static final String TAG = GcmBroadcastService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public GcmBroadcastService() {
        super("GcmBroadcastService");
    }

    private String title, data, link;
    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Bundle extras = intent.getExtras();
            title = extras.getString("title");
            data = extras.getString("data");
            link = extras.getString("link");
        } catch (Exception e) {

        }
        Log.e("Service", title + ", " + data);
        processUserMessage(title, data, link);
    }

    /**
     * Processing user specific push message
     * It will be displayed with / without image in push notification tray
     * */
    private void processUserMessage(String title, String data, String link) {
        try {
            JSONObject datObj = new JSONObject(data);

            String imageUrl = datObj.getString("image");
            Log.e("image", imageUrl);

            JSONObject mObj = datObj.getJSONObject("message");
            String post_title = mObj.getString("title");
            String post_content = mObj.getString("content");

            /*Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            showNotificationMessageWithBigImage(getApplicationContext(), title, post_title, post_content, resultIntent, imageUrl, link);*/

        } catch (JSONException e) {
            Log.e(TAG, "json parsing error: " + e.getMessage());
            Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Showing notification with text and image
     * */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String content, Intent intent, String imageUrl, String url) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, content, intent, imageUrl, url);
    }
}
