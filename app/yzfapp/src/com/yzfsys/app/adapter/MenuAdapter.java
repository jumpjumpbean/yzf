/**
 * 	Copyright (c) 2014 Centling
 * 		All Rights Reserved
 *
 * This is unpublished proprietary source code of Centling.
 * The copyright notice above does not evidence any actual 
 * or intended publication of such source code.
 *
 * This file may not be modified except by authorized Centling Personnel
 */

package com.yzfsys.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzfsys.app.R;


public class MenuAdapter extends ArrayAdapter<String> {
    
    public MenuAdapter(Context context, 
                              int textViewResourceId, 
                              List<String> titles
                              ) {
        super(context, textViewResourceId, titles);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.fragment_menu_item, null);
        }
        
        ViewHolder holder = (ViewHolder) v.getTag(R.id.id_holder);       
        
        if (holder == null) {
            holder = new ViewHolder(v);
            v.setTag(R.id.id_holder, holder);
        }        
        
        String entry = (String)getItem(position);
        
//        holder.icon.setBackground();
        holder.title.setText(entry);
        
        return v;
    }
      
    protected class ViewHolder {
    	ImageView icon;
        TextView title; 
        ImageView mark;
        
        public ViewHolder(View v) {
            icon = (ImageView) v.findViewById(R.id.iv_mfi_icon);
            title = (TextView) v.findViewById(R.id.tv_mfi_title);
            mark = (ImageView) v.findViewById(R.id.iv_mfi_side_dot);
            v.setTag(this);
        }
    }
}
