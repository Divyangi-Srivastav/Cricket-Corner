package com.example.livecricketapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecricketapp.R;

public class FixturesAdapters extends RecyclerView.Adapter<FixturesAdapters.FixturesViewHolder> {

    private LayoutInflater layoutInflater;

    public FixturesAdapters (Context context)
    {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public FixturesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_fixtures_adapter,parent,false);
        return new FixturesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FixturesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class FixturesViewHolder extends RecyclerView.ViewHolder {
        public FixturesViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
