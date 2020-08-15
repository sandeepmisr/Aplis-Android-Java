package com.edu.browse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edu.aplis.R;
import com.edu.book.CourseDetail;
import com.edu.editortemplatetwo.EditorSecondSubActivity;
import com.edu.editortemplatetwo.PlayerListener;
import com.edu.preference.PrefrenceUtils;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

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

public class BrowseFragment extends Fragment implements ResponceQueues, ClickBrowseAdapter,OnTouchPosition,PlayerListener {
    public Context context;
    private int pageindex=1;
    private int number_of_index=2;

    RecyclerView rec_view;
    private ArrayList<BrowseModel> browseModelArrayList;
    private ArrayList<BrowseModel> browseBookModelArrayList;
    private ArrayList<BrowseModel> parentbrowseModelArrayList;
    BrowseModel browseModel;
    BrowseModel parenntbrowseModel;

    ParentCatModel parentCatModel;
    BrowseActivityAdapter browseActivityAdapter;
    ParentCategory_ActivityAdapter parentCategory_activityAdapter;
    HashMap<String, String> hashMap = new HashMap<>();
    ShimmerFrameLayout shimmerFrameLayout;

    RecyclerView recviewparentcat;
    LinearLayout ll_scroll;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.browse_fragment, container, false);

        context = getActivity();
        recviewparentcat=rootView.findViewById(R.id.recviewparentcat);
        shimmerFrameLayout=rootView.findViewById(R.id.shimmerFrameLayout);
        rec_view=rootView.findViewById(R.id.rec_view);
        rec_view.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
        rec_view.setItemAnimator(new DefaultItemAnimator());
        recviewparentcat.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        recviewparentcat.setItemAnimator(new DefaultItemAnimator());

        parentbrowseModelArrayList=new ArrayList<>();
        browseBookModelArrayList=new ArrayList<>();


//        trustEveryone();
//        showbookslist();
        showParentList();
        return rootView;
    }

    private void showParentList() {

        Api api = RetrofitClient.getClient(context,Api.BASE_URL).create(Api.class);

        Call<ParentCategory_FirstRetrofitModel> call = api.getParentCategory(Cons.GETPARENTCATURL+"?school_type="+ PrefrenceUtils.readString(context,PrefrenceUtils.PREF_SCHOOLTYPE,""));
//        Log.e("discover",call.toString());
        call.enqueue(new Callback<ParentCategory_FirstRetrofitModel>() {
            @Override
            public void onResponse(Call<ParentCategory_FirstRetrofitModel> call, Response<ParentCategory_FirstRetrofitModel> response) {

                try {
//                    shimmerFrameLayout.stopShimmerAnimation();
//                    shimmerFrameLayout.stopShimmerAnimation();
//                    shimmerFrameLayout.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
//                    shimmerFrameLayout.setVisibility(View.GONE);
                    recviewparentcat.setVisibility(View.VISIBLE);
                    Log.e("discovererror", Cons.GETPARENTCATURL+"?school_type="+ PrefrenceUtils.readString(context,PrefrenceUtils.PREF_SCHOOLTYPE,"") + " "+response.body().getParentCatgoery_thirdRetrofitModelList());

//                    JSONArray jsonArray = new JSONArray(new Gson().toJson(parentCategory_firstRetrofitModel.getParnet_categorySecondRetrofitModel()));
//                    Log.e("discovertry", jsonArray + "");
                } catch (Exception e) {
                    Log.e("discovererror", response.body().getStatus() + ""+Cons.GETPARENTCATURL+"?school_type="+ PrefrenceUtils.readString(context,PrefrenceUtils.PREF_SCHOOLTYPE,""));

                }
                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    setParentCatList(response.body().getParentCatgoery_thirdRetrofitModelList());
                }
            }
            @Override
            public void onFailure(Call<ParentCategory_FirstRetrofitModel>call, Throwable t) {
                Log.e("errordo",t.getMessage());
            }
        });

    }

    private void setParentCatList(List<ParentCatgoery_ThirdRetrofitModel> data) {
        for (int i = 0;i<data.size();i++) {
            parenntbrowseModel = new BrowseModel();
            parenntbrowseModel.setId(data.get(i).getId());
            parenntbrowseModel.setTitle(data.get(i).getTitle());
            parentbrowseModelArrayList.add(parenntbrowseModel);
        }
        parentCategory_activityAdapter = new ParentCategory_ActivityAdapter(context, parentbrowseModelArrayList,this,this);
        recviewparentcat.setAdapter(parentCategory_activityAdapter);
    }

    @Override
    public void clickonplay(String content, SimpleExoPlayerView simpleExoPlayer) {

    }




    private void showbookslist(String ParentID) {
        Log.e("BROWSE_URL090",ParentID);

//        shimmerFrameLayout.setVisibility(View.VISIBLE);

//        if (pageindex>number_of_index) {
//            Log.e("BROWSE_URL","doneshowbookslist");
//
////            Log.e("PAGE_INDEX","done all part"+ " "+getIntent().getStringExtra("category_id"));
//        }
//        else {

            Api api = RetrofitClient.getClient(context,Api.BASE_URL).create(Api.class);

            Call<BrowseFirstUsingParentvRetrofitModel> call = api.getRecentBrowseBookListByParentCategoryID(Cons.GETSUBCATUSING_PARENTCATEGORYID+ParentID);
//        Log.e("discover",call.toString());
            call.enqueue(new Callback<BrowseFirstUsingParentvRetrofitModel>() {
                @Override
                public void onResponse(Call<BrowseFirstUsingParentvRetrofitModel> call, Response<BrowseFirstUsingParentvRetrofitModel> response) {

                    try {
//                    shimmerFrameLayout.stopShimmerAnimation();
//                    shimmerFrameLayout.stopShimmerAnimation();
//                    shimmerFrameLayout.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
//                        shimmerFrameLayout.setVisibility(View.GONE);
                        rec_view.setVisibility(View.VISIBLE);
                        recviewparentcat.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        Log.e("discovererror", e + "");

                    }
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        setBrowseList(response.body().getData().getBrowseSecondUsingParentvRetrofitModels());
                    }
                }
                @Override
                public void onFailure(Call<BrowseFirstUsingParentvRetrofitModel>call, Throwable t) {
                    Log.e("errordo",t.getMessage());
                }
            });

//                hashMap.clear();
//                hashMap.put("page_id", pageindex + "");
//                ApiService task = new ApiService(context, this, Cons.BROWSE_URL+pageindex, hashMap, 1);
//                task.execute();

//        }
    }

    private void setBrowseList(List<BrowseSecondUsingParentvRetrofitModel> data) {
        browseBookModelArrayList.clear();

        Log.e("BROWSE_URL","doneshowbookslisthaiyeah" + ""+ " "+data.size()+"");
        for (int i =0;i<data.size();i++) {
            browseModelArrayList=new ArrayList<>();
            browseModelArrayList.clear();

            String book_name = data.get(i).getName();
            String book_id = data.get(i).getId();

            for (int j = 0; j < data.get(i).getTop_books().size(); j++) {
                browseModel = new BrowseModel();
                browseModel.setRecentid(data.get(i).getTop_books().get(j).getId());
                browseModel.setRecenttitle(data.get(i).getTop_books().get(j).getTitle());
                browseModel.setRecentsubtitle(data.get(i).getTop_books().get(j).getSub_title());
                browseModel.setRecentdescriptior(data.get(i).getTop_books().get(j).getDescription());
                browseModel.setBg_image(data.get(i).getTop_books().get(j).getBook_cover());
                browseModelArrayList.add(browseModel);
            }
            browseModel.setBrowseModels(browseModelArrayList);
            browseModel.setTitle(book_name);
            browseModel.setId(book_id);
            browseBookModelArrayList.add(browseModel);

            //            inflateRecentlyAdded(browseBookModelArrayList);

            Log.e("BROWSE_URL90","doneshowbookslisthaiyeah" +
            ""+ " "+browseBookModelArrayList.get(i).getBrowseModels().size()+" "+browseModel.getBrowseModels().get(0).getSub_title()+"");

        }
//        inflateRecentlyAdded(browseBookModelArrayList);
        browseActivityAdapter = new BrowseActivityAdapter(context, browseBookModelArrayList, this, this);
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
        startActivity(new Intent(context, BrowseActivitySeeAll.class).putExtra("category_id",position+""));
        Log.e("browseseeall","pos"+position+"");
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


    public void inflateRecentlyAdded(ArrayList<BrowseModel> browseBookModelArrayList) {
        Log.e("VIEWS", "1 " + browseBookModelArrayList.size());
        ll_scroll.removeAllViews();
        for (int i = 0; i < browseBookModelArrayList.size(); i++) {
            try {
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.recentaddedbrowse, null);
                TextView tv_name =  view.findViewById(R.id.text_heading);
                TextView text_recent1 =  view.findViewById(R.id.text_recent1);
                LinearLayout ll_adapter =  view.findViewById(R.id.ll_adapter);
                HorizontalScrollView hv =  view.findViewById(R.id.horizontalScrollView);
                tv_name.setText(browseBookModelArrayList.get(i).getTitle());

                for (int j=0;j<browseBookModelArrayList.get(i).getBrowseModels().size();j++) {
                    Log.e("VIEWS","donecatchholdersize1 "+browseBookModelArrayList.get(i).getBrowseModels().get(j).getId());

                    text_recent1.setText(browseBookModelArrayList.get(i).getBrowseModels().get(j).getId());
                }

                ll_scroll.addView(view);
                }

            catch (Exception e) {
                Log.e("VIEWS","1 "+browseBookModelArrayList.size()+" catch"+e.toString());
            }


        }
    }


}
