package com.example.cop4331projectdefinitive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EmployeeSubChildAdapter extends RecyclerView.Adapter<EmployeeSubChildAdapter.ViewHolder> {

    ArrayList<String> detailList;
    Context mContext;

    public EmployeeSubChildAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_food_detail_layout,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.employeeDetailText.setText(detailList.get(position));
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    public void setDetailList(ArrayList<String> detailList) {
        this.detailList = detailList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView employeeDetailText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            employeeDetailText = itemView.findViewById(R.id.empFoodDetText);
        }
    }
}
