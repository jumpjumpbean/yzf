
package com.yzfsys.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzfsys.app.R;
import com.yzfsys.app.bean.OrderEntry;
import com.yzfsys.app.common.OrderStatus;
import com.yzfsys.app.util.StringUtils;


public class OrderAdapter extends ArrayAdapter<OrderEntry> {
    
    public OrderAdapter(Context context, 
                              int textViewResourceId, 
                              List<OrderEntry> titles
                              ) {
        super(context, textViewResourceId, titles);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.fragment_order_item, null);
        }
        
        ViewHolder holder = (ViewHolder) v.getTag(R.id.id_holder);       
        
        if (holder == null) {
            holder = new ViewHolder(v);
            v.setTag(R.id.id_holder, holder);
        }        
        
        OrderEntry entry = (OrderEntry)getItem(position);
        v.setTag(R.id.id_entry, entry);
        holder.customer.setText(entry.getCustomer());
        holder.address.setText(entry.getAddress());
        String planDate = "计划时间：" + entry.getPlanStartDate() + "~" + entry.getPlanEndDate();
        holder.planDate.setText(planDate);
        String curProcess = "当前状态：" + getOrderStatus(entry);
        if(!StringUtils.isEmpty(entry.getCurProcName())) {
        	curProcess += "  当前阶段：" + entry.getCurProcName();
        }
        holder.currentProcess.setText(curProcess);
        if(entry.getIsDelayed()) {
        	holder.frame.setBackgroundResource(R.color.progress_delay);
        }
        
        return v;
    }
    
    private String getOrderStatus(OrderEntry entry) {
    	String orderStatus = "";
    	if(null == entry) return orderStatus;
    	if(OrderStatus.STR_IN_PROGRESS.equals(entry.getStatus())) {
    		if(entry.getIsDelayed()) {
    			orderStatus = "延期";
    		} else {
    			orderStatus = "进度正常";
    		}
    	} else {
    		orderStatus = OrderStatus.getStatusDesc(entry.getStatus());
    	}
    	return orderStatus;
    }
    
    protected class ViewHolder {
    	LinearLayout frame;
        TextView customer; 
        TextView address;
        TextView planDate;
        TextView currentProcess;
        
        public ViewHolder(View v) {
        	frame = (LinearLayout) v.findViewById(R.id.ll_foi_frame);
        	customer = (TextView) v.findViewById(R.id.tv_foi_customer);
        	address = (TextView) v.findViewById(R.id.tv_foi_address);
        	planDate = (TextView) v.findViewById(R.id.tv_foi_plan_date);
        	currentProcess = (TextView) v.findViewById(R.id.tv_foi_current_process);
            v.setTag(this);
        }
    }
}
