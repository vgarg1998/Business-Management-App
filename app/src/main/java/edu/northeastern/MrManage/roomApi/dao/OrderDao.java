package edu.northeastern.MrManage.roomApi.dao;

import androidx.lifecycle.LiveData;
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
    LiveData<List<Order>> getActiveOrders();

    @Query("UPDATE `Order` SET received_quantity = received_quantity + :quantity WHERE order_id = :orderId")
    void updateReceivedQuantity(Long orderId, double quantity);

    @Query("UPDATE `ORDER` SET stage = 0, end_date = :currentDate WHERE order_id = :orderId")
    void makeOrderReceived(Long orderId, Long currentDate);

    @Query("SELECT * FROM `ORDER` WHERE stage = 0")
    LiveData<List<Order>> getReceivedOrder();

    @Query("SELECT * FROM `ORDER` WHERE product_id=:productId and stage =:stage")
    LiveData<List<Order>> getOrdersByProduct(Long productId, int stage);

    @Query("SELECT o.* FROM `Order` o " +
            "JOIN Product p ON o.product_id = p.product_id " +
            "JOIN User c ON p.customer_id = c.id " +
            "WHERE c.id = :customerId and o.stage=:stage")
    LiveData<List<Order>> getOrdersByCustomer(Long customerId, int stage);

    @Query("DELETE FROM `ORDER` WHERE order_id = :orderId")
    void deleteOrder(Long orderId);
}
