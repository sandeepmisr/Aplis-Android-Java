package com.edu.editortemplatetwo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.viewpager.widget.ViewPager;

import com.badoualy.stepperindicator.StepperIndicator;
import com.barchartpojo.Screenshot;
import com.editorsecondsub_subactivitypojo.Editorsub_subFirstDetailsRetrofitModel;
import com.editorsecondsub_subactivitypojo.Editorsub_subThirdDetailsRetrofitModel;
import com.editorsecondsub_subactivitypojo.Editorsub_subThirdRetrofitModel;
import com.editorsub_sub_subpojo.EditorSub_Sub_SubFirstRetrofitModel;
import com.editorsub_sub_subpojo.EditorSub_Sub_SubSecondRetrofitModel;
import com.editorsub_sub_subpojo.Editorsub_sub_subFirstDetailsRetrofitModel;
import com.editorsub_sub_subpojo.Editorsub_sub_subSecondDetailsRetrofitModel;
import com.edu.aplis.DemoApplication;
import com.edu.aplis.R;
import com.edu.discover.Books;
import com.edu.discover.ClickAdapter;
import com.edu.discover.DiscoverModel;
import com.edu.editortemplate.EditorSubActivity;
import com.edu.editortemplate.EditoroneAdapter;
import com.edu.fab.FABsMenu;
import com.edu.fab.FABsMenuListener;
import com.edu.fab.TitleFAB;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;
import com.edu.sketch.SketchActivity;
import com.edu.transformation.FadeOutTransformation;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
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
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import at.blogc.android.views.ExpandableTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorSecondSub_sub_subActivity extends AppCompatActivity implements ResponceQueues,ClickAdapter,PlayerListener {
    TextView subtext_title,subtext_subtitle;
    ViewPager recyclerViewvpager;
    ExpandableTextView subtext_description;
    LinearLayout ll_scrollseriesoftitle;
    HashMap<String,String> hashMap=new HashMap<>();
    Context context;
    private  ArrayList<EditorSubModel> editorModelArrayList;
    EditorSubModel editorSubModel;
    String sub_id = "";
    String sub_name= "";
    String sub_subname= "";
    String sub_description= "";
    String ar_url= "";
    String bg_image = "";
    RecyclerView rec_view;
    StepperIndicator pageIndicatorView;
    EditorSecondSub_sub_subActivityAdapter editorSecondSub_subActivityAdapter;
    LinearLayout layout_bg;

    SimpleExoPlayer simpleExoPlayer= null;
    SimpleCache simpleCache = null;
    ImageView image_Ar;
    private int currentPos = 0;
    private int prevPos = -1;

    LinearLayout viewpagertopiclayer;
    LinearLayout linear_layer;
    CardView cardimage_Ar;
    CardView cardimage_visualaize;
    ImageView image_visualaize;

    private int startWindow;
    private long startPosition;

    private  ArrayList<DiscoverModel> discoverModelArrayList;
    PlayerListener playerListener;
    private FABsMenu fabMenu;

    EditoroneAdapter editoroneAdapter;
    String book_Id= "";

    VideoView videoView;
    private PlayerView videoSurfaceView;
    private SimpleExoPlayer videoPlayer;
    private FrameLayout frameLayout;
    private int playPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondsub_subeditor);
        context = EditorSecondSub_sub_subActivity.this;


        viewpagertopiclayer = findViewById(R.id.viewpagertopiclayer);
//        linear_layer = findViewById(R.id.linear_layer);
        cardimage_Ar = findViewById(R.id.cardimage_Ar);
        cardimage_visualaize = findViewById(R.id.cardimage_visualaize);
        image_visualaize = findViewById(R.id.image_visualaize);

        subtext_title = findViewById(R.id.text_title);
        subtext_subtitle = findViewById(R.id.text_subtitle);
        subtext_description = findViewById(R.id.text_subdetail);
        rec_view = findViewById(R.id.rec_view);
        layout_bg = findViewById(R.id.layout_bg);
        image_Ar = findViewById(R.id.image_Ar);

        recyclerViewvpager = findViewById(R.id.vp);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//        recyclerView.setLayoutManager(linearLayoutManager);
        discoverModelArrayList = new ArrayList<DiscoverModel>();

        playerListener =this;

        callanalytics();
        editorModelArrayList= new ArrayList<>();
        sub_id = getIntent().getStringExtra("sub_sub_id_s");
        sub_name = getIntent().getStringExtra("subtitle_name");
        sub_subname = getIntent().getStringExtra("subtitle_subname");
        sub_description = getIntent().getStringExtra("subtitle_description");
        ar_url = getIntent().getStringExtra("ar_url");

//        bg_image = getIntent().getStringExtra("title_bgimage");

        subtext_title.setText(sub_name);
        subtext_subtitle.setText(sub_subname);
        try {
            if (ar_url.equalsIgnoreCase("") || ar_url.equalsIgnoreCase("null")) {
                cardimage_Ar.setVisibility(View.GONE);
            }

        }
        catch (Exception e){
            cardimage_Ar.setVisibility(View.GONE);

        }

        fabMenu = findViewById(R.id.fabs_menu);
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
                //getToast("Notice");
                // ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(EditorSecondSubActivity.this,layout_bg,"imagesketch");

                // startActivity(new Intent(context, SketchActivity.class).putExtra("color","transparent2").putExtra("sub_name",bg_image),activityOptionsCompat.toBundle());
//                Log.e("clicked","cicl===="+bg_image+"https://images.unsplash.com/photo-1546146477-15a587cd3fcb?ixlib=rb-1.2.1&w=1000&q=80");
            }
        });

        cardimage_visualaize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewpagertopiclayer.getVisibility()==View.GONE){

                    Transition transition = new Fade();
                    transition.setDuration(600);
                    transition.addTarget(R.id.viewpagertopiclayer);

                    TransitionManager.beginDelayedTransition(viewpagertopiclayer, transition);

                    image_visualaize.setImageResource(R.drawable.visuliaze);
                    viewpagertopiclayer.setVisibility(View.VISIBLE);
//                    linear_layer.setVisibility(View.VISIBLE);

                }
                else{
                    Transition transition = new Fade();
                    transition.setDuration(600);
                    transition.addTarget(R.id.viewpagertopiclayer);

                    TransitionManager.beginDelayedTransition(viewpagertopiclayer, transition);
                    image_visualaize.setImageResource(R.drawable.un_eye);
                    viewpagertopiclayer.setVisibility(View.GONE);
//                    linear_layer.setVisibility(View.GONE);

                }
            }
        });

        cardimage_Ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAR(ar_url);
            }
        });



        rec_view.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));


        recyclerViewvpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                Log.e("TAGADAPTERinsta_POS","scrolled"+ position+" ");
            }

            @Override
            public void onPageSelected(int position) {
                prevPos = currentPos;
                currentPos = position;
                Fragment fragment = getSupportFragmentManager().
                        findFragmentByTag("android:switcher:" + R.id.vp + ":" + prevPos);
                InstamodulePager mprevFragment = (InstamodulePager) fragment;
                try{
                    mprevFragment.closeexoplayer();
//        mFragment.initializePlayer();


                }
                catch (Exception e){

                }


//                recyclerViewvpager.getAdapter().notifyDataSetChanged();



                try {

                    Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vp + ":" + currentPos);
                    InstamodulePager mnextFragment = (InstamodulePager) page;
                    bg_image = discoverModelArrayList.get(currentPos).getCard_url();
                    if (discoverModelArrayList.get(currentPos).getMime_type().equalsIgnoreCase("video")) {
                        mnextFragment.closeexoplayer();
                        mnextFragment.initializePlayer();


                    }

                    else if(discoverModelArrayList.get(currentPos).getMime_type().equalsIgnoreCase("web")) {
                        mnextFragment.callweb();

                    }

                    else if(discoverModelArrayList.get(currentPos).getMime_type().equalsIgnoreCase("gif")) {
                        mnextFragment.callgif();

                    } else if(discoverModelArrayList.get(currentPos).getMime_type().equalsIgnoreCase("image")) {
                        mnextFragment.callimage();

                    }
//mFragment.initializePlayer(player,simpleExoPlayer);
//                        nextChild = recyclerViewvpager.getChildAt(position);
//                        nextChild.setVisibility(View.GONE);
//                        nextChild.setVisibility(View.VISIBLE);


//                        if (simpleExoPlayer != null) {
//
//                            simpleExoPlayer.release();
//                        simpleExoPlayer = null;
//                        }
//                         player = nextChild.findViewById(R.id.videoFullScreenPlayer);
//                        player.setVisibility(View.GONE);
//                        player.setVisibility(View.VISIBLE);


                }
                catch (Exception e){

                }

                Log.e("TAGADAPTERinsta_POS","selected"+ position+" "+discoverModelArrayList.get(position).getMime_type());



            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("TAGADAPTERinsta_POS","state"+" "+state+"");

            }
        });





        subtext_description.setText(sub_description);

        subtext_description.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                if (subtext_description.isExpanded())
                {
                    subtext_description.collapse();
//                    buttonToggle.setText(R.string.expand);
                }
                else
                {
                    subtext_description.expand();
//                    buttonToggle.setText(R.string.collapse);
                }
            }
        });

        image_Ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAR(ar_url);
            }
        });

        Log.e(getClass().getSimpleName(),getIntent().getStringExtra("sub_sub_id_s")+ " " + getIntent().getStringExtra("subtitle_name"));
//        makeHTTPCall(Cons.GET_SUBCHAPTERYBDETAILCHAPTERID+ sub_id);


//        getDataFromServer();
        makeHTTPCall(Cons.GET_SUBSUBCHAPTERYBDETAILCHAPTERID+ sub_id);

    }

    public  void clicksketchedit(View view1,String sketchoption,String content){
        fabMenu.setVisibility(View.GONE);
        rec_view.setVisibility(View.GONE);
//        String filename = Screenshot.getScreenshot(layout_bg,context);
//        Screenshot.getScreenshot(layout_bg,context);
//        startActivity(new Intent(context, SketchActivity.class).putExtra("color",sketchoption).putExtra("sub_name",filename));

        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(EditorSecondSub_sub_subActivity.this,layout_bg,"imagesketch");
//
        startActivity(new Intent(context, SketchActivity.class).putExtra("color",sketchoption).putExtra("sub_name",content));

    }


    private void getDataFromServer() {
        hashMap.clear();
        makeHTTPCall(Cons.GET_SUB_SUBCHAPTERBYSUBID+ sub_id);
        Log.e(getClass().getSimpleName(),Cons.GET_SUB_SUBCHAPTERBYSUBID+ sub_id);

    }

    private void makeHTTPCall(final String url) {

        Api api = RetrofitClient.getClient(context, Api.BASE_URL).create(Api.class);

        Call<Editorsub_sub_subFirstDetailsRetrofitModel> call = api.getEditorSecondSub_sub_subActivity(url);
//        Log.e("discovertry",call.u);
        call.enqueue(new Callback<Editorsub_sub_subFirstDetailsRetrofitModel>() {
            @Override
            public void onResponse(Call<Editorsub_sub_subFirstDetailsRetrofitModel> call, Response<Editorsub_sub_subFirstDetailsRetrofitModel> response) {

                try {
                    discoverdata(response.body().getEditorsub_sub_subSecondDetailsRetrofitModel());
//                    setwebdata(response.body().getViewActivitySecondModel().getViewActivityThirdModel().getUrl());
//                    shimmerFrameLayout.stopShimmerAnimation();
//                    shimmerFrameLayout.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
//                    BookFirstRetrofitModel discoverRetrofitArrayModel = response.body();
//                    JSONArray jsonArray = new JSONArray(new Gson().toJson(response.body().getBookSecondRetrofitModel()));
                    Log.e(getClass().getSimpleName()+"done", url+ " "+response.message());
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), e + "");

                }
//                if (response.body().getStatus().equalsIgnoreCase("1")) {
//                    setBookDetails(response.body());
//                }
            }

            @Override
            public void onFailure(Call<Editorsub_sub_subFirstDetailsRetrofitModel> call, Throwable t) {
                Log.e("errordosusub",url+ " "+ t.getMessage());
            }
        });


//        ApiService apiService = new ApiService(context,this, url,hashMap,1);
//        apiService.execute();
    }


    private void discoverdata(Editorsub_sub_subSecondDetailsRetrofitModel editorsub_sub_subSecondDetailsRetrofitModel){
        Log.e("done",editorsub_sub_subSecondDetailsRetrofitModel.getEditorsub_sub_subThirdDetailsRetrofitModels().size()+"");

        for (int i=0;i<editorsub_sub_subSecondDetailsRetrofitModel.getEditorsub_sub_subThirdDetailsRetrofitModels().size();i++){
            DiscoverModel discoverModel = new DiscoverModel();
            String id = editorsub_sub_subSecondDetailsRetrofitModel.getEditorsub_sub_subThirdDetailsRetrofitModels().get(i).getId();
            String mime_type =editorsub_sub_subSecondDetailsRetrofitModel.getEditorsub_sub_subThirdDetailsRetrofitModels().get(i).getMime_type();
            String card_url = editorsub_sub_subSecondDetailsRetrofitModel.getEditorsub_sub_subThirdDetailsRetrofitModels().get(i).getUrl();
            //                    String Ar_url = jsonObjectseries.getString("ar_url");

            discoverModel.setId(id);
            discoverModel.setTitle("");
            discoverModel.setStatus("");
            discoverModel.setLong_description("");
            discoverModel.setMime_type(mime_type);
            discoverModel.setCard_url(card_url);
            discoverModel.setAr_url("");
            discoverModelArrayList.add(discoverModel);



        }
        if (discoverModelArrayList.size()>0) {
            bg_image = discoverModelArrayList.get(0).getCard_url();

            Log.e("done",discoverModelArrayList.size()+"");
            setPagerAdapter();
        }




    }


    private void setrecyclerdataadapter(List<EditorSub_Sub_SubSecondRetrofitModel> editorSub_sub_subSecondRetrofitModels) {
        editorModelArrayList.clear();

        for (int i = 0; i < editorSub_sub_subSecondRetrofitModels.size(); i++) {
            String mime_type = "";
            String is_timeline = editorSub_sub_subSecondRetrofitModels.get(i).getIs_timeline();
            String is_graphp = editorSub_sub_subSecondRetrofitModels.get(i).getIs_graphp();
            String is_pichart =editorSub_sub_subSecondRetrofitModels.get(i).getIs_pichart();
            String is_column = editorSub_sub_subSecondRetrofitModels.get(i).getIs_column();
            String is_website =editorSub_sub_subSecondRetrofitModels.get(i).getIs_website();

            String color_code =editorSub_sub_subSecondRetrofitModels.get(i).getColor_code();
            String btn_image =editorSub_sub_subSecondRetrofitModels.get(i).getBtn_image();
            String show_button_text =editorSub_sub_subSecondRetrofitModels.get(i). getShow_button_text();
            if (is_timeline.equalsIgnoreCase("1")){

                mime_type = "timeline";
            }
            else if (is_graphp.equalsIgnoreCase("1")){
                mime_type = "graph";

            }

            else if (is_pichart.equalsIgnoreCase("1")){
                mime_type = "pie_chart";


            }else if (is_column.equalsIgnoreCase("1")){
                mime_type = "column";


            }else if (is_website.equalsIgnoreCase("1")){
                mime_type = "web";

            }
            else{
                mime_type = "text";
            }
            editorSubModel = new EditorSubModel();

            editorSubModel.setMime_type(mime_type);
            editorSubModel.setId(editorSub_sub_subSecondRetrofitModels.get(i).getId());
            editorSubModel.setTitle_id(editorSub_sub_subSecondRetrofitModels.get(i).getTitle_id());
            editorSubModel.setTitle(editorSub_sub_subSecondRetrofitModels.get(i).getTitle());
            editorSubModel.setSub_title(editorSub_sub_subSecondRetrofitModels.get(i).getSub_heading());
            editorSubModel.setDescription(editorSub_sub_subSecondRetrofitModels.get(i).getLong_description());

            if (color_code.contains("#")) {
                editorSubModel.setBtn_image(color_code);
            }
            else{
                editorSubModel.setBtn_image(btn_image);

            }
            editorSubModel.setBtn_bg_image(btn_image);
            editorSubModel.setColor_code(color_code);
            editorSubModel.setShow_button_text(show_button_text);
            editorSubModel.setAr_url(editorSub_sub_subSecondRetrofitModels.get(i).getAr_url());
            editorModelArrayList.add(editorSubModel);

        }
        editorSecondSub_subActivityAdapter = new EditorSecondSub_sub_subActivityAdapter(context, editorModelArrayList,this);
        rec_view.setAdapter(editorSecondSub_subActivityAdapter);
    }

    @Override
    public void responceQue(String responce, String url, String extra_text) {
        Log.e("Response===",url+" response"+responce);

        try {

            if (url.contains(Cons.GET_SUB_SUBCHAPTERBYSUBID)) {
                editorModelArrayList.clear();
                JSONObject jsonObject = new JSONObject(responce);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                Log.e("Response===", jsonArray.length() + "");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectsubeditordetail = jsonArray.getJSONObject(i);
                    String id = jsonObjectsubeditordetail.getString("id");
                    String title_id = jsonObjectsubeditordetail.getString("sub_topic_id");
                    String title = jsonObjectsubeditordetail.getString("heading");
                    String sub_heading = jsonObjectsubeditordetail.getString("sub_heading");
                    String ar_url = jsonObjectsubeditordetail.getString("ar_url");
                    String long_description = jsonObjectsubeditordetail.getString("html_content");
                    String bg_image = jsonObjectsubeditordetail.getString("bg_image");
                    String btn_image = jsonObjectsubeditordetail.getString("btn_image");
                    String color_code = jsonObjectsubeditordetail.getString("color_code");
                    String show_button_text = jsonObjectsubeditordetail.getString("show_button_text");

                    String is_timeline = jsonObjectsubeditordetail.getString("is_timeline");
                    String is_graphp = jsonObjectsubeditordetail.getString("is_graphp");
                    String is_pichart = jsonObjectsubeditordetail.getString("is_pichart");
                    String is_column = jsonObjectsubeditordetail.getString("is_column");
                    String is_website = jsonObjectsubeditordetail.getString("is_website");


                    editorSubModel = new EditorSubModel();
                    editorSubModel.setId(id);
                    editorSubModel.setTitle_id(title_id);
                    editorSubModel.setTitle(title);
                    editorSubModel.setSub_title(sub_heading);
                    editorSubModel.setDescription(long_description);

                    if (color_code.contains("#")) {
                        editorSubModel.setBtn_image(color_code);
                    } else {
                        editorSubModel.setBtn_image(btn_image);
                    }
                    editorSubModel.setBtn_bg_image(btn_image);
                    editorSubModel.setColor_code(color_code);
                    editorSubModel.setShow_button_text(show_button_text);
                    editorSubModel.setAr_url(ar_url);
                    editorModelArrayList.add(editorSubModel);


                }
                editorSecondSub_subActivityAdapter = new EditorSecondSub_sub_subActivityAdapter(context, editorModelArrayList,this);
                rec_view.setAdapter(editorSecondSub_subActivityAdapter);
            }
            else {
                JSONObject jsonObject=new JSONObject(responce);
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("sub_sub_topic_files");

                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObjectseries=jsonArray.getJSONObject(i);
                    DiscoverModel discoverModel = new DiscoverModel();
                    String id = jsonObjectseries.getString("id");
                    String mime_type = jsonObjectseries.getString("mime_type");
                    String card_url = jsonObjectseries.getString("url");
//                    String ar_url = jsonObjectseries.getString("ar_url");

                    discoverModel.setId(id);
                    discoverModel.setTitle("");
                    discoverModel.setStatus("");
                    discoverModel.setLong_description("");
                    discoverModel.setMime_type(mime_type);
                    discoverModel.setCard_url(card_url);
                    discoverModel.setAr_url("");
                    discoverModelArrayList.add(discoverModel);


                }
                if (discoverModelArrayList.size()>0) {
                    setPagerAdapter();
                }
//                recyclerView.setOnTouchListener(new OnSwipeTouchListener(context) {
//
//                    public void onSwipeLeft() {
//                        startActivity(new Intent(context,EditorSubActivity.class).putExtra("chapter_id",discoverModelArrayList.get(recyclerView.getCurrentItem()).getId()));
////                    Toast.makeText(EditorActivity.this, finalI+ " "+discoverModelArrayList.get(recyclerView.getCurrentPage()).getId(), Toast.LENGTH_SHORT).show();
//                    }
//
//                });



            }
        } catch (JSONException e) {
            Log.e("JSONEXCEPTION",e+"");
            e.printStackTrace();
        }

    }





    @Override
    public void clickoncard(ImageView view,int position,String mimetype,String content,String title, String long_desc,List<Books> list,String image_Ar,String id) {
        releaseplayer();

//        startActivity(new Intent(context,EditorSubActivity.class).putExtra("chapter_id",id));

        Log.e("POS",position+"");
    }




    @Override
    public void onStart() {
        super.onStart();
    }


    private void setPagerAdapter() {
        try {
          PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
            FadeOutTransformation fadeOutTransformation = new FadeOutTransformation();

//            FadeOutTransformation fadeOutTransformation = new FadeOutTransformation();
//            pager.setPageTransformer(true, fadeOutTransformation);


//            FadeOutTransformation fadeOutTransformation = new FadeOutTransformation();
//            pager.setPageTransformer(true, fadeOutTransformation);


            recyclerViewvpager.setAdapter(adapter);
            recyclerViewvpager.setPageTransformer(true, fadeOutTransformation);
            pageIndicatorView.setVisibility(View.VISIBLE);
            pageIndicatorView.setViewPager(recyclerViewvpager, recyclerViewvpager.getAdapter().getCount() - 1);
            pageIndicatorView.setStepCount(discoverModelArrayList.size());
            pageIndicatorView.setCurrentStep(0);
//            FadeOutTransformation fadeOutTransformation = new FadeOutTransformation();
//            PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());

//            FadeOutTransformation fadeOutTransformation = new FadeOutTransformation();
//            pager.setPageTransformer(true, fadeOutTransformation);


            recyclerViewvpager.setAdapter(adapter);
//            pager.setCurrentItem(0);
            recyclerViewvpager.setOffscreenPageLimit(discoverModelArrayList.size()-1);
        } catch (Exception e) {

        }
    }

    @Override
    public void clickonplay(String content, SimpleExoPlayerView simpleExoPlayer) {
        initializePlayer(content,simpleExoPlayer);


    }

    public void onback(View view) {
        finish();
    }

    public void ontopic(View view) {
        releaseplayer();
        Intent intent=new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }


    private class PagerAdapter extends FragmentPagerAdapter {
        PlayerListener playerListener;

        public PagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {

//            if (position== 0){
//                c_page="";
//                next_page="";
//            }
//            else {
//                c_page = arrayList.get(position).getText();
//                next_page = arrayList.get(position + 1).getText();
//            }
//
//            imageid = arrayList.get(position).getBtn_image();
//            sliderid = arrayList.get(position).getImage_slider();
//            gravity_orientation = arrayList.get(position).getGravity();
//            PrefrenceUtils.writeInteger(context,PrefrenceUtils.PREF_SOUNDOFTHUNDER,position);
//            int itemindex = pager.getCurrentItem();


            Log.e("getItme", "" + position);
            return InstamodulePager.newInstance(discoverModelArrayList.get(position).getTitle(),discoverModelArrayList.get(position).getStatus()
                    ,discoverModelArrayList.get(position).getLong_description(),discoverModelArrayList.get(position).getMime_type()
                    ,discoverModelArrayList.get(position).getCard_url(),discoverModelArrayList.get(position).getCard_url(),context,playerListener,position);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public int getCount() {
//            Log.e("getCount", "" + NUM_PAGE);
            return discoverModelArrayList.size();
        }
    }


    private void initializePlayer(String card_url, SimpleExoPlayerView player){


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
        Uri videoUri = Uri.parse(card_url);
        MediaSource videoSource = new ExtractorMediaSource(videoUri,
                dataSourceFactory, extractorsFactory, null, null);
        Log.e("urlvidei",card_url);
        // Prepare the player with the source.
        simpleExoPlayer.prepare(videoSource);
        simpleExoPlayer.setPlayWhenReady(true);
        player.buildDrawingCache();

        boolean haveStartPosition = startWindow != C.INDEX_UNSET;
        if (haveStartPosition) {
            simpleExoPlayer.seekTo(startWindow, startPosition);
        }
        MediaSource audioSource = new ExtractorMediaSource(Uri.parse(card_url),
                new CacheDataSourceFactory(context, 100 * 1024 * 1024, 5 * 1024 * 1024), new DefaultExtractorsFactory(), null, null);



//        getDownloadCache().release();

//        audioSource.
        simpleExoPlayer.prepare(audioSource, !haveStartPosition, false);


    }

    @Override
    public void onBackPressed() {
        releaseplayer();
        finish();


    }
    private void releaseplayer(){
        try {

            Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vp + ":" + currentPos);
            InstamodulePager mnextFragment = (InstamodulePager) page;

            if (discoverModelArrayList.get(currentPos).getMime_type().equalsIgnoreCase("video")) {
                mnextFragment.closeexoplayer();
//                mnextFragment.initializePlayer();


            }
        }
        catch (Exception e){

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
//            File file = new File(context.getCacheDir(), "media"+System.currentTimeMillis());



            simpleCache = new SimpleCache(file, evictor);
//

            return new CacheDataSource(simpleCache, defaultDatasourceFactory.createDataSource(),
                    new FileDataSource(), new CacheDataSink(simpleCache, maxFileSize),
                    CacheDataSource.FLAG_BLOCK_ON_CACHE | CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR, null);
        }
    }


    private void openAR(String Ar_url){
        Log.e("AR_URL",ar_url+"");
        try {
//        "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Avocado/glTF/Avocado.gltf"
            Intent sceneViewerIntent = new Intent(Intent.ACTION_VIEW);
            sceneViewerIntent.setData(Uri.parse(Ar_url));
            sceneViewerIntent.setPackage("com.google.android.googlequicksearchbox");
            startActivity(sceneViewerIntent);
        }
        catch (Exception e){
            Toast.makeText(context,"No Apps to perform this action",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2){
            if (resultCode==RESULT_OK){
                Intent intent=new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }



    }
    @Override
    protected void onRestart() {
        super.onRestart();
        fabMenu.setVisibility(View.VISIBLE);
        rec_view.setVisibility(View.VISIBLE);
    }


    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }

}
