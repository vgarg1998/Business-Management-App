package edu.northeastern.MrManage.view.viewholders;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.MrManage.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public TextView productName;

    public TextView orderedQuantity;

    public TextView inStockQuantity;

    public TextView deliveredQuantity;

    public TextView inStockBags;

    public TextView totalDeliveredBags;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.product_name_text_view);

        orderedQuantity = itemView.findViewById(R.id.text_view_ordered_quantity);
        inStockQuantity = itemView.findViewById(R.id.in_stock_quantity_text_view);
        deliveredQuantity = itemView.findViewById(R.id.text_view_delivered_quantity);
        inStockBags = itemView.findViewById(R.id.in_stock_bags);
        totalDeliveredBags = itemView.findViewById(R.id.text_view_total_delivered_bags);
    }
}
