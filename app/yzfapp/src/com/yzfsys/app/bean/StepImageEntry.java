package com.yzfsys.app.bean;

import org.json.JSONObject;

import android.graphics.Bitmap;

import com.yzfsys.app.util.StringUtils;

public class StepImageEntry extends BaseEntry {
	
	private static final long serialVersionUID = 6571128095537520121L;
	private String mId;
	private String mImageUrl;
//	private Bitmap mImage;
	private int mDrawableId;
	
	public String getId() {
		return mId;
	}
	public void setId(String id) {
		this.mId = id;
	}
	public String getImageUrl() {
		return mImageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.mImageUrl = imageUrl;
	}
//	public Bitmap getImage() {
//		return mImage;
//	}
//	public void setImage(Bitmap image) {
//		this.mImage = image;
//	}
	public int getDrawableId() {
		return mDrawableId;
	}
	public void setDrawableId(int drawableId) {
		this.mDrawableId = drawableId;
	}
	
    public void parse(JSONObject data) throws Exception {
    	try {
    		mId = StringUtils.formatEmpty(data.getString("id"));
    		mImageUrl = StringUtils.formatEmpty(data.getString("pic"));
	    } catch (Exception e) {
			throw e;
	    } finally {
	    	
	    }     
    }
	

}
