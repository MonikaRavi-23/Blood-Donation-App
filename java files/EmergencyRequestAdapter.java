package com.example.erythrolinkapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmergencyRequestAdapter extends RecyclerView.Adapter<EmergencyRequestAdapter.ViewHolder> {

    private List<EmergencyRequest> requestList;

    public EmergencyRequestAdapter(List<EmergencyRequest> requestList) {
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emergency_request_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmergencyRequest request = requestList.get(position);

        holder.patientTv.setText("Patient: " + request.getPatient());
        holder.hospitalTv.setText("Hospital: " + request.getHospital());
        holder.unitsTv.setText("Units: " + request.getUnits());
        holder.ageTv.setText("Age: " + request.getAge());
        holder.bloodGroupTv.setText("Blood Group: " + request.getBloodGroup());
        holder.bloodTypeTv.setText("Type: " + request.getBloodType());
        holder.userTv.setText("Requested By (User): " + request.getUserId());

    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView patientTv, hospitalTv, unitsTv, ageTv, bloodGroupTv, bloodTypeTv, userTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            patientTv = itemView.findViewById(R.id.patientTv);
            hospitalTv = itemView.findViewById(R.id.hospitalTv);
            unitsTv = itemView.findViewById(R.id.unitsTv);
            ageTv = itemView.findViewById(R.id.ageTv);
            bloodGroupTv = itemView.findViewById(R.id.bloodGroupTv);
            bloodTypeTv = itemView.findViewById(R.id.bloodTypeTv);
            userTv = itemView.findViewById(R.id.userTv);
        }
    }
}
