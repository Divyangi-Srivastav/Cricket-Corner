package com.example.livecricketapp.admin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecricketapp.R;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private LayoutInflater layoutInflater;
    private List<String> comments = new ArrayList<>();
    private On_Click onClick;
    private String string;

    public CommentsAdapter (Context context , List<String> comments , On_Click onClick , String string)
    {
        layoutInflater = LayoutInflater.from(context);
        this.comments = comments;
        this.onClick = onClick;
        this.string = string;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_comments,parent,false);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {

        if ( string.equalsIgnoreCase("user") )
            holder.image.setVisibility(View.GONE);

        holder.comments.setText(comments.get(position));
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.delete_comment(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder {

        private TextView comments;
        private ImageView image;

        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            comments = itemView.findViewById(R.id.comment);
            image = itemView.findViewById(R.id.comment_cross);
        }
    }

    public interface On_Click{
        void delete_comment( int a );
    }

}
