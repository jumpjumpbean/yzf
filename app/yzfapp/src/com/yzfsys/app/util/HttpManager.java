/**
 * 	Copyright (c) 2014 Centling
 * 		All Rights Reserved
 *
 * This is unpublished proprietary source code of Centling.
 * The copyright notice above does not evidence any actual 
 * or intended publication of such source code.
 *
 * This file may not be modified except by authorized Centling Personnel
 */

package com.yzfsys.app.util;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap.CompressFormat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;

/**
 * HTTP请求管理类，用于基于volley的异步通讯
 * 
 * @jacob niu
 */
public class HttpManager {
	
	private static final int MAX_CACHE = 10;
    /**
     * Log or request TAG
     */
    public static final String TAG = "HttpManager";	
 
	/**
	 * Volley recommends in-memory L1 cache but both a disk and memory cache are provided.
	 * Volley includes a L2 disk cache out of the box but you can technically use a disk cache as an L1 cache provided
	 * you can live with potential i/o blocking. 
	 *
	 */
	public enum ImageCacheType {
		DISK
		, MEMORY
	}

    /**
     * A singleton instance of the HttpManager class for easy access in other places
     */
	private static HttpManager mInstance;
    
    /**
     * Global request queue for Volley
     */
	private RequestQueue mRequestQueue;
	
    /**
     * Global image loader for Volley
     */
    private ImageLoader mImageLoader;

	/**
	 * Image cache implementation
	 */
	private ImageCache mImageCache;
    
	private HttpManager(){}
	
	public static HttpManager getInstance(){
		if(mInstance == null)
			mInstance = new HttpManager();
		
		return mInstance;
	}
  
	/**
	 * Initializer for the manager. Must be called prior to use. 
	 * 
	 * @param context
	 * 			application context
	 * @param uniqueName
	 * 			name for the cache location
	 * @param compressFormat
	 * 			file type compression format.
	 * @param quality
	 */	
	public void init(Context context, String uniqueName, CompressFormat compressFormat, int quality, ImageCacheType type) {
		if(mInstance == null)
			mInstance = new HttpManager();
		
		mRequestQueue = Volley.newRequestQueue(context);

        int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE))
                .getMemoryClass();
        int max = memClass / 8;
        if(max > MAX_CACHE) max = MAX_CACHE;
        int cacheSize = 1024 * 1024 * max;
        
		switch (type) {
		case DISK:
			mImageCache= new DiskLruImageCache(context, uniqueName, cacheSize, compressFormat, quality);
			break;
		case MEMORY:
			mImageCache = new BitmapLruImageCache(cacheSize);
		default:
			mImageCache = new BitmapLruImageCache(cacheSize);
			break;
		}
		
		mImageLoader = new ImageLoader(mRequestQueue, mImageCache);
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }


    public ImageLoader getImageLoader() {
        if (mImageLoader != null) {
            return mImageLoader;
        } else {
            throw new IllegalStateException("ImageLoader not initialized");
        }
    }
    
    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     * 
     * @param req
     * @param tag
     */
//    public <T> void addToRequestQueue(Request<T> req, String tag) {
//        // set the default tag if tag is empty
//        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
//
//        VolleyLog.d("Adding request to queue: %s", req.getUrl());
//
//        getRequestQueue().add(req);
//    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     * 
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);

        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     * 
     * @param tag
     */
//    public void cancelPendingRequests(Object tag) {
//        if (mRequestQueue != null) {
//            mRequestQueue.cancelAll(tag);
//        }
//    }
    
 
    public void cancelPendingRequests() {    
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }
}
