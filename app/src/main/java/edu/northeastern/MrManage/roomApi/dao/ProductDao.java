package edu.northeastern.MrManage.roomApi.dao;

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

    @Query("Select * from Product where customer_id = :customerId" )
    List<Product> getProducts(Long customerId);

    @Query("UPDATE Product SET quantity_in_order = :quantity + quantity_in_order WHERE product_id = :productId")
    void updateOrderedQuantity(String productId, String quantity);

    @Query("SELECT product_name as productName, customer_id as customerId FROM Product WHERE product_id = :productId")
    ProductCustomer getProductName(Long productId);


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


}

