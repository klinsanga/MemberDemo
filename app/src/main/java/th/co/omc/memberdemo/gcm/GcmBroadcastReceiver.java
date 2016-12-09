package th.co.omc.memberdemo.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by teera-s on 5/30/2016 AD.
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String title = intent.getStringExtra("title");
        String data = intent.getStringExtra("data");
        String link = intent.getStringExtra("link");

        ComponentName comp = new ComponentName(context.getPackageName(), GcmBroadcastService.class.getName());
        intent.putExtra("title", title);
        intent.putExtra("data", data);
        intent.putExtra("link", data);
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}
