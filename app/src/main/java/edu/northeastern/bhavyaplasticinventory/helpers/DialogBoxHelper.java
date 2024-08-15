package edu.northeastern.bhavyaplasticinventory.helpers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import edu.northeastern.bhavyaplasticinventory.R;
import edu.northeastern.bhavyaplasticinventory.threads.AddCustomerTask;

public class DialogBoxHelper {

    public static void showAddCustomerDialog(Context context, int width) {
        Dialog addCustomerDialog = new Dialog(context);
        addCustomerDialog.setContentView(R.layout.add_customer_dialog);
        // Set background to transparent
        addCustomerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Set the width of the dialog to match the width of the activity
        Window window = addCustomerDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = width; // Set the width here
            window.setAttributes(layoutParams);
        }
        TextInputEditText nameEditText = addCustomerDialog.findViewById(R.id.customer_name_input);
        TextInputEditText emailEditText = addCustomerDialog.findViewById(R.id.customer_email_input);
        TextInputEditText phoneNumberEditText = addCustomerDialog.findViewById(R.id.customer_phone_number_input);
        TextInputEditText gstNumberEditText = addCustomerDialog.findViewById(R.id.gst_number_input);
        Button submit = addCustomerDialog.findViewById(R.id.add_customer_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String phoneNumber = phoneNumberEditText.getText().toString();
                String gstNumber = gstNumberEditText.getText().toString();
                String email = emailEditText.getText().toString();
                AddCustomerTask validationTask = new AddCustomerTask(new AddCustomerTask.ValidationListener() {
                    @Override
                    public void onValidationResult(boolean isValid) {
                        if(isValid){
                            addCustomerDialog.dismiss();
                        }else{
                            Toast.makeText(addCustomerDialog.getContext(), "One or more inputs are invalid", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                validationTask.execute(name, email, phoneNumber, gstNumber);
            }
        });
        addCustomerDialog.show();
    }
}
