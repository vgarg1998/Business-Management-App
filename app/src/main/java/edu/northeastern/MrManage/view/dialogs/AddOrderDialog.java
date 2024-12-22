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

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.MrManageDatabase;
import edu.northeastern.MrManage.roomApi.entities.Product;
import edu.northeastern.MrManage.roomApi.entities.User;
import edu.northeastern.MrManage.threads.AddOrderTask;
import edu.northeastern.MrManage.utility.RoomResponse;
import edu.northeastern.MrManage.utility.interfaces.ValidationListener;

public class AddOrderDialog{

    public static void showAddOrderDialog(Context context, int width) {
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
        setOnSubmitClickEvent(addOrderDialog,context,submit);
        setUpDynamicDialogDisplay(addOrderDialog,context);


    }

    private static void setOnSubmitClickEvent(Dialog dialog,Context context,Button submit) {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView proposedDeliveryDate = dialog.findViewById(R.id.delivery_date_text);
                Log.d("Clicked","Add Order Button Clicked");
                Spinner productSpinner = dialog.findViewById(R.id.product_spinner);
                Product selectedProduct = (Product) productSpinner.getSelectedItem();
                Spinner manufacturerSpinner = dialog.findViewById(R.id.manufacturer_spinner);
                User selectedManufacturer = (User) manufacturerSpinner.getSelectedItem();
                TextInputEditText quantityEditText = dialog.findViewById(R.id.order_quantity_input);

                AddOrderTask addOrderTask = new AddOrderTask(new ValidationListener() {
                    @Override
                    public void onValidationResult(RoomResponse roomResponse) {
                        Log.d("Validation:Order", "Evaluating Validation listener response");
                        if(roomResponse.getIsValid()){
                            Toast.makeText(context,roomResponse.getMessage(),Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(context,roomResponse.getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                });
                addOrderTask.execute(selectedProduct.getProductId().toString(), String.valueOf(selectedManufacturer.getId()),quantityEditText.getText().toString().trim(), proposedDeliveryDate.getText().toString().trim() );
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
            String selectedDate = datePicker.getHeaderText(); // Formatted date
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String formattedDate = sdf.format(new Date(selection));
            proposedDeliveryDate.setText(formattedDate);
        });

    }

    private static void setUpDynamicDialogDisplay(Dialog dialog, Context context) {
        Spinner spinner = dialog.findViewById(R.id.customer_spinner);
        Spinner productSpinner = dialog.findViewById(R.id.product_spinner);
        Spinner manufacturerSpinner = dialog.findViewById(R.id.manufacturer_spinner);
        new Thread(()->{
            List<User> customers = new ArrayList<>();
            customers.add(new User()); //act as blank customer
            customers.addAll(MrManageDatabase.getINSTANCE(context).userDao().getAllCustomers());
            ((AppCompatActivity)context).runOnUiThread(()->{
                // 3. Create an ArrayAdapter for Customer objects
                ArrayAdapter<User> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, customers);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            });
        }).start();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position != 0) {
                    User selectedCustomer = (User) spinner.getSelectedItem();
                    Long customerId = selectedCustomer.getId();
                    new Thread(()->{
                        List<Product> products = new ArrayList<>();
                        products.add(new Product());
                        products.addAll(MrManageDatabase.getINSTANCE(context).productDao().getProducts(customerId));
                        ((AppCompatActivity)context).runOnUiThread(()->{
                            ArrayAdapter<Product> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, products);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            productSpinner.setAdapter(adapter);
                            dialog.findViewById(R.id.product_linear_view).setVisibility(View.VISIBLE);
                        });
                    }).start();
                }else{
                    dialog.findViewById(R.id.product_linear_view).setVisibility(View.INVISIBLE);
                    dialog.findViewById(R.id.manufacturer_linear_view).setVisibility(View.INVISIBLE);
                    dialog.findViewById(R.id.order_quantity_input_layout).setVisibility(View.INVISIBLE);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    dialog.findViewById(R.id.manufacturer_linear_view).setVisibility(View.VISIBLE);
                    new Thread(()->{
                        List<User> manufacturers = new ArrayList<>();
                        manufacturers.add(new User()); //act as blank customer
                        manufacturers.addAll(MrManageDatabase.getINSTANCE(context).userDao().getAllManufacturers());
                        ((AppCompatActivity)context).runOnUiThread(()->{
                            // 3. Create an ArrayAdapter for Customer objects
                            ArrayAdapter<User> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, manufacturers);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            manufacturerSpinner.setAdapter(adapter);
                        });
                    }).start();
                }else{
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
                if(position!=0){
                    dialog.findViewById(R.id.order_quantity_input_layout).setVisibility(View.VISIBLE);
                }else{
                    dialog.findViewById(R.id.order_quantity_input_layout).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }
}
