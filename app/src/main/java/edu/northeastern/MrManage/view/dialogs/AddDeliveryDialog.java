package edu.northeastern.MrManage.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.northeastern.MrManage.Executors.AddDeliveryRunnable;
import edu.northeastern.MrManage.MainActivity;
import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.entities.Product;
import edu.northeastern.MrManage.roomApi.entities.User;
import edu.northeastern.MrManage.roomApi.view_model.DeliveryViewModel;
import edu.northeastern.MrManage.roomApi.view_model.ProductViewModel;
import edu.northeastern.MrManage.roomApi.view_model.UserViewModel;
import edu.northeastern.MrManage.utility.DateTimeUtils;
import edu.northeastern.MrManage.utility.RoomResponse;
import edu.northeastern.MrManage.utility.interfaces.ValidationListener;

public class AddDeliveryDialog {
    final Context context;
    static UserViewModel userViewModel;
    static ProductViewModel productViewModel;

    static long selectedDate = 0;

    public AddDeliveryDialog(Context context) {
        this.context = context;
        userViewModel = new ViewModelProvider((MainActivity) context).get(UserViewModel.class);
        productViewModel = new ViewModelProvider((MainActivity) context).get(ProductViewModel.class);
    }

    public static void addDeliveryDialog(Context context, int width) {
        Log.d("AddDeliveryDialog", "Activity context: " + context);

        Dialog addDeliveryDialog = new Dialog(context);
        addDeliveryDialog.setContentView(R.layout.create_delivery_dialog);
        // Set background to transparent
        addDeliveryDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Set the width of the dialog to match the width of the activity
        Window window = addDeliveryDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = width; // Set the width here
            window.setAttributes(layoutParams);
        }

        addDeliveryDialog.show();

        setUpDynamicDialogDisplay(addDeliveryDialog, context);
        TextView deliveryText = addDeliveryDialog.findViewById(R.id.delivery_date_text);
        MaterialDatePicker<Long> datePicker = getDatePicker();
        Button proposedDeliveryDateButton = addDeliveryDialog.findViewById(R.id.select_delivery_date_button);
        AppCompatActivity activity = (AppCompatActivity) context;
        // Show the Date Picker when the button is clicked
        proposedDeliveryDateButton.setOnClickListener(v -> datePicker.show(activity.getSupportFragmentManager(), "DATE_PICKER"));
        datePicker.addOnPositiveButtonClickListener(selection -> {

            deliveryText.setText(DateTimeUtils.formatTime(selection));
            selectedDate = selection;

        });
        Spinner spinner = addDeliveryDialog.findViewById(R.id.customer_spinner);
        Spinner productSpinner = addDeliveryDialog.findViewById(R.id.product_spinner);
        EditText quantityInput = addDeliveryDialog.findViewById(R.id.delivered_quantity_input);
        EditText transitCostInput = addDeliveryDialog.findViewById(R.id.delivery_cost_input);
        EditText bagsInput = addDeliveryDialog.findViewById(R.id.delivered_bags_input);
        Button addDeliveryButton = addDeliveryDialog.findViewById(R.id.add_delivery_button);
        TextView deliveryDate = addDeliveryDialog.findViewById(R.id.delivery_date_text);
        setCustomerSpinner(spinner, context);
        // Submit button logic
        addDeliveryButton.setOnClickListener(view -> {
            try {
                Product selectedProduct = ((Product) productSpinner.getSelectedItem());

                String[] deliveryDetails = {
                        quantityInput.getText().toString(),
                        bagsInput.getText().toString(),
                        transitCostInput.getText().toString(),
                        String.valueOf(selectedDate)
                };

                DeliveryViewModel deliveryViewModel = new ViewModelProvider((MainActivity) context).get(DeliveryViewModel.class);

                // Run delivery insertion in a background thread
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.submit(new AddDeliveryRunnable(selectedProduct, deliveryDetails, deliveryViewModel, new ValidationListener() {
                    @Override
                    public void onValidationResult(RoomResponse roomResponse) {
                        if (roomResponse.getIsValid()) {
                            Toast.makeText(context, "Delivery Added Successfully", Toast.LENGTH_SHORT).show();
                            addDeliveryDialog.dismiss();
                        } else {
                            Toast.makeText(context, roomResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
                // Close the dialog after submission

            } catch (Exception e) {
                Toast.makeText(context, "Error fetching input: " + e.getStackTrace(), Toast.LENGTH_SHORT).show();
                Log.e("AddDeliveryDialog", "Error fetching input: " + e.getMessage());
            }
        });
    }

    static MaterialDatePicker<Long> getDatePicker() {
        return MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Delivery Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .setCalendarConstraints(
                        new CalendarConstraints.Builder()
                                .setValidator(DateValidatorPointForward.now()) // Only future dates
                                .build())
                .build();
    }

    private static void setUpDynamicDialogDisplay(Dialog dialog, Context context) {
        Spinner spinner = dialog.findViewById(R.id.customer_spinner);
        Spinner productSpinner = dialog.findViewById(R.id.product_spinner);
        setCustomerSpinner(spinner, context);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                User selectedCustomer = (User) spinner.getSelectedItem();
                Long customerId = selectedCustomer.getId();

                productViewModel.getProducts(customerId).observe((MainActivity) context, allProducts -> {
                    if (allProducts == null || allProducts.isEmpty()) {
                        // Hide product spinner and order quantity layout if no products are found
                        dialog.findViewById(R.id.product_linear_view).setVisibility(View.INVISIBLE);
                        dialog.findViewById(R.id.order_quantity_input_layout).setVisibility(View.INVISIBLE);
                    } else {
                        // Populate the product spinner and show it
                        ArrayAdapter<Product> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, allProducts);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        productSpinner.setAdapter(adapter);
                        dialog.findViewById(R.id.product_linear_view).setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Hide all related views when no customer is selected
                dialog.findViewById(R.id.product_linear_view).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.order_quantity_input_layout).setVisibility(View.INVISIBLE);
            }
        });
        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    dialog.findViewById(R.id.order_quantity_input_layout).setVisibility(View.VISIBLE);
                } else {
                    dialog.findViewById(R.id.order_quantity_input_layout).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dialog.findViewById(R.id.order_quantity_input_layout).setVisibility(View.INVISIBLE);
            }
        });


    }

    private static void setCustomerSpinner(Spinner spinner, Context context) {
        userViewModel.getAllCustomer().observe((MainActivity) context, customers -> {
            ArrayAdapter<User> adapter = new ArrayAdapter<>(
                    context,
                    android.R.layout.simple_spinner_item,
                    customers
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        });
    }


}
