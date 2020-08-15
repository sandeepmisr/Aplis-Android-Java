package com.edu.browse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.aplis.R;
import com.edu.book.CourseDetail;
import com.edu.editortemplatetwo.EditorSecondSubActivity;
import com.edu.editortemplatetwo.PlayerListener;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;
import com.edu.search.Search_Booklists;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

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

public class BrowseActivitySeeAll extends AppCompatActivity implements ResponceQueues, ClickBrowseAdapter,OnTouchPosition,PlayerListener {
    public Context context;
    private int pageindex=1;
    private int number_of_index=2;
    RecyclerView rec_view;
    private ArrayList<BrowseModel> browseBookModelArrayList;
    BrowseModel browseModel;
    BrowseSeeAllAdapter browseActivityAdapter;
    ShimmerFrameLayout shimmerFrameLayout;
    String catgeory_id="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_activitysee_all);
        context = BrowseActivitySeeAll.this;
        shimmerFrameLayout=findViewById(R.id.shimmerFrameLayout);
        rec_view=findViewById(R.id.rec_view);
        rec_view.setLayoutManager(new GridLayoutManager(this, 2));
        rec_view.setItemAnimator(new DefaultItemAnimator());

        browseBookModelArrayList=new ArrayList<>();
        Log.e("browseseeallif","doneshowbookslistparentid");

        catgeory_id = getIntent().getStringExtra("category_id");
        Log.e("browseseeallif","doneshowbookslistparentid");

        showbookslist(catgeory_id);
    }



    @Override
    public void clickonplay(String content, SimpleExoPlayerView simpleExoPlayer) {

    }




    private void showbookslist(String ParentID) {
        Log.e("browseseeallif","doneshowbookslistparentid");

        shimmerFrameLayout.setVisibility(View.VISIBLE);

        if (pageindex>number_of_index) {
            Log.e("browseseeallif","doneshowbookslist");

//            Log.e("PAGE_INDEX","done all part"+ " "+getIntent().getStringExtra("category_id"));
        }
        else {

            Api api = RetrofitClient.getClient(context, Api.BASE_URL).create(Api.class);

            Call<BrowseFirstRetrofitModel> call = api.getBrowseBookList(Cons.BROWSE_URL + ParentID);
//        Log.e("discover",call.toString());
            call.enqueue(new Callback<BrowseFirstRetrofitModel>() {
                @Override
                public void onResponse(Call<BrowseFirstRetrofitModel> call, Response<BrowseFirstRetrofitModel> response) {

                    try {
                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        rec_view.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        Log.e("browseseeallcatch", e + "");

                    }
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        setBrowseList(response.body().getBrowseSecondRetrofitModel().getData());
                    }
                }

                @Override
                public void onFailure(Call<BrowseFirstRetrofitModel> call, Throwable t) {
                    Log.e("browseseeallerror", t.getMessage());
                }
            });
        }

//                hashMap.clear();
//                hashMap.put("page_id", pageindex + "");
//                ApiService task = new ApiService(context, this, Cons.BROWSE_URL+pageindex, hashMap, 1);
//                task.execute();

//        }
    }

    private void setBrowseList(List<BrowseThirdRetrofitModel> data) {

        Log.e("BROWSE_URL","browseseeall" +
                ""+ " "+data.size()+"");
        for (int i =0;i<data.size();i++) {
            browseModel = new BrowseModel();
            String book_name = data.get(i).getTitle();
            String book_subtitle = data.get(i).getSub_title();
            String book_description = data.get(i).getDescription();
            String book_id = data.get(i).getId();
            String book_cover = data.get(i).getBook_cover();
            browseModel.setTitle(book_name);
            browseModel.setSub_title(book_subtitle);
            browseModel.setDescription(book_description);
            browseModel.setId(book_id);
            browseModel.setBg_image(book_cover);
            browseBookModelArrayList.add(browseModel);
//            inflateRecentlyAdded(browseBookModelArrayList);

            Log.e("BROWSE_URL90","doneshowbookslisthaiyeah" +
            ""+ " "+browseBookModelArrayList.size());

        }

        browseActivityAdapter = new BrowseSeeAllAdapter(context, browseBookModelArrayList, this, this);
        rec_view.setAdapter(browseActivityAdapter);

    }


    @Override
    public void clickoncard(ImageView view, String book_id, String title, String url) {
        Log.e("PAGE_INDEXbook","pos"+book_id+"");

        if (url.equals("parent_category")){
            showbookslist(book_id);
        }
        else {
            try {
                startActivity(new Intent(context, EditorSecondSubActivity.class).putExtra("sub_id", browseBookModelArrayList.get(Integer.parseInt(book_id)).getId())
                        .putExtra("title_name", browseBookModelArrayList.get(Integer.parseInt(book_id)).getTitle())
                        .putExtra("title_subname", browseBookModelArrayList.get(Integer.parseInt(book_id)).getSub_title())
                        .putExtra("title_description", browseBookModelArrayList.get(Integer.parseInt(book_id)).getDescription())
                        .putExtra("title_bgimage", browseBookModelArrayList.get(Integer.parseInt(book_id)).getBg_image())
                );
            }
            catch (Exception e){

            }
//            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(BrowseActivitySeeAll.this, view, "cover_image");
//
//            startActivity(new Intent(context, CourseDetail.class).putExtra("book_id", book_id).putExtra("cover_image", title), activityOptionsCompat.toBundle());
        }

    }

    @Override
    public void position(int position) {
//        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(BrowseActivitySeeAll.this,view,"cover_image");
//
//        startActivity(new Intent(context, CourseDetail.class).putExtra("book_id",id).putExtra("cover_image",content),activityOptionsCompat.toBundle());
        Log.e("PAGE_INDEX","pos"+position+"");
//        showbookslist(book_id);
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

    }

    public void onback(View view) {
        finish();
    }


//    public void inflateRecentlyAdded(ArrayList<BrowseModel> browseBookModelArrayList) {
//        Log.e("VIEWS", "1 " + browseBookModelArrayList.size());
//        ll_scroll.removeAllViews();
//        for (int i = 0; i < browseBookModelArrayList.size(); i++) {
//            try {
//                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                final View view = inflater.inflate(R.layout.recentaddedbrowse, null);
//                TextView tv_name =  view.findViewById(R.id.text_heading);
//                TextView text_recent1 =  view.findViewById(R.id.text_recent1);
//                LinearLayout ll_adapter =  view.findViewById(R.id.ll_adapter);
//                HorizontalScrollView hv =  view.findViewById(R.id.horizontalScrollView);
//                tv_name.setText(browseBookModelArrayList.get(i).getTitle());
//
//                for (int j=0;j<browseBookModelArrayList.get(i).getBrowseModels().size();j++) {
//                    Log.e("VIEWS","donecatchholdersize1 "+browseBookModelArrayList.get(i).getBrowseModels().get(j).getId());
//
//                    text_recent1.setText(browseBookModelArrayList.get(i).getBrowseModels().get(j).getId());
//                }
//
//                ll_scroll.addView(view);
//                }
//
//            catch (Exception e) {
//                Log.e("VIEWS","1 "+browseBookModelArrayList.size()+" catch"+e.toString());
//            }
//
//
//        }
//    }


}
