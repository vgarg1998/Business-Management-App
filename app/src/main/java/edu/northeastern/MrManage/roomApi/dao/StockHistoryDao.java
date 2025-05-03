package edu.northeastern.MrManage.roomApi.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.northeastern.MrManage.roomApi.entities.StockHistory;

@Dao
public interface StockHistoryDao {

    @Insert
    void insert(StockHistory stockHistory);

    @Query("SELECT * FROM stock_history")
    LiveData<List<StockHistory>> getAllStockHistory();

    @Query("DELETE FROM stock_history WHERE id= :id")
    void deleteStockHistory(long id);
}