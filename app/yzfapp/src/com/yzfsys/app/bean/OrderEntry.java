package com.yzfsys.app.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import com.yzfsys.app.common.OrderStatus;
import com.yzfsys.app.util.StringUtils;

public class OrderEntry extends BaseEntry {
	
	private static final long serialVersionUID = -8022641797203075422L;
	private String mId;
	private String mCustomer;
	private String mQc;
	private String mSupervisor;
	private String mAddress;
	private String mContractNo;
	private String mPlanStartDate;
	private String mPlanEndDate;
	private String mRealStartDate;
	private String mRealEndDate;
	private String mStatus;
	private String mCurProcId;
	private String mCurProcName;
	private String mCurProcPlanStartDate;
	private String mCurProcPlanEndDate;
	private String mCurProcRealStartDate;
	private boolean mIsDelayed = false;
	
	public String getId() {
		return mId;
	}
	public void setId(String id) {
		this.mId = id;
	}
	public String getCustomer() {
		return mCustomer;
	}
	public void setCustomer(String customer) {
		this.mCustomer = customer;
	}
	public String getAddress() {
		return mAddress;
	}
	public void setAddress(String address) {
		this.mAddress = address;
	}
	public String getContractNo() {
		return mContractNo;
	}
	public void setContractNo(String contractNo) {
		this.mContractNo = contractNo;
	}	
	public String getPlanStartDate() {
		return mPlanStartDate;
	}
	public void setPlanStartDate(String planStartDate) {
		this.mPlanStartDate = planStartDate;
	}
	public String getPlanEndDate() {
		return mPlanEndDate;
	}
	public void setPlanEndDate(String planEndDate) {
		this.mPlanEndDate = planEndDate;
	}

    public String getQc() {
		return mQc;
	}
	public void setQc(String qc) {
		this.mQc = qc;
	}
    public String getStatus() {
		return mStatus;
	}
	public void setStatus(String status) {
		this.mStatus = status;
	}
	public String getSupervisor() {
		return mSupervisor;
	}
	public void setSupervisor(String supervisor) {
		this.mSupervisor = supervisor;
	}
	public String getRealStartDate() {
		return mRealStartDate;
	}
	public void setRealStartDate(String realStartDate) {
		this.mRealStartDate = realStartDate;
	}
	public String getRealEndDate() {
		return mRealEndDate;
	}
	public void setRealEndDate(String realEndDate) {
		this.mRealEndDate = realEndDate;
	}
	public String getCurProcId() {
		return mCurProcId;
	}
	public void setCurProcId(String curProcId) {
		this.mCurProcId = curProcId;
	}
	public String getCurProcName() {
		return mCurProcName;
	}
	public void setCurProcName(String curProcName) {
		this.mCurProcName = curProcName;
	}
	public String getCurProcPlanStartDate() {
		return mCurProcPlanStartDate;
	}
	public void setCurProcPlanStartDate(String curProcPlanStartDate) {
		this.mCurProcPlanStartDate = curProcPlanStartDate;
	}
	public String getCurProcPlanEndDate() {
		return mCurProcPlanEndDate;
	}
	public void setCurProcPlanEndDate(String curProcPlanEndDate) {
		this.mCurProcPlanEndDate = curProcPlanEndDate;
	}
	public String getCurProcRealStartDate() {
		return mCurProcRealStartDate;
	}
	public void setCurProcRealStartDate(String curProcRealStartDate) {
		this.mCurProcRealStartDate = curProcRealStartDate;
	}
	public boolean getIsDelayed() {
		return mIsDelayed;
	}
	public void setIsDelayed(boolean isDelayed) {
		this.mIsDelayed = isDelayed;
	}
	
	public void parse(JSONObject data) throws Exception {
    	try {
    		mId = StringUtils.formatEmpty(data.getString("id"));
    		mCustomer = StringUtils.formatEmpty(data.getString("customer"));
    		mQc = StringUtils.formatEmpty(data.getString("qc"));
    		mContractNo = StringUtils.formatEmpty(data.getString("contractNo"));
    		mSupervisor = StringUtils.formatEmpty(data.getString("supervisor"));
    		mAddress = StringUtils.formatEmpty(data.getString("address"));
    		mStatus = StringUtils.formatEmpty(data.getString("status"));
    		mPlanStartDate = StringUtils.formatUTCDate(data.getString("planStartDate"));
    		mPlanEndDate = StringUtils.formatUTCDate(data.getString("planEndDate"));
    		mRealStartDate = StringUtils.formatUTCDate(data.getString("realStartDate"));
    		mRealEndDate = StringUtils.formatUTCDate(data.getString("realEndDate"));
    		JSONArray processArray = data.getJSONArray("curProcess");
    		if(null != processArray && processArray.length() > 0) {
        		JSONObject processEntry = processArray.getJSONObject(0);
        		mCurProcId = StringUtils.formatEmpty(processEntry.getString("id"));
        		mCurProcName = StringUtils.formatEmpty(processEntry.getString("name"));
        		mCurProcPlanStartDate = StringUtils.formatUTCDate(processEntry.getString("planStartDate"));
        		mCurProcPlanEndDate = StringUtils.formatUTCDate(processEntry.getString("planEndDate"));
        		mCurProcRealStartDate = StringUtils.formatUTCDate(processEntry.getString("realStartDate"));
        		if(OrderStatus.STR_IN_PROGRESS.equals(mStatus)) {
        			mIsDelayed = StringUtils.isBeforeToday(mCurProcPlanEndDate);
        		}
    		}

	    } catch (Exception e) {
			throw e;
	    } finally {
	    	
	    }     
    }
	
}
