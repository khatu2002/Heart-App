package com.example.heartdiseaseprediction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;


public class ChooseServiceActivity extends AppCompatActivity {
    ImageView schedule;
    ImageButton btn_ReturnHome;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooce_service);
        schedule=findViewById(R.id.Schedule);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScheduleActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_ReturnHome = findViewById(R.id.btn_ReturnHome);
//

        btn_ReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                startActivity(intent);
                finish();
            }
        });
        Spinner spinner = findViewById(R.id.ChooseService);
        String[] services = {"Cardiology services", "Heart health programs", "Cardiovascular diagnostics",
                "Interventional cardiology", "Electrophysiology services", "Cardiac rehabilitation",
                "Heart surgery", "Heart transplant services", "Cardiothoracic surgery", "Pediatric cardiology services"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, services);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
}
