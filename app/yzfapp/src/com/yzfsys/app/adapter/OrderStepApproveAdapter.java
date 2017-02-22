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

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.yzfsys.app.R;
import com.yzfsys.app.bean.StepImageEntry;
import com.yzfsys.app.common.Constants;


public class OrderStepApproveAdapter extends ArrayAdapter<StepImageEntry> {
	private ImageLoader mImageLoader;
    
    public OrderStepApproveAdapter(Context context, 
                              int textViewResourceId, 
                              List<StepImageEntry> objects,
                              ImageLoader imageLoader
                              ) {
        super(context, textViewResourceId, objects);
        mImageLoader = imageLoader;
    }

    protected ImageLoader getImageLoader() {
    	return mImageLoader;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.image_grid_item, null);
        }
        
        ViewHolder holder = (ViewHolder) v.getTag(R.id.id_holder);       
        
        if (holder == null) {
            holder = new ViewHolder(v);
            v.setTag(R.id.id_holder, holder);
        }        
        
        StepImageEntry entry = (StepImageEntry) getItem(position);
        v.setTag(R.id.id_entry, entry);
        if (entry.getImageUrl() != null) {
            holder.image.setImageUrl(Constants.URL_YZF_IMAGE+entry.getImageUrl(), mImageLoader);
        } else {
            holder.image.setImageResource(R.drawable.no_image);
        }
        
        return v;
    }
      
    protected class ViewHolder {
        NetworkImageView image;
        
        public ViewHolder(View v) {
            image = (NetworkImageView) v.findViewById(R.id.niv_igi_network_image);
            v.setTag(this);
        }
    }
}
