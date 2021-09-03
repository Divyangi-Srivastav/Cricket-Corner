package com.example.livecricketapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {

    private LayoutInflater layoutInflater;

    public DashboardAdapter ( Context context )
    {
        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @NotNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DashboardAdapter.DashboardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DashboardViewHolder extends RecyclerView.ViewHolder {
        public DashboardViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
