package com.yzfsys.app.util;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import com.yzfsys.app.MainApplication;


public class DPIUtil
{
	private static Display defaultDisplay;
	private static float mDensity = 160.0F;

	public static int dip2px(float paramFloat)
	{
		return (int)(0.5F + paramFloat * mDensity);
	}

	public static Display getDefaultDisplay()
	{
		if (defaultDisplay == null)
			defaultDisplay = ((WindowManager)MainApplication.getInstance().getSystemService("window")).getDefaultDisplay();
		return defaultDisplay;
	}

	public static float getDensity()
	{
		return mDensity;
	}

	@SuppressWarnings("deprecation")
	public static int getHeight()
	{
		return getDefaultDisplay().getHeight();
	}

	@SuppressWarnings("deprecation")
	public static int getWidth()
	{
		return getDefaultDisplay().getWidth();
	}

	public static int percentHeight(float paramFloat)
	{
		return (int)(paramFloat * getHeight());
	}

	public static int percentWidth(float paramFloat)
	{
		return (int)(paramFloat * getWidth());
	}

	public static int px2dip(Context paramContext, float paramFloat)
	{
		return (int)(0.5F + paramFloat / mDensity);
	}

	public static int px2sp(Context paramContext, float paramFloat)
	{
		return (int)(0.5F + paramFloat / paramContext.getResources().getDisplayMetrics().scaledDensity);
	}

	public static void setDensity(float paramFloat)
	{
		mDensity = paramFloat;
	}
}