package com.yzfsys.app.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yzfsys.app.R;
import com.yzfsys.app.adapter.OrderStepApproveAdapter;
import com.yzfsys.app.bean.OrderStepEntry;
import com.yzfsys.app.bean.StepImageEntry;
import com.yzfsys.app.common.Auth;
import com.yzfsys.app.common.Constants;
import com.yzfsys.app.util.HttpManager;
import com.yzfsys.app.util.StringUtils;
import com.yzfsys.app.widget.FButton;

public class StepPictureApproveActivity extends BaseActivity {

	private static final String API_STEP_IMAGE_REQUEST = "pics";
	private static final String API_STEP_UPDATE_STATUS_REQUEST = "updateStatus";
	private OrderStepEntry mOrderStepEntry = null;
	private ArrayList<StepImageEntry> mEntries = new ArrayList<StepImageEntry>();
	private OrderStepApproveAdapter mAdapter;
	private ProgressDialog mPd = null;
	private SharedPreferences mSp;
	private GridView mImageGrid;
    private FButton mBtnSubmit;
    private FButton mBtnReject;
    private TextView mTvNoData;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		mOrderStepEntry = (OrderStepEntry) intent.getSerializableExtra(Constants.ORDER_STEP_ENTRY_SERIALIZABLE_KEY);
		setContentView(R.layout.activity_image_grid);
		mTvTitle = (TextView)findViewById(R.id.tv_ab_title);
		this.setTitleText("图片审核");
		
		mAdapter = new OrderStepApproveAdapter(this, 0, mEntries, HttpManager.getInstance().getImageLoader());
		mImageGrid = (GridView) findViewById(R.id.gv_aig_image_grid);
		mImageGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mImageGrid.setAdapter(mAdapter);
		mPd = new ProgressDialog(this);
		mSp = getSharedPreferences("yzfsp", Context.MODE_PRIVATE);
		mTvNoData = (TextView) findViewById(R.id.tv_aig_no_data);
		mBtnSubmit = (FButton) findViewById(R.id.btn_aig_submit);
		mBtnReject = (FButton) findViewById(R.id.btn_aig_reject);
		
		String stepName = mOrderStepEntry.getName();
		if(stepName.equals(Constants.STEP_CUSTOMER_APPROVE)) {
			mBtnReject.setVisibility(View.GONE);
			mBtnSubmit.setButtonColor(this.getResources().getColor(R.color.fbutton_color_orange));
			mBtnSubmit.setText(R.string.button_aml_next_step);
			mBtnSubmit.setOnClickListener(createCustomerApproveOnClickListener());
		} else {
			mBtnSubmit.setOnClickListener(createUpdateStatusOnClickListener());
			mBtnReject.setOnClickListener(createRejectOnClickListener());
		}
				
	}

	@Override
	public void onResume() {
		super.onResume();
		mEntries.clear();
		initStepData();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mEntries.clear();
	}

    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        switch (requestCode) {  
        case Constants.CUSTOMER_APPROVE_ACTIVITY_RESULT_CODE:
        	if(RESULT_OK == resultCode) {
        		Intent intent = new Intent();
        		this.setResult(Activity.RESULT_OK, intent);
        		this.finish();
        	}
            break;  
        default:  
            break;  
        }  
    }	

	private View.OnClickListener createCustomerApproveOnClickListener() {
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), CustomerApproveActivity.class);	
				intent.putExtra(Constants.ORDER_STEP_ENTRY_SERIALIZABLE_KEY, mOrderStepEntry);
				startActivityForResult(intent, Constants.CUSTOMER_APPROVE_ACTIVITY_RESULT_CODE);
			}
		};
	}
    
	private View.OnClickListener createUpdateStatusOnClickListener() {
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				updateStatus(Constants.QC_APPROVE);
			}
		};
	}
	
	private View.OnClickListener createRejectOnClickListener() {
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				updateStatus(Constants.QC_REJECT);
			}
		};
	}
	
	private void initStepData() {
		try {
			String token = mSp.getString(Constants.SP_KEY_TOKEN, "");
			if(StringUtils.isEmpty(token)) {
				relogin(this.getClass().getName(), this);
				return;
			}
			mPd.setMessage("加载中...");
			mPd.show();
			mTvNoData.setVisibility(View.GONE);
			mImageGrid.setVisibility(View.VISIBLE);
			
	        String requestUrl = Constants.URL_YZF + API_STEP_IMAGE_REQUEST 
	        		+ "?token=" + token
	        		+ "&procstepid=" + mOrderStepEntry.getId();

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
                		JSONArray entries = response.getJSONArray("oplog");
                        JSONObject entry;
                        for (int i = 0; i < entries.length(); i++) {
                            entry = entries.getJSONObject(i);
                            StepImageEntry stepImageEntry = new StepImageEntry();
                            stepImageEntry.parse(entry);
                            mEntries.add(stepImageEntry);
                        }
                        mAdapter.notifyDataSetChanged();
                	} else {
                		final String message = StringUtils.formatEmpty(response.getString("msg"));
                		if(Constants.MESSAGE_NO_PICTURE.equals(message)) {
                    		mTvNoData.setVisibility(View.VISIBLE);
                    		mTvNoData.setText(Constants.MESSAGE_NO_PICTURE);
                    		mImageGrid.setVisibility(View.GONE);
                		} else {
                			showErrorDialog();	
                		}
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
    
	private void updateStatus(String status) {
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
	        		+ "&status=" + status;

	        JsonObjectRequest request = new JsonObjectRequest(Method.POST,
	        		requestUrl,
	        		null,
	        		createUpdateSuccessListener(), 
	        		createReqErrorListener());

	        request.setShouldCache(false);
	        HttpManager.getInstance().addToRequestQueue(request); 				
		} catch(Exception e) {
			e.printStackTrace();
			Log.e(this.getClass().getName(), e.getMessage());
		}		
	}
	
    private Response.Listener<JSONObject> createUpdateSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                	mPd.dismiss();
                	String result = StringUtils.formatEmpty(response.getString("result"));
                	if(Constants.RESULT_SUCCESS.equals(result)) {
                		Intent intent = new Intent();
                		Toast.makeText(StepPictureApproveActivity.this, "更新成功", Toast.LENGTH_LONG).show();
                		StepPictureApproveActivity.this.setResult(Activity.RESULT_OK, intent);
                		StepPictureApproveActivity.this.finish();
                	} else {
                		showErrorDialog();
                	}
                } catch (Exception e) {               	
                    showErrorDialog();
                }
            }
        };
    }
}
