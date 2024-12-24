package edu.northeastern.MrManage.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.entities.Order;
import edu.northeastern.MrManage.roomApi.view_model.ProductViewModel;
import edu.northeastern.MrManage.roomApi.view_model.ShipmentViewModel;
import edu.northeastern.MrManage.roomApi.view_model.UserViewModel;
import edu.northeastern.MrManage.view.activity.ActiveOrderActivity;
import edu.northeastern.MrManage.view.dialogs.ViewOrderDialog;
import edu.northeastern.MrManage.view.viewholders.ActiveOrderViewHolder;

public class ActiveOrderAdapter extends RecyclerView.Adapter<ActiveOrderViewHolder> {
    List<Order> orders;
    Context context;


    public ActiveOrderAdapter(List<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public ActiveOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View active_order_item_layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_order_item_layout, parent, false);
        return new ActiveOrderViewHolder(active_order_item_layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveOrderViewHolder holder, int position) {
        Order order = orders.get(position);

        // Set basic Order details
        holder.orderDate.setText(order.getOrderDate());
        holder.orderQuantity.setText(String.valueOf(order.getOrderedQuantity()));
        holder.quantityAcquired.setText(String.valueOf(order.getReceivedQuantity()));
        holder.proposedEndDate.setText(order.getProposedEndDate());

        ProductViewModel productViewModel = new ViewModelProvider((ActiveOrderActivity) context).get(ProductViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider((ActiveOrderActivity) context).get(UserViewModel.class);
        ShipmentViewModel shipmentViewModel = new ViewModelProvider((ActiveOrderActivity) context).get(ShipmentViewModel.class);
        productViewModel.getProductNameAndCustomerId(order.getProductId()).observe((ActiveOrderActivity) context, productCustomerId -> {
            Long customer_Id = productCustomerId.getCustomerId();
            String productName = productCustomerId.getProductName();
            holder.productName.setText(productName);
            userViewModel.getUserName(customer_Id).observe((ActiveOrderActivity) context, customerName -> {
                holder.customerName.setText(customerName);
                shipmentViewModel.getNumberOfShipments(order.getOrderId())
                        .observe((ActiveOrderActivity) context, numberOfShipmentsReturned -> {
                            holder.itemView.setOnClickListener((v) -> {
                                new ViewOrderDialog().showDialog(v.getContext(), order, productName, customerName, numberOfShipmentsReturned, order.getReceivedQuantity(), position);

                            });
                        });
            });
        });
    }


    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void updateOrder(int position, Order order) {
        orders.set(position, order);
        notifyItemChanged(position);
    }

    public void updateOrders(List<Order> updatedOrders) {
        this.orders = updatedOrders; // Replace old data with new data
        notifyDataSetChanged(); // Notify adapter to refresh the view
    }

}
