package com.example.livecricketapp.admin.adapters;

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
import com.example.livecricketapp.model.SingleMatchInfo;

import java.util.ArrayList;
import java.util.List;

public class HostAMatch_MatchAdapter extends RecyclerView.Adapter<HostAMatch_MatchAdapter.HostViewHolder> {

    private LayoutInflater layoutInflater;
    private List<SingleMatchInfo> matchInfoList = new ArrayList<>();
    private On_Click onClick;

    public HostAMatch_MatchAdapter (Context context , List<SingleMatchInfo> matchInfoList , On_Click onClick)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.matchInfoList = matchInfoList;
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
        holder.match_no.setText(matchInfoList.get(position).getMatchNo());
        holder.match_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.linearLayout.setBackgroundResource(R.drawable.buttons);
                holder.match_no.setTextColor(Color.parseColor("#FFFFFF"));
                onClick.select_match(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return matchInfoList.size();
    }

    public class HostViewHolder extends RecyclerView.ViewHolder {

        private TextView match_no;
        private LinearLayout linearLayout;

        public HostViewHolder(@NonNull View itemView) {
            super(itemView);
            match_no = itemView.findViewById(R.id.tournament_name);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }

    public interface On_Click{
        void select_match ( int a );
    }
}
