package com.yzfsys.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yzfsys.app.AppManager;
import com.yzfsys.app.R;

/**
 * The base class of Activity
 * 
 */
public class BaseActivity extends Activity {

	private boolean mAllowFullScreen = true;
	private boolean mAllowDestroy = true;
	private View mView;
	private boolean mIsNeedTitleBack = true;
	protected TextView mTvTitle;	
	protected boolean mInError = false;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAllowFullScreen = true;

		// Add activity to stack
		AppManager.getInstance().addActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		if(mIsNeedTitleBack) {
			ImageView ivMenuButton = (ImageView) findViewById(R.id.iv_ab_menu_left);
			ivMenuButton.setClickable(false);
			ivMenuButton.setVisibility(View.GONE);
			RelativeLayout rlBackButton = (RelativeLayout) findViewById(R.id.rl_ab_menu_back);
			rlBackButton.setVisibility(View.VISIBLE);
			rlBackButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					BaseActivity.this.finish();
				}
			});			
		}
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

	protected void setIsNeedTitleBack(boolean isNeedBack)
	{
		mIsNeedTitleBack = isNeedBack;
	}
	
    protected void relogin(String tag, Context context) {
    	Log.i(tag, "token expried, relogin");
		Intent intent = new Intent(context, LoginActivity.class);
		context.startActivity(intent);    	
    }
	
	public boolean isAllowFullScreen() {
		return mAllowFullScreen;
	}

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

    
    protected void showErrorDialog(String errorMessage) {
		mInError = true;
		Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
