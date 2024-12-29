package edu.northeastern.MrManage.roomApi.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Shipment", foreignKeys = {
        @ForeignKey(
                entity = Order.class,
                parentColumns = "order_id",
                childColumns = "order_id",
                onDelete = ForeignKey.CASCADE)
})
public class Shipment {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "shipment_id")
    private Long shipmentId;

    @ColumnInfo(name = "order_id")
    private Long orderId;
    @ColumnInfo(name = "quantity") //weight
    private double quantity;

    @ColumnInfo(name = "number_of_bags")
    private int numberOfBags;

    @ColumnInfo(name = "cost")
    private double cost;

    @ColumnInfo(name = "delivery_date")
    private long date;

    @ColumnInfo(name = "delivery_type")
    private int delivery_type; //1 for customer, 0 for warehouse

    public Shipment(Long orderId, int numberOfBags, double quantity, int delivery_type, int cost) {
        this.orderId = orderId;
        this.numberOfBags = numberOfBags;
        this.quantity = quantity;

        this.cost = cost;
        this.date = System.currentTimeMillis();
        this.delivery_type = delivery_type;
    }

    public Long getOrderId() {
        return orderId;
    }

    public double getQuantity() {
        return quantity;
    }

    public long getDate() {
        return date;
    }

    public int getNumberOfBags() {
        return numberOfBags;
    }

    public void setNumberOfBags(int numberOfBags) {
        this.numberOfBags = numberOfBags;
    }

    public int getDelivery_type() {
        return delivery_type;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDelivery_type(int delivery_type) {
        this.delivery_type = delivery_type;
    }

    public Shipment() {

    }
}


