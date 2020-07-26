package com.edu.editortemplate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.edu.aplis.DemoApplication;
import com.edu.aplis.R;
import com.edu.discover.Books;
import com.edu.discover.ClickAdapter;
import com.edu.discover.DiscoverModel;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditorSubActivity extends AppCompatActivity implements ResponceQueues,ClickAdapter {
    VerticalViewPager recyclerView;
    HashMap<String,String> hashMap=new HashMap<>();
    Context context;
    private  ArrayList<DiscoverModel> discoverModelArrayList;
    EditoroneAdapter editoroneAdapter;
    String book_Id= "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        context = EditorSubActivity.this;

        recyclerView = findViewById(R.id.recyclerview);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//        recyclerView.setLayoutManager(linearLayoutManager);
        discoverModelArrayList = new ArrayList<DiscoverModel>();

        book_Id = getIntent().getStringExtra("chapter_id");

        callanalytics();
        Log.e("TAGEDITOR",book_Id);
        getDataFromServer();
    }


    private void getDataFromServer() {
        hashMap.clear();
        Log.e("TAGEDITOR",Cons.GET_SUBCHAPTERBYCHAPTERID+book_Id);
        ApiService apiService = new ApiService(context,this, Cons.GET_SUBCHAPTERBYCHAPTERID+book_Id+"&page=1",hashMap,1);
        apiService.execute();
    }

    @Override
    public void responceQue(String responce, String url, String extra_text) {
        discoverModelArrayList.clear();
        Log.e("Response===",url+responce);
        try {
            JSONObject jsonObject = new JSONObject(responce);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
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
            inflateList(discoverModelArrayList);



        } catch (JSONException e) {
            Log.e("JSONEXCEPTION",e+"");
            e.printStackTrace();
        }

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

    private void inflateList(final ArrayList<DiscoverModel> discoverModelArrayList) {
        Log.e("TAG_PAHER_LIST",discoverModelArrayList.size()+"");
        if (discoverModelArrayList.size()<1){
            finish();
        }
        for (int i  =0;i<discoverModelArrayList.size();i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View itemView = inflater.inflate(R.layout.editorone_adapter, null);


            TextView tittle = itemView.findViewById(R.id.heading);
            TextView text_body = itemView.findViewById(R.id.subheading);
            TextView text_longdescription = itemView.findViewById(R.id.longtext);
            LinearLayout view_mime = itemView.findViewById(R.id.mimeview);
            RelativeLayout frame = itemView.findViewById(R.id.frame);

            tittle.setText(discoverModelArrayList.get(i).getTitle());
            text_body.setText(discoverModelArrayList.get(i).getStatus());
            text_longdescription.setText(Html.fromHtml(discoverModelArrayList.get(i).getLong_description()));

            Log.e("VIDEO","video "+discoverModelArrayList.get(i).getMime_type());

            if (tittle.equals("")||tittle.equals(null)){
                tittle.setVisibility(View.GONE);
            }
            if (text_body.equals("")||text_body.equals(null)){
                text_body.setVisibility(View.GONE);
            }
            if (text_longdescription.equals("")||text_longdescription.equals(null)){
                text_longdescription.setVisibility(View.GONE);
            }
            if (discoverModelArrayList.get(i).getMime_type().equalsIgnoreCase("image")){
                ImageView imageView = new ImageView(context);
                RelativeLayout.LayoutParams vp =
                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(vp);
                Glide
                        .with(context)
                        .load(discoverModelArrayList.get(i).getCard_url())
                        .thumbnail(0.05f)
                        .centerCrop()
                        .into(imageView);
                view_mime.addView(imageView);

            }
            else if (discoverModelArrayList.get(i).getMime_type().equalsIgnoreCase("web")){
                WebView webView = new WebView(context);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(discoverModelArrayList.get(i).getCard_url());
                WebSettings webSettings = webView.getSettings();
                webSettings.setAllowFileAccess(true);
                webSettings.setJavaScriptEnabled(true);
                webSettings.setSupportZoom(true);
                webSettings.setBuiltInZoomControls(true);
                webSettings.setDisplayZoomControls(false);
                view_mime.addView(webView);
            }

            else if (discoverModelArrayList.get(i).getMime_type().equalsIgnoreCase("gif")){
                ImageView imageView = new ImageView(context);
                RelativeLayout.LayoutParams vp =
                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(vp);
                Glide
                        .with(context)
                        .load(discoverModelArrayList.get(i).getCard_url())
                        .asGif()
                        .thumbnail(0.05f)
                        .centerCrop()
                        .into(imageView);
                view_mime.addView(imageView);

                }

            else if (discoverModelArrayList.get(i).getMime_type().equalsIgnoreCase("video")){
                Log.e("VIDEO","video "+Uri.parse(discoverModelArrayList.get(i).getCard_url()));
                VideoView videoView = new VideoView(context);
//                videoView.setVideoURI(Uri.parse(discoverModelArrayList.get(i).getCard_url()));
////                video_view.setVideoPath("/sdcard/myvideo.mp4");
//                videoView.setMediaController(new MediaController(this));
//                videoView.setZOrderOnTop(true);
//                videoView.start();
//                videoView.pl
                RelativeLayout.LayoutParams vp =
                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                videoView.setLayoutParams(vp);
                view_mime.addView(videoView);

            }


            final int finalI = i;
            frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    startActivity(new Intent(context,EditorSubTopicActivity.class).putExtra("chapter_id",discoverModelArrayList.get(finalI).getId()));

                }
            });



            recyclerView.addView(itemView);

            recyclerView.setOnTouchListener(new OnSwipeTouchListener(context) {

                public void onSwipeRight() {
                    finish();
//                    startActivity(new Intent(context,EditorSubActivity.class).putExtra("chapter_id",discoverModelArrayList.get(recyclerView.getCurrentPage()).getId()));
//                    Toast.makeText(EditorSubActivity.this, finalI+ " "+discoverModelArrayList.get(recyclerView.getCurrentPage()).getId(), Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    @Override
    public void clickoncard(ImageView view,int position,String mimetype,String content,String title, String long_desc,List<Books> list,String image_Ar,String id) {
//        startActivity(new Intent(context,EditorSubTopicActivity.class).putExtra("chapter_id",id));

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


    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }
    public void setAnimation(){

//        a.reset();
//        startActivity(new Intent(context, Loginclass.class));

//        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
    }
}
