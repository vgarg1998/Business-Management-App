package edu.northeastern.MrManage.roomApi.view_model;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.northeastern.MrManage.roomApi.entities.Order;
import edu.northeastern.MrManage.roomApi.repositories.OrderRepository;


@HiltViewModel
public class OrderViewModel extends ViewModel {
    private final OrderRepository orderRepository;
    private final LiveData<List<Order>> allActiveOrders;
    private final ExecutorService executorService;

    @Inject
    public OrderViewModel(OrderRepository repository) {
        this.orderRepository = repository;
        this.allActiveOrders = orderRepository.getActiveOrders();
        this.executorService = Executors.newSingleThreadExecutor(); // Create a single-threaded executor
    }

    // Expose LiveData for orders
    public LiveData<List<Order>> getAllActiveOrders() {
        return allActiveOrders;
    }

    // Add an order
    public void addOrder(Order order) {
        executorService.execute(() -> {
            orderRepository.insertOrder(order);
        });
    }

    public void updateReceivedQuantity(Long orderId, double quantity) {
        executorService.execute(() -> {
            orderRepository.updateReceivedQuantity(orderId, quantity);
        });

    }

    public void makeOrderReceived(Long orderId, Long currentDate) {
        executorService.execute(() -> {
            orderRepository.makeOrderReceived(orderId, currentDate);
        });
    }

    public LiveData<List<Order>> getReceivedOrder() {
        return orderRepository.getReceivedOrder();
    }

    // Delete an order


    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown(); // Shutdown executor service when ViewModel is cleared
    }

    public LiveData<List<Order>> getOrdersByProduct(Long productId, int stage) {
        return orderRepository.getOrdersByProduct(productId, stage);
    }

    public LiveData<List<Order>> getOrdersByCustomer(Long customerId, int stage) {
        return orderRepository.getOrdersByCustomer(customerId, stage);
    }

    public void deleteOrder(Order order) {
        executorService.execute(() -> orderRepository.deleteOrder(order));
    }
}
