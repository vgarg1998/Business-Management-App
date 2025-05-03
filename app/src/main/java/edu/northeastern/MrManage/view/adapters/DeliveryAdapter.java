package edu.northeastern.MrManage.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.entities.Delivery;
import edu.northeastern.MrManage.roomApi.view_model.DeliveryViewModel;
import edu.northeastern.MrManage.roomApi.view_model.ProductViewModel;
import edu.northeastern.MrManage.roomApi.view_model.UserViewModel;
import edu.northeastern.MrManage.utility.DateTimeUtils;
import edu.northeastern.MrManage.view.activity.DeliveryHistoryActivity;
import edu.northeastern.MrManage.view.viewholders.DeliveryViewHolder;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryViewHolder> {

    private List<Delivery> deliveryList;
    Context context;

    // Constructor to accept the list of deliveries
    public DeliveryAdapter(List<Delivery> deliveryList, Context context) {
        this.deliveryList = deliveryList;
        this.context = context;
    }

    @NonNull
    @Override
    public DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.delivery_item_layout, parent, false);
        return new DeliveryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryViewHolder holder, int position) {
        // Get the current delivery object
        Delivery delivery = deliveryList.get(position);
        // Bind the data to the views
        holder.productName.setText("");
        holder.customerName.setText("");
        holder.deliveryDate.setText(DateTimeUtils.formatTime(delivery.getDate()));
        holder.quantity.setText(String.format("%.2f kg", delivery.getTotalQuantityKg()));
        holder.numberOfBags.setText(String.valueOf(delivery.getNumberOfBags()));
        holder.transitCost.setText(String.valueOf(delivery.getTransitCost()));
        ProductViewModel productViewModel = new ViewModelProvider((DeliveryHistoryActivity) context).get(ProductViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider((DeliveryHistoryActivity) context).get(UserViewModel.class);
        productViewModel.getProductNameAndCustomerId(delivery.getProductId()).observe((DeliveryHistoryActivity) context, productCustomerId -> {
            Long customer_Id = productCustomerId.getCustomerId();
            String productName = productCustomerId.getProductName();
            holder.productName.setText(productName);
            userViewModel.getUserName(customer_Id).observe((DeliveryHistoryActivity) context, customerName -> {
                holder.customerName.setText(customerName);
            });
        });

        holder.buttonDelete.setOnClickListener(v -> {
            // Show a confirmation dialog
            new AlertDialog.Builder(context)
                    .setTitle("Delete Delivery")
                    .setMessage("Are you sure you want to delete this delivery?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Delete the delivery
                        DeliveryViewModel deliveryViewModel = new ViewModelProvider((DeliveryHistoryActivity) context).get(DeliveryViewModel.class);
                        deliveryViewModel.delete(delivery);
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return deliveryList.size();
    }

    public void updateDelivery(List<Delivery> deliveries) {
        deliveryList.clear();
        deliveryList.addAll(deliveries);
        notifyDataSetChanged();
    }
}
