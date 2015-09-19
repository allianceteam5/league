package com.league.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**  
 *   
 * @author liugang  
 * @date 2015年9月15日   
 */
public class ViewPaperAdapter extends PagerAdapter{

	private List<ImageView> mImageViewList;
	public ViewPaperAdapter(List<ImageView> mlist) {
		// TODO Auto-generated constructor stub
		super();
		mImageViewList=mlist;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView(mImageViewList.get(position%mImageViewList.size()));
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		container.addView(mImageViewList.get(position % mImageViewList.size()));  
        return mImageViewList.get(position % mImageViewList.size());  
	}
	
}
