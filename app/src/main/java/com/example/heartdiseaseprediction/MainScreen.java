package com.example.heartdiseaseprediction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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

import com.example.heartdiseaseprediction.databinding.ActivityMainScreenBinding;
import com.example.heartdiseaseprediction.ml.Model;
import com.github.mikephil.charting.charts.Chart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.*;



public class MainScreen extends AppCompatActivity
{

    BottomNavigationView bottomNavigationView;
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
    ChartFragment chartFragment = new ChartFragment();
    AppointmentFragment appointmentFragment = new AppointmentFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    ActivityMainScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        // Khởi tạo bottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

// Set item được chọn
        bottomNavigationView.setSelectedItemId(R.id.home);

// Thiết lập lắng nghe sự kiện cho bottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.home){
                replaceFragment(new HomeFragment());
            } else if (id == R.id.chart) {
                replaceFragment(new ChartFragment());

            } else if (id == R.id.history) {
                replaceFragment(new AppointmentFragment());
            } else if (id == R.id.user) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });




    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,fragment);
        fragmentTransaction.commit();
    }


}