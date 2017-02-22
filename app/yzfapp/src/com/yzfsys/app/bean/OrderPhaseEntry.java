package com.yzfsys.app.bean;

import org.json.JSONObject;

import com.yzfsys.app.common.CustomerAttitude;
import com.yzfsys.app.common.OrderStatus;
import com.yzfsys.app.util.StringUtils;

public class OrderPhaseEntry extends BaseEntry {
	
	private static final long serialVersionUID = -3920337653664770921L;
	private String mPhaseId;
	private String mPhaseName;
	private String mStatus;
	private String mSupervisorId;
	private String mSupervisorName;
	private String mQcId;
	private String mQcName;
	private String mPlanStartDate;
	private String mPlanEndDate;
	private String mRealStartDate;
	private String mRealEndDate;
	private String mAttitude;
	private String mComments;
	private String mHasMaterial;
	
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
		this.mPhaseName = phaseName;
	}
	public String getStatus() {
		return mStatus;
	}
	public void setStatus(String status) {
		this.mStatus = status;
	}
	public String getSupervisorId() {
		return mSupervisorId;
	}
	public void setSupervisorId(String supervisorId) {
		this.mSupervisorId = supervisorId;
	}
	public String getSupervisorName() {
		return mSupervisorName;
	}
	public void setSupervisorName(String supervisorName) {
		this.mSupervisorName = supervisorName;
	}
	public String getQcId() {
		return mQcId;
	}
	public void setmQcId(String qcId) {
		this.mQcId = qcId;
	}
	public String getQcName() {
		return mQcName;
	}
	public void setQcName(String qcName) {
		this.mQcName = qcName;
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
	public String getRealStartDate() {
		return mRealStartDate;
	}
	public void setmRealStartDate(String realStartDate) {
		this.mRealStartDate = realStartDate;
	}
	public String getRealEndDate() {
		return mRealEndDate;
	}
	public void setRealEndDate(String realEndDate) {
		this.mRealEndDate = realEndDate;
	}
	public String getAttitude() {
		return mAttitude;
	}
	public void setAttitude(String attitude) {
		this.mAttitude = attitude;
	}
	public String getComments() {
		return mComments;
	}
	public void setmComments(String comments) {
		this.mComments = comments;
	}
    public String getHasMaterial() {
		return mHasMaterial;
	}
	public void setHasMaterial(String hasMaterial) {
		this.mHasMaterial = hasMaterial;
	}
	
	public void parse(JSONObject data) throws Exception {
    	try {
    		mPhaseId = StringUtils.formatEmpty(data.getString("id"));
    		mPhaseName = StringUtils.formatEmpty(data.getString("period"));
    		mStatus = StringUtils.formatEmpty(data.getString("status"));
    		mSupervisorId = StringUtils.formatEmpty(data.getString("supervisorId"));
    		mSupervisorName = StringUtils.formatEmpty(data.getString("supervisor"));
    		mQcId = StringUtils.formatEmpty(data.getString("qcId"));
    		mQcName = StringUtils.formatEmpty(data.getString("qcName"));
    		mPlanStartDate = StringUtils.formatUTCDate(data.getString("planStartDate"));
    		mPlanEndDate = StringUtils.formatUTCDate(data.getString("planEndDate"));
    		mRealStartDate = StringUtils.formatUTCDate(data.getString("realStartDate"));
    		mRealEndDate = StringUtils.formatUTCDate(data.getString("realEndDate"));
    		mAttitude = StringUtils.formatEmpty(data.getString("attitude"));
    		mComments = StringUtils.formatEmpty(data.getString("memo"));
    		mHasMaterial = StringUtils.formatEmpty(data.getString("hasMaterial"));
	    } catch (Exception e) {
			throw e;
	    } finally {
	    	
	    }     
    }
	
	public String getAttitudeDesc() {
    	String statusDesc;
    	int index = 0;
    	
    	try {
        	index = Integer.parseInt(mAttitude);
        	if(index < 0 || index > CustomerAttitude.INT_VERY_SATISFIED) {
        		return "";
        	} else {
        		statusDesc = CustomerAttitude.description[index];
        	}
    	} catch (Exception e) {
    		statusDesc = "";
    	} finally {
    		
    	}
    	return statusDesc; 
    }
	
	public String getPlanDate() {
		return "计划时间：" + mPlanStartDate + "~" + mPlanEndDate;
	}
	
	public String getActualDate() {	
		if("".equals(mRealStartDate))
			return "";
		
		if("".equals(mRealEndDate)) {
			return "实际时间：" + mRealStartDate + "至今";
		} else {
			return "实际时间：" + mRealStartDate + "~" + mRealEndDate;
		}
	}
	
	public String getCommentsDesc() {
		return "客户评价：" + getAttitudeDesc();
	}
}
