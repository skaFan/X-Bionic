package com.ska.x_bionic.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ConnectivityChangeReiceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		boolean hasConn = ConnectivityUtil.isOnline(context);
		if (!hasConn) {
			Toast.makeText(context, "网络没连接", Toast.LENGTH_SHORT).show();
		}
	}
}
