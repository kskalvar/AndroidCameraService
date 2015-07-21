package com.mycompany.androidcamera;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

@SuppressWarnings("deprecation")
public class CameraService extends Service {

	private Camera mCamera;

	private final IBinder mBinder = new MyBinder();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (getCameraInstance()) {
			Log.i("CameraService", "Camera Not in Use");
		} else {
			Log.i("CameraService", "Camera in Use");
		}

		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	public class MyBinder extends Binder {
		CameraService getService() {
			return CameraService.this;
		}
	}

	public boolean getCameraInstance() {

		boolean available = false;

		mCamera = getCamera();

		if (mCamera != null) {
			available = true;
			mCamera.release();
		}

		return available;
	}

	/**
	 * A safe way to get an instance of the Camera object.
	 **/

	public static Camera getCamera() {

		Camera c = null;
		try {
			c = Camera.open(0); // attempt to get a Camera instance
		} catch (Exception e) {
			// Camera is not available (in use or does not exist)
		}
		return c; // returns null if camera is unavailable
	}

}
