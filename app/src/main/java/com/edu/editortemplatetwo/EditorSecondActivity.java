package com.edu.editortemplatetwo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.edu.aplis.DemoApplication;
import com.edu.aplis.R;
import com.edu.book.BookFirstRetrofitModel;
import com.edu.discover.Books;
import com.edu.discover.ClickAdapter;
import com.edu.discover.DiscoverModel;
import com.edu.editortemplate.EditorSubActivity;
import com.edu.editortemplate.EditoroneAdapter;
import com.edu.editortemplate.OnSwipeTouchListener;
import com.edu.editortemplate.PagerFragment1;
import com.edu.editortemplate.VerticalViewPager;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;
import com.edu.sketch.SketchActivity;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import at.blogc.android.views.ExpandableTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorSecondActivity extends AppCompatActivity implements ResponceQueues,ClickAdapter {
    private static Context context;
    TextView text_title,text_subtitle;
    ExpandableTextView text_description;
    LinearLayout ll_scrollseriesoftitle;
    HashMap<String,String> hashMap=new HashMap<>();
    public  ImageView layout_bg;

    RecyclerView rec_view;
    private  ArrayList<EditorModel> editorModelArrayList;
    EditorModel editorModel;
    EditorSecondActivityAdapter editorSecondActivityAdapter;
    String book_Id= "";
    String book_title= "";
    String book_subtitle= "";
    String book_description= "";
    Button btn_discover;
    int finalI=0;


    public static View parent;
    VideoView videoView;
    private PlayerView videoSurfaceView;
    private SimpleExoPlayer videoPlayer;
    private FrameLayout frameLayout;
    private int playPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondeditor);
        context = EditorSecondActivity.this;

        layout_bg = findViewById(R.id.layout_bg);
        text_title = findViewById(R.id.text_title);
        text_subtitle = findViewById(R.id.text_subtitle);
        text_description = findViewById(R.id.text_subdetail);
        ll_scrollseriesoftitle = findViewById(R.id.ll_scrollseriesoftitle);
        rec_view = findViewById(R.id.rec_view);
        btn_discover = findViewById(R.id.btn_discover);


        editorModelArrayList= new ArrayList<>();
        book_Id = getIntent().getStringExtra("book_id");
        book_title = getIntent().getStringExtra("book_title");
        book_subtitle = getIntent().getStringExtra("book_subtitle");
        book_description = getIntent().getStringExtra("book_description");

        text_title.setText(book_title);
        text_subtitle.setText(book_subtitle);


        btn_discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(context, EditorSecondSubActivity.class).putExtra("sub_id", editorModelArrayList.get(finalI).getId())
                            .putExtra("title_name", editorModelArrayList.get(finalI).getTitle())
                            .putExtra("title_subname", editorModelArrayList.get(finalI).getSub_title())
                            .putExtra("title_description", editorModelArrayList.get(finalI).getDescription())
                            .putExtra("title_bgimage", editorModelArrayList.get(finalI).getBg_image())
                    );
                }
                catch (Exception e){

                }
            }
        });
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false);
        rec_view.setLayoutManager(linearLayoutManager);

        callanalytics();

//        String html = "<html><style type=\"text/css\"> @font-face {font-family: MyFont;src: url('file:///android_asset/fonts/playfairdisplayregular.ttf'); font-weight: normal; font-style: normal} @font-face {font-family: MyFontBOLD;src: url('file:///android_asset/fonts/playfairdisplaybold.ttf'); font-weight: bold; font-style: normal} @font-face {font-family: MyFontITALIC;src: url('file:///android_asset/fonts/playfairdisplayitalic.ttf'); font-weight: normal; font-style: italic}"+
//                "h1{\n" +
//                "font-family: MyFont;"+
//                "font-size: 18px;\n" +
//                "}\n" +
//                "\n" +
//                "h2{\n" +
//                "font-family: MyFont;"+
//                "  font-size: 16px;\n" +
//                "}\n" +
//                "p {\n" +
//                "font-family: MyFont;"+
//                "  font-size: 14px;\n" +
//                "}\n" +
//                "body {\n" +
//                "font-family: MyFont;"+
//                "  background-color: white;\n" +
//                "  color: black;\n" +
//                "}\n" +
//                "</style> \n <body>"+book_description+"</body></html>";
//        final String mimeType = "text/html";
//        final String encoding = "UTF-8";
//
//        text_description.loadDataWithBaseURL("", html, mimeType, encoding, "");




//        Log.e("TAGEDITOR",book_Id);
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



    private void getDataFromServer() {

        Api api = RetrofitClient.getClient(context, Api.BASE_URL).create(Api.class);

        Call<EditorFirstRetrofitModel> call = api.getChapterByBookId(Cons.GET_CHAPTERBYBOOKID+book_Id);
//        Log.e("discovertry",call.u);
        call.enqueue(new Callback<EditorFirstRetrofitModel>() {
            @Override
            public void onResponse(Call<EditorFirstRetrofitModel> call, Response<EditorFirstRetrofitModel> response) {

                try {

                    setrecyclerdataadapter(response.body().getEditorSecondRetrofitModel().getEditorThirdRetrofitModels());
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
            public void onFailure(Call<EditorFirstRetrofitModel> call, Throwable t) {
                Log.e("errordo", t.getMessage());
            }
        });

//        trustEveryone();
//        hashMap.clear();
//        Log.e("TAGEDITOR",Cons.GET_CHAPTERBYBOOKID+book_Id);
//        ApiService apiService = new ApiService(context,this, Cons.GET_CHAPTERBYBOOKID+book_Id,hashMap,1);
//        apiService.execute();
    }


    private void setrecyclerdataadapter(List<EditorThirdRetrofitModel> editorThirdRetrofitModels) {
        editorModelArrayList.clear();

        for (int i = 0; i < editorThirdRetrofitModels.size(); i++) {
            String title=editorThirdRetrofitModels.get(i).getTitle();
            if (title == null || title.equalsIgnoreCase("null")) {
                title = "";
            }

            editorModel = new EditorModel();
            editorModel.setId(editorThirdRetrofitModels.get(i).getId());
            editorModel.setTitle(title);
            editorModel.setSub_title(editorThirdRetrofitModels.get(i).getSub_title());
            editorModel.setDescription(editorThirdRetrofitModels.get(i).getDescription());
            editorModel.setImage(editorThirdRetrofitModels.get(i).getTopic_cover());
            editorModel.setBg_image(editorThirdRetrofitModels.get(i).getTopic_cover());
            editorModel.setColor_code(editorThirdRetrofitModels.get(i).getColor_code());
            editorModelArrayList.add(editorModel);
        }


            if (editorModelArrayList.size()>0) {
        changebg(0 + "", null, editorModelArrayList.get(0).getTitle(), editorModelArrayList.get(0).getSub_title(), editorModelArrayList.get(0).getDescription(), editorModelArrayList.get(0).getImage());
    }

    editorSecondActivityAdapter = new EditorSecondActivityAdapter(context, editorModelArrayList,this);
            rec_view.setAdapter(editorSecondActivityAdapter);
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

    @Override
    public void responceQue(String responce, String url, String extra_text) {
        editorModelArrayList.clear();
        Log.e("Response===",url+responce);

        try {
            JSONObject jsonObject = new JSONObject(responce);
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("data");
            Log.e("Response===",jsonArray.length()+"");

            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObjectseries=jsonArray.getJSONObject(i);
                DiscoverModel discoverModel = new DiscoverModel();
                String id = jsonObjectseries.getString("id");
                String title = jsonObjectseries.getString("title");
                String sub_heading = jsonObjectseries.getString("sub_title");
                String card_url = jsonObjectseries.getString("topic_cover");
                String long_description = jsonObjectseries.getString("description");
                String bg_image = jsonObjectseries.getString("bg_image");
                String color_code = jsonObjectseries.getString("color_code");


                if (title==null||title.equalsIgnoreCase("null")){
                    title = "";
                }

                editorModel =new EditorModel();
                editorModel.setId(id);
                editorModel.setTitle(title);
                editorModel.setSub_title(sub_heading);
                editorModel.setDescription(long_description);
                editorModel.setImage(card_url);
                editorModel.setBg_image(card_url);
                editorModel.setColor_code(color_code);
                editorModelArrayList.add(editorModel);
            }

//            Collections.sort(editorModelArrayList, new Comparator() {
//                @Override
//                public int compare(Object o1, Object o2) {
//                    EditorModel p1 = (EditorModel) o1;
//                    EditorModel p2 = (EditorModel) o2;
//                    return p1.getId().compareToIgnoreCase(p2.getId());
//                }
//            });
            if (editorModelArrayList.size()>0) {
                changebg(0 + "", null, editorModelArrayList.get(0).getTitle(), editorModelArrayList.get(0).getSub_title(), editorModelArrayList.get(0).getDescription(), editorModelArrayList.get(0).getImage());
            }

            editorSecondActivityAdapter = new EditorSecondActivityAdapter(context, editorModelArrayList,this);
            rec_view.setAdapter(editorSecondActivityAdapter);
//
//
//            addarrivals(editorModelArrayList);
        } catch (JSONException e) {
            Log.e("JSONEXCEPTION",e+"");
            e.printStackTrace();
        }


    }

    private void addarrivals(final ArrayList<EditorModel> editorModelArrayList) {

        {
            for (int i = 0; i < editorModelArrayList.size(); i++) {
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.homearrivals, null);
                final ImageView imageView = view.findViewById(R.id.image);
                TextView text_name = view.findViewById(R.id.text_name);

                text_name.setVisibility(View.VISIBLE);
                Glide
                        .with(context)
                        .load(editorModelArrayList.get(i).getImage())
                        .centerCrop()
                        .into(imageView);

                text_name.setText(editorModelArrayList.get(i).getTitle());
                ll_scrollseriesoftitle.addView(view);


                final int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(EditorSecondActivity.this,imageView,"titlebg");



                    }
                });
            }

        }
    }


    @Override
    public void clickoncard(ImageView view,int position,String title,String content,String subtitle, String long_desc,List<Books> list,String image_Ar,String id) {
//        clicksketchedit(view,"transparent",content);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_left);
        rec_view.startAnimation(animation);
        if (position==editorModelArrayList.size()-1) {
            rec_view.getLayoutManager().scrollToPosition(0);

        }
        else if (position==editorModelArrayList.size()-2){
            rec_view.getLayoutManager().scrollToPosition(position+1);

        }
        else {
            rec_view.getLayoutManager().scrollToPosition(position+2);
        }

//        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(EditorSecondActivity.this,view,"topictitlebg");
//        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache(true);
//        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
//        view.setDrawingCacheEnabled(false);

        changebg(position+"",null,title,subtitle,long_desc,content);

//        clicksketchedit(view,"transparent");
//        view.buildDrawingCache();
//        Bitmap image = view.getDrawingCache();
//        TranslateAnimation animation = new TranslateAnimation(0, 50, 0, 100);
//        animation.setDuration(500);
//        animation.setFillAfter(false);
//        animation.setAnimationListener(new MyAnimationListener(bitmap,title,subtitle,long_desc,content));
//
//        view.startAnimation(animation);
//


        Log.e("POS",position+" "+content);
    }

    private Bitmap imageView2Bitmap(ImageView view){
        Bitmap bitmap = ((BitmapDrawable)view.getDrawable()).getBitmap();
        return bitmap;
    }

    private void changebg(String id,Bitmap bundle, String title, String subtitle, String longdescription, final String bgimageurl){
        finalI=Integer.parseInt(id);
//        Animation animation1 =
//                AnimationUtils.loadAnimation(getApplicationContext(),
//                        R.anim.fadein);
//        layout_bg.startAnimation(animation1);

//        try {
//            layout_bg.setImageBitmap(bundle);
//        }
//        catch (Exception e){
//
//        }
        URL url = null;
//        try {
//            url = new URL(bgimageurl);
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            layout_bg.setImageBitmap(bmp);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        layout_bg.setTransitionName("topictitlebg");
//        Animation fadeOut = AnimationUtils.loadAnimation(context, R.anim.fadeout);

//        TransitionDrawable transitionDrawable = (TransitionDrawable)layout_bg.getBackground();
//        transitionDrawable.startTransition(200);


//        layout_bg.startAnimation(fadeOut);
//
//        fadeOut.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//            @Override
//            public void onAnimationEnd(Animation animation) {

        Glide.with(context).load(bgimageurl).asBitmap().into(new SimpleTarget<Bitmap>((R.dimen.dp140), (R.dimen.dp140)) {


            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                Drawable color = new ColorDrawable(Color.parseColor("#eddddf"));
                layout_bg.setImageDrawable(color);
            }

            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                glideAnimation.animate()
                Drawable drawable = new BitmapDrawable(context.getResources(), resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    layout_bg.setImageDrawable(drawable);
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
//                Drawable color = new ColorDrawable(Color.parseColor(bgimageurl));

//                LayerDrawable ld = new LayerDrawable(new Drawable[]{color, errorDrawable});
//                imageView.setImageDrawable(ld);
//                Log.e("TAGEDITORbg_image","failed"+itemList.get(position).getColor_code());

//                layout_bg.setImageDrawable(color);

            }

        });

//        Glide.with(context)
//                        .load(bgimageurl)
//                        .into(layout_bg);
//                Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fadein);
//                layout_bg.startAnimation(fadeIn);
//            }
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });

        text_title.setText(title);
        text_subtitle.setText(subtitle);
        text_description.setText(Html.fromHtml(longdescription));




        text_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text_description.isExpanded()) {
                    text_description.collapse();
//                    buttonToggle.setText(R.string.expand);
                } else {
                    text_description.expand();
//                    buttonToggle.setText(R.string.collapse);
                }
            }
        });




    }

    public  void clicksketchedit(View view1,String sketchoption,String content){
//bitmap=null;
        parent=view1;
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(EditorSecondActivity.this,layout_bg,"imagesketch");

        startActivity(new Intent(context, SketchActivity.class).putExtra("color",sketchoption).putExtra("sub_name",content),activityOptionsCompat.toBundle());
//        _intent.;
//        context.startActivity(_intent);

//                Bitmap _bitmap =view; // your bitmap
//                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
//                _bitmap.compress(Bitmap.CompressFormat.PNG, 50, _bs);
//                _intent.putExtra("byteArray", _bs.toByteArray());
//        context.startActivity(_intent);
//        bitmap.setDrawingCacheEnabled(true);
//        final Dialog dialog = new Dialog(context);
//        dialog.setContentView(R.layout.custom_dialog);
//
//        dialog.show();
//
//        /* My Cancel Button , and its listener */
//
//        ImageView image_edit=dialog.findViewById(R.id.image_edit);
//        ImageView image_editwhite=dialog.findViewById(R.id.image_editwhite);
//        image_edit.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//
//                Intent _intent = new Intent(context, SketchActivity.class).putExtra("sub_name",sub_name);
//                _intent.putExtra("color",sketchoption);
//
////                Bitmap _bitmap =view; // your bitmap
////                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
////                _bitmap.compress(Bitmap.CompressFormat.PNG, 50, _bs);
////                _intent.putExtra("byteArray", _bs.toByteArray());
//                context.startActivity(_intent);
//
//                dialog.dismiss();
//            }
//        });

//        image_editwhite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent _intent = new Intent(context, SketchActivity.class);
//                _intent.putExtra("color",sketchoption);
//                _intent.putExtra("sub_name",sub_name);
//                context.startActivity(_intent);
//
//                dialog.dismiss();
//            }
//        });
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

    public void onback(View view) {
        finish();
    }

    private class MyAnimationListener implements Animation.AnimationListener {
        Bitmap bitmap;
        String title;
        String subtitle;
        String long_desc;
        public MyAnimationListener(Bitmap bitmap, String title, String subtitle, String long_desc, String content) {
            this.bitmap=bitmap;
            this.title=title;
            this.subtitle=subtitle;
            this.long_desc=long_desc;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            changebg("",bitmap,title,subtitle,long_desc,"");
//            layout_bg.clearAnimation();
//            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            lp.setMargins(50, 100, 0, 0);
//            layout_bg.setLayoutParams(lp);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

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
