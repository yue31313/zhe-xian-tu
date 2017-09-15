package com.yzxy.draw;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Timer;

import com.yzxy.draw.MyChartView.Mstyle;
import com.yzxy.draw.tools.Tools;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.app.Activity;
import android.graphics.Bitmap;



public class MainActivity extends Activity {

	MyChartView tu;
	Button BT_Add;
	Timer mTimer =new Timer();
	HashMap<Double, Double> map;
	Double key=8.0;
	Double value=0.0;
	Tools tool=new Tools();
	ChartView chartView;
	RelativeLayout layout; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		//chartView = (ChartView) findViewById(R.id.charview);
	ChartView view = new ChartView(this); 
	             layout=(RelativeLayout) this.findViewById(R.id.rela);
//	         	// X轴刻度  4
		view.SetInfo(new String[] { "21", "22", "23", "24", "25", "26", "27","28" }, 
				// Y轴刻度  5                            
				new String[] { "36.2", "36.4", "36.6", "36.8", "37.0", "37.2","37.4","37.6" }, 
				// 数据  6                         
				new String[] { "37.5", "36.2", "36.4", "37.0", "36.8", "36.1", "36.6","36.5" }, 
			                            
				getWindowManager().getDefaultDisplay()); 
		
		
//		CicleView view = new CicleView(this);
//		view.SetInfo(new String[] { "月经开始","排卵日","下次月经开始" }, 
//				// Y轴刻度  5                            
//				new String[] { "36.0", "36.5", "37.0", "37.5", "38.0"}, 
//			// 数据  6                         
//				new String[] { "37.5", "36.2", "36.4", "37.0", "36.8", "36.1", "36.6","36.5" }, 
//			                            
//				getWindowManager().getDefaultDisplay()); 

		layout.addView(view);

		//BT_Add=(Button)findViewById(R.id.bt_add);
//		tu= (MyChartView)findViewById(R.id.menulist);
//		tu.SetTuView(map,50,10,"x","y",false);
//		map=new HashMap<Double, Double>();
//		map.put(1.0, (double) 0);
//    	map.put(3.0, 25.0);
//    	map.put(4.0, 32.0);
//    	map.put(5.0, 41.0);
//    	map.put(6.0, 16.0);
//    	map.put(7.0, 36.0);
//    	map.put(8.0, 26.0);
//    	tu.setTotalvalue(50);
//    	tu.setPjvalue(10);
//    	tu.setMap(map);
////		tu.setXstr("");
////		tu.setYstr("");
//		tu.setMargint(20);
//		tu.setMarginb(50);
//		tu.setMstyle(Mstyle.Line);
//	
//		BT_Add.setOnClickListener(new click());
	}

	class click implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			Random rd=new Random(System.currentTimeMillis());
			Double temp= rd.nextDouble();
			randmap(map, temp*50);
		}}
	
	private void randmap(HashMap<Double, Double> mp,Double d)
	{
		ArrayList<Double> dz=tool.getintfrommap(mp);
		Double[] dvz=new Double[mp.size()];
		int t=0;
		@SuppressWarnings("rawtypes")
		Set set= mp.entrySet();   
        @SuppressWarnings("rawtypes")
		Iterator iterator = set.iterator();
		 while(iterator.hasNext())
		{  
			@SuppressWarnings("rawtypes")
			Map.Entry mapentry  = (Map.Entry)iterator.next();   
			dvz[t]=(Double)mapentry.getValue();
			t+=1;
		} 
		 for(int j=0;j<dz.size()-1;j++)
		 {
			 mp.put(dz.get(j), mp.get(dz.get(j+1)));
		 }
		 mp.put((Double)dz.get(mp.size()-1), d);
		 tu.postInvalidate();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	        // TODO Auto-generated method stub
	        if(item.getItemId() == R.id.menu_settings)
	        {
	        	if (false == tu.isDrawingCacheEnabled()) 
	    		{  
	                tu.setDrawingCacheEnabled(true);
	            }  
	        	Bitmap bitmap = tu.getDrawingCache();  
	        	Tools tool=new Tools();
	        	try {
					Boolean b=tool.saveFile(bitmap, " ");
					if(b)
					Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        if(item.getItemId() == R.id.menu_ch)
	        {
	        	tu.setMstyle(Mstyle.Curve);
	        	tu.setIsylineshow(true);
	        	tu.postInvalidate();
	        }
	        if(item.getItemId() == R.id.menu_ch2)
	        {

	        	tu.setMstyle(Mstyle.Line);
	        	tu.setIsylineshow(false);
	        	tu.postInvalidate();
	        }
	        return true;
	  }

}
