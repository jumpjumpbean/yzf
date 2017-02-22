package com.yzfsys.app.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yzfsys.app.R;
import com.yzfsys.app.adapter.OrderMaterialAdapter;
import com.yzfsys.app.bean.MaterialEntry;
import com.yzfsys.app.bean.OrderStepEntry;
import com.yzfsys.app.common.Auth;
import com.yzfsys.app.common.Constants;
import com.yzfsys.app.util.HttpManager;
import com.yzfsys.app.util.StringUtils;
import com.yzfsys.app.widget.FButton;

public class StepMaterialApproveActivity extends BaseActivity {

	private static final String API_STEP_MATERIAL_REQUEST = "material";
	private static final String API_STEP_UPDATE_STATUS_REQUEST = "updateStatus";
	private OrderStepEntry mOrderStepEntry = null;
	private String mPhaseId;
	private ListView mLvMaterialList;
    private ArrayList<MaterialEntry> mEntries = new ArrayList<MaterialEntry>();
    private OrderMaterialAdapter mAdapter;
    private FButton mBtnSubmit;
    private FButton mBtnReject;
    private TextView mTvNoData;
    private SharedPreferences mSp;
	private ProgressDialog mPd = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		mOrderStepEntry = (OrderStepEntry) intent.getSerializableExtra(Constants.ORDER_STEP_ENTRY_SERIALIZABLE_KEY);
		mPhaseId = intent.getStringExtra(Constants.PHASE_ID_KEY);
		setContentView(R.layout.activity_material_list);
		mTvTitle = (TextView)findViewById(R.id.tv_ab_title);
		this.setTitleText("材料审核");
		
		mAdapter = new OrderMaterialAdapter(this, 0, mEntries);
		mLvMaterialList = (ListView) findViewById(R.id.lv_aml_list);
		mLvMaterialList.setAdapter(mAdapter);
		mEntries.clear();
		mPd = new ProgressDialog(this);
		mSp = getSharedPreferences("yzfsp", Context.MODE_PRIVATE);
		mTvNoData = (TextView) findViewById(R.id.tv_aml_no_data);
		mBtnSubmit = (FButton) findViewById(R.id.btn_aml_submit);
		mBtnReject = (FButton) findViewById(R.id.btn_aml_reject);
		mBtnSubmit.setOnClickListener(createSubmitOnClickListener());
		String stepName = mOrderStepEntry.getName();
		if(stepName.equals(Constants.STEP_CUSTOMER_APPROVE)) {
			mBtnReject.setVisibility(View.GONE);
		} else {
			mBtnReject.setOnClickListener(createRejectOnClickListener());
		}
		
	}

	@Override
	public void onResume() {
		super.onResume();
		mEntries.clear();
		initMaterialData();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mEntries.clear();
	}

    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        switch (requestCode) {  
        case Constants.STEP_PICTURE_APPROVE_ACTIVITY_RESULT_CODE:
        	if(RESULT_OK == resultCode) {
        		this.finish();
        	}
            break;  
        default:  
            break;  
        }  
    }
	
	private View.OnClickListener createSubmitOnClickListener() {
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), StepPictureApproveActivity.class);	
				intent.putExtra(Constants.ORDER_STEP_ENTRY_SERIALIZABLE_KEY, mOrderStepEntry);
				intent.putExtra(Constants.PHASE_ID_KEY, mPhaseId);
				startActivityForResult(intent, Constants.STEP_PICTURE_APPROVE_ACTIVITY_RESULT_CODE);					
			}
		};
	}
	
	private View.OnClickListener createRejectOnClickListener() {
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				updateStatus();
			}
		};
	}
	
	private void initMaterialData() {
		try {
			String token = mSp.getString(Constants.SP_KEY_TOKEN, "");
			if(StringUtils.isEmpty(token)) {
				relogin(this.getClass().getName(), this);
				return;
			}
			mPd.setMessage("加载中...");
			mPd.show();
			mTvNoData.setVisibility(View.GONE);
			mLvMaterialList.setVisibility(View.VISIBLE);
			
	        String requestUrl = Constants.URL_YZF + API_STEP_MATERIAL_REQUEST 
	        		+ "?token=" + token
	        		+ "&processId=" + mPhaseId;

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
                	final String result = StringUtils.formatEmpty(response.getString("result"));
                	if(Constants.RESULT_SUCCESS.equals(result)) {             		
                		JSONArray entries = response.getJSONArray("oplog");
                        JSONObject entry;
                        for (int i = 0; i < entries.length(); i++) {
                            entry = entries.getJSONObject(i);
                            MaterialEntry materialEntry = new MaterialEntry();
                            materialEntry.parse(entry);
                            mEntries.add(materialEntry);
                        }
                        mAdapter.notifyDataSetChanged();
                	} else {
                		final String message = StringUtils.formatEmpty(response.getString("msg"));
                		if(Constants.MESSAGE_NO_MATERIAL.equals(message)) {
                    		mTvNoData.setVisibility(View.VISIBLE);
                    		mTvNoData.setText(Constants.MESSAGE_NO_MATERIAL);
                    		mLvMaterialList.setVisibility(View.GONE);
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
	        		+ "&status=" + Constants.QC_REJECT;

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
                		Toast.makeText(StepMaterialApproveActivity.this, "更新成功", Toast.LENGTH_LONG).show();
                		StepMaterialApproveActivity.this.finish();
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
