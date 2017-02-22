package com.yzfsys.app.activity;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yzfsys.app.R;
import com.yzfsys.app.common.Constants;
import com.yzfsys.app.util.HttpManager;
import com.yzfsys.app.util.StringUtils;

public class LoginActivity extends BaseActivity {

	private static final String API_LOGIN = "login";
	private static final String MSG_LOGIN_FAILED = "登录失败，请重新登录";
	private Button mBtnLogin;
	private EditText mEtUserName;
	private EditText mEtPassword;
	private SharedPreferences mSp;
	private ProgressDialog mPd = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_login);
			mBtnLogin = (Button) findViewById(R.id.btn_al_login);
			mBtnLogin.setOnClickListener(createBtnLoginClickListener());
			mEtUserName = (EditText) findViewById(R.id.et_al_user_name);
			mEtPassword = (EditText) findViewById(R.id.et_al_password);

			mSp = this.getSharedPreferences("yzfsp", MODE_PRIVATE);
			mPd = new ProgressDialog(this);
			this.setIsNeedTitleBack(false);
		}catch(Exception e) {
			e.printStackTrace();
			Log.e(this.getClass().getName(), e.getCause().toString());
		}
	}

	private View.OnClickListener createBtnLoginClickListener() {
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String userName = mEtUserName.getText().toString();
				String password = mEtPassword.getText().toString();
				
				if(StringUtils.isEmpty(userName))
				{
					Toast.makeText(v.getContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
					Log.w(this.getClass().getName(), "user name is empty");
					return;
				}
				if(StringUtils.isEmpty(password))
				{
					Toast.makeText(v.getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
					Log.w(this.getClass().getName(), "password is empty");
					return;
				}
				
				mSp.edit().putString(Constants.SP_KEY_USER_NAME, userName).commit();
				mSp.edit().putString(Constants.SP_KEY_PASSWORD, password).commit();
				mPd.setMessage("加载中...");
				mPd.show();
				
		        String requestUrl = Constants.URL_YZF + API_LOGIN 
		        		+ "?account=" + userName
		        		+ "&password=" + password;				

		        JsonObjectRequest request = new JsonObjectRequest(Method.POST,
		        		requestUrl,
		        		null,
		        		createReqSuccessListener(), 
		        		createReqErrorListener());

		        request.setShouldCache(false);
		        HttpManager.getInstance().addToRequestQueue(request); 
			}
		};
	}

    private Response.Listener<JSONObject> createReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                	mPd.dismiss();
                	final String result = response.getString("result");
                	if(result.equals("") || result.equals(Constants.RESULT_FAIL)) {
                		showErrorDialog(MSG_LOGIN_FAILED);
                		return;
                	}
                	
                	final String token = response.getString("token");
                	if(token.equals("") || token.equals("null")) {
                		showErrorDialog(MSG_LOGIN_FAILED);
                		return;
                	}
                	mSp.edit().putString(Constants.SP_KEY_TOKEN, token).commit();
                	final String userId = response.getString("userId");
                	if(userId.equals("") || userId.equals("null")) {
                		showErrorDialog(MSG_LOGIN_FAILED);
                		return;
                	}
                	mSp.edit().putString(Constants.SP_KEY_USER_ID, userId).commit();
                	final JSONArray array = response.getJSONArray("auth");
            		if(null != array && array.length() > 0) {
                		final String auth = StringUtils.formatEmpty(array.getString(0));
                    	if(auth.equals("")) {
                    		showErrorDialog(MSG_LOGIN_FAILED);
                    		return;
                    	}
                		mSp.edit().putString(Constants.SP_KEY_AUTH, auth).commit();
            		} else {
                		showErrorDialog(MSG_LOGIN_FAILED);
                		return;           			
            		}
                	showMainActivity(LoginActivity.this, token);
                } catch (Exception e) {               	
                	showErrorDialog(MSG_LOGIN_FAILED);
                }
            }
        };
    }

    private Response.ErrorListener createReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mPd.dismiss();
            	showErrorDialog(MSG_LOGIN_FAILED);
            }
        };
    }	
    
	private void showMainActivity(Context context, String token) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra("puid", token);
		context.startActivity(intent);
	}
}
