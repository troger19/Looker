package com.example.looker.util;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

//bezi v rovnakom threade ako aplikacia
public class MyService extends Service {

	private ClipboardManager myClipboard;

	private final IBinder mBinder = new LocalBinder();

	public void onLocationChanged(Location arg0) {
	}

	public void onProviderDisabled(String arg0) {
	}

	public void onProviderEnabled(String arg0) {
	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d("SVTEST", "Loc service ONBIND");
		myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d("SVTEST", "Loc service ONUNBIND");
		return super.onUnbind(intent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Won't run unless it's EXPLICITLY STARTED
		Log.d("SVTEST", "Loc service ONSTARTCOMMAND");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("SVTEST", "Loc service ONDESTROY");
	}

	public class LocalBinder extends Binder {
		// Return this instance of LocalService so clients can call public methods
		public MyService getService() {
			return MyService.this;
		}
	}

	public String pasteClipboard() {
		// A method you can call in the Activity
		Log.d("SVTEST", "Loc service EXECUTING THE METHOD");
		ClipData abc = myClipboard.getPrimaryClip();
		ClipData.Item item = abc.getItemAt(0);
		String text = item.getText().toString();
		return text;
	}

}
