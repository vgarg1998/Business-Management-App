package edu.northeastern.MrManage.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.entities.User;
import edu.northeastern.MrManage.roomApi.view_model.ProductViewModel;
import edu.northeastern.MrManage.view.adapters.ProductViewAdapter;
import edu.northeastern.MrManage.view.dialogs.AddProductDialog;

@AndroidEntryPoint
public class CustomerProfileActivity extends AppCompatActivity {
    Long customer_id;
    ProductViewAdapter productAdapter;
    RecyclerView productRecyclerview;

    ProductViewModel productViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User customer = (User) getIntent().getExtras().getSerializable("client_data");
        setContentView(R.layout.customer_profile_layout);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        TextView name = findViewById(R.id.customer_name);
        name.setText(customer.getName());
        TextView email = findViewById((R.id.email_text));
        email.setText("Email: " + customer.getEmail_id());
        TextView gstNumber = findViewById(R.id.gst_number_text);
        gstNumber.setText("GST No.: " + customer.getGst_number());
        TextView phoneNumber = findViewById(R.id.phone_number_text);
        phoneNumber.setText("Phone Number: " + customer.getPhone_number());
        customer_id = customer.getId();
        setAllProductsInRecyclerView(customer_id);
    }

    private void setAllProductsInRecyclerView(Long id) {
        productRecyclerview = findViewById(R.id.product_recycler_view);
        productAdapter = new ProductViewAdapter(new ArrayList<>());
        productViewModel.getProducts(id).observe(this, products -> {
            productAdapter.updateProduct(products);
        });
        productRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        productRecyclerview.setAdapter(productAdapter);
    }

    public void openAddProductDialog(View view) {
        View rootView = getWindow().getDecorView().getRootView();
        AddProductDialog.addProductDialog(this, rootView.getWidth(), customer_id, productAdapter);

    }

}
