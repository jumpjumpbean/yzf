
package com.yzfsys.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yzfsys.app.R;
import com.yzfsys.app.bean.WorkEntry;


public class WorkAdapter extends ArrayAdapter<WorkEntry> {
    
    public WorkAdapter(Context context, 
                              int textViewResourceId, 
                              List<WorkEntry> titles
                              ) {
        super(context, textViewResourceId, titles);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.fragment_work_item, null);
        }
        
        ViewHolder holder = (ViewHolder) v.getTag(R.id.id_holder);       
        
        if (holder == null) {
            holder = new ViewHolder(v);
            v.setTag(R.id.id_holder, holder);
        }        
        
        WorkEntry entry = (WorkEntry) getItem(position);
        v.setTag(R.id.id_entry, entry);
        holder.todo.setText("订单"+entry.getContractNo()+"等待您的处理");
        holder.phase.setText("阶段："+entry.getPhaseName());
        holder.step.setText("步骤："+entry.getStepName());
        holder.customer.setText("客户："+entry.getCustomer());
        
        return v;
    }
      
    protected class ViewHolder {
        TextView todo; 
        TextView phase;
        TextView step;
        TextView customer;
        
        public ViewHolder(View v) {
        	todo = (TextView) v.findViewById(R.id.tv_fwi_todo);
        	phase = (TextView) v.findViewById(R.id.tv_fwi_phase);
        	step = (TextView) v.findViewById(R.id.tv_fwi_step);
        	customer = (TextView) v.findViewById(R.id.tv_fwi_customer);
            v.setTag(this);
        }
    }
}
