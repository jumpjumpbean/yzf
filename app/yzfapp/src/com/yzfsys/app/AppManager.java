package com.yzfsys.app;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.yzfsys.app.util.HttpManager;

/**
 * 搴旂敤绋嬪簭Activity绠＄悊绫伙細鐢ㄤ簬Activity绠＄悊鍜屽簲鐢ㄧ▼搴忛�鍑� * 
 * @jacob niu 
 */
public class AppManager {
	
	private static Stack<Activity> mActivityStack;
	private static AppManager mInstance;
	
	private AppManager(){}
	
	/**
	 * 鍗曚竴瀹炰緥
	 */
	public static AppManager getInstance(){
		if(mInstance == null){
			mInstance = new AppManager();
		}
		return mInstance;
	}
	
	/**
	 * 娣诲姞Activity鍒板爢鏍�	 */
	public void addActivity(Activity activity){
		if(mActivityStack == null){
			mActivityStack = new Stack<Activity>();
		}
		mActivityStack.add(activity);
	}
	
	/**
	 * 鑾峰彇褰撳墠Activity锛堝爢鏍堜腑鏈�悗涓�釜鍘嬪叆鐨勶級
	 */
	public Activity currentActivity(){
		Activity activity = mActivityStack.lastElement();
		return activity;
	}
	
	/**
	 * 缁撴潫褰撳墠Activity锛堝爢鏍堜腑鏈�悗涓�釜鍘嬪叆鐨勶級
	 */
	public void finishActivity(){
		Activity activity = mActivityStack.lastElement();
		finishActivity(activity);
	}
	
	/**
	 * 缁撴潫鎸囧畾鐨凙ctivity
	 */
	public void finishActivity(Activity activity){
		if(activity != null){
			mActivityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}
	
	/**
	 * 缁撴潫鎸囧畾绫诲悕鐨凙ctivity
	 */
	public void finishActivity(Class<?> cls){
		for (Activity activity : mActivityStack) {
			if(activity.getClass().equals(cls) ){
				finishActivity(activity);
			}
		}
	}
	
	/**
	 * 缁撴潫鎵�湁Activity
	 */
	public void finishAllActivity(){
		for (int i = 0, size = mActivityStack.size(); i < size; i++){
            if (null != mActivityStack.get(i)){
            	mActivityStack.get(i).finish();
            }
	    }
		mActivityStack.clear();
	}
	
	/**
	 * 閫�嚭搴旂敤绋嬪簭
	 */
	public void AppExit(Context context) {
		try {
			HttpManager.getInstance().cancelPendingRequests();
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.killBackgroundProcesses(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {	}
	}
}