package com.dxb.android_163_client.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 新手引导页
 * @author dongxiaobing
 *
 */
public class GuideActivity extends Activity {
	
	public final int[] imageList=new int[] {R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
	private ViewPager vp_page;
	private Button bt_start;
	private ArrayList<ImageView> imageArrayList;
	private LinearLayout lv_yuan;
	private int mWidth;//圆点之间距离
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		//初始化viewPager
		init_viewPager();
		//初始化底部圆
		init_yuan();
		click_start_button();
	}
	

	private void init_yuan() {
		lv_yuan = (LinearLayout) findViewById(R.id.lv_yuan);
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(10,10);
		for(int i:imageList){
			View view=new View(this);
			view.setBackgroundResource(R.drawable.shape_yuan);
//			if(i>0){
//				params.leftMargin=10;
//			}
			view.setLayoutParams(params);
			lv_yuan.addView(view);
		}
		
		//当在一个视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变时，所要调用的回调函数的接口类
		//测量圆点之间的距离
		
		lv_yuan.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			

			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				System.out.println("圆点绘制完成");
				mWidth = lv_yuan.getChildAt(1).getLeft()-lv_yuan.getChildAt(0).getLeft();
				System.out.println("圆点之间的宽度是："+mWidth);
			}
		});
	}

	private void click_start_button() {
		bt_start=(Button) findViewById(R.id.bt_start);
		bt_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//点击开始体验按钮，保存已经设置过新手引导页到sharepreference
				SharedPreferences spf = getSharedPreferences("config", MODE_PRIVATE);
				spf.edit().putBoolean("guide_show", true).commit();
				
				//跳转到mainActivy
				startActivity(new Intent(GuideActivity.this, MainActivity.class));
				finish();
				
			}
		});
	}
	/**
	 * 初始化viewPager
	 */
	@SuppressWarnings("deprecation")
	public void init_viewPager() {
		vp_page=(ViewPager) findViewById(R.id.vp_page);
		
		imageArrayList=new ArrayList<ImageView>();
		for(int i:imageList){
			ImageView iv=new ImageView(this);
			iv.setBackgroundResource(i);
			imageArrayList.add(iv);
		}
		vp_page.setAdapter(new MyPageAdapter());
		
		vp_page.setOnPageChangeListener(new myPageChange());
	}
	
	/*
	 * 给viewPager设置适配器
	 */
	class MyPageAdapter extends PagerAdapter{
		@Override
		public int getCount() {
			return imageArrayList.size();
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(imageArrayList.get(position));
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(imageArrayList.get(position));
			return imageArrayList.get(position);
		}
	}
	
	
	
	class myPageChange implements OnPageChangeListener{
		//滑动的状态，arg0 ==1的时候表示正在滑动，arg0==2的时候表示滑动完毕了，arg0==0的时候表示什么都没做，就是停在那
		@Override
		public void onPageScrollStateChanged(int arg0) {
			//System.out.println("onPageScrollStateChanged");
		}
		//当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到调用。其中三个参数的含义分别为
		/*
		 * arg0 :当前页面，及你点击滑动的页面
		 * arg1:当前页面偏移的百分比
		 * arg2:当前页面偏移的像素位置  
		 */ 
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			//System.out.println("onPageScrolled");
			
			//在屏幕滑动的时候，根据偏移的百分比计算红色圆点的偏移量，根据这个偏移量绘制红色圆点的位置
			
			View lv_yuan_red = findViewById(R.id.lv_yuan_red);
			int len=(int)(mWidth*arg1)+arg0*mWidth;
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)lv_yuan_red.getLayoutParams();
			params.leftMargin=len;
			lv_yuan_red.setLayoutParams(params);//重新绘制
			
			
		}
		//arg0是表示你当前选中的页面，这事件是在你页面跳转完毕的时候调用的
		@Override
		public void onPageSelected(int arg0) {
			System.out.println("onPageSelected");
		}
		
	}
}
