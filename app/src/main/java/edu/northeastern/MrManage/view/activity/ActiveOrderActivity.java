package edu.northeastern.MrManage.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import edu.northeastern.MrManage.MainActivity;
import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.entities.Order;
import edu.northeastern.MrManage.roomApi.entities.Product;
import edu.northeastern.MrManage.roomApi.entities.User;
import edu.northeastern.MrManage.roomApi.view_model.OrderViewModel;
import edu.northeastern.MrManage.roomApi.view_model.ProductViewModel;
import edu.northeastern.MrManage.roomApi.view_model.UserViewModel;
import edu.northeastern.MrManage.view.adapters.ActiveOrderAdapter;
import edu.northeastern.MrManage.view.dialogs.ViewOrderDialog;


@AndroidEntryPoint
public class ActiveOrderActivity extends AppCompatActivity {
    private final static int stage = -1;

    // Initialize the list
    ActiveOrderAdapter activeOrderAdapter;
    RecyclerView activeOrderRecyclerView;

    OrderViewModel orderViewModel;

    Spinner customerSpinner;
    Spinner productSpinner;

    int width;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_order_layout);
        customerSpinner = findViewById(R.id.customer_spinner);
        productSpinner = findViewById(R.id.product_spinner);
        setCustomerSpinner(customerSpinner,this);
        // Initialize RecyclerView and Adapter before adding data
        activeOrderRecyclerView = findViewById(R.id.active_order_recycler_view);

        activeOrderRecyclerView.setAdapter(activeOrderAdapter);
        activeOrderRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch data in a background thread
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);


        // Observe changes in LiveData from the ViewModel

        orderViewModel.getAllActiveOrders().observe(this, activeOrders -> {
            Log.d("ActiveOrders", "Observed orders: " + activeOrders);
            if (activeOrders != null) {
                if (activeOrderAdapter == null) {
                    View rootView = getWindow().getDecorView().getRootView();
                    rootView.post(() -> {
                        int width = rootView.getWidth();
                        if (width > 0) {
                            activeOrderAdapter = new ActiveOrderAdapter(activeOrders, this, width);
                            Log.d("Active Order Activity", "All Orders Width SET :" + width);
                            // Attach adapter to RecyclerView or update UI
                            activeOrderRecyclerView.setAdapter(activeOrderAdapter);
                        } else {
                            Log.e("Active Order Activity", "Width is still 0 after layout.");
                        }
                    });
                }else{
                    activeOrderAdapter.updateOrders(activeOrders);
                }
            }
        });


        customerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                User customer = (User) parent.getSelectedItem();
                if(customer!=null){
                    ProductViewModel productViewModel = new ViewModelProvider(ActiveOrderActivity.this).get(ProductViewModel.class);
                    List<Product> productList = new ArrayList<>();
                    productList.add(new Product());
                    productViewModel.getProducts(customer.getId()).observe(ActiveOrderActivity.this,products->{
                        productList.addAll(products);
                        ArrayAdapter<Product> adapter = new ArrayAdapter<>(
                                getApplicationContext(),
                                android.R.layout.simple_spinner_item,
                                productList
                        );

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        productSpinner.setAdapter(adapter);
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {

            Order order = data.getParcelableExtra("order");
            String productName = data.getStringExtra("productName");
            String customerName = data.getStringExtra("customerName");
            int numberOfShipments = data.getIntExtra("numberOfShipments", -1);
            double receivedQuantity = data.getDoubleExtra("receivedQuantity", -1);
            int position = data.getIntExtra("position", -1);

            if (numberOfShipments == -1 || receivedQuantity == -1 || position == -1) {
                Toast.makeText(this, "Please re-open order details to view changes", Toast.LENGTH_SHORT).show();
            } else {
                order.setReceivedQuantity(receivedQuantity);
                activeOrderAdapter.updateOrder(position, order);
                new ViewOrderDialog().showDialog(this, order, productName, customerName, numberOfShipments, receivedQuantity, position, width );
            }


            // Fetch updated data from Room DB
        }
    }

    public void startSearch(View view) {
        // Get selected customer and product
        User selectedCustomer = (User) customerSpinner.getSelectedItem();
        Product selectedProduct = (Product) productSpinner.getSelectedItem();

        // Check if a customer is selected
        if (selectedCustomer != null) {
            // Fetch orders based on the selected product and customer
            if (selectedProduct != null && productSpinner.getSelectedItemPosition()!=0) {
                // Show orders for the selected customer and product
                orderViewModel.getOrdersByProduct(selectedProduct.getProductId(), stage)
                        .observe(this, orders -> {
                            if (orders != null) {
                                activeOrderAdapter.updateOrders(orders);
                                Toast.makeText(this, "Showing orders for customer: " + selectedCustomer.getName() +
                                        " and product: " + selectedProduct, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "No orders found for the selected customer and product", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                // Show orders for the selected customer and all products
                orderViewModel.getOrdersByCustomer(selectedCustomer.getId(),stage)
                        .observe(this, orders -> {
                            if (orders != null) {
                                activeOrderAdapter.updateOrders(orders);
                                Toast.makeText(this, "Showing all orders for customer: " + selectedCustomer.getName(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "No orders found for the selected customer", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } else {
            Toast.makeText(this, "Please select a customer", Toast.LENGTH_SHORT).show();
        }
    }

    private static void setCustomerSpinner(Spinner spinner, Context context) {
        UserViewModel userViewModel = new ViewModelProvider((ActiveOrderActivity)context).get(UserViewModel.class);
        userViewModel.getAllCustomer().observe((ActiveOrderActivity) context, customers -> {
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