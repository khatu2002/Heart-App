package com.example.heartdiseaseprediction;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import com.github.mikephil.charting.utils.ColorTemplate;

public class ChartActivity extends AppCompatActivity{
    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;
    ImageView MakeApointment;
    // array list for storing entries.
    ArrayList barEntriesArrayList;
    ImageButton btn_ReturnHome;
    private TimeUnit TimeUnit;
    UserSessionManager session;
    User user;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        session=new UserSessionManager(getApplicationContext());
        user=session.getUserDetails();
        MakeApointment = findViewById(R.id.makeApointment);
        MakeApointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChooseServiceActivity.class);
                startActivity(intent);
                finish();
            }
        });
//

        btn_ReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                startActivity(intent);
                finish();
            }
        });

        // initializing variable for bar chart.
        barChart = findViewById(R.id.bar_chart);

        // calling method to get bar entries.
        getBarEntries();

        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "Geeks for Geeks");

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);

        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);

        
    }
    private void getBarEntries() {
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

// Lấy dữ liệu từ node "cardiacInfo"
        myRef.child(user.getUserID()).child("cardiacInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Lấy dữ liệu từ DataSnapshot
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    String value = childSnapshot.getValue(String.class);
                    float floatValue = Float.parseFloat(value);
                    int intValue = Math.round(floatValue);
                    System.out.println("Key: " + key + ", Value: " + value);
                    // Trích xuất giá trị giờ từ chuỗi timestamp
                    String hourString = key.substring(8, 10);
                    // Chuyển đổi giá trị giờ từ chuỗi sang kiểu số nguyên
                    int hour = Integer.parseInt(hourString);
                    System.out.println("Hour: " + hour + ", Heat beat: " + intValue);
                   barEntriesArrayList.add(new BarEntry(hour, intValue));
                    barChart.invalidate();
                }
                setupBarChart();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseError", "Firebase database error: " + databaseError.getMessage());
            }

        });

}
    private void setupBarChart() {
        barDataSet = new BarDataSet(barEntriesArrayList, "Heart Beat");
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barDataSet.setColors(Color.argb(225,50, 151, 168));
        barChart.getDescription().setEnabled(false);
    }
}