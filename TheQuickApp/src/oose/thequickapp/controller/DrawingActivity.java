package oose.thequickapp.controller;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import oose.thequickapp.R;
import oose.thequickapp.UI.DrawingView;
import oose.thequickapp.UI.ListViewAdapterForDrawing;
import oose.thequickapp.UI.ShakeDetector;
import oose.thequickapp.UI.ShakeDetector.OnShakeListener;
import oose.thequickapp.model.Analyzer;
import oose.thequickapp.model.AppList;
import oose.thequickapp.model.DrawPattern;
import oose.thequickapp.model.DrawingModel;
import oose.thequickapp.model.FeedBack;
import oose.thequickapp.model.InstalledApps;
import oose.thequickapp.model.LearningModel;
import oose.thequickapp.model.OnLineLearning;
import oose.thequickapp.model.PInfo;
import oose.thequickapp.model.Pattern;
import oose.thequickapp.model.Recognizer;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * This class defines the main interface of the launcher when the phone
 * powers on. User is able to draw letter or number to recognize apps or
 * record voice to recognize apps.
 * 
 * @author zcy1848
 *
 */
public class DrawingActivity extends Activity {
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;
	
	private DrawingModel drawingModel;
	private DrawingView dv;
	private ArrayList<PInfo> installedApps;
	
	private ListView lv;
	
	private Button btn;
	private ListViewAdapterForDrawing listAdapter;
	private Handler handler;
	private int n;
	private Analyzer analyzer;
	private boolean isRecording;
	private String filename;
	private Thread thread;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				String s = (String)msg.obj;
				if(s.equals("drawAgain")){
					drawAgain();
					return;
				}
				analyze(s);
			}
		};
		filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recordIn.pcm";
		context = this;
		
		drawingModel = new DrawingModel();
		n = drawingModel.N;
		dv = new DrawingView(this, drawingModel);
		dv.setOnTouchListener(new View.OnTouchListener() {
			int width = dv.LEN/n;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int s = ((int)event.getX()-dv.left)/width;
				int t = ((int)event.getY()-dv.top)/width;
				int action = event.getAction();
				if(s>=0&&s<n&&t>=0&&t<n&&(action==MotionEvent.ACTION_DOWN||action==MotionEvent.ACTION_MOVE)){
					drawingModel.set(s, t);
					if(!dv.press){
						dv.press = true;
						handler.postDelayed(new Runnable(){
							@Override
							public void run() {
								//handler.sendEmptyMessage(0);
			                    Message msg = Message.obtain();
		                    	msg.obj = "drawAgain";
		                    	msg.setTarget(handler);
		                    	msg.sendToTarget();
							}
						}, 3000);						
					}
					if(action==MotionEvent.ACTION_DOWN){
						dv.path.moveTo(event.getX(), event.getY());
					}else if(action==MotionEvent.ACTION_MOVE){
						if(dv.path.isEmpty()) dv.path.moveTo(event.getX(), event.getY());
						else dv.path.lineTo(event.getX(), event.getY());
					}
					dv.postInvalidate();
				}
				return true;
			}
		});
		
		LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LayoutParams param2 = new LinearLayout.LayoutParams(dv.LEN/2, dv.LEN/2);
		LayoutParams layoutParam = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		btn = new Button(this);
		btn.setText("Voice!");
		btn.setLayoutParams(param2);
		btn.setBackgroundColor(Color.GREEN);
		btn.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				if(action==MotionEvent.ACTION_DOWN){
					btn.setBackgroundColor(Color.RED);
					Thread thread = new Thread(new Runnable(){
						@Override
						public void run() {
							record(filename);
		                    Message msg = Message.obtain();
	                    	msg.obj = filename;
	                    	msg.setTarget(handler);
	                    	msg.sendToTarget();
						}
					});
					thread.start();
				}else if(action==MotionEvent.ACTION_UP){
					btn.setBackgroundColor(Color.GREEN);
					isRecording = false;
				}
				return true;
			}
			
		});
		
		LinearLayout layout1 = new LinearLayout(this);
		layout1.setOrientation(LinearLayout.VERTICAL);
		layout1.setGravity(Gravity.CENTER);
		layout1.addView(dv);
		layout1.addView(btn);
		
		installedApps = new InstalledApps(this).getInstalledApps(false);
		AppList.getInstance(installedApps.size());
		listAdapter = new ListViewAdapterForDrawing(this);
		lv = new ListView(this);
		lv.setAdapter(listAdapter);
		lv.setLayoutParams(params);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					finish();
					int tmp = AppList.getInstance().getAppsToShow().get(position).getId();
					startActivity(installedApps.get(tmp).toOpen);
					FeedBack fb = new FeedBack(position);
					analyzer.userFeedback(fb);
					/*listAdapter.clearList();
					lv.setAdapter(listAdapter);
					lv.postInvalidate();*/			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});

		LinearLayout layout2 = new LinearLayout(this);
		layout2.setOrientation(LinearLayout.HORIZONTAL);
		layout2.addView(layout1);
		layout2.addView(lv);
		layout2.setBackgroundResource(R.drawable.bg3);
		this.setContentView(layout2);
		
		//set ShakeDetector
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mShakeDetector = new ShakeDetector();
		mShakeDetector.setOnShakeListener(new OnShakeListener(){
			@Override
			public void onShake(int count) {
				finish();
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
	}
	
	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(mShakeDetector);
		super.onPause();
	}
	
	/**
	 * Pass the DrawingPattern to analyzer for analysis and clean the
	 * palette.
	 */
	private void drawAgain(){
		Pattern pattern = new DrawPattern(drawingModel.getArray());
		LearningModel lm = OnLineLearning.getInstance(AppList.getInstance().getSize(), n*n);
		analyzer = new Analyzer(pattern, lm);
		analyzer.analyze();
		
		listAdapter.setList(installedApps);
		lv.setAdapter(listAdapter);
		lv.postInvalidate();
		
		dv.press = false;
		dv.path.reset();
		dv.postInvalidate();
	}
	
	/**
	 * Record the voice when "voice" button is pressed, save the sound to
	 * file system.
	 * @param filename the file name where is sound is recorded
	 */
	private void record(String filename){
		int frequency = 8000;
	    int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	    int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
	    File file = new File(filename);
	    if(file.exists()) file.delete();
	    try{
	    	file.createNewFile();
	    }catch(IOException e) {
	    	throw new IllegalStateException("Failed to create " + file.toString());
	    }
	    try{
	    	OutputStream os = new FileOutputStream(file);
	    	BufferedOutputStream bos = new BufferedOutputStream(os);
	    	DataOutputStream dos = new DataOutputStream(bos);
	    	int bufferSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration,  audioEncoding);
	    	AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
	                                                  frequency, channelConfiguration,
	                                                  audioEncoding, bufferSize);
	    	short[] buffer = new short[bufferSize];  
	    	audioRecord.startRecording();
	 
	    	isRecording = true ;
	    	while(isRecording) {
	    		int bufferReadResult = audioRecord.read(buffer, 0, bufferSize);
	    		for(int i = 0; i<bufferReadResult; i++)
	    			dos.writeShort(buffer[i]);
	    	}
	    	audioRecord.stop();
	    	dos.close();
	    }catch(Throwable t) {
	    	Log.e("AudioRecord","Recording Failed");
	    }
	}
	
	/**
	 * Analyze the file of sound and get into the related app or return
	 * remind info.
	 * @param filename the file name of analyzed sound
	 */
	void analyze(String filename){
		String pname = Recognizer.analyze(filename,context);
        if(pname==null){
        	Toast.makeText(DrawingActivity.this, "Not match!", Toast.LENGTH_SHORT).show();
        }else{
        	PackageInfoGenerator pg = new PackageInfoGenerator(context);
        	finish();
        	startActivity(pg.intentRecongnize(pname, pg));
        }
	}
}
