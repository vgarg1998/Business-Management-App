package edu.northeastern.MrManage.roomApi.repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Transaction;

import java.util.List;

import javax.inject.Inject;

import edu.northeastern.MrManage.roomApi.dao.OrderDao;
import edu.northeastern.MrManage.roomApi.dao.ProductDao;
import edu.northeastern.MrManage.roomApi.entities.Order;

public class OrderRepository {
    private final OrderDao orderDao;
    private final ProductDao productDao;

    @Inject
    public OrderRepository(OrderDao orderDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
    }

    // Get all orders
    public LiveData<List<Order>> getActiveOrders() {
        return orderDao.getActiveOrders();
    }

    // Insert an order
    @Transaction
    public void insertOrder(Order order) {
        orderDao.insertOrder(order);
        productDao.updateOrderedQuantity(order.getProductId(), order.getOrderedQuantity());
    }

    // Delete an order
    public void updateReceivedQuantity(Long orderId, double quantity) {
        orderDao.updateReceivedQuantity(orderId, quantity);
    }

    public void makeOrderReceived(Long orderId, Long currentDate) {
        orderDao.makeOrderReceived(orderId, currentDate);
    }

    public LiveData<List<Order>> getReceivedOrder() {
        return orderDao.getReceivedOrder();
    }

    public LiveData<List<Order>> getOrdersByProduct(Long productId, int stage) {
        return orderDao.getOrdersByProduct(productId, stage);
    }

    public LiveData<List<Order>> getOrdersByCustomer(Long customerId, int stage) {
        return orderDao.getOrdersByCustomer(customerId, stage);
    }

    @Transaction
    public void deleteOrder(Order order) {
        orderDao.deleteOrder(order.getOrderId());
        productDao.updateOrderedQuantity(order.getProductId(), -order.getOrderedQuantity());
    }
}
