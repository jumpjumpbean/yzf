package com.yzfsys.app.common;

public class OrderStatus {

	public static final String[] description 
		= new String[]{"", "未开始", "进行中", "已结束"};
	public static final String STR_NOT_START = "1";
	public static final String STR_IN_PROGRESS = "2";
	public static final String STR_COMPLETED = "3";
	
	public static final int INT_NOT_START = 1;
	public static final int INT_COMPLETED = 3;

	public static String getStatusDesc(String status) {
    	String statusDesc;
    	int index = 0;
    	
    	try {
        	index = Integer.parseInt(status);
        	if(index < 0 || index > INT_COMPLETED) {
        		return "";
        	} else {
        		statusDesc = description[index];
        	}		
    	} catch (Exception e) {
    		statusDesc = "";
    	} finally {
    		
    	}
    	return statusDesc; 
    }
}
