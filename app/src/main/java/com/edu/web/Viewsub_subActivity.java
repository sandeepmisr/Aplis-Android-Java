package com.edu.web;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.aplis.R;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import com.viewactivitypojo.ViewActivityFirstRetrofitModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Viewsub_subActivity extends AppCompatActivity implements ResponceQueues {
    String data = "";
    WebView view;
    String web_id="";
    Context context;
    HashMap<String,String> hashMap=new HashMap<>();
    private ProgressDialog prg;

    private  int counter=0;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_layout);

        view  =findViewById(R.id.webView);
        context = Viewsub_subActivity.this;
//        data = "<h4><font face=\"Times New Roman\">i am here</font></h4><p><img src=\"http://35.173.187.82/aplis/public/storage/images/aplis-images-2019-12-30%2023:59:35-5e0a420f14dcd.jpeg\"><font face=\"Times New Roman\"><br></font></p><p><u><i style=\"background-color: rgb(255, 0, 128);\"><font color=\"#ffffff\">can you see image</font></i></u></p>";


        final ZoomLinearLayout zoomLinearLayout = (ZoomLinearLayout) findViewById(R.id.zoom_linear_layout);
        zoomLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                zoomLinearLayout.init(Viewsub_subActivity.this);
                return false;
            }
        });


        getDataFromServer();

    }

//    private void getDataFromServer() {
//        web_id = getIntent().getStringExtra("sub_sub_id_s");
//        hashMap.clear();
//        Log.e("TAGEDITOR", Cons.GET_SUBSUBCHAPTERYBDETAILCHAPTERID + web_id);
//        ApiService apiService = new ApiService(context, this, Cons.GET_SUBSUBCHAPTERYBDETAILCHAPTERID + web_id, hashMap, 1);
//        apiService.execute();
//    }

    private void getDataFromServer() {


        web_id = getIntent().getStringExtra("sub_sub_id_s");
        Api api = RetrofitClient.getClient(context, Api.BASE_URL).create(Api.class);

        Call<ViewActivityFirstRetrofitModel> call = api.getGET_SUBCHAPTERYBDETAILCHAPTERID(Cons.GET_SUBSUBCHAPTERYBDETAILCHAPTERID + web_id);
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

                view.getSettings().setJavaScriptEnabled(true);




                super.onPageFinished(view, url);
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {



                super.onPageStarted(view, url, favicon);
            }





        });
        prg = ProgressDialog.show(Viewsub_subActivity.this, "Please wait", "Processing...", true);
        prg.setCancelable(true);
        view.loadUrl(myurl);



    }



    @Override
    protected void onResume() {
        super.onResume();


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
