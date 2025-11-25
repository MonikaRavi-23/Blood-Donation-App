package com.example.erythrolinkapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.DonorViewHolder> {
    private List<Donor> donorList;

    public DonorAdapter(List<Donor> donorList) {
        this.donorList = donorList;
    }

    @NonNull
    @Override
    public DonorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_donor, parent, false);
        return new DonorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorViewHolder holder, int position) {
        Donor donor = donorList.get(position);
        holder.nameTv.setText("Name: " + donor.getName());
        holder.mobileTv.setText("Mobile: " + donor.getMobile());
        holder.bloodGroupTv.setText("Blood Group: " + donor.getBloodGroup());
    }

    @Override
    public int getItemCount() {
        return donorList.size();
    }

    public static class DonorViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv, mobileTv, bloodGroupTv;

        public DonorViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.donorNameTv);
            mobileTv = itemView.findViewById(R.id.donorMobileTv);
            bloodGroupTv = itemView.findViewById(R.id.donorBloodGroupTv);
        }
    }
}

