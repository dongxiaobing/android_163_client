package com.dxb.android_163_client.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.Display.Mode;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {
	private RelativeLayout rlSplash;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		rlSplash = (RelativeLayout) findViewById(R.id.rl_splash);
		startAnimation();
	}

	private void startAnimation() {
		// TODO Auto-generated method stub
		AnimationSet as=new AnimationSet(false);
		
		
		
		//缩放动画
		ScaleAnimation sa=new ScaleAnimation(0, 1, 0, 1,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		sa.setDuration(3000);
		as.addAnimation(sa);
		//旋转动画
		RotateAnimation ra=new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		ra.setDuration(3000);
		as.addAnimation(ra);
		
		//渐变动画
		AlphaAnimation aa=new AlphaAnimation(0, 1);
		aa.setDuration(3000);
		as.addAnimation(aa);
		
		
		
		//布局文件开始动画效果
		rlSplash.startAnimation(as);
		
		as.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			// 动画执行结束
			@Override
			public void onAnimationEnd(Animation animation) {
				jumpNextPage();
			}

			
		});
	}
	
	/**
	 * 跳转到新手引导页
	 * 如果之前有设置新手引导页，则后续再次打开不用显示新手引导页
	 */
	private void jumpNextPage() {
		SharedPreferences spf = getSharedPreferences("config", MODE_PRIVATE);
		boolean boolean1 = spf.getBoolean("guide_show",false);
		if(!boolean1){
			//之前没有设置新手引导页
			
			startActivity(new Intent(SplashActivity.this, GuideActivity.class));
		}else{
			//之前打开过新手引导页
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
		}
		finish();
	}
}
