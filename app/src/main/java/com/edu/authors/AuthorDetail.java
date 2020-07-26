package com.edu.authors;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edu.aplis.R;
import com.edu.book.CourseDetail;
import com.edu.book.TagAdapter;
import com.edu.search.Search_Booklists;
import com.edu.search.Series;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import at.blogc.android.views.ExpandableTextView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AuthorDetail extends AppCompatActivity implements View.OnClickListener, ResponceQueues {
    ExpandableTextView text_author;
    TextView txt_start, text_title, text_subname, text_pubname, text_pubdate, text_pubisbn, text_publang, text_categories, text_agerange;
    WebView text_subdetail;
    TextView text_darkmode;
    Context context;
    CircleImageView image_author;
    CircleImageView cover_image;
    ImageView cover;
    ImageView imageview_slider;
    HorizontalScrollView horizontalseries;
    LinearLayout linearstart, linearresume, lineartopthreed, linear_notes, ll_scrollseries;
    String sub_name = "";
    HashMap<String, String> hashMap = new HashMap<>();
    ArrayList<String> tagNames;
    RecyclerView recyclerView;
    String description;
    TagAdapter adapter;
    ArrayList<Series> serieslist;
    Series seriesmodel;
    public Boolean isVisible = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.author_detail);
        context = AuthorDetail.this;

        txt_start = findViewById(R.id.txt_start);
        text_author = findViewById(R.id.text_author);
        text_subdetail = findViewById(R.id.text_subdetail);
        linearstart = findViewById(R.id.linearstart);
        linearresume = findViewById(R.id.linearresume);
        lineartopthreed = findViewById(R.id.lineartopthreed);
        linear_notes = findViewById(R.id.linear_notes);
        image_author = findViewById(R.id.image_author);
        text_title = findViewById(R.id.text_title);
        text_subname = findViewById(R.id.text_subname);
        text_pubname = findViewById(R.id.text_pubname);
        text_pubdate = findViewById(R.id.text_pubdate);
        text_pubisbn = findViewById(R.id.text_pubisbn);
        text_publang = findViewById(R.id.text_publang);
        text_categories = findViewById(R.id.text_categories);
        text_agerange = findViewById(R.id.text_agerange);
        cover_image = findViewById(R.id.circular);
        cover = findViewById(R.id.cover);
        imageview_slider = findViewById(R.id.imageview_slider);
        horizontalseries = findViewById(R.id.horizontalseries);
        ll_scrollseries = findViewById(R.id.ll_scrollseries);

        recyclerView = findViewById(R.id.rvTags);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        serieslist = new ArrayList<>();
        Log.e("data", "clikedbhbin:" + sub_name);



        makehttpcall();
        linearstart.setOnClickListener(this);
        linearresume.setOnClickListener(this);
        imageview_slider.setOnClickListener(this);
        linear_notes.setOnClickListener(this);

        text_author.setAnimationDuration(750L);
        text_author.setInterpolator(new OvershootInterpolator());
        text_author.setExpandInterpolator(new OvershootInterpolator());
        text_author.setCollapseInterpolator(new OvershootInterpolator());
        text_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text_author.isExpanded()) {
                    text_author.collapse();
//                    buttonToggle.setText(R.string.expand);
                } else {
                    text_author.expand();
//                    buttonToggle.setText(R.string.collapse);
                }
            }
        });


        text_subname.setText(getIntent().getStringExtra("sub_name"));


        txt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        text_subdetail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        Log.e("check", "clickdetail");

    }

    private void addarrivals(final ArrayList<Series> serieslist) {

        {
            for (int i = 0; i < serieslist.size(); i++) {
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.homearrivals, null);
                ImageView imageView = view.findViewById(R.id.image);
                TextView text_name = view.findViewById(R.id.text_name);
                Glide
                        .with(context)
                        .load(serieslist.get(i).getPro_image())
                        .centerCrop()
                        .into(imageView);

                final int finalI = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(AuthorDetail.this,view,"cover_image");

                        startActivity(new Intent(context, CourseDetail.class).putExtra("book_id", serieslist.get(finalI).getProduct_id()).putExtra("cover_image",serieslist.get(finalI).getPro_image()),activityOptionsCompat.toBundle());

                    }
                });

                ll_scrollseries.addView(view);
            }

        }
    }

    private void makehttpcall() {
        String author_id = getIntent().getStringExtra("unique_id");
        hashMap.clear();
        ApiService apiService = new ApiService(context, this, Cons.AUTHORSDETAILS_URL + author_id, hashMap, 1);
        apiService.execute();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linearstart:
                break;

            case R.id.linearresume:
                break;
            case R.id.linear_notes:
                break;
        }
    }

    public void back(View view) {
        finish();
    }

    public void cromecast(View view) {
    }


    @Override
    public void responceQue(String responce, String url, String extra_text) {
        try {
            JSONObject jsonObject = new JSONObject(responce);
            JSONObject jsonObject1 = jsonObject.getJSONArray("data").getJSONObject(0);
            String title = jsonObject1.getString("first_name") + " " + jsonObject1.getString("last_name");
//            String sub_title = jsonObject1.getString("sub_title");
            description = jsonObject1.getString("description");
            String profile_pic = jsonObject1.getString("profile_pic");
            String coverdata = jsonObject1.getString("cover_pic");
            String language = jsonObject1.getString("language");
            try {
                JSONArray jsonArray = jsonObject1.getJSONArray("books");
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObjectseries = jsonArray.getJSONObject(i);
                    String series_image = jsonObjectseries.getString("book_cover");
                    String book_id = jsonObjectseries.getString("id");


                    seriesmodel = new Series();
                    seriesmodel.setPro_image(series_image);
                    seriesmodel.setProduct_id(book_id);
                    serieslist.add(seriesmodel);

                }
                Log.e("Jsondata==", serieslist.size() + " " + jsonArray.toString());

                addarrivals(serieslist);

            } catch (Exception e) {
                Log.e("Jsondata==", e + "");

            }

            text_title.setText(title);

            Glide
                    .with(context)
                    .load(coverdata)
                    .centerCrop()
                    .into(cover);

            Glide
                    .with(context)
                    .load(profile_pic)
                    .centerCrop()
                    .into(cover_image);


        } catch (Exception e) {

        }


        String DES = "<p><i>i am italics</i></p><p><b>i am Bold</b></p><p>I am Regular</p>";
        String html = "<html><style type=\"text/css\"> @font-face {font-family: MyFont;src: url('file:///android_asset/fonts/playfairdisplayregular.ttf'); font-weight: normal; font-style: normal} @font-face {font-family: MyFontBOLD;src: url('file:///android_asset/fonts/playfairdisplaybold.ttf'); font-weight: bold; font-style: normal} @font-face {font-family: MyFontITALIC;src: url('file:///android_asset/fonts/playfairdisplayitalic.ttf'); font-weight: normal; font-style: italic}" +
                "h1{\n" +
                "font-family: MyFont;" +
                "font-size: 18px;\n" +
                "}\n" +
                "\n" +
                "h2{\n" +
                "font-family: MyFont;" +
                "  font-size: 16px;\n" +
                "}\n" +
                "p {\n" +
                "font-family: MyFont;" +
                "  font-size: 14px;\n" +
                "}\n" +
                "body {\n" +
                "font-family: MyFont;" +
                "  background-color: white;\n" +
                "  color: black;\n" +
                "}\n" +
                "</style> \n <body>" + description + "</body></html>";
        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        String text = "<html><head><style @font-face {\n" +
                "                    font-family: \"MyFont\";\n" +
                "                    src: url('file:///android_asset/fonts/BLKCHCRY.TTF');\n" +
                "                } type=\"text/css\">body{color: #247BA0; background-color: #FFFFFF;}</style></head><body>" + description + "</body></html>";


        text_subdetail.loadDataWithBaseURL("", html, mimeType, encoding, "");



    }


}
