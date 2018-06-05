package com.example.bhavesh.voter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class PieChart extends Fragment {
    View parentHolder;
    private static final int READ_BLOCK_SIZE = 1000;
    String[] entryArray = new String[1000];

    public PieChart() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentHolder=inflater.inflate(R.layout.fragment_pie_chart, container, false);

        String s="";

        try
        {
            FileInputStream fIn =getActivity().openFileInput("vote.txt");
            InputStreamReader isr = new InputStreamReader(fIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            int charRead;
            while ((charRead = isr.read(inputBuffer))>0)
            {
//---convert the chars to a String---
                String readString =String.copyValueOf(inputBuffer, 0,charRead);
                s += readString;
                inputBuffer = new char[READ_BLOCK_SIZE];
            }
            Log.v("Tag","Hello "+s);
            if(s!=null)
            {
                entryArray=s.split(",");
            }
            else
            {
                Toast.makeText(getActivity().getBaseContext(),"File not loaded successfully!",Toast.LENGTH_LONG).show();
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

        /*Float voteCount[]=new Float[1000];

        voteCount[0]=*/

        com.github.mikephil.charting.charts.PieChart pieChart = (com.github.mikephil.charting.charts.PieChart) parentHolder.findViewById(R.id.piechart);
        //pieChart.setUsePercentValues(true);

        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        if(Float.parseFloat(entryArray[0])!=0) {
            yvalues.add(new Entry(Float.parseFloat(entryArray[0]), 0));
        }
        if(Float.parseFloat(entryArray[1])!=0) {
            yvalues.add(new Entry(Float.parseFloat(entryArray[1]), 1));
        }
        if(Float.parseFloat(entryArray[2])!=0) {
            yvalues.add(new Entry(Float.parseFloat(entryArray[2]), 2));
        }
        if(Float.parseFloat(entryArray[3])!=0) {
            yvalues.add(new Entry(Float.parseFloat(entryArray[3]), 3));
        }

        PieDataSet dataSet = new PieDataSet(yvalues, "Election Results");

        ArrayList<String> xVals = new ArrayList<String>();

        if(Float.parseFloat(entryArray[0])!=0) {
            xVals.add("AAP");
        }
        if(Float.parseFloat(entryArray[1])!=0){
            xVals.add("Shivsena");
        }
        if(Float.parseFloat(entryArray[2])!=0){
            xVals.add("Congress");
        }
        if(Float.parseFloat(entryArray[3])!=0){
            xVals.add("BJP");
        }

        PieData data = new PieData(xVals, dataSet);

        data.setValueFormatter(new DefaultValueFormatter(0));

        pieChart.setData(data);

//        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
//        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
//        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        pieChart.setDescription("This is Pie Chart");

        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(30f);
        pieChart.setHoleRadius(30f);

        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);

        return parentHolder;
    }
}