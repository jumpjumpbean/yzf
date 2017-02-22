
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
import com.yzfsys.app.bean.OrderStepEntry;
import com.yzfsys.app.bean.OrderStepOplogEntry;


public class OrderStepOplogAdapter extends ArrayAdapter<OrderStepOplogEntry> {
    
    public OrderStepOplogAdapter(Context context, 
                              int textViewResourceId, 
                              List<OrderStepOplogEntry> titles
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
            
            OrderStepOplogEntry entry = (OrderStepOplogEntry) getItem(position);
            v.setTag(R.id.id_entry, entry);
            holder.memo.setText(entry.getMemo());
            holder.createdDate.setText(entry.getCreatedDate());
            formatCompleted(holder);      	
        } catch (Exception e) {
        	Log.e("OrderPhaseTraceAdapter", e.getMessage());
        }

               
        return v;
    }
    
    private void formatCompleted(ViewHolder holder) {
    	holder.frame.setBackgroundResource(R.drawable.phase_trace_list_gray_bg_c);
    }
    
    protected class ViewHolder {
    	RelativeLayout frame;
        TextView memo;
        TextView createdDate;
        TextView attitude;
        View line1;
        View line2;
        
        public ViewHolder(View v) {
        	frame = (RelativeLayout) v.findViewById(R.id.rl_fopti_frame);
        	memo = (TextView) v.findViewById(R.id.tv_fopti_phase_name);
        	createdDate = (TextView) v.findViewById(R.id.tv_fopti_plan_date);
        	line1 = (View) v.findViewById(R.id.v_fopti_track_line1);
        	line1 = (View) v.findViewById(R.id.v_fopti_track_line2);
            v.setTag(this);
        }
    }
}
