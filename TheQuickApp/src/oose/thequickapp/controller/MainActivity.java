package oose.thequickapp.controller;

import oose.thequickapp.R;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * MainActivity is the first activity of our app's setting part;
 * Now this setting part only support setting up the grid pattern to be recognized and 
 * select an app to be opened.
 * @author Group 12
 *
 */
public class MainActivity extends Activity {

	boolean isServiceOn;
	String serviceName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//TODO

		serviceName = MyService.class.getName();
		isServiceOn = check(serviceName);
		isServiceOn = true;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ToggleButton serviceOnOff = (ToggleButton) findViewById(R.id.controllService);
		Button gridButton = (Button) findViewById(R.id.GridButton);
		//		Button shakeButton = (Button) findViewById(R.id.ShakeButton);
		Button dButton = (Button) findViewById(R.id.ShakeButton);

		Button soundButton = (Button) findViewById(R.id.SoundButton);
		Button vsButton = (Button) findViewById(R.id.ViewSavedButton);
		serviceOnOff.setChecked(isServiceOn);

		gridButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentToGb = new Intent(MainActivity.this,GridSetActivity.class);
				startActivity(intentToGb);

			}
		});

		soundButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intentToSound = new Intent(MainActivity.this, AudioRecordActivity.class);	
				startActivity(intentToSound);
			}
		});

		vsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentToViewSaved = new Intent(MainActivity.this, ViewSavedActivity.class);		
				startActivity(intentToViewSaved);
			}
		});
		
		dButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentToDG = new Intent(MainActivity.this, DrawSettingActivity.class);
				startActivity(intentToDG);
			}
		});
		

		serviceOnOff.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "The Service is set " + isChecked, Toast.LENGTH_SHORT).show();
				if(isChecked){
					startService(new Intent(MainActivity.this, MyService.class));
					isServiceOn = true;
				}else{
					
					stopService(new Intent(MainActivity.this, MyService.class));
					isServiceOn = false;
				}
			}
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private boolean check(String serviceName){
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		boolean result = false;
		for(RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
			if (serviceName == service.service.getClassName()){
				result = true;
			}
		}
		return result;
	}


}
