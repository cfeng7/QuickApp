package oose.thequickapp.UI;


import oose.thequickapp.R;

//import com.mathworks.toolbox.javabuilder.MWException;

//import test1.WaveR;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;


/**
 * RectangleView is a kind of View which represents the grid user may want to draw on
 * @author Group 12
 *
 */
public class TempView extends View{
	public final int NUM = 4;
	public final int WIDTH = 200;
	public final int STROKE_WIDTH = 2;
	int windowWidth;
	int windowHeight;
	public int left;
	public int top;
	GridSettingModel model;
	Paint paint;
	Canvas can = null;
	public TempView(Context context, GridSettingModel model){
		super(context);
		this.model = model;
		this.paint = new Paint();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		windowWidth = display.getWidth();
		windowHeight = display.getHeight();
		left = (windowWidth-WIDTH*NUM)/2;
		top = (windowHeight-WIDTH*NUM)/2;
	
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//paint.setStrokeWidth(0);
		//paint.setColor(Color.WHITE);
		//canvas.drawRect(left, top, left+WIDTH*NUM, top+WIDTH*NUM, paint);
		Rect rc = new Rect(left, top, left+WIDTH*NUM, top+WIDTH*NUM);
		can = canvas;
		
		Drawable d0=getResources().getDrawable(R.drawable.blank); //xxx根据自己的情况获取drawable
		BitmapDrawable bd0 = (BitmapDrawable) d0;
		Bitmap bm0 = bd0.getBitmap();
		
		Drawable d1=getResources().getDrawable(R.drawable.error); //xxx根据自己的情况获取drawable
		BitmapDrawable bd1 = (BitmapDrawable) d1;
		Bitmap bm1 = bd1.getBitmap();
		for(int j=0;j<NUM;j++)
			for(int i=0;i<NUM;i++){
				Bitmap bm = null;
				
				if(!model.check(j, i)){
					bm=getBmIndex(i*NUM+j+1);
					 paint.setAlpha(50);
				}else{
					bm=getBmIndex(i*NUM+j+1);
					 paint.setAlpha(255);
				}
				
				
				int x = left+WIDTH*j;
				int y = top+WIDTH*i;
				Rect r = new Rect(x+STROKE_WIDTH, y+STROKE_WIDTH, x+WIDTH-STROKE_WIDTH, y+WIDTH-STROKE_WIDTH);
				
               
				canvas.drawBitmap(bm, null, r, paint);
			//	canvas.dra
				
				//canvas.drawRect(r, paint);
			}
		
	 

	}
	
	class Shake implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public void setColorOn(){
		for(int j=0;j<NUM;j++)
			for(int i=0;i<NUM;i++){
				Bitmap bm = null;
				
					bm=getBmIndex(i*NUM+j+1);
					paint.setColor(Color.RED);
	int x = left+WIDTH*j;
				int y = top+WIDTH*i;
				Rect r = new Rect(x+STROKE_WIDTH, y+STROKE_WIDTH, x+WIDTH-STROKE_WIDTH, y+WIDTH-STROKE_WIDTH);
				
                paint.setAlpha(100);
  
				//can.drawBitmap(bm, null, r, paint);
			//	canvas.dra
				
				//canvas.drawRect(r, paint);
			}
	}
	public Bitmap getBmIndex(int index){
		Drawable d=null;
		switch(index){
		case 1:d=getResources().getDrawable(R.drawable.a); break;
		case 2:d=getResources().getDrawable(R.drawable.b); break;
		case 3:d=getResources().getDrawable(R.drawable.c); break;
		case 4:d=getResources().getDrawable(R.drawable.d); break;
		case 5:d=getResources().getDrawable(R.drawable.e); break;
		case 6:d=getResources().getDrawable(R.drawable.f); break;
		case 7:d=getResources().getDrawable(R.drawable.g); break;
		case 8:d=getResources().getDrawable(R.drawable.h); break;
		case 9:d=getResources().getDrawable(R.drawable.i); break;
		case 10:d=getResources().getDrawable(R.drawable.j); break;
		case 11:d=getResources().getDrawable(R.drawable.k); break;
		case 12:d=getResources().getDrawable(R.drawable.l); break;
		case 13:d=getResources().getDrawable(R.drawable.m); break;
		case 14:d=getResources().getDrawable(R.drawable.n); break;
		case 15:d=getResources().getDrawable(R.drawable.o); break;
		case 16:d=getResources().getDrawable(R.drawable.p); break;
		default: d=getResources().getDrawable(R.drawable.blank); break;
		}
		BitmapDrawable bd = (BitmapDrawable) d;
		bd.setAlpha(122);
		Bitmap bm = bd.getBitmap();
          return bm;
	}
	
 
}
