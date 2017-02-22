package com.yzfsys.app.bean;

import org.json.JSONObject;

import com.yzfsys.app.util.StringUtils;

public class OrderStepOplogEntry extends BaseEntry {
	
	private static final long serialVersionUID = 6571128095537520121L;
	private String mId;
	private String mOperater;
	private String mStatus;
	private String mMemo;
	private String mCreatedDate;
	private String mAttitude;
	
	public String getId() {
		return mId;
	}
	public void setId(String id) {
		this.mId = id;
	}
	public String getOperater() {
		return mOperater;
	}
	public void setOperater(String operater) {
		this.mOperater = operater;
	}
	public String getStatus() {
		return mStatus;
	}
	public void setStatus(String status) {
		this.mStatus = status;
	}
	public String getMemo() {
		return mMemo;
	}
	public void setMemo(String memo) {
		this.mMemo = memo;
	}
	public String getCreatedDate() {
		return mCreatedDate;
	}
	public void setCreatedDate(String createdDate) {
		this.mCreatedDate = createdDate;
	}
	public String getAttitude() {
		return mAttitude;
	}
	public void setAttitude(String attitude) {
		this.mAttitude = attitude;
	}
	
    public void parse(JSONObject data) throws Exception {
    	try {
    		mId = StringUtils.formatEmpty(data.getString("id"));
    		mOperater = StringUtils.formatEmpty(data.getString("operater"));
    		mStatus = StringUtils.formatEmpty(data.getString("status"));
    		mMemo = StringUtils.formatEmpty(data.getString("memo"));
    		mAttitude = StringUtils.formatEmpty(data.getString("attitude"));
    		mCreatedDate = StringUtils.formatUTCDate(data.getString("dateCreated"));
	    } catch (Exception e) {
			throw e;
	    } finally {
	    	
	    }     
    }
	

}
