package com.yzfsys.app.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.yzfsys.app.AppManager;

/**
 * The base class of Activity
 * 
 * @jacob niu
 */
public class BaseSlidingActivity extends SlidingFragmentActivity {

	private boolean mAllowFullScreen = true;
	private boolean mAllowDestroy = true;
	private View mView;	
	protected TextView mTvTitle;	
	protected boolean mInError = false;


//	public BaseActivity(int titleRes) {
//		mTitleRes = titleRes;
//	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAllowFullScreen = true;

//		setTitle(mTitleRes);
		// Add activity to stack
		AppManager.getInstance().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// Finish activity and remove from stack
		AppManager.getInstance().finishActivity(this);
	}

	public void setTitleText(String title)
	{
		mTvTitle.setText(title);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getSupportMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
	
	public boolean isAllowFullScreen() {
		return mAllowFullScreen;
	}

	/**
	 * 鐠佸墽鐤嗛弰顖氭儊閸欘垯浜掗崗銊ョ潌
	 * 
	 * @param allowFullScreen
	 */
	public void setAllowFullScreen(boolean allowFullScreen) {
		this.mAllowFullScreen = allowFullScreen;
	}

	public void setAllowDestroy(boolean allowDestroy) {
		this.mAllowDestroy = allowDestroy;
	}

	public void setAllowDestroy(boolean allowDestroy, View view) {
		this.mAllowDestroy = allowDestroy;
		this.mView = view;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && mView != null) {
			mView.onKeyDown(keyCode, event);
			if (!mAllowDestroy) {
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

    protected void showErrorDialog() {
		mInError = true;
		Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show();
    }
	
}
