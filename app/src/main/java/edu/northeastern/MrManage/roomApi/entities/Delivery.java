package edu.northeastern.MrManage.roomApi.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import edu.northeastern.MrManage.roomApi.entities.unified_data_model.UnifiedItem;

@Entity(
        tableName = "delivery",
        foreignKeys = @ForeignKey(
                entity = Product.class,
                parentColumns = "product_id",
                childColumns = "product_id",
                onDelete = ForeignKey.CASCADE
        )
)
public class Delivery implements UnifiedItem {
    @PrimaryKey(autoGenerate = true)
    private long id; // Auto-incremented ID

    @ColumnInfo(name = "product_id")
    private long productId; // Foreign key for Product

    @ColumnInfo(name = "transit_cost")
    private double transitCost; // Transit cost

    @ColumnInfo(name = "quantity")
    private double totalQuantityKg; // Total quantity in kilograms

    @ColumnInfo(name = "number_of_bags")
    private int numberOfBags; // Number of bags

    @ColumnInfo(name = "delivery_date")
    private long date; // Delivery date

    public Delivery() {
    }

    public Delivery(Long productId, double quantity, int numberOfBagsDelivered, double transitCost, long date) {
        this.productId = productId;
        this.totalQuantityKg = quantity;
        this.numberOfBags = numberOfBagsDelivered;
        this.transitCost = transitCost;
        this.date = date;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public double getTransitCost() {
        return transitCost;
    }

    public void setTransitCost(double transitCost) {
        this.transitCost = transitCost;
    }

    public double getTotalQuantityKg() {
        return totalQuantityKg;
    }

    public void setTotalQuantityKg(double totalQuantityKg) {
        this.totalQuantityKg = totalQuantityKg;
    }

    public int getNumberOfBags() {
        return numberOfBags;
    }

    public void setNumberOfBags(int numberOfBags) {
        this.numberOfBags = numberOfBags;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(long deliveryDate) {
        this.date = deliveryDate;
    }
}
