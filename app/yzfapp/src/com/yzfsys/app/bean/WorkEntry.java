package com.yzfsys.app.bean;

import org.json.JSONObject;

import com.yzfsys.app.util.StringUtils;

public class WorkEntry extends BaseEntry {
	
	private static final long serialVersionUID = -3704597241930681023L;
	private String mStepId;
	private String mCustomer;
	private String mStepName;
	private String mPhaseId;
	private String mPhaseName;
	private String mOrderId;
	private String mAddress;
	private String mContractNo;
	
	public String getCustomer() {
		return mCustomer;
	}
	public void setmCustomer(String customer) {
		this.mCustomer = customer;
	}
	public String getAddress() {
		return mAddress;
	}
	public void setAddress(String address) {
		this.mAddress = address;
	}
	public String getStepId() {
		return mStepId;
	}
	public void setStepId(String stepId) {
		this.mStepId = stepId;
	}
	public String getStepName() {
		return mStepName;
	}
	public void setStepName(String stepName) {
		this.mStepName = stepName;
	}
	public String getPhaseId() {
		return mPhaseId;
	}
	public void setPhaseId(String phaseId) {
		this.mPhaseId = phaseId;
	}
	public String getPhaseName() {
		return mPhaseName;
	}
	public void setPhaseName(String phaseName) {
		this.mPhaseId = phaseName;
	}
	public String getOrderId() {
		return mOrderId;
	}
	public void setOrderId(String orderId) {
		this.mOrderId = orderId;
	}
	public String getContractNo() {
		return mContractNo;
	}
	public void setContractNo(String contractNo) {
		this.mContractNo = contractNo;
	}
	
	public void parse(JSONObject data) throws Exception {
    	try {
    		mStepId = StringUtils.formatEmpty(data.getString("stepId"));
    		mCustomer = StringUtils.formatEmpty(data.getString("customer"));
    		mStepName = StringUtils.formatEmpty(data.getString("stepName"));
    		mPhaseId = StringUtils.formatEmpty(data.getString("processId"));
    		mPhaseName = StringUtils.formatEmpty(data.getString("processName"));
    		mAddress = StringUtils.formatEmpty(data.getString("address"));
    		mOrderId = StringUtils.formatEmpty(data.getString("projectId"));
    		mContractNo = StringUtils.formatEmpty(data.getString("contractNo"));
	    } catch (Exception e) {
			throw e;
	    } finally {
	    	
	    }     
    }
	
}
