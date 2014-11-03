package com.example.navigationdrawer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Toast;
 
public class FragmentStatistics extends Fragment {
 
	private MySQLite db=null;
	private int maxID = 0;
	private double[] En = new double[7];
	private double[] Math = new double[7];
	private double[] Physical = new double[7];
	private double[] EnTest = new double[7];
	private double[] MathTest = new double[7];
	private double[] PhysicalTest = new double[7];
	private double[] Date = new double[] {0,1,2,3,4,5,6};
	private String[] dateString = new String[7];
    @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	public static Fragment newInstance(Context context) {
    	FragmentStatistics f = new FragmentStatistics();
 
        return f;
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	
    	db =new MySQLite(getActivity()); 
		db.OpenDB();
		
		
		DateComputing();
		
		Log.d(""+(int)Date[6]+" ", ""+Date[0]);
		
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_three, null);
        LinearLayout L1 = (LinearLayout) root.findViewById(R.id.L1);
        LinearLayout L2 = (LinearLayout) root.findViewById(R.id.L2);
        String[] titles = new String[] { "英文", "數學", "物理"}; // 定義折線的名稱
        
        List<double[]> x = new ArrayList<double[]>(); // 點的x坐標
        List<double[]> y = new ArrayList<double[]>(); // 點的y坐標
        
        List<double[]> x_test = new ArrayList<double[]>(); // 點的x坐標
        List<double[]> y_test = new ArrayList<double[]>(); // 點的y坐標
        
        // 數值X,Y坐標值輸入
        x.add(Date); //英文
        y.add(En); //英文
        
        x.add(Date); //數學
        y.add(Math); //數學
        
        x.add(Date);//物理
        y.add(Physical); //物理
        
        x_test.add(Date); //英文
        y_test.add(EnTest); //英文
        
        x_test.add(Date); //數學
        y_test.add(MathTest); //數學
        
        x_test.add(Date);//物理
        y_test.add(PhysicalTest); //物理
        
        XYMultipleSeriesDataset dataset = buildDatset(titles, x, y); // 推播
        
        XYMultipleSeriesDataset dataset_test = buildDatset(titles, x_test, y_test); // 測驗
        
        int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.RED};// 折線的顏色
        PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.CIRCLE, PointStyle.CIRCLE}; // 折線點的形狀
        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles, true);
        XYMultipleSeriesRenderer renderer_test = buildRenderer(colors, styles, true);
        //
        setChartSettings(renderer, "推播練習次數", "日期", "次數", Date[0], Date[6], 0, 25, Color.BLACK);// 定義折線圖
        
        GraphicalView chart =(GraphicalView)ChartFactory.getLineChartView(getActivity(), dataset, renderer);
        //setContentView(chart);
        android.view.ViewGroup.LayoutParams params = L1.getLayoutParams();
        	params.height = textsize(250);
        L1.addView(chart);
        //
        
        setChartSettings(renderer_test, "測驗練習次數", "日期", "次數", Date[0], Date[6], 0, 25, Color.BLACK);
        
        GraphicalView chart2 =(GraphicalView)ChartFactory.getLineChartView(getActivity(), dataset_test, renderer_test);
        android.view.ViewGroup.LayoutParams params2 = L2.getLayoutParams();
    		params2.height = textsize(250);
        L2.addView(chart2);
        return root;
    }
 

    protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
            String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor) {
        renderer.setChartTitle(title); // 折線圖名稱
        renderer.setChartTitleTextSize(24); // 折線圖名稱字形大小
        renderer.setXTitle(xTitle); // X軸名稱
        renderer.setYTitle(yTitle); // Y軸名稱
        renderer.setXAxisMin(xMin); // X軸顯示最小值
        renderer.setXAxisMax(xMax); // X軸顯示最大值
        renderer.setXLabelsColor(Color.BLACK); // X軸線顏色
        renderer.setYAxisMin(yMin); // Y軸顯示最小值
        renderer.setYAxisMax(yMax); // Y軸顯示最大值
        renderer.setAxesColor(axesColor); // 設定坐標軸顏色
        renderer.setYLabelsColor(0, Color.BLACK); // Y軸線顏色
        renderer.setLabelsColor(Color.BLACK); // 設定標籤顏色
        renderer.setMarginsColor(Color.WHITE); // 設定背景顏色
        renderer.setShowGrid(true); // 設定格線
        renderer.setDisplayChartValues(true); //顯示折線上點的數值
        renderer.setPanEnabled(true, false);  //X軸能捲動 Y軸無法捲動
        renderer.setAxisTitleTextSize(textsize(16));   //XY軸名稱大小
        //renderer.setZoomButtonsVisible(true);  //放大縮小按鈕
        renderer.setChartTitleTextSize(textsize(24));
        renderer.setLabelsTextSize(textsize(16));//刻度文字大小
        renderer.setLegendTextSize(textsize(13));//圖例文字大小
        renderer.setPointSize(textsize(2));  //點的大小
        renderer.setChartValuesTextSize(textsize(10)); //折線上的數值大小
        //renderer.seriesRenderer2.setLineWidth(textsize(3)); //線的寬度
        renderer.setZoomEnabled(true, false); //讓x能縮放 Y軸無法縮放
        //renderer.setXLabels(0); //自定義X軸座標名稱
        renderer.setMargins(new int[] {textsize(30), textsize(30), textsize(30),textsize(20)});  //上左下右邊距
        
        for (int i = 0; i < dateString.length; i++) 
        { 
        renderer.addTextLabel(i, dateString[i]);
        }
        renderer.setXLabelsAlign(Align.CENTER);
        renderer.setXLabels(0);
    }

    // 定義折線圖的格式
    private XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles, boolean fill) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            r.setFillPoints(fill);
            renderer.addSeriesRenderer(r); //將座標變成線加入圖中顯示
        }
        return renderer;
    }

    // 資料處理
    private XYMultipleSeriesDataset buildDatset(String[] titles, List<double[]> xValues,
            List<double[]> yValues) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        int length = titles.length; // 折線數量
        for (int i = 0; i < length; i++) {
            // XYseries對象,用於提供繪製的點集合的資料
            XYSeries series = new XYSeries(titles[i]); // 依據每條線的名稱新增
            double[] xV = xValues.get(i); // 獲取第i條線的資料
            double[] yV = yValues.get(i);
            int seriesLength = xV.length; // 有幾個點

            for (int k = 0; k < seriesLength; k++) // 每條線裡有幾個點
            {
                series.add(xV[k], yV[k]);
            }
            dataset.addSeries(series);
        }
        return dataset;
    }
    
    private int textsize(int size){
    	 DisplayMetrics dm = this.getResources().getDisplayMetrics();
    	 return (int)(size*dm.density);
    }
    
    private double DateComputing(){
    	
    	maxID = db.maxID(6);
    	Log.d("maxID", ""+maxID);
    	SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	String stringDate;
    	SimpleDateFormat mSimpleDateFormat_MD = new SimpleDateFormat("MM-dd");
    	String stringDate_MD;
    	double doubleDate;
    	Date mDate = new Date(System.currentTimeMillis());
    	Date mDate_MD = new Date(System.currentTimeMillis());
    	Date oldDate;
    	Date oldDate_MD;
    	Calendar mCalendar = Calendar.getInstance();
    	mCalendar.setTime(mDate);
    	Calendar mCalendar_MD = Calendar.getInstance();
    	mCalendar_MD.setTime(mDate_MD);
    	Cursor mCursor;
    	int EnScore = 0;
    	int MathScore = 0;
    	int PhysicalScore = 0;
    	int EnScoreTest = 0;
    	int MathScoreTest = 0;
    	int PhysicalScoreTest = 0;
    	// x = date ; y = point ;
    	
    	for(int i=0 ; i<7 ; i++){
    		if(i == 0){
    			mCalendar.add(Calendar.DAY_OF_MONTH, 0);
    		}else{
    			mCalendar.add(Calendar.DAY_OF_MONTH, -1);
        		mCalendar_MD.add(Calendar.DAY_OF_MONTH, -1);
    		}    		
    		oldDate = mCalendar.getTime();
    		oldDate_MD = mCalendar_MD.getTime();
    		stringDate = mSimpleDateFormat.format(oldDate);
    		Log.d("stringDate   --   "+i, stringDate);
    		stringDate_MD = mSimpleDateFormat_MD.format(oldDate_MD);
    		Log.d("stringDate_MD  --  "+i, stringDate_MD);
    		dateString[i] = stringDate_MD.replaceAll("-", "/");
    		EnScore = 0;
    		MathScore = 0;
    		PhysicalScore = 0;
    		EnScoreTest = 0;
        	MathScoreTest = 0;
        	PhysicalScoreTest = 0;
    		for(int j=1 ; j<=maxID ; j++){
    			
    			mCursor = db.get_statisics(j);
    			
    			if(stringDate.equals(mCursor.getString(1))){
    				if(mCursor.getString(2).equals("En")){
    					EnScore++;
    				}else if(mCursor.getString(2).equals("Math")){
    					MathScore++;
      				}else if(mCursor.getString(2).equals("Physics")){
      					PhysicalScore++;
      				}else if(mCursor.getString(2).equals("Test_En")){
      					EnScoreTest++;
    				}else if(mCursor.getString(2).equals("Test_Math")){
      					MathScoreTest++;
      				}else if(mCursor.getString(2).equals("Test_Physics")){
      					PhysicalScoreTest++;
      				}
    			}
    		}
    		En[i] = EnScore;
    		Math[i] = MathScore;
    		Physical[i] = PhysicalScore;
    		//Log.d("Physical"+i, stringDate+PhysicalScore);
    		EnTest[i] = EnScoreTest;
    		MathTest[i] = MathScoreTest;
    		PhysicalTest[i] = PhysicalScoreTest;
    	}
    	
    	return 0;
    }
}