package com.edu.chatsub_sub;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.edu.aplis.DemoApplication;
import com.edu.aplis.R;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import at.blogc.android.views.ExpandableTextView;

public class Barchartsub_subActivity extends AppCompatActivity implements ResponceQueues {

    BarChart chart;
    String heading;
    String barchart_id;
    PieDataSet pieDataSet;
    ArrayList pieEntries;
    HashMap<String, String> hashMap = new HashMap<>();
    Context context;

    LinearLayout linear_table;
    TextView text_pietitle,text_piesubtitle,text_piedescription;
    TextView text_piesidetitle,text_piesidesubtitle;
    ExpandableTextView text_piesidedescription;
    ImageView piesideimage,piesideimagecancel,piesideimagebar;
    LinearLayout piesidescrren;
    LinearLayout leftpielinear;
    int color[] = {R.color.colorAccent, R.color.textCol, R.color.colorPrimary, R.color.colorPrimary};
    ArrayList<Piemodel> barmodelArrayList;
    Piemodel piemodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
         chart = findViewById(R.id.barchart);
        barmodelArrayList = new ArrayList<>();
        context = Barchartsub_subActivity.this;

        linear_table = findViewById(R.id.linear_table);
        text_pietitle = findViewById(R.id.text_pietitle);
        text_piesubtitle = findViewById(R.id.text_piesubtitle);
        text_piedescription = findViewById(R.id.text_piedescription);

        text_piesidetitle = findViewById(R.id.text_piesidetitle);
        text_piesidesubtitle = findViewById(R.id.text_piesidesubtitle);
        text_piesidedescription = findViewById(R.id.text_piesidedescription);
        piesideimage = findViewById(R.id.piesideimage);
        piesideimagecancel = findViewById(R.id.piesideimagecancel);
        piesideimagebar = findViewById(R.id.piesideimagebar);
        piesidescrren = findViewById(R.id.piesidescrren);
        leftpielinear = findViewById(R.id.leftpielinear);
    callanalytics();

        leftpielinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                piesideimagecancel.setVisibility(View.GONE);
                piesidescrren.setVisibility(View.GONE);
                piesideimagebar.setVisibility(View.GONE);
            }
        });

        text_piesidedescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text_piesidedescription.isExpanded()) {
                    text_piesidedescription.collapse();
//                    buttonToggle.setText(R.string.expand);
                } else {
                    text_piesidedescription.expand();
//                    buttonToggle.setText(R.string.collapse);
                }
            }
        });


        getDataFromServer();
//        ArrayList NoOfEmp = new ArrayList();

//        NoOfEmp.add(new BarEntry(945f, 0));
//        NoOfEmp.add(new BarEntry(1040f, 1));
//        NoOfEmp.add(new BarEntry(1133f, 2));
//        NoOfEmp.add(new BarEntry(1240f, 3));
//        NoOfEmp.add(new BarEntry(1369f, 4));
//        NoOfEmp.add(new BarEntry(1487f, 5));
//        NoOfEmp.add(new BarEntry(1501f, 6));
//        NoOfEmp.add(new BarEntry(1645f, 7));
//        NoOfEmp.add(new BarEntry(1578f, 8));
//        NoOfEmp.add(new BarEntry(1695f, 9));
//
//        ArrayList year = new ArrayList();
//
//        year.add("2008");
//        year.add("2009");
//        year.add("2010");
//        year.add("2011");
//        year.add("2012");
//        year.add("2013");
//        year.add("2014");
//        year.add("2015");
//        year.add("2016");
//        year.add("2017");
//
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//        int redColorValue = Color.parseColor("#f25d73");
//        int BLACK = Color.parseColor("#010101");
//        int BLUE = Color.parseColor("#4a90e2");
//        int WHITE = Color.parseColor("#eeeeee");
//
//        colors.add(redColorValue);
//        colors.add(BLACK);
//        colors.add(BLUE);
//        colors.add(WHITE);

//        BarDataSet bardataset = new BarDataSet(NoOfEmp, "No Of Employee");
//        chart.animateY(3000);
//        BarData data = new BarData(bardataset);
//        bardataset.setColors(colors);
//        chart.setData(data);
    }

    private void getDataFromServer() {
        barchart_id = getIntent().getStringExtra("sub_sub_id_s");
        hashMap.clear();
        Log.e("TAGEDITOR", Cons.GET_SUBSUBCHAPTERYBDETAILCHAPTERID + barchart_id);
        ApiService apiService = new ApiService(context, this, Cons.GET_SUBSUBCHAPTERYBDETAILCHAPTERID + barchart_id, hashMap, 1);
        apiService.execute();
    }

    @Override
    public void responceQue(String responce, String url, String extra_text) {
        barmodelArrayList.clear();
        Log.e("Response===", url + responce);

        try {
            JSONObject jsonObject = new JSONObject(responce);
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("columns");

            heading = jsonObject.getJSONObject("data").getString("heading");

            Log.e("Response===", jsonArray.length() + "");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectseries = jsonArray.getJSONObject(i);
                String id = jsonObjectseries.getString("id");
                String title = jsonObjectseries.getString("heading");
                String sub_heading = jsonObjectseries.getString("sub_heading");
                String heading_url = jsonObjectseries.getString("url");
                String long_description = jsonObjectseries.getString("description");
                String numaric_value = jsonObjectseries.getString("numaric_value");
                String color_code = jsonObjectseries.getString("color_code");
                String ar_url = jsonObjectseries.getString("ar_url");

                piemodel = new Piemodel();
                piemodel.setId(id);
                piemodel.setHeading(title);
                piemodel.setSub_heading(sub_heading);
                piemodel.setDescription(long_description);
                piemodel.setNumaric_value(numaric_value+"f");
                piemodel.setUrl(heading_url);
                piemodel.setAr_url(ar_url);
                piemodel.setColor_code(color_code);
                barmodelArrayList.add(piemodel);

            }
            setPieChart(barmodelArrayList);
            inflateResults(barmodelArrayList);

        } catch (JSONException e) {
            Log.e("JSONEXCEPTION", e + "");
            e.printStackTrace();
        }

    }

    private void setPieChart(ArrayList<Piemodel> barmodelArrayList){
        ArrayList NoOfEmp = new ArrayList();
        ArrayList year = new ArrayList();
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int i =0;i<barmodelArrayList.size();i++) {

            NoOfEmp.add(new BarEntry(Float.parseFloat(barmodelArrayList.get(i).getNumaric_value()), i));
            year.add(barmodelArrayList.get(i).getHeading());
            colors.add(Color.parseColor(barmodelArrayList.get(i).getColor_code()));
        }
        Log.e("JSONEXCEPTION", NoOfEmp.size()+" " + year.size()+" "+colors.size()+"");

        BarDataSet bardataset = new BarDataSet(NoOfEmp, "No Of Employee");
        chart.animateY(3000);
//        BarData data = new BarData(year, bardataset);
        bardataset.setColors(colors);
//        chart.setData(data);


//        BarDataSet bardataset = new BarDataSet(NoOfEmp, heading);
//        chart.animateY(3000);
//        BarData data = new BarData(year, bardataset);
//        bardataset.setColors(colors);
//        chart.setData(data);


//        PieDataSet dataSet = new PieDataSet(NoOfEmp, heading);
//
//        PieData data = new PieData(year, dataSet);
//        chart.setData(data);
//        chart.setDrawHoleEnabled(false);

//        int redColorValue = Color.parseColor("#f25d73");
//        int BLACK = Color.parseColor("#010101");
//        int BLUE = Color.parseColor("#4a90e2");
//        int WHITE = Color.parseColor("#eeeeee");
//
//        colors.add(redColorValue);
//        colors.add(BLACK);
//        colors.add(BLUE);
//        colors.add(WHITE);


//        dataSet.setColors(colors);
//        pieChart.animateXY(3000, 3000);



//        NoOfEmp.add(new Entry(945f, 0));
//        NoOfEmp.add(new Entry(1040f, 1));
//        NoOfEmp.add(new Entry(1133f, 2));
//        NoOfEmp.add(new Entry(1240f, 3));
//
//
//        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Number Of Employees");
//
//        ArrayList year = new ArrayList();
//
//        year.add("2008");
//        year.add("2009");
//        year.add("2010");
//        year.add("2011");
////
////
//        PieData data = new PieData(year, dataSet);
//        pieChart.setData(data);
//        pieChart.setDrawHoleEnabled(false);
//
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//        int redColorValue = Color.parseColor("#f25d73");
//        int BLACK = Color.parseColor("#010101");
//        int BLUE = Color.parseColor("#4a90e2");
//        int WHITE = Color.parseColor("#eeeeee");
//
//        colors.add(redColorValue);
//        colors.add(BLACK);
//        colors.add(BLUE);
//        colors.add(WHITE);
//
//
//        dataSet.setColors(colors);
//        pieChart.animateXY(3000, 3000);
    }


    public void inflateResults(final ArrayList<Piemodel> piemodelArrayList) {
        linear_table.removeAllViews();
        for (int i = 0; i < piemodelArrayList.size(); i++) {
            try {
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.piecolorcode, null);
                TextView text_description = (TextView) view.findViewById(R.id.text_description);
                ImageView image_pie = (ImageView) view.findViewById(R.id.image_pie);

                text_description.setText(piemodelArrayList.get(i).getHeading());
                try {
                    image_pie.setBackgroundColor(Color.parseColor(piemodelArrayList.get(i).getColor_code()));
                }catch (Exception e){

                }


//                    Glide.with(context)
//                            .load(piemodelArrayList.get(i).getUrl())
//                            .centerCrop()
//                            .into(image_pie);


                final int finalI = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Animation animation = AnimationUtils.loadAnimation(context, R.anim.rightleft);
//                        animation.setDuration(1000); // duartion in ms
//                        animation.setFillAfter(false);
//                        piesidescrren.startAnimation(animation);
                        piesideimagecancel.setVisibility(View.VISIBLE);
                        piesidescrren.setVisibility(View.VISIBLE);
                        piesideimagebar.setVisibility(View.VISIBLE);
                        try {
                            piesideimagebar.setBackgroundColor(Color.parseColor(piemodelArrayList.get(finalI).getColor_code()));
                        }catch (Exception e){

                        }
                        text_piesidetitle.setText(piemodelArrayList.get(finalI).getHeading());
                        text_piesidesubtitle.setText(piemodelArrayList.get(finalI).getSub_heading());
                        text_piesidedescription.setText(piemodelArrayList.get(finalI).getDescription());

                        Glide.with(context)
                                .load(piemodelArrayList.get(finalI).getUrl())
                                .centerCrop()
                                .into(piesideimage);


                    }
                });
                linear_table.addView(view);

            }
            catch (Exception e) {
                Log.e("VIEWS","1 "+piemodelArrayList.size()+" catch"+e.toString());
            }


        }
    }

    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }



}
