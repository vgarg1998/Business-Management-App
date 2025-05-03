package edu.northeastern.MrManage.Executors;

import android.os.Handler;
import android.os.Looper;

import edu.northeastern.MrManage.roomApi.entities.Delivery;
import edu.northeastern.MrManage.roomApi.entities.Product;
import edu.northeastern.MrManage.roomApi.view_model.DeliveryViewModel;
import edu.northeastern.MrManage.utility.RoomResponse;
import edu.northeastern.MrManage.utility.interfaces.ValidationListener;

public class AddDeliveryRunnable implements Runnable {

    private final Product product;
    private final String[] deliveryDetails;
    private final DeliveryViewModel deliveryViewModel;
    private final ValidationListener validationListener;

    public AddDeliveryRunnable(Product product, String[] deliveryDetails, DeliveryViewModel deliveryViewModel, ValidationListener validationListener) {
        this.product = product;
        this.deliveryDetails = deliveryDetails;
        this.deliveryViewModel = deliveryViewModel;
        this.validationListener = validationListener;
    }

    @Override
    public void run() {
        RoomResponse roomResponse = validateDeliveryAndAdd();
        new Handler(Looper.getMainLooper()).post(() -> validationListener.onValidationResult(roomResponse));
    }

    private RoomResponse validateDeliveryAndAdd() {
        try {
            // Parse input data
            String quantityStr = deliveryDetails[0];
            String numberOfBagsDeliveredStr = deliveryDetails[1];
            String transitCostStr = deliveryDetails[2];
            long deliveryDate = Long.parseLong(deliveryDetails[3]);

            if (quantityStr.isEmpty() || numberOfBagsDeliveredStr.isEmpty() || transitCostStr.isEmpty()) {
                return new RoomResponse(false, "Input fields cannot be empty");
            }

            // Convert inputs to appropriate types
            double quantity = Double.parseDouble(quantityStr);
            int numberOfBagsDelivered = Integer.parseInt(numberOfBagsDeliveredStr);
            double transitCost = Double.parseDouble(transitCostStr);

            if (quantity <= 0 || numberOfBagsDelivered <= 0 || transitCost < 0) {
                return new RoomResponse(false, "Invalid values: Quantity, Number of Bags, and Transit Cost must be positive.");
            }
            // Check to make sure the delivery quanity of bags and quantity of product is less or equal to in stock
            int numberOfBagsInStock;

            if (numberOfBagsDelivered > product.getNumberOfBagsInStock()) {
                return new RoomResponse(false, "Number of bags delivered cannot be greater than the number of bags in stock.");
            }
            if (quantity > product.getQuantityInInventory()) {
                return new RoomResponse(false, "Quantity delivered cannot be greater than the quantity in stock.");
            }
            // Create Delivery object
            Delivery delivery = new Delivery(product.getProductId(), quantity, numberOfBagsDelivered, transitCost, deliveryDate);

            // Insert delivery into the database
            deliveryViewModel.insertDelivery(delivery);

            return new RoomResponse(true, "Delivery added successfully.");
        } catch (NumberFormatException e) {
            return new RoomResponse(false, "Invalid number format: " + e.getMessage());
        } catch (Exception e) {
            return new RoomResponse(false, "An error occurred: " + e.getMessage());
        }
    }
}
