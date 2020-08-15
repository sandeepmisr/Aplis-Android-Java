package com.edu.web;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.aplis.DemoApplication;
import com.edu.aplis.R;
import com.edu.webservice.Cons;
import java.util.HashMap;

public class TermsConditions extends AppCompatActivity  {
    String data = "";
    WebView view;
    String web_id="";
    Context context;
    HashMap<String,String> hashMap=new HashMap<>();
    private ProgressDialog prg;

    String bg_image = "";
//    ImageView layout_bg;
    ZoomLinearLayout zoomLinearLayout;
    RelativeLayout layout_back;

    private  int counter=0;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.termsconditions);

        view  =findViewById(R.id.webView);
        context = TermsConditions.this;
//        layout_bg = findViewById(R.id.layout_bg);

//        data = "<h4><font face=\"Times New Roman\">i am here</font></h4><p><img src=\"http://35.173.187.82/aplis/public/storage/images/aplis-images-2019-12-30%2023:59:35-5e0a420f14dcd.jpeg\"><font face=\"Times New Roman\"><br></font></p><p><u><i style=\"background-color: rgb(255, 0, 128);\"><font color=\"#ffffff\">can you see image</font></i></u></p>";

         zoomLinearLayout = (ZoomLinearLayout) findViewById(R.id.zoom_linear_layout);
        layout_back =  findViewById(R.id.layout_back);

      //  callanalytics();
        zoomLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                zoomLinearLayout.init(TermsConditions.this);
                return false;
            }
        });
//"http://docs.google.com/gview?embedded=true&url="
        setwebdata(Cons.TERMSANDCONDITIONS);
   }


    private void setwebdata(String url){
        loadwebdata(url);

    }

    private void loadwebdata(final String url){

        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setAppCacheEnabled(true);
        view.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getPath());
        view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        view.setWebViewClient(new WebViewClient());

        //URL gir "....."
        getWebview(url);
//        webView.setWebViewClient(new MyWebViewClient());


        Log.e("clickver","one"+url);


    }


    public void getWebview(String myurl)
    {





        view.setWebViewClient(new WebViewClient()
        {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {


                prg.show();



                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                prg.dismiss();

                super.onPageFinished(view, url);
//                view.loadUrl(url);
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {



                super.onPageStarted(view, url, favicon);
            }


            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.e("clickver","one"+error.getDescription()+ " "+error.getErrorCode());

            }
        });
        prg = ProgressDialog.show(TermsConditions.this, "Please wait", "Processing...", true);
        prg.setCancelable(true);
        view.loadUrl(myurl);



    }



    @Override
    protected void onResume() {
        super.onResume();


    }



    public void onback(View view) {
        finish();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
/*    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }*/



}
