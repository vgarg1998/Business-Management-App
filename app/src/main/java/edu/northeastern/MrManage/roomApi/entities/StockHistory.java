package edu.northeastern.MrManage.roomApi.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "stock_history", foreignKeys = @ForeignKey(
        entity = Product.class,
        parentColumns = "product_id",
        childColumns = "product_id",
        onDelete = ForeignKey.CASCADE
))
public class StockHistory {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "product_id")
    private long productId;
    private int numberOfBags;
    private double quantity;
    private long addedDate;

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

    public int getNumberOfBags() {
        return numberOfBags;
    }

    public void setNumberOfBags(int numberOfBags) {
        this.numberOfBags = numberOfBags;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public long getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(long addedDate) {
        this.addedDate = addedDate;
    }

    // Getters and Setters
}