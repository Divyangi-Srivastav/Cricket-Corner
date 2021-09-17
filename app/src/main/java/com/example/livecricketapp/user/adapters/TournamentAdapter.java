package com.example.livecricketapp.user.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecricketapp.R;
import com.example.livecricketapp.model.TournamentInfo;

import java.util.ArrayList;
import java.util.List;

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.TournamentViewHolder> {

    private String status;
    private LayoutInflater layoutInflater;
    private List<TournamentInfo> infoList = new ArrayList<>();

    public TournamentAdapter (Context context , List<TournamentInfo> infoList , String status)
    {
        layoutInflater = LayoutInflater.from(context);
        this.infoList = infoList;
        this.status = status;
    }

    @NonNull
    @Override
    public TournamentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_view_tournament , parent, false);
        return new TournamentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TournamentViewHolder holder, int position) {

        holder.name.setText(infoList.get(position).getTournamentName());
        holder.no_of_teams.setText("Number of Teams : " + String.valueOf(infoList.get(position).getNumber_of_teams()));
        holder.start_date.setText("Start Date : " + infoList.get(position).getStart_date());
        holder.end_date.setText("End Date : " + infoList.get(position).getEnd_date());

        if ( status.equalsIgnoreCase("previous") )
        {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFF1F1"));
        }
        else if ( status.equalsIgnoreCase("ongoing")  )
        {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#F1FFDE"));
        }
        else if ( status.equalsIgnoreCase("upcoming") )
        {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#E9F4FF"));
        }
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public class TournamentViewHolder extends RecyclerView.ViewHolder {

        private TextView name,no_of_teams,start_date,end_date,winning_team;
        private CardView cardView;

        public TournamentViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tournament_name);
            no_of_teams = itemView.findViewById(R.id.no_of_teams);
            start_date = itemView.findViewById(R.id.start_date);
            end_date = itemView.findViewById(R.id.end_date);
            winning_team = itemView.findViewById(R.id.winning_team);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
