package edu.northeastern.MrManage.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.entities.Delivery;
import edu.northeastern.MrManage.view.viewholders.DeliveryViewHolder;

public class DeliveryHistoryAdapter extends RecyclerView.Adapter<DeliveryHistoryAdapter.DeliveryViewHolder> {

    private List<Delivery> deliveryList;
    private List<Delivery> deliveryListFull; // Full copy for filtering

    public DeliveryHistoryAdapter(List<Delivery> deliveryList) {
        this.deliveryList = new ArrayList<>(deliveryList);
        this.deliveryListFull = new ArrayList<>(deliveryList);
    }


    @NonNull
    @Override
    public DeliveryHistoryAdapter.DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryHistoryAdapter.DeliveryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return deliveryList.size();
    }

//    public void filter(String customerQuery, String productQuery) {
//        if (customerQuery.isEmpty() && productQuery.isEmpty()) {
//            deliveryList.clear();
//            deliveryList.addAll(deliveryListFull);
//        } else {
//            List<Delivery> filteredList = new ArrayList<>();
//            for (Delivery delivery : deliveryListFull) {
//                boolean matchesCustomer = customerQuery.isEmpty() ||
//                        delivery.getCustomerName().toLowerCase().contains(customerQuery.toLowerCase());
//                boolean matchesProduct = productQuery.isEmpty() ||
//                        delivery.getProductName().toLowerCase().contains(productQuery.toLowerCase());
//
//                if (matchesCustomer && matchesProduct) {
//                    filteredList.add(delivery);
//                }
//            }
//            deliveryList.clear();
//            deliveryList.addAll(filteredList);
//        }
//        notifyDataSetChanged();
//    }
}
