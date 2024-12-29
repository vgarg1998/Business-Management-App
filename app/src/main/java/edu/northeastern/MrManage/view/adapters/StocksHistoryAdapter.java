package edu.northeastern.MrManage.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.entities.StockHistory;
import edu.northeastern.MrManage.roomApi.view_model.StockHistoryViewModel;
import edu.northeastern.MrManage.view.activity.StocksHistoryActivity;
import edu.northeastern.MrManage.view.viewholders.StocksHistoryViewHolder;


public class StocksHistoryAdapter extends RecyclerView.Adapter<StocksHistoryViewHolder> {

    private List<StockHistory> stockHistories = new ArrayList<>();
    private StockHistoryViewModel stockHistoryViewModel;
    private OnItemClickListener listener;

    private Context context;

    public interface OnItemClickListener {
        void onItemClick(StockHistory stockHistory);
    }

    public StocksHistoryAdapter(OnItemClickListener listener, StockHistoryViewModel stockHistoryViewModel, Context context) {
        this.listener = listener;
        this.stockHistoryViewModel = stockHistoryViewModel;
        this.context = context;
    }

    @NonNull
    @Override
    public StocksHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stock_history, parent, false);
        return new StocksHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StocksHistoryViewHolder holder, int position) {
        StockHistory currentStockHistory = stockHistories.get(position);
        holder.bind(currentStockHistory, listener);
        stockHistoryViewModel.getProductName(currentStockHistory.getProductId()).observe((StocksHistoryActivity) context, productName -> {
            holder.setProductName(productName);
        });
    }

    @Override
    public int getItemCount() {
        return stockHistories.size();
    }

    public void setStockHistories(List<StockHistory> stockHistories) {
        this.stockHistories = stockHistories;
        notifyDataSetChanged();
    }
}