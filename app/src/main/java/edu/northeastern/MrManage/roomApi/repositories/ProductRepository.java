package edu.northeastern.MrManage.roomApi.repositories;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import edu.northeastern.MrManage.roomApi.dao.ProductDao;
import edu.northeastern.MrManage.roomApi.entities.Product;
import edu.northeastern.MrManage.roomApi.entities.ProductCustomer;

public class ProductRepository {
    private final ProductDao productDao;

    @Inject
    public ProductRepository(ProductDao productDao) {
        this.productDao = productDao;
    }


    // Method to insert a product
    public void insertProduct(Product product) {
        productDao.insertProduct(product);
    }

    // Method to fetch products for a specific customer
    public LiveData<List<Product>> getProducts(Long customerId) {
        return productDao.getProducts(customerId);
    }

    // Method to update ordered quantity
    public void updateOrderedQuantity(Long productId, double quantity) {
        productDao.updateOrderedQuantity(productId, quantity);
    }

    // Method to get product name and customer ID
    public ProductCustomer getProductNameAndCustomerId(Long productId) {
        return productDao.getProductNameAndCustomerId(productId);
    }

    // Method to update the number of bags delivered
    public void updateNumberOfBagsDelivered(long productId, long bagsDelivered) {
        productDao.updateNumberOfBagsDelivered(productId, bagsDelivered);
    }

    // Method to update the number of bags in stock
    public void updateNumberOfBagsInStock(long productId, long bagsInStock) {
        productDao.updateNumberOfBagsInStock(productId, bagsInStock);
    }

    // Method to update the quantity delivered
    public void updateQuantityDelivered(long productId, double delivered) {
        productDao.updateQuantityDelivered(productId, delivered);
    }

    // Method to update the quantity in inventory
    public void updateQuantityInStock(long productId, double delivered) {
        productDao.updateQuantityInStock(productId, delivered);
    }
}
