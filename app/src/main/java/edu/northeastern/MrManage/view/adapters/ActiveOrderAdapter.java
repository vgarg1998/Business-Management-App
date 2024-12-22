package edu.northeastern.MrManage.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Dao;

import java.util.List;

import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.MrManageDatabase;

import edu.northeastern.MrManage.roomApi.dao.ShipmentDao;
import edu.northeastern.MrManage.roomApi.entities.Order;
import edu.northeastern.MrManage.roomApi.entities.ProductCustomer;
import edu.northeastern.MrManage.utility.AppContextHolder;
import edu.northeastern.MrManage.view.dialogs.ViewOrderDialog;
import edu.northeastern.MrManage.view.viewholders.ActiveOrderViewHolder;

public class ActiveOrderAdapter extends RecyclerView.Adapter<ActiveOrderViewHolder>{

    List<Order> orders;

    public ActiveOrderAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public ActiveOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View active_order_item_layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_order_item_layout,parent,false);
        return new ActiveOrderViewHolder(active_order_item_layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveOrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.orderDate.setText(order.getOrderDate());
        holder.orderQuantity.setText(String.valueOf(order.getOrderedQuantity()));
        holder.productName.setText("Loading...");
        holder.customerName.setText("Loading...");
        holder.quantityAcquired.setText(String.valueOf(order.getReceivedQuantity()));
        holder.proposedEndDate.setText(order.getProposedEndDate());
        String[] prod_cust = new String[2];
        int[] numberOfShipments = new int[1];
        double[] quantityReceived = new double[1];

        new Thread(()->{
            ProductCustomer product_customer = MrManageDatabase.getINSTANCE(AppContextHolder.getContext()).productDao().getProductName(order.getProductId());
            Long customer_Id = product_customer.getCustomerId();
            String customer_name = MrManageDatabase.getINSTANCE(AppContextHolder.getContext()).userDao().getCustomerName(customer_Id);

            holder.itemView.post(()->{
               holder.productName.setText(product_customer.getProductName());
               prod_cust[0] = product_customer.getProductName();
               prod_cust[1] = customer_name;
               holder.customerName.setText(customer_name);
            });
            ShipmentDao shipmentDao = MrManageDatabase.getINSTANCE(AppContextHolder.getContext()).shipmentDao();
            numberOfShipments[0] = shipmentDao.getNumberOfShipments(order.getOrderId());
            quantityReceived[0] = shipmentDao.getReceivedQuantity(order.getOrderId());

        }).start();

        holder.itemView.setOnClickListener((v)->{
             new ViewOrderDialog().showDialog(v.getContext(),order,prod_cust[0], prod_cust[1],numberOfShipments[0],quantityReceived[0],position);

        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void updateOrder(int position, Order order){
        orders.set(position,order);
        notifyItemChanged(position);
    }
}
