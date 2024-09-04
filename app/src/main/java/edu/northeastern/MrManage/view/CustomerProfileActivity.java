package edu.northeastern.MrManage.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.doa.entities.Customer;

public class CustomerProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Customer customer = (Customer) getIntent().getExtras().getSerializable("client_data");
        setContentView(R.layout.customer_profile_layout);

        TextView name = findViewById(R.id.customer_name);
        name.setText(customer.getName());

    }
}
