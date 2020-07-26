package com.edu.timeline;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.webkit.WebView;
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
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.viewpager.widget.ViewPager;

import com.badoualy.stepperindicator.StepperIndicator;
import com.edu.aplis.DemoApplication;
import com.edu.aplis.R;
import com.edu.chatsub_sub.Piechartsub_subActivtiy;
import com.edu.discover.Books;
import com.edu.discover.ClickAdapter;
import com.edu.discover.DiscoverModel;
import com.edu.editortemplate.EditoroneAdapter;
import com.edu.editortemplatetwo.EditorSecondSub_subActivityAdapter;
import com.edu.editortemplatetwo.EditorSecondSub_sub_subActivity;
import com.edu.editortemplatetwo.EditorSub_subModel;
import com.edu.editortemplatetwo.InstamodulePager;
import com.edu.editortemplatetwo.PlayerListener;
import com.edu.fab.FABsMenu;
import com.edu.fab.FABsMenuListener;
import com.edu.fab.TitleFAB;
import com.edu.piechart.BarchartActivitynew;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;
import com.edu.sketch.SketchActivity;
import com.edu.transformation.FadeOutTransformation;
import com.edu.tutorialviewpager.CustomPagerAdapter;
import com.edu.web.Viewsub_subActivity;
import com.edu.webservice.ApiService;
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
import com.piechartpojo.PieFirstRetrofitModel;
import com.timelinepojo.TimelineFirstRetrofitModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import at.blogc.android.views.ExpandableTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeLineActivity extends AppCompatActivity implements ResponceQueues ,PlayerListener {
    ViewPager recyclerViewvpager;
    StepperIndicator pageIndicatorView;
    HashMap<String, String> hashMap = new HashMap<>();
    Context context;
    String timeline_id = "";
    String ar_url = "";
    SimpleExoPlayer simpleExoPlayer = null;
    SimpleCache simpleCache = null;
    private int currentPos = 0;
    private int prevPos = -1;
    CardView cardimage_Ar;
    CardView cardimage_visualaize;
    ImageView image_visualaize;
    ClickTImeline clickTImeline;
    private ArrayList<TimeLineModel> timeLineModelArrayList;
    TimeLineModel timeLineModel;
    String intent_url = "";
    LinearLayout viewpagertopiclayer;
    FrameLayout root;
    TextView heading, subheading;
    ExpandableTextView longtext;
    private FABsMenu fabMenu;
    String bg_image = "";
    ImageView layout_bg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        context = TimeLineActivity.this;

        viewpagertopiclayer = findViewById(R.id.viewpagertopiclayer);
        root = findViewById(R.id.frame_layout);
        heading = findViewById(R.id.heading);
        subheading = findViewById(R.id.subheading);
        longtext = findViewById(R.id.text_subdetail);

        cardimage_Ar = findViewById(R.id.cardimage_Ar);
        cardimage_visualaize = findViewById(R.id.cardimage_visualaize);
        image_visualaize = findViewById(R.id.image_visualaize);

        recyclerViewvpager = findViewById(R.id.vp);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);

        callanalytics();
        timeLineModelArrayList = new ArrayList<>();


        longtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (longtext.isExpanded()) {
                    longtext.collapse();
                } else {
                    longtext.expand();
                }
            }
        });

        cardimage_Ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAR(timeLineModelArrayList.get(currentPos).getAr_url());
            }
        });

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
                    root.setVisibility(View.VISIBLE);
//                    root.setBackgroundColor(getResources().getColor(R.color.textCol));

                } else {
                    Transition transition = new Fade();
                    transition.setDuration(600);
                    transition.addTarget(R.id.viewpagertopiclayer);

                    TransitionManager.beginDelayedTransition(viewpagertopiclayer, transition);
                    image_visualaize.setImageResource(R.drawable.un_eye);
                    viewpagertopiclayer.setVisibility(View.GONE);
                    root.setVisibility(View.GONE);
//                    root.setBackgroundColor(getResources().getColor(R.color.white));

                }
            }
        });

        timeline_id = getIntent().getStringExtra("timeline_id");
        intent_url = getIntent().getStringExtra("url");
        Log.e("TAGADAPTERinsta_POS", "scrolled" + timeline_id + " ");

        makeHTTPCall(intent_url + timeline_id);


        recyclerViewvpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                Log.e("TAGADAPTERinsta_POS1", "scrolled" + timeLineModelArrayList.get(currentPos).getMime_type() + " " + timeLineModelArrayList.get(currentPos).getCard_url());

                prevPos = currentPos;
                currentPos = position;
                setsubchapterdetail(currentPos);
                Fragment fragment = getSupportFragmentManager().
                        findFragmentByTag("android:switcher:" + R.id.vp + ":" + prevPos);
                InstamodulePager mprevFragment = (InstamodulePager) fragment;
                try {
                    mprevFragment.closeexoplayer();
//        mFragment.initializePlayer();


                } catch (Exception e) {

                }


//                recyclerViewvpager.getAdapter().notifyDataSetChanged();


                try {

                    Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vp + ":" + currentPos);
                    InstamodulePager mnextFragment = (InstamodulePager) page;
                    cardimage_Ar.setVisibility(View.VISIBLE);
                    bg_image = timeLineModelArrayList.get(currentPos).getCard_url();

                    try {
                        if (timeLineModelArrayList.get(currentPos).getAr_url().equalsIgnoreCase("null")) {
                            cardimage_Ar.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {

                    }
                    Log.e("TAGADAPTERinsta_POS12", "scrolled" + timeLineModelArrayList.get(currentPos).getMime_type() + " " + timeLineModelArrayList.get(currentPos).getCard_url());

                    if (timeLineModelArrayList.get(currentPos).getMime_type().equalsIgnoreCase("video")) {
                        mnextFragment.closeexoplayer();
                        mnextFragment.initializePlayer();
                    }

                    else if(timeLineModelArrayList.get(currentPos).getMime_type().equalsIgnoreCase("web")) {
                        mnextFragment.callweb();

                    }
                    else if(timeLineModelArrayList.get(currentPos).getMime_type().equalsIgnoreCase("gif")) {
                        mnextFragment.callgif();

                    } else if(timeLineModelArrayList.get(currentPos).getMime_type().equalsIgnoreCase("image")) {
                        mnextFragment.callimage();

                    }

                    Log.e("TAGADAPTERinsta_POS11", "scrolled" + timeLineModelArrayList.get(currentPos).getMime_type() + " " + timeLineModelArrayList.get(currentPos).getCard_url());

                } catch (Exception e) {
                    Log.e("TAGADAPTERinsta_POS14", "scrolled" + e + "");

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

                Log.e("TAGADAPTERinsta_POS", "state" + " " + state + "");

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

                Log.e("clicked", "cicl====");

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
                clicksketchedit(layout_bg, "white", bg_image);
            }
        });

        TitleFAB noticeFab = findViewById(R.id.noticefab);
        noticeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicksketchedit(layout_bg, "transparent", bg_image);

            }
        });

    }

    private void setsubchapterdetail(int currentPos) {
        heading.setText(timeLineModelArrayList.get(currentPos).getTitle());
        subheading.setText(timeLineModelArrayList.get(currentPos).getStatus());
        longtext.setText(timeLineModelArrayList.get(currentPos).getLong_description());
    }


    public void clicksketchedit(View view1, String sketchoption, String content) {

//        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(TimeLineActivity.this,layout_bg,"imagesketch");

        startActivity(new Intent(context, SketchActivity.class).putExtra("color", sketchoption).putExtra("sub_name", content));

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

    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }

    private void setTimelineActivity(TimelineFirstRetrofitModel body) {
        timeLineModelArrayList.clear();
//        body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().size();
        Log.e("ResponsSUNASUNe===", body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().toString());

        for (int i = 0; i < body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().size(); i++) {
            timeLineModel = new TimeLineModel();
            String id = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getId();
            String heading = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getHeading();
            String sub_heading = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getSub_heading();
            String description = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getDescription();
            String date = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getDate();
            String time = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getTime();
            String year = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getYear();
            String mime_type = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getMime_type();
            String card_url = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getCard_url();
            String Ar_url = body.getTimelineSecondRetrofitModel().getTimelineThirdRetrofitModels().get(i).getAr_url();

            timeLineModel.setId(id);
            timeLineModel.setTitle(heading);
            timeLineModel.setStatus(sub_heading);
            timeLineModel.setLong_description(description);
            timeLineModel.setDate(date);
            timeLineModel.setTime(time);
            timeLineModel.setYaer(year);
            timeLineModel.setMime_type(mime_type);
            timeLineModel.setCard_url(card_url);
            timeLineModel.setAr_url(Ar_url);
            timeLineModelArrayList.add(timeLineModel);
            Log.e("ResponsSUNASUNe===", "url" + card_url);


        }
        if (timeLineModelArrayList.size() > 0) {
            currentPos = 0;
            setsubchapterdetail(0);
            bg_image = timeLineModelArrayList.get(0).getCard_url();

            Log.e("ResponsSUNASUNe===", "done");

            setPagerAdapter();
        }


    }

    @Override
    public void responceQue(String responce, String url, String extra_text) {
//
        Log.e("Response===", url + " response" + responce);

        try {

            if (url.contains(intent_url)) {
                timeLineModelArrayList.clear();

                JSONObject jsonObject = new JSONObject(responce).getJSONObject("data");
                JSONArray jsonArray = jsonObject.getJSONArray("timeline_entries");
                Log.e("ResponsSUNASUNe===", jsonArray.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectseries = jsonArray.getJSONObject(i);
                    timeLineModel = new TimeLineModel();
                    String id = jsonObjectseries.getString("id");
                    String heading = jsonObjectseries.getString("heading");
                    String sub_heading = jsonObjectseries.getString("sub_heading");
                    String description = jsonObjectseries.getString("description");
                    String date = jsonObjectseries.getString("date");
                    String time = jsonObjectseries.getString("time");
                    String year = jsonObjectseries.getString("year");
                    String mime_type = jsonObjectseries.getString("mime_type");
                    String card_url = jsonObjectseries.getString("url");
                    String Ar_url = jsonObjectseries.getString("ar_model");

                    timeLineModel.setId(id);
                    timeLineModel.setTitle(heading);
                    timeLineModel.setStatus(sub_heading);
                    timeLineModel.setLong_description(description);
                    timeLineModel.setDate(date);
                    timeLineModel.setTime(time);
                    timeLineModel.setYaer(year);
                    timeLineModel.setMime_type(mime_type);
                    timeLineModel.setCard_url(card_url);
                    timeLineModel.setAr_url(Ar_url);
                    timeLineModelArrayList.add(timeLineModel);


                }
                if (timeLineModelArrayList.size() > 0) {
                    currentPos = 0;
                    setsubchapterdetail(0);
                    Log.e("ResponsSUNASUNe===", "done");

                    setPagerAdapter();
                }


            }
        } catch (JSONException e) {
            Log.e("ResponsSUNASUNe===", e + "");
            e.printStackTrace();
        }

    }


    @Override
    public void onStart() {
        super.onStart();
    }


    private void setPagerAdapter() {
        Log.e("TAGADAPTERinsta_POSpager", "adapter" + " " + timeLineModelArrayList.size() + "");
        try {
            String[] yearArr = new String[timeLineModelArrayList.size()];
            for (int i = 0; i < timeLineModelArrayList.size(); i++) {
                yearArr[i] = timeLineModelArrayList.get(i).getYaer();
            }

//            FadeOutTransformation fadeOutTransformation = new FadeOutTransformation();
            PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), this);
            FadeOutTransformation fadeOutTransformation = new FadeOutTransformation();

//            FadeOutTransformation fadeOutTransformation = new FadeOutTransformation();
//            pager.setPageTransformer(true, fadeOutTransformation);


            recyclerViewvpager.setAdapter(adapter);
            recyclerViewvpager.setPageTransformer(true, fadeOutTransformation);
            try {
                pageIndicatorView.setViewPager(recyclerViewvpager, recyclerViewvpager.getAdapter().getCount() - 1);
                pageIndicatorView.setStepCount(timeLineModelArrayList.size());
                pageIndicatorView.setLabels(yearArr);
                pageIndicatorView.setLabelColor(getResources().getColor(R.color.white));
                pageIndicatorView.setCurrentStep(0);
                pageIndicatorView.setVisibility(View.VISIBLE);
            } catch (Exception e) {

            }

//                pageIndicatorView.setLabels(str);

            recyclerViewvpager.setOffscreenPageLimit(timeLineModelArrayList.size()-1);
        } catch (Exception e) {

        }
    }

    @Override
    public void clickonplay(String content, SimpleExoPlayerView simpleExoPlayerView) {
        initializePlayer(content, simpleExoPlayerView);

    }

    private void initializePlayer(String card_url, SimpleExoPlayerView player) {


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
        Log.e("urlvidei", card_url);
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


    public void image_visualaizeclick(View view) {
    }

    public void onback(View view) {
        finish();
    }

    public void ontopic(View view) {
        releaseplayer();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
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
            File file = new File(context.getCacheDir(), "media" + System.currentTimeMillis());
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

        public PagerAdapter(FragmentManager fm, PlayerListener playerListener) {
            super(fm);
            this.playerListener = playerListener;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {

            Log.e("getItme", "done" + position);
            return InstamodulePager.newInstance(timeLineModelArrayList.get(position).getTitle(), timeLineModelArrayList.get(position).getStatus()
                    , timeLineModelArrayList.get(position).getLong_description(), timeLineModelArrayList.get(position).getMime_type()
                    , timeLineModelArrayList.get(position).getCard_url(), timeLineModelArrayList.get(position).getAr_url(), context, playerListener, position);

        }

        @Override
        public int getItemPosition(Object object) {

            return POSITION_NONE;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);

        }

        @Override
        public int getCount() {
            return timeLineModelArrayList.size();
        }



    }

    @Override
    public void onBackPressed() {
        releaseplayer();
        finish();


    }

    private void releaseplayer() {
        try {

            Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vp + ":" + currentPos);
            InstamodulePager mnextFragment = (InstamodulePager) page;

            if (timeLineModelArrayList.get(currentPos).getMime_type().equalsIgnoreCase("video")) {
                mnextFragment.closeexoplayer();
//                mnextFragment.initializePlayer();


            }
        } catch (Exception e) {

        }
    }

    private void openAR(String Ar_url) {
        Log.e("AR_URL", ar_url + "");

        try {
//        "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Avocado/glTF/Avocado.gltf"
            Intent sceneViewerIntent = new Intent(Intent.ACTION_VIEW);
            sceneViewerIntent.setData(Uri.parse(Ar_url));
            sceneViewerIntent.setPackage("com.google.android.googlequicksearchbox");
            startActivity(sceneViewerIntent);
        } catch (Exception e) {
            Toast.makeText(context, "No Apps to perform this action", Toast.LENGTH_LONG).show();
        }
    }

    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }

}
