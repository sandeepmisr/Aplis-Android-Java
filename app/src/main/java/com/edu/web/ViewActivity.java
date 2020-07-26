package com.edu.web;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.barchartpojo.Screenshot;
import com.editorsecondsubactivitypojo.SecondRetrofitModel;
import com.edu.aplis.R;
import com.edu.editortemplatetwo.EditorSecondSub_subActivity;
import com.edu.fab.FABsMenu;
import com.edu.fab.FABsMenuListener;
import com.edu.fab.TitleFAB;
import com.edu.piechart.Piemodel;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;
import com.edu.sketch.SketchActivity;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import com.viewactivitypojo.ViewActivityFirstRetrofitModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewActivity extends AppCompatActivity implements ResponceQueues {
    String data = "";
    WebView view;
    String web_id="";
    String web_url="";
    Context context;
    HashMap<String,String> hashMap=new HashMap<>();
    private ProgressDialog prg;
    private FABsMenu fabMenu;
    String bg_image = "";
//    ImageView layout_bg;
    ZoomLinearLayout zoomLinearLayout;
    RelativeLayout layout_back;

    private  int counter=0;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_layout);

        view  =findViewById(R.id.webView);
        context =ViewActivity.this;
//        layout_bg = findViewById(R.id.layout_bg);

//        data = "<h4><font face=\"Times New Roman\">i am here</font></h4><p><img src=\"http://35.173.187.82/aplis/public/storage/images/aplis-images-2019-12-30%2023:59:35-5e0a420f14dcd.jpeg\"><font face=\"Times New Roman\"><br></font></p><p><u><i style=\"background-color: rgb(255, 0, 128);\"><font color=\"#ffffff\">can you see image</font></i></u></p>";

         zoomLinearLayout = (ZoomLinearLayout) findViewById(R.id.zoom_linear_layout);
        layout_back =  findViewById(R.id.layout_back);
        zoomLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                zoomLinearLayout.init(ViewActivity.this);
                return false;
            }
        });

        fabMenu = findViewById(R.id.fabs_menu);
//        fabMenu.setVisibility(View.GONE);
        fabMenu.setMenuListener(new FABsMenuListener() {


            // You don't need to override all methods. Just the ones you want.

            @Override
            public void onMenuClicked(FABsMenu fabsMenu) {
                super.onMenuClicked(fabsMenu); // Default implementation opens the menu on click
                // showToast("You pressed the menu!");

            }

            @Override
            public void onMenuCollapsed(FABsMenu fabsMenu) {
                super.onMenuCollapsed(fabsMenu);
                // showToast("The menu has been collapsed!");

                Log.e("clicked","cicl====");

            }

            @Override
            public void onMenuExpanded(FABsMenu fabsMenu) {
                super.onMenuExpanded(fabsMenu);
                // showToast("The menu has been expanded!");
            }
        });

        TitleFAB diaryFab = findViewById(R.id.diaryfab);
        diaryFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicksketchedit(zoomLinearLayout,"white",bg_image);
            }
        });

        TitleFAB noticeFab = findViewById(R.id.noticefab);
        noticeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicksketchedit(zoomLinearLayout,"transparent",bg_image);

            }
        });

        web_url = getIntent().getStringExtra("ar_url");

//        setwebdata(web_url);


//        loadwebdata("https://www.facebook.com");


//        loadwebdata("https://test.oppwa.com/v1/paymentWidgets.js?checkoutId=2D75D026A8593241236D953FF561E6BD.uat01-vm-tx01");
        getDataFromServer();

    }

    public  void clicksketchedit(View view1,String sketchoption,String content){
        fabMenu.setVisibility(View.GONE);
        layout_back.setVisibility(View.GONE);
        String filename = Screenshot.getScreenshot(zoomLinearLayout,context);
//        Screenshot.getScreenshot(layout_bg,context);
        startActivity(new Intent(context, SketchActivity.class).putExtra("color",sketchoption).putExtra("sub_name",filename));

//        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(ViewActivity.this,layout_bg,"imagesketch");

//        startActivity(new Intent(context, SketchActivity.class).putExtra("color",sketchoption).putExtra("sub_name",content));

    }


    private void getDataFromServer() {


        web_id = getIntent().getStringExtra("sub_sub_id_s");
        Api api = RetrofitClient.getClient(context, Api.BASE_URL).create(Api.class);

        Call<ViewActivityFirstRetrofitModel> call = api.getGET_SUBCHAPTERYBDETAILCHAPTERID(Cons.GET_SUBCHAPTERYBDETAILCHAPTERID + web_id);
//        Log.e("discovertry",call.u);
        call.enqueue(new Callback<ViewActivityFirstRetrofitModel>() {
            @Override
            public void onResponse(Call<ViewActivityFirstRetrofitModel> call, Response<ViewActivityFirstRetrofitModel> response) {

                try {

                    setwebdata(response.body().getViewActivitySecondModel().getViewActivityThirdModel().getUrl());
//                    shimmerFrameLayout.stopShimmerAnimation();
//                    shimmerFrameLayout.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
//                    BookFirstRetrofitModel discoverRetrofitArrayModel = response.body();
//                    JSONArray jsonArray = new JSONArray(new Gson().toJson(response.body().getBookSecondRetrofitModel()));
                    Log.e(getClass().getSimpleName(), response.toString() + " "+response.message());
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), e + "");

                }
//                if (response.body().getStatus().equalsIgnoreCase("1")) {
//                    setBookDetails(response.body());
//                }
            }

            @Override
            public void onFailure(Call<ViewActivityFirstRetrofitModel> call, Throwable t) {
                Log.e("errordo", t.getMessage());
            }
        });



//        hashMap.clear();
//        Log.e("TAGEDITOR", Cons.GET_SUBCHAPTERYBDETAILCHAPTERID + web_id);
//        ApiService apiService = new ApiService(context, this, Cons.GET_SUBCHAPTERYBDETAILCHAPTERID + web_id, hashMap, 1);
//        apiService.execute();
    }



    private void setwebdata(String url){
        loadwebdata(url);

    }
    @Override
    public void responceQue(String responce, String url, String extra_text) {
        Log.e("Response===", url + responce);

        try {
            JSONObject jsonObject = new JSONObject(responce);

            JSONObject jsonObject1 =jsonObject.getJSONObject("data").getJSONObject("website");
            loadwebdata(jsonObject1.getString("url"));

        } catch (JSONException e) {
            Log.e("JSONEXCEPTION", e + "");
            e.printStackTrace();
        }

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





        });
        prg = ProgressDialog.show(ViewActivity.this, "Please wait", "Processing...", true);
        prg.setCancelable(true);
        view.loadUrl(myurl);



    }



    @Override
    protected void onResume() {
        super.onResume();


    }

    public void ontopic(View view) {
        Intent intent=new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onback(View view) {
        finish();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        fabMenu.setVisibility(View.VISIBLE);
        layout_back.setVisibility(View.VISIBLE);
    }


//    class MyWebViewClient extends WebViewClient {
//        @Override
//        // show the web page in webview but not in web browser
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            //1 option
//            // getApplicationContext().startActivity(
//            // new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//            // 2 option
//            view.loadUrl(url);
//            return true;
//        }
//    }


}
