package edu.northeastern.MrManage.Executors;

import android.os.Handler;
import android.os.Looper;

import edu.northeastern.MrManage.roomApi.entities.Delivery;
import edu.northeastern.MrManage.roomApi.view_model.DeliveryViewModel;
import edu.northeastern.MrManage.utility.RoomResponse;
import edu.northeastern.MrManage.utility.interfaces.ValidationListener;

public class AddDeliveryRunnable implements Runnable {

    private final Long productId;
    private final String[] deliveryDetails;
    private final DeliveryViewModel deliveryViewModel;
    private final ValidationListener validationListener;

    public AddDeliveryRunnable(Long productId, String[] deliveryDetails, DeliveryViewModel deliveryViewModel, ValidationListener validationListener) {
        this.productId = productId;
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

            // Create Delivery object
            Delivery delivery = new Delivery(productId, quantity, numberOfBagsDelivered, transitCost, deliveryDate);

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
