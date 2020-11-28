package com.example.cop4331projectdefinitive;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import static com.example.cop4331projectdefinitive.CartActivity.PREPARING_TEXT;
import static com.example.cop4331projectdefinitive.CartActivity.READY_TEXT;
import static com.example.cop4331projectdefinitive.CartActivity.RECEIVED_TEXT;

public class EmployeeParentRecAdapter extends RecyclerView.Adapter<EmployeeParentRecAdapter.ViewHolder> {

    List<User> userList;
    EmployeeChildRecAdapter empChildAdapter;
    Context mContext;

    public EmployeeParentRecAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_card_layout,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User curUser = userList.get(position);
        Order userOrder = curUser.order;
        Cart userCart = userOrder.storedCart;
        ArrayList<MenuItem> cartItems = userCart.getCartContents();

        holder.orderStatusText.setText(userOrder.orderStatus);
        holder.orderCostText.setText(String.format("$ %.2f",userCart.getTotalCartCost()*1.065));
        holder.userNameText.setText(curUser.userName);

        empChildAdapter = new EmployeeChildRecAdapter(mContext);
        holder.orderItemRec.setAdapter(empChildAdapter);
        holder.orderItemRec.setLayoutManager(new LinearLayoutManager(holder.orderItemRec.getContext()));
        empChildAdapter.setItemList(cartItems);

        holder.advanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curText = holder.orderStatusText.getText().toString();

                if(curText.compareTo(RECEIVED_TEXT) == 0 || curText.compareTo(PREPARING_TEXT) == 0) {
                    if(curText.compareTo(RECEIVED_TEXT) == 0) {
                        curUser.order.setOrderStatus(PREPARING_TEXT);
                    }
                    else {
                        curUser.order.setOrderStatus(READY_TEXT);
                    }
                    AdvanceOrderTask task = new AdvanceOrderTask();
                    task.execute(curUser);
                }
                else {
                    curUser.order = null;
                    AdvanceOrderTask task = new AdvanceOrderTask();
                    task.execute(curUser);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderCostText;
        TextView orderStatusText;
        TextView userNameText;
        ImageButton advanceButton;
        RecyclerView orderItemRec;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderCostText = itemView.findViewById(R.id.empCostTxt);
            orderStatusText = itemView.findViewById(R.id.empStatusTxt);
            userNameText = itemView.findViewById(R.id.empViewUserName);
            advanceButton = itemView.findViewById(R.id.employeeAdvButton);
            orderItemRec = itemView.findViewById(R.id.empItemRecView);
        }
    }

    public class AdvanceOrderTask extends AsyncTask<User, Void, List<User>> {

        @Override
        protected void onPostExecute(List<User> userList) {
            super.onPostExecute(userList);
            setUserList(userList);
        }

        @Override
        protected List<User> doInBackground(User... users) {
            User userToUpdate = users[0];
            AppDatabase db = Room.databaseBuilder(mContext.getApplicationContext(),
                    AppDatabase.class, "user-database").build();
            db.userDao().updateUser(userToUpdate);
            List<User> userList = db.userDao().findAllWithOrders("null");
            return userList;
        }
    }
}
