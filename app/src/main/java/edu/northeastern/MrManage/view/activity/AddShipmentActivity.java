package edu.northeastern.MrManage.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dagger.hilt.android.AndroidEntryPoint;
import edu.northeastern.MrManage.Executors.AddOrderRunnable;
import edu.northeastern.MrManage.Executors.AddShipmentRunnable;
import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.MrManageDatabase;
import edu.northeastern.MrManage.roomApi.entities.Order;
import edu.northeastern.MrManage.roomApi.entities.Shipment;
import edu.northeastern.MrManage.roomApi.view_model.OrderViewModel;
import edu.northeastern.MrManage.roomApi.view_model.ProductViewModel;
import edu.northeastern.MrManage.roomApi.view_model.ShipmentViewModel;
import edu.northeastern.MrManage.view.dialogs.AddOrderDialog;


@AndroidEntryPoint
public class AddShipmentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_shipment_layout);
        TextView textViewOrderId = findViewById(R.id.orderId);
        TextView textViewProductName = findViewById(R.id.productName);
        Spinner spinner = findViewById(R.id.spinnerShipmentType);
        EditText editTextNumberOfBags = findViewById(R.id.editTextNumberOfBags);
        EditText editTextTotalWeight = findViewById(R.id.editTextTotalWeight);
        EditText editTextTransitCost = findViewById(R.id.editTextTransitCost);
        Button submitButton = findViewById(R.id.buttonSubmit);

        Intent intent = getIntent();
        Order order;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            order = intent.getParcelableExtra("order", Order.class); // API 33 and above
        } else {
            order = (Order) intent.getParcelableExtra("order"); // Older APIs
        }
        Bundle bundle = intent.getExtras();

        textViewOrderId.setText(String.valueOf(order.getOrderId()));
        String productName = bundle.getString("productName");
        textViewProductName.setText(productName);

        String customerName = bundle.getString("customerName");
        int numberOfShipments = bundle.getInt("numberOfShipments");
        double receivedQuantity = bundle.getDouble("receivedQuantity");

        int position = bundle.getInt("position");


        submitButton.setOnClickListener(v -> {
            boolean flag = true;
            String shipmentTypeStr = spinner.getSelectedItem().toString();
            //1 for customer, 0 for warehouse
            int shipmentType = 1;
            if (shipmentTypeStr.equals("Received at Warehouse")) {
                shipmentType = 0;
            }

            String numberOfBagsStr = editTextNumberOfBags.getText().toString().trim();
            String totalWeightStr = editTextTotalWeight.getText().toString().trim();
            String transitCostStr = editTextTransitCost.getText().toString().trim();
            String[] shipmentDetails = new String[]{numberOfBagsStr, totalWeightStr, transitCostStr};
            ShipmentViewModel shipmentViewModel = new ViewModelProvider(this).get(ShipmentViewModel.class);
            OrderViewModel orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
            ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(new AddShipmentRunnable(order.getOrderId(), order.getProductId(), shipmentType, shipmentDetails,
                    shipmentViewModel, orderViewModel, productViewModel, (roomResponse) -> {
                if (roomResponse.getIsValid()) {
                    Toast.makeText(this, roomResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("order", order);
                    resultIntent.putExtra("customerName", customerName);
                    resultIntent.putExtra("productName", productName);
                    resultIntent.putExtra("numberOfShipments", numberOfShipments + 1);
                    double totalWeight = Double.parseDouble(totalWeightStr);
                    resultIntent.putExtra("receivedQuantity", receivedQuantity + totalWeight);
                    resultIntent.putExtra("position", position);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(this, roomResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }));
        });
    }
}



