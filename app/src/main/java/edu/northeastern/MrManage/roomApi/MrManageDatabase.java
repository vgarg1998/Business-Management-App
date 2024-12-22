package edu.northeastern.MrManage.roomApi;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import edu.northeastern.MrManage.roomApi.dao.OrderDao;
import edu.northeastern.MrManage.roomApi.dao.ProductDao;
import edu.northeastern.MrManage.roomApi.dao.ShipmentDao;
import edu.northeastern.MrManage.roomApi.dao.UserDao;
import edu.northeastern.MrManage.roomApi.entities.Order;
import edu.northeastern.MrManage.roomApi.entities.Product;
import edu.northeastern.MrManage.roomApi.entities.Shipment;
import edu.northeastern.MrManage.roomApi.entities.User;

@Database(entities = {Product.class, Order.class, User.class, Shipment.class},version=1)
public abstract class MrManageDatabase extends RoomDatabase {
    private static volatile MrManageDatabase INSTANCE;

    public abstract ProductDao productDao();

    public abstract OrderDao orderDao();

    public abstract UserDao userDao();

    public abstract ShipmentDao shipmentDao();

    public static MrManageDatabase getINSTANCE(Context context){
        if(INSTANCE == null){
            synchronized (MrManageDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),MrManageDatabase.class,"mrmanage").build();
                }
            }
        }
        return INSTANCE;
    }
}
