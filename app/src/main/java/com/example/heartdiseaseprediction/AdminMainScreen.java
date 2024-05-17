package com.example.heartdiseaseprediction;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ScrollView;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class AdminMainScreen extends AppCompatActivity {

    TextView username;
    ;
    ImageButton LogOutButton, showMoreChartButton;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String userId = currentUser.getUid();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_sceen);




        //username = findViewById(R.id.name);
        //heartRateTextView = findViewById(R.id.heart_rate_value);
        //getdata();
        LogOutButton = findViewById(R.id.LogOutBtn);
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


        // Lấy dữ liệu từ Firebase
        //getdata();
        //them item vô scrollview history
        ScrollView scrollView = findViewById(R.id.scrollItemIncoming);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayout);
        DatabaseReference appointmentsRef = FirebaseDatabase.getInstance().getReference().child("appointments");


        // Tạo truy vấn để lấy tất cả các cuộc hẹn có userID và status mong muốn
        Query appointmentsQuery = appointmentsRef.orderByChild("status").equalTo("incoming");

        appointmentsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appointmentSnapshot : dataSnapshot.getChildren()) {
                    // Lấy dữ liệu từ dataSnapshot
                    String nameStr = appointmentSnapshot.child("users").child("username").getValue(String.class);
                    String service = appointmentSnapshot.child("service").getValue(String.class);
                    String date=appointmentSnapshot.child("date").getValue(String.class);

                    // Tạo view mới từ layout XML
                    LayoutInflater inflater = LayoutInflater.from(AdminMainScreen.this);
                    View itemView = inflater.inflate(R.layout.item_user, null);
                    showMoreChartButton = itemView.findViewById(R.id.show_more_chart);
                    // Lắng nghe sự kiện click cho nút showMoreChartButton
                    showMoreChartButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Chuyển đến màn hình ChartActivity
                            Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
                            startActivity(intent);
                        }
                    });
                    // Đặt dữ liệu vào các TextView trong itemView
                    TextView Name = itemView.findViewById(R.id.name);
                    Name.setText(nameStr);

                    TextView Service = itemView.findViewById(R.id.servicename);
                    Service.setText(service+"    "+date);

                    linearLayout.addView(itemView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //them item vô scrollview incoming
        ScrollView scrollView1 = findViewById(R.id.scrollItemHistory);
        LinearLayout linearLayout1 = new LinearLayout(this);
        linearLayout1.setOrientation(LinearLayout.VERTICAL);
        scrollView1.addView(linearLayout1);
        // Tạo truy vấn để lấy tất cả các cuộc hẹn có userID và status mong muốn
        // Tạo truy vấn để lấy tất cả các cuộc hẹn có userID và status mong muốn
        Query appointmentsQueryEnd = appointmentsRef.orderByChild("status").equalTo("finish");

        appointmentsQueryEnd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appointmentSnapshot : dataSnapshot.getChildren()) {
                    // Lấy dữ liệu từ dataSnapshot
                    String nameStr = appointmentSnapshot.child("users").child("username").getValue(String.class);
                    String service = appointmentSnapshot.child("service").getValue(String.class);
                    String date=appointmentSnapshot.child("date").getValue(String.class);

                    // Tạo view mới từ layout XML
                    LayoutInflater inflater = LayoutInflater.from(AdminMainScreen.this);
                    View itemView = inflater.inflate(R.layout.item_user, null);
                    showMoreChartButton = itemView.findViewById(R.id.show_more_chart);
                    // Lắng nghe sự kiện click cho nút showMoreChartButton
                    showMoreChartButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Chuyển đến màn hình ChartActivity
                            Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
                            startActivity(intent);
                        }
                    });
                    // Đặt dữ liệu vào các TextView trong itemView
                    TextView Name = itemView.findViewById(R.id.name);
                    Name.setText(nameStr);

                    TextView Service = itemView.findViewById(R.id.servicename);
                    Service.setText(service+"    "+date);

                    linearLayout.addView(itemView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

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
//    private void getdata() {
//        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("users");
//
//        databaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                LinearLayout patientListLayout = findViewById(R.id.patientListLayout);
//                patientListLayout.removeAllViews(); // Clear previous views
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    User user = snapshot.getValue(User.class);
//                    if (user != null) {
//                        View itemView = LayoutInflater.from(AdminMainScreen.this).inflate(R.layout.item_user, null);
//                        TextView nameTextView = itemView.findViewById(R.id.name);
//                        TextView heartRateTextView = itemView.findViewById(R.id.heart_rate_value);
//                        nameTextView.setText(user.getUsername());
//                        // Set heart rate based on user's data
//                        String latestHeartbeat = getLatestHeartbeat(user.getCardiacInfo());
//                        heartRateTextView.setText("Heart beat: " + latestHeartbeat + " bpm");
//                        patientListLayout.addView(itemView);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.d("TAG", "Database Error: " + databaseError.getMessage());
//            }
//        });
//    }
}