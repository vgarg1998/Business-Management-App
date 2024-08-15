package edu.northeastern.bhavyaplasticinventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;

import edu.northeastern.bhavyaplasticinventory.helpers.DialogBoxHelper;
import edu.northeastern.bhavyaplasticinventory.threads.AddCustomerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOnClickListener();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnClickListener() {
        CardView customerLayout = findViewById(R.id.card_view_buyer);
        ShapeableImageView addCustomerButton = customerLayout.findViewById(R.id.shapeAble_image_add_purchaser_main);
        setOnTouchAnimation(addCustomerButton);
        addCustomerButton.setOnClickListener(v -> {
           // showAddCustomerDialog();
            View rootView  = getWindow().getDecorView().getRootView();
            DialogBoxHelper.showAddCustomerDialog(this, rootView.getWidth());
        });
    }

    private void setOnTouchAnimation(ShapeableImageView shapeableImageView){
        shapeableImageView.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Set the color or perform any animation when touched
                        shapeableImageView.setColorFilter(R.color.button_order_color, PorterDuff.Mode.SRC_ATOP);
                        break;
                    case MotionEvent.ACTION_UP:
                        // Reset the color or animation when touch released
                        shapeableImageView.clearColorFilter();
                        break;
                }
                return false; // Return true to indicate that the touch event has been consumed
            }
        });
    }
    private void showAddCustomerDialog() {
        Dialog addCustomerDialog = new Dialog(this);
        addCustomerDialog.setContentView(R.layout.add_customer_dialog);
        // Set background to transparent
       addCustomerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Get the width of the activity's root view
        View rootView = getWindow().getDecorView().getRootView();
        int width = rootView.getWidth();

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