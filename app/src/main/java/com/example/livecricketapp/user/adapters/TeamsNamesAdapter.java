package com.example.livecricketapp.user.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecricketapp.R;
import com.example.livecricketapp.model.SingleTeamInfo;

import java.util.ArrayList;
import java.util.List;

public class TeamsNamesAdapter extends RecyclerView.Adapter<TeamsNamesAdapter.TeamsViewHolder> {

    private LayoutInflater layoutInflater;
    private List<SingleTeamInfo> infoList = new ArrayList<>();
    private On_Click onClick;

    public TeamsNamesAdapter ( Context context , List<SingleTeamInfo> infoList , On_Click onClick)
    {
        layoutInflater = LayoutInflater.from(context);
        this.infoList = infoList;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public TeamsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_teams_and_matches_teamn,parent,false);
        return new TeamsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamsViewHolder holder, int position) {

        holder.teamName.setText(infoList.get(position).getTeamName());
        if ( infoList.get(position).getPaid() )
        {
            holder.card.setCardBackgroundColor(Color.parseColor("#F1FFDE"));
            holder.paid.setText("PAID");
        }
        else
        {
            holder.card.setCardBackgroundColor(Color.parseColor("#FFF1F1"));
            holder.paid.setText("UNPAID");
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.open_team(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public class TeamsViewHolder extends RecyclerView.ViewHolder {

        private TextView teamName, paid;
        private ImageView imageView;
        private CardView card;

        public TeamsViewHolder(@NonNull View itemView) {
            super(itemView);
            teamName = itemView.findViewById(R.id.team_name);
            paid = itemView.findViewById(R.id.paid);
            imageView = itemView.findViewById(R.id.image);
            card = itemView.findViewById(R.id.paid_card);
        }
    }

    public interface On_Click {
        void open_team ( int a );
    }
}
