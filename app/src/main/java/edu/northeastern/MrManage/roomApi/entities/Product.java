package edu.northeastern.MrManage.roomApi.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Product", foreignKeys = @ForeignKey(
        entity = User.class,
        parentColumns = "id",
        childColumns = "customer_id",
        onDelete = ForeignKey.CASCADE
))
public class Product {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id")
    private Long productId;

    @ColumnInfo(name = "product_name")
    private String productName;

    @ColumnInfo(name = "product_type")
    private String type;

    @ColumnInfo(name = "customer_id")
    private Long customerId;

    @ColumnInfo(name = "quantity_in_inventory")
    private Double quantityInInventor = 0.0;

    @ColumnInfo(name = "quantity_delivered")
    private Double quantityDelivered = 0.0;

    @ColumnInfo(name = "quantity_in_order")
    private Double quantityInOrder = 0.0;

    @ColumnInfo(name = "number_of_bags_delivered")
    private Long numberOfBagsDelivered = 0L;

    @ColumnInfo(name = "number_of_bags_in_stock")
    private long numberOfBagsInStock = 0L;

    public Long getNumberOfBagsDelivered() {
        return numberOfBagsDelivered;
    }

    public void setNumberOfBagsDelivered(Long numberOfBagsDelivered) {
        this.numberOfBagsDelivered = numberOfBagsDelivered;
    }


    public Product() {
    }


    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getType() {
        return type;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Double getQuantityInInventor() {
        return quantityInInventor;
    }

    public Double getQuantityDelivered() {
        return quantityDelivered;
    }

    public Double getQuantityInOrder() {
        return quantityInOrder;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setQuantityInInventor(Double quantityInInventor) {
        this.quantityInInventor = quantityInInventor;
    }

    public void setQuantityDelivered(Double quantityDelivered) {
        this.quantityDelivered = quantityDelivered;
    }

    public void setQuantityInOrder(Double quantityInOrder) {
        this.quantityInOrder = quantityInOrder;
    }

    public long getNumberOfBagsInStock() {
        return numberOfBagsInStock;
    }

    public void setNumberOfBagsInStock(long numberOfBagsInStock) {
        this.numberOfBagsInStock = numberOfBagsInStock;
    }


    public Product(String productName, String type, Long customerId) {
        this.productName = productName;
        this.type = type;
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return productName;
    }
}
