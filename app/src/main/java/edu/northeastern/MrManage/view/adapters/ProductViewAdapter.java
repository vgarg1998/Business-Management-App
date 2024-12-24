package edu.northeastern.MrManage.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.entities.Product;
import edu.northeastern.MrManage.view.viewholders.ProductViewHolder;

public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private List<Product> products;

    public ProductViewAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productViewHolderLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        return new ProductViewHolder(productViewHolderLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productName.setText(product.getProductName());
        holder.deliveredQuantity.setText(String.valueOf(product.getQuantityDelivered()));
        holder.inStockQuantity.setText(String.valueOf(product.getQuantityInInventor()));
        holder.orderedQuantity.setText(String.valueOf(product.getQuantityInOrder()));
        holder.totalDeliveredBags.setText(String.valueOf(product.getNumberOfBagsDelivered()));
        holder.inStockBags.setText(String.valueOf(product.getNumberOfBagsInStock()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateProduct(List<Product> newProducts) {
        products.clear();
        products.addAll(newProducts);
        notifyDataSetChanged();
    }

    public void addProduct(Product product) {
        products.add(product);
        notifyDataSetChanged();
    }
}
