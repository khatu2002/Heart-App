package com.example.heartdiseaseprediction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ReceiptActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recept);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String appoinmentID= sharedPreferences.getString("AppointmentKey", "");
      //them item vô scrollview history
        ScrollView scrollView = findViewById(R.id.scrollViewdrug);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayout);
        DatabaseReference appointmentsRef = FirebaseDatabase.getInstance().getReference("appointments");
        appointmentsRef.child(appoinmentID).child("medicines").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appointmentSnapshot : dataSnapshot.getChildren()) {
                        // Lấy dữ liệu từ dataSnapshot
                        String namedrug = appointmentSnapshot.child("name").getValue(String.class);
                        String routine = appointmentSnapshot.child("routine").getValue(String.class);
                        String amount = appointmentSnapshot.child("amount").getValue(String.class);
                        // Tạo view mới từ layout XML
                        LayoutInflater inflater = LayoutInflater.from(ReceiptActivity.this);
                        View itemView = inflater.inflate(R.layout.item_receipt, null);
                        // Đặt dữ liệu vào các TextView trong itemView

                        TextView name = itemView.findViewById(R.id.drugname);
                        name.setText(namedrug);
                        TextView Routine = itemView.findViewById(R.id.routine);
                        Routine.setText(routine);
                        TextView Amount = itemView.findViewById(R.id.amount);
                        Amount.setText(amount);

                        linearLayout.addView(itemView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
