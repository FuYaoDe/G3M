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
	private double[] En = new double[7];;
	private double[] Math = new double[7];;
	private double[] Physical = new double[7];;
	private double[] Date = new double[] {0,1,2,3,4,5,6};
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
        String[] titles = new String[] { "�^��", "�ƾ�", "���z"}; // �w�q��u���W��
        List<double[]> x = new ArrayList<double[]>(); // �I��x����
        List<double[]> y = new ArrayList<double[]>(); // �I��y����
        
        // �ƭ�X,Y���Эȿ�J
        x.add(Date); //�^��
        y.add(En); //�^��
        
        x.add(Date); //�ƾ�
        y.add(Math); //�ƾ�
        
        x.add(Date);//���z
        y.add(Physical); //���z
        
        XYMultipleSeriesDataset dataset = buildDatset(titles, x, y); // �x�s�y�Э�

        int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.RED};// ��u���C��
        PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.CIRCLE, PointStyle.CIRCLE}; // ��u�I���Ϊ�
        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles, true);

        setChartSettings(renderer, "�����m�ߦ���", "X�b�W��", "Y�b�W��", Date[0], Date[6], 0, 25, Color.BLACK);// �w�q��u��
        
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
        renderer.setChartTitle(title); // ��u�ϦW��
        renderer.setChartTitleTextSize(24); // ��u�ϦW�٦r�Τj�p
        renderer.setXTitle(xTitle); // X�b�W��
        renderer.setYTitle(yTitle); // Y�b�W��
        renderer.setXAxisMin(xMin); // X�b��̤ܳp��
        renderer.setXAxisMax(xMax); // X�b��̤ܳj��
        renderer.setXLabelsColor(Color.BLACK); // X�b�u�C��
        renderer.setYAxisMin(yMin); // Y�b��̤ܳp��
        renderer.setYAxisMax(yMax); // Y�b��̤ܳj��
        renderer.setAxesColor(axesColor); // �]�w���жb�C��
        renderer.setYLabelsColor(0, Color.BLACK); // Y�b�u�C��
        renderer.setLabelsColor(Color.BLACK); // �]�w�����C��
        renderer.setMarginsColor(Color.WHITE); // �]�w�I���C��
        renderer.setShowGrid(true); // �]�w��u
        renderer.setDisplayChartValues(true); //��ܧ�u�W�I���ƭ�
        renderer.setPanEnabled(true, false);  //X�b�౲�� Y�b�L�k����
        renderer.setAxisTitleTextSize(textsize(16));   //XY�b�W�٤j�p
        //renderer.setZoomButtonsVisible(true);  //��j�Y�p���s
        renderer.setChartTitleTextSize(textsize(24));
        renderer.setLabelsTextSize(textsize(16));//��פ�r�j�p
        renderer.setLegendTextSize(textsize(13));//�ϨҤ�r�j�p
        renderer.setPointSize(textsize(2));  //�I���j�p
        renderer.setChartValuesTextSize(textsize(10)); //��u�W���ƭȤj�p
        //renderer.seriesRenderer2.setLineWidth(textsize(3)); //�u���e��
        renderer.setZoomEnabled(true, false); //��x���Y�� Y�b�L�k�Y��
        //renderer.setXLabels(0); //�۩w�qX�b�y�ЦW��
        renderer.setMargins(new int[] {textsize(30), textsize(30), textsize(30),textsize(20)});  //�W���U�k��Z

    }

    // �w�q��u�Ϫ��榡
    private XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles, boolean fill) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            r.setFillPoints(fill);
            renderer.addSeriesRenderer(r); //�N�y���ܦ��u�[�J�Ϥ����
        }
        return renderer;
    }

    // ��ƳB�z
    private XYMultipleSeriesDataset buildDatset(String[] titles, List<double[]> xValues,
            List<double[]> yValues) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        int length = titles.length; // ��u�ƶq
        for (int i = 0; i < length; i++) {
            // XYseries��H,�Ω󴣨�ø�s���I���X�����
            XYSeries series = new XYSeries(titles[i]); // �̾ڨC���u���W�ٷs�W
            double[] xV = xValues.get(i); // �����i���u�����
            double[] yV = yValues.get(i);
            int seriesLength = xV.length; // ���X���I

            for (int k = 0; k < seriesLength; k++) // �C���u�̦��X���I
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
    	double doubleDate;
    	Date mDate = new Date(System.currentTimeMillis());
    	Date oldDate;
    	Calendar mCalendar = Calendar.getInstance();
    	mCalendar.setTime(mDate);
    	Cursor mCursor;
    	int EnScore = 0;
    	int MathScore = 0;
    	int PhysicalScore = 0;
    	// x = date ; y = point ;
    	
    	for(int i=0 ; i<7 ; i++){
    		mCalendar.add(Calendar.DATE, -i);
    		oldDate = mCalendar.getTime();
    		stringDate = mSimpleDateFormat.format(oldDate);
    		//Date[i] = Double.parseDouble(stringDate.replaceAll("-", ""));
    		EnScore = 0;
    		MathScore = 0;
    		PhysicalScore = 0;
    		for(int j=1 ; j<=maxID ; j++){
    			
    			mCursor = db.get_statisics(j);
    			
    			if(stringDate.equals(mCursor.getString(1))){
    				if(mCursor.getString(2).equals("En")){
    					EnScore++;
    				}else if(mCursor.getString(2).equals("Math")){
    					MathScore++;
      				}else if(mCursor.getString(2).equals("Physical")){
      					PhysicalScore++;
      				}
    			}
    		}
    		En[i] = EnScore;;
    		Math[i] = MathScore;
    		Physical[i] = PhysicalScore;
    	}
    	
    	return 0;
    }
}