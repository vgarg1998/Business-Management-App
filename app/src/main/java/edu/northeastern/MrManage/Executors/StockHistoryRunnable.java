package edu.northeastern.MrManage.Executors;

import android.os.Handler;
import android.os.Looper;

import edu.northeastern.MrManage.roomApi.entities.StockHistory;
import edu.northeastern.MrManage.roomApi.view_model.StockHistoryViewModel;
import edu.northeastern.MrManage.utility.RoomResponse;
import edu.northeastern.MrManage.utility.interfaces.ValidationListener;

public class StockHistoryRunnable implements Runnable {
    private final long productId;
    private final String[] stockHistoryDetails;
    private final StockHistoryViewModel stockHistoryViewModel;
    private final ValidationListener validationListener;

    public StockHistoryRunnable(long productId, String[] stockHistoryDetails, StockHistoryViewModel stockHistoryViewModel, ValidationListener validationListener) {
        this.productId = productId;
        this.stockHistoryDetails = stockHistoryDetails;
        this.stockHistoryViewModel = stockHistoryViewModel;
        this.validationListener = validationListener;
    }

    @Override
    public void run() {
        RoomResponse response = validateStockHistoryAndAdd();
        // Post result to the main thread
        new Handler(Looper.getMainLooper()).post(() -> validationListener.onValidationResult(response));
    }

    private RoomResponse validateStockHistoryAndAdd() {
        for (String input : stockHistoryDetails) {
            if (input == null || input.trim().isEmpty()) {
                return new RoomResponse(false, "Make sure no field is empty");
            }
        }

        try {
            double quantity = Double.parseDouble(stockHistoryDetails[0]);
            int numberOfBags = Integer.parseInt(stockHistoryDetails[1]);
            long addedDate = Long.parseLong(stockHistoryDetails[2]);

            if (quantity <= 0 || numberOfBags <= 0 || addedDate <= 0) {
                return new RoomResponse(false, "Invalid input values.");
            }

            StockHistory stockHistory = new StockHistory();
            stockHistory.setProductId(productId);
            stockHistory.setQuantity(quantity);
            stockHistory.setNumberOfBags(numberOfBags);
            stockHistory.setAddedDate(addedDate);

            stockHistoryViewModel.insert(stockHistory);

            return new RoomResponse(true, "Stock history added successfully.");
        } catch (NumberFormatException e) {
            return new RoomResponse(false, "Error parsing input values.");
        }
    }
}