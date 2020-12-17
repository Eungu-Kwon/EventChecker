package com.swdesign.eventchecker.StaticMethod;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageFileManager {

    // 썸네일 & 이미지 리스트 아이템으로 쓰일 ICON 파일 생성
    public static void saveImageIcon(Context c, File f){
        String iconFile;
        iconFile = c.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + f.getName() + "_icon";
        File tempFile = new File(iconFile);
        try {
            FileOutputStream out = new FileOutputStream(tempFile);

            Bitmap bmp = ImageCompute.getBmpFromPathWithResize(f.getAbsolutePath(), 200);
            bmp = ImageCompute.getCroppedImage(bmp);

            bmp.compress(Bitmap.CompressFormat.JPEG, 20, out);
            out.close();
            bmp.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 파일을 만들고 반환
    public static File createImageFile(Context c, String url) {
        String imageFileName = url;
        File storageDir = c.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir + "/" + imageFileName);
        Log.d("mTag", "created" + image.getAbsolutePath());
        return image;
    }
}
