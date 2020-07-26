package com.edu.discover;

import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.edu.aplis.R;
import com.edu.home.HomeActivity;
import com.edu.preference.PrefrenceUtils;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;
import com.edu.retrofitapi.UserAccount;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
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

import static android.content.Intent.FLAG_ACTIVITY_MULTIPLE_TASK;

public class DiscoverActivity extends Fragment implements ResponceQueues,ClickAdapter {
    RecyclerView recyclerView;
    HashMap<String,String> hashMap=new HashMap<>();
    Context context;
    private  ArrayList<DiscoverModel> discoverModelArrayList;
    DiscoverAdapter discoverAdapter;
    SwipeRefreshLayout swiperefresh;
     String content_url= "";
    ShimmerFrameLayout shimmerFrameLayout;
    ClickAdapter clickAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_discover, container, false);

        context = getActivity();
        clickAdapter=this;
        recyclerView = rootView.findViewById(R.id.recyclerview);
        swiperefresh = rootView.findViewById(R.id.swiperefresh);
        shimmerFrameLayout = rootView.findViewById(R.id.shimmerFrameLayout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        discoverModelArrayList = new ArrayList<DiscoverModel>();

        shimmerFrameLayout.startShimmerAnimation();

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiperefresh.setRefreshing(true);

                getDataFromServer();

            }
        });

        getDataFromServer();

        return rootView;

    }

    private void getDataFromServer() {

        Api api = RetrofitClient.getClient(context,Api.BASE_URL).create(Api.class);

        Call<DiscoverRetrofitModel> call = api.getDiscoverList();
//        Log.e("discover",call.toString());
        call.enqueue(new Callback<DiscoverRetrofitModel>() {
            @Override
            public void onResponse(Call<DiscoverRetrofitModel> call, Response<DiscoverRetrofitModel> response) {

                try {
//                    shimmerFrameLayout.stopShimmerAnimation();
//                    shimmerFrameLayout.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
                    DiscoverRetrofitModel discoverRetrofitArrayModel = response.body();
                    JSONArray jsonArray = new JSONArray(new Gson().toJson(response.body().getData()));
                    Log.e("discovertry", jsonArray + "");
                } catch (Exception e) {
                    Log.e("discovererror", e + "");

                }
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    setDiscoverList(response.body());
                }
            }
            @Override
            public void onFailure(Call<DiscoverRetrofitModel>call, Throwable t) {
                Log.e("errordo",t.getMessage());
            }
        });
//
//        trustEveryone();
//        hashMap.clear();
//
//        ApiService apiService = new ApiService(context,this, Cons.GET_ALL_DISCOVER_URL,hashMap,1);
//        apiService.execute();
    }

    @Override
    public void responceQue(String responce, String url, String extra_text) {
        discoverModelArrayList.clear();
        swiperefresh.setRefreshing(false);
        shimmerFrameLayout.stopShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        Log.e("Response===",url+responce);

        try {
            JSONObject jsonObject = new JSONObject(responce);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            Log.e("Response===",jsonArray.length()+"");

            for (int i=jsonArray.length()-1;i>=0;i--){
                JSONObject jsonObjectseries=jsonArray.getJSONObject(i);
                DiscoverModel discoverModel = new DiscoverModel();
                String id = jsonObjectseries.getString("id");
                String title = jsonObjectseries.getString("title");
                String status = jsonObjectseries.getString("status");
                String title_description = jsonObjectseries.getString("title_description");
                String mime_type = jsonObjectseries.getString("mime_type");
                String header_url = jsonObjectseries.getString("header_url");
                String card_url = jsonObjectseries.getString("card_url");
                String long_description = jsonObjectseries.getString("long_description");
                String image_Ar = jsonObjectseries.getString("ar_url");

                if (title==null||title.equalsIgnoreCase("null")){
                    title = "";
                }

                if (title_description==null||title_description.equalsIgnoreCase("null")){
                    title_description = "";
                }
                discoverModel.setId(id);
                discoverModel.setTitle(title);
                discoverModel.setStatus(status);
                discoverModel.setTitle_description(title_description);
                discoverModel.setMime_type(mime_type);
                discoverModel.setHeader_url(header_url);
                if (card_url==null||card_url.equalsIgnoreCase("null")) {
                    discoverModel.setCard_url(header_url);
                }
                else{
                    discoverModel.setCard_url(card_url);

                }
                discoverModel.setLong_description(long_description);
                discoverModel.setImage_Ar(image_Ar);
                discoverModelArrayList.add(discoverModel);

                if (title.equalsIgnoreCase("")){
                    discoverModel.setTitlecard_visible(8);
                }
                else{
                    discoverModel.setTitlecard_visible(0);

                }
                if (title_description.equalsIgnoreCase("")){
                    discoverModel.setTitldesecard_visible(8);

                }
                else{
                    discoverModel.setTitldesecard_visible(0);

                }
                if (title.isEmpty()&&title_description.isEmpty()){

                    Log.e("Response value===",title+"\t header_url"+title_description);

                    discoverModel.setVisible(8);
                }
                else{
                    discoverModel.setVisible(0);

                }
            }

            discoverAdapter = new DiscoverAdapter(context,discoverModelArrayList,this);
            recyclerView.setAdapter(discoverAdapter);

        } catch (JSONException e) {
            Log.e("JSONEXCEPTION",e+"");
            e.printStackTrace();
        }
    }

    @Override
    public void clickoncard(ImageView view, int position, String mimetype, String content, String title, String long_desc, List<Books> list, String image_Ar, String discover_id) {

        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),view,"imageMain");
//        startActivity(in,activityOptionsCompat.toBundle());
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
        startActivity(new Intent(context, DiscoverDetailActivity.class)

                .putExtra("title",title)
                .putExtra("long_desc",long_desc)
                .putExtra("discover_id",discover_id)
//                .putExtras(bundle)
                .putExtra("mimetype",mimetype).putExtra("content",content)
        .putExtra("image_Ar",image_Ar).putExtra("content_url",content_url)
        ,activityOptionsCompat.toBundle());
//        getActivity().finishAffinity();

        getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        Log.e("POS","AR"+image_Ar+"");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2){
            content_url = data.getStringExtra("content_url");
        }
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

    public void setAnimation(){

//        a.reset();
//        startActivity(new Intent(context, Loginclass.class));

//        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
    }

    public void setDiscoverList(DiscoverRetrofitModel discoverList) {
        discoverModelArrayList.clear();
        for (int i = discoverList.getData().size() - 1; i >= 0; i--) {
            Log.e("discovertryi", i + "");

            DiscoverModel discoverModel = new DiscoverModel();
            String id = discoverList.getData().get(i).getId();
            String title = discoverList.getData().get(i).getTitle();
            String status = discoverList.getData().get(i).getStatus();
            String title_description = discoverList.getData().get(i).getTitle_description();
            String mime_type = discoverList.getData().get(i).getMime_type();
            String header_url = discoverList.getData().get(i).getHeader_url();
            String card_url = discoverList.getData().get(i).getCard_url();
            String long_description = discoverList.getData().get(i).getLong_description();
            String image_Ar = discoverList.getData().get(i).getAr_url();
            if (title == null || title.equalsIgnoreCase("null")) {
                title = "";
            }

            if (title_description == null || title_description.equalsIgnoreCase("null")) {
                title_description = "";
            }
            discoverModel.setId(id);
            discoverModel.setTitle(title);
            discoverModel.setStatus(status);
            discoverModel.setTitle_description(title_description);
            discoverModel.setMime_type(mime_type);
            discoverModel.setHeader_url(header_url);
            if (card_url == null || card_url.equalsIgnoreCase("null")) {
                discoverModel.setCard_url(header_url);
            } else {
                discoverModel.setCard_url(card_url);

            }
            discoverModel.setLong_description(long_description);
            discoverModel.setImage_Ar(image_Ar);
            discoverModelArrayList.add(discoverModel);

            if (title.equalsIgnoreCase("")) {
                discoverModel.setTitlecard_visible(8);
            } else {
                discoverModel.setTitlecard_visible(0);

            }
            if (title_description.equalsIgnoreCase("")) {
                discoverModel.setTitldesecard_visible(8);

            } else {
                discoverModel.setTitldesecard_visible(0);

            }
            if (title.isEmpty() && title_description.isEmpty()) {

                Log.e("Response value===", title + "\t header_url" + title_description);

                discoverModel.setVisible(8);
            } else {
                discoverModel.setVisible(0);

            }
        }

        swiperefresh.setRefreshing(false);
        shimmerFrameLayout.stopShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        discoverAdapter = new DiscoverAdapter(context, discoverModelArrayList, this);
        recyclerView.setAdapter(discoverAdapter);
    }

}
