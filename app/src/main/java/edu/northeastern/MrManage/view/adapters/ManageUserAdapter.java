package edu.northeastern.MrManage.view.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.entities.User;
import edu.northeastern.MrManage.view.activity.CustomerProfileActivity;
import edu.northeastern.MrManage.view.viewholders.UserViewHolder;

public class ManageUserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    // private String TAG = ManageUserAdapter.class.getSimpleName();
    //This is where the list of customer should come
    private List<User> users;

    public ManageUserAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the item layout
        View customer_item_layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_layout, parent, false);
        //create the view holder
        return new UserViewHolder(customer_item_layout);
    }

    //To display the data at particular position
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = users.get(position);

        //bind the data to viewholder views
        holder.userName.setText(user.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Position: " + position, Toast.LENGTH_LONG).show();
                if (user.isCustomer()) {
                    Intent intent = new Intent(v.getContext(), CustomerProfileActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("client_data", user);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void updateUsers(List<User> newUsers) {
        users.clear();
        users.addAll(newUsers);
        notifyDataSetChanged();
    }
}
