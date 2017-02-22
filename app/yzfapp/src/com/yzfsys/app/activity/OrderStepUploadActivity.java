package com.yzfsys.app.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yzfsys.app.R;
import com.yzfsys.app.adapter.OrderStepUploadAdapter;
import com.yzfsys.app.bean.OrderStepEntry;
import com.yzfsys.app.bean.StepImageEntry;
import com.yzfsys.app.common.Constants;
import com.yzfsys.app.uploadservice.AbstractUploadServiceReceiver;
import com.yzfsys.app.uploadservice.UploadRequest;
import com.yzfsys.app.uploadservice.UploadService;
import com.yzfsys.app.util.HttpManager;
import com.yzfsys.app.util.StringUtils;
import com.yzfsys.app.widget.FButton;

public class OrderStepUploadActivity extends BaseActivity {

	private static final String TAG = "AndroidUploadService";
	private static final String API_STEP_IMAGE_UPLOAD = "upload";
	private static final String API_STEP_IMAGE_REQUEST = "pics";
	private static final String API_STEP_REQUEST_APPROVE = "applyExamine";
	private static final String UPLOAD_ID = UUID.randomUUID().toString();
	private OrderStepEntry mOrderStepEntry = null;
	private ArrayList<StepImageEntry> mEntries = new ArrayList<StepImageEntry>();
	private OrderStepUploadAdapter mAdapter;
	private ProgressDialog mPd = null;
	private SharedPreferences mSp;
	private GridView mImageGrid;
	private Bitmap mImage;
    private FButton mBtnSubmit;
    private FButton mBtnUpload;
    private TextView mTvNoData;
//    private int mIndex;

	private final AbstractUploadServiceReceiver uploadReceiver = new AbstractUploadServiceReceiver() {

		@Override
		public void onProgress(String uploadId, int progress) {
			Log.i(TAG, "The progress of the upload with ID " 
					+ uploadId + " is: " + progress);
		}

		@Override
		public void onError(String uploadId, Exception exception) {
			Log.e(TAG, "Error in upload with ID: " + uploadId + ". " 
					+ exception.getLocalizedMessage(), exception);
			mPd.dismiss();
			Toast.makeText(OrderStepUploadActivity.this, "上传失败", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCompleted(String uploadId,
								int serverResponseCode, 
								String serverResponseMessage,
								String serverResponseContent) {
			if (serverResponseCode != HttpURLConnection.HTTP_OK) {
				Log.e(TAG, "Error in upload with ID: " + uploadId + ". "
						+ serverResponseMessage);
				mPd.dismiss();
				Toast.makeText(OrderStepUploadActivity.this, 
						"上传失败，" + serverResponseMessage, Toast.LENGTH_LONG).show();
			} else {
				try {
					JSONObject response = new JSONObject(serverResponseContent);
					String result = response.getString("result");
					String message = response.getString("msg");
					if(Constants.RESULT_SUCCESS.equals(result)) {
						Log.i(TAG, "Upload with ID " + uploadId 
								+ " is completed");	
						mPd.dismiss();
						Toast.makeText(OrderStepUploadActivity.this, "上传成功", 
								Toast.LENGTH_LONG).show();
						mEntries.clear();
						initStepData();
					} else {
						Log.e(TAG, "Error in upload with ID: " + uploadId + ". "
								+ message);
						mPd.dismiss();
						Toast.makeText(OrderStepUploadActivity.this, 
								"上传失败，" + message, Toast.LENGTH_LONG).show();						
					}
				} catch (Exception e) {
					Log.e(TAG, e.getMessage());
				}
				
			}
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		mOrderStepEntry = (OrderStepEntry) intent.getSerializableExtra(Constants.ORDER_STEP_ENTRY_SERIALIZABLE_KEY);
		setContentView(R.layout.activity_image_upload);
		mTvTitle = (TextView)findViewById(R.id.tv_ab_title);
		this.setTitleText("上传图片");
		
		mAdapter = new OrderStepUploadAdapter(this, 0, mEntries, HttpManager.getInstance().getImageLoader());
		mImageGrid = (GridView) findViewById(R.id.gv_aiu_image_upload);
		mImageGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mImageGrid.setAdapter(mAdapter);
//		StepImageEntry entry = new StepImageEntry();
//		entry.setDrawableId(R.drawable.bt_add_pic);
//		mEntries.add(entry);
//		mAdapter.notifyDataSetChanged();
//		mIndex = 0;
		mPd = new ProgressDialog(this);
		mSp = getSharedPreferences("yzfsp", Context.MODE_PRIVATE);
		mTvNoData = (TextView) findViewById(R.id.tv_aiu_no_data);
		mBtnUpload = (FButton) findViewById(R.id.btn_aiu_upload);
		mBtnUpload.setOnClickListener(createImageClickListener());
		mBtnSubmit = (FButton) findViewById(R.id.btn_aiu_submit);
		mBtnSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				requestApprove();
			}
		});
	}

    @Override
    protected void onResume() {
        super.onResume();
        uploadReceiver.register(this);
		mEntries.clear();
		initStepData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
        	uploadReceiver.unregister(this);
        } catch (IllegalArgumentException e) {
            // ignore
        }
    }

	@Override
	public void onDestroy() {
		super.onDestroy();
		mEntries.clear();
	}
    
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        
        super.onActivityResult(requestCode, resultCode, data);             
        if(resultCode != RESULT_OK) {
//        	Toast.makeText(this, "拍照失败", Toast.LENGTH_SHORT).show();
        	return;
        }
        if(requestCode==0){  
        	setImageFromPhotoAlbum(data);
        }else if(requestCode==1){
        	setImageFromCamera(data);
        }  
//        mIndex = 0;
    }
    
	private void initStepData() {
		try {
			String token = mSp.getString(Constants.SP_KEY_TOKEN, "");
			if(StringUtils.isEmpty(token)) {
				relogin(this.getClass().getName(), this);
				return;
			}
			mPd.setMessage("加载中...");
			mPd.show();
			mTvNoData.setVisibility(View.GONE);
			mImageGrid.setVisibility(View.VISIBLE);
			
	        String requestUrl = Constants.URL_YZF + API_STEP_IMAGE_REQUEST 
	        		+ "?token=" + token
	        		+ "&procstepid=" + mOrderStepEntry.getId();

	        JsonObjectRequest request = new JsonObjectRequest(Method.POST,
	        		requestUrl,
	        		null,
	        		createPicReqSuccessListener(), 
	        		createReqErrorListener());

	        request.setShouldCache(false);
	        HttpManager.getInstance().addToRequestQueue(request); 				
		} catch(Exception e) {
			e.printStackTrace();
			Log.e(this.getClass().getName(), e.getMessage());
		}		
	}

    private Response.Listener<JSONObject> createPicReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                	mPd.dismiss();
                	String result = StringUtils.formatEmpty(response.getString("result"));
                	if(Constants.RESULT_SUCCESS.equals(result)) {
                		JSONArray entries = response.getJSONArray("oplog");
                        JSONObject entry;
                        for (int i = 0; i < entries.length(); i++) {
                            entry = entries.getJSONObject(i);
                            StepImageEntry stepImageEntry = new StepImageEntry();
                            stepImageEntry.parse(entry);
                            mEntries.add(stepImageEntry);
                        }
                        mAdapter.notifyDataSetChanged();
                	} else {
                		final String message = StringUtils.formatEmpty(response.getString("msg"));
                		if(Constants.MESSAGE_NO_PICTURE.equals(message)) {
                    		mTvNoData.setVisibility(View.VISIBLE);
                    		mTvNoData.setText(Constants.MESSAGE_NO_PICTURE);
                    		mImageGrid.setVisibility(View.GONE);
                		} else {
                			showErrorDialog();	
                		}
                	}
                } catch (Exception e) {               	
                    showErrorDialog();
                }
            }
        };
    }
    
	private View.OnClickListener createImageClickListener() {
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
                final CharSequence[] items = { "相册", "拍照" };  
                
                AlertDialog dlg = new AlertDialog.Builder(OrderStepUploadActivity.this).setTitle("选择照片").setItems(items,   
                        new DialogInterface.OnClickListener() {  
                              
                            @Override  
                            public void onClick(DialogInterface dialog, int which) {  

                                if(which==1){
                                	getImageFromCamera();
                                }else{
                                	getImageFromPhotoAlbum();
                                }  
                                  
                            }  
                        }).create();  
                dlg.show();				
			}
		};
	}

	private void getImageFromCamera() {
	    destoryBimap();
	    String state = Environment.getExternalStorageState();
	    if (state.equals(Environment.MEDIA_MOUNTED)) {
	        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        startActivityForResult(intent, 1);
	    } else {
	        Toast.makeText(OrderStepUploadActivity.this, "没有SD卡", Toast.LENGTH_LONG).show();
	    }
	}
	
	private void getImageFromPhotoAlbum() {
    	Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
    	getImage.addCategory(Intent.CATEGORY_OPENABLE);
    	getImage.setType("image/jpeg");
    	startActivityForResult(getImage, 0);		
	}
	
	private void destoryBimap()
	{
	    if (mImage != null && ! mImage.isRecycled()) {
	    	mImage.recycle();
	    	mImage = null;
	    }
	}
	
    private void setImageFromCamera(Intent data) {
        Uri uri = data.getData();
        if (uri != null) {
            mImage = BitmapFactory.decodeFile(uri.getPath());
        }
        if (mImage == null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
            	mImage = (Bitmap) bundle.get("data");
            } else {
                Toast.makeText(OrderStepUploadActivity.this, "拍照失败", Toast.LENGTH_LONG).show();
                return;
            }
        }
     
        FileOutputStream fileOutputStream = null;
        try {
            String saveDir = Environment.getExternalStorageDirectory()
            		+ Constants.YZF_STORE_DIRECTORY;
            File dir = new File(saveDir);
            if (!dir.exists()) dir.mkdir();
            SimpleDateFormat t = new SimpleDateFormat("yyyyMMddssSSS");
            String filename = "MT" + (t.format(new Date())) + ".jpg";
            File file = new File(saveDir, filename);
            fileOutputStream = new FileOutputStream(file);
            mImage.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            upload(this, file.getPath());
//            StepImageEntry entry = new StepImageEntry();
//            entry.setImageUrl(file.getPath());
//            mEntries.add(0, entry);
//            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            destoryBimap();
        }    	
    }
    
    private void setImageFromPhotoAlbum(Intent data) {
        try {  
            Uri selectedImage = data.getData();  
            String[] filePathColumn = { MediaStore.Images.Media.DATA };  

            Cursor cursor = getContentResolver().query(selectedImage,  
                    filePathColumn, null, null, null);  
            cursor.moveToFirst();  

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);  
            String imagePath = cursor.getString(columnIndex);  
            cursor.close();
            upload(this, imagePath);
//            StepImageEntry entry = new StepImageEntry();
//            entry.setImageUrl(imagePath);
//            mEntries.add(0, entry);
//            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {  
            Log.e(this.getClass().getName(), e.getMessage());  
            e.printStackTrace();  
        }
    }
    
    public void upload(final Context context, final String filePath) {
    	
		String token = mSp.getString(Constants.SP_KEY_TOKEN, "");
		if(StringUtils.isEmpty(token)) {
			relogin(this.getClass().getName(), this);
			return;
		}
        String requestUrl = Constants.URL_YZF + API_STEP_IMAGE_UPLOAD 
        		+ "?token=" + token
        		+ "&procstepid=" + mOrderStepEntry.getId();
        
    	final UploadRequest request = new UploadRequest(context, 
    			UPLOAD_ID, requestUrl);

//    	if(index < 0 || index >= mEntries.size()-1) {
//    		return;
//    	}
//    	StepImageEntry entry = mEntries.get(index);
    	
    	request.addFileToUpload(filePath, 
    							"Filedata",
    							"test.jpg",
    							null);

//    	request.addHeader("your-custom-header", "your-custom-value");
//    	request.addParameter("parameter-name", "parameter-value");

    	//configure the notification
    	request.setNotificationConfig(0,
    								"notification title",
    								"upload in progress text",
    								"upload completed successfully text",
    								"upload error text",
    								false);

    	try {
			mPd.setMessage("上传中...");
			mPd.show();
    		//Start upload service and display the notification
    		UploadService.startUpload(request);

    	} catch (Exception exc) {
    		//You will end up here only if you pass an incomplete UploadRequest
    		Log.e("AndroidUploadService", exc.getLocalizedMessage(), exc);
    	}
    }
    
	private void requestApprove() {
		try {
			String token = mSp.getString(Constants.SP_KEY_TOKEN, "");
			if(StringUtils.isEmpty(token)) {
				relogin(this.getClass().getName(), this);
				return;
			}
			mPd.setMessage("加载中...");
			mPd.show();
			
	        String requestUrl = Constants.URL_YZF + API_STEP_REQUEST_APPROVE 
	        		+ "?token=" + token
	        		+ "&procstepid=" + mOrderStepEntry.getId();

	        JsonObjectRequest request = new JsonObjectRequest(Method.POST,
	        		requestUrl,
	        		null,
	        		createReqSuccessListener(), 
	        		createReqErrorListener());

	        request.setShouldCache(false);
	        HttpManager.getInstance().addToRequestQueue(request); 				
		} catch(Exception e) {
			e.printStackTrace();
			Log.e(this.getClass().getName(), e.getMessage());
		}		
	}

    private Response.Listener<JSONObject> createReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                	mPd.dismiss();
                	String result = StringUtils.formatEmpty(response.getString("result"));
                	if(Constants.RESULT_SUCCESS.equals(result)) {
                		Toast.makeText(OrderStepUploadActivity.this, "申请成功", Toast.LENGTH_LONG).show();
                		OrderStepUploadActivity.this.finish();
                	} else {
                		showErrorDialog();
                	}
                } catch (Exception e) {               	
                    showErrorDialog();
                }
            }
        };
    }

    private Response.ErrorListener createReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	mPd.dismiss();
            	VolleyLog.e(this.getClass().getName(), error.getCause());
                showErrorDialog();
            }
        };
    }	
}
