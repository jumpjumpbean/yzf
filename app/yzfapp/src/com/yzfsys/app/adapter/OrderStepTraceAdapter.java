
package com.yzfsys.app.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzfsys.app.R;
import com.yzfsys.app.adapter.OrderPhaseTraceAdapter.ViewHolder;
import com.yzfsys.app.bean.OrderPhaseEntry;
import com.yzfsys.app.bean.OrderStepEntry;
import com.yzfsys.app.common.OrderStatus;


public class OrderStepTraceAdapter extends ArrayAdapter<OrderStepEntry> {
    
    public OrderStepTraceAdapter(Context context, 
                              int textViewResourceId, 
                              List<OrderStepEntry> titles
                              ) {
        super(context, textViewResourceId, titles);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        try {
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.fragment_order_phase_trace_item, null);
            }
            
            ViewHolder holder = (ViewHolder) v.getTag(R.id.id_holder);       
            
            if (holder == null) {
                holder = new ViewHolder(v);
                v.setTag(R.id.id_holder, holder);
            }        
            
            OrderStepEntry entry = (OrderStepEntry) getItem(position);
            v.setTag(R.id.id_entry, entry);
            holder.step.setText(getStepName(entry));
            if(!OrderStatus.STR_NOT_START.equals(entry.getStatus())) {
            	holder.updatedDate.setVisibility(View.VISIBLE);
            	holder.updatedDate.setText(entry.getUpdatedDate());
            } else {
            	holder.updatedDate.setVisibility(View.GONE);
            }
            if(OrderStatus.STR_COMPLETED.equals(entry.getStatus())) {
            	formatCompleted(holder);
//            } else if(OrderStatus.STR_IN_PROGRESS.equals(entry.getStatus())) {
//            	formatInProgress(holder);
            } else {
            	formatIncomplete(holder, v);
            }        	
        } catch (Exception e) {
        	Log.e("OrderPhaseTraceAdapter", e.getMessage());
        }

               
        return v;
    }
    
    private void formatCompleted(ViewHolder holder) {
    	holder.frame.setBackgroundResource(R.drawable.phase_trace_list_gray_bg_c);
    }

    private void formatInProgress(ViewHolder holder) {
    	holder.frame.setBackgroundResource(R.color.progress_normal);
    }
    
    private void formatIncomplete(ViewHolder holder, View v) {
    	holder.frame.setBackgroundResource(R.drawable.phase_trace_list_bg_c);
    	holder.step.setTextColor(v.getResources().getColor(R.color.progress_track_normal));
    }
  
    private String getStepName(OrderStepEntry entry) {
    	String name = "";
    	
    	if(null == entry) return "";
    	
    	name = entry.getName() + " " + OrderStatus.getStatusDesc(entry.getStatus());
    	return name;
    }
    
    protected class ViewHolder {
    	RelativeLayout frame;
        TextView step;
        TextView updatedDate;
        TextView attitude;
        View line1;
        View line2;
        
        public ViewHolder(View v) {
        	frame = (RelativeLayout) v.findViewById(R.id.rl_fopti_frame);
        	step = (TextView) v.findViewById(R.id.tv_fopti_phase_name);
        	updatedDate = (TextView) v.findViewById(R.id.tv_fopti_plan_date);
        	line1 = (View) v.findViewById(R.id.v_fopti_track_line1);
        	line1 = (View) v.findViewById(R.id.v_fopti_track_line2);
            v.setTag(this);
        }
    }
}
