package com.yzfsys.app.bean;

import org.json.JSONObject;

import com.yzfsys.app.util.StringUtils;

public class OrderStepEntry extends BaseEntry {
	
	private static final long serialVersionUID = 6571128095537520121L;
	private String mId;
	private String mName;
	private String mStatus;
	private String mMemo;
	private String mUpdatedDate;
	private String mAttitude;
	
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
	public String getUpdatedDate() {
		return mUpdatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.mUpdatedDate = updatedDate;
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
    		mName = StringUtils.formatEmpty(data.getString("stepName"));
    		mStatus = StringUtils.formatEmpty(data.getString("status"));
    		mMemo = StringUtils.formatEmpty(data.getString("memo"));
    		mAttitude = StringUtils.formatEmpty(data.getString("attitude"));
    		mUpdatedDate = StringUtils.formatUTCDate(data.getString("dateUpdated"));
	    } catch (Exception e) {
			throw e;
	    } finally {
	    	
	    }     
    }
	

}
