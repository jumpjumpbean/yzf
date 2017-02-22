
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
import com.yzfsys.app.bean.OrderPhaseEntry;
import com.yzfsys.app.common.OrderStatus;


public class OrderPhaseTraceAdapter extends ArrayAdapter<OrderPhaseEntry> {
    
    public OrderPhaseTraceAdapter(Context context, 
                              int textViewResourceId, 
                              List<OrderPhaseEntry> titles
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
            
            OrderPhaseEntry entry = (OrderPhaseEntry)getItem(position);
            v.setTag(R.id.id_entry, entry);
            holder.name.setText(getPhaseName(entry));
            holder.planDate.setText(entry.getPlanDate());
            if(!OrderStatus.STR_NOT_START.equals(entry.getStatus())) {
            	holder.actualDate.setVisibility(View.VISIBLE);
            	holder.actualDate.setText(entry.getActualDate());
            }
            if(OrderStatus.STR_COMPLETED.equals(entry.getStatus())) {
            	holder.comments.setVisibility(View.VISIBLE);
            	holder.comments.setText(entry.getCommentsDesc());
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
    	holder.itemFrame.setBackgroundResource(R.color.progress_normal);
    }
    
    private void formatIncomplete(ViewHolder holder, View v) {
    	holder.frame.setBackgroundResource(R.drawable.phase_trace_list_bg_c);
    	holder.name.setTextColor(v.getResources().getColor(R.color.progress_track_normal));
    	holder.planDate.setTextColor(v.getResources().getColor(R.color.progress_track_normal));
    	holder.actualDate.setTextColor(v.getResources().getColor(R.color.progress_track_normal));
    }
    
    private String getPhaseName(OrderPhaseEntry entry) {
    	String name = "";
    	
    	if(null == entry) return "";
    	
    	name = entry.getPhaseName() + " " + OrderStatus.getStatusDesc(entry.getStatus());
    	return name;
    }
    
    protected class ViewHolder {
    	RelativeLayout frame;
    	RelativeLayout itemFrame;
        TextView name;
        TextView planDate;
        TextView actualDate;
        TextView comments;
        View line1;
        View line2;
        
        public ViewHolder(View v) {
        	frame = (RelativeLayout) v.findViewById(R.id.rl_fopti_frame);
        	itemFrame = (RelativeLayout) v.findViewById(R.id.rl_fopti_trace_item);
        	name = (TextView) v.findViewById(R.id.tv_fopti_phase_name);
        	planDate = (TextView) v.findViewById(R.id.tv_fopti_plan_date);
        	actualDate = (TextView) v.findViewById(R.id.tv_fopti_actual_date);
        	comments = (TextView) v.findViewById(R.id.tv_fopti_comments);
        	line1 = (View) v.findViewById(R.id.v_fopti_track_line1);
        	line1 = (View) v.findViewById(R.id.v_fopti_track_line2);
            v.setTag(this);
        }
    }
}
