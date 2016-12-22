package th.co.omc.memberdemo.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import th.co.omc.memberdemo.activity.NoInternetActivity;

public class NetworkChangeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, final Intent intent) {

		try {
			String status = NetworkUtil.getConnectivityStatusString(context);
			if (status.equals("Wifi enabled") || status.equals("Mobile data enabled")) {
				NoInternetActivity.getInstance().detectWifiConnected("connect");
				/*SigninActivity.getInstance().detectWifiConnected("connect");*/
			} else {
				NoInternetActivity.getInstance().detectWifiConnected("not connect");
				/*MainActivity.getInstance().detectWifiConnected("not connect");
				SigninActivity.getInstance().detectWifiConnected("not connect");*/
			}
		} catch (Exception e) {

		}
	}
}
