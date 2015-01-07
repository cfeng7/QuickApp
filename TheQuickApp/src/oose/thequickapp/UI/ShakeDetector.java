package oose.thequickapp.UI;

import java.util.Calendar;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * This class defines a detector of the activity of shaking.
 * When the signal is triggered, some activities will be
 * taken.
 * 
 * @author zcy1848
 *
 */
public class ShakeDetector implements SensorEventListener {

	private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
	private static final int SHAKE_SLOP_TIME_MS = 500;
	private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;
	private OnShakeListener mListener;
	private long mShakeTimestamp;
	private int mShakeCount;
	
	/**
	 * Adds a listener listening for the activity of shaking to the detector.
	 * @param listener a listener listening for the activity of shaking
	 */
	public void setOnShakeListener(OnShakeListener listener){
		this.mListener = listener;
	}
	
	/**
	 * This interface defines a listener on the main interface
	 * to listen for the activity of shaking.
	 * @author zcy1848
	 *
	 */
	public interface OnShakeListener{
		public void onShake(int count);
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		long now = Calendar.getInstance().getTimeInMillis();
		if(mListener!=null){
			float x = event.values[0];
			float y = event.values[1];
			float z = event.values[2];
			
			float gX = x/SensorManager.GRAVITY_EARTH;
			float gY = y/SensorManager.GRAVITY_EARTH;
			float gZ = z/SensorManager.GRAVITY_EARTH;
			float gForce = (float)Math.sqrt(gX*gX+gY*gY+gZ*gZ);
			if(gForce>SHAKE_THRESHOLD_GRAVITY){
				if(mShakeTimestamp+SHAKE_SLOP_TIME_MS>now){
					return;
				}
				if(mShakeTimestamp+SHAKE_COUNT_RESET_TIME_MS<now){
					mShakeCount = 0;
				}
				mShakeTimestamp = now;
				mShakeCount++;
				mListener.onShake(mShakeCount);
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

}
