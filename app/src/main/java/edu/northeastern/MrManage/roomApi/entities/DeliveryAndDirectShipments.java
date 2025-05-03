package edu.northeastern.MrManage.roomApi.entities;

import java.util.List;

public class DeliveryAndDirectShipments {

    List<Shipment> directShipments;
    List<Delivery> deliveries;

    public DeliveryAndDirectShipments(List<Shipment> directShipments, List<Delivery> deliveries) {
        this.directShipments = directShipments;
        this.deliveries = deliveries;
    }

    public List<Shipment> getDirectShipments() {
        return directShipments;
    }

    public void setDirectShipments(List<Shipment> directShipments) {
        this.directShipments = directShipments;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }
}
