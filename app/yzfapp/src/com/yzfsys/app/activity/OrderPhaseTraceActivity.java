package com.yzfsys.app.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
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
import com.yzfsys.app.adapter.OrderPhaseTraceAdapter;
import com.yzfsys.app.bean.OrderEntry;
import com.yzfsys.app.bean.OrderPhaseEntry;
import com.yzfsys.app.common.Constants;
import com.yzfsys.app.common.OrderStatus;
import com.yzfsys.app.util.DPIUtil;
import com.yzfsys.app.util.HttpManager;
import com.yzfsys.app.util.StringUtils;

public class OrderPhaseTraceActivity extends BaseActivity {

	private static final String API_ORDER_PHASE_REQUEST = "process";
	private ViewGroup mTraceViewGroup;
	private OrderEntry mOrderEntry = null;
    private ArrayList<OrderPhaseEntry> mEntries = new ArrayList<OrderPhaseEntry>();
    private OrderPhaseTraceAdapter mAdapter;
	private ProgressDialog mPd = null;
	private SharedPreferences mSp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		mOrderEntry = (OrderEntry) intent.getSerializableExtra(Constants.ORDER_ENTRY_SERIALIZABLE_KEY);
		setContentView(R.layout.activity_order_step_trace);
		mTvTitle = (TextView)findViewById(R.id.tv_ab_title);
		this.setTitleText("订单详情");
		
		mPd = new ProgressDialog(this);
		mSp = getSharedPreferences("yzfsp", MODE_PRIVATE);
		mAdapter = new OrderPhaseTraceAdapter(this, 0, mEntries);
		mTraceViewGroup = (ViewGroup) findViewById(R.id.ll_fopt_trace_list);
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		clearData();
		initPhaseData();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		clearData();
	}
	
	private void initPhaseData() {
		try {
			String token = mSp.getString(Constants.SP_KEY_TOKEN, "");
			if(StringUtils.isEmpty(token)) {
				relogin(this.getClass().getName(), this);
				return;
			}
			mPd.setMessage("加载中...");
			mPd.show();
			
	        String requestUrl = Constants.URL_YZF + API_ORDER_PHASE_REQUEST 
	        		+ "?token=" + token
	        		+ "&projectId=" + mOrderEntry.getId();

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
                		JSONArray entries = response.getJSONArray("process");
                        JSONObject entry;
                        for (int i = 0; i < entries.length(); i++) {
                            entry = entries.getJSONObject(i);
                            OrderPhaseEntry orderPhaseEntry = new OrderPhaseEntry();
                            orderPhaseEntry.parse(entry);
                            mEntries.add(0, orderPhaseEntry);
                        }
                        mAdapter.notifyDataSetChanged();
                        initPhaseView();
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
	
    private void initPhaseView() {
    	initProjecgtInfo();
    	initPhaseInfo();
    	showTraceInfo();
    }
    
	private void initPhaseInfo() {
		int i = 0;
		
		for(;i < mAdapter.getCount(); ++i) 
		{
			View viewPhaseTraceItem = mAdapter.getView(i, null, null);
			OrderPhaseEntry entry = (OrderPhaseEntry) viewPhaseTraceItem.getTag(R.id.id_entry);
            if(!OrderStatus.STR_NOT_START.equals(entry.getStatus())) {
            	viewPhaseTraceItem.setOnClickListener(createPhaseTraceItemClickListener());
            }
			mTraceViewGroup.addView(viewPhaseTraceItem);
		}
		
	}

	private View.OnClickListener createPhaseTraceItemClickListener() {
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				OrderPhaseEntry entry = (OrderPhaseEntry) v.getTag(R.id.id_entry);
				
				if (entry == null)
					return;
				Intent intent = new Intent(v.getContext(), OrderStepTraceActivity.class);
				intent.putExtra(Constants.ORDER_PHASE_ENTRY_SERIALIZABLE_KEY, entry);
				startActivity(intent);				
			}
		};
	}
	
	private void initProjecgtInfo()
	{
		TextView tvOrderNumberContent = (TextView) findViewById(R.id.tv_fopt_order_num_content);
		TextView tvCustomerNameContent = (TextView) findViewById(R.id.tv_fopt_customer_name_content);
		tvOrderNumberContent.setText(mOrderEntry.getContractNo());
		tvCustomerNameContent.setText(mOrderEntry.getCustomer());
		
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
