package com.example.heartdiseaseprediction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import com.example.heartdiseaseprediction.ml.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Locale;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.google.firebase.auth.FirebaseAuth;

import android.media.MediaPlayer;



public class MainScreen extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    // creating a variable for our
    // Database Reference for Firebase.
    DatabaseReference databaseReference;
    MediaPlayer mediaPlayer;
    // variable for Text view.
    TextView tv_HeartRate;
    TextView tv_timeStamp;
    CircularProgressBar prog_Bar;
    Button btn_ShowProfile,btn_ShowChart,btn_ShowHistory;
    ImageButton LogOutButton;

    FirebaseAuth mAuth;

    long k = 1000;
    float[] mean = {-6.8565e-08F, -5.0756e-08F,-5.4763e-08F,
            1.6562e-07F,-1.5583e-08F, -1.2466e-08F, -6.6784e-08F,
            -5.3205e-08F, -2.7604e-08F, -4.2742e-08F, 9.7059e-08F};
    float[] variance = {1.0000F, 1.0000F, 1.0000F, 1.0000F, 1.0000F, 1.0000F, 1.0000F, 1.0000F, 1.0000F,
            1.0000F, 1.0000F};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        tv_HeartRate = findViewById(R.id.textView_HeartRate);
        tv_timeStamp = findViewById(R.id.lastResultDate);
        prog_Bar = findViewById(R.id.circularProgressBar);
        LogOutButton=findViewById(R.id.LogOutBtn);
        //tv_HeartRate = findViewById(R.id.textView_HeartRate);
        //btn_ShowProfile = findViewById(R.id.btn_ShowProfile);
        //result = findViewById(R.id.tv_result);
        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get
        // reference for our database.
        databaseReference = firebaseDatabase.getReference("data");

        btn_ShowProfile = findViewById(R.id.btn_ShowProfile);
        //Show profile action
        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(MainScreen.this,"LogOut Successfully",Toast.LENGTH_SHORT).show();
            }
        });
        btn_ShowProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_ShowChart=findViewById(R.id.btn_ShowChart);
        btn_ShowChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_ShowHistory=findViewById(R.id.btn_ShowHistory);
        btn_ShowHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManageApointmentActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // initializing our object class variable.
        // calling method
        // for getting data.
        getdata();
    }

    private void getdata() {

        // calling add value event listener method
        // for getting the values from database.
        databaseReference.child("93eXberrMRh04kseFhXvBNegRvI3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                String beatValue = String.valueOf(snapshot.child("beat").getValue());

                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                String timestamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
                databaseReference = firebaseDatabase.getReference("users");
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference userCardiacInfoRef = FirebaseDatabase.getInstance().getReference("users")
                        .child(userId)
                        .child("cardiacInfo");



                databaseReference.child("users").child(userId).child("cardiacInfo").child("lastUpdateTimestamp").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            // Tạo một đối tượng Date đại diện cho thời điểm hiện tại
                            Date now = new Date();

                            // Định dạng thời gian hiện tại theo kiểu yyyyMMddHH
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");

                            // Chuyển đổi thời gian hiện tại sang chuỗi theo định dạng đã chỉ định
                            final String timestampFinal = sdf.format(now);

                            databaseReference.child("users").child(userId).child("cardiacInfo").child(timestampFinal).setValue(beatValue)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d("Firebase", "Heart rate successfully written!");
                                        // Cập nhật lastUpdateTimestamp với biến final

                                    })
                                    .addOnFailureListener(e -> Log.w("Firebase", "Error writing heart rate", e));
                        } else {
                            Log.w("Firebase", "Failed to get last update timestamp", task.getException());
                        }

                    }
                });
                // our value to our text view in below line.
                tv_HeartRate.setText(beatValue);
                tv_timeStamp.setText("Last result: " + currentDate + " at " + currentTime);
                float beat = Float.parseFloat(beatValue);
                float beatid = 100 * (beat / 250);
                prog_Bar.setProgressWithAnimation((int)beatid, k); // =1s
                //calPrediction();
                try {
                    Predict(40, 1, 2, 140, 289, 0, 0, (int)beat, 0, 0, 1);
                }
                catch(IOException e)
                {
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MainScreen.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void Predict(int Age, int Sex, int Cp, int Restbps, int Chol,
                         int Fbs, int Rest_ecg, int Heart_rate, int Agina, float Oldpeak, int Slope) throws IOException {
        Model model = Model.newInstance(getApplicationContext());
        float[] floatArr = {(float)Age, (float)Sex, (float)Cp, (float)Restbps, (float)Chol, (float)Fbs
                , (float)Rest_ecg, (float)Heart_rate, (float)Agina, (float)Oldpeak, (float)Slope};
        // Creates inputs for reference.
        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 11}, DataType.FLOAT32);

        //Scale input
        scaleTensorBuffer(inputFeature0, mean, variance);

        inputFeature0.loadArray(floatArr);

        // Runs model inference and gets result.
        Model.Outputs outputs = model.process(inputFeature0);
        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
        TextView tv= findViewById(R.id.textView_PredictResult);
        float[] data1=outputFeature0.getFloatArray();
        if (data1[0] >= data1[1])
        {
            tv.setTextColor(Color.BLACK);
            tv.setText("Your Heart health is NORMAL now!");
        }
        else {
            tv.setTextColor(Color.RED);
            tv.setText("Your Heart health is Dangerous!!!");
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep_warning);
            mediaPlayer.start();
        }
        // Releases model resources if no longer used.
        model.close();
    }
    public void scaleTensorBuffer(TensorBuffer tensorBuffer, float[] mean, float[] variance) {
        float[] floatValues = tensorBuffer.getFloatArray();
        for (int i = 0; i < floatValues.length; i++) {
            float stdDev = (float) Math.sqrt(variance[i]);
            floatValues[i] = (floatValues[i] - mean[i]) / stdDev;
        }
        tensorBuffer.loadArray(floatValues);
    }
}