package com.yzfsys.app.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yzfsys.app.R;
import com.yzfsys.app.adapter.OrderStepTraceAdapter;
import com.yzfsys.app.bean.OrderPhaseEntry;
import com.yzfsys.app.bean.OrderStepEntry;
import com.yzfsys.app.common.Auth;
import com.yzfsys.app.common.Constants;
import com.yzfsys.app.common.OrderStatus;
import com.yzfsys.app.util.DPIUtil;
import com.yzfsys.app.util.HttpManager;
import com.yzfsys.app.util.StringUtils;

public class OrderStepTraceActivity extends BaseActivity {

	private static final String API_ORDER_STEP_REQUEST = "procstep";
	private ViewGroup mTraceViewGroup;
	private OrderPhaseEntry mOrderPhaseEntry = null;
    private ArrayList<OrderStepEntry> mEntries = new ArrayList<OrderStepEntry>();
    private OrderStepTraceAdapter mAdapter;
	private ProgressDialog mPd = null;
	private SharedPreferences mSp;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		mOrderPhaseEntry = (OrderPhaseEntry) intent.getSerializableExtra(Constants.ORDER_PHASE_ENTRY_SERIALIZABLE_KEY);
		setContentView(R.layout.activity_order_step_trace);
		mTvTitle = (TextView)findViewById(R.id.tv_ab_title);
		this.setTitleText(mOrderPhaseEntry.getPhaseName()+"阶段详情");
		
		mPd = new ProgressDialog(this);
		mSp = getSharedPreferences("yzfsp", Context.MODE_PRIVATE);
		mAdapter = new OrderStepTraceAdapter(this, 0, mEntries);
		mTraceViewGroup = (ViewGroup) findViewById(R.id.ll_fopt_trace_list);
	}

	@Override
	public void onResume() {
		super.onResume();
		clearData();
		initStepData();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		clearData();
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
			
	        String requestUrl = Constants.URL_YZF + API_ORDER_STEP_REQUEST 
	        		+ "?token=" + token
	        		+ "&processId=" + mOrderPhaseEntry.getPhaseId();

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
                		JSONArray entries = response.getJSONArray("procstep");
                        JSONObject entry;
                        for (int i = 0; i < entries.length(); i++) {
                            entry = entries.getJSONObject(i);
                            OrderStepEntry orderStepEntry = new OrderStepEntry();
                            orderStepEntry.parse(entry);
                            mEntries.add(0, orderStepEntry);
                        }
                        mAdapter.notifyDataSetChanged();
                        initStepView();
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
            	Log.e(this.getClass().getName(), error.getMessage());
                showErrorDialog();
            }
        };
    }	
	
    private void initStepView() {
    	initPhaseInfo();
    	initStepInfo();
    	showTraceInfo();
    }
    
	private void initStepInfo() {
		int i = 0;
		
		for(;i < mAdapter.getCount(); ++i) 
		{
			View viewStepTraceItem = mAdapter.getView(i, null, null);
			OrderStepEntry entry = (OrderStepEntry) viewStepTraceItem.getTag(R.id.id_entry);
            if(OrderStatus.STR_IN_PROGRESS.equals(entry.getStatus())) {
				String stepName = entry.getName();
				if(stepName.equals(Constants.STEP_UPLOAD_CONSTRUCTION_PIC)
						|| stepName.equals(Constants.STEP_UPLOAD_MATERIAL_PIC)
						|| stepName.equals(Constants.STEP_QC_APPROVE)
						|| stepName.equals(Constants.STEP_CUSTOMER_APPROVE)) {
					viewStepTraceItem.setOnClickListener(createStepTraceItemClickListener());	
				}
            }
			mTraceViewGroup.addView(viewStepTraceItem);
		}
		
	}

	private View.OnClickListener createStepTraceItemClickListener() {
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				OrderStepEntry entry = (OrderStepEntry) v.getTag(R.id.id_entry);
				
				if (entry == null)
					return;
				Intent intent = null;
				String stepName = entry.getName();
				if(stepName.equals(Constants.STEP_UPLOAD_CONSTRUCTION_PIC)
						|| stepName.equals(Constants.STEP_UPLOAD_MATERIAL_PIC)) {
					if(!Auth.isHaveAuth(v.getContext(), Auth.AUTH_SUPERVISOR, mOrderPhaseEntry.getSupervisorId()))
					{
						showErrorDialog("您没有权限操作");
						return;
					} else {
						intent = new Intent(v.getContext(), OrderStepUploadActivity.class);	
					}
				} else if(stepName.endsWith(Constants.STEP_QC_APPROVE)) {
					if(!Auth.isHaveAuth(v.getContext(), Auth.AUTH_QC, mOrderPhaseEntry.getQcId()))
					{
						showErrorDialog("您没有权限操作");
						return;
					} else {
						if(mOrderPhaseEntry.getHasMaterial().equals(Constants.HAS_MATERIAL)) {
							intent = new Intent(v.getContext(), StepMaterialApproveActivity.class);
						} else {
							intent = new Intent(v.getContext(), StepPictureApproveActivity.class);
						}	
					}	
				} else if(stepName.equals(Constants.STEP_CUSTOMER_APPROVE)) {
					if(!Auth.isHaveAuth(v.getContext(), Auth.AUTH_CUSTOMER, ""))
					{
						showErrorDialog("您没有权限操作");
						return;
					} else {
						if(mOrderPhaseEntry.getHasMaterial().equals(Constants.HAS_MATERIAL)) {
							intent = new Intent(v.getContext(), StepMaterialApproveActivity.class);
						} else {
							intent = new Intent(v.getContext(), StepPictureApproveActivity.class);
						}	
					}
				}
				
				intent.putExtra(Constants.ORDER_STEP_ENTRY_SERIALIZABLE_KEY, entry);
				intent.putExtra(Constants.PHASE_ID_KEY, mOrderPhaseEntry.getPhaseId());
				startActivity(intent);				
			}
		};
	}
	
	private void initPhaseInfo()
	{
		TextView tvOrderNumber = (TextView) findViewById(R.id.tv_fopt_order_num);
		TextView tvOrderNumberContent = (TextView) findViewById(R.id.tv_fopt_order_num_content);
		TextView tvCustomerName = (TextView) findViewById(R.id.tv_fopt_customer_name);
		TextView tvCustomerNameContent = (TextView) findViewById(R.id.tv_fopt_customer_name_content);
		tvOrderNumber.setText("阶段：");
		tvOrderNumberContent.setText(mOrderPhaseEntry.getPhaseName());
		tvCustomerName.setText("计划时间：");
		tvCustomerNameContent.setText(mOrderPhaseEntry.getPlanStartDate()+"~"+mOrderPhaseEntry.getPlanEndDate());
		
	}
	
	private void showTraceInfo()
	{
		int count = mTraceViewGroup.getChildCount();
		int i = 0;
		View itemView;
		View splitLine1;
		View splitLine2;
		for(;i < count; ++i)
		{
			itemView = mTraceViewGroup.getChildAt(i);
			itemView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
			splitLine1 = itemView.findViewById(R.id.v_fopti_track_line1);
			splitLine2 = itemView.findViewById(R.id.v_fopti_track_line2);
			if(count == 1) 
			{
				splitLine2.setVisibility(View.GONE);
			}
			if(i == 0) 
			{
				splitLine1.setVisibility(View.GONE);
				splitLine2.setMinimumHeight((itemView.getMeasuredHeight() 
						- DPIUtil.dip2px(10.0F) 
						- BitmapFactory.decodeResource(getResources(), R.drawable.phase_trace_track_point).getHeight())
						- 3);
			}
			else if(i == count-1)
			{
				splitLine2.setVisibility(View.GONE);
				splitLine1.setMinimumHeight(DPIUtil.dip2px(10.0F) - 3);
			}
			else
			{
				splitLine1.setMinimumHeight(DPIUtil.dip2px(10.0F) - 2);
				splitLine2.setMinimumHeight((itemView.getMeasuredHeight() 
						- DPIUtil.dip2px(10.0F) 
						- BitmapFactory.decodeResource(getResources(), R.drawable.phase_trace_track_point).getHeight())
						- 3);			
			}
		}
	}
	
	private void clearData() {
		mEntries.clear();
		mTraceViewGroup.removeAllViews();		
	}
}
