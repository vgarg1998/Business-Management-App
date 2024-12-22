package edu.northeastern.MrManage.threads;

import android.os.AsyncTask;
import edu.northeastern.MrManage.roomApi.MrManageDatabase;
import edu.northeastern.MrManage.roomApi.entities.Order;
import edu.northeastern.MrManage.utility.AppContextHolder;
import edu.northeastern.MrManage.utility.DateTimeUtils;
import edu.northeastern.MrManage.utility.RoomResponse;
import edu.northeastern.MrManage.utility.interfaces.ValidationListener;

public class AddOrderTask extends AsyncTask<String,Void, RoomResponse> {
    private Order order;
    ValidationListener validationListener;

    public AddOrderTask(ValidationListener validationListener) {
        this.validationListener = validationListener;
    }

    @Override
    protected RoomResponse doInBackground(String... strings) {
        String productId = strings[0];
        String manufactureId = strings[1];
        String quantity = strings[2];
        String proposedEndDate = strings[3];
        if(quantity == null || quantity.isEmpty()){
            return new RoomResponse(false,"Quality is required");
        }
        String[] message = {""};
        boolean[] foundIssue = {false};
        if(quantity.matches("-?\\d+(\\.\\d+)?")){
            new Thread(()->{
                //using dummy manfacturer value
                order = new Order(Long.parseLong(productId),Long.parseLong(manufactureId), DateTimeUtils.getCurrentDateTime(),Double.parseDouble(quantity), proposedEndDate);
                try{
                    MrManageDatabase.getINSTANCE(AppContextHolder.getContext()).orderDao().insertOrder(order);
                    MrManageDatabase.getINSTANCE(AppContextHolder.getContext()).productDao().updateOrderedQuantity(productId,quantity);
                }catch(Exception ex){
                    foundIssue[0] = false;
                    message[0] = ex.getMessage();
                }
            }).start();

        }else{
            return new RoomResponse(false, "Only Numbers Allowed");
        }
        if (foundIssue[0]) {
            return new RoomResponse(false, message[0]);
        }
        return new RoomResponse(true, "Successfully added new order");
    }

    @Override
    protected void onPostExecute(RoomResponse roomResponse) {
        super.onPostExecute(roomResponse);
        if (validationListener != null) {
            validationListener.onValidationResult(roomResponse);
        }
    }
}
