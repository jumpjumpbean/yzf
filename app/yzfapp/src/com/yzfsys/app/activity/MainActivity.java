package com.yzfsys.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yzfsys.app.AppManager;
import com.yzfsys.app.R;
import com.yzfsys.app.fragment.MenuFragment;
import com.yzfsys.app.fragment.OrderListFragment;

public class MainActivity extends BaseSlidingActivity 
	implements View.OnClickListener, SlidingMenu.OnClosedListener {

	private Fragment mContent;
	private long mKeyBackWaitTime = 2000;
	private long mKeyBackTouchTime = 0;
	protected Fragment mFrag;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = new MenuFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
		}
		
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new OrderListFragment();	
		
		// set the Above View
		setContentView(R.layout.activity_main);
		mTvTitle = (TextView)findViewById(R.id.tv_ab_title);
		
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);		
//		sm.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		setSlidingActionBarEnabled(false);
		
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mContent).commit();
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getSupportMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		getSlidingMenu().showContent();
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.iv_ab_menu_left:
			getSlidingMenu().showMenu();
			break;
		default:
			break;
		}
	}	

	@Override
	public void onClosed()
	{
		
	}
	
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
			long currentTime = System.currentTimeMillis();
			if((currentTime - mKeyBackTouchTime) >= mKeyBackWaitTime) {
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				mKeyBackTouchTime = currentTime;
			}else {
				AppManager.getInstance().AppExit(MainActivity.this);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
