package com.rjp.fastframework.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SuperReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, SystemService.class));
		Log.i("bqt", intent.getAction());
	}
}