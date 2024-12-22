package edu.northeastern.MrManage.roomApi.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.northeastern.MrManage.roomApi.entities.Order;

@Dao
public interface OrderDao {

    @Insert
    void insertOrder(Order order);

    @Query("SELECT * FROM `Order` WHERE stage = -1")
    List<Order> getActiveOrders();

    @Query("UPDATE `Order` SET received_quantity = received_quantity + :quantity WHERE order_id = :orderId")
    void updateReceivedQuantity(Long orderId, double quantity);

}
