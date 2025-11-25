package com.example.erythrolinkapp;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class PaymentActivity extends AppCompatActivity {

    TextView contributionTypeTv, userNameTv, userEmailTv;
    EditText cardNumberEt, cardHolderEt, cvvEt, expiryDateEt;
    Button submitBtn;

    String contributionType, userName, userEmail;
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // ✅ Receive data from ContributeActivity
        userName = getIntent().getStringExtra("USER_NAME");
        userEmail = getIntent().getStringExtra("USER_EMAIL");
        contributionType = getIntent().getStringExtra("CONTRIBUTION_TYPE");

        // ✅ Bind UI
        contributionTypeTv = findViewById(R.id.contributionTypeTv);
        userNameTv = findViewById(R.id.userNameTv);
        userEmailTv = findViewById(R.id.userEmailTv);

        cardNumberEt = findViewById(R.id.cardNumberEt);
        cardHolderEt = findViewById(R.id.cardHolderEt);
        cvvEt = findViewById(R.id.cvvEt);
        expiryDateEt = findViewById(R.id.expiryDateEt);
        submitBtn = findViewById(R.id.submitBtn);

        // ✅ Set details
        contributionTypeTv.setText("Contribution Type: " + contributionType);
        userNameTv.setText("Name: " + userName);
        userEmailTv.setText("Email: " + userEmail);

        // ✅ Handle submit
        submitBtn.setOnClickListener(v -> validateAndProcessPayment());
    }

    private void validateAndProcessPayment() {
        String cardNumber = cardNumberEt.getText().toString().trim();
        String holder = cardHolderEt.getText().toString().trim();
        String cvv = cvvEt.getText().toString().trim();
        EditText expiryDateEt = findViewById(R.id.expiryDateEt);
        String expiry = expiryDateEt.getText().toString().trim();

        if (cardNumber.isEmpty() || holder.isEmpty() || cvv.isEmpty() || expiry.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ Validate expiry format
        if (!expiry.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
            Toast.makeText(this, "Enter valid expiry (MM/YY)", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Payment Successful ✅", Toast.LENGTH_LONG).show();

        // Proceed to OTPActivity
        Intent otpIntent = new Intent(PaymentActivity.this, OTPActivity.class);
        otpIntent.putExtra("USER_NAME", userName);
        otpIntent.putExtra("CONTRIBUTION_TYPE", contributionType);
        startActivity(otpIntent);

        // Send notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_REQUEST_CODE);
            } else {
                sendThankYouNotification(userName, contributionType);
            }
        } else {
            sendThankYouNotification(userName, contributionType);
        }
    }

    private void sendThankYouNotification(String name, String type) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = "thanks_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Thank You Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.heart_logo)
                .setContentTitle("Thank You ❤️")
                .setContentText("Heartfelt thanks, " + name + " for your " + type + "!")
                .setAutoCancel(true);

        manager.notify(1, builder.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendThankYouNotification(userName, contributionType);
            }
        }
    }
}
