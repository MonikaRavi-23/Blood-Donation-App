package com.example.erythrolinkapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    TextView welcomeTv, userIdTv, bloodGroupTv;
    ImageButton backBtn;
    Button requestBtn, donateBtn, contributeBtn, awarenessBtn;
    Button callPoliceBtn, callAmbulanceBtn; // ✅ new buttons only added

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize UI components
        welcomeTv = findViewById(R.id.welcomeTv);
        backBtn = findViewById(R.id.backBtn);
        userIdTv = findViewById(R.id.userIdTv);
        bloodGroupTv = findViewById(R.id.bloodGroupTv);
        requestBtn = findViewById(R.id.requestBtn);
        donateBtn = findViewById(R.id.donateBtn);
        contributeBtn = findViewById(R.id.contributeBtn);
        awarenessBtn = findViewById(R.id.awarenessBtn);
        callPoliceBtn = findViewById(R.id.callPoliceBtn);     // ✅ new
        callAmbulanceBtn = findViewById(R.id.callAmbulanceBtn); // ✅ new

        // Get data from LoginActivity
        String username = getIntent().getStringExtra("username");
        String userId = getIntent().getStringExtra("userId");
        String bloodGroup = getIntent().getStringExtra("bloodGroup");

        // Set welcome text
        if (username != null && !username.isEmpty()) {
            welcomeTv.setText("Welcome, " + username + "!");
        } else {
            welcomeTv.setText("Welcome!");
        }

        // Set user details
        userIdTv.setText(userId != null && !userId.isEmpty() ? "User ID: " + userId : "User ID: N/A");
        bloodGroupTv.setText(bloodGroup != null && !bloodGroup.isEmpty() ? "Blood Group: " + bloodGroup : "Blood Group: Not set");

        // Back button
        backBtn.setOnClickListener(v -> onBackPressed());

        // Button actions
        requestBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, request_bloodActivity.class);
            startActivity(intent);
        });

        donateBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DonateBloodActivity.class);
            startActivity(intent);
        });

        contributeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ContributeActivity.class);
            startActivity(intent);
        });

        awarenessBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AwarenessActivity.class);
            startActivity(intent);
        });

        // ✅ Emergency call buttons
        callPoliceBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:100"));
            startActivity(intent);
        });

        callAmbulanceBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:108"));
            startActivity(intent);
        });
    }
}
