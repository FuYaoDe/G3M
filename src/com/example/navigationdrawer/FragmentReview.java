package com.example.navigationdrawer;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
 
public class FragmentReview extends Fragment{

	private static String[] titles = new String[]
			{ 
				"英文","數學","物理"
			};
	
	public static Fragment newInstance(Context context) {
		FragmentReview f = new FragmentReview();
		
        return f;
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_one, null);
        ViewPager viewPager1 = (ViewPager) root.findViewById(R.id.viewpager1);
		viewPager1.setAdapter(new MyPagerAdapter());
        return root;
        //
    }
    
    private class MyPagerAdapter extends PagerAdapter
	{
		@Override
		public int getCount()
		{
			return titles.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return view.equals(object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			TextView text = new TextView(getActivity());
			text.setText(titles[position]);
			container.addView(text);
			return text;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			if (object instanceof View)
			{
				container.removeView((View) object);
			}
		}
		
		@Override
		public CharSequence getPageTitle(int position)
		{
			return titles[position];
		}
	}

	public static class MyFragment extends Fragment
	{		
		private static final String TAG = "MyFragment";
		public MyFragment()
		{
			super();
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			View view = inflater.inflate(R.layout.fragment_one, container, false);
			View text = view.findViewById(android.R.id.text1);
			if(text != null && text instanceof TextView)
			{
				((TextView)text).setText(titles[getArguments().getInt("position")]);
			}
			return view;
		}
	   
		@Override
		public void onAttach(Activity activity)
		{
			super.onAttach(activity);
			//Log.d(TAG, "Attached " + getArguments().getInt("position"));
		}
		
		@Override
		public void onDetach()
		{
			//Log.d(TAG, "Detached " + getArguments().getInt("position"));
			super.onDetach();
		}
	}
}

