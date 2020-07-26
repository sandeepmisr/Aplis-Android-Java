package com.edu.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.aplis.DemoApplication;
import com.edu.aplis.R;
import com.edu.book.CourseDetail;
import com.edu.discover.Books;
import com.edu.discover.ClickAdapter;
import com.edu.discover.DiscoverBooksAdapter;
import com.edu.discover.DiscoverDetailActivity;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Search_Booklists extends AppCompatActivity implements ResponceQueues, ClickAdapter {

    Context context;
    ArrayList<BooksSearch> booksserieslist;
    BooksSearch books;
    RecyclerView recyclerView;
    String uniqure_id ="";
    String uniqure_url ="";
    SearchBookAdapter searchBookAdapter;
    TextView text_title;
    TextView text_subtitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_booklist);

        context= Search_Booklists.this;
        text_title = findViewById(R.id.text_title);
        text_subtitle = findViewById(R.id.text_subtitle);
        recyclerView = findViewById(R.id.recyclerview);
        booksserieslist = new ArrayList<>();
        booksserieslist = new ArrayList<>();
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        callanalytics();
        text_title.setText(getIntent().getStringExtra("title"));
        if(getIntent().getStringExtra("description").startsWith("Category")) {
            text_subtitle.setText(getIntent().getStringExtra("description").replace("Category",""));
        }
        else{
            text_subtitle.setText(getIntent().getStringExtra("description"));

        }
        uniqure_id = getIntent().getStringExtra("unique_id");
        uniqure_url = getIntent().getStringExtra("unique_url");

//        Log.e("TAGERROR","des"+getIntent().getStringExtra("description")+" "+uniqure_url);

        getDataFromServer(uniqure_id,uniqure_url);


    }


    private void getDataFromServer(String target_id,String url) {
        Log.e("TAGERROR","des"+" "+uniqure_url+target_id);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.clear();
        ApiService apiService = new ApiService(context,this, url+target_id,hashMap,1);
        apiService.execute();
    }

    @Override
    public void responceQue(String responce, String url, String extra_text) {
        try{
            Log.e("restry","try");
            JSONObject jsonObject = new JSONObject(responce);
            if (url.contains(Cons.GET_FAVURL)){
//                JSONObject jsonObject1 = jsonObject.getJSONArray("data").getJSONObject(0);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0;i<jsonArray.length();i++){
                    books = new BooksSearch();
                    books.setProduct_id(jsonArray.getJSONObject(i).getString("id"));
                    books.setName_title(jsonArray.getJSONObject(i).getString("title"));
                    books.setMsg(jsonArray.getJSONObject(i).getString("sub_title"));
                    books.setPro_image(jsonArray.getJSONObject(i).getString("book_cover"));
                    booksserieslist.add(books);

                }
            }
            else {
                JSONObject jsonObject1 = jsonObject.getJSONArray("data").getJSONObject(0);
                JSONArray jsonArray = jsonObject1.getJSONArray("books");
                for (int i = 0; i < jsonArray.length(); i++) {
                    books = new BooksSearch();
                    books.setProduct_id(jsonArray.getJSONObject(i).getString("id"));
                    books.setName_title(jsonArray.getJSONObject(i).getString("title"));
                    books.setMsg(jsonArray.getJSONObject(i).getString("sub_title"));
                    books.setPro_image(jsonArray.getJSONObject(i).getString("book_cover"));
                    booksserieslist.add(books);

                }
            }
            searchBookAdapter = new SearchBookAdapter(context,booksserieslist,this);
            recyclerView.setAdapter(searchBookAdapter);

//            adddiscoverbooks(booksserieslist);
        }
        catch (Exception e){
            Log.e("restry","catch"+e+"");


        }
    }

    @Override
    public void clickoncard(ImageView view, int position, String id, String content, String title, String long_desc, List<Books> booksList, String image_Ar, String discover_id) {
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(Search_Booklists.this,view,"cover_image");

        startActivity(new Intent(context, CourseDetail.class).putExtra("book_id",id).putExtra("cover_image",content),activityOptionsCompat.toBundle());
    }

    public void imageback(View view) {
        finish();
    }

    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }


}
