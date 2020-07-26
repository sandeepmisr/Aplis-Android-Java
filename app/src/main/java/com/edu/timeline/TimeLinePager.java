package com.edu.timeline;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.edu.aplis.R;
import com.edu.editortemplatetwo.PlayerListener;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
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
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;

import java.io.File;

import at.blogc.android.views.ExpandableTextView;

public class TimeLinePager extends Fragment implements Player.EventListener {

    private static PlayerListener playerListenernew;
    private static ClickTImeline clickTImelineentry;
    private String title;
    private int page;
    static Context mcontext;
    SimpleExoPlayer simpleExoPlayer= null;
    SimpleCache simpleCache = null;
    private int startWindow;
    private long startPosition;
    String Card_url;
    SimpleExoPlayerView player;
    private boolean startAutoPlay;

    private static final String TAG = "ExoPlayerActivity";
    private static final String KEY_VIDEO_URI = "video_uri";

    public static TimeLinePager newInstance(String title, String subheading, String longtext,String mimetype, String Card_url, String Ar_url, Context context
    , PlayerListener playerListener, int position,ClickTImeline clickTImeline) {
        TimeLinePager fragment = new TimeLinePager();
        mcontext=context;
        playerListenernew=playerListener;
        clickTImelineentry=clickTImeline;
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("subheading", subheading);
        args.putString("longtext", longtext);
//        args.putString("date", date);
//        args.putString("time", time);
//        args.putString("year", year);
        args.putString("mimetype", mimetype);
        args.putString("Card_url", Card_url);
        args.putString("Ar_url", Ar_url);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    public TimeLinePager() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String title = this.getArguments().getString("title");
        String subheading = this.getArguments().getString("subheading");
        String longtext = this.getArguments().getString("longtext");
        String mimetype = this.getArguments().getString("mimetype");
        Card_url = this.getArguments().getString("Card_url");
        final String Ar_url = this.getArguments().getString("Ar_url");
        Integer position = this.getArguments().getInt("position");
        View itemView = inflater.inflate(R.layout.timelineapager_adapater, container, false);

//        View view = EditorSecondSub_subActivity.recyclerViewvpager.getChildAt(EditorSecondSub_subActivity.recyclerViewvpager.getCurrentItem()).getRootView();
//        TextView tittle = itemView.findViewById(R.id.heading);
//        TextView text_body = itemView.findViewById(R.id.subheading);
//        final ExpandableTextView text_longdescription = itemView.findViewById(R.id.longtext);
//        final ImageView image_visualaize=itemView.findViewById(R.id.image_visualaize);
//        final CardView cardimage_visualaize=itemView.findViewById(R.id.cardimage_visualaize);
//        ImageView image_Ar=itemView.findViewById(R.id.image_Ar);
//        CardView cardimage_Ar=itemView.findViewById(R.id.cardimage_Ar);
        LinearLayout view_mime = itemView.findViewById(R.id.mimeview);
//        final LinearLayout viewpagertopiclayer = itemView.findViewById(R.id.viewpagertopiclayer);
        WebView webView = itemView.findViewById(R.id.webView);
         player = itemView.findViewById(R.id.videoFullScreenPlayer);

//        tittle.setText(title);
//        text_body.setText(subheading);
//
//
//        text_longdescription.setText(Html.fromHtml(longtext));
//        text_longdescription.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(final View v)
//            {
//                if (text_longdescription.isExpanded())
//                {
//                    text_longdescription.collapse();
//                }
//                else
//                {
//                    text_longdescription.expand();
//                }
//            }
//        });



        player.setVisibility(View.GONE);


//        view_mime.setVisibility(View.VISIBLE);
//        webView.setVisibility(View.GONE);

        Log.e("TAGADAPTERinsta",mimetype+ " ");
        if (mimetype.equalsIgnoreCase("image")){
            ImageView imageView = new ImageView(mcontext);
            RelativeLayout.LayoutParams vp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(vp);
            Glide
                    .with(mcontext)
                    .load(Card_url)
                    .into(imageView);
            view_mime.addView(imageView);

        }

        else if (mimetype.equalsIgnoreCase("web")){
            view_mime.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setAppCacheEnabled(true);
            webView.getSettings().setAppCachePath(mcontext.getCacheDir().getPath());
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            webView.setWebViewClient(new WebViewClient());

            //URL gir "....."
            getWebview(Card_url,webView);

//            WebView webView = new WebView(mcontext);
//            webView.setWebViewClient(new WebViewClient());
//            WebSettings webSettings = webView.getSettings();
//            webSettings.setAllowFileAccess(true);
//            webSettings.setJavaScriptEnabled(true);
//            webSettings.setSupportZoom(true);
//            webSettings.setBuiltInZoomControls(true);
//            webSettings.setDisplayZoomControls(false);
//            webView.loadDataWithBaseURL(null, Card_url, "text/html", "UTF-8", null);

//            view_mime.addView(webView);
        }

        else if (mimetype.equalsIgnoreCase("gif")){
            ImageView imageView = new ImageView(mcontext);
            RelativeLayout.LayoutParams vp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(vp);
            Glide
                    .with(mcontext)
                    .load(Card_url)
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .thumbnail(0.05f)
                    .centerCrop()
                    .into(imageView);
            view_mime.addView(imageView);

        }

        else if (mimetype.equalsIgnoreCase("video")){
//            VideoView videoView = new VideoView(mcontext);
//            RelativeLayout.LayoutParams vp =
//                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
//                            RelativeLayout.LayoutParams.MATCH_PARENT);
//            videoView.setLayoutParams(vp);
//            videoView.setVideoURI(Uri.parse(Card_url));
//            videoView.start();
            view_mime.setVisibility(View.GONE);
            webView.setVisibility(View.GONE);
//            player.setVisibility(View.VISIBLE);
//            playerListenernew.clickonplay(Card_url,player);
//            initializePlayer(Card_url,player);

            Log.e("getItme",player.getVisibility()+"");
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





    public void getWebview(String myurl,WebView webView)
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

                view.getSettings().setJavaScriptEnabled(true);




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


    public void initializePlayer(){
        player.setVisibility(View.VISIBLE);

        // Create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        //Initialize the player
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(mcontext, trackSelector);

        //Initialize simpleExoPlayerView
        player.setPlayer(simpleExoPlayer);

        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(mcontext, Util.getUserAgent(mcontext, "Aplinew"));

        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        // This is the MediaSource representing the media to be played.
        Uri videoUri = Uri.parse(Card_url);
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
        MediaSource audioSource = new ExtractorMediaSource(Uri.parse(Card_url),
                new CacheDataSourceFactory(mcontext, 100 * 1024 * 1024, 5 * 1024 * 1024), new DefaultExtractorsFactory(), null, null);



//        getDownloadCache().release();

//        audioSource.
        simpleExoPlayer.prepare(audioSource, !haveStartPosition, false);


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
        if (simpleExoPlayer != null) {
            startAutoPlay = simpleExoPlayer.getPlayWhenReady();
            startWindow = simpleExoPlayer.getCurrentWindowIndex();
            startPosition = Math.max(0, simpleExoPlayer.getContentPosition());
        }
    }

    private void clearStartPosition() {
        startAutoPlay = true;
        startWindow = C.INDEX_UNSET;
        startPosition = C.TIME_UNSET;
    }


public void closeexoplayer(){
        Log.e("exoplayer","check"+simpleExoPlayer+"");
    if (simpleExoPlayer != null) {
        player.invalidate();
        simpleExoPlayer.release();
        simpleExoPlayer = null;
    }
}



}
