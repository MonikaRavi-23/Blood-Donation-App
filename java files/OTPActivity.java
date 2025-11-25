package com.example.erythrolinkapp;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class OTPActivity extends AppCompatActivity {

    EditText otpEt;
    Button verifyOtpBtn;
    String generatedOtp, userName, contributionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpEt = findViewById(R.id.otpEt);
        verifyOtpBtn = findViewById(R.id.verifyOtpBtn);

        // Get data from PaymentActivity
        userName = getIntent().getStringExtra("USER_NAME");
        contributionType = getIntent().getStringExtra("CONTRIBUTION_TYPE");

        // Simulate sending OTP
        generatedOtp = String.format("%06d", new Random().nextInt(999999));
        Toast.makeText(this, "OTP sent: " + generatedOtp, Toast.LENGTH_LONG).show();

        verifyOtpBtn.setOnClickListener(v -> {
            String enteredOtp = otpEt.getText().toString().trim();

            if (enteredOtp.isEmpty()) {
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show();
                return;
            }

            if (enteredOtp.equals(generatedOtp)) {
                Toast.makeText(this, "OTP Verified ✅", Toast.LENGTH_SHORT).show();

                // Go to ThankYouActivity
                Intent intent = new Intent(OTPActivity.this, ThankYouActivity.class);
                intent.putExtra("USER_NAME", userName);
                intent.putExtra("CONTRIBUTION_TYPE", contributionType);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid OTP ❌", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

