package com.edu.browse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.aplis.R;
import com.edu.book.CourseDetail;
import com.edu.discover.Books;
import com.edu.discover.ClickAdapter;
import com.edu.discover.DiscoverRetrofitModel;
import com.edu.editortemplatetwo.EditorSecondActivityAdapter;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import com.facebook.shimmer.ShimmerFrameLayout;
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

public class BrowseFragment extends Fragment implements ResponceQueues, ClickBrowseAdapter,OnTouchPosition {
    public Context context;
    private int pageindex=1;
    private int number_of_index=2;

    RecyclerView rec_view;
    private ArrayList<BrowseModel> browseModelArrayList;
    BrowseModel browseModel;
    BworseActivityAdapter browseActivityAdapter;
    HashMap<String, String> hashMap = new HashMap<>();
ShimmerFrameLayout shimmerFrameLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.browse_fragment, container, false);

        context = getActivity();
        rec_view=rootView.findViewById(R.id.rec_view);
        shimmerFrameLayout=rootView.findViewById(R.id.shimmerFrameLayout);
        rec_view.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rec_view.setItemAnimator(new DefaultItemAnimator());

        browseModelArrayList=new ArrayList<>();

//        trustEveryone();
        showbookslist();
        return rootView;
    }

    private void showbookslist() {

        if (pageindex>number_of_index) {
            Log.e("BROWSE_URL","doneshowbookslist");

//            Log.e("PAGE_INDEX","done all part"+ " "+getIntent().getStringExtra("category_id"));
        }
        else {

            Api api = RetrofitClient.getClient(context,Api.BASE_URL).create(Api.class);

            Call<BrowseFirstRetrofitModel> call = api.getBrowseBookList();
//        Log.e("discover",call.toString());
            call.enqueue(new Callback<BrowseFirstRetrofitModel>() {
                @Override
                public void onResponse(Call<BrowseFirstRetrofitModel> call, Response<BrowseFirstRetrofitModel> response) {

                    try {
//                    shimmerFrameLayout.stopShimmerAnimation();
//                    shimmerFrameLayout.stopShimmerAnimation();
//                    shimmerFrameLayout.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        rec_view.setVisibility(View.VISIBLE);
                        BrowseFirstRetrofitModel browseFirstRetrofitModel = response.body();
                        JSONArray jsonArray = new JSONArray(new Gson().toJson(response.body().getBrowseSecondRetrofitModel().getData()));
                        Log.e("discovertry", jsonArray + "");
                    } catch (Exception e) {
                        Log.e("discovererror", e + "");

                    }
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        setBrowseList(response.body());
                    }
                }
                @Override
                public void onFailure(Call<BrowseFirstRetrofitModel>call, Throwable t) {
                    Log.e("errordo",t.getMessage());
                }
            });
            Log.e("BROWSE_URL","doneshowbookslist"+ " "+pageindex+"");

//                hashMap.clear();
//                hashMap.put("page_id", pageindex + "");
//                ApiService task = new ApiService(context, this, Cons.BROWSE_URL+pageindex, hashMap, 1);
//                task.execute();

        }
    }

    private void setBrowseList(BrowseFirstRetrofitModel body) {
        number_of_index = Integer.parseInt(body.getBrowseSecondRetrofitModel().getLast_page());
        for (int i =0;i<body.getBrowseSecondRetrofitModel().getData().size();i++){
            String book_id = body.getBrowseSecondRetrofitModel().getData().get(i).getId();
            String book_title = body.getBrowseSecondRetrofitModel().getData().get(i).getTitle();
            String book_subtitle = body.getBrowseSecondRetrofitModel().getData().get(i).getSub_title();
            String book_cover = body.getBrowseSecondRetrofitModel().getData().get(i).getBook_cover();
            browseModel = new BrowseModel();
            browseModel.setId(book_id);
            browseModel.setTitle(book_title);
            browseModel.setSub_title(book_subtitle);
            browseModel.setImage(book_cover);

            browseModelArrayList.add(browseModel);
        }
        browseActivityAdapter = new BworseActivityAdapter(context, browseModelArrayList,this,this);
        rec_view.setAdapter(browseActivityAdapter);
        rec_view.scrollToPosition((pageindex-1)*10);
        pageindex++;


    }


    @Override
    public void responceQue(String responce, String url, String extra_text) {
    Log.e("BROWSE_URL","done"+url+ " "+responce);
try{
//    Log.e("BROWSE_URL","donetry");

    JSONObject jsonObject=new JSONObject(responce);
    number_of_index = Integer.parseInt(jsonObject.getJSONObject("data").getString("last_page"));
    JSONArray jsonArray=jsonObject.getJSONObject("data").getJSONArray("data");


}
catch (Exception e){
    Log.e("BROWSE_URL","donecatch"+e+"");


}





    }


    @Override
    public void clickoncard(ImageView view, String book_id, String title) {
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),view,"cover_image");

        startActivity(new Intent(context, CourseDetail.class).putExtra("book_id",book_id).putExtra("cover_image",title),activityOptionsCompat.toBundle());

    }

    @Override
    public void position(int position) {
        Log.e("PAGE_INDEX","pos"+position+"");
        showbookslist();
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
