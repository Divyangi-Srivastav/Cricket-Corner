package com.example.livecricketapp.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecricketapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CreateTournamentAdapter extends RecyclerView.Adapter<CreateTournamentAdapter.CreateViewHolder> {

    private LayoutInflater layoutInflater;
    private int a;
    private  List<String> strings = new ArrayList<>();

    public CreateTournamentAdapter(Context context, int a) {
        layoutInflater = LayoutInflater.from(context);
        this.a = a;
    }

    @NonNull
    @NotNull
    @Override
    public CreateViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_view_layout, parent, false);
        return new CreateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CreateTournamentAdapter.CreateViewHolder holder, int position) {

        holder.editText.setHint("Enter Name of Team " + String.valueOf(position + 1));
        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                for ( int i=0 ; i < strings.size() ; i++ )
                {
                    if ( strings.contains(s.toString()) )
                    {
                        strings.remove(i);
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                strings.add(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return a;
    }

    public List<String> get_list ()
    {
        return strings;
    }

    public class CreateViewHolder extends RecyclerView.ViewHolder {

        private EditText editText;
        private ImageView imageView;

        public CreateViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.name_cross);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.getText().clear();
                }
            });
        }
    }
}
