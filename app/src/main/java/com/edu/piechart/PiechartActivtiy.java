package com.edu.piechart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.aplis.DemoApplication;
import com.edu.aplis.R;
import com.edu.discover.DiscoverModel;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.edu.webservice.ResponceQueues;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PiechartActivtiy extends AppCompatActivity implements ResponceQueues {

    PieChart pieChart;
    String heading;
    String piechart_id;
    PieDataSet pieDataSet;
    ArrayList pieEntries;
    HashMap<String, String> hashMap = new HashMap<>();
    Context context;

    int color[] = {R.color.colorAccent, R.color.textCol, R.color.colorPrimary, R.color.colorPrimary};
    ArrayList<Piemodel> piemodelArrayList;
    Piemodel piemodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);

        pieChart = findViewById(R.id.mchart);
        piemodelArrayList = new ArrayList<>();
        context =PiechartActivtiy.this;

       // callanalytics();
//        pieChart.setHoleColor(R.color.TextColor);

        pieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(PiechartActivtiy.this, "click", Toast.LENGTH_SHORT).show();

            }
        });


        getDataFromServer();

    }

    private void getEntries(float value, int length) {
        pieEntries = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            pieEntries.add(i);
//            pieEntries.set(i,"h");
        }
    }

    private void getDataFromServer() {
        piechart_id = getIntent().getStringExtra("sub_sub_id_s");
        hashMap.clear();
        Log.e("TAGEDITOR", Cons.GET_SUBCHAPTERYBDETAILCHAPTERID + piechart_id);
        ApiService apiService = new ApiService(context, this, Cons.GET_SUBCHAPTERYBDETAILCHAPTERID + piechart_id, hashMap, 1);
        apiService.execute();
    }

    @Override
    public void responceQue(String responce, String url, String extra_text) {
        piemodelArrayList.clear();
        Log.e("Response===", url + responce);

        try {
            JSONObject jsonObject = new JSONObject(responce);
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("pie_charts");

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
                piemodelArrayList.add(piemodel);

            }
            setPieChart(piemodelArrayList);
        } catch (JSONException e) {
            Log.e("JSONEXCEPTION", e + "");
            e.printStackTrace();
        }

    }

    private void setPieChart(ArrayList<Piemodel> piemodelArrayList){
        ArrayList NoOfEmp = new ArrayList();
        ArrayList year = new ArrayList();
        ArrayList<Integer> colors = new ArrayList<Integer>();

        List<PieEntry> entries = new ArrayList<>();
        for (int i =0;i<piemodelArrayList.size();i++) {
            entries.add(new PieEntry(Float.parseFloat(piemodelArrayList.get(i).getNumaric_value()), piemodelArrayList.get(i).getHeading()));
            colors.add(Color.parseColor(piemodelArrayList.get(i).getColor_code()));
//            entries.add(new PieEntry(26.7f, "Yellow"));
//            entries.add(new PieEntry(24.0f, "Red"));
//            entries.add(new PieEntry(30.8f, "Blue"));
//
//
//
//
//            int redColorValue = Color.parseColor("#f25d73");
//            int BLACK = Color.parseColor("#010101");
//            int BLUE = Color.parseColor("#4a90e2");
//            int WHITE = Color.parseColor("#eeeeee");

//            colors.add(redColorValue);
//            colors.add(BLACK);
//            colors.add(BLUE);
//            colors.add(WHITE);
        }
        PieDataSet set = new PieDataSet(entries, heading);
        set.setColors(colors);

        pieChart.setDrawHoleEnabled(false);
//        set.setDrawValues(false);
//        pieChart.setData(data);

        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.animateXY(3000, 3000);
        pieChart.invalidate();


        pieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        getBaseContext(),
                        "done"+ " % " ,
                        Toast.LENGTH_SHORT).show();
//                SeriesSelection seriesSelection = mChart.getCurrentSeriesAndPoint();
//                if (seriesSelection != null) {
//
//                     Getting the name of the clicked slice
//                    int seriesIndex = seriesSelection.getPointIndex();
//                    String selectedSeries="";
//                    selectedSeries = code[seriesIndex];
//
//                     Getting the value of the clicked slice
//                    double value = seriesSelection.getXValue();
//                    DecimalFormat dFormat = new DecimalFormat("#.#");
//
//                     Displaying the message
//                    Toast.makeText(
//                            getBaseContext(),
//                            selectedSeries + " : "  + Double.valueOf(dFormat.format(value)) + " % " ,
//                            Toast.LENGTH_SHORT).show();

            }
        });


        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.e("TAG", "onValueSelected: value selected from chart");
                Log.e("TAG", "onValueSelected: "+ e.toString());
                Log.e("TAG", "onValueSelected: "+ h.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });




//        ArrayList NoOfEmp = new ArrayList();
//        ArrayList year = new ArrayList();
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//
//        for (int i =0;i<piemodelArrayList.size();i++) {
//
//            NoOfEmp.add(new Entry(Float.parseFloat(piemodelArrayList.get(i).getNumaric_value()), i));
//            NoOfEmp.add(new Entry(1040f, 1));
//            NoOfEmp.add(new Entry(1133f, 2));
//            NoOfEmp.add(new Entry(1240f, 3));
//
//
//            year.add(piemodelArrayList.get(i).getHeading());
//            colors.add(Color.parseColor(piemodelArrayList.get(i).getColor_code()));
//
//            year.add("2009");
//            year.add("2010");
//            year.add("2011");
//


//        }
//            PieDataSet dataSet = new PieDataSet(NoOfEmp, heading);
//
//            PieData data = new PieData(year, dataSet);
//        pieChart.setData(data);
//        pieChart.setDrawHoleEnabled(false);
//

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

    public void ontopic(View view) {
        Intent intent=new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

/*    private void callanalytics() {
        DemoApplication application = (DemoApplication) getApplication();
        application.trackScreenView(getClass().getSimpleName());
    }*/
}
