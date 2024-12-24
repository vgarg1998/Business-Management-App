package edu.northeastern.MrManage.view.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.MrManage.R;

public class DeliveryViewHolder extends RecyclerView.ViewHolder {
    TextView customerName, productName, quantity, date;

    public DeliveryViewHolder(@NonNull View itemView) {
        super(itemView);
        customerName = itemView.findViewById(R.id.customerName);
        productName = itemView.findViewById(R.id.productName);
        quantity = itemView.findViewById(R.id.quantity);
        date = itemView.findViewById(R.id.date);
    }
}