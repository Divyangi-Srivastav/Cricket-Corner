package com.example.livecricketapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecricketapp.DataOperations.OperationOnDate;
import com.example.livecricketapp.R;
import com.example.livecricketapp.model.TournamentInfo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CreateTournamentThreeAdapterOne extends RecyclerView.Adapter<CreateTournamentThreeAdapterOne.CreateViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private CreateTournamentThreeAdapterTwo createTournamentThreeAdapterTwo;
    private TournamentInfo tournamentInfo;
    private int no_of_days;
    private List<String> dates = new ArrayList<>();

    public CreateTournamentThreeAdapterOne (Context context , TournamentInfo tournamentInfo , List<String> dates)
    {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.tournamentInfo = tournamentInfo;
        this.dates = dates;
        no_of_days = OperationOnDate.number_of_days(tournamentInfo.getStart_date(),tournamentInfo.getEnd_date());
    }

    @NonNull
    @NotNull
    @Override
    public CreateViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_view_matches_one,parent,false);
        return new CreateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CreateTournamentThreeAdapterOne.CreateViewHolder holder, int position) {
        
        createTournamentThreeAdapterTwo = new CreateTournamentThreeAdapterTwo(context,
                tournamentInfo.getNo_of_matches_day(),tournamentInfo.getMatchTimings(),tournamentInfo.getTeamNames(),dates.get(position)
                ,position );
        holder.recyclerView.setAdapter(createTournamentThreeAdapterTwo);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public int getItemCount() {
        return no_of_days;
    }

    public class CreateViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerView;

        public CreateViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_view);
        }
    }
}
