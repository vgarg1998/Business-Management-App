package edu.northeastern.MrManage.view.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.MrManage.R;

public class DeliveryViewHolder extends RecyclerView.ViewHolder {
    public TextView customerName, productName, quantity, numberOfBags,deliveryDate, transitCost;
    public Button buttonDelete;

    public DeliveryViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.product_name);
            customerName = itemView.findViewById(R.id.customer_name);
            deliveryDate = itemView.findViewById(R.id.delivery_date);
            quantity = itemView.findViewById(R.id.quantity);
            numberOfBags = itemView.findViewById(R.id.number_of_bags);
            transitCost = itemView.findViewById(R.id.transit_cost);

            buttonDelete = itemView.findViewById(R.id.delete_button);
    }
}