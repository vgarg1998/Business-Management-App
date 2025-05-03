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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.northeastern.MrManage.Executors.AddOrderRunnable;
import edu.northeastern.MrManage.MainActivity;
import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.entities.Product;
import edu.northeastern.MrManage.roomApi.entities.User;
import edu.northeastern.MrManage.roomApi.view_model.OrderViewModel;
import edu.northeastern.MrManage.roomApi.view_model.ProductViewModel;
import edu.northeastern.MrManage.roomApi.view_model.UserViewModel;
import edu.northeastern.MrManage.utility.DateTimeUtils;


public class AddOrderDialog {

    final Context context;

    static UserViewModel userViewModel;

    static OrderViewModel orderViewModel;
    static ProductViewModel productViewModel;

    static long selectedDate = 0;


    public AddOrderDialog(Context context) {
        this.context = context;
        userViewModel = new ViewModelProvider((MainActivity) context).get(UserViewModel.class);
        orderViewModel = new ViewModelProvider((MainActivity) context).get(OrderViewModel.class);
        productViewModel = new ViewModelProvider((MainActivity) context).get(ProductViewModel.class);
    }

    public void showAddOrderDialog(Context context, int width) {
        Dialog addOrderDialog = new Dialog(context);
        addOrderDialog.setContentView(R.layout.create_order_dialog);
        // Set background to transparent
        addOrderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Set the width of the dialog to match the width of the activity
        Window window = addOrderDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = width; // Set the width here
            window.setAttributes(layoutParams);
        }
        addOrderDialog.show();
        Button submit = addOrderDialog.findViewById(R.id.add_order_button);
        setOnSubmitClickEvent(addOrderDialog, context, submit);
        setUpDynamicDialogDisplay(addOrderDialog, context);


    }

    private static void setOnSubmitClickEvent(Dialog dialog, Context context, Button submit) {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView proposedDeliveryDate = dialog.findViewById(R.id.delivery_date_text);
                Log.d("Clicked", "Add Order Button Clicked");
                Spinner productSpinner = dialog.findViewById(R.id.product_spinner);
                Product selectedProduct = (Product) productSpinner.getSelectedItem();
                Spinner manufacturerSpinner = dialog.findViewById(R.id.manufacturer_spinner);
                User selectedManufacturer = (User) manufacturerSpinner.getSelectedItem();
                TextInputEditText quantityEditText = dialog.findViewById(R.id.order_quantity_input);
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.submit(new AddOrderRunnable(new ViewModelProvider((MainActivity) context).get(OrderViewModel.class),
                        new ViewModelProvider((MainActivity) context).get(ProductViewModel.class)
                        , new String[]{selectedProduct.getProductId().toString(),
                        String.valueOf(selectedManufacturer.getId()),
                        quantityEditText.getText().toString().trim(),
                        String.valueOf(selectedDate)}, roomResponse -> {
                    if (roomResponse.getIsValid()) {
                        Toast.makeText(context, roomResponse.getMessage(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, roomResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }));
                executorService.shutdown();
            }
        });
        TextView proposedDeliveryDate = dialog.findViewById(R.id.delivery_date_text);
        MaterialDatePicker<Long> datePicker =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Delivery Date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                        .setCalendarConstraints(
                                new CalendarConstraints.Builder()
                                        .setValidator(DateValidatorPointForward.now()) // Only future dates
                                        .build())
                        .build();
        Button proposedDeliveryDateButton = dialog.findViewById(R.id.select_delivery_date_button);
        AppCompatActivity activity = (AppCompatActivity) context;
        // Show the Date Picker when the button is clicked
        proposedDeliveryDateButton.setOnClickListener(v -> datePicker.show(activity.getSupportFragmentManager(), "DATE_PICKER"));
        datePicker.addOnPositiveButtonClickListener(selection -> {
            proposedDeliveryDate.setText(DateTimeUtils.formatTime(selection)
            );
            selectedDate = selection;

        });

    }

    private static void setUpDynamicDialogDisplay(Dialog dialog, Context context) {
        Spinner spinner = dialog.findViewById(R.id.customer_spinner);
        Spinner productSpinner = dialog.findViewById(R.id.product_spinner);
        Spinner manufacturerSpinner = dialog.findViewById(R.id.manufacturer_spinner);

        setCustomerSpinner(spinner, context);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                User selectedCustomer = (User) spinner.getSelectedItem();
                Long customerId = selectedCustomer.getId();

                List<Product> products = new ArrayList<>();
                products.add(new Product());
                productViewModel.getProducts(customerId).observe((MainActivity) context, allProducts -> {
                    products.addAll(allProducts);
                    ArrayAdapter<Product> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, products);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    productSpinner.setAdapter(adapter);
                    dialog.findViewById(R.id.product_linear_view).setVisibility(View.VISIBLE);
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                dialog.findViewById(R.id.product_linear_view).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.manufacturer_linear_view).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.order_quantity_input_layout).setVisibility(View.INVISIBLE);
            }
        });

        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    dialog.findViewById(R.id.manufacturer_linear_view).setVisibility(View.VISIBLE);
                    setManufacturerSpinner(manufacturerSpinner, context);
                } else {
                    dialog.findViewById(R.id.manufacturer_linear_view).setVisibility(View.INVISIBLE);
                    dialog.findViewById(R.id.order_quantity_input_layout).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        manufacturerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                dialog.findViewById(R.id.order_quantity_input_layout).setVisibility(View.VISIBLE);

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

    private static void setManufacturerSpinner(Spinner spinner, Context context) {
        userViewModel.getAllManufacturers().observe((MainActivity) context, manufacturers -> {

            ArrayAdapter<User> adapter = new ArrayAdapter<>(
                    context,
                    android.R.layout.simple_spinner_item,
                    manufacturers
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        });
    }
}
