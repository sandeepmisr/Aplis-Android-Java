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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.aplis.DemoApplication;
import com.edu.aplis.R;
import com.edu.book.CourseDetail;
import com.edu.discover.Books;
import com.edu.discover.ClickAdapter;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Search_SubCatlists extends AppCompatActivity implements ResponceQueues, ClickAdapter {

    Context context;
    ArrayList<BooksSearch> booksserieslist;
    BooksSearch books;
    RecyclerView recyclerView;
    String uniqure_id ="";
    String uniqure_url ="";
    SearchSubCatAdapter searchBookAdapter;
    TextView text_title;
    TextView text_subtitle;
    TextView categories;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_booklist);

        context= Search_SubCatlists.this;
        text_title = findViewById(R.id.text_title);
        text_subtitle = findViewById(R.id.text_subtitle);
        categories = findViewById(R.id.categories);
        recyclerView = findViewById(R.id.recyclerview);

        categories.setVisibility(View.GONE);
        booksserieslist = new ArrayList<>();
        booksserieslist = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        callanalytics();
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        text_title.setText(getIntent().getStringExtra("title"));
        text_subtitle.setText(getIntent().getStringExtra("description"));
        uniqure_id = getIntent().getStringExtra("unique_id");
        uniqure_url = getIntent().getStringExtra("unique_url");

        Log.e("TAGERROR",uniqure_id+ " "+"des"+getIntent().getStringExtra("description")+" ");

        getDataFromServer(uniqure_id,uniqure_url);


    }

    private void getDataFromServer(String target_id,String url) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.clear();
        ApiService apiService = new ApiService(context,this, url+target_id,hashMap,1);
        apiService.execute();
    }

    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }

    @Override
    public void responceQue(String responce, String url, String extra_text) {
        try{
            Log.e("restry","try "+responce);
            JSONObject jsonObject = new JSONObject(responce);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0;i<jsonArray.length();i++){
                books = new BooksSearch();
                books.setProduct_id(jsonArray.getJSONObject(i).getString("id"));
                books.setName_title(jsonArray.getJSONObject(i).getString("name"));
                books.setMsg(text_title.getText().toString());
                books.setDescription(jsonArray.getJSONObject(i).getString("description"));
                books.setPro_image(jsonArray.getJSONObject(i).getString("description"));
                booksserieslist.add(books);

            }
            searchBookAdapter = new SearchSubCatAdapter(context,booksserieslist,this);
            recyclerView.setAdapter(searchBookAdapter);

//            adddiscoverbooks(booksserieslist);
        }
        catch (Exception e){
            Log.e("restrysub","catch"+e+" "+responce);


        }
    }

    @Override
    public void clickoncard(ImageView view, int position, String id, String content, String title, String uniqure_url, List<Books> booksList, String image_Ar, String discover_id) {

    startActivity(new Intent(context, Search_Booklists.class) .putExtra("title", title)
            .putExtra("description",content)
            .putExtra("unique_id", id)
            .putExtra("unique_url", uniqure_url));
    }

    public void imageback(View view) {
        finish();
    }
}
