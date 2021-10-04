package com.example.livecricketapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecricketapp.R;
import com.example.livecricketapp.model.TournamentInfo;

import java.util.ArrayList;
import java.util.List;

public class HostAMatchTourAdapter extends RecyclerView.Adapter<HostAMatchTourAdapter.HostViewHolder> {

    private LayoutInflater layoutInflater;
    private List<TournamentInfo> infoList = new ArrayList<>();
    private On_Click onClick;

    public HostAMatchTourAdapter (Context context , List<TournamentInfo> infoList , On_Click onClick){
        layoutInflater = LayoutInflater.from(context);
        this.infoList = infoList;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public HostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_host_match_tour,parent,false);
        return new HostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HostViewHolder holder, int position) {
        holder.tour_name.setText(infoList.get(position).getTournamentName());
        holder.tour_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.linearLayout.setBackgroundResource(R.drawable.buttons);
                holder.tour_name.setTextColor(Color.parseColor("#FFFFFF"));
                onClick.select_tour(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public class HostViewHolder extends RecyclerView.ViewHolder {

        private TextView tour_name;
        private LinearLayout linearLayout;

        public HostViewHolder(@NonNull View itemView) {
            super(itemView);
            tour_name = itemView.findViewById(R.id.tournament_name);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }

    public interface On_Click{
        void select_tour ( int a );
    }
}
