package com;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.anychart.AnyChart;
//import com.anychart.AnyChartView;
//import com.anychart.chart.common.dataentry.DataEntry;
//import com.anychart.chart.common.dataentry.ValueDataEntry;
//import com.anychart.chart.common.listener.Event;
//import com.anychart.chart.common.listener.ListenersInterface;
//import com.anychart.charts.Pie;
//import com.anychart.enums.Align;
//import com.anychart.enums.LegendLayout;
import com.edu.aplis.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PiechartActivity extends AppCompatActivity {

//    private AnyChartView pieChart;
//
////    PieChart pieChart;
//    PieData pieData;
//    PieDataSet pieDataSet;
//    ArrayList pieEntries;
//    ArrayList PieEntryLabels;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pie);
//
//
//        pieChart = (AnyChartView) findViewById(R.id.chart);
//        pieChart.setProgressBar(findViewById(R.id.progress_bar));
//        Pie pie = AnyChart.pie();
//
//        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
//            @Override
//            public void onClick(Event event) {
//                Toast.makeText(PiechartActivity.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
//            }
//        });
//        pie.data(getData());
//        pie.title("Corona Diseases patients in 2020");
//        pie.labels().position("outside");
//        pie.legend().title().enabled(true);
//        pie.legend().title()
//                .text("Total Corona patients - 374")
//                .padding(0d, 0d, 10d, 0d);
//        pie.legend()
//                .position("center-bottom")
//                .itemsLayout(LegendLayout.HORIZONTAL)
//                .align(Align.CENTER);
//        pieChart.setChart(pie);
//    }
//
//
//
//    private ArrayList getData(){
//        ArrayList<DataEntry> entries = new ArrayList<>();
//        entries.add(new ValueDataEntry("Delhi", 20));
//        entries.add(new ValueDataEntry("MAHARASHTRA", 63));
//        entries.add(new ValueDataEntry("BIHAR", 20));
//        entries.add(new ValueDataEntry("UP", 18));
//
//        return entries;
//    }
////        PieChart pieChart = findViewById(R.id.chart);
////        ArrayList NoOfEmp = new ArrayList();
////
////        NoOfEmp.add(new Entry(945f, 0));
////        NoOfEmp.add(new Entry(1040f, 1));
////        NoOfEmp.add(new Entry(1133f, 2));
////        NoOfEmp.add(new Entry(1240f, 3));
////
////
////        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Number Of Employees");
////
////        ArrayList year = new ArrayList();
////
////        year.add("2008");
////        year.add("2009");
////        year.add("2010");
////        year.add("2011");
////
////
////        PieData data = new PieData(year, dataSet);
////        pieChart.setData(data);
////        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
////        pieChart.animateXY(3000, 3000);
//
////        pieDataSet.addEntry(pieChart);
//
//
//    private void getEntries(float value,int length) {
//        pieEntries = new ArrayList<>();
//
//        for (int i= 0;i<length;i++) {
////            pieEntries.add(new PieEntry(value, i));
////            pieEntries.set(i,"h");
//        }
//    }


}
