package com.example.livecricketapp.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecricketapp.activities.AdsRequest;

public class AdRequestsAdapter extends RecyclerView.Adapter<AdRequestsAdapter.AdViewHolder> {
    @NonNull
    @Override
    public AdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class AdViewHolder extends RecyclerView.ViewHolder {
        public AdViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
