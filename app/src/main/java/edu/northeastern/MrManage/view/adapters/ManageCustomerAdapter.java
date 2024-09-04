package edu.northeastern.MrManage.view.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.doa.entities.Customer;
import edu.northeastern.MrManage.view.CustomerProfileActivity;
import edu.northeastern.MrManage.view.viewholders.CustomerViewHolder;

public class ManageCustomerAdapter extends RecyclerView.Adapter<CustomerViewHolder> {
  // private String TAG = ManageCustomerAdapter.class.getSimpleName();
    //This is where the list of customer should come
    private List<Customer> customers;

    public ManageCustomerAdapter(List<Customer> customers) {
        this.customers = customers;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       //Inflate the item layout
        View customer_item_layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item_layout,parent,false);
        //create the view holder
        return new CustomerViewHolder(customer_item_layout);
    }
    //To display the data at particular position
    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Customer currentCustomer = customers.get(position);

        //bind the data to viewholder views
        holder.customerName.setText(currentCustomer.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Position: " + position, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), CustomerProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("client_data", currentCustomer);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public void updateClients(List<Customer> newCustomers) {
        customers.clear();
        customers.addAll(newCustomers);
        notifyDataSetChanged();
    }
}
