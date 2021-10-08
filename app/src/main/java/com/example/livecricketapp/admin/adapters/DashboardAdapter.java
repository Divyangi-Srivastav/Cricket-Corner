package com.example.livecricketapp.admin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecricketapp.R;
import com.example.livecricketapp.model.DashboardTournamentInfo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {

    private LayoutInflater layoutInflater;
    private Move_Next move_next;
    private List<DashboardTournamentInfo> infoList = new ArrayList<>();

    public DashboardAdapter ( Context context , Move_Next move_next , List<DashboardTournamentInfo> infoList )
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.move_next = move_next;
        this.infoList = infoList;
    }


    @NonNull
    @NotNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_view_dashboard,parent,false);
        return new DashboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DashboardAdapter.DashboardViewHolder holder, int position) {

        holder.name.setText(infoList.get(position).getTournamentName());
        holder.host_name.setText("Hosted By :- " + infoList.get(position).getHostName());
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public class DashboardViewHolder extends RecyclerView.ViewHolder {

        private TextView name , host_name , view_more;

        public DashboardViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tournament_name);
            host_name = itemView.findViewById(R.id.tournament_host_name);
            view_more = itemView.findViewById(R.id.view_more);
            view_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    move_next.move_to_other_activity(getAdapterPosition());
                }
            });
        }
    }

    public interface Move_Next{
        void move_to_other_activity( int a );
    }
}
