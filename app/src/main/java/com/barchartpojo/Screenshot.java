package com.barchartpojo;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Screenshot {

    public static String getScreenshot(View view, Context context){




//        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache(true);
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        //give YourVideoUrl below
        retriever.setDataSource("https://d1ytro2lmo1bmr.cloudfront.net/videos/BigBuckBunny.mp4", new HashMap<String, String>());
// this gets frame at 2nd second
        Bitmap bitmap = retriever.getFrameAtTime(2000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);

//        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());

        //Save bitmap
        String extr = Environment.getExternalStorageDirectory().toString() +   File.separator + "Aplis";


        String fileName = System.currentTimeMillis()+"_screenshot.jpg";
//        String fileName = new SimpleDateFormat(System.currentTimeMillis()+""+"_screenshot.jpg'").format(new Date());
        File myPath = new File(extr, fileName);
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "Aplis");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Screen", "screen");
        }catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


//        screenView.setDrawingCacheEnabled(false);
        return fileName;

    }

}
