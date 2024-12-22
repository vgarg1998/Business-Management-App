package edu.northeastern.MrManage.threads;

import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import edu.northeastern.MrManage.roomApi.MrManageDatabase;
import edu.northeastern.MrManage.roomApi.dao.ProductDao;

import edu.northeastern.MrManage.roomApi.entities.Product;
import edu.northeastern.MrManage.utility.AppContextHolder;
import edu.northeastern.MrManage.utility.RoomResponse;
import edu.northeastern.MrManage.utility.interfaces.ValidationListener;
import edu.northeastern.MrManage.view.adapters.ProductViewAdapter;

public class AddProductTask extends AsyncTask<String,Void,RoomResponse> {
    private Product product;

    private ValidationListener validationListener;
    private ProductViewAdapter productViewAdapter;

    public AddProductTask(ValidationListener validationListener, ProductViewAdapter productViewAdapter) {
        this.validationListener = validationListener;
        this.productViewAdapter = productViewAdapter;
    }

    @Override
    protected RoomResponse doInBackground(String... strings) {
        String customerId = strings[0];
        String productName = strings[1];
        String productType = strings[2];
        if(productName == null || productName.isEmpty()){
            return new RoomResponse(false,"Product name can not be null");
        }
        product = new Product(productName,productType,Long.parseLong(customerId));
        String[] message = {""};
        boolean[] foundIssue = {false};
        MrManageDatabase mrManageDatabase = MrManageDatabase.getINSTANCE(AppContextHolder.getContext());
        ProductDao productDao = mrManageDatabase.productDao();
        try{
            productDao.insertProduct(product);

            //need adapter here

        }catch(SQLiteConstraintException e){
            if (e.getMessage() != null && e.getMessage().contains("UNIQUE constraint failed")) {
                String messageToUser = "";
                foundIssue[0] = true;
                if (e.getMessage().contains("Product.product_name")) {
                    System.out.println("Error: The product name is already in use for customer. Please use a different product name.");
                    messageToUser = "Error: The product name is already in use for customer. Please use a different product name.";
                }else {
                    // Handle other types of SQLite constraint exceptions if necessary
                    System.out.println("Internal Error while adding new product, Contact Support");
                    messageToUser = "Internal Error while adding new product, Contact Support";
                    e.printStackTrace(); // Log the exception
                }
                message[0] = messageToUser;
            }
        }
        if (foundIssue[0]) {
            return new RoomResponse(false, message[0]);
        }
        return new RoomResponse(true, "Successfully added new product");
    }

    @Override
    protected void onPostExecute(RoomResponse roomResponse) {
        super.onPostExecute(roomResponse);
        if (validationListener != null) {
            validationListener.onValidationResult(roomResponse);
        }
        if(roomResponse.getIsValid())
            productViewAdapter.addProduct(product);
    }
}
