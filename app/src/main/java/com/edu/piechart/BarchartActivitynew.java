package com.edu.piechart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.barchartpojo.BarFirstRetrofitModel;
import com.barchartpojo.BarFourthColumn_valuesRetrofitModel;
import com.bumptech.glide.Glide;
import com.edu.aplis.DemoApplication;
import com.edu.aplis.R;
import com.edu.editortemplatetwo.EditorFirstRetrofitModel;
import com.edu.retrofitapi.Api;
import com.edu.retrofitapi.RetrofitClient;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import at.blogc.android.views.ExpandableTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarchartActivitynew extends AppCompatActivity implements ResponceQueues {
    BarChart chart;
    String heading;
    String sub_heading;
    String pie_description;
    String barchart_id;

    float barWidth;
    float barSpace;
    float groupSpace;

    HashMap<String, String> hashMap = new HashMap<>();
    Context context;

    int total_value=0;

    LinearLayout linear_table;
    TextView text_pietitle,text_piesubtitle,text_piedescription;
    TextView text_piesidetitle,text_piesidesubtitle;
    ExpandableTextView text_piesidedescription;
    ImageView piesideimage,piesideimagecancel,piesideimagebar;
    LinearLayout piesidescrren;
    LinearLayout leftpielinear;
    protected RectF mOnValueSelectedRectF = new RectF();


    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList pieEntries;

    int color[] ={R.color.colorAccent,R.color.textCol,R.color.colorPrimary,R.color.colorPrimary};
    ArrayList<Piemodel> barseriesmodelArrayList;
    ArrayList<Piemodel> barscatmodelArrayList;
    Piemodel piemodel1;
    Piemodel piemodel2;

    ArrayList<BarDataSet> barDataSetArrayList;
    BarDataSet barDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        chart = findViewById(R.id.barchart);
        barseriesmodelArrayList = new ArrayList<>();
        barscatmodelArrayList = new ArrayList<>();
        barDataSetArrayList = new ArrayList<>();
        context = BarchartActivitynew.this;


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

        //callanalytics();
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


        barWidth = 0.4f;
        barSpace = 0f;
        groupSpace = 0.4f;

//        chart.setDescription(null);
        chart.setPinchZoom(true);
        chart.setScaleEnabled(true);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);
//        chart.highlightValue(null);


        getDataFromServer(getIntent().getStringExtra("url"));
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                showtoast(barscatmodelArrayList.get(Math.round(h.getX())).getHeading(),
                        barscatmodelArrayList.get(Math.round(h.getX())).getNumaric_value().replace("f",""),
                        showpercentagevalue(barscatmodelArrayList.get(Math.round(h.getX())).getNumaric_value().replace("f",""),total_value+""));
                Log.e("TAG", "onValueSelected: value selected from chart"+h.toString());
//                Log.e("TAG", "onValueSelected: "+chart.getXAxis().getValueFormatter().getFormattedValue(e.getX(), chart.getXAxis()));
//                Log.e("TAG", "onValueSelected: "+ barscatmodelArrayList.get(Math.round(h.getX())).getNumaric_value()+
//                        " "+showpercentagevalue(barscatmodelArrayList.get(Math.round(h.getX())).getNumaric_value().replace("f",""),total_value+""));
            }

            @Override
            public void onNothingSelected() {

            }
        });
//
//        int groupCount = 6;
//
//        ArrayList xVals = new ArrayList();
//
//        xVals.add("Jan");
//        xVals.add("Feb");
//        xVals.add("Mar");
//        xVals.add("Apr");
//        xVals.add("May");
//        xVals.add("Jun");
//
//        ArrayList NoOfEmp = new ArrayList();
//        ArrayList yVals2 = new ArrayList();
//
//
////        yVals1.add(new BarEntry(1f,  1));
////        yVals2.add(new BarEntry(1, (float) 2));
////        yVals1.add(new BarEntry(2, (float) 3));
////        yVals2.add(new BarEntry(2, (float) 4));
////        yVals1.add(new BarEntry(3, (float) 5));
////        yVals2.add(new BarEntry(3, (float) 6));
////        yVals1.add(new BarEntry(4, (float) 7));
////        yVals2.add(new BarEntry(4, (float) 8));
////        yVals1.add(new BarEntry(5, (float) 9));
////        yVals2.add(new BarEntry(5, (float) 10));
////        yVals1.add(new BarEntry(6, (float) 11));
////        yVals2.add(new BarEntry(6, (float) 12));
//
//
//        NoOfEmp.add(new BarEntry(0f, 10));
//        yVals2.add(new BarEntry(0f, 16));
//        NoOfEmp.add(new BarEntry(0f, 20));
//        yVals2.add(new BarEntry(0f, 30));
//        NoOfEmp.add(new BarEntry(0f, 40));
//        yVals2.add(new BarEntry(0f, 50));
//        NoOfEmp.add(new BarEntry(0f, 60));
//        yVals2.add(new BarEntry(0f, 70));
//        NoOfEmp.add(new BarEntry(0f, 80));
//        yVals2.add(new BarEntry(0f, 90));
//        NoOfEmp.add(new BarEntry(0f, 90));
//        yVals2.add(new BarEntry(0f, 70));
//
//
//
//        BarDataSet set1, set2;
//        set1 = new BarDataSet(NoOfEmp, "A");
//        set1.setColor(Color.RED);
//        set2 = new BarDataSet(yVals2, "B");
//        set2.setColor(Color.BLUE);
//        BarData data = new BarData(set1,set2);
//        data.setValueFormatter(new LargeValueFormatter());
//        chart.setData(data);
//        chart.getBarData().setBarWidth(barWidth);
//        chart.getXAxis().setAxisMinimum(0);
//        chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
//        chart.groupBars(0, groupSpace, barSpace);
//        chart.getData().setHighlightEnabled(false);
//        chart.invalidate();
//
//
//        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(true);
//        l.setYOffset(20f);
//        l.setXOffset(0f);
//        l.setYEntrySpace(0f);
//        l.setTextSize(8f);
//
//        //X-axis
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setGranularity(1f);
//        xAxis.setGranularityEnabled(true);
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setDrawGridLines(false);
//        xAxis.setAxisMaximum(6);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
////Y-axis
//        chart.getAxisRight().setEnabled(false);
//        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setValueFormatter(new LargeValueFormatter());


//        ArrayList NoOfEmp = new ArrayList();
//
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

//        List<BarEntry> entriesGroup1 = new ArrayList<>();
//        List<BarEntry> entriesGroup2 = new ArrayList<>();
// fill the lists
//        for(int i = 0; i < NoOfEmp.length; i++) {
//            entriesGroup1.add(new BarEntry(i, NoOfEmp.get(i)));
//            entriesGroup2.add(new BarEntry(i, group2.getValue()));
//        }
//        BarDataSet set1 = new BarDataSet(entriesGroup1, "Group 1");
//        BarDataSet set2 = new BarDataSet(entriesGroup2, "Group 2");

//        ArrayList NoOfEmp = new ArrayList();
//
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
//        year.add("HIndu");
//        year.add("2HIndu009");
//        year.add("HIndu");
//        year.add("20HIndu11");
//        year.add("HIndu");
//        year.add("HIndu");
//        year.add("HIndu");
//        year.add("HIndu");
//        year.add("HIndu");
//        year.add("HIndu");
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
//        BarDataSet bardataset = new BarDataSet(NoOfEmp, "No Of Employee");
//        chart.animateY(3000);
//        BarData data = new BarData(year, bardataset);
//        bardataset.setColors(colors);
//        chart.setData(data);
    }

    private void getDataFromServer(String url) {
        barchart_id = getIntent().getStringExtra("sub_sub_id_s");

        Api api = RetrofitClient.getClient(context, Api.BASE_URL).create(Api.class);

        Call<BarFirstRetrofitModel> call = api.getGETBarChhart(url+ barchart_id);
//        Log.e("discovertry",call.u);
        call.enqueue(new Callback<BarFirstRetrofitModel>() {
            @Override
            public void onResponse(Call<BarFirstRetrofitModel> call, Response<BarFirstRetrofitModel> response) {

                try {

                    setBarchart(response.body());
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
            public void onFailure(Call<BarFirstRetrofitModel> call, Throwable t) {
                Log.e("errordo", t.getMessage());
            }
        });
//        hashMap.clear();
//        Log.e("TAGEDITOR", url+ barchart_id);
//        ApiService apiService = new ApiService(context, this, url+ barchart_id, hashMap, 1);
//        apiService.execute();
    }


    private void setBarchart(BarFirstRetrofitModel body){
        barseriesmodelArrayList.clear();
        int groupCount = 0;



        heading = body.getBarSecondRetrofitModel().getHeading();

        sub_heading = body.getBarSecondRetrofitModel().getSub_heading();
        pie_description = body.getBarSecondRetrofitModel().getHtml_content();

        text_pietitle.setText(heading);
        text_piesubtitle.setText(sub_heading);
        text_piedescription.setText(pie_description);

        groupCount =body.getBarSecondRetrofitModel().getBarThirdRetrofitModels().size();
        ArrayList xVals = new ArrayList();
        ArrayList NoOfEmp = new ArrayList();
        ArrayList yVals2 = new ArrayList();


        Log.e("Response===", body.getBarSecondRetrofitModel().getBarThirdRetrofitModels().size() + "");

        for (int i = 0; i < body.getBarSecondRetrofitModel().getBarThirdRetrofitModels().size(); i++) {
            String id = body.getBarSecondRetrofitModel().getBarThirdRetrofitModels().get(i).getId();
            String title = body.getBarSecondRetrofitModel().getBarThirdRetrofitModels().get(i).getHeading();
            String sub_heading =body.getBarSecondRetrofitModel().getBarThirdRetrofitModels().get(i).getSub_heading();
            String heading_url = body.getBarSecondRetrofitModel().getBarThirdRetrofitModels().get(i).getUrl();
            String long_description = body.getBarSecondRetrofitModel().getBarThirdRetrofitModels().get(i).getDescription();
            String numaric_value = body.getBarSecondRetrofitModel().getBarThirdRetrofitModels().get(i).getNumaric_value();
            String color_code =body.getBarSecondRetrofitModel().getBarThirdRetrofitModels().get(i).getColor_code();
            String ar_url =body.getBarSecondRetrofitModel().getBarThirdRetrofitModels().get(i).getAr_url();

            piemodel1 = new Piemodel();
            piemodel1.setId(id);
            piemodel1.setHeading(title);
            piemodel1.setSub_heading(sub_heading);
            piemodel1.setDescription(long_description);
            piemodel1.setNumaric_value(numaric_value);
            piemodel1.setUrl(heading_url);
            piemodel1.setAr_url(ar_url);
            piemodel1.setColor_code(color_code);
            barseriesmodelArrayList.add(piemodel1);

            total_value = total_value+Integer.parseInt(piemodel1.getNumaric_value().replace("f",""));

        }
        inflateResults(barseriesmodelArrayList);

//        JSONArray jsonArray1 = body.getBarSecondRetrofitModel().getBarFourthColumn_categoryRetrofitModels();

        for (int j = 0;j<body.getBarSecondRetrofitModel().getBarFourthColumn_categoryRetrofitModels().size();j++) {
            String name = body.getBarSecondRetrofitModel().getBarFourthColumn_categoryRetrofitModels().get(j).getName();
            xVals.add(name);

//            JSONArray jsonArray2 = body.getBarSecondRetrofitModel().getBarFourthColumn_categoryRetrofitModels().get(j).getBarFourthColumn_valuesRetrofitModels();

            for (int z = 0;z<body.getBarSecondRetrofitModel().getBarFourthColumn_categoryRetrofitModels().get(j).getBarFourthColumn_valuesRetrofitModels().size();z++){
                BarFourthColumn_valuesRetrofitModel barFourthColumn_valuesRetrofitModel = body.getBarSecondRetrofitModel().getBarFourthColumn_categoryRetrofitModels().get(j).getBarFourthColumn_valuesRetrofitModels().get(z);

                String id = barFourthColumn_valuesRetrofitModel.getId();
                String numaric_value = barFourthColumn_valuesRetrofitModel.getValue();

                piemodel2 = new Piemodel();
                piemodel2.setId(id);
                piemodel2.setNumaric_value(numaric_value);

                barscatmodelArrayList.add(piemodel2);

            }
        }
        if (groupCount==1){
            setSingleBarChart(barscatmodelArrayList,barseriesmodelArrayList);
        }
        else {
            setbargroupchart(xVals, groupCount, barscatmodelArrayList, barseriesmodelArrayList);
        }

        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);



    }
    @Override
    public void responceQue(String responce, String url, String extra_text) {
        barseriesmodelArrayList.clear();
        int groupCount = 0;

//        xVals.add("Jan");
//        xVals.add("Feb");
//        xVals.add("Mar");
//        xVals.add("Apr");
//        xVals.add("May");
//        xVals.add("Jun");


        Log.e("Response===", url + responce);

        try {
            JSONObject jsonObject = new JSONObject(responce);
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("columns");

            heading = jsonObject.getJSONObject("data").getString("heading");

            sub_heading = jsonObject.getJSONObject("data").getString("sub_heading");
            pie_description = jsonObject.getJSONObject("data").getString("html_content");

            text_pietitle.setText(heading);
            text_piesubtitle.setText(sub_heading);
            text_piedescription.setText(pie_description);


            groupCount =jsonArray.length();
            ArrayList xVals = new ArrayList();
            ArrayList NoOfEmp = new ArrayList();
            ArrayList yVals2 = new ArrayList();


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

                piemodel1 = new Piemodel();
                piemodel1.setId(id);
                piemodel1.setHeading(title);
                piemodel1.setSub_heading(sub_heading);
                piemodel1.setDescription(long_description);
                piemodel1.setNumaric_value(numaric_value);
                piemodel1.setUrl(heading_url);
                piemodel1.setAr_url(ar_url);
                piemodel1.setColor_code(color_code);
                barseriesmodelArrayList.add(piemodel1);

                total_value = total_value+Integer.parseInt(piemodel1.getNumaric_value().replace("f",""));

            }
            inflateResults(barseriesmodelArrayList);

            JSONArray jsonArray1 = jsonObject.getJSONObject("data").getJSONArray("column_categories");

            for (int j = 0;j<jsonArray1.length();j++) {
                String name = jsonArray1.getJSONObject(j).getString("name");
                xVals.add(name);

                JSONArray jsonArray2 = jsonArray1.getJSONObject(j).getJSONArray("column_values");

                for (int z = 0;z<jsonArray2.length();z++){
                    JSONObject jsonObjectseries = jsonArray2.getJSONObject(z);

                    String id = jsonObjectseries.getString("id");
                    String numaric_value = jsonObjectseries.getString("value");

                    piemodel2 = new Piemodel();
                    piemodel2.setId(id);
                    piemodel2.setNumaric_value(numaric_value);

                    barscatmodelArrayList.add(piemodel2);

                }
            }
            if (groupCount==1){
                setSingleBarChart(barscatmodelArrayList,barseriesmodelArrayList);
            }
            else {
                setbargroupchart(xVals, groupCount, barscatmodelArrayList, barseriesmodelArrayList);
            }

            chart.getDescription().setEnabled(false);
            chart.getLegend().setEnabled(false);

        } catch (JSONException e) {
            Log.e("JSONEXCEPTION", e + "");
            e.printStackTrace();
        }

    }

    private void setbargroupchart(ArrayList xVals, int groupcount, ArrayList<Piemodel> barscatmodelArrayList, ArrayList<Piemodel> barseriesmodelArrayList){


        if (groupcount==2){
            ArrayList x1 = new ArrayList();
            ArrayList y1 = new ArrayList();
            for (int count = 0;count<barscatmodelArrayList.size();count+=2) {
                x1.add(new BarEntry(1f, Integer.parseInt(barscatmodelArrayList.get(count).getNumaric_value())));
                y1.add(new BarEntry(1f, Integer.parseInt(barscatmodelArrayList.get(count+1).getNumaric_value())));
            }

            BarDataSet set1, set2;
            set1 = new BarDataSet(x1, barseriesmodelArrayList.get(0).getHeading());
            set1.setColor(Color.parseColor(barseriesmodelArrayList.get(0).getColor_code()));
            set2 = new BarDataSet(y1, barseriesmodelArrayList.get(1).getHeading());
            set2.setColor(Color.parseColor(barseriesmodelArrayList.get(1).getColor_code()));

            BarData data = new BarData(set1,set2);
            data.setValueFormatter(new LargeValueFormatter());
            data.setHighlightEnabled(true);

            data.setDrawValues(true);

            chart.setData(data);

        }
        else if (groupcount==3){
            ArrayList x1 = new ArrayList();
            ArrayList y1 = new ArrayList();
            ArrayList z1 = new ArrayList();
            for (int count = 0;count<barscatmodelArrayList.size();count+=3) {
                x1.add(new BarEntry(0f, Integer.parseInt(barscatmodelArrayList.get(count).getNumaric_value())));
                y1.add(new BarEntry(0f, Integer.parseInt(barscatmodelArrayList.get(count+1).getNumaric_value())));
                z1.add(new BarEntry(0f, Integer.parseInt(barscatmodelArrayList.get(count+2).getNumaric_value())));
            }
            BarDataSet set1, set2,set3;
            set1 = new BarDataSet(x1, barseriesmodelArrayList.get(0).getHeading());
            set1.setColor(Color.parseColor(barseriesmodelArrayList.get(0).getColor_code()));
            set2 = new BarDataSet(y1, barseriesmodelArrayList.get(1).getHeading());
            set2.setColor(Color.parseColor(barseriesmodelArrayList.get(1).getColor_code()));
            set3 = new BarDataSet(z1, barseriesmodelArrayList.get(2).getHeading());
            set3.setColor(Color.parseColor(barseriesmodelArrayList.get(2).getColor_code()));
            BarData data = new BarData(set1,set2,set3);
            data.setValueFormatter(new LargeValueFormatter());
            chart.animateXY(2000, 2000) ;
            chart.setHighlightPerTapEnabled(true);
            chart.setData(data);

        }
        else if (groupcount==4){
            ArrayList x1 = new ArrayList();
            ArrayList y1 = new ArrayList();
            ArrayList z1 = new ArrayList();
            ArrayList x2 = new ArrayList();
            for (int count = 0;count<barscatmodelArrayList.size();count+=4) {
                x1.add(new BarEntry(0f, Integer.parseInt(barscatmodelArrayList.get(count).getNumaric_value())));
                y1.add(new BarEntry(0f, Integer.parseInt(barscatmodelArrayList.get(count+1).getNumaric_value())));
                z1.add(new BarEntry(0f, Integer.parseInt(barscatmodelArrayList.get(count+2).getNumaric_value())));
                x2.add(new BarEntry(0f, Integer.parseInt(barscatmodelArrayList.get(count+3).getNumaric_value())));
            }
            BarDataSet set1, set2,set3,set4;
            set1 = new BarDataSet(x1, barseriesmodelArrayList.get(0).getHeading());
            set1.setColor(Color.parseColor(barseriesmodelArrayList.get(0).getColor_code()));
            set2 = new BarDataSet(y1, barseriesmodelArrayList.get(1).getHeading());
            set2.setColor(Color.parseColor(barseriesmodelArrayList.get(1).getColor_code()));
            set3 = new BarDataSet(z1, barseriesmodelArrayList.get(2).getHeading());
            set3.setColor(Color.parseColor(barseriesmodelArrayList.get(2).getColor_code()));
            set4 = new BarDataSet(x2, barseriesmodelArrayList.get(3).getHeading());
            set4.setColor(Color.parseColor(barseriesmodelArrayList.get(3).getColor_code()));
            BarData data = new BarData(set1,set2,set3,set4);
            data.setValueFormatter(new LargeValueFormatter());
            chart.setData(data);
        }
        else if (groupcount==5){
            ArrayList x1 = new ArrayList();
            ArrayList y1 = new ArrayList();
            ArrayList z1 = new ArrayList();
            ArrayList x2 = new ArrayList();
            ArrayList y2 = new ArrayList();
            for (int count = 0;count<barscatmodelArrayList.size();count+=5) {
                x1.add(new BarEntry(0f, Integer.parseInt(barscatmodelArrayList.get(count).getNumaric_value())));
                y1.add(new BarEntry(0f, Integer.parseInt(barscatmodelArrayList.get(count+1).getNumaric_value())));
                z1.add(new BarEntry(0f, Integer.parseInt(barscatmodelArrayList.get(count+2).getNumaric_value())));
                x2.add(new BarEntry(0f, Integer.parseInt(barscatmodelArrayList.get(count+3).getNumaric_value())));
                y2.add(new BarEntry(0f, Integer.parseInt(barscatmodelArrayList.get(count+4).getNumaric_value())));
            }
            BarDataSet set1, set2,set3,set4,set5;
            set1 = new BarDataSet(x1, barseriesmodelArrayList.get(0).getHeading());
            set1.setColor(Color.parseColor(barseriesmodelArrayList.get(0).getColor_code()));
            set2 = new BarDataSet(y1, barseriesmodelArrayList.get(1).getHeading());
            set2.setColor(Color.parseColor(barseriesmodelArrayList.get(1).getColor_code()));
            set3 = new BarDataSet(z1, barseriesmodelArrayList.get(2).getHeading());
            set3.setColor(Color.parseColor(barseriesmodelArrayList.get(2).getColor_code()));
            set4 = new BarDataSet(x2, barseriesmodelArrayList.get(3).getHeading());
            set4.setColor(Color.parseColor(barseriesmodelArrayList.get(3).getColor_code()));
            set5 = new BarDataSet(y2, barseriesmodelArrayList.get(4).getHeading());
            set5.setColor(Color.parseColor(barseriesmodelArrayList.get(4).getColor_code()));
            BarData data = new BarData(set1,set2,set3,set4,set5);
            data.setValueFormatter(new LargeValueFormatter());
            chart.setData(data);
        }
//        ArrayList xVals = new ArrayList();
//
//        xVals.add("Jan");
//        xVals.add("Feb");
//        xVals.add("Mar");
//        xVals.add("Apr");
//        xVals.add("May");
//        xVals.add("Jun");







        chart.getBarData().setBarWidth(barWidth);
        chart.getBarData().setHighlightEnabled(true); // allow highlighting for DataSet
        chart.setHighlightPerTapEnabled(true);
        chart.setHighlightPerDragEnabled(true);
        // set this to false to disable the drawing of highlight indicator (lines)

        chart.getXAxis().setAxisMinimum(0);
        chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupcount);
        chart.groupBars(0, groupSpace, barSpace);
        chart.getData().setHighlightEnabled(true);
        chart.animateXY(2000, 2000) ;
        chart.getData().setDrawValues(true);
        chart.setHighlightPerTapEnabled(true);
        chart.invalidate();

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(20f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        //X-axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(6);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
//Y-axis
        chart.getAxisRight().setEnabled(false);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());

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


    private void setSingleBarChart(ArrayList<Piemodel> barscatmodelArrayList, ArrayList<Piemodel> barseriesmodelArrayList){
        ArrayList NoOfEmp = new ArrayList();
        ArrayList year = new ArrayList();
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int i =0;i<barscatmodelArrayList.size();i++) {
            NoOfEmp.add(new BarEntry(Float.parseFloat(barscatmodelArrayList.get(i).getNumaric_value()+"f"), i));
        }
        year.add(barseriesmodelArrayList.get(0).getHeading());
        colors.add(Color.parseColor(barseriesmodelArrayList.get(0).getColor_code()));
        Log.e("JSONEXCEPTION", NoOfEmp.size()+" " + year.size()+" "+colors.size()+"");

        BarDataSet bardataset = new BarDataSet(NoOfEmp, barseriesmodelArrayList.get(0).getHeading());
        chart.animateXY(2000, 2000) ;
        chart.setHighlightPerTapEnabled(true);
//        BarData data = new BarData(year, bardataset);
        bardataset.setColors(colors);

//        chart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
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


    private String showpercentagevalue(String inputvalue,String totalvalue){
        String percent_value="";
        float number = (Integer.parseInt(inputvalue)*100)/Integer.parseInt(totalvalue);
        percent_value = number+"";
        return percent_value;
    }


    private void showtoast(String title,String value,String percentage){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.piechartdvalue, (ViewGroup) findViewById(R.id.custom_toast_layout));
        TextView text_title = (TextView) layout.findViewById(R.id.text_title);
        TextView text_value = (TextView) layout.findViewById(R.id.text_value);
        TextView text_percentage = (TextView) layout.findViewById(R.id.text_percentage);
        text_title.setText(title);
        text_value.setText("Value: "+value);
        text_percentage.setText("Percentage Value : "+percentage+"%");
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(2);
        toast.setView(layout);
        toast.show();

    }

    public void onback(View view) {
        finish();
    }

    public void ontopic(View view) {
        Intent intent=new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }



/*    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }*/

//    @Override
//    public void onValueSelected(Entry e, Highlight h) {
//
//        if (e == null)
//            return;
//
//        RectF bounds = mOnValueSelectedRectF;
//        chart.getBarBounds((BarEntry) e, bounds);
//        MPPointF position = chart.getPosition(e, YAxis.AxisDependency.LEFT);
//
//        Log.i("bounds", bounds.toString());
//        Log.i("position", position.toString());
//
//        Log.i("x-index",
//                "low: " + chart.getLowestVisibleX() + ", high: "
//                        + chart.getHighestVisibleX());
//
//        MPPointF.recycleInstance(position);
//    }

//    @Override
//    public void onNothingSelected() { }



}
