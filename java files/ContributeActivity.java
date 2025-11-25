package com.example.erythrolinkapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ContributeActivity extends AppCompatActivity {

    EditText nameEt, emailEt;
    Spinner typeSp;
    Button contributeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute);

        nameEt = findViewById(R.id.nameEt);
        emailEt = findViewById(R.id.emailEt);
        typeSp = findViewById(R.id.typeSp);
        contributeBtn = findViewById(R.id.contributeBtn);

        // Spinner values
        String[] contributionTypes = {"Donate", "Volunteer", "Spread Awareness", "Resource Support"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, contributionTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSp.setAdapter(adapter);

        contributeBtn.setOnClickListener(v -> {
            String name = nameEt.getText().toString().trim();
            String email = emailEt.getText().toString().trim();
            String type = typeSp.getSelectedItem().toString();

            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill all details!", Toast.LENGTH_SHORT).show();
            } else {
                // ✅ Open PaymentActivity
                Intent intent = new Intent(ContributeActivity.this, PaymentActivity.class);
                intent.putExtra("USER_NAME", name);
                intent.putExtra("USER_EMAIL", email);
                intent.putExtra("CONTRIBUTION_TYPE", type);
                startActivity(intent);
            }
        });
    }
}
