package com.example.erythrolinkapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEt, passwordEt;
    private Button loginBtn;
    private TextView signUpTv;
    private RadioButton userRadio;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // UI references
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        loginBtn = findViewById(R.id.loginBtn);
        signUpTv = findViewById(R.id.signUpTv);
        userRadio = findViewById(R.id.radioUser);

        // Default → User
        userRadio.setChecked(true);

        // Login button
        loginBtn.setOnClickListener(v -> {
            // Only user login now
            loginUser();
        });

        // Sign up redirect
        signUpTv.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class))
        );
    }

    private void loginUser() {
        String email = emailEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();

        // Validation
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        // FirebaseAuth login
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            String uid = firebaseUser.getUid();

                            // Check user info in Realtime Database
                            DatabaseReference userRef = FirebaseDatabase.getInstance()
                                    .getReference("Users")
                                    .child(uid);

                            userRef.get().addOnSuccessListener(dataSnapshot -> {
                                if (dataSnapshot.exists()) {
                                    String name = dataSnapshot.child("name").getValue(String.class);
                                    String bloodGroup = dataSnapshot.child("bloodGroup").getValue(String.class);

                                    // Open HomeActivity
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putExtra("username", name);
                                    intent.putExtra("userId", uid);
                                    intent.putExtra("bloodGroup", bloodGroup);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // User exists in Auth but not in DB
                                    mAuth.signOut();
                                    Toast.makeText(LoginActivity.this,
                                            "Account setup incomplete. Please register again.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(e ->
                                    Toast.makeText(LoginActivity.this,
                                            "DB Error: " + e.getMessage(),
                                            Toast.LENGTH_SHORT).show()
                            );
                        }
                    } else {
                        Toast.makeText(this,
                                "Login Failed: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
