package edu.northeastern.MrManage.roomApi;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import edu.northeastern.MrManage.roomApi.dao.DeliveryDao;
import edu.northeastern.MrManage.roomApi.dao.OrderDao;
import edu.northeastern.MrManage.roomApi.dao.ProductDao;
import edu.northeastern.MrManage.roomApi.dao.ShipmentDao;
import edu.northeastern.MrManage.roomApi.dao.StockHistoryDao;
import edu.northeastern.MrManage.roomApi.dao.UserDao;
import edu.northeastern.MrManage.roomApi.entities.Delivery;
import edu.northeastern.MrManage.roomApi.entities.Order;
import edu.northeastern.MrManage.roomApi.entities.Product;
import edu.northeastern.MrManage.roomApi.entities.Shipment;
import edu.northeastern.MrManage.roomApi.entities.StockHistory;
import edu.northeastern.MrManage.roomApi.entities.User;

@Database(entities = {Product.class, Order.class, User.class, Shipment.class, Delivery.class, StockHistory.class}, version = 1)
public abstract class MrManageDatabase extends RoomDatabase {
    public abstract ProductDao productDao();

    public abstract OrderDao orderDao();

    public abstract UserDao userDao();

    public abstract ShipmentDao shipmentDao();

    public abstract DeliveryDao deliveryDao();

    public abstract StockHistoryDao stockHistoryDao();

}
