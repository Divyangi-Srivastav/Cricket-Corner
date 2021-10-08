package com.example.livecricketapp.admin.adapters;

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

public class ParticipationFeesAdapter extends RecyclerView.Adapter<ParticipationFeesAdapter.ParticipationViewHolder> {

    private LayoutInflater layoutInflater;
    private List<SingleTeamInfo> infoList = new ArrayList<>();
    private On_Click onClick;

    public ParticipationFeesAdapter( Context context , List<SingleTeamInfo> infoList , On_Click onClick)
    {
        layoutInflater = LayoutInflater.from(context);
        this.infoList = infoList;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ParticipationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_partication_adapter,parent,false);
        return new ParticipationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipationViewHolder holder, int position) {

        holder.team_name.setText(infoList.get(position).getTeamName());
        holder.amount_team.setText("Amount Paid : " + String.valueOf( infoList.get(position).getAmountPaid() ));

        if ( infoList.get(position).getPaid() )
        {
            holder.paid.setText("PAID");
            holder.paid_card.setCardBackgroundColor(Color.parseColor("#F1FFDE"));
            holder.image.setVisibility(View.GONE);
        }
        else
        {
            holder.paid.setText("UNPAID");
            holder.paid_card.setCardBackgroundColor(Color.parseColor("#FFF1F1"));
        }

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.remove_team(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public class ParticipationViewHolder extends RecyclerView.ViewHolder {

        private TextView team_name , amount_team  , paid ;
        private ImageView image;
        private CardView paid_card;

        public ParticipationViewHolder(@NonNull View itemView) {
            super(itemView);
            team_name = itemView.findViewById(R.id.team_name);
            amount_team = itemView.findViewById(R.id.amount_team);
            paid = itemView.findViewById(R.id.paid);
            image = itemView.findViewById(R.id.image);
            paid_card = itemView.findViewById(R.id.paid_card);
        }
    }

    public interface On_Click{

        void remove_team( int a );

    }

}
