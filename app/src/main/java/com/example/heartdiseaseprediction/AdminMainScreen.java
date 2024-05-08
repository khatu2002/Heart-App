package com.example.heartdiseaseprediction;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.example.heartdiseaseprediction.ml.Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AdminMainScreen extends AppCompatActivity {

    TextView username, heartRateTextView;
    ;
    ImageButton LogOutButton, showMoreChartButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_screen);

        LogOutButton = findViewById(R.id.LogOutBtn);
        showMoreChartButton = findViewById(R.id.show_more_chart);


        username = findViewById(R.id.name);
        heartRateTextView = findViewById(R.id.heart_rate_value);
        getdata();
        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(AdminMainScreen.this, "LogOut Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        // Lắng nghe sự kiện click cho nút showMoreChartButton
        showMoreChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình ChartActivity
                Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
                startActivity(intent);
            }
        });

        // Lấy dữ liệu từ Firebase
        getdata();
    }
    private String getLatestHeartbeat(Map<String, String> cardiacInfo) {
        long latestTimeMillis = Long.MIN_VALUE;
        String latestHeartbeat = null;

        for (Map.Entry<String, String> entry : cardiacInfo.entrySet()) {
            String timeString = entry.getKey();
            String heartbeat = entry.getValue();

            // Chuyển đổi thời gian từ định dạng chuỗi sang millis
            long timeMillis = Long.parseLong(timeString);

            // Nếu thời gian của cặp này lớn hơn thời gian mới nhất đã biết
            if (timeMillis > latestTimeMillis) {
                // Cập nhật thời gian mới nhất và giá trị nhịp tim mới nhất
                latestTimeMillis = timeMillis;
                latestHeartbeat = heartbeat;
            }
        }

        // Trả về nhịp tim mới nhất
        return latestHeartbeat;
    }
    private void getdata() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("users");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LinearLayout patientListLayout = findViewById(R.id.patientListLayout);
                patientListLayout.removeAllViews(); // Clear previous views

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        View itemView = LayoutInflater.from(AdminMainScreen.this).inflate(R.layout.item_user, null);
                        TextView nameTextView = itemView.findViewById(R.id.name);
                        TextView heartRateTextView = itemView.findViewById(R.id.heart_rate_value);
                        nameTextView.setText(user.getUsername());
                        // Set heart rate based on user's data
                        String latestHeartbeat = getLatestHeartbeat(user.getCardiacInfo());
                        heartRateTextView.setText("Heart beat: " + latestHeartbeat + " bpm");
                        patientListLayout.addView(itemView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", "Database Error: " + databaseError.getMessage());
            }
        });
    }
}