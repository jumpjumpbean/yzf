package com.yzfsys.app.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yzfsys.app.R;
import com.yzfsys.app.bean.OrderStepEntry;
import com.yzfsys.app.common.Constants;
import com.yzfsys.app.common.CustomerAttitude;
import com.yzfsys.app.util.HttpManager;
import com.yzfsys.app.util.StringUtils;
import com.yzfsys.app.widget.FButton;

public class CustomerApproveActivity extends BaseActivity {

	private static final String API_STEP_UPDATE_STATUS_REQUEST = "doverify";
	private OrderStepEntry mOrderStepEntry = null;
	private RadioGroup mRgAttitude;
	private RadioButton mRbVerySatisfied;
	private RadioButton mRbSatisfied;
	private RadioButton mRbApprove;
	private RadioButton mRbReject;
    private FButton mBtnSubmit;
    private EditText mEtComments;
    private SharedPreferences mSp;
    private String mAttitude;
	private ProgressDialog mPd = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		mOrderStepEntry = (OrderStepEntry) intent.getSerializableExtra(Constants.ORDER_STEP_ENTRY_SERIALIZABLE_KEY);
		setContentView(R.layout.activity_customer_approve);
		mTvTitle = (TextView)findViewById(R.id.tv_ab_title);
		this.setTitleText("客户验收");
		
		mAttitude = CustomerAttitude.STR_VERY_SATISFIED;
		mPd = new ProgressDialog(this);
		mSp = getSharedPreferences("yzfsp", Context.MODE_PRIVATE);

		mEtComments = (EditText) findViewById(R.id.et_aca_customer_comments);
		mRgAttitude = (RadioGroup) findViewById(R.id.rg_aca_radio_group);
		mRbVerySatisfied = (RadioButton) findViewById(R.id.rb_aca_very_satisfied);
		mRbSatisfied = (RadioButton) findViewById(R.id.rb_aca_satisfied);
		mRbApprove = (RadioButton) findViewById(R.id.rb_aca_approve);
		mRbReject = (RadioButton) findViewById(R.id.rb_aca_reject);
		mRgAttitude.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				if(checkedId == mRbVerySatisfied.getId()) {
					mAttitude = CustomerAttitude.STR_VERY_SATISFIED;
				} else if (checkedId == mRbSatisfied.getId()) {
					mAttitude = CustomerAttitude.STR_SATISFIED;
				} else if (checkedId == mRbApprove.getId()) {
					mAttitude = CustomerAttitude.STR_PASS;
				} else if (checkedId == mRbReject.getId()) {
					mAttitude = CustomerAttitude.STR_REJECT;
				}
			}
		});
		
		mBtnSubmit = (FButton) findViewById(R.id.btn_aca_submit);
		mBtnSubmit.setOnClickListener(createSubmitOnClickListener());

	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
	private View.OnClickListener createSubmitOnClickListener() {
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isCommentsNeeded()) {
					Toast.makeText(CustomerApproveActivity.this, "留言不能为空", Toast.LENGTH_LONG).show();
					mEtComments.requestFocus();
				} else {
					updateStatus();	
				}
								
			}
		};
	}

	private boolean isCommentsNeeded() {
		if(mRbReject.isChecked() && mEtComments.getText().toString().equals("")) {
			return true;
		} else {
			return false;
		}
	}
	
	private void updateStatus() {
		try {
			String token = mSp.getString(Constants.SP_KEY_TOKEN, "");
			if(StringUtils.isEmpty(token)) {
				relogin(this.getClass().getName(), this);
				return;
			}
			mPd.setMessage("加载中...");
			mPd.show();
			
	        String requestUrl = Constants.URL_YZF + API_STEP_UPDATE_STATUS_REQUEST 
	        		+ "?token=" + token
	        		+ "&procstepid=" + mOrderStepEntry.getId()
	        		+ "&attitude=" + mAttitude
	        		+ "&memo=" + mEtComments.getText().toString();

	        JsonObjectRequest request = new JsonObjectRequest(Method.POST,
	        		requestUrl,
	        		null,
	        		createReqSuccessListener(), 
	        		createReqErrorListener());

	        request.setShouldCache(false);
	        HttpManager.getInstance().addToRequestQueue(request); 				
		} catch(Exception e) {
			e.printStackTrace();
			Log.e(this.getClass().getName(), e.getMessage());
		}		
	}
	
    private Response.Listener<JSONObject> createReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                	mPd.dismiss();
                	String result = StringUtils.formatEmpty(response.getString("result"));
                	if(Constants.RESULT_SUCCESS.equals(result)) {
                		Intent intent = new Intent();
                		Toast.makeText(CustomerApproveActivity.this, "更新成功", Toast.LENGTH_LONG).show();
                		CustomerApproveActivity.this.setResult(Activity.RESULT_OK, intent);
                		CustomerApproveActivity.this.finish();
                	} else {
                		showErrorDialog();
                	}
                } catch (Exception e) {               	
                    showErrorDialog();
                }
            }
        };
    }
    
    private Response.ErrorListener createReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mPd.dismiss();
                showErrorDialog();
            }
        };
    }
}
