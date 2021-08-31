package com.example.livecricketapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecricketapp.R;

import org.jetbrains.annotations.NotNull;

public class CreateTournamentAdapter extends RecyclerView.Adapter<CreateTournamentAdapter.CreateViewHolder> {

    private LayoutInflater layoutInflater;
    private int a;

    public CreateTournamentAdapter ( Context context , int a )
    {
        layoutInflater = LayoutInflater.from(context);
        this.a = a;
    }

    @NonNull
    @NotNull
    @Override
    public CreateViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_view_layout,parent,false);
        return new CreateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CreateTournamentAdapter.CreateViewHolder holder, int position) {

        holder.editText.setHint("Enter Name of Team " + String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return a;
    }

    public class CreateViewHolder extends RecyclerView.ViewHolder {

        private EditText editText;

        public CreateViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.name);
        }
    }
}
