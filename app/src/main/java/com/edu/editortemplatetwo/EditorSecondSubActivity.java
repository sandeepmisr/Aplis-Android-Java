package com.edu.editortemplatetwo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.barchartpojo.Screenshot;
import com.bumptech.glide.Glide;
import com.editorsecondsubactivitypojo.SecondRetrofitModel;
import com.editorsecondsubactivitypojo.ThirdRetrofitModel;
import com.edu.aplis.DemoApplication;
import com.edu.aplis.MainActivity;
import com.edu.aplis.R;
import com.edu.chatsub_sub.Piechartsub_subActivtiy;
import com.edu.discover.Books;
import com.edu.discover.ClickAdapter;
import com.edu.editortemplate.EditoroneAdapter;
import com.edu.fab.FABsMenu;
import com.edu.fab.FABsMenuListener;
import com.edu.fab.TitleFAB;
import com.edu.piechart.BarchartActivitynew;
import com.edu.piechart.PiechartActivtiy;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;
import com.edu.sketch.SketchActivity;
import com.edu.timeline.TimeLineActivity;
import com.edu.web.ViewActivity;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.util.RFABShape;
import com.wangjie.rapidfloatingactionbutton.util.RFABTextUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import at.blogc.android.views.ExpandableTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorSecondSubActivity extends AppCompatActivity implements ResponceQueues,ClickAdapter {
    TextView text_title,text_subtitle;
    ExpandableTextView text_description;
    LinearLayout ll_scrollseriesoftitle;
    HashMap<String,String> hashMap=new HashMap<>();
    Context context;
    private  ArrayList<EditorSubModel> editorModelArrayList;
    EditorSubModel editorSubModel;
    EditoroneAdapter editoroneAdapter;
    String title_id= "";
    String title_name= "";
    String title_subname= "";
    String title_description= "";
    String bg_image = "";
    RecyclerView rec_view;
    EditorSecondSubActivityAdapter editorSecondSubActivityAdapter;
    ImageView layout_bg;
    ProgressBar progressbar;
    ShimmerFrameLayout shimmerFrameLayout;

    LinearLayout viewpagertopiclayer;
    RelativeLayout root;
    CardView cardimage_Ar;
    CardView cardimage_visualaize;
    ImageView image_visualaize;

    private FABsMenu fabMenu;
    String global_weburl ="";
    String global_webid ="";
    String global_webheading ="";
    String global_websub_heading ="";
    String global_websub_description ="";

    RelativeLayout parent;
    VideoView videoView;
    private PlayerView videoSurfaceView;
    private SimpleExoPlayer videoPlayer;
    private FrameLayout frameLayout;
    private int playPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondsubeditor);
        context = EditorSecondSubActivity.this;

        viewpagertopiclayer = findViewById(R.id.viewpagertopiclayer);
        cardimage_Ar = findViewById(R.id.cardimage_Ar);
        cardimage_visualaize = findViewById(R.id.cardimage_visualaize);
        image_visualaize = findViewById(R.id.image_visualaize);
        text_title = findViewById(R.id.text_title);
        text_subtitle = findViewById(R.id.text_subtitle);
        text_description = findViewById(R.id.text_subdetail);
        rec_view = findViewById(R.id.rec_view);
        progressbar = findViewById(R.id.progressBar);
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        layout_bg = findViewById(R.id.layout_bg);

//        rfaLayout = findViewById(R.id.activity_main_rfal);
//        rfaBtn = findViewById(R.id.activity_main_rfab);
        layout_bg = findViewById(R.id.layout_bg);

        fabMenu = findViewById(R.id.fabs_menu);

        //callanalytics();
        fabMenu.setMenuListener(new FABsMenuListener() {


            // You don't need to override all methods. Just the ones you want.

            @Override
            public void onMenuClicked(FABsMenu fabsMenu) {
                super.onMenuClicked(fabsMenu); // Default implementation opens the menu on click
                // showToast("You pressed the menu!");

            }

            @Override
            public void onMenuCollapsed(FABsMenu fabsMenu) {
                super.onMenuCollapsed(fabsMenu);
                // showToast("The menu has been collapsed!");

                Log.e("clicked","cicl====");

            }

            @Override
            public void onMenuExpanded(FABsMenu fabsMenu) {
                super.onMenuExpanded(fabsMenu);
                // showToast("The menu has been expanded!");
            }
        });

        TitleFAB diaryFab = findViewById(R.id.diaryfab);
        diaryFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicksketchedit(layout_bg,"white",bg_image);
            }
        });

        TitleFAB noticeFab = findViewById(R.id.noticefab);
        noticeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicksketchedit(layout_bg,"transparent",bg_image);
                //getToast("Notice");
                // ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(EditorSecondSubActivity.this,layout_bg,"imagesketch");

                // startActivity(new Intent(context, SketchActivity.class).putExtra("color","transparent2").putExtra("sub_name",bg_image),activityOptionsCompat.toBundle());
//                Log.e("clicked","cicl===="+bg_image+"https://images.unsplash.com/photo-1546146477-15a587cd3fcb?ixlib=rb-1.2.1&w=1000&q=80");
            }
        });
        editorModelArrayList= new ArrayList<>();
        title_id = getIntent().getStringExtra("sub_id");
        title_name = getIntent().getStringExtra("title_name");
        title_subname = getIntent().getStringExtra("title_subname");
        title_description = getIntent().getStringExtra("title_description");
        bg_image = getIntent().getStringExtra("title_bgimage");

        text_title.setText(title_name);
        text_subtitle.setText(title_subname);
        cardimage_Ar.setVisibility(View.GONE);

        cardimage_visualaize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewpagertopiclayer.getVisibility()==View.GONE){

                    Transition transition = new Fade();
                    transition.setDuration(600);
                    transition.addTarget(R.id.viewpagertopiclayer);

                    TransitionManager.beginDelayedTransition(viewpagertopiclayer, transition);

                    image_visualaize.setImageResource(R.drawable.visuliaze);
                    viewpagertopiclayer.setVisibility(View.VISIBLE);
//                    root.setBackgroundColor(getResources().getColor(R.color.textCol));


                }
                else{
                    Transition transition = new Fade();
                    transition.setDuration(600);
                    transition.addTarget(R.id.viewpagertopiclayer);

                    TransitionManager.beginDelayedTransition(viewpagertopiclayer, transition);
                    image_visualaize.setImageResource(R.drawable.un_eye);
                    viewpagertopiclayer.setVisibility(View.GONE);
//                    root.setBackgroundColor(getResources().getColor(R.color.white));


                }
            }
        });

        cardimage_Ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAR("");
            }
        });
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false);
        rec_view.setLayoutManager(linearLayoutManager);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL);
//        rec_view.setLayoutManager(linearLayoutManager);
//        rec_view.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));


        Log.e("TAGEDITORbg_image",bg_image + title_id);

//        layout_bg.setTransitionName("titlebg");
//           imageView.load(url);
        Glide.with(context)
                .load(bg_image)
                .centerCrop()
                .into(layout_bg);



        String html = "<html><style type=\"text/css\"> @font-face {font-family: MyFont;src: url('file:///android_asset/fonts/playfairdisplayregular.ttf'); font-weight: normal; font-style: normal} @font-face {font-family: MyFontBOLD;src: url('file:///android_asset/fonts/playfairdisplaybold.ttf'); font-weight: bold; font-style: normal} @font-face {font-family: MyFontITALIC;src: url('file:///android_asset/fonts/playfairdisplayitalic.ttf'); font-weight: normal; font-style: italic}"+
                "h1{\n" +
                "font-family: MyFont;"+
                "font-size: 18px;\n" +
                "}\n" +
                "\n" +
                "h2{\n" +
                "font-family: MyFont;"+
                "  font-size: 16px;\n" +
                "}\n" +
                "p {\n" +
                "font-family: MyFont;"+
                "  font-size: 14px;\n" +
                "}\n" +
                "body {\n" +
                "font-family: MyFont;"+
                "  background-color: white;\n" +
                "  color: black;\n" +
                "}\n" +
                "</style> \n <body>"+title_description+"</body></html>";
        final String mimeType = "text/html";
        final String encoding = "UTF-8";

        text_description.setText(Html.fromHtml(title_description));

        text_description.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                if (text_description.isExpanded())
                {
                    text_description.collapse();
//                    buttonToggle.setText(R.string.expand);
                }
                else
                {
                    text_description.expand();
//                    buttonToggle.setText(R.string.collapse);
                }
            }
        });

        getDataFromServer();
    }

    private void getDataFromServer() {
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();
        Api api = RetrofitClient.getClient(context, Api.BASE_URL).create(Api.class);

        Call<SecondRetrofitModel> call = api.getSUBCHAPTERBYCHAPTERID(Cons.GET_SUBCHAPTERBYCHAPTERID+title_id);
//        Log.e("discovertry",call.u);
        call.enqueue(new Callback<SecondRetrofitModel>() {
            @Override
            public void onResponse(Call<SecondRetrofitModel> call, Response<SecondRetrofitModel> response) {

                try {shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.GONE);

                    setrecyclerdataadapter(response.body().getThirdRetrofitModels());
//                    shimmerFrameLayout.stopShimmerAnimation();
//                    shimmerFrameLayout.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
//                    BookFirstRetrofitModel discoverRetrofitArrayModel = response.body();
//                    JSONArray jsonArray = new JSONArray(new Gson().toJson(response.body().getBookSecondRetrofitModel()));
                    Log.e("editortry", response.toString() + " "+response.message());
                } catch (Exception e) {
                    Log.e("editorerror", e + "");

                }
//                if (response.body().getStatus().equalsIgnoreCase("1")) {
//                    setBookDetails(response.body());
//                }
            }

            @Override
            public void onFailure(Call<SecondRetrofitModel> call, Throwable t) {
                Log.e("errordo", t.getMessage());
            }
        });
//        hashMap.clear();
//        Log.e("TAGEDITOR",Cons.GET_SUBCHAPTERBYCHAPTERID+title_id);
//        ApiService apiService = new ApiService(context,this, Cons.GET_SUBCHAPTERBYCHAPTERID+title_id,hashMap,1);
//        apiService.execute();
    }


    private void setrecyclerdataadapter(List<ThirdRetrofitModel> thirdRetrofitModels) {
        editorModelArrayList.clear();

        for (int i = 0; i < thirdRetrofitModels.size(); i++) {
            String mime_type = "";
            String is_timeline = thirdRetrofitModels.get(i).getIs_timeline();
            String is_graphp = thirdRetrofitModels.get(i).getIs_graphp();
            String is_pichart =thirdRetrofitModels.get(i).getIs_pichart();
            String is_column = thirdRetrofitModels.get(i).getIs_column();
            String is_website =thirdRetrofitModels.get(i).getIs_website();

            String color_code =thirdRetrofitModels.get(i).getColor_code();
            String btn_image =thirdRetrofitModels.get(i).getBtn_image();
            String show_button_text =thirdRetrofitModels.get(i). getShow_button_text();
            if (is_timeline.equalsIgnoreCase("1")){

                mime_type = "timeline";
            }
            else if (is_graphp.equalsIgnoreCase("1")){
                mime_type = "graph";

            }

            else if (is_pichart.equalsIgnoreCase("1")){
                mime_type = "pie_chart";


            }else if (is_column.equalsIgnoreCase("1")){
                mime_type = "column";


            }
            else if (is_website.equalsIgnoreCase("1")){
                mime_type = "web";

            }
            else{
                mime_type = "text";
            }
            editorSubModel = new EditorSubModel();
            editorSubModel.setMime_type(mime_type);

            editorSubModel.setId(thirdRetrofitModels.get(i).getId());
            editorSubModel.setTitle_id(thirdRetrofitModels.get(i).getTitle_id());
            editorSubModel.setTitle(thirdRetrofitModels.get(i).getTitle());
            editorSubModel.setSub_title(thirdRetrofitModels.get(i).getSub_heading());
            editorSubModel.setDescription(thirdRetrofitModels.get(i).getLong_description());

            if (color_code.contains("#")) {
                editorSubModel.setBtn_image(color_code);
            }
            else{
                editorSubModel.setBtn_image(btn_image);

            }
            editorSubModel.setBtn_bg_image(btn_image);
            editorSubModel.setColor_code(color_code);
            editorSubModel.setShow_button_text(show_button_text);
            editorSubModel.setAr_url(thirdRetrofitModels.get(i).getAr_url());


            editorModelArrayList.add(editorSubModel);
            Log.e("titlenull","null"+thirdRetrofitModels.get(i).getTitle());


        }
//            toogle();
        editorSecondSubActivityAdapter = new EditorSecondSubActivityAdapter(context,editorModelArrayList,this);
        rec_view.setAdapter(editorSecondSubActivityAdapter);
    }




    @Override
    public void responceQue(String responce, String url, String extra_text) {
        editorModelArrayList.clear();
        Log.e("Response===",url+responce);

        try {
            JSONObject jsonObject = new JSONObject(responce);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            Log.e("Response===",jsonArray.length()+"");

            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObjectsubeditordetail=jsonArray.getJSONObject(i);
                String mime_type = "";
                String id = jsonObjectsubeditordetail.getString("id");
                String title_id = jsonObjectsubeditordetail.getString("topic_id");
                String title = jsonObjectsubeditordetail.getString("heading");
                String sub_heading = jsonObjectsubeditordetail.getString("sub_heading");
                String ar_url = jsonObjectsubeditordetail.getString("ar_url");
                String long_description = jsonObjectsubeditordetail.getString("html_content");
                String bg_image = jsonObjectsubeditordetail.getString("bg_image");
                String btn_image = jsonObjectsubeditordetail.getString("btn_image");
                String color_code = jsonObjectsubeditordetail.getString("color_code");
                String show_button_text = jsonObjectsubeditordetail.getString("show_button_text");

                String is_timeline = jsonObjectsubeditordetail.getString("is_timeline");
                String is_graphp = jsonObjectsubeditordetail.getString("is_graphp");
                String is_pichart = jsonObjectsubeditordetail.getString("is_pichart");
                String is_column = jsonObjectsubeditordetail.getString("is_column");
                String is_website = jsonObjectsubeditordetail.getString("is_website");


                if (is_timeline.equalsIgnoreCase("1")){

                    mime_type = "timeline";
                }
                else if (is_graphp.equalsIgnoreCase("1")){
                    mime_type = "graph";

                }

                else if (is_pichart.equalsIgnoreCase("1")){
                    mime_type = "pie_chart";


                }else if (is_column.equalsIgnoreCase("1")){
                    mime_type = "column";


                }else if (is_website.equalsIgnoreCase("1")){
                    mime_type = "web";


                }
                else{
                    mime_type = "text";
                }
                Log.e("clickver",mime_type);
                editorSubModel = new EditorSubModel();

                editorSubModel.setMime_type(mime_type);
                editorSubModel.setId(id);
                editorSubModel.setTitle_id(title_id);
                editorSubModel.setTitle(title);
                editorSubModel.setSub_title(sub_heading);
                editorSubModel.setDescription(long_description);

                if (color_code.contains("#")) {
                    editorSubModel.setBtn_image(color_code);
                }
                else{
                    editorSubModel.setBtn_image(btn_image);

                }
                editorSubModel.setBtn_bg_image(btn_image);
                editorSubModel.setColor_code(color_code);
                editorSubModel.setShow_button_text(show_button_text);
                editorSubModel.setAr_url(ar_url);
                editorModelArrayList.add(editorSubModel);

            }
//            toogle();
            editorSecondSubActivityAdapter = new EditorSecondSubActivityAdapter(context,editorModelArrayList,this);
            rec_view.setAdapter(editorSecondSubActivityAdapter);

        } catch (JSONException e) {
            Log.e("JSONEXCEPTION",e+"");
            e.printStackTrace();
        }

    }

    @Override



    public void clickoncard(ImageView view,int position,String mimetype,String content,String title, String long_desc,List<Books> list,String image_Ar,String id) {
        Log.e("Editorsub_subtopic1",mimetype+ " id"+id+ " pos"+position+image_Ar);

        switch (mimetype){
            case "web":
//                startActivity(new Intent(context, ViewActivity.class)
//                        .putExtra("sub_sub_id_s",position+"")
//                        .putExtra("subtitle_name",global_webheading)
//                        .putExtra("subtitle_subname",global_websub_heading)
//                        .putExtra("ar_url",global_weburl)
//                        .putExtra("subtitle_description",global_websub_description));
//
                startActivity(new Intent(context, ViewActivity.class)
                        .putExtra("sub_sub_id_s",position+"")
                        .putExtra("subtitle_name",title)
                        .putExtra("subtitle_subname",content)
                        .putExtra("ar_url",image_Ar)
                        .putExtra("subtitle_description",long_desc));
                break;

            case "text":
                Log.e("Responsenew","animate");
                startActivityForResult(new Intent(context, EditorSecondSub_subActivity.class)
                        .putExtra("sub_sub_id_s",position+"")
                        .putExtra("subtitle_name",title)
                        .putExtra("subtitle_subname",content)
                        .putExtra("ar_url",image_Ar)
                        .putExtra("subtitle_description",long_desc),2);
//                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                break;
            case "pie_chart":
                startActivity(new Intent(context, Piechartsub_subActivtiy.class)
                        .putExtra("sub_sub_id_s",position+"")
                        .putExtra("subtitle_name",title)
                        .putExtra("subtitle_subname",content)
                        .putExtra("ar_url",image_Ar)
                        .putExtra("subtitle_description",long_desc)
                        .putExtra("url",Cons.GET_SUBCHAPTERYBDETAILCHAPTERID));
                break;
            case "bar_chart":
                startActivity(new Intent(context, BarchartActivitynew.class)
                        .putExtra("sub_sub_id_s",position+"")
                        .putExtra("subtitle_name",title)
                        .putExtra("subtitle_subname",content)
                        .putExtra("subtitle_description",long_desc)
                        .putExtra("ar_url",image_Ar)
                        .putExtra("url",Cons.GET_SUBCHAPTERYBDETAILCHAPTERID));
                break;
            case "timeline":
                startActivity(new Intent(context, TimeLineActivity.class)
                        .putExtra("timeline_id",position+"")
                        .putExtra("subtitle_name",title)
                        .putExtra("subtitle_subname",content)
                        .putExtra("ar_url",image_Ar)
                        .putExtra("url",Cons.GET_SUBCHAPTERYBDETAILCHAPTERID)
                        .putExtra("subtitle_description",long_desc));
                break;

                case "graph":
                startActivity(new Intent(context, BarchartActivitynew.class)
                        .putExtra("sub_sub_id_s",position+"")
                        .putExtra("subtitle_name",title)
                        .putExtra("subtitle_subname",content)
                        .putExtra("subtitle_description",long_desc)
                        .putExtra("url",Cons.GET_SUBCHAPTERYBDETAILCHAPTERID));
                break;
                case "column":
                startActivity(new Intent(context, BarchartActivitynew.class)
                        .putExtra("sub_sub_id_s",position+"")
                        .putExtra("subtitle_name",title)
                        .putExtra("subtitle_subname",content)
                        .putExtra("subtitle_description",long_desc)
                        .putExtra("url",Cons.GET_SUBCHAPTERYBDETAILCHAPTERID));
                break;
                default:
                    startActivity(new Intent(context, BarchartActivitynew.class)
                            .putExtra("sub_sub_id_s",position+"")
                            .putExtra("subtitle_name",title)
                            .putExtra("subtitle_subname",content)
                            .putExtra("subtitle_description",long_desc)
                            .putExtra("url",Cons.GET_SUBCHAPTERYBDETAILCHAPTERID));
                    break;
        }




        Log.e(getClass().getSimpleName(),position+1+"");
    }


    public  void clicksketchedit(View view1,String sketchoption,String content){
        fabMenu.setVisibility(View.GONE);
        rec_view.setVisibility(View.GONE);
//        String filename = Screenshot.getScreenshot(layout_bg,context);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(EditorSecondSubActivity.this,layout_bg,"imagesketch");

        startActivity(new Intent(context, SketchActivity.class).putExtra("color",sketchoption).putExtra("sub_name",content),activityOptionsCompat.toBundle());

    }


    private void openAR(String Ar_url){
        Log.e("AR_URL",Ar_url+"");

        try {
//        "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Avocado/glTF/Avocado.gltf"
            Intent sceneViewerIntent = new Intent(Intent.ACTION_VIEW);
            sceneViewerIntent.setData(Uri.parse(Ar_url));
            sceneViewerIntent.setPackage("com.google.android.googlequicksearchbox");
            startActivity(sceneViewerIntent);
        }
        catch (Exception e){
            Toast.makeText(context,"No Apps to perform this action",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2){
            if (requestCode==RESULT_OK){
                finish();

            }
        }
    }

    public void onback(View view) {
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        fabMenu.setVisibility(View.VISIBLE);
        rec_view.setVisibility(View.VISIBLE);
    }

/*    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }*/
}
