package com.example.heartdiseaseprediction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PreCheckUpProcessActivity  extends AppCompatActivity {

    TextView temperater, blood_pressure;
    ImageView MedicalExamination;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precheckup_process);
        temperater = findViewById(R.id.temperater);
        blood_pressure = findViewById(R.id.bloodPressure);


        MedicalExamination = findViewById(R.id.MedicalExamination);
        MedicalExamination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckConstraint() == true) {
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("temperater", temperater.getText().toString());
                    editor.putString("blood_pressure", blood_pressure.getText().toString());
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), MedicalExaminationActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public boolean CheckConstraint() {
        temperater = findViewById(R.id.temperater);
        blood_pressure = findViewById(R.id.bloodPressure);
        if (TextUtils.isEmpty(temperater.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter temperater", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(blood_pressure.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter blood pressure", Toast.LENGTH_SHORT).show();
        }
        return true;

    }
}
