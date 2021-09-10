package com.example.livecricketapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecricketapp.R;

public class ParticipationFeesAdapter extends RecyclerView.Adapter<ParticipationFeesAdapter.ParticipationViewHolder> {

    private LayoutInflater layoutInflater;

    public ParticipationFeesAdapter( Context context )
    {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ParticipationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_partication_adapter,parent,false);
        return new ParticipationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ParticipationViewHolder extends RecyclerView.ViewHolder {

        private TextView team_name , amount_team  , paid ;
        private ImageView image;

        public ParticipationViewHolder(@NonNull View itemView) {
            super(itemView);
            team_name = itemView.findViewById(R.id.team_name);
            amount_team = itemView.findViewById(R.id.amount_team);
            paid = itemView.findViewById(R.id.paid);
            image = itemView.findViewById(R.id.image);
        }
    }
}
