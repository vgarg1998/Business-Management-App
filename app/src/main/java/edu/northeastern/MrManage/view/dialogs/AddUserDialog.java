package edu.northeastern.MrManage.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.northeastern.MrManage.Executors.AddUserRunnable;
import edu.northeastern.MrManage.MainActivity;
import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.view_model.UserViewModel;
import edu.northeastern.MrManage.utility.RoomResponse;
import edu.northeastern.MrManage.utility.interfaces.ValidationListener;

public class AddUserDialog {
    public static void showAddUserDialog(Context context, int width, boolean isCustomer) {
        Dialog addUserDialog = new Dialog(context);
        addUserDialog.setContentView(R.layout.add_user_layout);
        TextView userTypeBar = addUserDialog.findViewById(R.id.textView_user_type_bar);
        if (isCustomer) {
            userTypeBar.setText("ADD CUSTOMER");
        } else {
            userTypeBar.setText("ADD MANUFACTURER");
        }
        Window window = addUserDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = width; // Set the width here
            window.setAttributes(layoutParams);
        }
        TextInputEditText nameEditText = addUserDialog.findViewById(R.id.user_name_input);
        TextInputEditText emailEditText = addUserDialog.findViewById(R.id.user_email_input);
        TextInputEditText phoneNumberEditText = addUserDialog.findViewById(R.id.user_phone_number_input);
        TextInputEditText gstNumberEditText = addUserDialog.findViewById(R.id.gst_number_input);
        Button submit = addUserDialog.findViewById(R.id.add_user_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String phoneNumber = phoneNumberEditText.getText().toString();
                String gstNumber = gstNumberEditText.getText().toString();
                String email = emailEditText.getText().toString();

                ExecutorService executorService = Executors.newSingleThreadExecutor();
                String[] userDetails = {name, email, phoneNumber, gstNumber, String.valueOf(isCustomer)};

                executorService.submit(new AddUserRunnable(new ViewModelProvider((MainActivity) context).get(UserViewModel.class), userDetails, new ValidationListener() {
                    @Override
                    public void onValidationResult(RoomResponse roomResponse) {
                        // Handle result on UI thread
                        if (roomResponse.getIsValid()) {
                            Toast.makeText(context, roomResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            addUserDialog.dismiss();
                        } else {
                            Toast.makeText(context, roomResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }));

// Shutdown executor when no longer needed
                executorService.shutdown();
            }
        });
        addUserDialog.show();
    }


}
