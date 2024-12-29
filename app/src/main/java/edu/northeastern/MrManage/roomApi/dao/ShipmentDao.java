package edu.northeastern.MrManage.roomApi.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.northeastern.MrManage.roomApi.entities.Shipment;

@Dao
public interface ShipmentDao {

    @Query("Select count(*) from SHIPMENT where order_id =:orderId")
    LiveData<Integer> getNumberOfShipments(Long orderId);

    @Query("Select sum(quantity) from SHIPMENT where order_id=:orderId")
    double getReceivedQuantity(Long orderId);

    @Insert
    void insert(Shipment shipment);

    @Query("SELECT * FROM SHIPMENT")
    LiveData<List<Shipment>> getAllShipments();

    @Query("SELECT * FROM SHIPMENT WHERE delivery_type = 1")
    LiveData<List<Shipment>> getAllDirectShipments();
}
