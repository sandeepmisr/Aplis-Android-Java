
package com.edu.chatsub_sub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.barchartpojo.BarFirstRetrofitModel;
import com.barchartpojo.Screenshot;
import com.bumptech.glide.Glide;
import com.edu.aplis.DemoApplication;
import com.edu.aplis.R;
import com.edu.authors.AuthorDetail;
import com.edu.book.CourseDetail;
import com.edu.editortemplatetwo.EditorSecondSub_subActivity;
import com.edu.editortemplatetwo.InstamodulePager;
import com.edu.fab.FABsMenu;
import com.edu.fab.FABsMenuListener;
import com.edu.fab.TitleFAB;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;
import com.edu.search.Search_Booklists;
import com.edu.search.Search_SubCatlists;
import com.edu.search.TopResults;
import com.edu.sketch.SketchActivity;
import com.edu.web.ViewActivity;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;
import com.piechartpojo.PieFirstRetrofitModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import at.blogc.android.views.ExpandableTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Piechartsub_subActivtiy extends AppCompatActivity implements ResponceQueues {

    PieChart pieChart;
    String heading;
    String sub_heading;
    String pie_description;
    String piechart_id;
    PieDataSet pieDataSet;
    ArrayList pieEntries;
    HashMap<String, String> hashMap = new HashMap<>();
    Context context;
    int total_value=0;
    int color[] = {R.color.colorAccent, R.color.textCol, R.color.colorPrimary, R.color.colorPrimary};
    ArrayList<Piemodel> piemodelArrayList;
    Piemodel piemodel;
    LinearLayout linear_table;
    TextView text_pietitle,text_piesubtitle,text_piedescription;
    TextView text_piesidetitle,text_piesidesubtitle;
    ExpandableTextView text_piesidedescription;
    ImageView piesideimagecancel,piesideimagebar;
    ImageView piesideimage;
    WebView piesideweb;
    LinearLayout piesidescrren;
    LinearLayout leftpielinear;
    ViewPager recyclerViewvpager;


    String pie_url="";
    String bg_image = "";
    LinearLayout layout_bg;
    private FABsMenu fabMenu;

    SimpleExoPlayer simpleExoPlayer= null;
    SimpleCache simpleCache = null;
    private int startWindow;
    private long startPosition;
    SimpleExoPlayerView player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);

        context = Piechartsub_subActivtiy.this;
        pieChart = findViewById(R.id.mchart);
        linear_table = findViewById(R.id.linear_table);

        text_pietitle = findViewById(R.id.text_pietitle);
        text_piesubtitle = findViewById(R.id.text_piesubtitle);
        text_piedescription = findViewById(R.id.text_piedescription);

        text_piesidetitle = findViewById(R.id.text_piesidetitle);
        text_piesidesubtitle = findViewById(R.id.text_piesidesubtitle);
        text_piesidedescription = findViewById(R.id.text_piesidedescription);
        piesideimage = findViewById(R.id.piesideimage);
        piesideweb = findViewById(R.id.piesideweb);
        player = findViewById(R.id.piesidevideoFullScreenPlayer);
        piesideimagecancel = findViewById(R.id.piesideimagecancel);
        piesideimagebar = findViewById(R.id.piesideimagebar);
        piesidescrren = findViewById(R.id.piesidescrren);
        leftpielinear = findViewById(R.id.leftpielinear);
        recyclerViewvpager = findViewById(R.id.vp);
        layout_bg = findViewById(R.id.layout_bg);


        callanalytics();
        leftpielinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeexoplayer();
                piesideimagecancel.setVisibility(View.GONE);
                piesidescrren.setVisibility(View.GONE);
                piesideimagebar.setVisibility(View.GONE);
            }
        });

        text_piesidedescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text_piesidedescription.isExpanded()) {
                    text_piesidedescription.collapse();
//                    buttonToggle.setText(R.string.expand);
                } else {
                    text_piesidedescription.expand();
//                    buttonToggle.setText(R.string.collapse);
                }
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
                clicksketchedit(layout_bg,"white",bg_image);
            }
        });

        TitleFAB noticeFab = findViewById(R.id.noticefab);
        noticeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicksketchedit(layout_bg,"transparent",bg_image);

            }
        });



//        text_title = findViewById(R.id.text_title);
//        text_value = findViewById(R.id.text_value);
//        text_percentage = findViewById(R.id.text_percentage);
        piemodelArrayList = new ArrayList<>();
        pie_url =getIntent().getStringExtra("url");

//        pieChart.setHoleColor(R.color.TextColor);



        getDataFromServer(pie_url);

    }

    public  void clicksketchedit(View view1,String sketchoption,String content){
        fabMenu.setVisibility(View.GONE);
        linear_table.setVisibility(View.GONE);
        String filename = Screenshot.getScreenshot(layout_bg,context);
//        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(Piechartsub_subActivtiy.this,layout_bg,"imagesketch");

        startActivity(new Intent(context, SketchActivity.class).putExtra("color",sketchoption).putExtra("sub_name",filename));

    }


    private void getEntries(float value, int length) {
        pieEntries = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            pieEntries.add(i);
//            pieEntries.set(i,"h");
        }
    }

    private void getDataFromServer(String pie_url) {
        piechart_id = getIntent().getStringExtra("sub_sub_id_s");
        Api api = RetrofitClient.getClient(context, Api.BASE_URL).create(Api.class);

        Call<PieFirstRetrofitModel> call = api.getGETPieChart(pie_url + piechart_id);
//        Log.e("discovertry",call.u);
        call.enqueue(new Callback<PieFirstRetrofitModel>() {
            @Override
            public void onResponse(Call<PieFirstRetrofitModel> call, Response<PieFirstRetrofitModel> response) {

                try {

                    setPiedata(response.body());
//                    shimmerFrameLayout.stopShimmerAnimation();
//                    shimmerFrameLayout.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
//                    BookFirstRetrofitModel discoverRetrofitArrayModel = response.body();
//                    JSONArray jsonArray = new JSONArray(new Gson().toJson(response.body().getBookSecondRetrofitModel()));
                    Log.e("editortry", response.toString() + " "+response.message());
                } catch (Exception e) {
                    Log.e("editorerror", e + "");

                }
//                if (response.body().getStatus().equalsIgnoreCase("1")) {
//                    setBookDetails(response.body());
//                }
            }

            @Override
            public void onFailure(Call<PieFirstRetrofitModel> call, Throwable t) {
                Log.e("errordo", t.getMessage());
            }
        });

//        hashMap.clear();
//        Log.e("TAGEDITOR", pie_url + piechart_id);
//        ApiService apiService = new ApiService(context, this, pie_url + piechart_id, hashMap, 1);
//        apiService.execute();
    }


    private void setPiedata(PieFirstRetrofitModel body){
        piemodelArrayList.clear();

        heading = body.getPieSecondRetrofitModel().getHeading();
        sub_heading = body.getPieSecondRetrofitModel().getSub_heading();
        pie_description = body.getPieSecondRetrofitModel().getHtml_content();

        text_pietitle.setText(heading);
        text_piesubtitle.setText(sub_heading);
        text_piedescription.setText(pie_description);

        for (int i = 0; i < body.getPieSecondRetrofitModel().getPieThirdRetrofitModels().size(); i++) {
            String id = body.getPieSecondRetrofitModel().getPieThirdRetrofitModels().get(i).getId();
            String title = body.getPieSecondRetrofitModel().getPieThirdRetrofitModels().get(i).getHeading();
            String sub_heading = body.getPieSecondRetrofitModel().getPieThirdRetrofitModels().get(i).getSub_heading();
            String heading_url =body.getPieSecondRetrofitModel().getPieThirdRetrofitModels().get(i).getUrl();
            String long_description =body.getPieSecondRetrofitModel().getPieThirdRetrofitModels().get(i).getDescription();
            String numaric_value = body.getPieSecondRetrofitModel().getPieThirdRetrofitModels().get(i).getNumaric_value();
            String color_code = body.getPieSecondRetrofitModel().getPieThirdRetrofitModels().get(i).getColor_code();
            String ar_url = body.getPieSecondRetrofitModel().getPieThirdRetrofitModels().get(i).getAr_url();
            String mime_type = body.getPieSecondRetrofitModel().getPieThirdRetrofitModels().get(i).getMime_type();

            piemodel = new Piemodel();
            piemodel.setId(id);
            piemodel.setHeading(title);
            piemodel.setSub_heading(sub_heading);
            piemodel.setDescription(long_description);
            piemodel.setNumaric_value(numaric_value+"f");
            piemodel.setUrl(heading_url);
            piemodel.setAr_url(ar_url);
            piemodel.setColor_code(color_code);
            piemodel.setMime_type(mime_type);
            piemodelArrayList.add(piemodel);
            total_value = total_value+Integer.parseInt(piemodel.getNumaric_value().replace("f",""));


        }
        bg_image = piemodelArrayList.get(0).getUrl();

        setPieChart(piemodelArrayList);
        inflateResults(piemodelArrayList);


    }
    @Override
    public void responceQue(String responce, String url, String extra_text) {
        piemodelArrayList.clear();
        Log.e("Response===", url + responce);

        try {
            JSONObject jsonObject = new JSONObject(responce);
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("pie_charts");

             heading = jsonObject.getJSONObject("data").getString("heading");
             sub_heading = jsonObject.getJSONObject("data").getString("sub_heading");
             pie_description = jsonObject.getJSONObject("data").getString("html_content");

             text_pietitle.setText(heading);
             text_piesubtitle.setText(sub_heading);
             text_piedescription.setText(pie_description);


            Log.e("Response===", jsonArray.length() + "");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectseries = jsonArray.getJSONObject(i);
                String id = jsonObjectseries.getString("id");
                String title = jsonObjectseries.getString("heading");
                String sub_heading = jsonObjectseries.getString("sub_heading");
                String heading_url = jsonObjectseries.getString("url");
                String long_description = jsonObjectseries.getString("description");
                String numaric_value = jsonObjectseries.getString("numaric_value");
                String color_code = jsonObjectseries.getString("color_code");
                String ar_url = jsonObjectseries.getString("ar_url");

                piemodel = new Piemodel();
                piemodel.setId(id);
                piemodel.setHeading(title);
                piemodel.setSub_heading(sub_heading);
                piemodel.setDescription(long_description);
                piemodel.setNumaric_value(numaric_value+"f");
                piemodel.setUrl(heading_url);
                piemodel.setAr_url(ar_url);
                piemodel.setColor_code(color_code);
                piemodelArrayList.add(piemodel);
                total_value = total_value+Integer.parseInt(piemodel.getNumaric_value().replace("f",""));
            }
            setPieChart(piemodelArrayList);
            inflateResults(piemodelArrayList);
        } catch (JSONException e) {
            Log.e("JSONEXCEPTION", e + "");
            e.printStackTrace();
        }

    }

    private void setPieChart(final ArrayList<Piemodel> piemodelArrayList){
        ArrayList NoOfEmp = new ArrayList();
        ArrayList year = new ArrayList();
        ArrayList<Integer> colors = new ArrayList<Integer>();

        List<PieEntry> entries = new ArrayList<>();
        for (int i =0;i<piemodelArrayList.size();i++) {
            entries.add(new PieEntry(Float.parseFloat(piemodelArrayList.get(i).getNumaric_value()), piemodelArrayList.get(i).getHeading()));
            colors.add(Color.parseColor(piemodelArrayList.get(i).getColor_code()));
//            entries.add(new PieEntry(26.7f, "Yellow"));
//            entries.add(new PieEntry(24.0f, "Red"));
//            entries.add(new PieEntry(30.8f, "Blue"));
//
//
//
//
//            int redColorValue = Color.parseColor("#f25d73");
//            int BLACK = Color.parseColor("#010101");
//            int BLUE = Color.parseColor("#4a90e2");
//            int WHITE = Color.parseColor("#eeeeee");

//            colors.add(redColorValue);
//            colors.add(BLACK);
//            colors.add(BLUE);
//            colors.add(WHITE);
        }
        PieDataSet set = new PieDataSet(entries, "");
        set.setColors(colors);

        pieChart.setDrawHoleEnabled(false);
//        set.setDrawValues(false);
//        pieChart.setData(data);

        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.animateXY(2000, 2000);
        pieChart.invalidate();


        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                showtoast(piemodelArrayList.get(Math.round(h.getX())).getHeading(),
                        piemodelArrayList.get(Math.round(h.getX())).getNumaric_value().replace("f",""),
                        showpercentagevalue(piemodelArrayList.get(Math.round(h.getX())).getNumaric_value().replace("f",""),total_value+""));
                Log.e("TAG", "onValueSelected: value selected from chart");
                Log.e("TAG", "onValueSelected: "+ h.getDataSetIndex()+ " "+h.getX());
                Log.e("TAG", "onValueSelected: "+ piemodelArrayList.get(Math.round(h.getX())).getNumaric_value()+
                        " "+showpercentagevalue(piemodelArrayList.get(Math.round(h.getX())).getNumaric_value().replace("f",""),total_value+""));
            }

            @Override
            public void onNothingSelected() {

            }
        });




//        ArrayList NoOfEmp = new ArrayList();
//        ArrayList year = new ArrayList();
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//
//        for (int i =0;i<piemodelArrayList.size();i++) {
//
//            NoOfEmp.add(new Entry(Float.parseFloat(piemodelArrayList.get(i).getNumaric_value()), i));
//            NoOfEmp.add(new Entry(1040f, 1));
//            NoOfEmp.add(new Entry(1133f, 2));
//            NoOfEmp.add(new Entry(1240f, 3));
//
//
//            year.add(piemodelArrayList.get(i).getHeading());
//            colors.add(Color.parseColor(piemodelArrayList.get(i).getColor_code()));
//
//            year.add("2009");
//            year.add("2010");
//            year.add("2011");
//


//        }
//            PieDataSet dataSet = new PieDataSet(NoOfEmp, heading);
//
//            PieData data = new PieData(year, dataSet);
//        pieChart.setData(data);
//        pieChart.setDrawHoleEnabled(false);
//

//        int redColorValue = Color.parseColor("#f25d73");
//        int BLACK = Color.parseColor("#010101");
//        int BLUE = Color.parseColor("#4a90e2");
//        int WHITE = Color.parseColor("#eeeeee");
//
//        colors.add(redColorValue);
//        colors.add(BLACK);
//        colors.add(BLUE);
//        colors.add(WHITE);

//        dataSet.setColors(colors);
//        pieChart.animateXY(3000, 3000);







//        NoOfEmp.add(new Entry(945f, 0));
//        NoOfEmp.add(new Entry(1040f, 1));
//        NoOfEmp.add(new Entry(1133f, 2));
//        NoOfEmp.add(new Entry(1240f, 3));
//
//
//        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Number Of Employees");
//
//        ArrayList year = new ArrayList();
//
//        year.add("2008");
//        year.add("2009");
//        year.add("2010");
//        year.add("2011");
////
////
//        PieData data = new PieData(year, dataSet);
//        pieChart.setData(data);
//        pieChart.setDrawHoleEnabled(false);
//
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//        int redColorValue = Color.parseColor("#f25d73");
//        int BLACK = Color.parseColor("#010101");
//        int BLUE = Color.parseColor("#4a90e2");
//        int WHITE = Color.parseColor("#eeeeee");
//
//        colors.add(redColorValue);
//        colors.add(BLACK);
//        colors.add(BLUE);
//        colors.add(WHITE);
//
//
//        dataSet.setColors(colors);
//        pieChart.animateXY(3000, 3000);
    }

    private String showpercentagevalue(String inputvalue,String totalvalue){
        String percent_value="";
        float number = (Integer.parseInt(inputvalue)*100)/Integer.parseInt(totalvalue);
        percent_value = number+"";
        return percent_value;
    }

   private void showtoast(String title,String value,String percentage){
       LayoutInflater inflater = getLayoutInflater();
       View layout = inflater.inflate(R.layout.piechartdvalue, (ViewGroup) findViewById(R.id.custom_toast_layout));
       TextView text_title = (TextView) layout.findViewById(R.id.text_title);
       TextView text_value = (TextView) layout.findViewById(R.id.text_value);
       TextView text_percentage = (TextView) layout.findViewById(R.id.text_percentage);
       text_title.setText(title);
       text_value.setText("Value: "+value);
       text_percentage.setText("Percentage Value : "+percentage+"%");
       Toast toast = new Toast(getApplicationContext());
       toast.setDuration(2);
       toast.setView(layout);
       toast.show();

    }

    public void inflateResults(final ArrayList<Piemodel> piemodelArrayList) {
        linear_table.removeAllViews();
        for (int i = 0; i < piemodelArrayList.size(); i++) {
            try {
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.piecolorcode, null);
                TextView text_description = (TextView) view.findViewById(R.id.text_description);
                final ImageView image_pie = (ImageView) view.findViewById(R.id.image_pie);

                text_description.setText(piemodelArrayList.get(i).getHeading());
                try {
                    image_pie.setBackgroundColor(Color.parseColor(piemodelArrayList.get(i).getColor_code()));
                }catch (Exception e){

                }


//                    Glide.with(context)
//                            .load(piemodelArrayList.get(i).getUrl())
//                            .centerCrop()
//                            .into(image_pie);


                final int finalI = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        closeexoplayer();

                        Log.e("VIEWS","1 "+piemodelArrayList.get(finalI).getMime_type());

//                        Animation animation = AnimationUtils.loadAnimation(context, R.anim.rightleft);
//                        animation.setDuration(1000); // duartion in ms
//                        animation.setFillAfter(false);
//                        piesidescrren.startAnimation(animation);
                        piesideimagecancel.setVisibility(View.VISIBLE);
                        piesidescrren.setVisibility(View.VISIBLE);
                        piesideimagebar.setVisibility(View.VISIBLE);
                        try {
                            piesideimagebar.setBackgroundColor(Color.parseColor(piemodelArrayList.get(finalI).getColor_code()));
                        }catch (Exception e){

                        }
                        text_piesidetitle.setText(piemodelArrayList.get(finalI).getHeading());
                        text_piesidesubtitle.setText(piemodelArrayList.get(finalI).getSub_heading());
                        text_piesidedescription.setText(piemodelArrayList.get(finalI).getDescription());

                        switch (piemodelArrayList.get(finalI).getMime_type()){
                            case "image":
                                piesideimage.setVisibility(View.VISIBLE);
                                piesideweb.setVisibility(View.GONE);
                                player.setVisibility(View.GONE);
                                Glide.with(context)
                                        .load(piemodelArrayList.get(finalI).getUrl())
                                        .centerCrop()
                                        .into(piesideimage);
                                break;
                            case "gif":
                                piesideimage.setVisibility(View.VISIBLE);
                                piesideweb.setVisibility(View.GONE);
                                player.setVisibility(View.GONE);

                                Glide.with(context)
                                        .load(piemodelArrayList.get(finalI).getUrl())
                                        .asGif()
                                        .centerCrop()
                                        .into(piesideimage);
                                break;
                                case "web":
                                    piesideweb.setVisibility(View.VISIBLE);
                                    piesideimage.setVisibility(View.GONE);
                                    player.setVisibility(View.GONE);
                                    loadwebdata(piesideweb,piemodelArrayList.get(finalI).getUrl());
                                break;
                                case "video":
                                    player.setVisibility(View.VISIBLE);
                                    piesideimage.setVisibility(View.GONE);
                                    piesideweb.setVisibility(View.GONE);
                                    initializePlayer(piemodelArrayList.get(finalI).getUrl());
                                break;
                        }



                    }
                });
                linear_table.addView(view);

            }
            catch (Exception e) {
                Log.e("VIEWS","1 "+piemodelArrayList.size()+" catch"+e.toString());
            }


        }
    }

    private void loadwebdata(WebView view,final String url){

        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setAppCacheEnabled(true);
        view.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getPath());
        view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        view.setWebViewClient(new WebViewClient());

        //URL gir "....."
        getWebview(view,url);
//        webView.setWebViewClient(new MyWebViewClient());


        Log.e("clickver","one"+url);


    }

    public void getWebview(WebView view,String myurl)
    {





        view.setWebViewClient(new WebViewClient()
        {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {


//                prg.show();



                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

//                prg.dismiss();

                super.onPageFinished(view, url);
//                view.loadUrl(url);
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {



                super.onPageStarted(view, url, favicon);
            }





        });
//        prg = ProgressDialog.show(ViewActivity.this, "Please wait", "Processing...", true);
//        prg.setCancelable(true);
        view.loadUrl(myurl);



    }



    public void onback(View view) {
        finish();
    }

    public void ontopic(View view) {
        Intent intent=new Intent();
        setResult(RESULT_OK, intent);
        finish();


    }
    public void initializePlayer(String Card_url){
        player.setVisibility(View.VISIBLE);

        // Create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        //Initialize the player
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        //Initialize simpleExoPlayerView
        player.setPlayer(simpleExoPlayer);

        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(context, Util.getUserAgent(context, "Aplinew"));

        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        // This is the MediaSource representing the media to be played.
        Uri videoUri = Uri.parse(Card_url.replaceAll(" ", "%20"));
        MediaSource videoSource = new ExtractorMediaSource(videoUri,
                dataSourceFactory, extractorsFactory, null, null);
        Log.e("urlvidei",Card_url);
        // Prepare the player with the source.
        simpleExoPlayer.prepare(videoSource);
        simpleExoPlayer.setPlayWhenReady(true);
        player.buildDrawingCache();

        boolean haveStartPosition = startWindow != C.INDEX_UNSET;
        if (haveStartPosition) {
            simpleExoPlayer.seekTo(startWindow, startPosition);
        }
        MediaSource audioSource = new ExtractorMediaSource(Uri.parse(Card_url.replaceAll(" ", "%20")),
                new CacheDataSourceFactory(context, 100 * 1024 * 1024, 5 * 1024 * 1024), new DefaultExtractorsFactory(), null, null);



//        getDownloadCache().release();

//        audioSource.
        simpleExoPlayer.prepare(audioSource, !haveStartPosition, false);


    }

    public void closeexoplayer(){
        Log.e("exoplayer","check"+simpleExoPlayer+"");
        if (simpleExoPlayer != null) {
            player.invalidate();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }



    class CacheDataSourceFactory implements DataSource.Factory {
        private final Context context;
        private final DefaultDataSourceFactory defaultDatasourceFactory;
        private final long maxFileSize, maxCacheSize;

        CacheDataSourceFactory(Context context, long maxCacheSize, long maxFileSize) {
            super();
            this.context = context;
            this.maxCacheSize = maxCacheSize;
            this.maxFileSize = maxFileSize;
            String userAgent = Util.getUserAgent(context, context.getString(R.string.app_name));
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            defaultDatasourceFactory = new DefaultDataSourceFactory(this.context,
                    bandwidthMeter,
                    new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter));
        }

        @Override
        public DataSource createDataSource() {
            LeastRecentlyUsedCacheEvictor evictor = new LeastRecentlyUsedCacheEvictor(maxCacheSize);
            File file = new File(context.getCacheDir(), "media"+System.currentTimeMillis());



            simpleCache = new SimpleCache(file, evictor);
//

            return new CacheDataSource(simpleCache, defaultDatasourceFactory.createDataSource(),
                    new FileDataSource(), new CacheDataSink(simpleCache, maxFileSize),
                    CacheDataSource.FLAG_BLOCK_ON_CACHE | CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR, null);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        closeexoplayer();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fabMenu.setVisibility(View.VISIBLE);
        linear_table.setVisibility(View.VISIBLE);
    }

    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }
}
