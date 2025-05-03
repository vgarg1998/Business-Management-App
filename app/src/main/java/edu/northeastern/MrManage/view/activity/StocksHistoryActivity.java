package edu.northeastern.MrManage.view.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dagger.hilt.android.AndroidEntryPoint;
import edu.northeastern.MrManage.R;
import edu.northeastern.MrManage.roomApi.entities.StockHistory;
import edu.northeastern.MrManage.roomApi.view_model.StockHistoryViewModel;
import edu.northeastern.MrManage.view.adapters.StocksHistoryAdapter;

@AndroidEntryPoint
public class StocksHistoryActivity extends AppCompatActivity implements StocksHistoryAdapter.OnItemClickListener {

    private StockHistoryViewModel stockHistoryViewModel;
    private StocksHistoryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks_history);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_stocks_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        stockHistoryViewModel = new ViewModelProvider(this).get(StockHistoryViewModel.class);
        adapter = new StocksHistoryAdapter(this, stockHistoryViewModel, this);
        recyclerView.setAdapter(adapter);

        stockHistoryViewModel = new ViewModelProvider(this).get(StockHistoryViewModel.class);
        stockHistoryViewModel.getAllStockHistory().observe(this, stockHistories -> {
            adapter.setStockHistories(stockHistories);
        });
    }

    @Override
    public void onItemClick(StockHistory stockHistory) {
        if (stockHistoryViewModel != null) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Stock History")
                    .setMessage("Are you sure you want to delete this stock history?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        stockHistoryViewModel.delete(stockHistory);
                        Toast.makeText(this, "Stock history deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        } else {
            Toast.makeText(this, "Error: ViewModel is not initialized", Toast.LENGTH_SHORT).show();
        }
    }
}