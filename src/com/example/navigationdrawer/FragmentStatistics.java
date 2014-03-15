package com.example.navigationdrawer;

import java.util.ArrayList;
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
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Toast;
 
public class FragmentStatistics extends Fragment {
 
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
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_three, null);
        LinearLayout L1 = (LinearLayout) root.findViewById(R.id.L1);
        LinearLayout L2 = (LinearLayout) root.findViewById(R.id.L2);
        String[] titles = new String[] { "折線1", "折線2" }; // 定義折線的名稱
        List<double[]> x = new ArrayList<double[]>(); // 點的x坐標
        List<double[]> y = new ArrayList<double[]>(); // 點的y坐標
        // 數值X,Y坐標值輸入
        x.add(new double[] { 1, 3, 5, 7, 9, 11 });
        x.add(new double[] { 0, 2, 4, 6, 8, 10 ,13});
        y.add(new double[] { 3, 14, 8, 22, 16, 18 });
        y.add(new double[] { 20, 18, 15, 12, 10, 8 ,5});
        XYMultipleSeriesDataset dataset = buildDatset(titles, x, y); // 儲存座標值

        int[] colors = new int[] { Color.BLUE, Color.GREEN };// 折線的顏色
        PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND }; // 折線點的形狀
        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles, true);

        setChartSettings(renderer, "推播練習次數", "X軸名稱", "Y軸名稱", 0, 12, 0, 25, Color.BLACK);// 定義折線圖
        
        GraphicalView chart =(GraphicalView)ChartFactory.getLineChartView(getActivity(), dataset, renderer);
        //setContentView(chart);
        android.view.ViewGroup.LayoutParams params = L1.getLayoutParams();
        	params.height = textsize(250);
        L1.addView(chart);
        
        GraphicalView chart2 =(GraphicalView)ChartFactory.getLineChartView(getActivity(), dataset, renderer);
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
}