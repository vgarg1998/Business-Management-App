package edu.northeastern.MrManage.roomApi.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Transaction;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import edu.northeastern.MrManage.roomApi.dao.ProductDao;
import edu.northeastern.MrManage.roomApi.dao.StockHistoryDao;

import edu.northeastern.MrManage.roomApi.entities.StockHistory;

public class StockHistoryRepository {
    private StockHistoryDao stockHistoryDao;
    private LiveData<List<StockHistory>> allStockHistory;

    private ExecutorService executorService;

    private ProductDao productDao;

    @Inject
    public StockHistoryRepository(StockHistoryDao stockHistoryDao, ProductDao productDao) {

        this.stockHistoryDao = stockHistoryDao;
        this.allStockHistory = stockHistoryDao.getAllStockHistory();
        this.executorService = Executors.newSingleThreadExecutor();
        this.productDao = productDao;
    }

    public LiveData<List<StockHistory>> getAllStockHistory() {
        return allStockHistory;
    }

    @Transaction
    public void insert(StockHistory stockHistory) {
       stockHistoryDao.insert(stockHistory);
       productDao.updateStock(stockHistory.getProductId(), stockHistory.getNumberOfBags(),stockHistory.getQuantity());
    }

    @Transaction
    public void deleteStockHistory(StockHistory stockHistory) {
       stockHistoryDao.deleteStockHistory(stockHistory.getId());
       productDao.deleteStock(stockHistory.getProductId(), stockHistory.getNumberOfBags(),stockHistory.getQuantity());
    }

    public LiveData<String> getProductName(long productId) {
        return productDao.getProductName(productId);
    }
}