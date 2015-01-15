package oose.thequickapp.controller;

import oose.thequickapp.R;
import oose.thequickapp.UI.GridSettingModel;
import oose.thequickapp.UI.TempView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * This is the Activity of the first step of setup part. For now user will draw a pattern on the screen 
 * and the pattern will be saved for matching an action.
 * @author Cong Feng
 *
 */

public class GridSetActivity extends Activity {
	/**
	 * The model for the grid
	 */
	private GridSettingModel gridModel;
	/**
	 * The view representing the grid
	 */
	private TempView rv;

	private RelativeLayout mRelativeLayout;

	/*@Override
	public void onAttachedToWindow() {

		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG|WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onAttachedToWindow();
	}*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_FULLSCREEN);
				setContentView(R.layout.activity_grid);

		mRelativeLayout = new RelativeLayout(this);
		gridModel = new GridSettingModel();
		rv = new TempView(this, gridModel);
		rv.setOnTouchListener(new View.OnTouchListener() {
			int num = rv.NUM;
			int width = rv.WIDTH;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				if(action==MotionEvent.ACTION_DOWN){
					int s = ((int)event.getX()-rv.left)/width;
					int t = ((int)event.getY()-rv.top)/width;
					if(s>=0&&s<num&&t>=0&&t<num){
						gridModel.add(s, t);
						rv.invalidate();
					}
				}
				if(action==MotionEvent.ACTION_MOVE){
					int s = ((int)event.getX()-rv.left)/width;
					int t = ((int)event.getY()-rv.top)/width;
					if(s>=0&&s<num&&t>=0&&t<num){
						gridModel.add(s, t);
						rv.invalidate();
					}else{
						//						gridModel.clear();
						rv.invalidate();
					}
				}
				if(action==MotionEvent.ACTION_UP){
					//					gridModel.clear();
					rv.invalidate();
				}
				return true;
			}
		});
		Button b1 = new Button(this);
		//		Button b1 = (Button) findViewById(R.id.config1);
	//	b1.setBackgroundResource(R.drawable.roundedbuttonred);
	//	b1.set
		
		
//		
		GradientDrawable drawable = new GradientDrawable();
		    drawable.setShape(GradientDrawable.RECTANGLE);
		    drawable.setStroke(5, Color.WHITE);
		    drawable.setColor( Color.CYAN);
		    drawable.setAlpha(15);

		   b1.setBackgroundDrawable(drawable);
		b1.setText("Confirm");
		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(gridModel.getIsSecond()){
					if(gridModel.isMatch()){
						gridModel.save();
						gridModel.clear();
						rv.invalidate();
						gridModel.setIsSecond(false);
						Toast.makeText(GridSetActivity.this, "Pattern Saved", Toast.LENGTH_SHORT).show();
						Intent openGetApp = new Intent(GridSetActivity.this, GetAppActivity.class);
						openGetApp.putExtra("TheModel", gridModel);
						openGetApp.putExtra("FromWhich", "GridSetActivity");
						startActivity(openGetApp);
					}
					else{
						gridModel.clear();
						gridModel.setIsSecond(false);
						Toast.makeText(GridSetActivity.this, "Patterns doesn't match", Toast.LENGTH_SHORT).show();
						rv.setColorOn();
					    Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);                 
				        vibrator.vibrate(300); 
						rv.invalidate();
						
           
					}


				} else{
					gridModel.setIsSecond(true);
					gridModel.setResTemp();
					gridModel.clear();
					rv.invalidate();
				}

			}
		});
		rv.setBackgroundResource(R.drawable.bgm);
		//rv.setBackgroundColor(Color.WHITE);
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		lp.topMargin = -100;
		rv.setLayoutParams(lp);
		
		RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		lp2.addRule(RelativeLayout.CENTER_HORIZONTAL);
		lp2.width = 480;
		lp2.height = 160;
		b1.setTypeface(b1.getTypeface(), Typeface.ITALIC);
		b1.setTextColor(Color.WHITE);
		b1.setLayoutParams(lp2);
		
		mRelativeLayout.addView(rv);
		mRelativeLayout.addView(b1);

		setContentView(mRelativeLayout,rlp);
	}

	public void setUpActionBar(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
