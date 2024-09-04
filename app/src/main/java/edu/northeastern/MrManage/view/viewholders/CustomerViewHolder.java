package edu.northeastern.MrManage.view.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.MrManage.R;

public class CustomerViewHolder extends RecyclerView.ViewHolder {
    //This is where the attributes sit and gets assigned
    public TextView customerName;

    public CustomerViewHolder(@NonNull View itemView) {
        super(itemView);
        customerName = itemView.findViewById(R.id.customer_name);
    }
}
