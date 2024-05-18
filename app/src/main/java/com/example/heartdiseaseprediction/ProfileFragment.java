package com.example.heartdiseaseprediction;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {



    View rootView;
    ImageView logOut;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Khai báo TextView
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView textViewUsername = rootView.findViewById(R.id.textView_Username);
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
        //Show profile action
        logOut = rootView.findViewById(R.id.LogOutBtn);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                Toast.makeText(getActivity(),"LogOut Successfully",Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;
    }
}