package edu.northeastern.MrManage.roomApi.repositories;

import androidx.lifecycle.LiveData;

import javax.inject.Inject;

import edu.northeastern.MrManage.roomApi.dao.ShipmentDao;
import edu.northeastern.MrManage.roomApi.entities.Shipment;

public class ShipmentRepository {

    private ShipmentDao shipmentDao;

    @Inject
    public ShipmentRepository(ShipmentDao shipmentDao) {
        this.shipmentDao = shipmentDao;
    }


    public LiveData<Integer> getNumberOfShipments(Long orderId) {
        return shipmentDao.getNumberOfShipments(orderId);
    }


    public double getReceivedQuantity(Long orderId) {
        return shipmentDao.getReceivedQuantity(orderId);
    }


    public void insert(Shipment shipment) {
        shipmentDao.insert(shipment);
    }


}
