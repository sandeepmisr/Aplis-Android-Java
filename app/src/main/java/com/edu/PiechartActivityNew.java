package com.edu;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.aplis.R;
import com.edu.piechart.PiechartActivtiy;
import com.edu.piechart.Piemodel;
import com.edu.webservice.ApiService;
import com.edu.webservice.Cons;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

//import com.anychart.AnyChart;
//import com.anychart.AnyChartView;
//import com.anychart.chart.common.dataentry.DataEntry;
//import com.anychart.chart.common.dataentry.ValueDataEntry;
//import com.anychart.chart.common.listener.Event;
//import com.anychart.chart.common.listener.ListenersInterface;
//import com.anychart.charts.Pie;
//import com.anychart.enums.Align;
//import com.anychart.enums.LegendLayout;


public class PiechartActivityNew extends AppCompatActivity {

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
        context = PiechartActivityNew.this;
//        pieChart.setHoleColor(R.color.TextColor);

        pieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(PiechartActivityNew.this, "click", Toast.LENGTH_SHORT).show();

            }
        });


        setPieChart();

    }

    private void getEntries(float value, int length) {
        pieEntries = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            pieEntries.add(i);
//            pieEntries.set(i,"h");
        }
    }


    private void setPieChart(){
        ArrayList NoOfEmp = new ArrayList();
        ArrayList year = new ArrayList();
        ArrayList<Integer> colors = new ArrayList<Integer>();

for (int i =0;i<4;i++) {

    NoOfEmp.add(new Entry(Float.parseFloat((i+1)+"f"), i));
    year.add(i+"");
    colors.add(Color.parseColor("#f25d73"));


}
        PieDataSet dataSet = new PieDataSet(NoOfEmp, "");
        PieData data = new PieData( dataSet);
        pieChart.setData(data);
        pieChart.setDrawHoleEnabled(false);
        dataSet.setColors(colors);
        pieChart.animateXY(3000, 3000);
//
//

//            PieDataSet dataSet = new PieDataSet(NoOfEmp, "Pie chart test");
//
//            PieData data = new PieData(year, dataSet);
//            pieChart.setData(data);
//            pieChart.setDrawHoleEnabled(false);
//
////            int redColorValue = Color.parseColor("#f25d73");
////            int BLACK = Color.parseColor("#010101");
////            int BLUE = Color.parseColor("#4a90e2");
////
////            colors.add(BLACK);
////            colors.add(BLUE);
//
//
//            dataSet.setColors(colors);
////        data.setValueTextColors(colors);
//            pieChart.animateXY(3000, 3000);


    }


    public void ontopic(View view) {
    }
}
