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
import androidx.room.RoomDatabase;

import org.w3c.dom.Text;

import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.MrManageDatabase;
import edu.northeastern.MrManage.roomApi.entities.Order;
import edu.northeastern.MrManage.roomApi.entities.Shipment;

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
            if(shipmentTypeStr.equals("Received at Warehouse")){
                shipmentType = 0;
            }

            String numberOfBagsStr = editTextNumberOfBags.getText().toString().trim();
            String totalWeightStr = editTextTotalWeight.getText().toString().trim();
            String transitCostStr = editTextTransitCost.getText().toString().trim();


            try{
                int numberOfBags = Integer.parseInt(numberOfBagsStr);
                if(numberOfBags<=0){
                    flag = false;
                    throw new NumberFormatException("Received Bags can not be less than 1");
                }
                double totalWeight = Double.parseDouble(totalWeightStr);
                if(totalWeight<=0){
                    flag = false;
                    throw new NumberFormatException("Weight should be more than 0");
                }
                int transitCost = Integer.parseInt(transitCostStr);
                if(transitCost<0){
                    flag = false;
                    throw new NumberFormatException("Transit Cost can not be less than 0");
                }
                if(flag){
                    //go ahead
                    int finalShipmentType = shipmentType;
                    MrManageDatabase db = MrManageDatabase.getINSTANCE(getApplicationContext());
                    new Thread(()->{
                        Shipment shipment = new Shipment(order.getOrderId(), numberOfBags,totalWeight, finalShipmentType,transitCost);
                        db.shipmentDao().insert(shipment);
                        db.orderDao().updateReceivedQuantity(order.getOrderId(), totalWeight);
                        if(finalShipmentType==0){
                            db.productDao().updateNumberOfBagsInStock(order.getProductId(), numberOfBags);
                            db.productDao().updateQuantityInStock(order.getProductId(), totalWeight);
                        }else{
                            db.productDao().updateNumberOfBagsDelivered(order.getProductId(), numberOfBags);
                            db.productDao().updateQuantityDelivered(order.getProductId(), totalWeight);
                        }


                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("order",order);
                        resultIntent.putExtra("customerName", customerName);
                        resultIntent.putExtra("productName", productName);
                        resultIntent.putExtra("numberOfShipments",  numberOfShipments+1);
                        resultIntent.putExtra("receivedQuantity", receivedQuantity+totalWeight);
                        resultIntent.putExtra("position",position);

                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }).start();
                }else{
                    Toast.makeText(this, "Can not add shipment right now", Toast.LENGTH_SHORT).show();
                }

            }catch(NumberFormatException ex){

                Toast.makeText(this,ex.getMessage(), Toast.LENGTH_LONG).show();
            }catch (Exception e) {
                Log.e("AddShipmentActivity", "Error during database operations", e);
                // Handle error appropriately
            }
        });
    }
}



