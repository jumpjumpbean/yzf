package com.yzfsys.app.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yzfsys.app.R;
import com.yzfsys.app.activity.LoginActivity;
import com.yzfsys.app.activity.MainActivity;
import com.yzfsys.app.activity.OrderPhaseTraceActivity;
import com.yzfsys.app.activity.OrderStepTraceActivity;
import com.yzfsys.app.adapter.OrderAdapter;
import com.yzfsys.app.adapter.WorkAdapter;
import com.yzfsys.app.bean.OrderEntry;
import com.yzfsys.app.bean.WorkEntry;
import com.yzfsys.app.common.Constants;
import com.yzfsys.app.util.HttpManager;
import com.yzfsys.app.util.StringUtils;

public class WorkListFragment extends Fragment {

	private static final String API_TODO_REQUEST = "todos";
	private ListView mLvWorkList;
    private ArrayList<WorkEntry> mEntries = new ArrayList<WorkEntry>();
    private WorkAdapter mAdapter;
    private TextView mTvNoData;
	private ProgressDialog mPd = null;
	private SharedPreferences mSp;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_work_list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		MainActivity activity = (MainActivity) getActivity();
		activity.setTitleText("待办事项");		
		mPd = new ProgressDialog(this.getActivity());
		mSp = activity.getSharedPreferences("yzfsp", Context.MODE_PRIVATE);			
		mLvWorkList = (ListView)activity.findViewById(R.id.lv_fwl_list);
		mAdapter = new WorkAdapter(getActivity(), 0, mEntries);
		mLvWorkList.setAdapter(mAdapter);
		mLvWorkList.setOnItemClickListener(createLvItemClickListener());
		mTvNoData = (TextView) activity.findViewById(R.id.tv_fwl_no_data);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mEntries.clear();
		initTodoData();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mEntries.clear();
	}
	
	private AdapterView.OnItemClickListener createLvItemClickListener() {
		return new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, 
					int position, long id) {
				
				if (view == null)
					return;
				
				WorkEntry workEntry = (WorkEntry) view.getTag(R.id.id_entry);
				
				if (workEntry == null)
					return;
				OrderEntry orderEntry = new OrderEntry();
				orderEntry.setId(workEntry.getOrderId());
				orderEntry.setContractNo(workEntry.getContractNo());
				orderEntry.setCustomer(workEntry.getCustomer());
				Intent intent = new Intent(view.getContext(), OrderPhaseTraceActivity.class);
				intent.putExtra(Constants.ORDER_ENTRY_SERIALIZABLE_KEY, orderEntry);
				startActivity(intent);		
			}
		};
	}

	private void initTodoData() {
		try {
			String token = mSp.getString(Constants.SP_KEY_TOKEN, "");
			if(StringUtils.isEmpty(token)) {
				relogin(this.getActivity());
				return;
			}
			mPd.setMessage("加载中...");
			mPd.show();
			mTvNoData.setVisibility(View.GONE);
			mLvWorkList.setVisibility(View.VISIBLE);
			
	        String requestUrl = Constants.URL_YZF + API_TODO_REQUEST 
	        		+ "?token=" + token;				

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
                		JSONArray entries = response.getJSONArray("todos");
                        JSONObject entry;
                        for (int i = 0; i < entries.length(); i++) {
                            entry = entries.getJSONObject(i);
                            WorkEntry workEntry = new WorkEntry();
                            workEntry.parse(entry);
                            mEntries.add(workEntry);
                        }
                        mAdapter.notifyDataSetChanged();
                	} else {
                		final String message = StringUtils.formatEmpty(response.getString("msg"));
                		if(Constants.MESSAGE_NO_WORK.equals(message)) {
                    		mTvNoData.setVisibility(View.VISIBLE);
                    		mTvNoData.setText(Constants.MESSAGE_NO_WORK);
                    		mLvWorkList.setVisibility(View.GONE);
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
            	Log.e(this.getClass().getName(), error.getCause().toString());
                showErrorDialog();
            }
        };
    }
    
    protected void showErrorDialog() {
		Toast.makeText(this.getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
    }
    
    private void relogin(Context context) {
    	Log.i(this.getClass().getName(), "token expried, relogin");
		Intent intent = new Intent(context, LoginActivity.class);
		context.startActivity(intent);    	
    }	
	
}
