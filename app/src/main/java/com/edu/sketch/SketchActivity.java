package com.edu.sketch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.edu.aplis.R;
import com.edu.editortemplatetwo.EditorSecondActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;


import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SketchActivity extends Activity implements OnClickListener {
    Context context;
    //custom drawing view
    private DrawingView drawView;
    RelativeLayout drawing1;
    ImageView drawing2;
    //buttons
    private ImageButton currPaint, drawBtn, eraseBtn, newBtn, saveBtn, opacityBtn;
    //sizes
    private float smallBrush, mediumBrush, largeBrush;
    String sub_name="";
    private static final int GET_STORAGE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch);

        context=SketchActivity.this;
        //get drawing view
        drawView = findViewById(R.id.drawing);
        drawing1 = findViewById(R.id.drawing1);
        drawing2 = findViewById(R.id.drawing2);



        if (checkPermission()) {
            sub_name = getIntent().getStringExtra("sub_name");
            Log.e("check","true "+sub_name);

//
//            if (sub_name.equalsIgnoreCase("Daffodils")){
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//            }
//            else{
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//
//            }
        } else {
            Log.e("check","false");

            requestPermission();
        }

//        image = findViewById(R.id.image);


//        if(getIntent().hasExtra("byteArray")) {
//            ImageView _imv= new ImageView(this);
//            Bitmap _bitmap = Coursedetailpager.bitmap;
//            Drawable dr = new BitmapDrawable(_bitmap);
//            drawing1.setBackground(dr);
//        }
//
//        else{
////            drawView.setBackgroundColor(getResources().getColor(R.color.white));
//        }

        String color =getIntent().getStringExtra("color");


        if (color.equalsIgnoreCase("transparent")) {
//            EditorSecondActivity.layout_bg.setDrawingCacheEnabled(true);
//            Bitmap _bitmap = Coursedetailpager.bitmap.getDrawingCache();
//        Coursedetailpager.bitmap.recycle();
//            Drawable dr = new BitmapDrawable(EditorSecondActivity.layout_bg.getDrawingCache());
            drawing1.setTransitionName("imagesketch");

            Glide.with(context).load(sub_name).asBitmap().into(new SimpleTarget<Bitmap>((R.dimen.dp140), (R.dimen.dp140)) {


                @Override
                public void onLoadStarted(Drawable placeholder) {
                    super.onLoadStarted(placeholder);
                    Drawable color = new ColorDrawable(Color.parseColor("#f1f1f1"));
                    drawing1.setBackground(color);
//
////                LayerDrawable ld = new LayerDrawable(new Drawable[]{color, errorDrawable});
////                imageView.setImageDrawable(ld);
//                    Log.e("TAGEDITORbg_image","failed"+itemList.get(position).getColor_code());
//
//                    drawing1.setImageDrawable(color);
                }

                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    Drawable drawable = new BitmapDrawable(context.getResources(), resource);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        drawing1.setBackground(drawable);
                    }
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                }

            });


//            Glide.with(context).load(new File(Environment.getExternalStorageDirectory()+"/Aplis"+"/"+sub_name)).asBitmap().into(new SimpleTarget<Bitmap>((R.dimen.dp220), (R.dimen.dp220)) {
//
//
//                @Override
//                public void onLoadStarted(Drawable placeholder) {
//                    super.onLoadStarted(placeholder);
////                    Drawable color = new ColorDrawable(Color.parseColor("#eddddf"));
////                    drawing1.setImageDrawable(color);
//                }
//
//                @Override
//                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
////                glideAnimation.animate()
//                    Drawable drawable = new BitmapDrawable(context.getResources(), resource);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
////                        Log.e("checkerror","true "+"done");
//
//                        drawing2.setImageDrawable(drawable);
//                    }
//                }
//
//                @Override
//                public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                    super.onLoadFailed(e, errorDrawable);
//                    Log.e("checkerror","true "+e.getMessage());
//
////                Drawable color = new ColorDrawable(Color.parseColor(bgimageurl));
//
////                LayerDrawable ld = new LayerDrawable(new Drawable[]{color, errorDrawable});
////                imageView.setImageDrawable(ld);
////                Log.e("TAGEDITORbg_image","failed"+itemList.get(position).getColor_code());
//
////                layout_bg.setImageDrawable(color);
//
//                }
//
//            });

//            Glide.with(context)
//                    .load(sub_name)
//                    .centerCrop()
//                    .into(drawing1);
//            drawing1.setBackground(dr);
        }
        else{
            drawView.setBackgroundColor(getResources().getColor(R.color.white));
        }
//        Coursedetailpager.bitmap.destroyDrawingCache();
        //get the palette and first color button
        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
        currPaint = (ImageButton)paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        //sizes from dimensions
        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);

        //draw button
        drawBtn = (ImageButton)findViewById(R.id.draw_btn);
        drawBtn.setOnClickListener(this);

        //set initial size
        drawView.setBrushSize(mediumBrush);

        //erase button
        eraseBtn = (ImageButton)findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(this);

        //new button
        newBtn = (ImageButton)findViewById(R.id.new_btn);
        newBtn.setOnClickListener(this);

        //save button
        saveBtn = (ImageButton)findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);

        //opacity
        opacityBtn = (ImageButton)findViewById(R.id.opacity_btn);
        opacityBtn.setOnClickListener(this);
    }


    //user clicked paint
    public void paintClicked(View view){
        //use chosen color

        //set erase false
        drawView.setErase(false);
        drawView.setPaintAlpha(100);
        drawView.setBrushSize(drawView.getLastBrushSize());

        if(view!=currPaint){
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();
            drawView.setColor(color);
            //update ui
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint=(ImageButton)view;
        }
    }

    @Override
    public void onClick(View view){

        if(view.getId()==R.id.draw_btn){
            //draw button clicked
            final Dialog brushDialog = new Dialog(this);
            brushDialog.setTitle("Brush size:");
            brushDialog.setContentView(R.layout.brush_chooser);
            //listen for clicks on size buttons
            ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
            smallBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    drawView.setErase(false);
                    drawView.setBrushSize(smallBrush);
                    drawView.setLastBrushSize(smallBrush);
                    brushDialog.dismiss();
                }
            });
            ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
            mediumBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    drawView.setErase(false);
                    drawView.setBrushSize(mediumBrush);
                    drawView.setLastBrushSize(mediumBrush);
                    brushDialog.dismiss();
                }
            });
            ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
            largeBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    drawView.setErase(false);
                    drawView.setBrushSize(largeBrush);
                    drawView.setLastBrushSize(largeBrush);
                    brushDialog.dismiss();
                }
            });
            //show and wait for user interaction
            brushDialog.show();
        }
        else if(view.getId()==R.id.erase_btn){
            //switch to erase - choose size
            final Dialog brushDialog = new Dialog(this);
            brushDialog.setTitle("Eraser size:");
            brushDialog.setContentView(R.layout.brush_chooser);
            //size buttons
            ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
            smallBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(smallBrush);
                    brushDialog.dismiss();
                }
            });
            ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
            mediumBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(mediumBrush);
                    brushDialog.dismiss();
                }
            });
            ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
            largeBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(largeBrush);
                    brushDialog.dismiss();
                }
            });
            brushDialog.show();
        }
        else if(view.getId()==R.id.new_btn){
            //new button
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("New drawing");
            newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
            newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    drawView.startNew();
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            newDialog.show();
        }

        else if(view.getId()==R.id.save_btn){
//            checkfolderexist();
            //save drawing
            AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
            saveDialog.setTitle("Save drawing");
            saveDialog.setMessage("Save drawing to device Gallery?");
            saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    //save drawing
                    drawing1.setDrawingCacheEnabled(true);
                    String imgSaved =   createDirectoryAndSaveFile(drawing1.getDrawingCache(), UUID.randomUUID().toString()+".png");
                    //attempt to save
//                    String imgSaved = MediaStore.Images.Media.insertImage(
//                            getContentResolver(), drawing1.getDrawingCache(),
//                            Environment.getExternalStorageDirectory()+File.separator+"Aplis"+
//                            UUID.randomUUID().toString()+".png", "drawing");
                    //feedback
                    if(imgSaved!=null){
                        Toast savedToast = Toast.makeText(getApplicationContext(),
                                "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                        savedToast.show();

                        drawing1.destroyDrawingCache();

                    }

                    else{

//                        drawing1.setDrawingCacheEnabled(true);
//
//                        String imgSaved1 = MediaStore.Images.Media.insertImage(
//                                getContentResolver(), drawing1.getDrawingCache(),
//                                UUID.randomUUID().toString()+".png", "drawing");
//
//                        if(imgSaved1!=null){
//                            Toast savedToast = Toast.makeText(getApplicationContext(),
//                                    "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
//                            savedToast.show();
//                        }
//
////                        Toast unsavedToast = Toast.makeText(getApplicationContext(),
////                                "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
////                        unsavedToast.show();
//
//                        drawing1.destroyDrawingCache();
                    }
                }
            });
            saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){

                    drawView.setBackground(null);
                    drawing1.setDrawingCacheEnabled(true);

                    String imgSaved = MediaStore.Images.Media.insertImage(
                            getContentResolver(), drawing1.getDrawingCache(),
                            UUID.randomUUID().toString()+".png", "drawing");
                    drawing1.destroyDrawingCache();
                    dialog.cancel();
                }
            });
            saveDialog.show();
        }
        else if(view.getId()==R.id.opacity_btn){
            //launch opacity chooser
            final Dialog seekDialog = new Dialog(this);
            seekDialog.setTitle("Opacity level:");
            seekDialog.setContentView(R.layout.opacity_chooser);
            //get ui elements
            final TextView seekTxt = (TextView)seekDialog.findViewById(R.id.opq_txt);
            final SeekBar seekOpq = (SeekBar)seekDialog.findViewById(R.id.opacity_seek);
            //set max
            seekOpq.setMax(100);
            //show current level
            int currLevel = drawView.getPaintAlpha();
            seekTxt.setText(currLevel+"%");
            seekOpq.setProgress(currLevel);
            //update as user interacts
            seekOpq.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    seekTxt.setText(Integer.toString(progress)+"%");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}

            });
            //listen for clicks on ok
            Button opqBtn = (Button)seekDialog.findViewById(R.id.opq_ok);
            opqBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    drawView.setPaintAlpha(seekOpq.getProgress());
                    seekDialog.dismiss();
                }
            });
            //show dialog
            seekDialog.show();
        }
    }

    private String createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/Aplis");

        if (!direct.exists()) {
            File wallpaperDirectory = new File("/sdcard/Aplis/");
            wallpaperDirectory.mkdirs();
        }

        File file = new File(new File("/sdcard/Aplis/"), fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "done";
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(SketchActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE}, GET_STORAGE_REQUEST_CODE);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case GET_STORAGE_REQUEST_CODE:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission) {
                        Toast.makeText(SketchActivity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    } else {
                        finish();
                        Toast.makeText(SketchActivity.this,"Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }


}
