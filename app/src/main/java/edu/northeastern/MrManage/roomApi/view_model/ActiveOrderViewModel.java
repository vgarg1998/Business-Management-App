package edu.northeastern.MrManage.roomApi.view_model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.northeastern.MrManage.roomApi.entities.Order;
import edu.northeastern.MrManage.roomApi.entities.ProductCustomer;
import edu.northeastern.MrManage.roomApi.repositories.ProductRepository;
import edu.northeastern.MrManage.roomApi.repositories.ShipmentRepository;
import edu.northeastern.MrManage.roomApi.repositories.UserRepository;

@HiltViewModel
public class ActiveOrderViewModel extends ViewModel {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ShipmentRepository shipmentRepository;

    private final ExecutorService executorService;

    @Inject
    public ActiveOrderViewModel(ProductRepository productRepository, UserRepository userRepository, ShipmentRepository shipmentRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.shipmentRepository = shipmentRepository;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    // Fetch details for a specific order and return LiveData scoped to that order
    public LiveData<ActiveOrderViewModel.OrderDetails> getOrderDetailsForOrder(Order order) {
        MutableLiveData<OrderDetails> liveData = new MutableLiveData<>();
        executorService.submit(() -> {
            try {
                // Fetch data from repositories
                ProductCustomer productCustomer = productRepository.getProductNameAndCustomerId(order.getProductId());
                if (productCustomer != null) {
                    String productName = productCustomer.getProductName();
                    Long customerId = productCustomer.getCustomerId();
                    String customerName = userRepository.getUserName(customerId).getValue(); // Ensure this fetches synchronously or handle LiveData appropriately
                    Integer numberOfShipments = shipmentRepository.getNumberOfShipments(order.getOrderId()).getValue();

                    if (customerName != null && numberOfShipments != null) {
                        liveData.postValue(new OrderDetails(productName, customerName, numberOfShipments));
                    }
                }
            } catch (Exception e) {
                Log.e("ActiveOrderViewModel", "Error fetching order details", e);
            }
        });
        return liveData;
    }


    public static class OrderDetails {
        public final String productName;
        public final String customerName;
        public final int numberOfShipments;

        public OrderDetails(String productName, String customerName, int numberOfShipments) {
            this.productName = productName;
            this.customerName = customerName;
            this.numberOfShipments = numberOfShipments;
        }
    }
}
