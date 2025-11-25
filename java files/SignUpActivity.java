package com.example.erythrolinkapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    private EditText nameEt, emailEt, passwordEt, confirmPassEt, dobEt, phoneEt;
    private Spinner bloodGroupSp;
    private Button signUpBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        // UI references
        nameEt = findViewById(R.id.nameEt);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        confirmPassEt = findViewById(R.id.confirmPassEt);
        dobEt = findViewById(R.id.dobEt);
        phoneEt = findViewById(R.id.phoneEt);
        bloodGroupSp = findViewById(R.id.bloodGroupSp);
        signUpBtn = findViewById(R.id.signUpBtn);

        // Blood group spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.blood_groups, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroupSp.setAdapter(adapter);

        // Button action
        signUpBtn.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = nameEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        String confirmPass = confirmPassEt.getText().toString().trim();
        String dob = dobEt.getText().toString().trim();
        String phone = phoneEt.getText().toString().trim();
        String bloodGroup = bloodGroupSp.getSelectedItem().toString();

        // Validation
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(confirmPass) || TextUtils.isEmpty(dob)
                || TextUtils.isEmpty(phone) || TextUtils.isEmpty(bloodGroup)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPass)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "Registering user: " + email);

        // Create user with FirebaseAuth
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "FirebaseAuth: User created successfully");
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            String uid = firebaseUser.getUid();
                            Log.d(TAG, "FirebaseAuth: UID=" + uid);

                            // Save user info in Realtime Database first
                            HashMap<String, Object> userMap = new HashMap<>();
                            userMap.put("uid", uid);
                            userMap.put("name", name);
                            userMap.put("email", email);
                            userMap.put("dob", dob);
                            userMap.put("phone", phone);
                            userMap.put("bloodGroup", bloodGroup);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(uid)
                                    .setValue(userMap)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d(TAG, "Realtime DB: User saved successfully");

                                        // Redirect to HomeActivity after DB save
                                        Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                        intent.putExtra("username", name);
                                        intent.putExtra("userId", uid);
                                        intent.putExtra("bloodGroup", bloodGroup);
                                        startActivity(intent);
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e(TAG, "Realtime DB error", e);
                                        Toast.makeText(SignUpActivity.this, "Error saving user info", Toast.LENGTH_LONG).show();
                                    });
                        }
                    } else {
                        Exception e = task.getException();
                        if (e != null) {
                            Log.e(TAG, "FirebaseAuth error", e);
                            Toast.makeText(this, "Registration Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
