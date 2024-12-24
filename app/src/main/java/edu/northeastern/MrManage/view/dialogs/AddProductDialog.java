package edu.northeastern.MrManage.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.northeastern.MrManage.Executors.AddProductRunnable;
import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.view_model.ProductViewModel;
import edu.northeastern.MrManage.view.activity.CustomerProfileActivity;
import edu.northeastern.MrManage.view.adapters.ProductViewAdapter;

public class AddProductDialog {

    private ProductViewModel productViewModel;

    public static void addProductDialog(@NonNull Context context, int width, Long customer_id, ProductViewAdapter productViewAdapter) {
        Dialog addProductDialog = new Dialog(context);
        addProductDialog.setContentView(R.layout.add_product_dialog);
        // Set background to transparent
        addProductDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Set the width of the dialog to match the width of the activity
        Window window = addProductDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = width; // Set the width here
            window.setAttributes(layoutParams);
        }
        addProductDialog.show();
        Button submit = addProductDialog.findViewById(R.id.add_product_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText productNameEditText = addProductDialog.findViewById(R.id.product_name_input);
                TextInputEditText productTypeEditText = addProductDialog.findViewById(R.id.product_type_input);
                String productName = productNameEditText.getText().toString().toUpperCase();
                String productType = productTypeEditText.getText().toString().toUpperCase();

                ExecutorService executorService = Executors.newSingleThreadExecutor();

                executorService.submit(new AddProductRunnable(new ViewModelProvider((CustomerProfileActivity) context).get(ProductViewModel.class), new String[]{String.valueOf(customer_id), productName, productType}, productViewAdapter, (roomResponse) -> {
                    if (roomResponse.getIsValid()) {
                        Toast.makeText(addProductDialog.getContext(), roomResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        addProductDialog.dismiss();
                    } else {
                        Toast.makeText(addProductDialog.getContext(), roomResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
                executorService.shutdown();
            }
        });
    }
}
