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
import edu.northeastern.MrManage.roomApi.view_model.UserViewModel;
import edu.northeastern.MrManage.utility.DateTimeUtils;
import edu.northeastern.MrManage.view.activity.ReceivedOrderActivity;
import edu.northeastern.MrManage.view.viewholders.ReceivedOrderViewHolder;

public class ReceivedOrderAdapter extends RecyclerView.Adapter<ReceivedOrderViewHolder> {

    List<Order> orderList;

    Context context;

    public ReceivedOrderAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReceivedOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View receivedOrderViewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.received_order_item_layout, parent, false);
        return new ReceivedOrderViewHolder(receivedOrderViewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivedOrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.orderDate.setText(DateTimeUtils.formatTime(order.getOrderDate()));
        holder.orderQuantity.setText(String.valueOf(order.getOrderedQuantity()));
        holder.quantityAcquired.setText(String.valueOf(order.getReceivedQuantity()));
        holder.proposedEndDate.setText(DateTimeUtils.formatTime(order.getProposedEndDate()));
        holder.endDate.setText(DateTimeUtils.formatTime(order.getEndDate()));

        ProductViewModel productViewModel = new ViewModelProvider((ReceivedOrderActivity) context).get(ProductViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider((ReceivedOrderActivity) context).get(UserViewModel.class);
        productViewModel.getProductNameAndCustomerId(order.getProductId()).observe((ReceivedOrderActivity) context, productCustomerId -> {
            Long customer_Id = productCustomerId.getCustomerId();
            String productName = productCustomerId.getProductName();
            holder.productName.setText(productName);
            userViewModel.getUserName(customer_Id).observe((ReceivedOrderActivity) context, customerName -> {
                holder.customerName.setText(customerName);
            });
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void updateOrders(List<Order> receivedOrders) {
        orderList.clear();
        orderList.addAll(receivedOrders);
        notifyDataSetChanged();
    }
}
