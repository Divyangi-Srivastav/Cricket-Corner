package com.example.livecricketapp.admin.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecricketapp.R;
import com.example.livecricketapp.model.Reward;

import java.util.ArrayList;
import java.util.List;

public class DisplayRewardsAdapter extends RecyclerView.Adapter<DisplayRewardsAdapter.DisplayViewHolder> {

    private LayoutInflater layoutInflater ;
    private List<Reward> rewardList = new ArrayList<>();

    public DisplayRewardsAdapter (Context context , List<Reward> rewards)
    {
        layoutInflater = LayoutInflater.from(context);
        this.rewardList = rewards ;
    }

    @NonNull
    @Override
    public DisplayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_view_display_reward,parent,false);
        return new DisplayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayViewHolder holder, int position) {

        String tour = "Tournament Name : <b>" + rewardList.get(position).getTournamentName() + "</b>";
        String transactionId = "Transaction Id : <b>" + rewardList.get(position).getTransactionId() + "</b>";
        String user = "User Name : <b>" + rewardList.get(position).getUserName() + "</b>";
        String teamName = "Team Name : <b>" + rewardList.get(position).getTeamName() + "</b>";
        String match = "Match No : <b>" + rewardList.get(position).getMatchId() + "</b>";
        String amount = "Amount : <b>" + String.valueOf(rewardList.get(position).getAmount()) + "</b>";
        String playerName = "Player Name : <b>" + rewardList.get(position).getPlayerName() + "</b>";
        if (rewardList.get(position).getPlayerName().equalsIgnoreCase("Entire Team"))
            holder.playerName.setVisibility(View.GONE);

        holder.transactionId.setText(Html.fromHtml(transactionId));
        holder.userName.setText(Html.fromHtml(user));
        holder.tournamentName.setText(Html.fromHtml(tour));
        holder.matchNo.setText(Html.fromHtml(match));
        holder.teamName.setText(Html.fromHtml(teamName));
        holder.playerName.setText(Html.fromHtml(playerName));
        holder.amount.setText(Html.fromHtml(amount));
    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }

    public class DisplayViewHolder extends RecyclerView.ViewHolder {

        private TextView transactionId , userName , tournamentName , matchNo , teamName , playerName , amount;

        public DisplayViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionId = itemView.findViewById(R.id.transaction_id);
            userName = itemView.findViewById(R.id.user_name);
            tournamentName = itemView.findViewById(R.id.tour_name);
            matchNo = itemView.findViewById(R.id.match_no);
            teamName = itemView.findViewById(R.id.team_name);
            playerName = itemView.findViewById(R.id.player_name);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
