package com.example.heartdiseaseprediction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.heartdiseaseprediction.ml.Model;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {
    // creating a variable for
    // our Firebase Database.
    //FirebaseDatabase firebaseDatabase;
    // creating a variable for our
    // Database Reference for Firebase.
    //DatabaseReference databaseReference;
    ImageButton btn_ReturnHome;
    // variable for Text view.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        // Set Home selected


        // Khai báo TextView
        TextView textViewUsername = findViewById(R.id.textView_Username);
        FirebaseAuth auth = FirebaseAuth.getInstance();
// Khai báo DatabaseReference
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());

// Thực hiện lắng nghe sự kiện để lấy dữ liệu từ Firebase
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Lấy dữ liệu của trường "username" từ dataSnapshot
                    String username = dataSnapshot.child("username").getValue(String.class);

                    // Hiển thị username lên TextView
                    textViewUsername.setText(username);
                } else {
                    // Không tìm thấy dữ liệu hoặc user không tồn tại
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi truy vấn cơ sở dữ liệu
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

    }
}