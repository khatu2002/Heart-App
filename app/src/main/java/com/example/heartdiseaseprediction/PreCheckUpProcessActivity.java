package com.example.heartdiseaseprediction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PreCheckUpProcessActivity  extends AppCompatActivity {

    TextView temperater,blood_pressure;
    ImageView MedicalExamination;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precheckup_process);
        temperater=findViewById(R.id.temperater);
        blood_pressure=findViewById(R.id.bloodPressure);



        MedicalExamination=findViewById(R.id.MedicalExamination);
        MedicalExamination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("temperater", temperater.getText().toString());
                editor.putString("blood_pressure", blood_pressure.getText().toString());
                editor.apply();
                Intent intent=new Intent(getApplicationContext(),MedicalExaminationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
