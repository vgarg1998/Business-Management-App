package edu.northeastern.MrManage.view.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.entities.Order;
import edu.northeastern.MrManage.view.activity.AddShipmentActivity;

public class ViewOrderDialog {

    @SuppressLint("SetTextI18n")
    public void showDialog(Context context, Order order, String productName, String customerName, int numberOfShipment, double receivedQuant, int position) {
        Dialog dialogView = new Dialog(context);
        dialogView.setContentView(R.layout.order_view_layout);
        // Find views inside the dialog layout
        TextView orderId = dialogView.findViewById(R.id.order_id);
        TextView date = dialogView.findViewById(R.id.date);
        TextView manufacturer = dialogView.findViewById(R.id.manufacturer);
        TextView product = dialogView.findViewById(R.id.product);
        TextView customer = dialogView.findViewById(R.id.customer);
        TextView givenQuantity = dialogView.findViewById(R.id.given_quantity);
        TextView receivedQuantity = dialogView.findViewById(R.id.received_quantity);
        TextView numShipments = dialogView.findViewById(R.id.num_shipments);

        // Buttons
        Button addShipment = dialogView.findViewById(R.id.btn_add_shipment);
        Button pushOrder = dialogView.findViewById(R.id.btn_push_order);

        // Populate data
        orderId.setText(String.valueOf(order.getOrderId()));
        date.setText(order.getOrderDate());
        manufacturer.setText("Manufacturer: XYZ Corp");
        product.setText(productName);
        customer.setText(customerName);
        givenQuantity.setText("Ordered Quantity: " + order.getOrderedQuantity());
        receivedQuantity.setText("Received Quantity: " + receivedQuant);
        numShipments.setText("Shipments Received: " + numberOfShipment);
        dialogView.show();


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
    }

}
