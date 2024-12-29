package edu.northeastern.MrManage.roomApi.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.northeastern.MrManage.roomApi.entities.Delivery;
import edu.northeastern.MrManage.roomApi.repositories.DeliveryRepository;

@HiltViewModel
public class DeliveryViewModel extends ViewModel {


    private DeliveryRepository deliveryRepository;

    private ExecutorService executorService;

    @Inject
    public DeliveryViewModel(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void insertDelivery(Delivery delivery){
        executorService.execute(()->deliveryRepository.insertDelivery(delivery));
    }

    public void updateDelivery(Delivery delivery){
        executorService.execute(()->deliveryRepository.updateDelivery(delivery));
    }


    public LiveData<List<Delivery>> getDeliveriesByProductId(long productId){
        return deliveryRepository.getDeliveriesByProductId(productId);
    }


    public LiveData<List<Delivery>> getDeliveriesByDateRange(String startDate, String endDate){
        return deliveryRepository.getDeliveriesByDateRange(startDate,endDate);
    }


    public LiveData<List<Delivery>> getAllDeliveries(){
        return deliveryRepository.getAllDeliveries();
    }

    public void delete(Delivery delivery) {
        executorService.execute(() -> deliveryRepository.deleteDelivery(delivery));
    }
}

