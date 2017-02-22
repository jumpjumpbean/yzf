
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
import com.yzfsys.app.bean.MaterialEntry;
import com.yzfsys.app.bean.OrderEntry;
import com.yzfsys.app.common.OrderStatus;
import com.yzfsys.app.util.StringUtils;


public class OrderMaterialAdapter extends ArrayAdapter<MaterialEntry> {
    
    public OrderMaterialAdapter(Context context, 
                              int textViewResourceId, 
                              List<MaterialEntry> titles
                              ) {
        super(context, textViewResourceId, titles);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.activity_material_item, null);
        }
        
        ViewHolder holder = (ViewHolder) v.getTag(R.id.id_holder);       
        
        if (holder == null) {
            holder = new ViewHolder(v);
            v.setTag(R.id.id_holder, holder);
        }        
        
        MaterialEntry entry = (MaterialEntry)getItem(position);
        v.setTag(R.id.id_entry, entry);
        holder.name.setText(entry.getName());
        holder.type.setText("类型："+entry.getType());
        holder.model.setText("型号："+entry.getBrand()+entry.getModel());
        holder.count.setText("数量："+entry.getCount()+entry.getUnit());
        
        return v;
    }
    
    protected class ViewHolder {
        TextView name; 
        TextView type;
        TextView model;
        TextView count;
        
        public ViewHolder(View v) {
        	name = (TextView) v.findViewById(R.id.tv_ami_name);
        	type = (TextView) v.findViewById(R.id.tv_ami_type);
        	model = (TextView) v.findViewById(R.id.tv_ami_model);
        	count = (TextView) v.findViewById(R.id.tv_ami_count);
            v.setTag(this);
        }
    }
}
