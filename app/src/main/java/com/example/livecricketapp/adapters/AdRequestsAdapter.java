package com.example.livecricketapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livecricketapp.R;
import com.example.livecricketapp.model.AdBanner;

import java.util.ArrayList;
import java.util.List;

public class AdRequestsAdapter extends RecyclerView.Adapter<AdRequestsAdapter.AdViewHolder> {

    private LayoutInflater layoutInflater;
    private List<AdBanner> bannerList = new ArrayList<>();

    public AdRequestsAdapter(Context context, List<AdBanner> list) {
        layoutInflater = LayoutInflater.from(context);
        this.bannerList = list;
    }

    @NonNull
    @Override
    public AdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_view_ads, parent, false);
        return new AdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdViewHolder holder, int position) {

        if (!bannerList.get(position).getAdImageUrl().equalsIgnoreCase("")) {
            Glide.with(holder.image)
                    .asBitmap()
                    .load(bannerList.get(position).getAdImageUrl())
                    .into(holder.image);
        }
        holder.adText.setText(bannerList.get(position).getAdMessage());
        holder.adTime.setText("Time :- " + String.valueOf(bannerList.get(position).getAdTime()) + "min");
        holder.adMoney.setText("Paid :- ₹ " + String.valueOf(bannerList.get(position).getAmountPaid()));
    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    public class AdViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView adText, adTime, adMoney;

        public AdViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            adText = itemView.findViewById(R.id.ad_text);
            adMoney = itemView.findViewById(R.id.ad_money);
            adTime = itemView.findViewById(R.id.ad_time);

        }
    }
}
