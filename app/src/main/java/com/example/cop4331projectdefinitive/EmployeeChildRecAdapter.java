package com.example.cop4331projectdefinitive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EmployeeChildRecAdapter extends RecyclerView.Adapter<EmployeeChildRecAdapter.ViewHolder> {

    private ArrayList<MenuItem> itemList;
    private Context mContext;

    public EmployeeChildRecAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_food_item_layout,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        EmployeeSubChildAdapter subChildAdapter = new EmployeeSubChildAdapter(mContext);

        holder.employeeFoodText.setText(itemList.get(position).getItemName());
        holder.employeeDetRecView.setAdapter(subChildAdapter);
        holder.employeeDetRecView.setLayoutManager(new LinearLayoutManager(mContext));
        subChildAdapter.setDetailList(itemList.get(position).appliedDetail);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setItemList(ArrayList<MenuItem> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView employeeFoodText;
        RecyclerView employeeDetRecView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            employeeFoodText = itemView.findViewById(R.id.empFoodNameTxt);
            employeeDetRecView = itemView.findViewById(R.id.empFoodDetRecView);
        }
    }
}
