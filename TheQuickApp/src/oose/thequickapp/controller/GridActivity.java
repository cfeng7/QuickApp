package oose.thequickapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import oose.thequickapp.R;
import oose.thequickapp.UI.GridModel;
import oose.thequickapp.UI.RectangleView;
import oose.thequickapp.model.DatabaseOperations;
import oose.thequickapp.model.GridTranslater;
import oose.thequickapp.model.Position;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * This class defines the activity to show the main interface
 * when the phone is booted. It draws the 4*4 board, controls
 * the activity to unlock the phone by patterns or gestures.
 * 
 * @author zcy1848
 *
 */
public class GridActivity extends Activity {
	private GridModel gridModel;
	private RectangleView rv;

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

		gridModel = new GridModel();




		rv = new RectangleView(this, gridModel);
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
						if(gridModel.isMatch()) finish();
					}else{
						gridModel.clear();
						rv.invalidate();
					}
				}
				if(action==MotionEvent.ACTION_UP){
					openTheApp();
					gridModel.clear();
					rv.invalidate();

				}
				return true;
			}
		});

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		Button btn = new Button(this);
//		btn.setText("Back");
//		btn.setLayoutParams(params);
//		btn.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent i = new Intent(GridActivity.this, DrawingActivity.class);
//				finish();
//				startActivity(i);
//
//			}
//		});
		RelativeLayout layout = new RelativeLayout(this);
//		layout.setOrientation(LinearLayout.VERTICAL);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params.topMargin = 250;
		rv.setLayoutParams(params);
		layout.addView(rv);
//		layout.addView(btn);
		LayoutParams layoutParam = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		this.addContentView(layout, layoutParam);
	}



	private void openTheApp(){
		try{
			DatabaseOperations dop = new DatabaseOperations(this);
			Cursor cr = dop.getInformationFromGridDB(dop);
			Cursor cr1 = dop.getInformation(dop);
			GridTranslater translater = new GridTranslater();
			cr.moveToFirst();
			List<Integer> check = new ArrayList<Integer>();
			String appName = "";
			boolean matchOrNot = false;
			do{
				for (int i = 1; i<=16; i++){
					check.add(cr.getInt(i));
				}
				Set<Position> savedGridPattern = translater.translateToGrid(check);
				gridModel.setRes(savedGridPattern);
				if(gridModel.isMatch()){
					matchOrNot = true;
					String relationName = cr.getString(0);
					cr1.moveToFirst();
					do{
						if(relationName.equals(cr1.getString(0))){
							appName = cr1.getString(2);
						}

					}while(cr1.moveToNext());

				}
				check.clear();
			}while(cr.moveToNext());

			if(matchOrNot){
				PackageInfoGenerator pg = new PackageInfoGenerator(GridActivity.this);
				Intent toOpen = pg.intentRecongnize(appName, pg);
				if(toOpen != null){
					startActivity(toOpen);
				}
			}
		}catch(Exception e){
			
		}

	}
}
