package com.example.livecricketapp.user.adapters;

import android.content.Context;
import android.text.Html;
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

        String purchase_no = "Purchase : <b>" + String.valueOf(position+1) + "</b>";
        String subscription;
        if ( list.get(position).getTourSubscription() ) {
            subscription = "Subscription Brought : <b>Tournament</b>";
            holder.match_no.setVisibility(View.GONE);
        }else{
            subscription = "Subscription Brought : <b>Match</b>";
        }
        String tour = "Tournament Name : <b>" + list.get(position).getTournament_name() + "</b>";
        String date_of_purchase = "Date of Purchase : <b>" + list.get(position).getDate_of_purchase() + "</b>";
        String valid_from = "Valid From : <b>" + list.get(position).getValidFrom() + "</b>";
        String valid_till = "Valid Till : <b>" + list.get(position).getValidTill() + "</b>";
        String match = "Match No : <b>" + list.get(position).getMatchId() + "</b>";

        holder.purchase_no.setText(Html.fromHtml(purchase_no));
        holder.subscription_type.setText(Html.fromHtml(subscription));
        holder.tour_name.setText(Html.fromHtml(tour));
        holder.date_of_purchase.setText(Html.fromHtml(date_of_purchase));
        holder.valid_from.setText(Html.fromHtml(valid_from));
        holder.valid_till.setText(Html.fromHtml(valid_till));
        holder.match_no.setText(Html.fromHtml(match));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewSubscriptionHolder extends RecyclerView.ViewHolder {

        private TextView purchase_no , subscription_type ,tour_name , date_of_purchase , valid_from , valid_till , match_no ;

        public ViewSubscriptionHolder(@NonNull View itemView) {
            super(itemView);
            purchase_no = itemView.findViewById(R.id.purchase_no);
            subscription_type = itemView.findViewById(R.id.subcription_type);
            tour_name = itemView.findViewById(R.id.tour_name);
            date_of_purchase = itemView.findViewById(R.id.date_of_purchase);
            valid_from = itemView.findViewById(R.id.valid_from);
            valid_till = itemView.findViewById(R.id.valid_till);
            match_no = itemView.findViewById(R.id.match_no);
        }
    }
}
