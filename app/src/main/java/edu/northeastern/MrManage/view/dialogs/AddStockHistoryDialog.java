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

import edu.northeastern.MrManage.Executors.StockHistoryRunnable;
import edu.northeastern.MrManage.MainActivity;
import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.entities.Product;
import edu.northeastern.MrManage.roomApi.entities.User;
import edu.northeastern.MrManage.roomApi.view_model.ProductViewModel;
import edu.northeastern.MrManage.roomApi.view_model.StockHistoryViewModel;
import edu.northeastern.MrManage.roomApi.view_model.UserViewModel;
import edu.northeastern.MrManage.utility.DateTimeUtils;
import edu.northeastern.MrManage.utility.RoomResponse;
import edu.northeastern.MrManage.utility.interfaces.ValidationListener;

public class AddStockHistoryDialog {
    final Context context;
    static UserViewModel userViewModel;
    static ProductViewModel productViewModel;
    static long selectedDate = 0;

    public AddStockHistoryDialog(Context context) {
        this.context = context;
        userViewModel = new ViewModelProvider((MainActivity) context).get(UserViewModel.class);
        productViewModel = new ViewModelProvider((MainActivity) context).get(ProductViewModel.class);
    }

    public static void addStockHistoryDialog(Context context, int width) {
        Log.d("AddStockHistoryDialog", "Activity context: " + context);

        Dialog addStockHistoryDialog = new Dialog(context);
        addStockHistoryDialog.setContentView(R.layout.create_stock_history_dialog);
        addStockHistoryDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = addStockHistoryDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = width;
            window.setAttributes(layoutParams);
        }

        addStockHistoryDialog.show();

        setUpDynamicDialogDisplay(addStockHistoryDialog, context);
        TextView deliveryText = addStockHistoryDialog.findViewById(R.id.delivery_date_text);
        MaterialDatePicker<Long> datePicker = getDatePicker();
        Button proposedDeliveryDateButton = addStockHistoryDialog.findViewById(R.id.select_delivery_date_button);
        AppCompatActivity activity = (AppCompatActivity) context;
        proposedDeliveryDateButton.setOnClickListener(v -> datePicker.show(activity.getSupportFragmentManager(), "DATE_PICKER"));
        datePicker.addOnPositiveButtonClickListener(selection -> {
            deliveryText.setText(DateTimeUtils.formatTime(selection));
            selectedDate = selection;
        });

        Spinner spinner = addStockHistoryDialog.findViewById(R.id.customer_spinner);
        Spinner productSpinner = addStockHistoryDialog.findViewById(R.id.product_spinner);
        EditText quantityInput = addStockHistoryDialog.findViewById(R.id.order_quantity_input);
        EditText bagsInput = addStockHistoryDialog.findViewById(R.id.order_number_of_bags);
        Button addStockHistoryButton = addStockHistoryDialog.findViewById(R.id.add_order_button);
        setCustomerSpinner(spinner, context);

        addStockHistoryButton.setOnClickListener(view -> {
            try {
                Long selectedProductId = ((Product) productSpinner.getSelectedItem()).getProductId();
                String[] stockHistoryDetails = {
                        quantityInput.getText().toString(),
                        bagsInput.getText().toString(),
                        String.valueOf(selectedDate)
                };

                StockHistoryViewModel stockHistoryViewModel = new ViewModelProvider((MainActivity) context).get(StockHistoryViewModel.class);
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.submit(new StockHistoryRunnable(selectedProductId, stockHistoryDetails, stockHistoryViewModel, new ValidationListener() {
                    @Override
                    public void onValidationResult(RoomResponse roomResponse) {
                        // Handle validation result
                        if (roomResponse.getIsValid()) {
                            addStockHistoryDialog.dismiss();
                            Toast.makeText(context, "Stock history added successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Error adding stock history: " + roomResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
            } catch (Exception e) {
                Toast.makeText(context, "Error fetching input: " + e.getStackTrace(), Toast.LENGTH_SHORT).show();
                Log.e("AddStockHistoryDialog", "Error fetching input: " + e.getMessage());
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
                                .setValidator(DateValidatorPointForward.now())
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
                        // Handle empty product list
                    } else {
                        // Handle product list
                        ArrayAdapter<Product> adapter = new ArrayAdapter<>(
                                context,
                                android.R.layout.simple_spinner_item,
                                allProducts
                        );
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        productSpinner.setAdapter(adapter);
                        dialog.findViewById(R.id.product_linear_view).setVisibility(View.VISIBLE);

                        if (productSpinner.getSelectedItemPosition() >= 0) {
                            dialog.findViewById(R.id.order_quantity_input_layout).setVisibility(View.VISIBLE);
                        }

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
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