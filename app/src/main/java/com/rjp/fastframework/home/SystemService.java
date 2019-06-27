package com.rjp.fastframework.home;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class SystemService extends Service {
	private PhoneStateListener listener;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		listener = new MyPhoneStateListener();
		manager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		Log.i("bqt", "onCreate");
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("bqt", "onStartCommand");
		return START_STICKY;//当service因内存不足被kill，当内存又有的时候，service又被重新创建
	}
	
	@Override
	public void onDestroy() {
		Log.i("bqt", "onDestroy");
		//startService(new Intent(this, SystemService.class));//在onDestroy中再启动本服务，但是用户杀进程时不会调用onDestroy方法
		// 取消电话的监听
		((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
		super.onDestroy();
	}
}