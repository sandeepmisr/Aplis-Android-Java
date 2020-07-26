package com.edu.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.bumptech.glide.Glide;
import com.edu.aplis.DemoApplication;
import com.edu.authors.AuthorDetail;
import com.edu.book.CourseDetail;
import com.edu.aplis.R;
import com.edu.discover.Books;
import com.edu.discover.ClickAdapter;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class SearchResultActivity extends AppCompatActivity implements ResponceQueues, ClickAdapter {
    TextView text_nopro;
    Context context=null;
    EditText edittext_search;
    TextView text_cancel;

    HashMap<String,String> hashMap= new HashMap<>();

    ArrayList<Authors> authorsArrayList;
    ArrayList<BooksSearch> booksSearchArrayList;
    ArrayList<String> booksSearchstringArrayList;
    HashMap<String,String> hashMapbook=new HashMap();
    HashMap<String,String> hashMapauthor=new HashMap();
    HashMap<String,String> hashMapseries=new HashMap();
    ArrayList<Series> seriesArrayList;
    ArrayList<Categories> categoriesArrayList;
    ArrayList<TopResults> topResultsArrayList;

    Authors authors;
    BooksSearch booksSearch;
    Series series;
    Categories categories_model;

    TopResults topResults_model;
    LinearLayout ll_scroll_books;
    LinearLayout ll_scroll_authors;
    LinearLayout ll_scroll_series;
    LinearLayout ll_scroll_category;
    LinearLayout ll_scroll_topresults;

    TextView author;
    TextView books;
    TextView collections;
    TextView categories;
    TextView top_results;

    String global_books_image;
    String global_books;
    String global_authors;

    View view_ts;
    View view_book;
    View view_collection;
    View view_authors;
    View view_cat;

    TextView button1;

    LinearLayout layoutnoresult;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.searchresult);
        context= SearchResultActivity.this;


        text_nopro= findViewById(R.id.text_nopro);
        text_cancel= findViewById(R.id.text_cancel);
        edittext_search= findViewById(R.id.edittext_search);
        ll_scroll_books =  findViewById(R.id.ll_scroll_books);
        ll_scroll_authors =  findViewById(R.id.ll_scroll_authors);
        ll_scroll_series =  findViewById(R.id.ll_scroll_series);
        ll_scroll_category =  findViewById(R.id.ll_scroll_category);
        ll_scroll_topresults =  findViewById(R.id.ll_scroll_topresults);

        layoutnoresult =  findViewById(R.id.layoutnoresult);

        author =  findViewById(R.id.authors);
        books =  findViewById(R.id.books);
        collections =  findViewById(R.id.collections);
        categories =  findViewById(R.id.categories);
        top_results =  findViewById(R.id.top_result);

        view_cat =  findViewById(R.id.view_cat);
        view_ts =  findViewById(R.id.view_ts);
        view_book =  findViewById(R.id.view_book);
        view_collection =  findViewById(R.id.view_collection);
        view_authors =  findViewById(R.id.view_authors);
        button1 =  findViewById(R.id.button1);

        authorsArrayList =new ArrayList<>();
        booksSearchArrayList =new ArrayList<>();
        booksSearchstringArrayList =new ArrayList<>();
        seriesArrayList =new ArrayList<>();
        categoriesArrayList =new ArrayList<>();
        topResultsArrayList =new ArrayList<>();

        callanalytics();
        edittext_search.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        edittext_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()==0){
                    edittext_search.setCompoundDrawablesWithIntrinsicBounds( 0, 0,0, 0);

                }
                else {
                    edittext_search.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.close, 0);
                }

            }
        });

        edittext_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                trustEveryone();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (edittext_search.getText().toString().contains(" ")){
                        hashMap.clear();
                        makehttpCall(Cons.GET_URL_SEARCHITEM+edittext_search.getText().toString().split(" ")[0],hashMap);
                    }
                    else{
                        hashMap.clear();
                        makehttpCall(Cons.GET_URL_SEARCHITEM+edittext_search.getText().toString(),hashMap);
                    }
                    return true;
                }

                return false;
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext_search.setText("");

                authorsArrayList.clear();
                booksSearchArrayList.clear();
                seriesArrayList.clear();
                categoriesArrayList.clear();
                topResultsArrayList.clear();

                ll_scroll_topresults.removeAllViews();
                ll_scroll_category.removeAllViews();
                ll_scroll_series.removeAllViews();
                ll_scroll_authors.removeAllViews();
                ll_scroll_books.removeAllViews();

                inflatebooks(booksSearchArrayList);
                inflateTopResults(topResultsArrayList);
                inflateSeries(seriesArrayList);
                inflateauthors(authorsArrayList);
                inflatecategories(categoriesArrayList);

                author.setVisibility(View.GONE);
                view_authors.setVisibility(View.GONE);

                books.setVisibility(View.GONE);
                view_book.setVisibility(View.GONE);

                collections.setVisibility(View.GONE);
                view_collection.setVisibility(View.GONE);

                categories.setVisibility(View.GONE);
                view_cat.setVisibility(View.GONE);

                top_results.setVisibility(View.GONE);
                top_results.setVisibility(View.GONE);



            }
        });

//        edittext_search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (edittext_search.getText().toString().length()==4)
//
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        toolbartext.setText(getIntent().getStringExtra("key"));

//        hashMap.put("keyword",getIntent().getStringExtra("key"));

    }


    private void makehttpCall(String url, HashMap<String, String> hashMap) {
        Log.e("LOGGING","running");
        ApiService task = new ApiService(context,this, url, hashMap,1);
        task.execute();
    }

    @Override
    public void responceQue(String responce, String url, String extra_text) {
        try {
            booksSearchstringArrayList.clear();
            booksSearchArrayList.clear();
            authorsArrayList.clear();
            seriesArrayList.clear();
            categoriesArrayList.clear();
            topResultsArrayList.clear();
            hashMapbook.clear();
            hashMapauthor.clear();
            hashMapseries.clear();
            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(edittext_search.getWindowToken(), 0);
            JSONObject jsonObject = new JSONObject(responce);
if (jsonObject.getString("status").equalsIgnoreCase("1")){
    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
    JSONArray jsonArraybooks = jsonObject1.getJSONArray("books");

}
            if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                try {
                    JSONArray jsonArraybooks = jsonObject1.getJSONArray("books");

                    topResults_model  =new TopResults();
                    topResults_model.setName_title((jsonArraybooks.getJSONObject(0).getString("title")));
                    topResults_model.setMsg(("Book"));
                    topResults_model.setPro_image(jsonArraybooks.getJSONObject(0).getString("book_cover"));
                    topResults_model.setProduct_id(jsonArraybooks.getJSONObject(0).getString("id"));
                    topResultsArrayList.add(topResults_model);

                    for (int i = 0; i < jsonArraybooks.length(); i++) {
                        String title = jsonArraybooks.getJSONObject(i).getString("title");
                        String sub_title = jsonArraybooks.getJSONObject(i).getString("sub_title");
                        String book_cover = jsonArraybooks.getJSONObject(i).getString("book_cover");
                        String book_id = jsonArraybooks.getJSONObject(i).getString("id");

                        try {
                            JSONObject jsonObject2 = jsonArraybooks.getJSONObject(i).getJSONObject("author");
                            String author_title = jsonObject2.getString("first_name") + " " + jsonObject2.getString("last_name");
                            String author_profile_pic = jsonObject2.getString("profile_pic");
                            String author_id = jsonObject2.getString("id");
                            authors = new Authors();
                            authors.setName_title(author_title);
                            authors.setProduct_id(author_id);
                            authors.setPro_image(author_profile_pic);
                            if (hashMapauthor.containsKey(author_id)){
                                Log.e("afterophashmapauthor","if"+author_id);

                            }
                            else{

                                authorsArrayList.add(authors);
                                Log.e("afterophashmapauthor","else"+author_id +" "+authorsArrayList.size()+"");

                            }
                            hashMapauthor.put(author_id,"id");
                        }
                        catch (Exception e){
                            Log.e("afterophashmapauthor","else"+e+"");

                        }

                        try {
                            JSONObject jsonObject3 = jsonArraybooks.getJSONObject(i).getJSONObject("series");
                            Log.e("afterophashmap","if"+jsonObject3);
                            String series_title = jsonObject3.getString("title");
                            String series_id= jsonObject3.getString("id");
                            series = new Series();
                            series.setName_title(series_title);
                            series.setMsg(jsonObject3.getString("description"));
                            series.setProduct_id(series_id);

                            if (hashMapseries.containsKey(series_id)){
                                Log.e("afterophashmap","if"+title);

                            }
                            else{
                                Log.e("afterophashmap","else"+title);

                                seriesArrayList.add(series);
                            }
                            hashMapseries.put(series_id,"id");
                        }
                        catch (Exception e){

                        }

                        global_books_image = jsonArraybooks.getJSONObject(0).getString("book_cover");
                        global_books = jsonArraybooks.getJSONObject(0).getString("title");
                        booksSearch = new BooksSearch();
                        booksSearch.setProduct_id(book_id);
                        booksSearch.setName_title(title);
                        booksSearch.setMsg(sub_title);
                        booksSearch.setPro_image(book_cover);
//                        Log.e("afterophashmapbooksSearch","if"+jsonArray.getJSONObject(j));

//                        Log.e("afterophashmap",title);
                        if (hashMapbook.containsKey(book_id)){
                            Log.e("afterophashmap","if"+title);

                        }
                        else{
                            Log.e("afterophashmap","else"+title);
if (!booksSearchstringArrayList.contains(book_id)) {
    booksSearchArrayList.add(booksSearch);
    booksSearchstringArrayList.add(book_id);
}
                        }
                        hashMapbook.put(book_id,"id");


//                        try {
//                            JSONObject jsonObject4 = jsonArraybooks.getJSONObject(i).getJSONObject("category");
//
//                            topResults_model  =new TopResults();
//                            topResults_model.setName_title((jsonObject4.getString("name")));
//                            if (jsonObject4.getString("parent_category_id").equalsIgnoreCase("null")){
//                                topResults_model.setMsg(("Category"));
//                                topResults_model.setDescriptiom(jsonObject4.getString("description"));
//                            }
//                            else{
//                                topResults_model.setMsg("Category");
//                                topResults_model.setDescriptiom(jsonObject4.getString("description"));
//
////                        topResults_model.setMsg(jsonArraycategories.getJSONObject(0).getString("parent_category_id"));
//
//                            }
//                            topResults_model.setProduct_id(jsonObject4.getString("parent_category_id"));
//                            topResults_model.setC_id(jsonObject4.getString("id"));
//                            topResults_model.setPro_image(global_books_image);
//                            topResultsArrayList.add(topResults_model);
//                            for (int j = 0; j < jsonObject4.length(); j++) {
//                                categories_model = new Categories();
//                                if (jsonObject4.getJSONObject("parent").getString("parent_category_id").equalsIgnoreCase("null")){
//                                    categories_model.setMsg(jsonObject4.getJSONObject("parent").getString("description"));
//
//                                }
//                                else{
//                                    categories_model.setMsg(jsonObject4.getJSONObject("parent").getString("name"));
//                                }
//                                categories_model.setName_title(jsonObject4.getJSONObject("parent").getString("name"));
//                                categories_model.setDescription(jsonObject4.getJSONObject("parent").getString("description"));
//                                categories_model.setPro_image(global_books_image);
//                                Log.e("TAGERROR","parent_id"+jsonObject4.getJSONObject("parent").getString("parent_category_id"));
//                                categories_model.setProduct_id(jsonObject4.getJSONObject("parent").getString("parent_category_id"));
//                                categories_model.setC_id(jsonObject4.getJSONObject("parent").getString("id"));
//                                categoriesArrayList.add(categories_model);
//                            }
//                        }
//                        catch (Exception e){
//Log.e("name",e+"");
//                        }




                    }






                } catch (Exception e) {
                    Log.e("BOOKS",e+"");
                }



                try {
                    JSONArray jsonArraycategories = jsonObject1.getJSONArray("categories");

                    topResults_model  =new TopResults();
                    topResults_model.setName_title((jsonArraycategories.getJSONObject(0).getString("name")));
                    if (jsonArraycategories.getJSONObject(0).getString("parent_category_id").equalsIgnoreCase("null")){
                        topResults_model.setMsg(("Category"));
                        topResults_model.setDescriptiom(jsonArraycategories.getJSONObject(0).getString("description"));
                    }
                    else{
                        topResults_model.setMsg("Category");
                        topResults_model.setDescriptiom(jsonArraycategories.getJSONObject(0).getString("description"));

//                        topResults_model.setMsg(jsonArraycategories.getJSONObject(0).getString("parent_category_id"));

                    }
                    topResults_model.setProduct_id(jsonArraycategories.getJSONObject(0).getString("parent_category_id"));
                    topResults_model.setC_id(jsonArraycategories.getJSONObject(0).getString("id"));
                    topResults_model.setPro_image(global_books_image);
                    topResultsArrayList.add(topResults_model);
                    for (int i = 0; i < jsonArraycategories.length(); i++) {
                        categories_model = new Categories();
                        if (jsonArraycategories.getJSONObject(i).getString("parent_category_id").equalsIgnoreCase("null")){
                            categories_model.setMsg(jsonArraycategories.getJSONObject(i).getString("description"));

                        }
                        else{
                            categories_model.setMsg(jsonArraycategories.getJSONObject(i).getJSONObject("parent").getString("name"));
                        }
                        categories_model.setName_title(jsonArraycategories.getJSONObject(i).getString("name"));
                        categories_model.setDescription(jsonArraycategories.getJSONObject(i).getString("description"));
                        categories_model.setPro_image(global_books_image);
                        Log.e("TAGERROR","parent_id"+jsonArraycategories+"\n"+jsonArraycategories.getJSONObject(i).getString("parent_category_id"));
                        categories_model.setProduct_id(jsonArraycategories.getJSONObject(i).getString("parent_category_id"));
                        categories_model.setC_id(jsonArraycategories.getJSONObject(i).getString("id"));
                        categoriesArrayList.add(categories_model);
                    }
                }
                catch (Exception e){

                }

                try {
                    JSONArray jsonArrayauthors = jsonObject1.getJSONArray("authers");
                    topResults_model  =new TopResults();
                    topResults_model.setName_title((jsonArrayauthors.getJSONObject(0).getString("first_name") + " " + jsonArrayauthors.getJSONObject(0).getString("last_name")));
                    topResults_model.setMsg(("Author"));
                    topResults_model.setPro_image(jsonArrayauthors.getJSONObject(0).getString("profile_pic"));
                    topResults_model.setProduct_id(jsonArrayauthors.getJSONObject(0).getString("id"));
                    topResultsArrayList.add(topResults_model);
                    for (int i = 0; i < jsonArrayauthors.length(); i++) {
                        String title = jsonArrayauthors.getJSONObject(i).getString("first_name") + " " + jsonArrayauthors.getJSONObject(i).getString("last_name");
                        String book_cover = jsonArrayauthors.getJSONObject(i).getString("profile_pic");
                        String author_id = jsonArrayauthors.getJSONObject(i).getString("id");

                        global_authors  = jsonArrayauthors.getJSONObject(0).getString("profile_pic");

                        JSONArray jsonArray = jsonArrayauthors.getJSONObject(i).getJSONArray("books");

        for (int j= 0;j<jsonArray.length();j++){
            booksSearch = new BooksSearch();
            booksSearch.setName_title(jsonArray.getJSONObject(j).getString("title"));
            booksSearch.setMsg(jsonArray.getJSONObject(j).getString("sub_title"));
            booksSearch.setPro_image(jsonArray.getJSONObject(j).getString("book_cover"));
            booksSearch.setProduct_id(jsonArray.getJSONObject(j).getString("id"));

            Log.e("afterophashmapbooksSearchauthers","if"+jsonArray.getJSONObject(j));

            if (hashMapbook.containsKey(booksSearch.getProduct_id())){
                if (!booksSearchstringArrayList.contains(booksSearch.getProduct_id())) {
                    booksSearchArrayList.add(booksSearch);
                    booksSearchstringArrayList.add(booksSearch.getProduct_id());
                }
                Log.e("afterophashmap1","if"+booksSearch.getName_title());
            }
            else{
                Log.e("afterophashmap","else"+booksSearch.getName_title());

                if (!booksSearchstringArrayList.contains(booksSearch.getProduct_id())) {
                    booksSearchArrayList.add(booksSearch);
                    booksSearchstringArrayList.add(booksSearch.getProduct_id());
                }

            }
            hashMapbook.put(booksSearch.getProduct_id(),"id");
//            booksSearchArrayList.add(booksSearch);
//            booksSearchArrayList =  getUniqueValues(booksSearchArrayList);


        }
                        authors = new Authors();
                        authors.setName_title(title);
                        authors.setProduct_id(author_id);
                        authors.setPro_image(book_cover);
                        if (hashMapauthor.containsKey(author_id)){
                            Log.e("afterophashmap","if"+title);

                        }
                        else{
                            Log.e("afterophashmap","else"+title);

                            authorsArrayList.add(authors);
                        }
                        hashMapauthor.put(author_id,"id");


                    }


                } catch (Exception e) {
                }

                try {

                    JSONArray jsonArrayseries = jsonObject1.getJSONArray("series");
                    for (int i = 0; i < jsonArrayseries.length(); i++) {
                        String title = jsonArrayseries.getJSONObject(i).getString("title");
                        String series_id = jsonArrayseries.getJSONObject(i).getString("id");
                        String series_description = jsonArrayseries.getJSONObject(i).getString("description");

                        JSONArray jsonArray = jsonArrayseries.getJSONObject(i).getJSONArray("books");

                        for (int j= 0;j<jsonArray.length();j++){
                            booksSearch = new BooksSearch();
                            booksSearch.setName_title(jsonArray.getJSONObject(j).getString("title"));
                            booksSearch.setMsg(jsonArray.getJSONObject(j).getString("sub_title"));
                            booksSearch.setPro_image(jsonArray.getJSONObject(j).getString("book_cover"));
                            booksSearch.setProduct_id(jsonArray.getJSONObject(j).getString("id"));

                            Log.e("afterophashmapbooksSearch","if"+booksSearch.getProduct_id());

//                            Log.e("afterophashmap",booksSearch.getName_title());
                            if (hashMapbook.containsKey(booksSearch.getProduct_id())){
                                if (!booksSearchstringArrayList.contains(booksSearch.getProduct_id())) {
                                    booksSearchArrayList.add(booksSearch);
                                    booksSearchstringArrayList.add(booksSearch.getProduct_id());
                                }
                                Log.e("afterophashmapseries","if"+booksSearch.getName_title());

                            }
                            else{
                                Log.e("afterophashmapseries","else"+booksSearch.getName_title());
                                if (!booksSearchstringArrayList.contains(booksSearch.getProduct_id())) {
                                    booksSearchArrayList.add(booksSearch);
                                    booksSearchstringArrayList.add(booksSearch.getProduct_id());
                                }


                            }
                            hashMapbook.put(booksSearch.getProduct_id(),"id");
//                            booksSearchArrayList.add(booksSearch);
//                            booksSearchArrayList =  getUniqueValues(booksSearchArrayList);
//                            booksSearchArrayList = (ArrayList) booksSearchArrayList.stream().distinct().collect(Collectors.toList());

                        }
                        series = new Series();
                        series.setProduct_id(series_id);
                        series.setName_title(title);
                        series.setMsg(series_description);

                        if (hashMapseries.containsKey(series_id)){
                            Log.e("afterophashmap","if"+title);

                        }
                        else{
                            Log.e("afterophashmap","else"+title);

                            seriesArrayList.add(series);
                        }
                        hashMapseries.put(series_id,"id");



                    }
                }
                catch(Exception e){

                    }



                if (booksSearchArrayList.size()>0) {
                    books.setVisibility(View.VISIBLE);
                    view_book.setVisibility(View.VISIBLE);
                    inflatebooks(booksSearchArrayList);
                }
                else {
                    books.setVisibility(View.GONE);
                }

                books.setVisibility(View.VISIBLE);
                view_book.setVisibility(View.VISIBLE);
                inflatebooks(booksSearchArrayList);
                if (categoriesArrayList.size()>0) {
                    categories.setVisibility(View.VISIBLE);
                    inflatecategories(categoriesArrayList);
                    view_cat.setVisibility(View.VISIBLE);
                }
                else {
                    categories.setVisibility(View.GONE);
                }

                if (topResultsArrayList.size()>0) {
                    top_results.setVisibility(View.VISIBLE);
                    inflateTopResults(topResultsArrayList);

                }

                if(seriesArrayList.size()>0) {
                    collections.setVisibility(View.VISIBLE);
                    view_collection.setVisibility(View.VISIBLE);
                    inflateSeries(seriesArrayList);
                }
                else{
                }

                if(authorsArrayList.size()>0){
                    author.setVisibility(View.VISIBLE);
                    view_authors.setVisibility(View.VISIBLE);
                    inflateauthors(authorsArrayList);
                }
                else {
                    author.setVisibility(View.GONE);
                }

                if (booksSearchArrayList.size()==0&&authorsArrayList.size()==0&&seriesArrayList.size()==0&&topResultsArrayList.size()==0 &&categoriesArrayList.size()==0){
                        layoutnoresult.setVisibility(View.VISIBLE);
                    }
                    else{
                        layoutnoresult.setVisibility(View.GONE);


                }
            }

            else{
                text_nopro.setVisibility(View.GONE);
            }
            Log.e("SearchClikRes",responce);
        }
        catch (Exception e){

        }
    }

    public void onClick(View view) {
        finish();
    }

    @Override
    public void clickoncard(ImageView view,int position, String mimetype, String content, String title, String long_desc, List<Books> booksList, String image_Ar, String discover_id) {
//        startActivity(new Intent(context, ProductDetailActivity.class).putExtra("product_id",id));

    }


    public void inflateTopResults(final ArrayList<TopResults> topResultsArrayList) {
//        Log.e("VIEWSNEWSTORES", "1 " + list.size());
        ll_scroll_topresults.removeAllViews();
        for (int i = 0; i < topResultsArrayList.size(); i++) {
            try {
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.search_adapter, null);
                TextView text_name = (TextView) view.findViewById(R.id.text_title);
                TextView text_subtitle = (TextView) view.findViewById(R.id.text_subtitle);

                ImageView pro_image = (ImageView) view.findViewById(R.id.image1);
                ImageView image1_circle = (ImageView) view.findViewById(R.id.image1_circle);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        1
                );

                View viewline =  view.findViewById(R.id.view);

                viewline.setVisibility(View.VISIBLE);
                params.setMargins((int)getResources().getDimension(R.dimen.dp20), 0, 0, 0);
                viewline.setLayoutParams(params);
                if(i==topResultsArrayList.size()-1){
                    params.setMargins(0, 0, 0, 0);
                    viewline.setLayoutParams(params);



                }
                text_name.setText(topResultsArrayList.get(i).getName_title());
                text_subtitle.setText(topResultsArrayList.get(i).getMsg());

                if (topResultsArrayList.get(i).getMsg().equalsIgnoreCase("author")) {
                    image1_circle.setVisibility(View.VISIBLE);
                    pro_image.setVisibility(View.GONE);

                    Glide.with(context)
                            .load(topResultsArrayList.get(i).getPro_image())
                            .centerCrop()
                            .into(image1_circle);
                }
                Glide.with(context)
                        .load(topResultsArrayList.get(i).getPro_image())
                        .centerCrop()
                        .into(pro_image);

                final int finalI = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {

                            if (topResultsArrayList.get(finalI).getMsg().equalsIgnoreCase("author")){
                                startActivity(new Intent(context, AuthorDetail.class).putExtra("unique_id", topResultsArrayList.get(finalI).getProduct_id())
                                        .putExtra("unique_url", Cons.AUTHORSDETAILS_URL));
                            }
                            else if (topResultsArrayList.get(finalI).getMsg().equalsIgnoreCase("category")){
                                if (!topResultsArrayList.get(finalI).getProduct_id().equalsIgnoreCase("null")) {
                                    startActivity(new Intent(context, Search_Booklists.class)
                                            .putExtra("title", topResultsArrayList.get(finalI).getName_title())
                                            .putExtra("description", topResultsArrayList.get(finalI).getDescriptiom())
                                            .putExtra("unique_id", topResultsArrayList.get(finalI).getC_id())
                                            .putExtra("unique_url", Cons.SUBCATTOBOOK_URL));
                                }

                                else{
                                            startActivity(new Intent(context, Search_SubCatlists.class)
                                            .putExtra("title", topResultsArrayList.get(finalI).getName_title())
                                            .putExtra("description", topResultsArrayList.get(finalI).getDescriptiom())
                                            .putExtra("unique_id", topResultsArrayList.get(finalI).getC_id())
                                            .putExtra("unique_url", Cons.SUBCATTODETAIL_URL));
                                }
                            }

                            else if (topResultsArrayList.get(finalI).getMsg().equalsIgnoreCase("book")){
                                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SearchResultActivity.this,view,"cover_image");

                                startActivity(new Intent(context, CourseDetail.class).putExtra("book_id",topResultsArrayList.get(finalI).getProduct_id()).putExtra("cover_image",topResultsArrayList.get(finalI).getPro_image())
                                                ,activityOptionsCompat.toBundle());

                            }
                        }catch (Exception e){

                        }
                    }
                });
                ll_scroll_topresults.addView(view);

            }
            catch (Exception e) {
                Log.e("VIEWS","1 "+topResultsArrayList.size()+" catch"+e.toString());
            }


        }
    }



    public void inflatebooks(final ArrayList<BooksSearch> list) {
        Log.e("VIEWSNEWSTORESBOOK", "1 " + list.size());
        ll_scroll_books.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            try {
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.search_adapter, null);
                TextView text_name = (TextView) view.findViewById(R.id.text_title);
                TextView text_subtitle = (TextView) view.findViewById(R.id.text_subtitle);

                ImageView pro_image = (ImageView) view.findViewById(R.id.image1);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        1
                );

                View viewline =  view.findViewById(R.id.view);

                viewline.setVisibility(View.VISIBLE);
                params.setMargins((int)getResources().getDimension(R.dimen.dp20), 0, 0, 0);
                viewline.setLayoutParams(params);
                if(i==list.size()-1){
                    params.setMargins(0, 0, 0, 0);
                    viewline.setLayoutParams(params);

                }
                text_name.setText(list.get(i).getName_title());
                text_subtitle.setText(list.get(i).getMsg());

                Glide.with(context)
                        .load(list.get(i).getPro_image())
                        .centerCrop()
                        .into(pro_image);

                final int finalI = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SearchResultActivity.this,view,"cover_image");

                            startActivity(new Intent(context, CourseDetail.class).putExtra("book_id",list.get(finalI).getProduct_id()).putExtra("cover_image",list.get(finalI).getPro_image()),activityOptionsCompat.toBundle());
//                            click(list.get(finalI).getC_id(),"seller_products"+" "+list.get(finalI1).getName_title());
//                            productclick(list.get(finalI).getProduct_id());
                        }catch (Exception e){

                        }
                    }
                });
                ll_scroll_books.addView(view);

            }
            catch (Exception e) {
                Log.e("VIEWS","1 "+list.size()+" catch"+e.toString());
            }


        }
    }

    public void inflatecategories(final ArrayList<Categories> list) {
        Log.e("VIEWSNEWSTORES", "1 " + list.size());
        ll_scroll_category.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            try {
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.search_adapter, null);
                TextView text_name = (TextView) view.findViewById(R.id.text_title);
                TextView text_subtitle = (TextView) view.findViewById(R.id.text_subtitle);

                ImageView pro_image = (ImageView) view.findViewById(R.id.image1);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        1
                );

                View viewline =  view.findViewById(R.id.view);

                viewline.setVisibility(View.VISIBLE);
                params.setMargins((int)getResources().getDimension(R.dimen.dp20), 0, 0, 0);
                viewline.setLayoutParams(params);
                if(i==list.size()-1){
                    params.setMargins(0, 0, 0, 0);
                    viewline.setLayoutParams(params);

                }
                text_name.setText(list.get(i).getName_title());
                text_subtitle.setText(list.get(i).getMsg());

                Glide.with(context)
                        .load(list.get(i).getPro_image())
                        .centerCrop()
                        .into(pro_image);

                final int finalI = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (!list.get(finalI).getProduct_id().equalsIgnoreCase("null")) {
                                startActivity(new Intent(context, Search_Booklists.class)
                                        .putExtra("title", list.get(finalI).getName_title())
                                        .putExtra("description", list.get(finalI).getDescription())
                                        .putExtra("unique_id", list.get(finalI).getC_id())
                                        .putExtra("unique_url", Cons.SUBCATTOBOOK_URL));
                            }
                            else{
                                Log.e("TAGERROR",list.get(finalI).getProduct_id());
                                startActivity(new Intent(context, Search_SubCatlists.class)
                                        .putExtra("title", list.get(finalI).getName_title())
                                        .putExtra("description", list.get(finalI).getDescription())
                                        .putExtra("unique_id", list.get(finalI).getC_id())
                                        .putExtra("unique_url", Cons.SUBCATTODETAIL_URL));
                            }

//                            click(list.get(finalI).getC_id(),"seller_products"+" "+list.get(finalI1).getName_title());
//                            productclick(list.get(finalI).getProduct_id());
                        }catch (Exception e){

                        }
                    }
                });
                ll_scroll_category.addView(view);

            }
            catch (Exception e) {
                Log.e("VIEWS","1 "+list.size()+" catch"+e.toString());
            }


        }
    }


    public void inflateauthors(final ArrayList<Authors> list) {
        Log.e("VIEWSNEWSTORES", "1 " + list.size());
        ll_scroll_authors.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            try {
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.author_search_adapter, null);
                TextView text_name = (TextView) view.findViewById(R.id.text_title);
                ImageView pro_image = (ImageView) view.findViewById(R.id.image1);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1
                );

                View viewline =  view.findViewById(R.id.view);

                viewline.setVisibility(View.VISIBLE);
                params.setMargins((int)getResources().getDimension(R.dimen.dp20), 0, 0, 0);
                viewline.setLayoutParams(params);
                if(i==list.size()-1){
                    params.setMargins(0, 0, 0, 0);
                    viewline.setLayoutParams(params);

                }
                text_name.setText(list.get(i).getName_title());

                Glide.with(context)
                        .load(list.get(i).getPro_image())
                        .centerCrop()
                        .into(pro_image);

                final int finalI = i;
                final int finalI1 = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            startActivity(new Intent(context, AuthorDetail.class).putExtra("unique_id",list.get(finalI).getProduct_id())
                            .putExtra("unique_url",Cons.AUTHORSDETAILS_URL));

//                            click(list.get(finalI).getC_id(),"seller_products"+" "+list.get(finalI1).getName_title());
//                            productclick(list.get(finalI).getProduct_id());
                        }catch (Exception e){

                        }
                    }
                });
                ll_scroll_authors.addView(view);

            }
            catch (Exception e) {
                Log.e("VIEWS","1 "+list.size()+" catch"+e.toString());
            }


        }
    }

    public void inflateSeries(final ArrayList<Series> list) {
        Log.e("VIEWSNEWSTORES", "1 " + list.size());
        ll_scroll_series.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            try {
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.serach_series, null);
                TextView text_name = (TextView) view.findViewById(R.id.text_title);


                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1
                );

                View viewline =  view.findViewById(R.id.view);

                viewline.setVisibility(View.VISIBLE);
                params.setMargins((int)getResources().getDimension(R.dimen.dp20), 0, 0, 0);
                viewline.setLayoutParams(params);
                if(i==list.size()-1){
                    params.setMargins(0, 0, 0, 0);
                    viewline.setLayoutParams(params);

                }
                text_name.setText(list.get(i).getName_title());



                final int finalI = i;
                final int finalI1 = i;

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                         startActivity(new Intent(context, Search_Booklists.class)
                                 .putExtra("title", list.get(finalI).getName_title())
                                 .putExtra("description", list.get(finalI).getMsg())
                                 .putExtra("unique_id",list.get(finalI).getProduct_id())
                            .putExtra("unique_url",Cons.SERIESTOBOOK_URL));
                        }catch (Exception e){

                        }
                    }
                });
                ll_scroll_series.addView(view);

            }
            catch (Exception e) {
                Log.e("VIEWS","1 "+list.size()+" catch"+e.toString());
            }


        }
    }

    public  ArrayList<BooksSearch> sortList (List<BooksSearch> myList){
        Log.e("SEARCHARRAYLIST","afteroplist"+myList.size()+"");

        Set<BooksSearch> hashsetList = new HashSet<BooksSearch>(myList);
        Log.e("SEARCHARRAYLIST","afteropset"+hashsetList.size()+"");

        ArrayList<BooksSearch> list = new ArrayList<BooksSearch>(hashsetList);
        Log.e("SEARCHARRAYLIST","afteropsettolist"+list.size()+"");

        System.out.printf("\nUnique values using HashSet: %s%n", hashsetList);

return list;
    }


    public void text_cancel(View view) {
        finish();
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

    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }

}
