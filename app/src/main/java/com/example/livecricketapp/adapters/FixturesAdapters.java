package com.example.livecricketapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecricketapp.R;
import com.example.livecricketapp.model.SingleMatchInfo;

import java.util.ArrayList;
import java.util.List;

public class FixturesAdapters extends RecyclerView.Adapter<FixturesAdapters.FixturesViewHolder> {

    private LayoutInflater layoutInflater;
    private List<SingleMatchInfo> matchInfos = new ArrayList<>();
    private On_click onClick;

    public FixturesAdapters ( Context context , List<SingleMatchInfo> matchInfos , On_click onClick )
    {
        layoutInflater = LayoutInflater.from(context);
        this.matchInfos = matchInfos;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public FixturesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_fixtures_adapter,parent,false);
        return new FixturesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FixturesViewHolder holder, int position) {

        holder.match.setText(matchInfos.get(position).getMatchNo() + " | " + matchInfos.get(position).getDate() + " | " + matchInfos.get(position).getTime());
        holder.team1.setText(matchInfos.get(position).getTeam1Score().getTeamName());
        holder.team2.setText(matchInfos.get(position).getTeam2Score().getTeamName());
        if ( !matchInfos.get(position).getWinningTeam().equalsIgnoreCase("") ) {
            if (matchInfos.get(position).getTeam2Score().getTeamName().equalsIgnoreCase(matchInfos.get(position).getWinningTeam()))
                holder.team2.setTypeface(holder.team2.getTypeface(), Typeface.BOLD);
            else
                holder.team1.setTypeface(holder.team1.getTypeface(), Typeface.BOLD);
        }
        else
        {
            holder.team2.setTypeface(holder.team2.getTypeface(), Typeface.BOLD);
            holder.team1.setTypeface(holder.team1.getTypeface(), Typeface.BOLD);
        }

        if ( !matchInfos.get(position).getMatchResult().equalsIgnoreCase("") )
        {
            holder.matchResult.setText(matchInfos.get(position).getMatchResult());
        }

        switch ( matchInfos.get(position).getMatchStatus() )
        {
            case 0 : holder.cardView.setCardBackgroundColor(Color.parseColor("#FFF1F1"));
            break;
            case 1 : holder.cardView.setCardBackgroundColor(Color.parseColor("#F1FFDE"));
            break;
            case 2 : holder.cardView.setCardBackgroundColor(Color.parseColor("#E9F4FF"));
            break;
        }

    }

    @Override
    public int getItemCount() {
        return matchInfos.size();
    }

    public class FixturesViewHolder extends RecyclerView.ViewHolder {

        private TextView match , team1 , team2 , matchResult , view_more;
        private CardView cardView;

        public FixturesViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.linear_layout);
            match = itemView.findViewById(R.id.match);
            team1 = itemView.findViewById(R.id.team1);
            team2 = itemView.findViewById(R.id.team2);
            matchResult = itemView.findViewById(R.id.winner_statement);
            view_more = itemView.findViewById(R.id.view_more);
            view_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.move_to_match_settings(getAdapterPosition());
                }
            });
        }
    }

    public interface On_click {
        void move_to_match_settings( int a );
    }
}
