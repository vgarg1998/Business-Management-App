package edu.northeastern.MrManage.Executors;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Handler;
import android.os.Looper;

import edu.northeastern.MrManage.roomApi.entities.User;
import edu.northeastern.MrManage.roomApi.view_model.UserViewModel;
import edu.northeastern.MrManage.utility.RoomResponse;
import edu.northeastern.MrManage.utility.interfaces.ValidationListener;

public class AddUserRunnable implements Runnable {
    private final UserViewModel userViewModel;
    private final String[] userDetails;
    private final ValidationListener validationListener;

    public AddUserRunnable(UserViewModel userViewModel, String[] userDetails, ValidationListener validationListener) {
        this.userViewModel = userViewModel;
        this.userDetails = userDetails;
        this.validationListener = validationListener;
    }

    @Override
    public void run() {
        RoomResponse response = validateAndAddUser();
        // Post result to the main thread
        new Handler(Looper.getMainLooper()).post(() -> validationListener.onValidationResult(response));
    }

    private RoomResponse validateAndAddUser() {
        for (String input : userDetails) {
            if (input == null || input.trim().isEmpty()) {
                return new RoomResponse(false, "Make sure no field is empty");
            }
        }

        String name = userDetails[0].toUpperCase();
        String email = userDetails[1].toUpperCase();
        String phone_number = userDetails[2];
        String gst_number = userDetails[3].toUpperCase();
        boolean isCustomer = Boolean.parseBoolean(userDetails[4]);

        User user = new User.UserBuilder()
                .name(name)
                .email_id(email)
                .phone_number(phone_number)
                .gst_number(gst_number)
                .isCustomer(isCustomer)
                .build();

        try {
            userViewModel.insertUser(user);
            return new RoomResponse(true, "Successfully added new customer");
        } catch (SQLiteConstraintException e) {
            return new RoomResponse(false, handleSQLiteConstraintException(e));
        }
    }

    private String handleSQLiteConstraintException(SQLiteConstraintException e) {
        if (e.getMessage() != null) {
            if (e.getMessage().contains("User.email_id")) {
                return "Error: The email ID is already in use. Please use a different email.";
            } else if (e.getMessage().contains("User.phone_number")) {
                return "Error: The phone number is already in use. Please use a different phone number.";
            } else if (e.getMessage().contains("User.name")) {
                return "Error: The name is already in use. Please use a different name.";
            } else if (e.getMessage().contains("User.gst_number")) {
                return "Error: The GST number is already in use. Please use a different GST number.";
            }
        }
        return "Internal Error while adding new customer. Contact Support.";
    }
}
