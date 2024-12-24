package edu.northeastern.MrManage.roomApi.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.northeastern.MrManage.roomApi.entities.Shipment;
import edu.northeastern.MrManage.roomApi.repositories.ShipmentRepository;

@HiltViewModel
public class ShipmentViewModel extends ViewModel {

    private final ExecutorService executorService;

    private final ShipmentRepository shipmentRepository;


    @Inject
    public ShipmentViewModel(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
        this.executorService = Executors.newSingleThreadExecutor();
    }


    public LiveData<Integer> getNumberOfShipments(Long orderId) {
        return shipmentRepository.getNumberOfShipments(orderId);
    }

    public double getReceivedQuantity(Long orderId) {
        return shipmentRepository.getReceivedQuantity(orderId);
    }


    public void insert(Shipment shipment) {
        executorService.submit(() -> shipmentRepository.insert(shipment));
    }
}
