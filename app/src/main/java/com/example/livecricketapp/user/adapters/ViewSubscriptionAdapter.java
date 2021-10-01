package com.example.livecricketapp.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecricketapp.R;
import com.example.livecricketapp.model.SingleSubscription;

import java.util.ArrayList;
import java.util.List;

public class ViewSubscriptionAdapter extends RecyclerView.Adapter<ViewSubscriptionAdapter.ViewSubscriptionHolder> {

    private List<SingleSubscription> list = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public ViewSubscriptionAdapter ( Context context , List<SingleSubscription> list )
    {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    @Override
    public ViewSubscriptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_view_subscription,parent,false);
        return new ViewSubscriptionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewSubscriptionHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewSubscriptionHolder extends RecyclerView.ViewHolder {

        private TextView purchase_no , subscription_type ,tour_name , date_of_purchase , valid_from , valid_till ;

        public ViewSubscriptionHolder(@NonNull View itemView) {
            super(itemView);
            purchase_no = itemView.findViewById(R.id.purchase_no);
            subscription_type = itemView.findViewById(R.id.subcription_type);
            tour_name = itemView.findViewById(R.id.tour_name);
            date_of_purchase = itemView.findViewById(R.id.date_of_purchase);
            valid_from = itemView.findViewById(R.id.valid_from);
            valid_till = itemView.findViewById(R.id.valid_till);


        }
    }
}
