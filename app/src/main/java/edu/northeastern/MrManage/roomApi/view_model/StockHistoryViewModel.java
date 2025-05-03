package edu.northeastern.MrManage.roomApi.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.northeastern.MrManage.roomApi.entities.StockHistory;
import edu.northeastern.MrManage.roomApi.repositories.StockHistoryRepository;

@HiltViewModel
public class StockHistoryViewModel extends ViewModel {
    private StockHistoryRepository repository;
    private LiveData<List<StockHistory>> allStockHistory;

    private ExecutorService executorService;

    @Inject
    public StockHistoryViewModel(StockHistoryRepository stockHistoryRepository) {

        this.repository = stockHistoryRepository;
        allStockHistory = repository.getAllStockHistory();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<StockHistory>> getAllStockHistory() {
        return allStockHistory;
    }

    public void insert(StockHistory stockHistory) {
        executorService.submit(() -> {
            repository.insert(stockHistory);
        });
    }


    public void delete(StockHistory stockHistory) {
        executorService.submit(() -> {
            repository.deleteStockHistory(stockHistory);
        });
    }

    public LiveData<String> getProductName(long productId) {
        return repository.getProductName(productId);
    }
}