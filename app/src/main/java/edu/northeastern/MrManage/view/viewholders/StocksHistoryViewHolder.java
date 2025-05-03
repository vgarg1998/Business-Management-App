package edu.northeastern.MrManage.view.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.entities.StockHistory;
import edu.northeastern.MrManage.utility.DateTimeUtils;
import edu.northeastern.MrManage.view.adapters.StocksHistoryAdapter;

public class StocksHistoryViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewProductName;
    private TextView textViewProductId;
    private TextView textViewQuantity;
    private TextView textViewNumberOfBags;
    private TextView textViewAddedDate;

    private Button buttonDelete;

    public StocksHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewProductName = itemView.findViewById(R.id.text_view_product_name);
        textViewProductId = itemView.findViewById(R.id.text_view_product_id);
        textViewQuantity = itemView.findViewById(R.id.text_view_quantity);
        textViewNumberOfBags = itemView.findViewById(R.id.text_view_number_of_bags);
        textViewAddedDate = itemView.findViewById(R.id.text_view_added_date);
        buttonDelete = itemView.findViewById(R.id.delete_button);
    }

    public void bind(StockHistory stockHistory, StocksHistoryAdapter.OnItemClickListener listener) {
        textViewProductId.setText(String.valueOf(stockHistory.getProductId()));
        textViewQuantity.setText(String.valueOf(stockHistory.getQuantity()));
        textViewNumberOfBags.setText(String.valueOf(stockHistory.getNumberOfBags()));
        textViewAddedDate.setText(String.valueOf(DateTimeUtils.formatTime(stockHistory.getAddedDate())));

        buttonDelete.setOnClickListener(v -> listener.onItemClick(stockHistory));
    }

    public void setProductName(String productName) {
        textViewProductName.setText(productName);
    }
}