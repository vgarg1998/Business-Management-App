package edu.northeastern.MrManage.roomApi.repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import javax.inject.Inject;

import edu.northeastern.MrManage.roomApi.dao.DeliveryDao;
import edu.northeastern.MrManage.roomApi.dao.ProductDao;
import edu.northeastern.MrManage.roomApi.entities.Delivery;

public class DeliveryRepository {
    private DeliveryDao deliveryDao;
    private ProductDao productDao;


    @Inject
    public DeliveryRepository(DeliveryDao deliveryDao, ProductDao productDao) {
        this.deliveryDao = deliveryDao;
        this.productDao = productDao;
    }

    @Transaction
    public void insertDelivery(Delivery delivery){
        deliveryDao.insertDelivery(delivery);
        productDao.updateQuantityInStock(delivery.getProductId(),-delivery.getTotalQuantityKg());
        productDao.updateNumberOfBagsInStock(delivery.getProductId(),-delivery.getNumberOfBags());
        productDao.updateQuantityDelivered(delivery.getProductId(),delivery.getTotalQuantityKg());
        productDao.updateNumberOfBagsDelivered(delivery.getProductId(), delivery.getNumberOfBags());
    }

    public void updateDelivery(Delivery delivery){
        deliveryDao.updateDelivery(delivery);
    }

    public void deleteDelivery(long deliveryId){
        deliveryDao.deleteDelivery(deliveryId);
    }


    public LiveData<List<Delivery>> getDeliveriesByProductId(long productId){
        return deliveryDao.getDeliveriesByProductId(productId);
    }


    public LiveData<List<Delivery>> getDeliveriesByDateRange(String startDate, String endDate){
        return deliveryDao.getDeliveriesByDateRange(startDate,endDate);
    }


    public LiveData<List<Delivery>> getAllDeliveries(){
        return deliveryDao.getAllDeliveries();
    }
}
