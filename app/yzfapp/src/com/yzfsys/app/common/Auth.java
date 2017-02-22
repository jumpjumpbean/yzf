package com.yzfsys.app.common;

import com.yzfsys.app.util.StringUtils;

import android.content.Context;
import android.content.SharedPreferences;

public class Auth {

	public static final String AUTH_SUPERVISOR = "ROLE_SUPERVISOR"; // 监理
	public static final String AUTH_QC = "ROLE_QC"; // 质检
	public static final String AUTH_ADMIN = "ROLE_ADMIN"; // 管理员
	public static final String AUTH_REPO = "ROLE_REPO"; // 仓库管理员
	public static final String AUTH_CUSTOMER = "ROLE_CUSTOMER"; // 客户
	public static final String AUTH_SUPER = "ROLE_SUPER"; // 超级用户

	public static boolean isHaveAuth(Context context, String neededAuth, String neededUser) {
		boolean result = false;	
		final SharedPreferences sp = context.getSharedPreferences("yzfsp", Context.MODE_PRIVATE);
		final String curUserId = sp.getString(Constants.SP_KEY_USER_ID, "");
		if(StringUtils.isEmpty(curUserId)) {
			return result;
		}
		final String curAuth = sp.getString(Constants.SP_KEY_AUTH, "");
		if(StringUtils.isEmpty(curAuth)) {
			return result;
		}
		if(curAuth.equals(AUTH_SUPER)) {
			return true;
		}
		if(neededAuth.equals(AUTH_CUSTOMER)) {
			return neededAuth.equals(curAuth);
		}		
		if(neededAuth.equals(curAuth) && neededUser.equals(curUserId)) {
			result = true;
		}
		return result;
	}
}
