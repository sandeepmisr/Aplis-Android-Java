package com.edu.editortemplatetwo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.danikula.videocache.HttpProxyCacheServer;
import com.edu.aplis.DemoApplication;
import com.edu.aplis.R;
import com.edu.discover.DiscoverDetailActivity;
import com.edu.web.Viewsub_subActivity;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;

import java.io.File;

public class InstamodulePager extends Fragment implements Player.EventListener, PlaybackPreparer, PlayerControlView.VisibilityListener {

    private static PlayerListener playerListenernew;
    private String title;
    private int page;
    static Context mcontext;
    SimpleExoPlayer simpleExoPlayer= null;
    long current_frame=100000;
    SimpleCache simpleCache = null;
    private int startWindow;
    private long startPosition;
    String Card_url;
    SimpleExoPlayer player;
    PlayerView playerView;
    LinearLayout view_mime;
    WebView webView;
    private DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    private TrackGroupArray lastSeenTrackGroupArray;
    private ImageButton retryButton;
    private LinearLayout centerControl;
    private CoordinatorLayout controlView;
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;

    public View frame= null;
    private boolean startAutoPlay;
    PlaybackPreparer playbackPreparer;
    private DataSource.Factory dataSourceFactory;
    private MediaSource mediaSource;
    private boolean mExoPlayerFullscreen = false;
    String subheading;
    String longtext;
    String mimetype;
    String Ar_url;
    Integer position;
    private static final String TAG = "ExoPlayerActivity";
    private static final String KEY_VIDEO_URI = "video_uri";

    public static InstamodulePager newInstance(String title, String subheading, String longtext, String mimetype, String Card_url,String Ar_url, Context context
    ,PlayerListener playerListener,int position) {
        Log.e("getItme", "plater" + playerListener+" con"+context+" pos"+position+"");
        InstamodulePager fragment = new InstamodulePager();
        mcontext=context;
        playerListenernew=playerListener;
//      clickAdapter1=clickAdapter;
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("subheading", subheading);
        args.putString("longtext", longtext);
        args.putString("mimetype", mimetype);
        args.putString("Card_url", Card_url);
        args.putString("Ar_url", Ar_url);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    public InstamodulePager() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         title = this.getArguments().getString("title");
         subheading = this.getArguments().getString("subheading");
         longtext = this.getArguments().getString("longtext");
         mimetype = this.getArguments().getString("mimetype");
         Card_url = this.getArguments().getString("Card_url");
         Ar_url = this.getArguments().getString("Ar_url");
         position = this.getArguments().getInt("position");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View itemView = inflater.inflate(R.layout.editortwo_instapage_adapater, container, false);

//        View view = EditorSecondSub_subActivity.recyclerViewvpager.getChildAt(EditorSecondSub_subActivity.recyclerViewvpager.getCurrentItem()).getRootView();
        TextView tittle = itemView.findViewById(R.id.heading);
        TextView text_body = itemView.findViewById(R.id.subheading);
        TextView text_longdescription = itemView.findViewById(R.id.longtext);
         view_mime = itemView.findViewById(R.id.mimeview);
        LinearLayout viewpagertopiclayer = itemView.findViewById(R.id.viewpagertopiclayer);
        webView = itemView.findViewById(R.id.webView);
        playerView = itemView.findViewById(R.id.videoFullScreenPlayer);
        playbackPreparer =this;

        tittle.setText(title);
        text_body.setText(subheading);
        text_longdescription.setText(Html.fromHtml(longtext));
        frame = itemView;
//        playerView.setVisibility(View.GONE);


        Log.e("TAGADAPTERinsta",mimetype+ " ");
        if (mimetype.equalsIgnoreCase("image")){
            view_mime.setVisibility(View.VISIBLE);
            ImageView imageView = new ImageView(mcontext);
            RelativeLayout.LayoutParams vp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(vp);
            Glide
                    .with(mcontext)
                    .load(Card_url)
                    .thumbnail(0.05f)
                    .centerCrop()
                    .into(imageView);
            view_mime.addView(imageView);

        }

        else if (mimetype.equalsIgnoreCase("web")){
            view_mime.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            playerView.setVisibility(View.GONE);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setAppCacheEnabled(true);
            webView.getSettings().setAppCachePath(mcontext.getCacheDir().getPath());
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            webView.setWebViewClient(new WebViewClient());
            getWebview(Card_url);
        }

        else if (mimetype.equalsIgnoreCase("gif")){
            view_mime.setVisibility(View.VISIBLE);

            ImageView imageView = new ImageView(mcontext);
            RelativeLayout.LayoutParams vp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(vp);
            Glide
                    .with(mcontext)
                    .load(Card_url.replaceAll(" ", "%20"))
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .thumbnail(0.05f)
                    .centerCrop()
                    .into(imageView);
            view_mime.addView(imageView);
        }

        else if (mimetype.equalsIgnoreCase("video")){
//            view_mime.removeAllViews();
//
//            view_mime.setVisibility(View.GONE);
//            LinearLayout.LayoutParams vp =
//                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                            10);
//            webView.setLayoutParams(vp);
//            webView.destroy();
//            playerView.setVisibility(View.VISIBLE);
            Log.e("getItme","videp"+playerView.getVisibility()+"");
//            if (player.getVisibility()!=View.VISIBLE){
//                if (simpleExoPlayer != null) {
//                    simpleExoPlayer.release();
//                    simpleExoPlayer = null;
//                }
//            }

//            view_mime.addView(videoView);

        }
//        frame.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                clickAdapter.clickoncard(position,listModel.getMime_type(),listModel.getHeader_url(),listModel.getTitle(),listModel.getLong_description(),null,listModel.getImage_Ar()
////                        ,listModel.getId());
//            }
//        });


        try {
//            Picasso.with(context1).load(aaya)
//                    .placeholder(context1.getResources().getDrawable(R.drawable.logoplaceholder)).into(imageView);
        }
        catch (Exception e){
            e.printStackTrace();
        }




//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                clickAdapter1.click("","");
//            }
//        });
        return itemView;



    }





    public void getWebview(String myurl)
    {
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                prg.show();



                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

//                prg.dismiss();

//                view.getSettings().setJavaScriptEnabled(true);




                super.onPageFinished(view, url);
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {



                super.onPageStarted(view, url, favicon);
            }





        });
//        prg = ProgressDialog.show(mcontext, "Please wait", "Processing...", true);
//        prg.setCancelable(true);
        webView.loadUrl(myurl);



    }

    public void checkAr(String Ar_url){
    if (Ar_url.equalsIgnoreCase("null")||Ar_url.equalsIgnoreCase(null)){

    }
    else{

    }

}
    public void initializePlayer() {
        Log.e("CHECKWEB",webView.getVisibility()+"");
//        webView.setVisibility(View.GONE);
        playerView.setVisibility(View.VISIBLE);
        Log.e("CHECKWEB",webView.getVisibility()+"");

//        webView.setVisibility(View.GONE);

        // Create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        //Initialize the player
        player = ExoPlayerFactory.newSimpleInstance(mcontext, trackSelector);

        //Initialize simpleExoPlayerView
        playerView.setPlayer(player);
        playerView.setPlaybackPreparer(this);
        mediaSource = buildMediaSource(Uri.parse(Card_url.replaceAll(" ", "%20")));
        playerView.buildDrawingCache();
        boolean haveStartPosition = startWindow != C.INDEX_UNSET;
        if (haveStartPosition) {
            player.seekTo(startWindow, startPosition);
        }


        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(mcontext, Util.getUserAgent(mcontext, "Aplinew"));

        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        // This is the MediaSource representing the media to be played.
        Uri videoUri = Uri.parse(Card_url.replaceAll(" ", "%20"));

//        MediaSource audioSource = new ExtractorMediaSource(Uri.parse(Card_url.replaceAll(" ", "%20")),
//                new CacheDataSourceFactory(mcontext, 100 * 1024 * 1024, 5 * 1024 * 1024), new DefaultExtractorsFactory(), null, null);
//
////        MediaSource videoSource = new ExtractorMediaSource(videoUri,
////                dataSourceFactory, extractorsFactory, null, null);
//        Log.e("urlvidei",Card_url);
//        // Prepare the player with the source.
//        player.prepare(audioSource, !haveStartPosition, false);
        player.setPlayWhenReady(true);
        playerView.buildDrawingCache();
        player.getCurrentPosition();
//player.getCur
//        boolean haveStartPosition = startWindow != C.INDEX_UNSET;
//        if (haveStartPosition) {
//            player.seekTo(startWindow, startPosition);
//        }

        initCustomController();
        initFullscreenDialog();


        MediaSource audioSource = new ExtractorMediaSource(Uri.parse(Card_url.replaceAll(" ", "%20")),
                new CacheDataSourceFactory(mcontext, 100 * 1024 * 1024, 5 * 1024 * 1024), new DefaultExtractorsFactory(), null, null);
//
//
//
////        getDownloadCache().release();
//
////        audioSource.
        player.prepare(audioSource, !haveStartPosition, false);


    }


//    private void initFullscreenButton() {
//        RelativeLayout mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
//        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!mExoPlayerFullscreen) {
//                    setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                    openFullscreenDialog();
//                } else {
//                    setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                    closeFullscreenDialog();
//                }
//            }
//        });
//
//    }

    @Override
    public void preparePlayback() {

    }

    @Override
    public void onVisibilityChange(int visibility) {

    }


    private class PlayerEventListener implements Player.EventListener {

        private boolean isBehindLiveWindow(ExoPlaybackException e) {
            if (e.type != ExoPlaybackException.TYPE_SOURCE) {
                return false;
            }
            Throwable cause = e.getSourceException();
            while (cause != null) {
                if (cause instanceof BehindLiveWindowException) {
                    return true;
                }
                cause = cause.getCause();
            }
            return false;
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (playbackState == Player.STATE_ENDED) {
                showRetryButton();
            }
        }

        private void showRetryButton() {
            centerControl.setVisibility(View.GONE);
            retryButton.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPositionDiscontinuity(@Player.DiscontinuityReason int reason) {
            if (player.getPlaybackError() != null) {
                // The user has performed a seek whilst in the error state. Update the resume position so
                // that if the user then retries, playback resumes from the position to which they seeked.
                updateStartPosition();
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException e) {
            if (isBehindLiveWindow(e)) {
                clearStartPosition();
                initializePlayer();
            } else {
                updateStartPosition();
            }
        }

        @Override
        @SuppressWarnings("ReferenceEquality")
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            if (trackGroups != lastSeenTrackGroupArray) {
                MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                if (mappedTrackInfo != null) {
                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
                            == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                        showToast("unsupported video");
                    }
                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_AUDIO)
                            == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                        showToast("unsupported video");
                    }
                }
                lastSeenTrackGroupArray = trackGroups;
            }
        }

    }
    private void showToast(String message) {
        Toast.makeText(mcontext, message, Toast.LENGTH_LONG).show();
    }

    private void initCustomController() {
        controlView = playerView.findViewById(R.id.custom_controller);
        LinearLayout exoGoBack = controlView.findViewById(R.id.exo_exit);
        retryButton = controlView.findViewById(R.id.retry);
        centerControl = controlView.findViewById(R.id.center_control);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);

        exoGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mcontext.finish();
            }
        });

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                centerControl.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.GONE);
                player.seekTo(0);
                player.setPlayWhenReady(true);
            }
        });

//        initFullscreenButton();
    }


    private void openFullscreenDialog() {
        ((ViewGroup) playerView.getParent()).removeView(playerView);
        mFullScreenDialog.addContentView(playerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(mcontext, R.drawable.ic_arrow_collapse));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }

    /**
     * Close player full screen
     */
    private void closeFullscreenDialog() {
//        ((ViewGroup) playerView.getParent()).removeView(playerView);
//        ((FrameLayout) findViewById(R.id.root)).addView(playerView);
//        mExoPlayerFullscreen = false;
//        mFullScreenDialog.dismiss();
//        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(mcontext, R.drawable.ic_arrow_expand));
    }

    private void initFullscreenDialog() {
        mFullScreenDialog = new Dialog(mcontext, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            @Override
            public void onBackPressed() {
                if (mExoPlayerFullscreen) {
                    setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    closeFullscreenDialog();
                }
            }
        };
    }

    private void setScreenOrientation(int screenOrientationPortrait) {
//        mcontext.setRequestedOrientation(screenOrientationPortrait);
    }

    private void releasePlayer() {
        if (player != null) {
            updateTrackSelectorParameters();
            updateStartPosition();
            player.release();
            player = null;
            mediaSource = null;
            trackSelector = null;
        }
    }


    private void updateTrackSelectorParameters() {
        if (trackSelector != null) {
            trackSelectorParameters = trackSelector.getParameters();
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        @C.ContentType int type = Util.inferContentType(uri, null);
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_SS:
                return new SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            default:
                throw new IllegalStateException("Unsupported type: " + type);
        }
    }


    private DataSource.Factory buildDataSourceFactory() {
        return ((DemoApplication) mcontext.getApplicationContext()).buildDataSourceFactory();
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

    private void updateStartPosition() {
        if (player != null) {
            startAutoPlay = player.getPlayWhenReady();
            startWindow = player.getCurrentWindowIndex();
            startPosition = Math.max(0, player.getContentPosition());
        }
    }

    private void clearStartPosition() {
        startAutoPlay = true;
        startWindow = C.INDEX_UNSET;
        startPosition = C.TIME_UNSET;
    }


 public void closeexoplayer(){
     playerView.setVisibility(View.GONE);
     webView.setVisibility(View.GONE);
     view_mime.setVisibility(View.GONE);
     if (player != null) {
        playerView.invalidate();
        player.release();
        player = null;
    }
}

    public void callweb(){
        view_mime.setVisibility(View.GONE);
        playerView.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAppCachePath(mcontext.getCacheDir().getPath());
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient());
        getWebview(Card_url);
    }

    public void callgif(){
        view_mime.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        playerView.setVisibility(View.GONE);
        ImageView imageView = new ImageView(mcontext);
        RelativeLayout.LayoutParams vp =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(vp);
        Glide
                .with(mcontext)
                .load(Card_url.replaceAll(" ", "%20"))
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .thumbnail(0.05f)
                .centerCrop()
                .into(imageView);
        view_mime.addView(imageView);
    }

    public void callimage(){
        view_mime.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        playerView.setVisibility(View.GONE);
        ImageView imageView = new ImageView(mcontext);
        RelativeLayout.LayoutParams vp =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(vp);
        Glide
                .with(mcontext)
                .load(Card_url)
                .thumbnail(0.05f)
                .centerCrop()
                .into(imageView);
        view_mime.addView(imageView);

    }
}
