package edu.northeastern.MrManage.view.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.MrManage.R;

public class ReceivedOrderViewHolder extends RecyclerView.ViewHolder{
    public TextView productName;
    public TextView orderDate;
    public TextView orderQuantity;

    public TextView customerName;

    public TextView proposedEndDate;
    public TextView endDate;

    public TextView quantityAcquired;
    public ReceivedOrderViewHolder(@NonNull View itemView) {
        super(itemView);
        orderDate = itemView.findViewById(R.id.text_view_order_date);
        orderQuantity = itemView.findViewById(R.id.text_view_ordered_quantity);
        productName = itemView.findViewById(R.id.product_name_text_view);
        customerName = itemView.findViewById(R.id.customer_name_text_view);
        proposedEndDate = itemView.findViewById(R.id.text_view_proposed_end_date);
        endDate = itemView.findViewById(R.id.text_view_end_date);
        quantityAcquired = itemView.findViewById(R.id.text_view_order_acquired);
    }
}
