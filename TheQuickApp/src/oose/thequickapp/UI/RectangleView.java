package oose.thequickapp.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

/**
 * This class defines the 4*4 board on the main interface when
 * the phone's screen is on or it boots. The color of grid is
 * cyan, while it turns red when the grid is touched.
 * 
 * @author zcy1848
 *
 */
public class RectangleView extends View{
	public final int NUM = 4;
	public final int WIDTH = 150;
	final int STROKE_WIDTH = 2;
	int viewWidth;
	int viewHeight;
	public int left;
	public int top;
	GridModel model;
	Paint paint;

	/**
	 * This constructs the class given the context of the interface
	 * and the model given to the board.
	 * @param context the context of the interface
	 * @param model the model given to the board
	 */
	public RectangleView(Context context, GridModel model) {
		super(context);
		this.model = model;
		this.paint = new Paint();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		viewWidth = display.getWidth();
		viewHeight = display.getHeight()/2;
		left = (viewWidth-WIDTH*NUM)/2;
		top = (viewHeight-WIDTH*NUM)/2;
		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, viewHeight));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setStrokeWidth(0);
//		paint.setColor(Color.BLACK);
//		canvas.drawRect(left, top, left+WIDTH*NUM, top+WIDTH*NUM, paint);
		for(int j=0;j<NUM;j++)
			for(int i=0;i<NUM;i++){
				if(!model.check(j, i)){
					paint.setColor(Color.CYAN);
				}else{
					paint.setColor(Color.RED);
				}
				int x = left+WIDTH*j;
				int y = top+WIDTH*i;
				RectF r = new RectF(x+STROKE_WIDTH, y+STROKE_WIDTH, x+WIDTH-STROKE_WIDTH, y+WIDTH-STROKE_WIDTH);
//				canvas.drawRect(r, paint);
				canvas.drawRoundRect(r, 90, 90, paint);
			}
	}
}