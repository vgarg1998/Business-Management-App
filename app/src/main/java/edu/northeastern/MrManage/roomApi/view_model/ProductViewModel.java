package edu.northeastern.MrManage.roomApi.view_model;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.northeastern.MrManage.roomApi.entities.Product;
import edu.northeastern.MrManage.roomApi.entities.ProductCustomer;
import edu.northeastern.MrManage.roomApi.repositories.ProductRepository;

@HiltViewModel
public class ProductViewModel extends ViewModel {

    private final ProductRepository productRepository;

    private ExecutorService executorService;

    @Inject
    public ProductViewModel(ProductRepository productRepository) {
        this.productRepository = productRepository;

        this.executorService = Executors.newSingleThreadExecutor();
    }

    // Method to insert a product
    public void insertProduct(Product product) {
        executorService.execute(() -> productRepository.insertProduct(product));
    }

    // Method to fetch products for a specific customer
    public LiveData<List<Product>> getProducts(Long customerId) {
        return productRepository.getProducts(customerId);
    }

    // Method to update ordered quantity
    public void updateOrderedQuantity(String productId, String quantity) {
        executorService.execute(() -> productRepository.updateOrderedQuantity(productId, quantity));
    }

    // Method to get product name and customer ID
    public LiveData<ProductCustomer> getProductNameAndCustomerId(Long productId) {
        // If `productDao.getProductName` should return `LiveData`, refactor in DAO/repository.
        return new LiveData<>() {
            @Override
            protected void onActive() {
                executorService.execute(() -> {
                    ProductCustomer productCustomer = productRepository.getProductNameAndCustomerId(productId);
                    postValue(productCustomer);
                });
            }
        };
    }

    // Method to update the number of bags delivered
    public void updateNumberOfBagsDelivered(long productId, long bagsDelivered) {
        executorService.execute(() -> productRepository.updateNumberOfBagsDelivered(productId, bagsDelivered));
    }

    // Method to update the number of bags in sto ck
    public void updateNumberOfBagsInStock(long productId, long bagsInStock) {
        executorService.execute(() -> productRepository.updateNumberOfBagsInStock(productId, bagsInStock));
    }

    // Method to update the quantity delivered
    public void updateQuantityDelivered(long productId, double delivered) {
        executorService.execute(() -> productRepository.updateQuantityDelivered(productId, delivered));
    }

    // Method to update the quantity in inventory
    public void updateQuantityInStock(long productId, double delivered) {
        executorService.execute(() -> productRepository.updateQuantityInStock(productId, delivered));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}


