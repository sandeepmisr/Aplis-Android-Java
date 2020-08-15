package com.edu.editortemplatetwo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.viewpager.widget.ViewPager;

import com.badoualy.stepperindicator.StepperIndicator;
import com.editorsecondsub_subactivitypojo.Editorsub_subFirstDetailsRetrofitModel;
import com.editorsecondsub_subactivitypojo.Editorsub_subSecondRetrofitModel;
import com.editorsecondsub_subactivitypojo.Editorsub_subThirdDetailsRetrofitModel;
import com.editorsecondsub_subactivitypojo.Editorsub_subThirdRetrofitModel;
import com.edu.aplis.DemoApplication;
import com.edu.aplis.R;
import com.edu.chatsub_sub.Piechartsub_subActivtiy;
import com.edu.discover.Books;
import com.edu.discover.ClickAdapter;
import com.edu.discover.DiscoverModel;
import com.edu.editortemplate.EditoroneAdapter;
import com.edu.fab.FABsMenu;
import com.edu.fab.FABsMenuListener;
import com.edu.fab.TitleFAB;
import com.edu.piechart.BarchartActivitynew;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;
import com.edu.sketch.SketchActivity;
import com.edu.timeline.TimeLineModel;
import com.edu.transformation.FadeOutTransformation;
import com.edu.web.Viewsub_subActivity;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
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
import com.timelinepojo.TimelineFirstRetrofitModel;

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


public class TimeLineNewActivity extends AppCompatActivity implements ResponceQueues,ClickAdapter,PlayerListener {
    TextView subtext_title, subtext_subtitle;
    private ViewPager recyclerViewvpager;
    StepperIndicator pageIndicatorView;
    ExpandableTextView subtext_description;
    LinearLayout ll_scrollseriesoftitle;
    HashMap<String, String> hashMap = new HashMap<>();
    Context context;
    private ArrayList<EditorSub_subModel> editorModelArrayList;
    EditorSub_subModel editorSubModel;
    String sub_id = "";
    String sub_name = "";
    String sub_subname = "";
    String sub_description = "";
    String ar_url = "";
    String intent_url = "";

    String bg_image = "";
    View layout_bg;
    RecyclerView rec_view;
    EditorSecondSub_subActivityAdapter editorSecondSub_subActivityAdapter;

    View nextChild;
    SimpleExoPlayerView player;
    SimpleExoPlayer simpleExoPlayer = null;
    SimpleCache simpleCache = null;
    private int currentPos = 0;
    private int prevPos = -1;
    ImageView image_Ar;

    LinearLayout viewpagertopiclayer;
    LinearLayout linear_layer;
    LinearLayout leftpanel;
    CardView cardimage_Ar;
    CardView cardimage_visualaize;
    ImageView image_visualaize;

    private FABsMenu fabMenu;
    View view;
    private int startWindow;
    private long startPosition;


    private ArrayList<TimeLineModel> timeLineModelArrayList;
    Animation animZoomIN, animZoomOut;
    private View mCurrentView;

    EditoroneAdapter editoroneAdapter;
    String book_Id = "";

    VideoView videoView;
    private PlayerView videoSurfaceView;
    private SimpleExoPlayer videoPlayer;
    private FrameLayout frameLayout;
    private int playPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondsub_subeditor);
        context = TimeLineNewActivity.this;


        viewpagertopiclayer = findViewById(R.id.viewpagertopiclayer);
//        linear_layer = findViewById(R.id.linear_layer);
        cardimage_Ar = findViewById(R.id.cardimage_Ar);
        cardimage_visualaize = findViewById(R.id.cardimage_visualaize);
        image_visualaize = findViewById(R.id.image_visualaize);


        subtext_title = findViewById(R.id.text_title);
        subtext_subtitle = findViewById(R.id.text_subtitle);
        subtext_description = findViewById(R.id.text_subdetail);
        rec_view = findViewById(R.id.rec_view);
        image_Ar = findViewById(R.id.image_Ar);

        recyclerViewvpager = findViewById(R.id.vp);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);

        fabMenu = findViewById(R.id.fabs_menu);
        layout_bg = findViewById(R.id.layout_bg);
        leftpanel = findViewById(R.id.leftpanel);

        //callanalytics();
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
                clicksketchedit(null,"white",bg_image);
            }
        });

        TitleFAB noticeFab = findViewById(R.id.noticefab);
        noticeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicksketchedit(null,"transparent",bg_image);

            }
        });

        timeLineModelArrayList = new ArrayList<>();

        sub_id = getIntent().getStringExtra("timeline_id");
        sub_name = getIntent().getStringExtra("subtitle_name");
        sub_subname = getIntent().getStringExtra("subtitle_subname");
        sub_description = getIntent().getStringExtra("subtitle_description");
        ar_url = getIntent().getStringExtra("ar_url");
        intent_url = getIntent().getStringExtra("url");

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

        cardimage_visualaize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewpagertopiclayer.getVisibility() == View.GONE) {

                    Transition transition = new Fade();
                    transition.setDuration(600);
                    transition.addTarget(R.id.viewpagertopiclayer);

                    TransitionManager.beginDelayedTransition(viewpagertopiclayer, transition);

                    image_visualaize.setImageResource(R.drawable.visuliaze);
                    viewpagertopiclayer.setVisibility(View.VISIBLE);
//                    linear_layer.setVisibility(View.VISIBLE);
//                    root.setBackgroundColor(getResources().getColor(R.color.textCol));

                } else {
                    Transition transition = new Fade();
                    transition.setDuration(600);
                    transition.addTarget(R.id.viewpagertopiclayer);

                    TransitionManager.beginDelayedTransition(viewpagertopiclayer, transition);
                    image_visualaize.setImageResource(R.drawable.un_eye);
                    viewpagertopiclayer.setVisibility(View.GONE);
//                    linear_layer.setVisibility(View.GONE);
//                    root.setBackgroundColor(getResources().getColor(R.color.white));

                }
            }
        });

        cardimage_Ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAR(ar_url);
            }
        });
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false);
        rec_view.setLayoutManager(linearLayoutManager);

        subtext_description.setText(sub_description);

        subtext_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (subtext_description.isExpanded()) {
                    subtext_description.collapse();
                } else {
                    subtext_description.expand();
                }
            }
        });


        image_Ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAR(ar_url);
            }
        });



        Log.e(getClass().getSimpleName(),getIntent().getStringExtra("timeline_id")+ " " + getIntent().getStringExtra("subtitle_name"));
//        getDataFromServer();

        makeHTTPCall(intent_url+ sub_id);


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
//                        View onScreenView = recyclerViewvpager.getChildAt(position);
//Log.e("onScreenView",onScreenView+"");
                        bg_image = timeLineModelArrayList.get(currentPos).getCard_url();
//                        layout_bg = onScreenView;

if (timeLineModelArrayList.get(currentPos).getMime_type().equalsIgnoreCase("video")) {
    mnextFragment.closeexoplayer();
    mnextFragment.initializePlayer();

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

                Log.e("TAGADAPTERinsta_POS","selected"+ position+" "+timeLineModelArrayList.get(position).getMime_type());



            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("TAGADAPTERinsta_POS","state"+" "+state+"");

            }
        });
    }

    private void makeHTTPCall(String url) {

        Api api = RetrofitClient.getClient(context, Api.BASE_URL).create(Api.class);

        Call<TimelineFirstRetrofitModel> call = api.getTimelinePage(url);
        Log.e("discovertry", call.toString());
        call.enqueue(new Callback<TimelineFirstRetrofitModel>() {
            @Override
            public void onResponse(Call<TimelineFirstRetrofitModel> call, Response<TimelineFirstRetrofitModel> response) {

                try {
                    setTimelineActivity(response.body());
                    Log.e("editortry", response.toString() + " " + response.message());
                } catch (Exception e) {
                    Log.e("editorerror", e + "");

                }
//                if (response.body().getStatus().equalsIgnoreCase("1")) {
//                    setBookDetails(response.body());
//                }
            }

            @Override
            public void onFailure(Call<TimelineFirstRetrofitModel> call, Throwable t) {
                Log.e("errordo", t.getMessage());
            }
        });
//        trustEveryone();
//
//        ApiService apiService = new ApiService(context,this, url,hashMap,1);
//        apiService.execute();
    }
    private void setTimelineActivity(TimelineFirstRetrofitModel body) {
//        timeLineModelArrayList.clear();
//        body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().size();
        Log.e("ResponsSUNASUNe===", body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().toString());

        for (int i = 0; i < body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().size(); i++) {
            TimeLineModel  timeLineModel = new TimeLineModel();
            String id = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getId();
//            String heading = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getHeading();
//            String sub_heading = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getSub_heading();
//            String description = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getDescription();
//            String date = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getDate();
//            String time = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getTime();
//            String year = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getYear();
            String mime_type = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getMime_type();
            String card_url = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getCard_url();
//            String Ar_url = "null";

            timeLineModel.setId(id);
            timeLineModel.setTitle("heading");
            timeLineModel.setStatus("sub");
            timeLineModel.setLong_description("Sed");
            timeLineModel.setDate("date");
            timeLineModel.setTime("dint");
            timeLineModel.setYaer("yesr");
            timeLineModel.setMime_type(mime_type);
            timeLineModel.setCard_url(card_url);
            timeLineModel.setAr_url("");
            timeLineModelArrayList.add(timeLineModel);
            Log.e("ResponsSUNASUNe===value", "url" + id+" mime"+mime_type+ card_url);


        }
        if (timeLineModelArrayList.size() > 0) {
//            currentPos = 0;
//            setsubchapterdetail(0);
            bg_image = timeLineModelArrayList.get(0).getCard_url();

            Log.e("ResponsSUNASUNe===", "done");

            setPagerAdapter();
        }


    }



//    private void discoverdata(List<Editorsub_subThirdDetailsRetrofitModel> editorsub_subThirdDetailsRetrofitModels){
//        Log.e("ResponsSUNASUNetimeline===1", "url" + editorsub_subThirdDetailsRetrofitModels.size()+"");
//
//        for (int i=0;i<editorsub_subThirdDetailsRetrofitModels.size();i++){
//            DiscoverModel discoverModel = new DiscoverModel();
//            String id = editorsub_subThirdDetailsRetrofitModels.get(i).getId();
//            String mime_type =editorsub_subThirdDetailsRetrofitModels.get(i).getMime_type();
//            String card_url = editorsub_subThirdDetailsRetrofitModels.get(i).getUrl();
//            //                    String Ar_url = jsonObjectseries.getString("ar_url");
//
//            discoverModel.setId(id);
//            discoverModel.setTitle("");
//            discoverModel.setStatus("");
//            discoverModel.setLong_description("");
//            discoverModel.setMime_type(mime_type);
//            discoverModel.setCard_url(card_url);
//            discoverModel.setAr_url("");
//            timeLineModelArrayList.add(discoverModel);
//
//            Log.e("ResponsSUNASUNetimeline===1", "url" + timeLineModelArrayList.size()+"");
//
//
//        }
//        if (timeLineModelArrayList.size()>0) {
//            Log.e("ResponsSUNASUNetimeline===2", "url" + timeLineModelArrayList.size()+"");
//
//            bg_image = timeLineModelArrayList.get(0).getCard_url();
//            setPagerAdapter();
//        }
//
//
//
//
//    }

    private void setsubchapterdetail(int currentPos) {
        subtext_title.setText(timeLineModelArrayList.get(currentPos).getTitle());
        subtext_subtitle.setText(timeLineModelArrayList.get(currentPos).getStatus());
        subtext_description.setText(timeLineModelArrayList.get(currentPos).getLong_description());
    }


    @Override
    public void responceQue(String responce, String url, String extra_text) {
        Log.e("Response===",url+" response"+responce);


    }




    @Override
    public void clickoncard(ImageView view,int position,String mimetype,String content,String title, String long_desc,List<Books> list,String image_Ar,String id) {
        Log.e("Editorsub_subtopic2",mimetype+ " id"+id+ " pos"+position);
        releaseplayer();

        switch (mimetype){
            case "web":
                startActivityForResult(new Intent(context, Viewsub_subActivity.class)
                        .putExtra("sub_sub_id_s",position+"")
                        .putExtra("subtitle_name",title)
                        .putExtra("subtitle_subname",content)
                        .putExtra("ar_url",image_Ar)
                        .putExtra("subtitle_description",long_desc),2);
                break;
            case "text":
                startActivityForResult(new Intent(context, EditorSecondSub_sub_subActivity.class)
                        .putExtra("sub_sub_id_s",position+"")
                        .putExtra("subtitle_name",title)
                        .putExtra("subtitle_subname",content)
                        .putExtra("ar_url",image_Ar)
                        .putExtra("subtitle_description",long_desc),2);
                break;
            case "pie_chart":
                startActivityForResult(new Intent(context, Piechartsub_subActivtiy.class)
                        .putExtra("sub_sub_id_s",position+"")
                        .putExtra("subtitle_name",title)
                        .putExtra("subtitle_subname",content)
                        .putExtra("ar_url",image_Ar)
                        .putExtra("subtitle_description",long_desc)
                        .putExtra("url",Cons.GET_SUBSUBCHAPTERYBDETAILCHAPTERID),2);
                break;
            case "bar_chart":
                startActivityForResult(new Intent(context, BarchartActivitynew.class)
                        .putExtra("sub_sub_id_s",position+"")
                        .putExtra("subtitle_name",title)
                        .putExtra("subtitle_subname",content)
                        .putExtra("subtitle_description",long_desc)
                        .putExtra("ar_url",image_Ar)
                        .putExtra("url",Cons.GET_SUBSUBCHAPTERYBDETAILCHAPTERID),2);
                break;
            case "timeline":
                startActivityForResult(new Intent(context, TimeLineNewActivity.class)
                        .putExtra("timeline_id",position+"")
                        .putExtra("subtitle_name",title)
                        .putExtra("subtitle_subname",content)
                        .putExtra("ar_url",image_Ar)
                        .putExtra("url",Cons.GET_SUBSUBCHAPTERYBDETAILCHAPTERID)
                        .putExtra("subtitle_description",long_desc),2);
                break;

            case "graph":
                startActivityForResult(new Intent(context, BarchartActivitynew.class)
                        .putExtra("sub_sub_id_s",position+"")
                        .putExtra("subtitle_name",title)
                        .putExtra("subtitle_subname",content)
                        .putExtra("subtitle_description",long_desc)
                        .putExtra("url",Cons.GET_SUBSUBCHAPTERYBDETAILCHAPTERID),2);
                break;
            case "column":
                startActivityForResult(new Intent(context, BarchartActivitynew.class)
                        .putExtra("sub_sub_id_s",position+"")
                        .putExtra("subtitle_name",title)
                        .putExtra("subtitle_subname",content)
                        .putExtra("subtitle_description",long_desc)
                        .putExtra("ar_url",image_Ar)
                        .putExtra("url",Cons.GET_SUBSUBCHAPTERYBDETAILCHAPTERID),2);
                break;
            default:
                startActivityForResult(new Intent(context, BarchartActivitynew.class)
                        .putExtra("sub_sub_id_s",position+"")
                        .putExtra("subtitle_name",title)
                        .putExtra("subtitle_subname",content)
                        .putExtra("subtitle_description",long_desc)
                        .putExtra("ar_url",image_Ar)
                        .putExtra("url",Cons.GET_SUBSUBCHAPTERYBDETAILCHAPTERID),2);
                break;
        }

        Log.e("POS",position+"");
    }




    @Override
    public void onStart() {
        super.onStart();
    }


    private void setPagerAdapter() {
        try {

//            FadeOutTransformation fadeOutTransformation = new FadeOutTransformation();
            PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),this);
            FadeOutTransformation fadeOutTransformation = new FadeOutTransformation();

//            FadeOutTransformation fadeOutTransformation = new FadeOutTransformation();
//            pager.setPageTransformer(true, fadeOutTransformation);


//            FadeOutTransformation fadeOutTransformation = new FadeOutTransformation();
//            pager.setPageTransformer(true, fadeOutTransformation);


            recyclerViewvpager.setAdapter(adapter);
            recyclerViewvpager.setPageTransformer(true, fadeOutTransformation);
            try {
                pageIndicatorView.setVisibility(View.VISIBLE);
                pageIndicatorView.setViewPager(recyclerViewvpager, recyclerViewvpager.getAdapter().getCount() - 1);
                pageIndicatorView.setStepCount(timeLineModelArrayList.size());
                pageIndicatorView.setCurrentStep(0);
                Log.e("ResponsSUNASUNetimeline===", "url" + timeLineModelArrayList.size()+"");

            }
            catch (Exception e){
                Log.e("ResponsSUNASUNetimeline===", "url" + e+"");

            }

//            recyclerViewvpager.fixScrollSpeed();

            // just set viewPager
//            springIndicator.setViewPager(recyclerViewvpager);

//            pager.setCurrentItem(0);
            recyclerViewvpager.setOffscreenPageLimit(0);
        } catch (Exception e) {

        }
    }

    @Override
    public void clickonplay(String content, SimpleExoPlayerView simpleExoPlayerView) {
        initializePlayer(content,simpleExoPlayerView);

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
//        player.buildDrawingCache();
//
//        boolean haveStartPosition = startWindow != C.INDEX_UNSET;
//        if (haveStartPosition) {
//            simpleExoPlayer.seekTo(startWindow, startPosition);
//        }
//        MediaSource audioSource = new ExtractorMediaSource(Uri.parse(card_url),
//                new CacheDataSourceFactory(context, 100 * 1024 * 1024, 5 * 1024 * 1024), new DefaultExtractorsFactory(), null, null);



//        getDownloadCache().release();

//        audioSource.
//        simpleExoPlayer.prepare(audioSource, !haveStartPosition, false);


    }

    public void onback(View view) {
        finish();
    }

    public void ontopic(View view) {
//        Intent intent=new Intent();
//        setResult(RESULT_OK, intent);
        finish();
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


    private class PagerAdapter extends FragmentPagerAdapter {
        PlayerListener playerListener;
        public PagerAdapter(FragmentManager fm,PlayerListener playerListener) {
            super(fm);
            this.playerListener=playerListener;
        }

        @Override
        public Fragment getItem(int position) {
//            Log.e("getItmevp","check"+EditorSecondSub_subActivity.recyclerViewvpager.getCurrentItem()+"");


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


            Log.e("ResponsSUNASUNetimeline===", "url" + "try title"+timeLineModelArrayList.get(position).getTitle()
            +"status\n"+timeLineModelArrayList.get(position).getStatus()+"des\n"+timeLineModelArrayList.get(position).getLong_description()+"mime\n"+timeLineModelArrayList.get(position).getMime_type()
                    +"card\n"+timeLineModelArrayList.get(position).getCard_url() + "ar\n"+timeLineModelArrayList.get(position).getAr_url());
            return InstamodulePager.newInstance(timeLineModelArrayList.get(position).getTitle(),timeLineModelArrayList.get(position).getStatus()
                    ,timeLineModelArrayList.get(position).getLong_description(),timeLineModelArrayList.get(position).getMime_type()
                    ,timeLineModelArrayList.get(position).getCard_url(),"null",context,playerListener,position);
        }

        @Override
        public int getItemPosition(Object object) {
            Log.e("getItmelis", "done" + POSITION_UNCHANGED);
//            Log.e("TAGADAPTERinstagetItme", "done" + mCurrentView+"");

            return POSITION_NONE;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
//            mCurrentView = (View)object;
            super.setPrimaryItem(container, position, object);
//            Log.e("TAGADAPTERinstagetItme", "done" + mCurrentView+"");

        }

        @Override
        public int getCount() {
//            Log.e("getCount", "" + NUM_PAGE);
            return timeLineModelArrayList.size();
        }

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

            if (timeLineModelArrayList.get(currentPos).getMime_type().equalsIgnoreCase("video")) {
                mnextFragment.closeexoplayer();
//                mnextFragment.initializePlayer();


            }
        }
        catch (Exception e){

        }
    }

    public  void clicksketchedit(View view1,String sketchoption,String content){
        fabMenu.setVisibility(View.GONE);
        rec_view.setVisibility(View.GONE);
        leftpanel.setVisibility(View.GONE);
//        String filename = Screenshot.getScreenshot(layout_bg,context);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(TimeLineNewActivity.this,layout_bg,"imagesketch");

        startActivity(new Intent(context, SketchActivity.class).putExtra("color",sketchoption).putExtra("sub_name",content),activityOptionsCompat.toBundle());

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
                finish();
            }
        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fabMenu.setVisibility(View.VISIBLE);
        rec_view.setVisibility(View.VISIBLE);
        leftpanel.setVisibility(View.VISIBLE);
    }

/*    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }*/

}
