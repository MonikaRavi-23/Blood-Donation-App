package com.example.erythrolinkapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class request_bloodActivity extends AppCompatActivity {

    Spinner bloodTypeSpinner, bloodGroupSpinner, urgencySpinner;
    EditText patientNameEt, attendeeMobileEt, unitsEt, requiredDateEt, hospitalNameEt, hospitalAddressEt, ageEt;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);

        // Initialize views
        bloodTypeSpinner = findViewById(R.id.bloodTypeSpinner);
        bloodGroupSpinner = findViewById(R.id.bloodGroupSpinner);
        urgencySpinner = findViewById(R.id.urgencySpinner);
        patientNameEt = findViewById(R.id.patientNameEt);
        attendeeMobileEt = findViewById(R.id.attendeeMobileEt);
        unitsEt = findViewById(R.id.unitsEt);
        requiredDateEt = findViewById(R.id.requiredDateEt);
        hospitalNameEt = findViewById(R.id.hospitalNameEt);
        hospitalAddressEt = findViewById(R.id.hospitalAddressEt);
        ageEt = findViewById(R.id.AgeEt);
        submitBtn = findViewById(R.id.submitBtn);

        // Set dropdown options
        String[] bloodTypes = {"Select Blood Type", "Whole Blood", "Plasma", "Platelets"};
        String[] bloodGroups = {"Select Blood Group", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        String[] urgencyLevels = {"Select Urgency Level", "Low", "Medium", "High", "Critical"};

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bloodTypes);
        ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bloodGroups);
        ArrayAdapter<String> urgencyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, urgencyLevels);

        bloodTypeSpinner.setAdapter(typeAdapter);
        bloodGroupSpinner.setAdapter(groupAdapter);
        urgencySpinner.setAdapter(urgencyAdapter);

        // 👉 Open Google Maps App directly when clicking Hospital Address
        hospitalAddressEt.setOnClickListener(v -> {
            double lat = 20.5937; // India center (you can change to your city)
            double lng = 78.9629;
            String label = "Search Hospital Location";

            Uri uri = Uri.parse("geo:" + lat + "," + lng + "?q=" + Uri.encode(label));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "Google Maps app is not installed", Toast.LENGTH_SHORT).show();
            }
        });

        // 📅 Date Picker for Required Date
        requiredDateEt.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    request_bloodActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        requiredDateEt.setText(date);
                    },
                    year, month, day
            );
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); // Disable past dates
            datePickerDialog.show();
        });

        // Submit button click
        submitBtn.setOnClickListener(v -> {
            String patientName = patientNameEt.getText().toString().trim();
            String mobile = attendeeMobileEt.getText().toString().trim();
            String hospital = hospitalNameEt.getText().toString().trim();
            String hospitalAddress = hospitalAddressEt.getText().toString().trim();
            String age = ageEt.getText().toString().trim();
            String units = unitsEt.getText().toString().trim();
            String requiredDate = requiredDateEt.getText().toString().trim();
            String selectedBloodType = bloodTypeSpinner.getSelectedItem().toString();
            String selectedBloodGroup = bloodGroupSpinner.getSelectedItem().toString();
            String urgency = urgencySpinner.getSelectedItem().toString();

            if (patientName.isEmpty() || mobile.isEmpty() || hospital.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            // ✅ Save to Firebase
            DatabaseReference requestRef = FirebaseDatabase.getInstance().getReference("Requests");
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("bloodType", selectedBloodType);
            requestMap.put("bloodGroup", selectedBloodGroup);
            requestMap.put("patientName", patientName);
            requestMap.put("age", age);
            requestMap.put("mobile", mobile);
            requestMap.put("units", units);
            requestMap.put("date", requiredDate);
            requestMap.put("urgency", urgency);
            requestMap.put("hospitalName", hospital);
            requestMap.put("hospitalAddress", hospitalAddress);

            requestRef.child(userId).setValue(requestMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Request Submitted!", Toast.LENGTH_SHORT).show();
                    finish(); // go back to HomeActivity
                } else {
                    Toast.makeText(this, "Failed to submit request", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
