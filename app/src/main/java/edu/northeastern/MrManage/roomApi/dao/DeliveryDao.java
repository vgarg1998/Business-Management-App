package edu.northeastern.MrManage.roomApi.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.northeastern.MrManage.roomApi.entities.Delivery;

@Dao
public interface DeliveryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDelivery(Delivery delivery);

    @Update
    void updateDelivery(Delivery delivery);

    @Query("DELETE FROM delivery WHERE id = :deliveryId")
    void deleteDelivery(long deliveryId);

    @Query("SELECT * FROM delivery WHERE product_id = :productId")
    LiveData<List<Delivery>> getDeliveriesByProductId(long productId);

    @Query("SELECT * FROM delivery WHERE delivery_date BETWEEN :startDate AND :endDate")
    LiveData<List<Delivery>> getDeliveriesByDateRange(String startDate, String endDate);

    @Query("SELECT * FROM delivery ORDER BY delivery_date DESC")
    LiveData<List<Delivery>> getAllDeliveries();
}
