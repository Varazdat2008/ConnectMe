package com.example.teachme;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

    private Context context;
    private List<UserModel> userModelList;

    public UsersAdapter(Context context) {
        this.context = context;
        this.userModelList = new ArrayList<>(); // Ensure the list is initialized
    }

    public void add(UserModel userModel) {
        if (userModelList != null) {
            userModelList.add(userModel);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (userModelList != null) {
            userModelList.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (userModelList != null && position < userModelList.size()) {
            UserModel userModel = userModelList.get(position);
            holder.name.setText(userModel.getUserName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("id", userModel.getUserId());
                    intent.putExtra("name", userModel.getUserName());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (userModelList != null) ? userModelList.size() : 0;
    }

    public List<UserModel> getUserModelList() {
        return userModelList;
    }

    public void setUserModelList(List<UserModel> userModelList) {
        if (userModelList != null) {
            this.userModelList = userModelList;
        } else {
            this.userModelList = new ArrayList<>();
        }
        notifyDataSetChanged(); // Notify RecyclerView about the data change
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.username);
        }
    }
}
