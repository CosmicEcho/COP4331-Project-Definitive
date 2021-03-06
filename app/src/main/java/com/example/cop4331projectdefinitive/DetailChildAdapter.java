package com.example.cop4331projectdefinitive;

// Imports
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * RecyclerView Adapter that inflates cart_detail_layout and is used to populate the details RecyclerView of
 * cart_card_layout.
 */

public class DetailChildAdapter extends RecyclerView.Adapter<DetailChildAdapter.ViewHolder> {

    // Variable Declaration
    private ArrayList<String> detailList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_detail_layout,
                parent, false);

        return new ViewHolder(view);
    }

    // Sets childDetailTextView to the detailList.get() string
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.childDetailTextView.setText(detailList.get(position));
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    public void setDetailList(ArrayList<String> detailList) {
        this.detailList = detailList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView childDetailTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            childDetailTextView = itemView.findViewById(R.id.cartDetailText);
        }
    }

}
