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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yzfsys.app.R;
import com.yzfsys.app.activity.LoginActivity;
import com.yzfsys.app.activity.MainActivity;
import com.yzfsys.app.activity.OrderPhaseTraceActivity;
import com.yzfsys.app.adapter.OrderAdapter;
import com.yzfsys.app.bean.OrderEntry;
import com.yzfsys.app.common.Constants;
import com.yzfsys.app.util.HttpManager;
import com.yzfsys.app.util.StringUtils;

public class OrderListFragment extends Fragment {

	private static final String TAG = "tag_fragment_order_list";
	private static final String API_ORDER_REQUEST = "project";
	private ListView mLvOrderList;
    private ArrayList<OrderEntry> mEntries = new ArrayList<OrderEntry>();
    private OrderAdapter mAdapter;
	private ProgressDialog mPd = null;
	private SharedPreferences mSp;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_order_list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		MainActivity activity = (MainActivity) getActivity();
		activity.setTitleText("订单一览");
		mPd = new ProgressDialog(this.getActivity());
		mSp = activity.getSharedPreferences("yzfsp", Context.MODE_PRIVATE);
		mLvOrderList = (ListView)activity.findViewById(R.id.lv_fol_list);
		mAdapter = new OrderAdapter(this.getActivity(), 0, mEntries);
		mLvOrderList.setAdapter(mAdapter);
		mLvOrderList.setOnItemClickListener(createLvItemClickListener());
		mEntries.clear();
		initOrderData();
	}

	private AdapterView.OnItemClickListener createLvItemClickListener() {
		return new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, 
					int position, long id) {
				
				if (view == null)
					return;
				
				OrderEntry entry = (OrderEntry)view.getTag(R.id.id_entry);
				
				if (entry == null)
					return;
				
				Intent intent = new Intent(view.getContext(), OrderPhaseTraceActivity.class);
				intent.putExtra(Constants.ORDER_ENTRY_SERIALIZABLE_KEY, entry);
				startActivity(intent);	
			}
		};
	}

	private void initOrderData() {
		try {
			String token = mSp.getString(Constants.SP_KEY_TOKEN, "");
			if(StringUtils.isEmpty(token)) {
				relogin(this.getActivity());
				return;
			}
			mPd.setMessage("加载中...");
			mPd.show();
			
	        String requestUrl = Constants.URL_YZF + API_ORDER_REQUEST 
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
			Log.e(TAG, e.getMessage());
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
                		JSONArray entries = response.getJSONArray("project");
                        JSONObject entry;
                        for (int i = 0; i < entries.length(); i++) {
                            entry = entries.getJSONObject(i);
                            OrderEntry orderEntry = new OrderEntry();
                            orderEntry.parse(entry);
                            mEntries.add(orderEntry);
                        }
                        mAdapter.notifyDataSetChanged();
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
            	Log.e(TAG, error.getMessage());
                showErrorDialog();
            }
        };
    }
    
    protected void showErrorDialog() {
		Toast.makeText(this.getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
    }
    
    private void relogin(Context context) {
    	Log.i(TAG, "token expried, relogin");
		Intent intent = new Intent(context, LoginActivity.class);
		context.startActivity(intent);    	
    }
}
