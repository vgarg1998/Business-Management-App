package edu.northeastern.MrManage.view.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.entities.Order;
import edu.northeastern.MrManage.roomApi.view_model.OrderViewModel;
import edu.northeastern.MrManage.roomApi.view_model.UserViewModel;
import edu.northeastern.MrManage.view.activity.ActiveOrderActivity;
import edu.northeastern.MrManage.view.activity.AddShipmentActivity;

public class ViewOrderDialog {

    @SuppressLint("SetTextI18n")
    public void showDialog(Context context, Order order, String productName, String customerName, int numberOfShipment, double receivedQuant, int position, int width) {
        Dialog dialogView = new Dialog(context);
        dialogView.setContentView(R.layout.order_view_layout);
        // Find views inside the dialog layout
        Window window = dialogView.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = width; // Set the width here
            Log.d("SHOW ORDER DETAILS", "THE WIDTH SET IS " + width);
            window.setAttributes(layoutParams);
        }
        TextView orderId = dialogView.findViewById(R.id.order_id);
        TextView date = dialogView.findViewById(R.id.date);
        TextView manufacturer = dialogView.findViewById(R.id.manufacturer);
        TextView product = dialogView.findViewById(R.id.product);
        TextView customer = dialogView.findViewById(R.id.customer);
        TextView givenQuantity = dialogView.findViewById(R.id.given_quantity);
        TextView receivedQuantity = dialogView.findViewById(R.id.received_quantity);
        TextView numShipments = dialogView.findViewById(R.id.num_shipments);

        // Buttons
        Button editOrder = dialogView.findViewById(R.id.edit_order);
        Button deleteOrder = dialogView.findViewById(R.id.delete_order);
        Button addShipment = dialogView.findViewById(R.id.btn_add_shipment);
        Button pushOrder = dialogView.findViewById(R.id.btn_push_order);

        // Populate data
        orderId.setText("Order Id: " + order.getOrderId());
        date.setText("Order Date: " + order.getOrderDate());

        UserViewModel userViewModel = new ViewModelProvider((ActiveOrderActivity) context).get(UserViewModel.class);
        userViewModel.getUserName(order.getManufacturer()).observe((ActiveOrderActivity) context, name -> {
            manufacturer.setText("Manufacturer: " + name);
        });
        product.setText("Product Name: " + productName);
        customer.setText("Customer Name" + customerName);
        givenQuantity.setText("Ordered Quantity (Kg): " + order.getOrderedQuantity());
        receivedQuantity.setText("Received Quantity (Kg): " + receivedQuant);
        numShipments.setText("Shipments Received (in number): " + numberOfShipment);
        dialogView.show();


        editOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order.getReceivedQuantity() > 0 || numberOfShipment > 0) {
                    Toast.makeText(context, "Can not edit order because shipments are there", Toast.LENGTH_SHORT).show();
                } else {
                    //need to do
                }
            }
        });

        deleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order.getReceivedQuantity() > 0 || numberOfShipment > 0) {
                    Toast.makeText(context, "Can not edit order because shipments are there", Toast.LENGTH_SHORT).show();
                } else {
                    OrderViewModel orderViewModel = new ViewModelProvider((ActiveOrderActivity) context).get(OrderViewModel.class);
                    orderViewModel.deleteOrder(order);
                    Toast.makeText(context, "Order deleted successfully", Toast.LENGTH_SHORT).show();
                    dialogView.dismiss();
                }
            }
        });


        addShipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddShipmentActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("productName", productName);
                bundle.putString("customerName", customerName);
                bundle.putInt("numberOfShipments", numberOfShipment);
                bundle.putDouble("receivedQuantity", receivedQuant);
                bundle.putInt("position", position);
                intent.putExtra("order", order);
                intent.putExtras(bundle);
                ((Activity) context).startActivityForResult(intent, 101);
                dialogView.dismiss();
            }
        });

        pushOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderViewModel orderViewModel = new ViewModelProvider((ActiveOrderActivity) context).get(OrderViewModel.class);
                orderViewModel.makeOrderReceived(order.getOrderId(), System.currentTimeMillis());
                dialogView.dismiss();
            }
        });
    }

}
