package edu.northeastern.MrManage.utility;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import edu.northeastern.MrManage.roomApi.MrManageDatabase;
import edu.northeastern.MrManage.roomApi.dao.DeliveryDao;
import edu.northeastern.MrManage.roomApi.dao.OrderDao;
import edu.northeastern.MrManage.roomApi.dao.ProductDao;
import edu.northeastern.MrManage.roomApi.dao.ShipmentDao;
import edu.northeastern.MrManage.roomApi.dao.StockHistoryDao;
import edu.northeastern.MrManage.roomApi.dao.UserDao;
import edu.northeastern.MrManage.roomApi.repositories.DeliveryRepository;
import edu.northeastern.MrManage.roomApi.repositories.OrderRepository;
import edu.northeastern.MrManage.roomApi.repositories.ProductRepository;
import edu.northeastern.MrManage.roomApi.repositories.ShipmentRepository;
import edu.northeastern.MrManage.roomApi.repositories.StockHistoryRepository;
import edu.northeastern.MrManage.roomApi.repositories.UserRepository;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    @Singleton
    public static MrManageDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, MrManageDatabase.class, "mr_manage_db").build();
    }

    @Provides
    @Singleton
    public static UserDao provideUserDao(MrManageDatabase database) {
        return database.userDao();
    }

    @Provides
    @Singleton
    public static UserRepository provideUserRepository(UserDao userDao) {
        return new UserRepository(userDao);
    }

    @Provides
    @Singleton
    public static OrderDao provideOrderDao(MrManageDatabase database) {
        return database.orderDao();
    }

    @Provides
    @Singleton
    public static OrderRepository provideOrderRepository(OrderDao orderDao, ProductDao productDao) {
        return new OrderRepository(orderDao, productDao);
    }

    @Provides
    @Singleton
    public static ProductDao provideProductDao(MrManageDatabase database) {
        return database.productDao();
    }

    @Provides
    @Singleton
    public static ProductRepository provideProductRepository(ProductDao productDao) {
        return new ProductRepository(productDao);
    }

    @Provides
    @Singleton
    public static ShipmentDao provideShipmentDao(MrManageDatabase database) {
        return database.shipmentDao();
    }

    @Provides
    @Singleton
    public static ShipmentRepository provideShipmentRepository(ShipmentDao shipmentDao) {
        return new ShipmentRepository(shipmentDao);
    }

    @Provides
    @Singleton
    public static DeliveryDao provideDeliveryDao(MrManageDatabase database) {
        return database.deliveryDao();
    }

    @Provides
    @Singleton
    public static DeliveryRepository provideDeliveryRepository(DeliveryDao deliveryDao, ProductDao productDao) {
        return new DeliveryRepository(deliveryDao, productDao);
    }

    @Provides
    @Singleton
    public static StockHistoryDao provideStockHistoryDao(MrManageDatabase database) {
        return database.stockHistoryDao();
    }

    @Provides
    @Singleton
    public static StockHistoryRepository provideStockHistoryRepository(StockHistoryDao stockHistoryDao, ProductDao productDao) {
        return new StockHistoryRepository(stockHistoryDao, productDao);
    }



}
