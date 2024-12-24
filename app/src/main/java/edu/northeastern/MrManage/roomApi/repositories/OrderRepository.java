package edu.northeastern.MrManage.roomApi.repositories;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import edu.northeastern.MrManage.roomApi.dao.OrderDao;
import edu.northeastern.MrManage.roomApi.entities.Order;

public class OrderRepository {
    private final OrderDao orderDao;

    @Inject
    public OrderRepository(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    // Get all orders
    public LiveData<List<Order>> getActiveOrders() {
        return orderDao.getActiveOrders();
    }

    // Insert an order
    public void insertOrder(Order order) {
        orderDao.insertOrder(order);
    }

    // Delete an order
    public void updateReceivedQuantity(Long orderId, double quantity) {
        orderDao.updateReceivedQuantity(orderId, quantity);
    }
}
