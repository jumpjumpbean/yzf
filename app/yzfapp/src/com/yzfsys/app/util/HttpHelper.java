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

package com.yzfsys.app.util;

import java.util.HashMap;

import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;


/**
 * Helper class that is used to provide http api
 * 
 */
public class HttpHelper {
	
	public static void createJsonObjectRequest(int method, 
			String url, 
			HashMap<String, String> params, 
			Response.Listener<JSONObject> reqSuccessListener, 
			Response.ErrorListener reqErrorListener) {
        
        JsonObjectRequest myReq = new JsonObjectRequest(method, 
                                                url,
                                                new JSONObject(params),
                                                reqSuccessListener,
                                                reqErrorListener);

        HttpManager.getInstance().addToRequestQueue(myReq);		
	}
	
}
