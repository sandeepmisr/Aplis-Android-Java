package com.edu.editortemplate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.danikula.videocache.HttpProxyCacheServer;
import com.edu.aplis.DemoApplication;
import com.edu.aplis.R;
import com.edu.discover.Books;
import com.edu.discover.ClickAdapter;
import com.edu.discover.DiscoverDetailActivity;
import com.edu.discover.DiscoverModel;
import com.edu.viewholderadapter.VideoPlayerViewHolder;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
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
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
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
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditorActivity extends AppCompatActivity implements ResponceQueues,ClickAdapter {
    VerticalViewPager recyclerView;
    HashMap<String,String> hashMap=new HashMap<>();
    Context context;
    private  ArrayList<DiscoverModel> discoverModelArrayList;
    EditoroneAdapter editoroneAdapter;
    String book_Id= "";
    RelativeLayout parent;
    VideoView videoView;
    private PlayerView videoSurfaceView;
    private SimpleExoPlayer videoPlayer;
    private FrameLayout frameLayout;
    private int playPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        context = EditorActivity.this;

        recyclerView = findViewById(R.id.recyclerview);
        parent = findViewById(R.id.parent);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//        recyclerView.setLayoutManager(linearLayoutManager);
        discoverModelArrayList = new ArrayList<DiscoverModel>();

        book_Id = getIntent().getStringExtra("book_id");






        Log.e("TAGEDITOR",book_Id);
        getDataFromServer();

//        recyclerView.addOnScrollListener(new VerticalPager.OnScrollListener() {
//            @Override
//            public void onScroll(int scrollX) {
//
//            }
//
//            @Override
//            public void onViewScrollFinished(int currentPage) {
//                try {
//                    try {
//                        videoPlayer.release();
//                    } catch (Exception e) {
//
//                    }
//
//
////                    Log.e("VIEWVIDEO",view+"");
//
//                    if (discoverModelArrayList.get(currentPage).getMime_type().equalsIgnoreCase("video")) {
////                        View viewTag = recyclerView.findViewWithTag("view" + recyclerView.getCurrentPage());
////                        Log.e("VIEWVIDEORESUME",viewTag+"");
////
////                        VideoView videoViewTag = (VideoView)viewTag.findViewById(R.id.video);
//
////                        View view = recyclerView.getChildAt(currentPage);
////                        VideoView videoView =  view.findViewById(R.id.video);
////                        Log.e("VIEWVIDEO",view+"");
////
//                        playvideo(videoView,Uri.parse(discoverModelArrayList.get(currentPage).getCard_url()));
////                        onBind(discoverModelArrayList.get(currentPage).getCard_url(), context);
//                    }
//                }
//                catch (Exception e){
//                    Log.e("VIEWVIDEO",e+"");
//
//                }
//            Log.e("RESUME",currentPage+"");
//            }
//        });
    }

    private void playvideo(final VideoView videoView,Uri uri) {
//        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                videoView.start();
            }
        });
    }


    private void getDataFromServer() {
        hashMap.clear();
        Log.e("TAGEDITOR",Cons.GET_CHAPTERBYBOOKID+book_Id);
        ApiService apiService = new ApiService(context,this, Cons.GET_CHAPTERBYBOOKID+book_Id,hashMap,1);
        apiService.execute();
    }

    @Override
    public void responceQue(String responce, String url, String extra_text) {
        discoverModelArrayList.clear();
        Log.e("Response===",url+responce);

        try {
            JSONObject jsonObject = new JSONObject(responce);
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("data");
            Log.e("Response===",jsonArray.length()+"");

            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObjectseries=jsonArray.getJSONObject(i);
                DiscoverModel discoverModel = new DiscoverModel();
                String id = jsonObjectseries.getString("id");
                String title = jsonObjectseries.getString("heading");
                String sub_heading = jsonObjectseries.getString("sub_heading");
                String mime_type = jsonObjectseries.getString("mime_type");
                String card_url = jsonObjectseries.getString("image_url");
                String long_description = jsonObjectseries.getString("html_content");


                if (title==null||title.equalsIgnoreCase("null")){
                    title = "";
                }


                discoverModel.setId(id);
                discoverModel.setTitle(title);
                discoverModel.setStatus(sub_heading);
                discoverModel.setMime_type(mime_type);
                discoverModel.setCard_url(card_url);
                discoverModel.setLong_description(long_description);
                discoverModelArrayList.add(discoverModel);


            }

//            editoroneAdapter = new EditoroneAdapter(context,discoverModelArrayList,this);
//            inflateList(discoverModelArrayList);



        } catch (JSONException e) {
            Log.e("JSONEXCEPTION",e+"");
            e.printStackTrace();
        }
        setPagerAdapter();

        recyclerView.setOnTouchListener(new OnSwipeTouchListener(context) {

            public void onSwipeLeft() {
                startActivity(new Intent(context,EditorSubActivity.class).putExtra("chapter_id",discoverModelArrayList.get(recyclerView.getCurrentItem()).getId()));
//                    Toast.makeText(EditorActivity.this, finalI+ " "+discoverModelArrayList.get(recyclerView.getCurrentPage()).getId(), Toast.LENGTH_SHORT).show();
            }

        });



//        recyclerView.getCurrentPage()

        // try
        /*{
            JSONObject jsonObject = new JSONObject(responce);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            String title = jsonObject1.getString("title");
            String sub_title = jsonObject1.getString("sub_title");
            String description = jsonObject1.getString("description");
            String book_cover = jsonObject1.getString("book_cover");
            String coverdata = jsonObject1.getString("cover");
            String isbn_code = jsonObject1.getString("isbn_code");
            String published_at = jsonObject1.getString("published_at");
            String created_at = jsonObject1.getString("created_at");
            String tags = jsonObject1.getString("tags");
            String min_age = jsonObject1.getString("min_age");
            String max_age = jsonObject1.getString("max_age");
//            String published_a = jsonObject1.getString("published_a");
            String language = jsonObject1.getString("language");

            JSONObject jsonObject3 = jsonObject1.getJSONObject("category");
            String category_name = jsonObject3.getString("name");
            try {
                JSONObject jsonObject2 = jsonObject1.getJSONObject("author");
                String authorDescription = jsonObject2.getString("description");
                String author_pic = jsonObject2.getString("profile_pic");
                String status = jsonObject2.getString("status");


                Log.e("Jsondata==",authorDescription);

            }
            catch (Exception e){

            }

            try{
                JSONArray jsonArray  = jsonObject1.getJSONArray("other_books");
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObjectseries=jsonArray.getJSONObject(i);
                    String series_image = jsonObjectseries.getString("book_cover");

                }
                Log.e("Jsondata==",serieslist.size()+" "+serieslist);

                addarrivals(serieslist);

            }
            catch (Exception e){
                Log.e("Jsondata==",e+"");

            }

            text_pubdate.setText(created_at);
            text_agerange.setText(min_age+"-"+max_age);
            text_categories.setText(category_name);
            text_pubname.setText(language);
            text_publang.setText(language);
//            text_subdetail.setWebViewClient(new WebViewClient());

            Resources res = getResources();
            float fontSize = res.getDimension(R.dimen.dp240);
            WebSettings webSettings = text_subdetail.getSettings();
//            webSettings.setAllowFileAccess(true);
//            webSettings.setJavaScriptEnabled(true);
//            webSettings.setSupportZoom(true);
//            webSettings.setBuiltInZoomControls(true);
//            webSettings.setDisplayZoomControls(false);
//            webSettings.setDefaultFontSize((int)fontSize);

            String DES = "<p><i>i am italics</i></p><p><b>i am Bold</b></p><p>I am Regular</p>";
            String html = "<html><style type=\"text/css\"> @font-face {font-family: MyFont;src: url('file:///android_asset/fonts/playfairdisplayregular.ttf'); font-weight: normal; font-style: normal} @font-face {font-family: MyFontBOLD;src: url('file:///android_asset/fonts/playfairdisplaybold.ttf'); font-weight: bold; font-style: normal} @font-face {font-family: MyFontITALIC;src: url('file:///android_asset/fonts/playfairdisplayitalic.ttf'); font-weight: normal; font-style: italic}"+
                    "h1{\n" +
                    "font-family: MyFont;"+
                    "font-size: 40px;\n" +
                    "}\n" +
                    "\n" +
                    "h2{\n" +
                    "font-family: MyFont;"+
                    "  font-size: 25px;\n" +
                    "}\n" +
                    "p {\n" +
                    "font-family: MyFont;"+
                    "  font-size: 16px;\n" +
                    "}\n" +
                    "body {\n" +
                    "font-family:'MyFont';"+
                    "  background-color: white;\n" +
                    "  color: black;\n" +
                    "}\n" +
                    "</style> \n" +
                    "            }\n" +
                    "                <body>"+description+"</body></html>";
            final String mimeType = "text/html";
            final String encoding = "UTF-8";
            String text = "<html><head><style @font-face {\n" +
                    "                    font-family: \"MyFont\";\n" +
                    "                    src: url('file:///android_asset/fonts/BLKCHCRY.TTF');\n" +
                    "                } type=\"text/css\">body{color: #247BA0; background-color: #FFFFFF;}</style></head><body>"+ description + "</body></html>";
//            String szMessage = "<font face='trebuchet' size=30><a href=zz><b>click me</b></a></font>";

//            String html = "<html><body><font face='trebuchet' size=100></font><img src=\"" + description + "\" width=\"100%\" height=\"100%\"\"/></body></html>";
//String ws = "<head><style type=\"text/css\"> html, body { width:100%; height: 100%; margin: 0px; padding: 0px; } </style></head><body>\""+ description + "</body>";
//            webSettings.setUseWideViewPort(true);
//            webSettings.setLoadWithOverviewMode(true);
//            webSettings.setSupportMultipleWindows(false);
//            webSettings.setLoadsImagesAutomatically(true);
//            webSettings.setDomStorageEnabled(true);
//            webSettings.setLoadWithOverviewMode(true);
//            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            String styleString = description +"<body style=\"margin: 0; padding: 0\"><style><h1><font face='trebuchet' size=100></font></h1>img{display: inline;height: auto;max-width: 100%;}</style>";

//            View.setDrawingCacheEnabled(true);

            text_subdetail.loadDataWithBaseURL("", html, mimeType, encoding, "");

            text_title.setText(title);
            text_subname.setText(sub_title);
            text_pubisbn.setText(isbn_code);



            Glide
                    .with(context)
                    .load(coverdata)
                    .centerCrop()
                    .into(cover);

            Glide
                    .with(context)
                    .load(book_cover)
                    .centerCrop()
                    .into(cover_image);

            String[] elements = tags.split(",");

// step two : convert String array to list of String
            List<String> fixedLenghtList = Arrays.asList(elements);

// step three : copy fixed list to an ArrayList
            tagNames = new ArrayList<String>(fixedLenghtList);

//            Log.e("APIRES",tagNames+"");
//            try {
//                for (String s : listOfString) {
//                    tagNames.add(s);
//                }
//            } catch (Exception e) {
//                tagNames.add("");
//            }
            adapter = new TagAdapter(this, tagNames);

            recyclerView.setAdapter(adapter);

        }
        catch (Exception e){
            Log.e("APIRESPONSE",responce+ " "+e+"");

        }*/
    }


    private void setPagerAdapter() {
        try {
//            FadeOutTransformation fadeOutTransformation = new FadeOutTransformation();
            PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());

//            FadeOutTransformation fadeOutTransformation = new FadeOutTransformation();
//            pager.setPageTransformer(true, fadeOutTransformation);


            recyclerView.setAdapter(adapter);
//            pager.setCurrentItem(0);
//            recyclerView.setOffscreenPageLimit(1);
        } catch (Exception e) {

        }
    }



    private class PagerAdapter extends FragmentPagerAdapter {

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
            return PagerFragment1.newInstance(discoverModelArrayList.get(position).getTitle(),discoverModelArrayList.get(position).getStatus()
            ,discoverModelArrayList.get(position).getLong_description(),discoverModelArrayList.get(position).getMime_type()
            ,discoverModelArrayList.get(position).getCard_url(),context);
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

//    private void inflateList(final ArrayList<DiscoverModel> discoverModelArrayList) {
//        recyclerView.removeAllViews();
//        for (int i  =0;i<discoverModelArrayList.size();i++) {
//            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            View itemView = inflater.inflate(R.layout.editorone_adapter, null);
//
//
//            TextView tittle = itemView.findViewById(R.id.heading);
//            TextView text_body = itemView.findViewById(R.id.subheading);
//            TextView text_longdescription = itemView.findViewById(R.id.longtext);
//            LinearLayout view_mime = itemView.findViewById(R.id.mimeview);
////            final VideoView videoView = itemView.findViewById(R.id.video);
//            ImageView videoView_thumbnail = itemView.findViewById(R.id.videoView_thumbnail);
//            RelativeLayout video_frame = itemView.findViewById(R.id.video_frame);
//
//            tittle.setText(discoverModelArrayList.get(i).getTitle());
//            text_body.setText(discoverModelArrayList.get(i).getStatus());
//            text_longdescription.setText(Html.fromHtml(discoverModelArrayList.get(i).getLong_description()));
//
//            if (tittle.equals("")||tittle.equals(null)){
//                tittle.setVisibility(View.GONE);
//            }
//            if (text_body.equals("")||text_body.equals(null)){
//                text_body.setVisibility(View.GONE);
//            }
//            if (text_longdescription.equals("")||text_longdescription.equals(null)){
//                text_longdescription.setVisibility(View.GONE);
//            }
//
//
//            if (discoverModelArrayList.get(i).getMime_type().equalsIgnoreCase("image")){
//                ImageView imageView = new ImageView(context);
//                RelativeLayout.LayoutParams vp =
//                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                                RelativeLayout.LayoutParams.WRAP_CONTENT);
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setLayoutParams(vp);
//                Glide
//                        .with(context)
//                        .load(discoverModelArrayList.get(i).getCard_url())
//                        .thumbnail(0.05f)
//                        .centerCrop()
//                        .into(imageView);
//                view_mime.addView(imageView);
//
//            }
//            else if (discoverModelArrayList.get(i).getMime_type().equalsIgnoreCase("web")){
//                WebView webView = new WebView(context);
//                webView.setWebViewClient(new WebViewClient());
//                webView.loadUrl(discoverModelArrayList.get(i).getCard_url());
//                WebSettings webSettings = webView.getSettings();
//                webSettings.setAllowFileAccess(true);
//                webSettings.setJavaScriptEnabled(true);
//                webSettings.setSupportZoom(true);
//                webSettings.setBuiltInZoomControls(true);
//                webSettings.setDisplayZoomControls(false);
//                view_mime.addView(webView);
//            }
//
//            else if (discoverModelArrayList.get(i).getMime_type().equalsIgnoreCase("gif")){
//                ImageView imageView = new ImageView(context);
//                RelativeLayout.LayoutParams vp =
//                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                                RelativeLayout.LayoutParams.WRAP_CONTENT);
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setLayoutParams(vp);
//                Glide
//                        .with(context)
//                        .load(discoverModelArrayList.get(i).getCard_url())
//                        .asGif()
//                        .thumbnail(0.05f)
//                        .centerCrop()
//                        .into(imageView);
//                view_mime.addView(imageView);
//
//            }
//
//            else if (discoverModelArrayList.get(i).getMime_type().equalsIgnoreCase("video")){
//                 videoView = new VideoView(context);
//                RelativeLayout.LayoutParams vp =
//                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                                RelativeLayout.LayoutParams.WRAP_CONTENT);
////                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                videoView.setLayoutParams(vp);
////               View rootView = itemView.findViewById(R.id.root);
////                rootView.setOnClickListener(this);
////                PlayerView playerView = itemView.findViewById(R.id.player_view);
////                initializePlayer(playerView);
////                playerView.setControllerVisibilityListener(this);
////                playerView.setErrorMessageProvider(new DiscoverDetailActivity.PlayerErrorMessageProvider());
////                playerView.requestFocus();
////
////
////                view_mime.setVisibility(View.GONE);
////                videoView.setVisibility(View.VISIBLE);
//                videoView.setTag("view"+i);
//                videoView.setVideoURI(Uri.parse(discoverModelArrayList.get(i).getCard_url()));
////                VideoPlayerViewHolder.onBind(discoverModelArrayList.get(i).getCard_url(),context);
////                final ImageView imageView = new ImageView(context);
////                RelativeLayout.LayoutParams vp =
////                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
////                                RelativeLayout.LayoutParams.WRAP_CONTENT);
////                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
////
////                imageView.setLayoutParams(vp);
////                imageView.setImageResource(R.drawable.favafter);
////                Glide
////                        .with(context)
////                        .load(discoverModelArrayList.get(i).getCard_url())
////                        .thumbnail(0.05f)
////                        .centerCrop()
////                        .into(imageView);
//
////                videoView.setVideoURI(Uri.parse(discoverModelArrayList.get(i).getCard_url()));
//                final int finalI1 = i;
//
////                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
////                    public void onPrepared(MediaPlayer mp) {
////                        videoView.start();
////                    }
////                });
//                view_mime.addView(videoView);
//
//
//
//            }
//
//            final int finalI = i;
//
////            frame.setOn(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////
////                }
////            });
//
//            recyclerView.addView(itemView);
//            Log.e("TAG_PAHER",finalI + " "+discoverModelArrayList.get(finalI).getCard_url());
//            recyclerView.setOnTouchListener(new OnSwipeTouchListener(context) {
//
//                public void onSwipeLeft() {
//                    startActivity(new Intent(context,EditorSubActivity.class).putExtra("chapter_id",discoverModelArrayList.get(recyclerView.getCurrentPage()).getId()));
////                    Toast.makeText(EditorActivity.this, finalI+ " "+discoverModelArrayList.get(recyclerView.getCurrentPage()).getId(), Toast.LENGTH_SHORT).show();
//                }
//
//            });
//
//        }
//    }

    @Override
    public void clickoncard(ImageView view,int position,String mimetype,String content,String title, String long_desc,List<Books> list,String image_Ar,String id) {
        startActivity(new Intent(context,EditorSubActivity.class).putExtra("chapter_id",id));

//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
//        startActivity(new Intent(context, DiscoverDetailActivity.class)
//
//                .putExtra("title",title)
//                .putExtra("long_desc",long_desc)
//                .putExtra("discover_id",discover_id)
//                .putExtras(bundle)
//                .putExtra("mimetype",mimetype).putExtra("content",content)
//        .putExtra("image_Ar",image_Ar)
//        );
//        getActivity().finishAffinity();

//        getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        Log.e("POS",position+"");
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        checkOrientation(newConfig);
    }

    private void checkOrientation(Configuration newConfig){
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            discoverModelArrayList.clear();
            getDataFromServer();
            Log.d("OrientationMyApp", "Current Orientation : Landscape");
            // Your magic here for landscape mode
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            discoverModelArrayList.clear();
            getDataFromServer();
            Log.d("OrientationMyApp", "Current Orientation : Portrait");
            // Your magic here for portrait mode
        }
    }



//    public  void onBind(String mediaUrl, Context context) {
//        videoSurfaceView = new PlayerView(context);
//        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
//        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//        TrackSelection.Factory videoTrackSelectionFactory =
//                new AdaptiveTrackSelection.Factory(bandwidthMeter);
//        TrackSelector trackSelector =
//                new DefaultTrackSelector(videoTrackSelectionFactory);
//
//        // 2. Create the player
//        videoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
//
//        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
//                context, Util.getUserAgent(context, "RecyclerView VideoPlayer"));
////        requestManager1 = requestManager;
////        parent.setTag(this);
////        title.setText(mediaObject.getTitle());
////       requestManager1
////                .load(mediaObject.getThumbnail())
////                .into(thumbnail);
//
////        String mediaUrl = mediaObjects.get(targetPosition).getMedia_url();
//        if (mediaUrl != null) {
//            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
//                    .createMediaSource(Uri.parse(mediaUrl));
//            videoPlayer.prepare(videoSource);
//            videoPlayer.setPlayWhenReady(true);
//        }
//    }


    @Override
    public void onStart() {
        super.onStart();
    }

    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (Util.SDK_INT <= 23 || player == null) {
//            initializePlayer();
//            if (playerView != null) {
//                playerView.onResume();
//            }
//        }
//
//        if (isInPictureInPictureMode()){
//
//
//        }
//
//
//    }
//
//
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (Util.SDK_INT <= 23) {
//            if (playerView != null) {
//                playerView.onPause();
//            }
//            releasePlayer();
//        }
//
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (Util.SDK_INT > 23) {
//            if (playerView != null) {
//                playerView.onPause();
//            }
//            releasePlayer();
//        }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        updateTrackSelectorParameters();
//        updateStartPosition();
//        outState.putParcelable(KEY_TRACK_SELECTOR_PARAMETERS, trackSelectorParameters);
//        outState.putBoolean(KEY_AUTO_PLAY, startAutoPlay);
//        outState.putInt(KEY_WINDOW, startWindow);
//        outState.putLong(KEY_POSITION, startPosition);
//    }
//
//    // OnClickListener methods
//
//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        // See whether the player view wants to handle media or DPAD keys events.
//        return playerView.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
//    }
//
//    @Override
//    public void preparePlayback() {
//        initializePlayer();
//    }
//
//    private void initializePlayer(PlayerView player) {
//        if (player == null) {
//
//            Uri uri = Uri.parse(content);
//
//            if (!Util.checkCleartextTrafficPermitted(uri)) {
//                showToast("error");
//                return;
//            }
//            if (Util.maybeRequestReadExternalStoragePermission(/* activity= */ this, uri)) {
//                // The player will be reinitialized if the permission is granted.
//                return;
//            }
//
//            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//
//            TrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
//            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this);
//            trackSelector = new DefaultTrackSelector(trackSelectionFactory);
//            trackSelector.setParameters(trackSelectorParameters);
//            lastSeenTrackGroupArray = null;
//            LoadControl loadControl = new DefaultLoadControl();
//            player = ExoPlayerFactory.newSimpleInstance(this, renderersFactory, trackSelector, loadControl);
//
//
//            HttpProxyCacheServer proxyServer = new HttpProxyCacheServer.Builder(context).maxCacheSize(1024 * 1024 * 1024).build();
//            String proxyURL = proxyServer.getProxyUrl(content);
//            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
//                    Util.getUserAgent(context, getApplicationContext().getPackageName()));
//            player.addListener(new DiscoverDetailActivity.PlayerEventListener());
//
//
//            player.setPlayWhenReady(startAutoPlay);
//
////            player.prepare(audioSource);
//            playerView.setPlayer(player);
//            playerView.setPlaybackPreparer(this);
//            mediaSource = buildMediaSource(uri);
//            playerView.buildDrawingCache();
////            HttpProxyCacheServer proxy = getProxy();
////            String proxyUrl = proxy.getProxyUrl(new URL(uri.toString()));
////            playerView.setVideoPath(proxyUrl);
//
//        }
//        boolean haveStartPosition = startWindow != C.INDEX_UNSET;
//        if (haveStartPosition) {
//            player.seekTo(startWindow, startPosition);
//        }
//        initCustomController();
//        initFullscreenDialog();
//
//        MediaSource audioSource = new ExtractorMediaSource(Uri.parse(content),
//                new DiscoverDetailActivity.CacheDataSourceFactory(context, 100 * 1024 * 1024, 5 * 1024 * 1024), new DefaultExtractorsFactory(), null, null);
////        getDownloadCache().release();
//
////        audioSource.
//        player.prepare(audioSource, !haveStartPosition, false);
//
////        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
////                Util.getUserAgent(context, getPackageName()));
////
////
////        exoPlayer.prepare(new ProgressiveMediaSource.Factory(dataSourceFactory)
////                .createMediaSource(Uri.parse(proxyURL)););
//    }
//
//    private void initCustomController() {
//        controlView = playerView.findViewById(R.id.custom_controller);
//        LinearLayout exoGoBack = controlView.findViewById(R.id.exo_exit);
//        retryButton = controlView.findViewById(R.id.retry);
//        centerControl = controlView.findViewById(R.id.center_control);
//        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
//
//        exoGoBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        retryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                centerControl.setVisibility(View.VISIBLE);
//                retryButton.setVisibility(View.GONE);
//                player.seekTo(0);
//                player.setPlayWhenReady(true);
//            }
//        });
//
//        initFullscreenButton();
//    }
//
//    private MediaSource buildMediaSource(Uri uri) {
//        @C.ContentType int type = Util.inferContentType(uri, null);
//        switch (type) {
//            case C.TYPE_DASH:
//                return new DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
//            case C.TYPE_SS:
//                return new SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
//            case C.TYPE_HLS:
//                return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
//            case C.TYPE_OTHER:
//                return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
//            default:
//                throw new IllegalStateException("Unsupported type: " + type);
//        }
//    }
//
//    private void releasePlayer() {
//        if (player != null) {
//            updateTrackSelectorParameters();
//            updateStartPosition();
//            player.release();
//            player = null;
//            mediaSource = null;
//            trackSelector = null;
//        }
//    }
//
//    private void updateTrackSelectorParameters() {
//        if (trackSelector != null) {
//            trackSelectorParameters = trackSelector.getParameters();
//        }
//    }
//
//    private void updateStartPosition() {
//        if (player != null) {
//            startAutoPlay = player.getPlayWhenReady();
//            startWindow = player.getCurrentWindowIndex();
//            startPosition = Math.max(0, player.getContentPosition());
//        }
//    }
//
//    private void clearStartPosition() {
//        startAutoPlay = true;
//        startWindow = C.INDEX_UNSET;
//        startPosition = C.TIME_UNSET;
//    }
//
//    /**
//     * Returns a new DataSource factory.
//     */
//    private DataSource.Factory buildDataSourceFactory() {
//        return ((DemoApplication) getApplication()).buildDataSourceFactory();
//    }
//
//    private void showToast(int messageId) {
//        showToast(getString(messageId));
//    }
//
//    private void showToast(String message) {
//        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onVisibilityChange(int visibility) {
//        //System generated method
//    }
//
//    private void initFullscreenDialog() {
//        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
//            @Override
//            public void onBackPressed() {
//                if (mExoPlayerFullscreen) {
//                    setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                    closeFullscreenDialog();
//                }
//            }
//        };
//    }
//
//    /**
//     * Open player full screen
//     */
//    private void openFullscreenDialog() {
//        ((ViewGroup) playerView.getParent()).removeView(playerView);
//        mFullScreenDialog.addContentView(playerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_collapse));
//        mExoPlayerFullscreen = true;
//        mFullScreenDialog.show();
//    }
//
//    /**
//     * Close player full screen
//     */
//    private void closeFullscreenDialog() {
//        ((ViewGroup) playerView.getParent()).removeView(playerView);
//        ((FrameLayout) findViewById(R.id.root)).addView(playerView);
//        mExoPlayerFullscreen = false;
//        mFullScreenDialog.dismiss();
//        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_expand));
//    }
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
//    }
//
//    private void setScreenOrientation(int screenOrientationPortrait) {
//        setRequestedOrientation(screenOrientationPortrait);
//    }
//
//    private class PlayerEventListener implements Player.EventListener {
//
//        private boolean isBehindLiveWindow(ExoPlaybackException e) {
//            if (e.type != ExoPlaybackException.TYPE_SOURCE) {
//                return false;
//            }
//            Throwable cause = e.getSourceException();
//            while (cause != null) {
//                if (cause instanceof BehindLiveWindowException) {
//                    return true;
//                }
//                cause = cause.getCause();
//            }
//            return false;
//        }
//
//        @Override
//        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//            if (playbackState == Player.STATE_ENDED) {
//                showRetryButton();
//            }
//        }
//
//        private void showRetryButton() {
//            centerControl.setVisibility(View.GONE);
//            retryButton.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        public void onPositionDiscontinuity(@Player.DiscontinuityReason int reason) {
//            if (player.getPlaybackError() != null) {
//                // The user has performed a seek whilst in the error state. Update the resume position so
//                // that if the user then retries, playback resumes from the position to which they seeked.
//                updateStartPosition();
//            }
//        }
//
//        @Override
//        public void onPlayerError(ExoPlaybackException e) {
//            if (isBehindLiveWindow(e)) {
//                clearStartPosition();
//                initializePlayer();
//            } else {
//                updateStartPosition();
//            }
//        }
//
//        @Override
//        @SuppressWarnings("ReferenceEquality")
//        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//            if (trackGroups != lastSeenTrackGroupArray) {
//                MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
//                if (mappedTrackInfo != null) {
//                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
//                            == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
//                        showToast("unsupported video");
//                    }
//                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_AUDIO)
//                            == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
//                        showToast("unsupported video");
//                    }
//                }
//                lastSeenTrackGroupArray = trackGroups;
//            }
//        }
//
//    }
//
//
//    @Override
//    @SuppressWarnings("ReferenceEquality")
//    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//        if (trackGroups != lastSeenTrackGroupArray) {
//            MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
//            if (mappedTrackInfo != null) {
//                if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
//                        == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
//                    showToast("unsupported video");
//                }
//                if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_AUDIO)
//                        == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
//                    showToast("unsupported video");
//                }
//            }
//            lastSeenTrackGroupArray = trackGroups;
//        }
//    }
//
//}
//    private class PlayerErrorMessageProvider implements ErrorMessageProvider<ExoPlaybackException> {
//
//        @Override
//        public Pair<Integer, String> getErrorMessage(ExoPlaybackException e) {
//            String errorString ="generic error";
//            if (e.type == ExoPlaybackException.TYPE_RENDERER) {
//                Exception cause = e.getRendererException();
//                if (cause instanceof MediaCodecRenderer.DecoderInitializationException) {
//                    // Special case for decoder initialization failures.
//                    MediaCodecRenderer.DecoderInitializationException decoderInitializationException =
//                            (MediaCodecRenderer.DecoderInitializationException) cause;
//                    if (decoderInitializationException.decoderName == null) {
//                        if (decoderInitializationException.getCause() instanceof MediaCodecUtil.DecoderQueryException) {
//                            errorString = "error decoders";
//                        } else if (decoderInitializationException.secureDecoderRequired) {
//                            errorString =
//                                    "error decoders" +decoderInitializationException.mimeType;
//                        } else {
//                            errorString =
//                                    "error decoders"+decoderInitializationException.mimeType;
//                        }
//                    } else {
//                        errorString =
//                                "error decoders"+
//                                        decoderInitializationException.decoderName;
//                    }
//                }
//            }
//            return Pair.create(0, errorString);
//        }
//    }
//
//    class CacheDataSourceFactory implements DataSource.Factory {
//        private final Context context;
//        private final DefaultDataSourceFactory defaultDatasourceFactory;
//        private final long maxFileSize, maxCacheSize;
//
//        CacheDataSourceFactory(Context context, long maxCacheSize, long maxFileSize) {
//            super();
//            this.context = context;
//            this.maxCacheSize = maxCacheSize;
//            this.maxFileSize = maxFileSize;
//            String userAgent = Util.getUserAgent(context, context.getString(R.string.app_name));
//            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//            defaultDatasourceFactory = new DefaultDataSourceFactory(this.context,
//                    bandwidthMeter,
//                    new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter));
//        }
//
//        @Override
//        public DataSource createDataSource() {
//            LeastRecentlyUsedCacheEvictor evictor = new LeastRecentlyUsedCacheEvictor(maxCacheSize);
//            File file = new File(context.getCacheDir(), "media"+System.currentTimeMillis());
//
//
//
//            simpleCache = new SimpleCache(file, evictor);
////
//
//            return new CacheDataSource(simpleCache, defaultDatasourceFactory.createDataSource(),
//                    new FileDataSource(), new CacheDataSink(simpleCache, maxFileSize),
//                    CacheDataSource.FLAG_BLOCK_ON_CACHE | CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR, null);
//        }
//    }


}
