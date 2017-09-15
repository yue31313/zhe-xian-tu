package com.yzxy.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class MyDrawChartView extends View{

	public float s = 500;//默认中间线位置
	private boolean isTouched = false;//中间线点击位置变化控制
	
	private static final String[] SPPED_SCALES = new String[] { "0", "20", "40", "60", "80", "100", "120" ,"140"};
	private static final String[] SPPED_SCALES1 = new String[] { "0", "10", "20", "30", "40", "50", "60" ,"70"};
    
	private float[] points = new float[] { 33, 50, 59, 70, 83.5f, 100, 79 
			 							  ,33, 50, 59, 70, 83.5f, 100, 79
			 							  ,33, 50, 59, 70, 83.5f, 100, 79
			 							  ,33, 50, 59, 70, 83.5f, 100, 79
			 							  ,33, 50, 59};
    private String[] dates = new String[] { "12/1", "12/2", "12/3", "12/4", "12/5", "12/6", "12/7"
    										,"12/8", "12/9", "12/10", "12/11", "12/12", "12/13", "12/14"
    										,"12/15", "12/16", "12/17", "12/18", "12/19", "12/20", "12/21" 
    										,"12/22", "12/23", "12/24", "12/25", "12/26", "12/27", "12/28"
    										,"12/29", "12/30", "12/31"};
    private String[] dates1 = new String[] { "1", "2", "3", "4", "5", "6", "7"
			,"8", "9", "10", "11", "12", "13", "14"
			,"15", "16", "17", "18", "19", "20", "21" 
			,"22", "23", "24", "25", "26", "27", "28"
			,"29", "30", "31"};
	
    private float[] secondPoints = new float[]{14, 11, 22, 90, 33, 10, 66, 11
    										  ,14, 11, 22, 90, 33, 10, 66, 11
    										  ,14, 11, 22, 90, 33, 10, 66, 11
    										  ,14, 11, 22, 90, 33, 10, 66, 11
    										  ,14, 11, 22};
    
	public MyDrawChartView(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
	}
	
	public MyDrawChartView(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		super(context, attrs);
	}
	
	public MyDrawChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public void setData(int parentWidth, float[] points, String[] dates) {
		this.points = points;
		this.dates = dates1;
		
		if(points.length == 7) {
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.height = this.getHeight();//
			layoutParams.width = parentWidth;//this.getWidth();
			setLayoutParams(layoutParams);
		} else if(points.length > 7) {
			int xSpace = (parentWidth - 20) / 10;
			int width = xSpace * points.length + 20 + 30;
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.height = this.getHeight();
			layoutParams.width = width;
			setLayoutParams(layoutParams);
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		float gridX = 30;
		float gridY = getHeight() - 30;
		float gridWidth = getWidth() - 20;
	    float gridHeight = getHeight() - 60;
	    float beginX = gridX + 30;
	    float xSpace = (gridWidth - beginX - 30) / (this.points.length - 1);
		float ySpace = gridHeight/6;
		
		//
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.rgb(157, 157, 157));
		paint.setTextSize(12);
		paint.setTextAlign(Align.RIGHT);
		
		//
		float x1 = gridX;
		float x2 = gridWidth;
		float y;
		for(int n = 0; n < SPPED_SCALES1.length; n++) {
			y = gridY - n * ySpace;
			
			//
			canvas.drawLine(x1, y, x2, y, paint);
			canvas.drawText(SPPED_SCALES1[n], x1 - 6, y, paint);
		}
		
		//画一条垂直线, 即y轴。
		canvas.drawLine(gridX, gridY, gridX, gridY - gridHeight, paint);
//		paint.setColor(Color.GREEN);
//		paint.setStrokeWidth(3);
//		canvas.drawLine(s, gridY, s, gridY - gridHeight, paint);
		
		paint.setColor(Color.rgb(157, 157, 157));
		//
		float x;
		y = gridY + 20;
		paint.setTextAlign(Align.CENTER);
		for(int n = 0; n < dates1.length; n++) {
			//get x
			x = beginX + n * xSpace;
			// draw y text
			canvas.drawText(dates1[n], x, y, paint);
		}
		
		float lastPointX = 22;
		float lastPointY = 22;
		float currentPointX;
		float currentPointY;
		
		RectF pointRect = new RectF();
		
		paint.setColor(Color.rgb(5, 21, 67));
		paint.setStyle(Style.STROKE);//空心圆
		//绘制第一条线
		for(int n = 0; n < dates1.length; n++) {
			//get Current Point
			currentPointX = beginX + n * xSpace;
			currentPointY = points[n] / 120 * gridHeight;
			
			//draw line
			if(n > 0) {
				canvas.drawLine(lastPointX, lastPointY, currentPointX, currentPointY, paint);
				lastPointX = currentPointX;
				lastPointY = currentPointY;
				canvas.drawCircle(lastPointX, lastPointY, 6, paint);
//				canvas.drawCircle(currentPointY, currentPointY, 10, paint);
			}
			
			/*	
			//save point 
			lastPointX = currentPointX;
			lastPointY = currentPointY;
		
			// get point rectangle
			pointRect.left = currentPointX - 3;
			pointRect.top = currentPointY - 3;
			pointRect.right = currentPointX + 6;
			pointRect.bottom = currentPointY + 6;
			
			// draw 矩形 point
			canvas.drawRect(pointRect, paint);*/
		}
		
		//重新初始化点、
		lastPointX = 33;
		lastPointY = 33;
		currentPointX = 40;
		currentPointY = 40;
		
		paint.setStyle(Style.FILL);//实心圆、
		paint.setStrokeWidth(5);//线宽
		paint.setColor(Color.rgb(255, 151, 151));//线颜色、
	    //绘制第二条线、
		for(int n = 0; n < secondPoints.length; n++) {
			//get Current Point
			currentPointX = beginX + n * xSpace;
			currentPointY = secondPoints[n] / 120 * gridHeight;
			
			//draw line
			if(n > 0) {
				canvas.drawLine(lastPointX, lastPointY, currentPointX, currentPointY, paint);
				lastPointX = currentPointX;
				lastPointY = currentPointY;
				canvas.drawCircle(lastPointX, lastPointY, 10, paint);
			}
		}
		
		//中间线--
		paint.setColor(Color.GREEN);
		paint.setStrokeWidth(6);
		canvas.drawLine(s, gridY, s, gridY - gridHeight, paint);
	}
	
	//监听手势、
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.i("action-down", " action-down");
			if(event.getX() >=(s-50) && event.getX() <= (s+50)) {
				isTouched = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i("action-move", " action-move");
			if(isTouched) {
				s = event.getX();//将此时手势x坐标记录下来, 根据此x重绘中间线、
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			Log.i("action-up", " action-up");
			isTouched = false;
			s = event.getX();//记录当下位置坐标
			break;
		}
		
		return true;
	}

}