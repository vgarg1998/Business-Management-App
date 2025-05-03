package edu.northeastern.MrManage.roomApi.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.List;

import javax.inject.Inject;

import edu.northeastern.MrManage.roomApi.dao.DeliveryDao;
import edu.northeastern.MrManage.roomApi.dao.ShipmentDao;
import edu.northeastern.MrManage.roomApi.entities.Delivery;
import edu.northeastern.MrManage.roomApi.entities.DeliveryAndDirectShipments;
import edu.northeastern.MrManage.roomApi.entities.Shipment;


public class DeliveryAndDirectShipmentsRepository {
    DeliveryDao deliveryDao;
    ShipmentDao shipmentDao;

    @Inject
    public DeliveryAndDirectShipmentsRepository(DeliveryDao deliveryDao, ShipmentDao shipmentDao) {
        this.deliveryDao = deliveryDao;
        this.shipmentDao = shipmentDao;
    }

    public LiveData<DeliveryAndDirectShipments> getAllDeliveryAndDirectShipments() {
        MediatorLiveData<DeliveryAndDirectShipments> combinedData = new MediatorLiveData<>();
        LiveData<List<Delivery>> deliveryLiveData = deliveryDao.getAllDeliveries();
        LiveData<List<Shipment>> shipmentLiveData = shipmentDao.getAllDirectShipments();
        combinedData.addSource(deliveryLiveData, updatedDeliveries -> {
            List<Shipment> currentShipments = shipmentLiveData.getValue();
            combinedData.setValue(new DeliveryAndDirectShipments(
                    currentShipments != null ? currentShipments : List.of(),
                    updatedDeliveries
            ));
        });

        combinedData.addSource(shipmentLiveData, updatedShipmentData -> {
            List<Delivery> currentDelivery = deliveryLiveData.getValue();
            combinedData.setValue(new DeliveryAndDirectShipments(
                    updatedShipmentData,
                    currentDelivery != null ? currentDelivery : List.of()
            ));
        });

        return combinedData;
    }
}
