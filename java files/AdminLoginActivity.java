package com.example.erythrolinkapp;


import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText hospitalEt, passwordEt;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        hospitalEt = findViewById(R.id.hospitalEt);
        passwordEt = findViewById(R.id.passwordEt);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(v -> {
            String hospital = hospitalEt.getText().toString().trim();
            String password = passwordEt.getText().toString().trim();

            if (hospital.equals("Hospital123") && password.equals("admin123")) {
                Toast.makeText(this, "Admin Login Successful", Toast.LENGTH_SHORT).show();
                // TODO: Redirect to Admin Dashboard
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
