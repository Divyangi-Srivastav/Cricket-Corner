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

public class CreateTournamentThreeAdapterOne extends RecyclerView.Adapter<CreateTournamentThreeAdapterOne.CreateViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private CreateTournamentThreeAdapterTwo createTournamentThreeAdapterTwo;
    private TournamentInfo tournamentInfo;
    private String date;
    private Save_data save_data;
    private int no_of_days;

    public CreateTournamentThreeAdapterOne (Context context , TournamentInfo tournamentInfo , Save_data save_data)
    {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.tournamentInfo = tournamentInfo;
        date = tournamentInfo.getStart_date();
        this.save_data = save_data;
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

        if ( position > 0 )
        {
            date = OperationOnDate.date_increase(date);
        }
        createTournamentThreeAdapterTwo = new CreateTournamentThreeAdapterTwo(context,
                tournamentInfo.getNo_of_matches_day(),tournamentInfo.getMatchTimings(),tournamentInfo.getTeamNames(),date
                ,position );
        holder.recyclerView.setAdapter(createTournamentThreeAdapterTwo);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));

        if ( ( position + 1 ) == no_of_days)
        {
            holder.button2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return no_of_days;
    }

    public class CreateViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerView;
        private Button button2;

        public CreateViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            button2 = itemView.findViewById(R.id.btn_submit);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    save_data.save_all_data();
                }
            });
        }
    }
    public interface Save_data{
        void save_all_data();
    }
}
