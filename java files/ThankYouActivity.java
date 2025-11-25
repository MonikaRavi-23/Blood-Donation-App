package com.example.erythrolinkapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ThankYouActivity extends AppCompatActivity {

    TextView thankYouTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        thankYouTv = findViewById(R.id.thankYouTv);

        String userName = getIntent().getStringExtra("userName");
        String contributionType = getIntent().getStringExtra("contributionType");

        String message = " Thank You " +
                "🎉 Payment Successful 🎉\n\n" +
                "Dear " + "Suppoter" + ",\n" +
                "Thank you for your kind " + "Donation "+ " ❤️\n\n" +
                "Your support means the world to us and helps save precious lives. 🌍";

        thankYouTv.setText(message);
    }
}
