package com.example.navigationdrawer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

 
public class FragmentReview extends Fragment{

	public static String[] titles = new String[]
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
        viewPager1.setAdapter(new MyFragmentPagerAdapter(getFragmentManager()));
        return root;
        //
    }
	private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter
	{
		public MyFragmentPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position)
		{
			Bundle args = new Bundle();
			Log.d("position", Integer.toString(position));
			args.putInt("position", position);
			if(position==0)
			{
				return Fragment.instantiate(getActivity(), Fragment_english_list.class.getName(), args);
			}
			else
			{
				return Fragment.instantiate(getActivity(), Fragment_formula_list.class.getName(), args);
			}
		}

		@Override
		public int getCount()
		{
			return titles.length;
		}
		
		@Override
		public CharSequence getPageTitle(int position)
		{
			return titles[position];
		}
	}
}

