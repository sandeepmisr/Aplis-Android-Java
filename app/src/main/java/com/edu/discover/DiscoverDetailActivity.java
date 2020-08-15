package com.edu.discover;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import android.app.Activity;

import com.danikula.videocache.HttpProxyCacheServer;
import com.edu.book.CourseDetail;
import com.edu.aplis.DemoApplication;
import com.edu.aplis.R;
import com.edu.preference.PrefrenceUtils;
import com.edu.search.Search_Booklists;
import com.edu.web.ViewActivity;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
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
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
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
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.upstream.crypto.AesCipherDataSink;
import com.google.android.exoplayer2.upstream.crypto.AesCipherDataSource;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
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

import static com.google.android.exoplayer2.util.Util.getUtf8Bytes;

public class DiscoverDetailActivity extends Activity implements View.OnClickListener, PlaybackPreparer, PlayerControlView.VisibilityListener, ResponceQueues {
    private static final String KEY_TRACK_SELECTOR_PARAMETERS = "track_selector_parameters";
    private static final String KEY_WINDOW = "window";
    private static final String KEY_POSITION = "position";
    private static final String KEY_AUTO_PLAY = "auto_play";

    private static final CookieManager DEFAULT_COOKIE_MANAGER;

    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    private PlayerView playerView;

    private DataSource.Factory dataSourceFactory;
    private SimpleExoPlayer player;
    private MediaSource mediaSource;
    private DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    private TrackGroupArray lastSeenTrackGroupArray;

    private boolean startAutoPlay;
    private int startWindow;
    private long startPosition;
    private CoordinatorLayout controlView;
    private ImageButton retryButton;
    private LinearLayout centerControl;
    private boolean mExoPlayerFullscreen = false;
    private Dialog mFullScreenDialog;
    private ImageView mFullScreenIcon;
    Context context;
    LinearLayout view;
    TextView tittle;
    WebView text_subdetail;
    String strimage_Ar;
    String discover_id;
    String content;
    ImageView image_Ar;

    ArrayList<Books> booksserieslist;
    Books books;
    RecyclerView recyclerView;
    DiscoverBooksAdapter discoverBooksAdapter;
    LinearLayout ll_scrollbookseries;
    View rootView;
    WebView webView;
    SimpleCache simpleCache = null;
    String content_url="";
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSourceFactory = buildDataSourceFactory();
        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }
        setContentView(R.layout.discover_detail);
        context = DiscoverDetailActivity.this;


//        Intent i=new Intent();
//        i.putExtra("methodName","myMethod");//goes to previous INtent
//        setIntent(i);

//        Log.e("TAGNULL",simpleCache+"");
//        if (simpleCache != null){
//            dataSourceFactory = new CacheDataSourceFactory(context,
//                    100 * 1024 * 1024, 5 * 1024 * 1024);
//        mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
//                .createMediaSource(Uri.parse(content));
//        player.prepare(mediaSource);
//    }

        booksserieslist = new ArrayList<>();


         rootView = findViewById(R.id.root);
        webView = findViewById(R.id.webView);
        rootView.setOnClickListener(this);
        playerView = findViewById(R.id.player_view);
        recyclerView = findViewById(R.id.horizontalbookseries);
        progressBar = findViewById(R.id.progressBar);
        playerView.setControllerVisibilityListener(this);
        playerView.setErrorMessageProvider(new DiscoverDetailActivity.PlayerErrorMessageProvider());
        playerView.requestFocus();

        if (savedInstanceState != null) {
            trackSelectorParameters = savedInstanceState.getParcelable(KEY_TRACK_SELECTOR_PARAMETERS);
            startAutoPlay = savedInstanceState.getBoolean(KEY_AUTO_PLAY);
            startWindow = savedInstanceState.getInt(KEY_WINDOW);
            startPosition = savedInstanceState.getLong(KEY_POSITION);
        } else {
            trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();
            clearStartPosition();
        }

        callanalytics();

        view = findViewById(R.id.view);
        tittle = findViewById(R.id.tittle);
        text_subdetail = findViewById(R.id.text_subdetail);
        image_Ar = findViewById(R.id.image_Ar);
//        ll_scrollbookseries = findViewById(R.id.ll_scrollbookseries);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        Intent intent =  getIntent();
       String mimetype = intent.getStringExtra("mimetype");
       content  = intent.getStringExtra("content");
       String title  = intent.getStringExtra("title");
       String long_desc  = intent.getStringExtra("long_desc");
       strimage_Ar  = intent.getStringExtra("image_Ar");
       discover_id  = intent.getStringExtra("discover_id");

       try {
           if (!strimage_Ar.equalsIgnoreCase("")) {
               image_Ar.setVisibility(View.VISIBLE);
           }

           if (strimage_Ar.equalsIgnoreCase("null")) {
               image_Ar.setVisibility(View.GONE);
           }

           if (strimage_Ar == null) {
               image_Ar.setVisibility(View.GONE);

           }
       }
       catch (Exception e){
           image_Ar.setVisibility(View.GONE);

       }

    if (!title.equalsIgnoreCase("")){
        tittle.setVisibility(View.VISIBLE);
        }

     if (title.equalsIgnoreCase("null")){
         tittle.setVisibility(View.GONE);
    }

    if (title== null){
        tittle.setVisibility(View.GONE);
    }

        Log.e("TAGNULL","msg1"+content+ "  "+mimetype);
        getDataFromServer();
        Log.e("ar_url","url"+strimage_Ar);


        tittle.setText(title);
        String html = "<html><style type=\"text/css\"> @font-face {font-family: MyFont;src: url('file:///android_asset/fonts/playfairdisplayregular.ttf'); font-weight: normal; font-style: normal} @font-face {font-family: MyFontBOLD;src: url('file:///android_asset/fonts/playfairdisplaybold.ttf'); font-weight: bold; font-style: normal} @font-face {font-family: MyFontITALIC;src: url('file:///android_asset/fonts/playfairdisplayitalic.ttf'); font-weight: normal; font-style: italic}"+
                "h1{\n" +
                "font-family: MyFont;"+
                "font-size: 18px;\n" +
                "}\n" +
                "\n" +
                "h2{\n" +
                "font-family: MyFont;"+
                "  font-size: 16px;\n" +
                "}\n" +
                "p {\n" +
                "font-family: MyFont;"+
                "  font-size: 14px;\n" +
                "}\n" +
                "body {\n" +
                "font-family: MyFont;"+
                "  background-color: white;\n" +
                "  color: black;\n" +
                "}\n" +
                "</style> \n <body>"+long_desc+"</body></html>";
        final String mimeType = "text/html";
        final String encoding = "UTF-8";

        text_subdetail.loadDataWithBaseURL("", html, mimeType, encoding, "");

        text_subdetail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

//        ArrayList<Books> listbooks = new ArrayList<Books>();
//        listbooks = (ArrayList<Books>) getIntent().getSerializableExtra("list");

//        Log.e("LISTDETAIL",)
       if (mimetype.equalsIgnoreCase("image")){
           ImageView imageView = new ImageView(context);
           imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

           imageView.setTransitionName("imageMain");
//           imageView.load(url);
           Glide.with(context)
                    .load(content)
                    .centerCrop()
                    .into(imageView);
           view.addView(imageView);


       }
       else if (mimetype.equalsIgnoreCase("video")){
           rootView.setVisibility(View.VISIBLE);
           view.setVisibility(View.GONE);
//           if (Util.SDK_INT > 23) {
//               initializePlayer();
//               if (playerView != null) {
//                   playerView.xonResume();
//               }
//           }

//           startActivity(new Intent(context, MainVideoPlayer.class));
//           playVideo("https://www.radiantmediaplayer.com/media/bbb-360p.mp4"
//                   .replaceAll("[\\t\\n\\r]", ""));
//           final VideoView videoView = new VideoView(context);
//           MediaController mc = new MediaController(context);
//           videoView.setMediaController(mc);
//
//           videoView.setVideoURI(Uri.parse(content));
//           videoView.start();

//           LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//           lp.gravity = Gravity.BOTTOM;
//           mc.setLayoutParams(lp);
//
//           ((ViewGroup) mc.getParent()).removeView(mc);
//
////           view.addView(mc);
//           videoViewva.requestFocus();
//           videoViewva.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//               // Close the progress bar and play the video
//               public void onPrepared(MediaPlayer mp) {
////                   pDialog.dismiss();
//                   videoViewva.start();
////                   hideUI();
//               }
//           });
//           videoViewva.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//               // Close the progress bar and play the video
//               public boolean onError(MediaPlayer mp, int x, int y) {
//                   // if (y == MediaPlayer.MEDIA_ERROR_UNSUPPORTED) {
////                   pDialog.dismiss();
//                   // }
//                   return false;
//               }
//           });
//
//           videoViewva.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//               @Override
//               public void onFocusChange(View v, boolean hasFocus) {
//
//                   if (hasFocus) {
////                       hideUI();
//                   }
//               }
//           });
//        view.addView(videoView);


       }else if (mimetype.equalsIgnoreCase("gif")){
           ImageView imageView = new ImageView(context);
           imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
           Glide.with(context)
                   .load(content)
                   .asGif()
                   .into(imageView);
           view.addView(imageView);


       }else if (mimetype.equalsIgnoreCase("web")){
           view.setVisibility(View.GONE);
           webView.setVisibility(View.VISIBLE);
//           WebView webView = new WebView(context);
//           webView.getSettings().setJavaScriptEnabled(true);
////           webView.getSettings().getAllowFileAccess();
//          webView.loadUrl("https://www.google.com");

//           view.addView(webView);
           loadwebdata(content);

       }
    }

    @Override
    public void onNewIntent(Intent intent) {
        view.setVisibility(View.VISIBLE);
        rootView.setVisibility(View.GONE);

        rootView.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);
        content_url = content;
        if (content.equalsIgnoreCase(getIntent().getStringExtra("content"))){


        }
        else{
//            releasePlayer();
//          clearStartPosition();
        }
//        startActivity(new Intent(this, DiscoverDetailActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        Log.e("TAGNULL","msg"+getIntent().getStringExtra("content")+" "+content_url);

//        if (getIntent().getStringExtra("discover_id").equalsIgnoreCase(PrefrenceUtils.readString(context,PrefrenceUtils.PREF_COUNTRY,""))) {
////        final Uri uri = getIntent().getData();
////        releasePlayer();
////        clearStartPosition();
//            setIntent(intent);
//        }
//        else{
//            releasePlayer();
//          clearStartPosition();
//            setIntent(intent);
//        }
    }

    private void getDataFromServer() {
        progressBar.setVisibility(View.VISIBLE);
        trustEveryone();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.clear();
        Log.e("TAGERROR",discover_id);
        ApiService apiService = new ApiService(context,this, Cons.GET_DISCOVER_URLDETAILS+discover_id,hashMap,1);
        apiService.execute();
    }

    private void adddiscoverbooks(final ArrayList<Books> bookserieslist) {

        {
            for (int i = 0; i < bookserieslist.size(); i++) {
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.bookseries, null);
                ImageView imageView = view.findViewById(R.id.image);
                Button btn_view = view.findViewById(R.id.btn_view);
                TextView text_name = view.findViewById(R.id.text_name);

                text_name.setText(bookserieslist.get(i).getBook_name());
                Log.e("TAGERRORBOOK","list+"+bookserieslist.get(i).getBook_cover());
                Glide
                        .with(context)
                        .load(bookserieslist.get(i).getBook_cover())
                        .centerCrop()
                        .into(imageView);


                ll_scrollbookseries.addView(view);

                final int finalI = i;
                btn_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(DiscoverDetailActivity.this,view,"cover_image");

                        startActivity(new Intent(context, CourseDetail.class).putExtra("book_id",bookserieslist.get(finalI).getId()).putExtra("cover_image",bookserieslist.get(finalI).getBook_cover()),activityOptionsCompat.toBundle());
                    }
                });
            }

        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }

        if (isInPictureInPictureMode()){


        }


    }



    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length == 0) {
            return;
        }
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initializePlayer();
        } else {
            showToast("permission denied");
            finish();
        }
    }

    // Activity input

    @Override
    public void onSaveInstanceState(Bundle outState) {
        updateTrackSelectorParameters();
        updateStartPosition();
        outState.putParcelable(KEY_TRACK_SELECTOR_PARAMETERS, trackSelectorParameters);
        outState.putBoolean(KEY_AUTO_PLAY, startAutoPlay);
        outState.putInt(KEY_WINDOW, startWindow);
        outState.putLong(KEY_POSITION, startPosition);
    }

    // OnClickListener methods

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // See whether the player view wants to handle media or DPAD keys events.
        return playerView.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
    }

    // PlaybackControlView.PlaybackPreparer implementation

    @Override
    public void onClick(View view) {
        //System generated method
    }

    // PlaybackControlView.VisibilityListener implementation

    @Override
    public void preparePlayback() {
        initializePlayer();
    }

    private void initializePlayer() {
        if (player == null) {

            Uri uri = Uri.parse(content);

            if (!Util.checkCleartextTrafficPermitted(uri)) {
                showToast("error");
                return;
            }
            if (Util.maybeRequestReadExternalStoragePermission(/* activity= */ this, uri)) {
                // The player will be reinitialized if the permission is granted.
                return;
            }

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

            TrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this);
            trackSelector = new DefaultTrackSelector(trackSelectionFactory);
            trackSelector.setParameters(trackSelectorParameters);
            lastSeenTrackGroupArray = null;
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(this, renderersFactory, trackSelector, loadControl);


            HttpProxyCacheServer proxyServer = new HttpProxyCacheServer.Builder(context).maxCacheSize(1024 * 1024 * 1024).build();
            String proxyURL = proxyServer.getProxyUrl(content);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, getApplicationContext().getPackageName()));
            player.addListener(new DiscoverDetailActivity.PlayerEventListener());


            player.setPlayWhenReady(startAutoPlay);

//            player.prepare(audioSource);
            playerView.setPlayer(player);
            playerView.setPlaybackPreparer(this);
            mediaSource = buildMediaSource(uri);
            playerView.buildDrawingCache();
//            HttpProxyCacheServer proxy = getProxy();
//            String proxyUrl = proxy.getProxyUrl(new URL(uri.toString()));
//            playerView.setVideoPath(proxyUrl);

        }
        boolean haveStartPosition = startWindow != C.INDEX_UNSET;
        if (haveStartPosition) {
            player.seekTo(startWindow, startPosition);
        }
        initCustomController();
        initFullscreenDialog();

        MediaSource audioSource = new ExtractorMediaSource(Uri.parse(content),
                new CacheDataSourceFactory(context, 100 * 1024 * 1024, 5 * 1024 * 1024), new DefaultExtractorsFactory(), null, null);
//        getDownloadCache().release();

//        audioSource.
        player.prepare(audioSource, !haveStartPosition, false);

//        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
//                Util.getUserAgent(context, getPackageName()));
//
//
//        exoPlayer.prepare(new ProgressiveMediaSource.Factory(dataSourceFactory)
//                .createMediaSource(Uri.parse(proxyURL)););
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
                finish();
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

        initFullscreenButton();
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

    /**
     * Returns a new DataSource factory.
     */
    private DataSource.Factory buildDataSourceFactory() {
        return ((DemoApplication) getApplication()).buildDataSourceFactory();
    }

    private void showToast(int messageId) {
        showToast(getString(messageId));
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onVisibilityChange(int visibility) {
        //System generated method
    }

    /**
     * Initialize player full screen
     */
    private void initFullscreenDialog() {
        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            @Override
            public void onBackPressed() {
                if (mExoPlayerFullscreen) {
                    setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    closeFullscreenDialog();
                }
            }
        };
    }

    /**
     * Open player full screen
     */
    private void openFullscreenDialog() {
        ((ViewGroup) playerView.getParent()).removeView(playerView);
        mFullScreenDialog.addContentView(playerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_collapse));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }

    /**
     * Close player full screen
     */
    private void closeFullscreenDialog() {
        ((ViewGroup) playerView.getParent()).removeView(playerView);
        ((FrameLayout) findViewById(R.id.root)).addView(playerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_expand));
    }

    /**
     * Full screen button click listener
     */
    private void initFullscreenButton() {
        RelativeLayout mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen) {
                    setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    openFullscreenDialog();
                } else {
                    setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    closeFullscreenDialog();
                }
            }
        });
    }

    private void setScreenOrientation(int screenOrientationPortrait) {
        setRequestedOrientation(screenOrientationPortrait);
    }

    @Override
    public void responceQue(String responce, String url, String extra_text) {
        progressBar.setVisibility(View.GONE);

        try{
            JSONObject jsonObject = new JSONObject(responce);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonObject1.getJSONArray("books");
            for (int i = 0;i<jsonArray.length();i++){
                books =new Books();
                books.setId(jsonArray.getJSONObject(i).getString("id"));
                books.setBook_name(jsonArray.getJSONObject(i).getString("title"));
                books.setbook_cover(jsonArray.getJSONObject(i).getString("book_cover"));
                booksserieslist.add(books);

            }
            discoverBooksAdapter = new DiscoverBooksAdapter(context,booksserieslist);
            recyclerView.setAdapter(discoverBooksAdapter);

//            adddiscoverbooks(booksserieslist);
        }
        catch (Exception e){

        }
    Log.e("TAGERROR","response:+"+responce);
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

    private class PlayerErrorMessageProvider implements ErrorMessageProvider<ExoPlaybackException> {

        @Override
        public Pair<Integer, String> getErrorMessage(ExoPlaybackException e) {
            String errorString ="generic error";
            if (e.type == ExoPlaybackException.TYPE_RENDERER) {
                Exception cause = e.getRendererException();
                if (cause instanceof MediaCodecRenderer.DecoderInitializationException) {
                    // Special case for decoder initialization failures.
                    MediaCodecRenderer.DecoderInitializationException decoderInitializationException =
                            (MediaCodecRenderer.DecoderInitializationException) cause;
                    if (decoderInitializationException.decoderName == null) {
                        if (decoderInitializationException.getCause() instanceof MediaCodecUtil.DecoderQueryException) {
                            errorString = "error decoders";
                        } else if (decoderInitializationException.secureDecoderRequired) {
                            errorString =
                                    "error decoders" +decoderInitializationException.mimeType;
                        } else {
                            errorString =
                                    "error decoders"+decoderInitializationException.mimeType;
                        }
                    } else {
                        errorString =
                                "error decoders"+
                                        decoderInitializationException.decoderName;
                    }
                }
            }
            return Pair.create(0, errorString);
        }
    }

//    private HttpProxyCacheServer getProxy() {
//         should return single instance of HttpProxyCacheServer shared for whole app.
//    }

    public void imagearclick(View view) {
        openAR();
    }
    private void openAR(){
        try {
//        "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Avocado/glTF/Avocado.gltf"
            Intent sceneViewerIntent = new Intent(Intent.ACTION_VIEW);
            sceneViewerIntent.setData(Uri.parse("https://arvr.google.com/scene-viewer/1.1?file="+strimage_Ar));
            sceneViewerIntent.setPackage("com.google.android.googlequicksearchbox");
            startActivity(sceneViewerIntent);
        }
        catch (Exception e){
            Toast.makeText(context,"No Apps to perform this action"+ e+"",Toast.LENGTH_LONG).show();
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

//    @Override
//    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
//        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
//        if (!isInPictureInPictureMode){
//            Intent intent=(new Intent(DiscoverDetailActivity.this,DiscoverDetailActivity.class));
//            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//            startActivity(intent);
//        }
//    }

//    @Override
//    public void onBackPressed() {
//        Intent intent1 = new Intent();
//        intent1.putExtra("content_url",discover_id);
//        PrefrenceUtils.writeString(context,PrefrenceUtils.PREF_COUNTRY,discover_id);
//        super.onBackPressed();
//       enterPictureInPictureMode();
//    }

    @Override
    public void onUserLeaveHint () {
//            enterPictureInPictureMode();
        }


    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }});
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }


    private void loadwebdata(final String url){

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getPath());
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        //URL gir "....."
        getWebview(url);
//        webView.setWebViewClient(new MyWebViewClient());


        Log.e("clickver","one"+url);


    }


    public void getWebview(String url)
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
                Log.e("clickver","one"+error);

            }
        });
//        prg = ProgressDialog.show(ViewActivity.this, "Please wait", "Processing...", true);
//        prg.setCancelable(true);
        webView.loadUrl(url);



    }
    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }


}
