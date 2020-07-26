package com.edu.book;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edu.aplis.DemoApplication;
import com.edu.aplis.R;
import com.edu.authors.AuthorDetail;
import com.edu.discover.DiscoverRetrofitModel;
import com.edu.editortemplate.EditorActivity;
import com.edu.editortemplatetwo.EditorSecondActivity;
import com.edu.fav.FavUnFavResponse;
import com.edu.preference.PrefrenceUtils;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;
import com.edu.search.Series;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import at.blogc.android.views.ExpandableTextView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetail extends AppCompatActivity implements View.OnClickListener, ResponceQueues {
    ExpandableTextView text_author;
    TextView txt_start, text_title, text_subname, text_authname, text_pubname, text_pubdate, text_pubisbn, text_publang, text_categories, text_agerange;
    WebView text_subdetail;
    String text_author_id, global_book_id, description;
    TextView text_darkmode;
    Context context;
    CircleImageView image_author;
    ImageView cover_image;
    ImageView cover;
    ImageView imageview_slider;
    ImageView book_share;
    ImageView book_fav;
    HorizontalScrollView horizontalseries;
    LinearLayout linearstart, linearresume, lineartopthreed, linear_notes, ll_scrollseries;
    String sub_name = "";
    HashMap<String, String> hashMap = new HashMap<>();
    ArrayList<String> tagNames;
    RecyclerView recyclerView;
    TagAdapter adapter;
    TextView series;
    ArrayList<Series> serieslist;
    Series seriesmodel;
    public Boolean isFav = false;

    ShimmerFrameLayout shimmerFrameLayout;
    RelativeLayout top;
    String book_coverstr;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.app_bar_coursedetail);
        context = CourseDetail.this;

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
        text_authname = findViewById(R.id.text_authname);
        text_pubname = findViewById(R.id.text_pubname);
        text_pubdate = findViewById(R.id.text_pubdate);
        text_pubisbn = findViewById(R.id.text_pubisbn);
        text_publang = findViewById(R.id.text_publang);
        text_categories = findViewById(R.id.text_categories);
        text_agerange = findViewById(R.id.text_agerange);
        cover_image = findViewById(R.id.circular);
        cover = findViewById(R.id.cover);
        imageview_slider = findViewById(R.id.imageview_slider);
        book_share = findViewById(R.id.book_share);
        book_fav = findViewById(R.id.book_fav);
        series = findViewById(R.id.series);
        horizontalseries = findViewById(R.id.horizontalseries);
        ll_scrollseries = findViewById(R.id.ll_scrollseries);

        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        top = findViewById(R.id.top);

        recyclerView = findViewById(R.id.rvTags);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        serieslist = new ArrayList<>();

        callanalytics();

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
        book_coverstr = (getIntent().getStringExtra("cover_image"));

        cover_image.setTransitionName("cover_image");
        Glide
                .with(context)
                .load(book_coverstr)
                .centerCrop()
                .into(cover_image);
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

    }

    private void addarrivals(final ArrayList<Series> serieslist) {
        if (serieslist.size() == 0) {
            series.setVisibility(View.GONE);
        }

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
                        startActivity(new Intent(context, CourseDetail.class).putExtra("book_id", serieslist.get(finalI).getProduct_id()));

                    }
                });

                ll_scrollseries.addView(view);
            }

        }
    }


    private void makehttpcall() {
        shimmerFrameLayout.startShimmerAnimation();
        String book_id = getIntent().getStringExtra("book_id");
//
//
//        Api api = RetrofitClient.getClient(context, Api.BASE_URL).create(Api.class);
//
//        Call<BookFirstRetrofitModel> call = api.getBookCourseDetails(Cons.BOOKDETAILS_URL+book_id);
////        Log.e("discovertry",call.u);
//        call.enqueue(new Callback<BookFirstRetrofitModel>() {
//            @Override
//            public void onResponse(Call<BookFirstRetrofitModel> call, Response<BookFirstRetrofitModel> response) {
//
//                try {
//                    shimmerFrameLayout.stopShimmerAnimation();
//                    shimmerFrameLayout.setVisibility(View.GONE);
//                    top.setVisibility(View.VISIBLE);
//                    setBookDetails(response.body());
//
//                    Log.e("discovertry", response.body().toString());
//                } catch (Exception e) {
//                    Log.e("discovererror", e + "");
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<BookFirstRetrofitModel> call, Throwable t) {
//                Log.e("discovererror", t.getMessage());
//            }
//        });

//        trustcustom();
        hashMap.clear();
        ApiService apiService = new ApiService(context,this, Cons.BOOKDETAILS_URL+book_id,hashMap,1);
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

            if (url.contains(Cons.BOOKDETAILS_URL)) {
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                top.setVisibility(View.VISIBLE);
                Log.e("APIRESPONSE", responce + " " + "try" + ""+PrefrenceUtils.readString(context, PrefrenceUtils.PREF_ID, ""));

                JSONObject jsonObject = new JSONObject(responce);
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                if (jsonObject1.getJSONArray("fav").toString().contains(PrefrenceUtils.readString(context, PrefrenceUtils.PREF_ID, ""))) {
                    isFav = true;
                    book_fav.setImageDrawable(getDrawable(R.drawable.heartfill));
                } else {
                    book_fav.setImageDrawable(getDrawable(R.drawable.heartblank));

                }
//                for (int j = 0; j < jsonObject1.getJSONArray("fav").length(); j++) {
//                    if (jsonObject1.getJSONArray("fav").getString(j).contains(PrefrenceUtils.readString(context, PrefrenceUtils.PREF_ID, ""))){
//                        book_fav.setImageDrawable(getDrawable(R.drawable.heartfill));
//                    }
//                    Log.e("jsonarray", jsonObject1.getJSONArray("fav").getString(j));
//                }
                String title = jsonObject1.getString("title");
                global_book_id = jsonObject1.getString("id");
                String sub_title = jsonObject1.getString("sub_title");
                description = jsonObject1.getString("description");
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
                    String author_name = jsonObject2.getString("first_name") + " " + jsonObject2.getString("last_name");
                    String author_id = jsonObject2.getString("id");

                    text_author.setText(Html.fromHtml(authorDescription));
                    text_authname.setText(author_name);
                    text_author_id = (author_id);

                    Glide
                            .with(context)
                            .load(author_pic)
                            .centerCrop()
                            .into(image_author);
                    Log.e("Jsondata==", authorDescription);

                } catch (Exception e) {

                }

                try {
                    JSONArray jsonArray = jsonObject1.getJSONArray("other_books");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectseries = jsonArray.getJSONObject(i);
                        String series_image = jsonObjectseries.getString("book_cover");
                        String book_id = jsonObjectseries.getString("id");

                        seriesmodel = new Series();
                        seriesmodel.setPro_image(series_image);
                        seriesmodel.setProduct_id(book_id);
                        serieslist.add(seriesmodel);
                    }
                    Log.e("Jsondata==", serieslist.size() + " " + serieslist);

                    addarrivals(serieslist);

                } catch (Exception e) {
                    Log.e("Jsondata==", e + "");

                }

                text_pubdate.setText(created_at);
                text_agerange.setText(min_age + "-" + max_age);
                text_categories.setText(category_name);
                text_pubname.setText(language);
                text_publang.setText(language);
//            text_subdetail.setWebViewClient(new WebViewClient());

                Resources res = getResources();


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

                text_title.setText(title);
                text_subname.setText(sub_title);
                text_pubisbn.setText(isbn_code);


                Glide
                        .with(context)
                        .load(coverdata)
                        .centerCrop()
                        .into(cover);



                String[] elements = tags.split(",");

                List<String> fixedLenghtList = Arrays.asList(elements);

                tagNames = new ArrayList<String>(fixedLenghtList);

                adapter = new TagAdapter(this, tagNames);

                recyclerView.setAdapter(adapter);


            } else if (url.contains(Cons.ADD_TO_FAVURL)) {
                JSONObject jsonObject = new JSONObject(responce);
                if (jsonObject.getString("status").equals("1")) {
                    book_fav.setImageDrawable(getDrawable(R.drawable.heartfill));
                    isFav = true;
                }

                Log.e("APIRESPONSEFAV", responce);

            } else if (url.contains(Cons.UN_FAVURL)) {
                JSONObject jsonObject = new JSONObject(responce);
                if (jsonObject.getString("status").equals("1")) {
                    book_fav.setImageDrawable(getDrawable(R.drawable.heartblank));
                    isFav = false;
//                    Toast.makeText(context,"successfully deleted",Toast.LENGTH_LONG).show();

//                    makeHttpCall( Cons.GET_FAVURL,1);
                } else {
                    Toast.makeText(context, "something error", Toast.LENGTH_LONG).show();
                }

            }

        } catch (Exception e) {
            Log.e("APIRESPONSE", responce + " " + e + "");

        }

    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width: 100%; width:auto; height: auto;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    public void collecTION(View view) {
//        BottomSheetFragmentBook fragment = new BottomSheetFragmentBook(getIntent().getStringExtra("book_id"), isFav);
//        fragment.show(getSupportFragmentManager(), "TAG");
    }

    public void textauthornameclick(View view) {
        startActivity(new Intent(context, AuthorDetail.class).putExtra("unique_id", text_author_id));
    }

    public void bookLayout(View view) {
        startActivity(new Intent(context, EditorSecondActivity.class).putExtra("book_id", global_book_id)
                .putExtra("book_title", text_title.getText().toString())
                .putExtra("book_subtitle", text_subname.getText().toString())
                .putExtra("book_description", description));
//        startActivity(new Intent(context, EditorActivity.class).putExtra("book_id",global_book_id));

    }

    public void bookfav(View view) {
        Log.e("URLFAV", isFav+"");

        if (isFav) {

            callfavunfavapi(Cons.UN_FAVURL + global_book_id);
//            makeHttpCall(Cons.UN_FAVURL + global_book_id, 2);

        } else {
            callfavunfavapi(Cons.ADD_TO_FAVURL + global_book_id);

//            makeHttpCall(Cons.ADD_TO_FAVURL + global_book_id, 2);
        }

    }


    private void callfavunfavapi(final String url){
        Api api = RetrofitClient.getClient(context,Api.BASE_URL).create(Api.class);

        Call<FavUnFavResponse> call = api.dofavUnfavbook(url);
//        Log.e("discover",call.toString());
        call.enqueue(new Callback<FavUnFavResponse>() {
            @Override
            public void onResponse(Call<FavUnFavResponse> call, Response<FavUnFavResponse> response) {
                Log.e("faverror", response.body() + "");

                try {
//                    shimmerFrameLayout.stopShimmerAnimation();
//                    shimmerFrameLayout.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
                    FavUnFavResponse favUnFavResponse = response.body();
                    if (favUnFavResponse.getStatus().equalsIgnoreCase("1")) {
                        if (url.contains(Cons.ADD_TO_FAVURL)) {
                            book_fav.setImageDrawable(getDrawable(R.drawable.heartfill));
                            isFav = true;
                        }
                        else{
                            book_fav.setImageDrawable(getDrawable(R.drawable.heartblank));
                            isFav = false;

                        }
                        Toast.makeText(context, favUnFavResponse.getEmail(), Toast.LENGTH_LONG).show();

                    }
                } catch (Exception e) {
                    Log.e("faverror", e + "");

                }

            }
            @Override
            public void onFailure(Call<FavUnFavResponse>call, Throwable t) {
                Log.e("errordo",t.getMessage());
            }
        });
    }
    public void bookshare(View view) {
    }

    private void makeHttpCall(String url, int posttype) {
//        trustcustom();
        Log.e("URLFAV", url);
        hashMap.clear();
        ApiService apiService = new ApiService(context, this, url, hashMap, posttype);
        apiService.execute();
    }


    public void read(View view) {
        startActivity(new Intent(context, EditorSecondActivity.class).putExtra("book_id", global_book_id)
                .putExtra("book_title", text_title.getText().toString())
                .putExtra("book_subtitle", text_subname.getText().toString())
                .putExtra("book_description", description));
    }

    private void setBookDetails(BookFirstRetrofitModel body) {
        global_book_id = body.getBookSecondRetrofitModel().getId();
        description = body.getBookSecondRetrofitModel().getDescription();

        text_pubdate.setText(body.getBookSecondRetrofitModel().getCreated_at());
        text_agerange.setText(body.getBookSecondRetrofitModel().getMin_age() + "-" + body.getBookSecondRetrofitModel().getMax_age());
        //    text_categories.setText(body.getBookSecondRetrofitModel().getCa);
        text_pubname.setText(body.getBookSecondRetrofitModel().getLanguage());
        text_publang.setText(body.getBookSecondRetrofitModel().getLanguage());

        setAuthorPic(body);
        setCategoryDetail(body);
        setFavData(body);
        setOtherBookData(body);
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

        text_title.setText(body.getBookSecondRetrofitModel().getTitle());
        text_subname.setText(body.getBookSecondRetrofitModel().getSub_title());
        text_pubisbn.setText(body.getBookSecondRetrofitModel().getIsbn_code());


        Glide
                .with(context)
                .load(body.getBookSecondRetrofitModel().getCover())
                .centerCrop()
                .into(cover);

        Glide
                .with(context)
                .load(body.getBookSecondRetrofitModel().getBook_cover())
                .centerCrop()
                .into(cover_image);

        String[] elements = body.getBookSecondRetrofitModel().getTags().split(",");

        List<String> fixedLenghtList = Arrays.asList(elements);

        tagNames = new ArrayList<String>(fixedLenghtList);

        adapter = new TagAdapter(this, tagNames);

        recyclerView.setAdapter(adapter);


        ;
    }

    private void setOtherBookData(BookFirstRetrofitModel body) {
        for (int i=0;i<body.getBookSecondRetrofitModel().getBookFourthOtherBooksRetrofitModel().size();i++){

        String series_image = body.getBookSecondRetrofitModel().getBookFourthOtherBooksRetrofitModel().get(i).getBook_cover();
        String book_id = body.getBookSecondRetrofitModel().getBookFourthOtherBooksRetrofitModel().get(i).getId();

        seriesmodel = new Series();
        seriesmodel.setPro_image(series_image);
        seriesmodel.setProduct_id(book_id);
        serieslist.add(seriesmodel);
    }

    addarrivals(serieslist);

}



    private void setFavData(BookFirstRetrofitModel body) {

//        Log.e("bodyfav",body.getBookSecondRetrofitModel().);
        if (body.getBookSecondRetrofitModel().getBookthirdRetrofitModel().toString().contains(PrefrenceUtils.readString(context, PrefrenceUtils.PREF_ID, ""))) {
            isFav = true;
            book_fav.setImageDrawable(getDrawable(R.drawable.heartfill));
        }
        else{
            book_fav.setImageDrawable(getDrawable(R.drawable.heartblank));

        }



    }

    private void setAuthorPic(BookFirstRetrofitModel body) {
        text_author.setText(Html.fromHtml(body.getBookSecondRetrofitModel().getBookSixAuthorRetrofitModel().getDescription()));
        text_authname.setText(body.getBookSecondRetrofitModel().getBookSixAuthorRetrofitModel().getFirst_name()+" "+body.getBookSecondRetrofitModel().getBookSixAuthorRetrofitModel().getLast_name());
        text_author_id = (body.getBookSecondRetrofitModel().getBookSixAuthorRetrofitModel().getId());

        Glide
                .with(context)
                .load(body.getBookSecondRetrofitModel().getBookSixAuthorRetrofitModel().getProfile_pic())
                .centerCrop()
                .into(image_author);
    }

    private void setCategoryDetail(BookFirstRetrofitModel body) {
        text_categories.setText(body.getBookSecondRetrofitModel().getBookFifthCategoryRetrofitModel().getName());

    }


    private  OkHttpClient trustcustom(){
        OkHttpClient okHttpClient=null;
        try {
            // Load CAs from an InputStream
// (could be from a resource or ByteArrayInputStream or ...)
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
// From https://www.washington.edu/itconnect/security/ca/load-der.crt
            InputStream caInput = new BufferedInputStream(getResources().openRawResource(R.raw.certfile));
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

// Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

// Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

// Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);

            Log.e("CATCHerror","try");
// Tell the okhttp to use a SocketFactory from our SSLContext
            okHttpClient = new OkHttpClient.Builder().sslSocketFactory(context.getSocketFactory()).build();
        }
        catch (Exception e){
            Log.e("CATCHerror",e+"");
        }
        return okHttpClient;
    }


    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }


}
