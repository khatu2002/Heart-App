package com.example.heartdiseaseprediction;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ManageApointmentActivity  extends AppCompatActivity {
    ImageButton btn_ReturnHome;
    CardView upcoming,history;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String userId = currentUser.getUid();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    TextView service,date;
    DatabaseReference appointmentsRef;
    ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_apointment);

<<<<<<< HEAD
=======
        btn_ReturnHome = findViewById(R.id.btn_ReturnHome);
        btn_ReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                startActivity(intent);
                finish();
            }
        });
>>>>>>> origin/main
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

        //them item vô scrollview history
        ScrollView scrollView = findViewById(R.id.scrollItemHistory);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayout);
        DatabaseReference appointmentsRef = FirebaseDatabase.getInstance().getReference().child("appointments");


        // Tạo truy vấn để lấy tất cả các cuộc hẹn có userID và status mong muốn
<<<<<<< HEAD
        Query appointmentsQuery = appointmentsRef.orderByChild("status").equalTo( "finish");
=======
        Query appointmentsQuery = appointmentsRef.orderByChild("userid_status").equalTo(userId + "_incoming");
>>>>>>> origin/main

        appointmentsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appointmentSnapshot : dataSnapshot.getChildren()) {
                    if(appointmentSnapshot.child("user").child("userID").getValue(String.class).equals(userId)){

                    // Lấy dữ liệu từ dataSnapshot
                    String Service = appointmentSnapshot.child("service").getValue(String.class);
                    String Date = appointmentSnapshot.child("date").getValue(String.class);
                    String IDAppointment =appointmentSnapshot.getKey();
                    // Tạo view mới từ layout XML
                    LayoutInflater inflater = LayoutInflater.from(ManageApointmentActivity.this);
                    View itemView = inflater.inflate(R.layout.item_appointment_history, null);
                    // Đặt dữ liệu vào các TextView trong itemView
                    TextView serviceTextView = itemView.findViewById(R.id.servicename);
                    serviceTextView.setText(Service);

                    TextView dateTextView = itemView.findViewById(R.id.date);
                    dateTextView.setText(Date);

                    linearLayout.addView(itemView);
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("DateApoimentSelect", Date);
                                editor.putString("NameApoimentSelect", Service);
                                SaveSessionAppointment(IDAppointment);
                                SendToDetailAppoiment();

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //them item vô scrollview incoming
        ScrollView scrollView1 = findViewById(R.id.scrollItemIncoming);
        LinearLayout linearLayout1 = new LinearLayout(this);
        linearLayout1.setOrientation(LinearLayout.VERTICAL);
        scrollView1.addView(linearLayout1);
        // Tạo truy vấn để lấy tất cả các cuộc hẹn có userID và status mong muốn
<<<<<<< HEAD
        Query appointmentsQueryend = appointmentsRef.orderByChild("status").equalTo(userId + "incoming");;
=======
        Query appointmentsQueryend = appointmentsRef.orderByChild("userid_status").equalTo(userId + "_finish");;
>>>>>>> origin/main
        appointmentsQueryend.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appointmentSnapshot : dataSnapshot.getChildren()) {
                    if (appointmentSnapshot.child("user").child("userID").getValue(String.class).equals(userId)) {
                        // Lấy dữ liệu từ dataSnapshot
                        String Service = appointmentSnapshot.child("service").getValue(String.class);
                        String Date = appointmentSnapshot.child("date").getValue(String.class);
                        String IDAppointment =appointmentSnapshot.getKey();
                        // Tạo view mới từ layout XML
                        LayoutInflater inflater = LayoutInflater.from(ManageApointmentActivity.this);
                        View itemView = inflater.inflate(R.layout.item_appointment_incoming, null);
                        // Đặt dữ liệu vào các TextView trong itemView
                        TextView serviceTextView = itemView.findViewById(R.id.Serive);
                        serviceTextView.setText(Service);

                        TextView dateTextView = itemView.findViewById(R.id.dateservice);
                        dateTextView.setText(Date);

                        linearLayout1.addView(itemView);
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("DateApoimentSelect", Date);
                                editor.putString("NameApoimentSelect", Service);
                                editor.putString("appointmentID", IDAppointment);
                                SaveSessionAppointment(IDAppointment);
                                SendToDetailAppoiment();

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    private void SaveSessionAppointment(String IDAppointment){
        DatabaseReference appointmentsRef = FirebaseDatabase.getInstance().getReference().child("appointments");
        Query appointmentsQuery = appointmentsRef.orderByKey().equalTo(IDAppointment);
        appointmentsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Kiểm tra xem có dữ liệu trả về không
                if (dataSnapshot.exists()) {
                    // Lấy dữ liệu từ dataSnapshot
                    DataSnapshot Snapshot = dataSnapshot.getChildren().iterator().next();
                    String service =Snapshot.child("service").getValue(String.class);
                    String date =Snapshot.child("date").getValue(String.class);
                    AppointmentSessionManager Session = new AppointmentSessionManager(getApplicationContext());
                    Session.createAppointmentSession(new Appointment(date,service));

                } else {
                    Toast.makeText(getApplicationContext(), "Appoiment not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
                Toast.makeText(getApplicationContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void SendToDetailAppoiment(){
        Intent intent = new Intent(getApplicationContext(), DetailApointmentActivity.class);
        startActivity(intent);
        finish();
    }
}
