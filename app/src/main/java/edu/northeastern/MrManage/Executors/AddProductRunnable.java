package edu.northeastern.MrManage.Executors;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Handler;
import android.os.Looper;

import edu.northeastern.MrManage.roomApi.entities.Product;
import edu.northeastern.MrManage.roomApi.view_model.ProductViewModel;
import edu.northeastern.MrManage.utility.RoomResponse;
import edu.northeastern.MrManage.utility.interfaces.ValidationListener;
import edu.northeastern.MrManage.view.adapters.ProductViewAdapter;

public class AddProductRunnable implements Runnable {

    private String[] productDetails;

    private ValidationListener validationListener;
    private ProductViewAdapter productViewAdapter;

    private ProductViewModel productViewModel;


    public AddProductRunnable(ProductViewModel productViewModel, String[] productDetails, ProductViewAdapter productViewAdapter, ValidationListener validationListener) {
        this.productDetails = productDetails;
        this.validationListener = validationListener;
        this.productViewAdapter = productViewAdapter;
        this.productViewModel = productViewModel;
    }

    @Override
    public void run() {
        RoomResponse roomResponse = validateAndAddProduct(productDetails, validationListener);
        new Handler(Looper.getMainLooper()).post(() -> validationListener.onValidationResult(roomResponse));
    }

    private RoomResponse validateAndAddProduct(String[] productDetails, ValidationListener validationListener) {
        for (String input : productDetails) {
            if (input == null || input.trim().isEmpty()) {
                return new RoomResponse(false, "Make sure no field is empty");
            }
        }

        String customerId = productDetails[0];
        String productName = productDetails[1];
        String productType = productDetails[2];
        Product product = new Product(productName, productType, Long.parseLong(customerId));

        try {
            productViewModel.insertProduct(product);
            return new RoomResponse(true, "Successfully added new customer");
        } catch (SQLiteConstraintException e) {
            return new RoomResponse(false, handleSQLiteConstraintException(e));
        }

    }

    private String handleSQLiteConstraintException(SQLiteConstraintException e) {
        if (e.getMessage() != null && e.getMessage().contains("UNIQUE constraint failed")) {
            if (e.getMessage().contains("Product.product_name")) {
                return "Error: The product name is already in use for customer. Please use a different product name.";

            }
        }
        return "Internal Error while adding new customer. Contact Support.";
    }
}
