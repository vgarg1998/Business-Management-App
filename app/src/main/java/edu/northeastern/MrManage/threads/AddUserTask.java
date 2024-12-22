package edu.northeastern.MrManage.threads;

import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import edu.northeastern.MrManage.roomApi.MrManageDatabase;
import edu.northeastern.MrManage.roomApi.dao.UserDao;
import edu.northeastern.MrManage.roomApi.entities.User;
import edu.northeastern.MrManage.utility.AppContextHolder;
import edu.northeastern.MrManage.utility.RoomResponse;
import edu.northeastern.MrManage.utility.interfaces.ValidationListener;

public class AddUserTask extends AsyncTask<String, Void, RoomResponse> {

    private ValidationListener validationListener;

    public AddUserTask(ValidationListener listener) {
        this.validationListener = listener;
    }

    @Override
    protected RoomResponse doInBackground(String... strings) {
        for (String input : strings) {
            if (input == null || input.trim().isEmpty()) {
                return new RoomResponse(false, "Make sure no field is empty");
            }
        }
        String name = strings[0].toUpperCase();
        String email = strings[1].toUpperCase();
        String phone_number = strings[2];
        String gst_number = strings[3].toUpperCase();
        boolean isCustomer = Boolean.parseBoolean(strings[4]);
        User user = new User.UserBuilder().name(name).email_id(email).phone_number(phone_number).gst_number(gst_number).isCustomer(isCustomer).build();
        MrManageDatabase mrManageDatabase = MrManageDatabase.getINSTANCE(AppContextHolder.getContext());
        UserDao userDao = mrManageDatabase.userDao();
        String[] message = {""};
        boolean[] foundIssue = {false};
        try {
            userDao.insertUser(user); // Attempt to insert
        } catch (SQLiteConstraintException e) {
            if (e.getMessage() != null && e.getMessage().contains("UNIQUE constraint failed")) {
                String messageToUser = "";
                foundIssue[0] = true;
                if (e.getMessage().contains("User.email_id")) {
                    System.out.println("Error: The email ID is already in use. Please use a different email.");
                    messageToUser = "Error: The email ID is already in use. Please use a different email";
                } else if (e.getMessage().contains("User.phone_number")) {
                    System.out.println("Error: The phone number is already in use. Please use a different phone number.");
                    messageToUser = "Error: The phone number is already in use. Please use a different phone number.";
                } else if (e.getMessage().contains("User.name")) {
                    System.out.println("Error: The name is already in use. Please use a different name.");
                    messageToUser = "Error: The name is already in use. Please use a different name.";
                } else if (e.getMessage().contains("User.gst_number")) {
                    System.out.println("Error: The gst_number is already in use. Please use a different gst_number.");
                    messageToUser = "Error: The gst_number is already in use. Please use a different gst_number.";
                } else {
                    // Handle other types of SQLite constraint exceptions if necessary
                    System.out.println("Internal Error while adding new customer, Contact Support");
                    messageToUser = "Internal Error while adding new customer, Contact Support";
                    e.printStackTrace(); // Log the exception
                }
                message[0] = messageToUser;
            }
        }
        if (foundIssue[0]) {
            return new RoomResponse(false, message[0]);
        }
        return new RoomResponse(true, "Successfully added new customer");
    }

    @Override
    protected void onPostExecute(RoomResponse roomResponse) {
        super.onPostExecute(roomResponse);
        // Invoke the onValidationResult method of the ValidationListener
        if (validationListener != null) {
            validationListener.onValidationResult(roomResponse);
        }
    }

}
