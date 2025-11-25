package com.example.erythrolinkapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DonateBloodActivity extends AppCompatActivity {

    EditText donorNameEt, donorMobileEt, donorBloodGroupEt;
    RadioButton healthyRb;
    Button registerBtn;
    RecyclerView donorRecyclerView;

    DonorAdapter donorAdapter;
    List<Donor> donorList;

    DatabaseReference donorDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_blood);

        donorNameEt = findViewById(R.id.donorNameEt);
        donorMobileEt = findViewById(R.id.donorMobileEt);
        donorBloodGroupEt = findViewById(R.id.donorBloodGroupEt);
        healthyRb = findViewById(R.id.healthyRb);
        registerBtn = findViewById(R.id.registerBtn);
        donorRecyclerView = findViewById(R.id.donorRecyclerView);

        donorList = new ArrayList<>();
        donorAdapter = new DonorAdapter(donorList);
        donorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        donorRecyclerView.setAdapter(donorAdapter);

        // ✅ Firebase reference
        donorDbRef = FirebaseDatabase.getInstance().getReference("donors");

        // ✅ Load existing donors from Firebase
        donorDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                donorList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Donor donor = ds.getValue(Donor.class);
                    if (donor != null) {
                        donorList.add(donor);
                    }
                }
                donorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(DonateBloodActivity.this, "Failed to load donors", Toast.LENGTH_SHORT).show();
            }
        });

        // ✅ Add new donor
        registerBtn.setOnClickListener(v -> {
            String name = donorNameEt.getText().toString().trim();
            String mobile = donorMobileEt.getText().toString().trim();
            String bloodGroup = donorBloodGroupEt.getText().toString().trim();

            if (name.isEmpty() || mobile.isEmpty() || bloodGroup.isEmpty() || !healthyRb.isChecked()) {
                Toast.makeText(this, "Please complete all fields!", Toast.LENGTH_SHORT).show();
            } else {
                String id = donorDbRef.push().getKey(); // unique ID
                Donor donor = new Donor(name, mobile, bloodGroup);
                donorDbRef.child(id).setValue(donor);

                donorNameEt.setText("");
                donorMobileEt.setText("");
                donorBloodGroupEt.setText("");
                healthyRb.setChecked(false);

                Toast.makeText(this, "Donor Registered Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
