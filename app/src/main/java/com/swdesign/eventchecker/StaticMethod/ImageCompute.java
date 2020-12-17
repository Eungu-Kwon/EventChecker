package com.swdesign.eventchecker.StaticMethod;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

// 이미지 관련 계산을 처리하는 스태틱 메소드가 있는 클래스
public class ImageCompute {

    public static final int NO_RESIZE = -1;

    // 지정된 사이즈 이하로 설정가능한 inSampleSize 반환
    // 출처 : https://developer.android.com/topic/performance/graphics/load-bitmap
    public static int calculateInSampleSize(int imageWidth, int imageHeight, int reqWidth, int reqHeight) {
        int inSampleSize = 1;

        if (imageHeight > reqHeight || imageWidth > reqWidth) {

            final int halfHeight = imageHeight / 2;
            final int halfWidth = imageWidth / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    // path에서 이미지를 읽고 회전값만큼 회전시킨 후 Bitmap 이미지를 size만큼 축소시키고 반환 반환
    public static Bitmap getBmpFromPathWithResize(String path, int size){
        if(path == null) return null;

        File f = new File(path);
        if(!f.exists()) return null;

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(f.getAbsolutePath(), options);

        //int orientation = getOrientationOfImage(path);

        if(size != NO_RESIZE) options.inSampleSize = ImageCompute.calculateInSampleSize(options.outWidth, options.outHeight, size, size);
        options.inJustDecodeBounds = false;

        Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath(), options);

        //bmp = getBmpWithRotate(bmp, orientation);

        return bmp;
    }

    // 이미지 중앙을 3:2 비율로 잘라내는 메소드
    public static Bitmap getCroppedImage(Bitmap bmp) {
        int padding = 10;
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int offsetX = 0;
        int offsetY = 0;

        if(width < 10 || height < 10) {
            return bmp;
        }

        if(((height * 4) / 3) < width){
            padding = height / 3;
            offsetX = (width - height - padding) / 2;
            width = height + padding;
        }
        else {
            padding = width / 3;
            offsetY = (height - width + padding) / 2;
            height = width - padding;
        }

        Bitmap croppedBmp = Bitmap.createBitmap(bmp, offsetX, offsetY, width, height);
        bmp.recycle();

        return croppedBmp;
    }

    // inputstream 을 byte[]로 반환
    public static byte[] inputStreamToByteArray(InputStream is) {

        byte[] resBytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int read = -1;
        try {
            while ( (read = is.read(buffer)) != -1 ) {
                bos.write(buffer, 0, read);
            }

            resBytes = bos.toByteArray();
            bos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return resBytes;
    }

}
