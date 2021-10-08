package com.example.livecricketapp.admin.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecricketapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CreateTournamentTimeAdapter extends RecyclerView.Adapter<CreateTournamentTimeAdapter.CreateViewHolder> {

    private LayoutInflater layoutInflater;
    private int a;
    private List<String> timeList = new ArrayList<>();

    public CreateTournamentTimeAdapter (Context context , int a)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.a = a;
    }

    @NonNull
    @NotNull
    @Override
    public CreateViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.recycler_view_time,parent,false);
        return new CreateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CreateTournamentTimeAdapter.CreateViewHolder holder, int position) {

        holder.button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                timeList.add( String.valueOf(holder.timePicker.getHour()) + " : " + String.valueOf(holder.timePicker.getMinute()) );
                holder.button.setVisibility(View.GONE);
            }
        });
    }

    public List<String> getTimeList ()
    {
        return timeList;
    }

    @Override
    public int getItemCount() {
        return a;
    }

    public class CreateViewHolder extends RecyclerView.ViewHolder {

        private TimePicker timePicker;
        private Button button;

        public CreateViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            timePicker = itemView.findViewById(R.id.time_picker1);
            button = itemView.findViewById(R.id.save_time);
        }
    }
}
