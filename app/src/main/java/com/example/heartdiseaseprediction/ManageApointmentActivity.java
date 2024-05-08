package com.example.heartdiseaseprediction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ManageApointmentActivity  extends AppCompatActivity {
    ImageButton btn_ReturnHome;
    CardView upcoming,history;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_apointment);

        btn_ReturnHome = findViewById(R.id.btn_ReturnHome);
        btn_ReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                startActivity(intent);
                finish();
            }
        });
        upcoming=findViewById(R.id.upcoming);
        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DetailApointmentActivity.class);
                startActivity(intent);
                finish();
            }
        });
        history=findViewById(R.id.History);
        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DetailApointmentActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
