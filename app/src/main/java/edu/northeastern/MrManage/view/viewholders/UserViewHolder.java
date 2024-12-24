package edu.northeastern.MrManage.view.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.MrManage.R;

public class UserViewHolder extends RecyclerView.ViewHolder {
    //This is where the attributes sit and gets assigned
    public TextView userName;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        userName = itemView.findViewById(R.id.user_name);
    }
}
