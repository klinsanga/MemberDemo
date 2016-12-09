package th.co.omc.memberdemo.gcm;

import android.content.Context;
import android.content.Intent;

public class MyGcmPushReceiver /*extends GcmListenerService*/ {

    private static final String TAG = MyGcmPushReceiver.class.getSimpleName();

    private NotificationUtils notificationUtils;

    /**
     * Called when message is received.
     *
     * @param from   SenderID of the sender.
     * @param bundle Data bundle containing message data as key/value pairs.
     *               For Set of keys use data.keySet().
     */
    /*@Override
    public void onMessageReceived(String from, Bundle bundle) {
        String title = bundle.getString("title");
        String data = bundle.getString("data");
        String link = bundle.getString("link");
        Log.e(TAG, "From: " + from);
        Log.e(TAG, "title: " + title);
        Log.e(TAG, "data: " + data);

        if(MyApplication.getInstance().getPrefManager().getUser() == null){
            Log.e(TAG, "user is not logged in, skipping push notification");
            return;
        }

        //processUserMessage(title, data, link);
    }*/

    /**
     * Processing user specific push message
     * It will be displayed with / without image in push notification tray
     * */
    /*private void processUserMessage(String title, String data, String link) {
        try {
            JSONObject datObj = new JSONObject(data);

            String imageUrl = datObj.getString("image");
            Log.e("image", imageUrl);

            JSONObject mObj = datObj.getJSONObject("message");
            String post_title = mObj.getString("title");
            String post_content = mObj.getString("content");

            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            showNotificationMessageWithBigImage(getApplicationContext(), title, post_title, post_content, resultIntent, imageUrl, link);

        } catch (JSONException e) {
            Log.e(TAG, "json parsing error: " + e.getMessage());
            Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }*/

    /**
     * Showing notification with text and image
     * */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String content, Intent intent, String imageUrl, String url) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, content, intent, imageUrl, url);
    }
}
