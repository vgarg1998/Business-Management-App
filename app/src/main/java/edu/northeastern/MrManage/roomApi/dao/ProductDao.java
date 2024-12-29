package edu.northeastern.MrManage.roomApi.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.northeastern.MrManage.roomApi.entities.Product;
import edu.northeastern.MrManage.roomApi.entities.ProductCustomer;

@Dao
public interface ProductDao {

    @Insert
    void insertProduct(Product product);

    @Query("Select * from Product where customer_id = :customerId")
    LiveData<List<Product>> getProducts(Long customerId);

    @Query("UPDATE Product SET quantity_in_order = :quantity + quantity_in_order WHERE product_id = :productId")
    void updateOrderedQuantity(Long productId, double quantity);

    @Query("SELECT product_name as productName, customer_id as customerId FROM Product WHERE product_id = :productId")
    ProductCustomer getProductNameAndCustomerId(Long productId);


    // Method to update number_of_bags_delivered
    @Query("UPDATE Product SET number_of_bags_delivered = number_of_bags_delivered + :bagsDelivered WHERE product_id = :productId")
    void updateNumberOfBagsDelivered(long productId, long bagsDelivered);

    // Method to update number_of_bags_in_stock
    @Query("UPDATE Product SET number_of_bags_in_stock = number_of_bags_in_stock + :bagsInStock WHERE product_id = :productId")
    void updateNumberOfBagsInStock(long productId, long bagsInStock);


    @Query("UPDATE `Product` SET quantity_delivered = quantity_delivered + :delivered WHERE product_id = :productId")
    void updateQuantityDelivered(long productId, double delivered);

    @Query("UPDATE `Product` SET quantity_in_inventory = quantity_in_inventory + :delivered WHERE product_id = :productId")
    void updateQuantityInStock(long productId, double delivered);


    @Query("UPDATE Product SET number_of_bags_in_stock = number_of_bags_in_stock  +:numberOfBags, quantity_in_inventory = quantity_in_inventory +:quantity WHERE product_id = :productId")
    void updateStock(long productId, int numberOfBags, double quantity);

    @Query("UPDATE Product SET number_of_bags_in_stock = number_of_bags_in_stock - :numberOfBags, quantity_in_inventory = quantity_in_inventory - :quantity WHERE product_id = :productId")
    void deleteStock(long productId, int numberOfBags, double quantity);

    @Query("SELECT product_name FROM Product WHERE product_id = :productId")
    LiveData<String> getProductName(long productId);
}

