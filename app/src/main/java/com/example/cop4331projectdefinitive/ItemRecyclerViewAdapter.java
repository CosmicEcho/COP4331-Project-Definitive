package com.example.cop4331projectdefinitive;

// Imports

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * RecyclerView Adapter that inflates the item_card_layout and is used to populate the Recycler View for
 * MenuItemActivity and activity_menu_item.xml. In addition to filling the RecyclerView with Strings from
 * detailList, this class keeps track of selected items using selectedDetails.
 */

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder>{

    // Variable Declaration
    private ArrayList<String> detailList;
    private ArrayList<String> selectedDetails;
    private Context mContext;

    public ItemRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
        selectedDetails = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String selected = detailList.get(position);
        holder.detail.setText(selected);

        // On click listener that checks the status of detail checkboxes and adds or removes them from
        // the selectedDetails ArrayList
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.detail.isChecked()) {
                    selectedDetails.add(selected);
                }
                else {
                    selectedDetails.remove(selected);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    public void setDetailList(ArrayList<String> detailList) {
        this.detailList = detailList;
        notifyDataSetChanged();
    }

    // Function that is called by MenuItemActivity to get the applied MenuItem customization
    public ArrayList<String> returnSelected() {
        return selectedDetails;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private CheckBox detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.detailCardLayout);
            detail = itemView.findViewById(R.id.detailCheckBox);
        }
    }
}
