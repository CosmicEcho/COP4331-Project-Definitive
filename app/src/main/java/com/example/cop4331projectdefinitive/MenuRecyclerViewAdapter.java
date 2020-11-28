package com.example.cop4331projectdefinitive;

// Imports

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * RecyclerView Adapter that inflates menu_card_layout and is used to populate the RecyclerViews for
 * MenuActivity and activity_menu.xml. Contains an onClickListener that directs the user to MenuItemActivity
 * with the ID of the respective MenuItem from the menuItems ArrayList.
 */

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.ViewHolder> {
    // Constant that is used as the intent key for MenuItems being passed to MenuItemActvity
    public static final String FOOD_KEY = "foodKey";

    // Variable Declaration
    private ArrayList<MenuItem> menuItems = new ArrayList<>();
    private Context mContext;

    public MenuRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.foodName.setText(menuItems.get(position).getItemName());
        holder.imgFood.setImageResource(R.drawable.food_placeholder);

        // On click listener that directs the user to MenuItemActivity and sends an int representing
        // the given MenuItem ID
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MenuItemActivity.class);
                intent.putExtra(FOOD_KEY, menuItems.get(position).getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private ImageView imgFood;
        private TextView foodName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent_menu);
            imgFood = itemView.findViewById(R.id.imgFood);
            foodName = itemView.findViewById(R.id.foodName);
        }
    }
}
