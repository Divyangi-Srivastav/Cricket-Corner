package com.example.livecricketapp.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecricketapp.R;

public class ScorecardAdapter extends RecyclerView.Adapter<ScorecardAdapter.ScoreViewHolder> {

    private LayoutInflater layoutInflater;

    public ScorecardAdapter (Context context)
    {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_scorecard,parent,false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 11;
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder {

        private TextView name , runs , wickets ;
        private View runs_nill , wickets_nill  ;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            runs = itemView.findViewById(R.id.runs);
            wickets = itemView.findViewById(R.id.wickets);
            runs_nill = itemView.findViewById(R.id.runs_nill);
            wickets_nill = itemView.findViewById(R.id.wickets_nill);
        }
    }
}
