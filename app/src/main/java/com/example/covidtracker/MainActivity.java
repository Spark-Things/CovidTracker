package com.example.covidtracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidtracker.api.Apiutility;
import com.example.covidtracker.api.CountryData;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView TotalConfirm,TodayConfirm,TotalActive,TotalRecoverd,TodayRecoverd,TotalDeath,TodayDeath,TotalTests,date;

    private List<CountryData> list;

    private PieChart pieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        init();

        Apiutility.getApiInterface().getCountryData()
                .enqueue(new Callback<List<CountryData>>() {
//                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                        list.addAll(response.body());

                        for (int i =0 ;i<list.size();i++){
                            if (list.get(i).getCountry().equals("India")){
                                int confirm = Integer.parseInt(list.get(i).getCases());
                                int active = Integer.parseInt(list.get(i).getActive());
                                int recoverd = Integer.parseInt(list.get(i).getRecovered());
                                int death = Integer.parseInt(list.get(i).getDeaths());
                                int todayConfirm = Integer.parseInt(list.get(i).getTodayCases());
                                int todayRecoverd = Integer.parseInt(list.get(i).getTodayRecovered());
                                int todayDeath = Integer.parseInt(list.get(i).getTodayDeaths());
                                int totalTest = Integer.parseInt(list.get(i).getTests());

                                TotalConfirm.setText(NumberFormat.getInstance().format(confirm));
                                TotalActive.setText(NumberFormat.getInstance().format(active));
                                TotalRecoverd.setText(NumberFormat.getInstance().format(recoverd));
                                TotalDeath.setText(NumberFormat.getInstance().format(death));
                                TodayConfirm.setText(NumberFormat.getInstance().format(todayConfirm));
                                TodayRecoverd.setText(NumberFormat.getInstance().format(todayRecoverd));
                                TotalDeath.setText(NumberFormat.getInstance().format(todayDeath));
                                TotalTests.setText(NumberFormat.getInstance().format(totalTest));


                                
//                                setText(list.get(i).getUpdated());

                                pieChart.addPieSlice(new PieModel("confirm",confirm,getResources().getColor(R.color.yellow)));
                                pieChart.addPieSlice(new PieModel("active",active,getResources().getColor(R.color.blue_pie)));
                                pieChart.addPieSlice(new PieModel("recoverd",recoverd,getResources().getColor(R.color.green_pie)));
                                pieChart.addPieSlice(new PieModel("death",death,getResources().getColor(R.color.red_pie)));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CountryData>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    private void setText(String updated) {
//        DateFormat format = new SimpleDateFormat("MMM dd, yyyy");
//        long miliseconds = Long.parseLong(updated);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(miliseconds);
//
//        date.setText("Updated at"+format.format(calendar.getTime()));

//    }

    public void init(){
        TotalConfirm = (TextView) findViewById(R.id.TotalConfirm);
        TodayConfirm = (TextView) findViewById(R.id.TodayConfirm);
        TotalActive = (TextView) findViewById(R.id.TotalActive);
        TotalRecoverd = (TextView) findViewById(R.id.TotalRecoverd);
        TodayRecoverd = (TextView) findViewById(R.id.TodayRecoverd);
        TotalDeath = (TextView) findViewById(R.id.TotalDeath);
        TodayDeath = (TextView) findViewById(R.id.TotalDeath);
        TotalTests = (TextView) findViewById(R.id.TotalTests);
        pieChart = (PieChart) findViewById(R.id.piechart);
        date = (TextView) findViewById(R.id.date);
    }
}