package edu.northeastern.MrManage.roomApi.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import edu.northeastern.MrManage.roomApi.entities.DeliveryAndDirectShipments;
import edu.northeastern.MrManage.roomApi.repositories.DeliveryAndDirectShipmentsRepository;

public class DeliveryAndDirectShipmentViewModel extends ViewModel {

    DeliveryAndDirectShipmentsRepository deliveryAndDirectShipmentsRepository;
    private final LiveData<DeliveryAndDirectShipments> deliveryAndDirectShipmentsLiveData;

    public DeliveryAndDirectShipmentViewModel(DeliveryAndDirectShipmentsRepository deliveryAndDirectShipmentsRepository) {
        this.deliveryAndDirectShipmentsRepository = deliveryAndDirectShipmentsRepository;
        this.deliveryAndDirectShipmentsLiveData = deliveryAndDirectShipmentsRepository.getAllDeliveryAndDirectShipments();
    }

    public LiveData<DeliveryAndDirectShipments> getDeliveryAndDirectShipments(){
        return deliveryAndDirectShipmentsLiveData;
    }
}
