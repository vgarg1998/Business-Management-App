package edu.northeastern.MrManage.Executors;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import edu.northeastern.MrManage.roomApi.entities.Shipment;
import edu.northeastern.MrManage.roomApi.view_model.OrderViewModel;
import edu.northeastern.MrManage.roomApi.view_model.ProductViewModel;
import edu.northeastern.MrManage.roomApi.view_model.ShipmentViewModel;
import edu.northeastern.MrManage.utility.RoomResponse;
import edu.northeastern.MrManage.utility.interfaces.ValidationListener;

public class AddShipmentRunnable implements Runnable {

    Long orderId;
    Long productId;
    int shipmentType;
    String[] shipmentDetails;
    ShipmentViewModel shipmentViewModel;
    OrderViewModel orderViewModel;

    ProductViewModel productViewModel;

    ValidationListener validationListener;

    public AddShipmentRunnable(Long orderId,Long productId, int shipmentType,String[] shipment, ShipmentViewModel shipmentViewModel,
                               OrderViewModel orderViewModel, ProductViewModel productViewModel
            ,ValidationListener validationListener) {
        this.orderId = orderId;
        this.productId = productId;
        this.shipmentType = shipmentType;
        this.shipmentDetails = shipment;
        this.shipmentViewModel = shipmentViewModel;
        this.orderViewModel = orderViewModel;
        this.productViewModel = productViewModel;
        this.validationListener = validationListener;
    }

    @Override
    public void run() {
        RoomResponse roomResponse = validateAndAddShipment();
        new Handler(Looper.getMainLooper()).post(() -> validationListener.onValidationResult(roomResponse));
    }

    private RoomResponse validateAndAddShipment() {
        // Validate input
        RoomResponse validationResponse = validateInput();
        if (!validationResponse.getIsValid()) {
            return validationResponse;
        }

        // Parse validated input
        int numberOfBags = Integer.parseInt(shipmentDetails[0]);
        double totalWeight = Double.parseDouble(shipmentDetails[1]);
        int transitCost = Integer.parseInt(shipmentDetails[2]);

        try {
            Shipment shipment = new Shipment(orderId, numberOfBags, totalWeight, shipmentType, transitCost);
            shipmentViewModel.insert(shipment);
            orderViewModel.updateReceivedQuantity(orderId, totalWeight);

            if (shipmentType == 0) {
                productViewModel.updateNumberOfBagsInStock(productId, numberOfBags);
                productViewModel.updateQuantityInStock(productId, totalWeight);
            } else {
                productViewModel.updateNumberOfBagsDelivered(productId, numberOfBags);
                productViewModel.updateQuantityDelivered(productId, totalWeight);
            }
        } catch (Exception e) {
            Log.d("Shipment Add Failed", e.getMessage());
            return new RoomResponse(false, "An unexpected error occurred while adding shipment");
        }

        return new RoomResponse(true, "Shipment added successfully");
    }

    private RoomResponse validateInput() {
        // Validate empty input
        if (shipmentDetails[0].isEmpty() || shipmentDetails[1].isEmpty() || shipmentDetails[2].isEmpty()) {
            return new RoomResponse(false, "Bags, Quantity, and Transit Cost cannot be empty");
        }

        // Validate number of bags
        try {
            int numberOfBags = Integer.parseInt(shipmentDetails[0]);
            if (numberOfBags <= 0) {
                return new RoomResponse(false, "Number of bags must be greater than 0");
            }
        } catch (NumberFormatException e) {
            return new RoomResponse(false, "Number of bags must be a valid integer");
        }

        // Validate total weight
        try {
            double totalWeight = Double.parseDouble(shipmentDetails[1]);
            if (totalWeight <= 0) {
                return new RoomResponse(false, "Total weight must be greater than 0");
            }
        } catch (NumberFormatException e) {
            return new RoomResponse(false, "Total weight must be a valid number");
        }

        // Validate transit cost
        try {
            int transitCost = Integer.parseInt(shipmentDetails[2]);
            if (transitCost < 0) {
                return new RoomResponse(false, "Transit cost cannot be negative");
            }
        } catch (NumberFormatException e) {
            return new RoomResponse(false, "Transit cost must be a valid integer");
        }

        return new RoomResponse(true, "Validation successful");
    }
}
