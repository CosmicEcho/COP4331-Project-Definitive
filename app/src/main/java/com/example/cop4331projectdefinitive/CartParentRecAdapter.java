package com.example.cop4331projectdefinitive;

// Imports
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * RecyclerView Adapter that inflates the cart_card_layout and is used to populate the RecyclerView
 * for CartActivity and activity_cart.xml. Utilizes DetailChildAdapter to populate the RecyclerView
 * of details for each item in the cart.
 */

public class CartParentRecAdapter extends RecyclerView.Adapter<CartParentRecAdapter.ViewHolder> {

    // Variable Declaration
    ArrayList<MenuItem> cartItems;
    DetailChildAdapter detailAdapter;
    Context mContext;

    public CartParentRecAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_card_layout,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem currentItem = cartItems.get(position);
        holder.foodName.setText(currentItem.getItemName());
        holder.cartCostText.setText("$ " + Double.toString(currentItem.getItemPrice()));

        // RecyclerView initialization for selected details of item in the cartItems ArrayList
        detailAdapter = new DetailChildAdapter();
        holder.detailRecView.setAdapter(detailAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.detailRecView.getContext());
        holder.detailRecView.setLayoutManager(layoutManager);
        detailAdapter.setDetailList(currentItem.appliedDetail);

        // OnClickListener that deletes items from the cart and updates the RecyclerView accordingly.
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Delete this item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.getInstance().getCart().removeItem(currentItem);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void setCartItems(ArrayList<MenuItem> cartItems) {
        this.cartItems = cartItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodName;
        TextView cartCostText;
        ImageButton deleteButton;
        RecyclerView detailRecView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foodName = itemView.findViewById(R.id.cartFoodTitle);
            deleteButton = itemView.findViewById(R.id.cartDeleteButton);
            detailRecView = itemView.findViewById(R.id.cartRecView);
            cartCostText = itemView.findViewById(R.id.cartCostText);
        }
    }
}
