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
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.yzfsys.app.R;
import com.yzfsys.app.bean.StepImageEntry;
import com.yzfsys.app.common.Constants;
import com.yzfsys.app.util.BitmapSize;
import com.yzfsys.app.util.ImageUtils;


public class OrderStepUploadAdapter extends ArrayAdapter<StepImageEntry> {
	
//	private View.OnClickListener mAddPicListener;
	private ImageLoader mImageLoader;
	
    public OrderStepUploadAdapter(Context context, 
                              int textViewResourceId, 
                              List<StepImageEntry> objects,
                              ImageLoader imageLoader
                              ) {
        super(context, textViewResourceId, objects);
        mImageLoader = imageLoader;
//        mAddPicListener = listener;
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
            holder.networkImage.setImageUrl(Constants.URL_YZF_IMAGE+entry.getImageUrl(), mImageLoader);
        } else {
            holder.networkImage.setImageResource(R.drawable.no_image);
        }
//        holder.networkImage.setVisibility(View.GONE);
//        holder.localImage.setVisibility(View.VISIBLE);
//        if(entry.getDrawableId() != 0) {
//        	holder.localImage.setImageBitmap(null);
//        	holder.localImage.setBackgroundResource(entry.getDrawableId());
////        	if(mAddPicListener != null) {
////        		holder.localImage.setOnClickListener(mAddPicListener);
////        	}
//        } else {
//        	holder.localImage.setBackground(null);
//        	holder.localImage.setOnClickListener(null);
//        	holder.localImage.setImageBitmap(getBitmap(entry.getImageUrl()));
//        }
        
        
        return v;
    }
    
    private Bitmap getBitmap(String filePath) {
    	BitmapSize bitmapSize = new BitmapSize(110, 110);
    	return ImageUtils.decodeSampledBitmapFromFile(filePath, bitmapSize, null);
    }
    
    protected class ViewHolder {
        NetworkImageView networkImage;
        ImageView localImage;
        
        public ViewHolder(View v) {
        	networkImage = (NetworkImageView) v.findViewById(R.id.niv_igi_network_image);
        	localImage = (ImageView) v.findViewById(R.id.iv_igi_local_image);
            v.setTag(this);
        }
    }
}
