package edu.northeastern.MrManage.Executors;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Handler;
import android.os.Looper;

import edu.northeastern.MrManage.roomApi.entities.Order;
import edu.northeastern.MrManage.roomApi.view_model.OrderViewModel;
import edu.northeastern.MrManage.roomApi.view_model.ProductViewModel;
import edu.northeastern.MrManage.utility.DateTimeUtils;
import edu.northeastern.MrManage.utility.RoomResponse;
import edu.northeastern.MrManage.utility.interfaces.ValidationListener;

public class AddOrderRunnable implements Runnable {

    private String[] orderDetails;
    private ValidationListener validationListener;
    private OrderViewModel orderViewModel;
    private ProductViewModel productViewModel;

    public AddOrderRunnable(OrderViewModel orderViewModel, ProductViewModel productViewModel, String[] orderDetails, ValidationListener validationListener) {
        this.orderDetails = orderDetails;
        this.validationListener = validationListener;
        this.orderViewModel = orderViewModel;
        this.productViewModel = productViewModel;
    }

    @Override
    public void run() {
        RoomResponse roomResponse = validateAndAddOrder(orderDetails);
        new Handler(Looper.getMainLooper()).post(() -> validationListener.onValidationResult(roomResponse));
    }

    private RoomResponse validateAndAddOrder(String[] orderDetails) {
        // Validate inputs
        for (String input : orderDetails) {
            if (input == null || input.trim().isEmpty()) {
                return new RoomResponse(false, "Make sure no field is empty");
            }
        }

        String productId = orderDetails[0];
        String manufactureId = orderDetails[1];
        String quantity = orderDetails[2];
        String proposedEndDate = orderDetails[3];

        if (!quantity.matches("-?\\d+(\\.\\d+)?")) {
            return new RoomResponse(false, "Only numeric values are allowed for quantity");
        }

        try {
            // Create and insert Order object
            Order order = new Order(
                    Long.parseLong(productId),
                    Long.parseLong(manufactureId),
                    DateTimeUtils.getCurrentDateTime(),
                    Double.parseDouble(quantity),
                    proposedEndDate
            );
            orderViewModel.addOrder(order);
            productViewModel.updateOrderedQuantity(productId, quantity);
            return new RoomResponse(true, "Successfully added new order");
        } catch (SQLiteConstraintException e) {
            return new RoomResponse(false, handleSQLiteConstraintException(e));
        } catch (Exception e) {
            return new RoomResponse(false, "Internal Error: " + e.getMessage());
        }
    }

    private String handleSQLiteConstraintException(SQLiteConstraintException e) {
        if (e.getMessage() != null && e.getMessage().contains("UNIQUE constraint failed")) {
            if (e.getMessage().contains("Order.product_id")) {
                return "Error: The product ID already exists. Please use a different product ID.";
            }
        }
        return "Internal Error while adding new order. Contact Support.";
    }
}
