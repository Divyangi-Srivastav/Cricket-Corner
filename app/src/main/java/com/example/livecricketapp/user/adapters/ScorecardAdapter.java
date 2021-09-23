package com.example.livecricketapp.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecricketapp.R;
import com.example.livecricketapp.model.TeamScoreCard;

public class ScorecardAdapter extends RecyclerView.Adapter<ScorecardAdapter.ScoreViewHolder> {

    private LayoutInflater layoutInflater;
    private TeamScoreCard scoreCard;
    private Update_scorecard update_scorecard;

    public ScorecardAdapter(Context context, TeamScoreCard card, Update_scorecard update_scorecard) {
        layoutInflater = LayoutInflater.from(context);
        this.scoreCard = card;
        this.update_scorecard = update_scorecard;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_scorecard, parent, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {

        holder.name.setText(scoreCard.getCards().get(position).getPlayerName());
        if (scoreCard.getCards().get(position).getRuns() == 0) {
            holder.runs.setText("");
            holder.runs_nill.setVisibility(View.VISIBLE);
        } else {
            holder.runs.setText(String.valueOf(scoreCard.getCards().get(position).getRuns()));
            holder.runs_nill.setVisibility(View.GONE);
        }
        if (scoreCard.getCards().get(position).getWickets() == 0) {
            holder.wickets.setText("");
            holder.wickets_nill.setVisibility(View.VISIBLE);
        } else {
            holder.wickets.setText(String.valueOf(scoreCard.getCards().get(position).getWickets()));
            holder.wickets_nill.setVisibility(View.GONE);
        }

        holder.runs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_scorecard.update_runs(holder.getAdapterPosition(), scoreCard.getTeamName());
            }
        });

        holder.wickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_scorecard.update_wickets(holder.getAdapterPosition(), scoreCard.getTeamName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return 11;
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder {

        private TextView name, runs, wickets;
        private View runs_nill, wickets_nill;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            runs = itemView.findViewById(R.id.runs);
            wickets = itemView.findViewById(R.id.wickets);
            runs_nill = itemView.findViewById(R.id.runs_nill);
            wickets_nill = itemView.findViewById(R.id.wickets_nill);
        }
    }

    public interface Update_scorecard {
        void update_runs(int a, String teamName);

        void update_wickets(int a, String teamName);
    }

}
