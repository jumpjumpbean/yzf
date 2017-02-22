package com.yzfsys.app;

import java.net.CookieHandler;
import java.net.CookieManager;

import android.app.Application;
import android.graphics.Bitmap.CompressFormat;

import com.yzfsys.app.util.DPIUtil;
import com.yzfsys.app.util.HttpManager;
import com.yzfsys.app.util.HttpManager.ImageCacheType;

/**
 * 鍏ㄥ眬搴旂敤绋嬪簭绫伙細鐢ㄤ簬淇濆瓨鍜岃皟鐢ㄥ叏灞�簲鐢ㄩ厤缃強澶勭悊. 
 *
 *@jacob niu
 */
public class MainApplication extends Application {
	
	private static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
	private static int DISK_IMAGECACHE_QUALITY = 100;  //PNG is lossless so quality is ignored but must be provided
	protected static MainApplication mInstance;

	public static MainApplication getInstance(){
//		if(mInstance == null)
//			mInstance = new MainApplication();
		
		return mInstance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		DPIUtil.setDensity(getResources().getDisplayMetrics().density);
		init();
	}

	/**
	 * Intialize the request manager and the image cache 
	 */
	private void init() {
		CookieManager manager = new CookieManager();
		CookieHandler.setDefault(manager);
		
		HttpManager.getInstance().init(this,
				this.getPackageCodePath()
				, DISK_IMAGECACHE_COMPRESS_FORMAT
				, DISK_IMAGECACHE_QUALITY
				, ImageCacheType.MEMORY);
	}
}