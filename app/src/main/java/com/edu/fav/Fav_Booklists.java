package com.edu.fav;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.aplis.R;
import com.edu.book.CourseDetail;
import com.edu.browse.BrowseFirstRetrofitModel;
import com.edu.discover.Books;
import com.edu.discover.ClickAdapter;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;
import com.edu.search.BooksSearch;
import com.edu.search.SearchBookAdapter;
import com.edu.search.Search_Booklists;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fav_Booklists extends AppCompatActivity implements ResponceQueues, ClickAdapter {
    Context context;
    ArrayList<BooksSearch> booksserieslist;
    BooksSearch books;
    RecyclerView recyclerView;
    String uniqure_id = "";
    String uniqure_url = "";
    FavBookAdapter favBookAdapter;
    TextView text_title;
    TextView text_subtitle;
    TextView text_search;
    LinearLayout coordinatorLayout;
    private Paint p = new Paint();
ClickAdapter clickAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_booklist);

        context = Fav_Booklists.this;
        clickAdapter=this;
        text_title = findViewById(R.id.text_title);
        text_subtitle = findViewById(R.id.text_subtitle);
        text_search = findViewById(R.id.text_search);
        recyclerView = findViewById(R.id.recyclerview);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        booksserieslist = new ArrayList<>();
        booksserieslist = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
//        int resId = R.anim.layout_animation_fall_down;
//        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, resId);
//        recyclerView.setLayoutAnimation(animation);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


//        uniqure_id = getIntent().getStringExtra("unique_id");
        uniqure_url = Cons.GET_FAVURL;
//        uniqure_url = getIntent().getStringExtra("unique_url");

//        Log.e("TAGERROR","des"+getIntent().getStringExtra("description")+" "+uniqure_url);

        makeHttpCall(uniqure_url,1);

//        enableSwipe();
    }

    private void makeHttpCall( String url, int posttype) {

        Api api = RetrofitClient.getClient(context,Api.BASE_URL).create(Api.class);

        Call<FavRetrofitModel> call = api.getFavBookLists(url);
//        Log.e("discover",call.toString());
        call.enqueue(new Callback<FavRetrofitModel>() {
            @Override
            public void onResponse(Call<FavRetrofitModel> call, Response<FavRetrofitModel> response) {

                try {

                    FavRetrofitModel favRetrofitModel = response.body();
                    JSONArray jsonArray = new JSONArray(new Gson().toJson(favRetrofitModel.getData()));

                    for (int i = 0; i < jsonArray.length(); i++) {
                        books = new BooksSearch();
                        books.setProduct_id(favRetrofitModel.getData().get(i).getId());
                        books.setName_title(favRetrofitModel.getData().get(i).getTitle());
                        books.setMsg(favRetrofitModel.getData().get(i).getSub_title());
                        books.setPro_image(favRetrofitModel.getData().get(i).getBook_cover());

                        booksserieslist.add(books);
                    }

                    favBookAdapter = new FavBookAdapter(context, booksserieslist, clickAdapter);
                    recyclerView.setAdapter(favBookAdapter);
                    Log.e("discovertry", jsonArray + "");
                } catch (Exception e) {
                    Log.e("discovererror", e + "");

                }

            }
            @Override
            public void onFailure(Call<FavRetrofitModel>call, Throwable t) {
                Log.e("errordo",t.getMessage());
            }
        });
//        trustEveryone();
//        Log.e("TAGERROR", "des" + " " + url);
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.clear();
//        ApiService apiService = new ApiService(context, this, url , hashMap, posttype);
//        apiService.execute();
    }

    @Override
    public void responceQue(String responce, String url, String extra_text) {
        Log.e("restry", "try" + "\n" + responce);

        try {

            if (url.contains(Cons.GET_FAVURL)) {
                booksserieslist.clear();
                Log.e("restry", "try" + "\n" + responce);
                JSONObject jsonObject = new JSONObject(responce);
//                JSONObject jsonObject1 = jsonObject.getJSONArray("data").getJSONObject(0);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    books = new BooksSearch();
                    books.setProduct_id(jsonArray.getJSONObject(i).getString("id"));
                    books.setName_title(jsonArray.getJSONObject(i).getString("title"));
                    books.setMsg(jsonArray.getJSONObject(i).getString("sub_title"));
                    books.setPro_image(jsonArray.getJSONObject(i).getString("book_cover"));

//                    JSONArray jsonArray1  = jsonArray.getJSONArray(i).getString("fav");
//
//                    for (int j = 0;j<js)
                    booksserieslist.add(books);
                }

//                if (booksserieslist.size()>0){
//                    text_search.setVIs
//                }
                favBookAdapter = new FavBookAdapter(context, booksserieslist, this);
                recyclerView.setAdapter(favBookAdapter);
            }
            else if (url.contains(Cons.UN_FAVURL)){
                JSONObject jsonObject = new JSONObject(responce);
                if (jsonObject.getString("status").equals("1")) {
                    Toast.makeText(context,"successfully deleted",Toast.LENGTH_LONG).show();

                    makeHttpCall( Cons.GET_FAVURL,1);
                }
                else{
                    Toast.makeText(context,"something error",Toast.LENGTH_LONG).show();
                }

            }


//            adddiscoverbooks(booksserieslist);
        } catch (Exception e) {
            Log.e("restry", "catch" + e + "");


        }
    }

    @Override
    public void clickoncard(ImageView view, int position, String id, String content, String title, String long_desc, List<Books> booksList, String image_Ar, String discover_id) {
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(Fav_Booklists.this,view,"cover_image");

        startActivity(new Intent(context, CourseDetail.class).putExtra("book_id", id).putExtra("cover_image",content),activityOptionsCompat.toBundle());
    }

    public void imageback(View view) {
        finish();
    }
    private void enableSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){
                    final BooksSearch deletedModel = booksserieslist.get(position);
                    final int deletedPosition = position;
                    Log.e("TAGERROR1", "des1" + " " + Cons.UN_FAVURL + booksserieslist.get(position).getProduct_id());

                    makeHttpCall(Cons.UN_FAVURL + booksserieslist.get(position).getProduct_id(),2);
                    favBookAdapter.removeItem(position);
                    // showing snack bar with Undo option
//                    Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), " removed from Recyclerview!", Snackbar.LENGTH_LONG);
//                    snackbar.setAction("UNDO", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            // undo is selected, restore the deleted item
//                            favBookAdapter.restoreItem(deletedModel, deletedPosition);
//                        }
//                    });
//                    snackbar.setActionTextColor(Color.YELLOW);
//                    snackbar.show();
                } else {
                    final BooksSearch deletedModel = booksserieslist.get(position);
                    final int deletedPosition = position;
                    favBookAdapter.removeItem(position);

                    makeHttpCall(Cons.UN_FAVURL + booksserieslist.get(position).getProduct_id(),2);

                    // showing snack bar with Undo option
//                    Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), " removed from Recyclerview!", Snackbar.LENGTH_LONG);
//                    snackbar.setAction("UNDO", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            // undo is selected, restore the deleted item
//                            favBookAdapter.restoreItem(deletedModel, deletedPosition);
//                        }
//                    });
//                    snackbar.setActionTextColor(Color.YELLOW);
//                    snackbar.show();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_delete);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_delete);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
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


}
