package com.yzfsys.app.util;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * ImageUtils
 * 
 * convert between Bitmap, byte array, Drawable
 */
public class ImageUtils {

	private final static String TAG = "ImageUtils";
    /**
     * convert Bitmap to byte array
     * 
     * @param b
     * @return
     */
    public static byte[] bitmapToByte(Bitmap b) {
        if (b == null) {
            return null;
        }

        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, o);
        return o.toByteArray();
    }

    /**
     * convert byte array to Bitmap
     * 
     * @param b
     * @return
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * convert Drawable to Bitmap
     * 
     * @param d
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable d) {
        return d == null ? null : ((BitmapDrawable)d).getBitmap();
    }

    /**
     * convert Bitmap to Drawable
     * 
     * @param b
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap b) {
        return b == null ? null : new BitmapDrawable(b);
    }

    /**
     * convert Drawable to byte array
     * 
     * @param d
     * @return
     */
    public static byte[] drawableToByte(Drawable d) {
        return bitmapToByte(drawableToBitmap(d));
    }

    /**
     * scale image
     * 
     * @param org
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
        return scaleImage(org, (float)newWidth / org.getWidth(), (float)newHeight / org.getHeight());
    }

    /**
     * scale image
     * 
     * @param org
     * @param scaleWidth sacle of width
     * @param scaleHeight scale of height
     * @return
     */
    public static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight) {
        if (org == null) {
            return null;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
    }

    /**
     * close inputStream
     * 
     * @param s
     */
    private static void closeInputStream(InputStream s) {
        if (s == null) {
            return;
        }

        try {
            s.close();
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        }
    }
    
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, BitmapSize maxSize, Bitmap.Config config) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        options.inInputShareable = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, maxSize.getWidth(), maxSize.getHeight());
        options.inJustDecodeBounds = false;
        if (config != null) {
            options.inPreferredConfig = config;
        }
        try {
            return BitmapFactory.decodeResource(res, resId, options);
        } catch (Throwable e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static Bitmap decodeSampledBitmapFromFile(String filename, BitmapSize maxSize, Bitmap.Config config) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        options.inInputShareable = true;
        BitmapFactory.decodeFile(filename, options);
        options.inSampleSize = calculateInSampleSize(options, maxSize.getWidth(), maxSize.getHeight());
        options.inJustDecodeBounds = false;
        if (config != null) {
            options.inPreferredConfig = config;
        }
        try {
            return BitmapFactory.decodeFile(filename, options);
        } catch (Throwable e) {
        	Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static Bitmap decodeSampledBitmapFromDescriptor(FileDescriptor fileDescriptor, BitmapSize maxSize, Bitmap.Config config) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        options.inInputShareable = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        options.inSampleSize = calculateInSampleSize(options, maxSize.getWidth(), maxSize.getHeight());
        options.inJustDecodeBounds = false;
        if (config != null) {
            options.inPreferredConfig = config;
        }
        try {
            return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        } catch (Throwable e) {
        	Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static Bitmap decodeSampledBitmapFromByteArray(byte[] data, BitmapSize maxSize, Bitmap.Config config) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        options.inInputShareable = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        options.inSampleSize = calculateInSampleSize(options, maxSize.getWidth(), maxSize.getHeight());
        options.inJustDecodeBounds = false;
        if (config != null) {
            options.inPreferredConfig = config;
        }
        try {
            return BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } catch (Throwable e) {
        	Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static Bitmap decodeResource(Resources res, int resId) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inInputShareable = true;
        try {
            return BitmapFactory.decodeResource(res, resId, options);
        } catch (Throwable e) {
        	Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static Bitmap decodeFile(String filename) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inInputShareable = true;
        try {
            return BitmapFactory.decodeFile(filename, options);
        } catch (Throwable e) {
        	Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fileDescriptor) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inInputShareable = true;
        try {
            return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        } catch (Throwable e) {
        	Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static Bitmap decodeByteArray(byte[] data) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inInputShareable = true;
        try {
            return BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } catch (Throwable e) {
        	Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int maxWidth, int maxHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (width > maxWidth || height > maxHeight) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) maxHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) maxWidth);
            }

            final float totalPixels = width * height;

            final float maxTotalPixels = maxWidth * maxHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > maxTotalPixels) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }    
}
