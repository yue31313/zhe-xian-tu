package com.yzxy.draw;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

public class ChartView extends View {

	public int XPoint = 40; // 原点的X坐标
	public int YPoint = 460; // 原点的Y坐标
	public int XScale = 55; // X的刻度长度
	public int YScale = 60; // Y的刻度长度
	public int XLength = 500; // X轴的长度
	public int YLength = 470; // Y轴的长度
	public String[] XLabel; // X的刻度
	public String[] YLabel; // Y的刻度
	public String[] Data; // 数据
	public String Title; // 显示的标题
	private List<PointBean> allpoint = new ArrayList<PointBean>();// 保存所以的操作坐标
	private Paint xPaint;
	public static final int RECT_SIZE = 55;
	private Paint yPaint;
	private Paint dataPaint;
	private Paint yTxtPaint;

	private Paint dushuPaint;
	private Paint selectPaint;
	private int screenWidth;
	private int screenHeight;

	private String data = "";
	private String dushu="";
	private float x;
	private float y;
	private Paint ciclePaint;

	public ChartView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void SetInfo(String[] XLabels, String[] YLabels, String[] AllData,
			Display display) {
		XLabel = XLabels;
		YLabel = YLabels;
		Data = AllData;
		this.screenWidth = display.getWidth();
		this.screenHeight = display.getHeight();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);// 重写onDraw方法

		xPaint = new Paint();
		xPaint.setStyle(Paint.Style.STROKE);
		xPaint.setAntiAlias(true);// 去锯齿
		xPaint.setColor(Color.RED);// 颜色
		xPaint.setTextSize(25); // 设置轴文字大小

		yPaint = new Paint();
		PathEffect effects = new DashPathEffect(new float[] { 5, 5, 5, 5 }, 1);
		yPaint.setPathEffect(effects);
		yPaint.setStyle(Paint.Style.STROKE);
		yPaint.setAntiAlias(true);// 去锯齿
		yPaint.setColor(Color.parseColor("#808b98"));// 颜色

		yTxtPaint = new Paint();
		yTxtPaint.setStyle(Paint.Style.STROKE);
		yTxtPaint.setAntiAlias(true);// 去锯齿
		yTxtPaint.setColor(Color.parseColor("#808b98"));// 颜色
		yTxtPaint.setTextSize(22); // 设置轴文字大小

		dataPaint = new Paint();
		dataPaint.setStyle(Paint.Style.FILL);
		dataPaint.setAntiAlias(true);// 去锯齿
		dataPaint.setColor(Color.parseColor("#37b99c"));// 颜色
		dataPaint.setTextSize(22); // 设置轴文字大小
		dataPaint.setStrokeWidth(4);

		ciclePaint = new Paint();
		ciclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
		ciclePaint.setAntiAlias(true);// 去锯齿
		ciclePaint.setColor(Color.parseColor("#37b99c"));// 颜色

		selectPaint = new Paint();
		selectPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		selectPaint.setAntiAlias(true);// 去锯齿
		selectPaint.setColor(Color.WHITE);// 颜色
		selectPaint.setTextSize(22); // 设置轴文字大小
		
		dushuPaint= new Paint();

		dushuPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		dushuPaint.setAntiAlias(true);// 去锯齿
		dushuPaint.setColor(Color.RED);// 颜色
		dushuPaint.setTextSize(30);
	
		
		// 设置Y轴
		// canvas.drawLine(XPoint, YPoint - YLength, XPoint, YPoint, paint); //
		// 轴线
		for (int i = 0; i * YScale < YLength; i++) {

			canvas.drawLine(XPoint + 20, YPoint - i * YScale, XPoint + XLength,
					YPoint - i * YScale, yPaint); // 刻度
			try {
				canvas.drawText(YLabel[i], XPoint - 30,YPoint - i * YScale + 5, yTxtPaint); // 文字
				
			} catch (Exception e) {
			}

		}
		// canvas.drawLine(XPoint, YPoint - YLength, XPoint - 3, YPoint -
		// YLength+ 6, paint); // 箭头
		// canvas.drawLine(XPoint, YPoint - YLength, XPoint + 3, YPoint -
		// YLength + 6, paint);
		// 设置X轴
		canvas.drawLine(XPoint - 20, YPoint + 70, XPoint + XLength,
				YPoint + 70, xPaint); // 轴线
		canvas.drawText("4月", XPoint - 20, YPoint + 60, xPaint);
		for (int i = 0; i * XScale < XLength; i++) {
			// canvas.drawLine(XPoint + i * XScale+35, YPoint, XPoint + i *
			// XScale+35,YPoint , xPaint); // 刻度
			try {
				canvas.drawText(XLabel[i], XPoint + i * XScale + 25,
						YPoint + 110, xPaint); // 文字
				PointBean bean = new PointBean();
				Point point = new Point(XPoint + i * XScale + 25, YPoint + 110);
				bean.point = point;
				bean.title = XLabel[i];
				

				// 数据值
				if (i > 0 && YCoord(Data[i - 1]) != -999&& YCoord(Data[i]) != -999) // 保证有效数据
					canvas.drawLine(XPoint + (i - 1) * XScale + 35,YCoord(Data[i - 1]), XPoint + i * XScale + 35,YCoord(Data[i]), dataPaint);
				canvas.drawCircle(XPoint + i * XScale + 35, YCoord(Data[i]), 8,dataPaint);
				bean.dushu=Data[i];
				allpoint.add(bean);
			} catch (Exception e) {
			}
		}

		canvas.drawCircle(x + 9, y - 8, 22, ciclePaint);
		canvas.drawCircle(x + 13,YCoord(dushu),16,dushuPaint);
		canvas.drawText(dushu+"°", x + 45, YCoord(dushu)-10, dushuPaint);
		canvas.drawText(data, x - 2, y, selectPaint);
		// canvas.drawLine(XPoint + XLength, YPoint, XPoint + XLength - 6,
		// YPoint - 3, paint); // 箭头
		// canvas.drawLine(XPoint + XLength, YPoint, XPoint + XLength - 6,YPoint
		// + 3, paint);
		// paint.setTextSize(16);
		// canvas.drawText(Title, 200, 160, paint);
	}

	private float YCoord(String y0) // 计算绘制时的Y坐标，无数据时返回-999
	{
		float y;
		try {
			y = Float.parseFloat(y0);
		} catch (Exception e) {
			return -999; // 出错则返回-999
		}
		try {
			// YPoint -(y-36.2)/0.2*YScale;
			// return YPoint - y * YScale / Float.parseFloat(YLabel[1]);
			return (float) (YPoint - (y - 36.2) / 0.2 * YScale);
		} catch (Exception e) {
		}
		return y;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			for (int i = 0; i < allpoint.size(); i++) {
				if (pointToRect(allpoint.get(i).point).contains(event.getX(),
						event.getY())) {

					PointBean bean = allpoint.get(i);
					Point point = bean.point;

					x = point.x;
					y = point.y;

					data = bean.title;

					dushu=bean.dushu;
					postInvalidate();
					Log.e("信息>>>>>>>>>>>>>>>>>", "yes!!!"+data+"dushu"+dushu);

				}
			}

			break;

		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:

			break;
		default:
			break;
		}

		return true;
	}

	private RectF pointToRect(Point p) {
		return new RectF(p.x - RECT_SIZE / 2, p.y - RECT_SIZE / 2, p.x
				+ RECT_SIZE / 2, p.y + RECT_SIZE / 2);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub

		if (XLength > screenWidth) {
			int with = XLabel.length * XScale + XPoint + 25 * 2;

			setMeasuredDimension(with, screenHeight);

		} else {
			setMeasuredDimension(screenWidth, screenHeight);
		}
	}

}
