package com.yzfsys.app.bean;

import org.json.JSONObject;

import com.yzfsys.app.util.StringUtils;

public class MaterialEntry extends BaseEntry {
	
	private static final long serialVersionUID = 919629843408707038L;
	private String mId;
	private String mName;
	private String mPhase;
	private String mType;
	private String mCount;
	private String mMemo;
	private String mUnit;
	private String mBrand;
	private String mModel;
	
	public String getId() {
		return mId;
	}
	public void setId(String id) {
		this.mId = id;
	}
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		this.mName = name;
	}
    public String getPhase() {
		return mPhase;
	}
	public void setPhase(String phase) {
		this.mPhase = phase;
	}
	public String getType() {
		return mType;
	}
	public void setType(String type) {
		this.mType = type;
	}
	public String getCount() {
		return mCount;
	}
	public void setCount(String count) {
		this.mCount = count;
	}
	public String getMemo() {
		return mMemo;
	}
	public void setMemo(String memo) {
		this.mMemo = memo;
	}
	public String getUnit() {
		return mUnit;
	}
	public void setUnit(String unit) {
		this.mUnit = unit;
	}
	public String getBrand() {
		return mBrand;
	}
	public void setBrand(String brand) {
		this.mBrand = brand;
	}
	public String getModel() {
		return mModel;
	}
	public void setModel(String model) {
		this.mModel = model;
	}
	public void parse(JSONObject data) throws Exception {
    	try {
    		mId = StringUtils.formatEmpty(data.getString("id"));
    		mName = StringUtils.formatEmpty(data.getString("name"));
    		mPhase = StringUtils.formatEmpty(data.getString("period"));
    		mMemo = StringUtils.formatEmpty(data.getString("memo"));
    		mType = StringUtils.formatEmpty(data.getString("type"));
    		mCount = StringUtils.formatEmpty(data.getString("cnt"));
    		mUnit = StringUtils.formatEmpty(data.getString("unit"));
    		mBrand = StringUtils.formatEmpty(data.getString("brand"));
    		mModel = StringUtils.formatEmpty(data.getString("model"));
	    } catch (Exception e) {
			throw e;
	    } finally {
	    	
	    }     
    }
	

}
