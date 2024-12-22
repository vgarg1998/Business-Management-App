package edu.northeastern.MrManage.roomApi.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import edu.northeastern.MrManage.roomApi.entities.Shipment;

@Dao
public interface ShipmentDao {

    @Query("Select count(*) from SHIPMENT where order_id =:orderId")
    int getNumberOfShipments(Long orderId);

    @Query("Select sum(quantity) from SHIPMENT where order_id=:orderId")
    double getReceivedQuantity(Long orderId);

    @Insert
    void insert(Shipment shipment);
}
