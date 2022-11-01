package com.sgtech.learnkotlin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class FbRecycler extends RecyclerView.Adapter<FbRecycler.ViewHolder> {
    Context context;
    int i = 0;
    ArrayList<DataHolder> dataHolders;

    public FbRecycler(ArrayList<DataHolder> dataHolders, Context context) {
        this.context = context;
        this.dataHolders = dataHolders;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(dataHolders.get(position).getTitle());
        if (i < position) {
            holder.cardView.setAnimation(setAnimation());
            i++;
        }

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DashBoardActivity.class);
            intent.putExtra(KeyClass.FIRE_ID, position+1);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataHolders.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            cardView = itemView.findViewById(R.id.layout);
        }
    }

    public Animation setAnimation() {
        Animation animation =
                AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        animation.setDuration(500);
        return animation;
    }

}
