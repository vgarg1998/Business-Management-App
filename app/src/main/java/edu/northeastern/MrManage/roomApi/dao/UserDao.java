package edu.northeastern.MrManage.roomApi.dao;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.northeastern.MrManage.roomApi.entities.User;



@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM User WHERE isCustomer = 1" )
    List<User> getAllCustomers();

    @Query("SELECT * FROM User Where isCustomer = 0")
    List<User> getAllManufacturers();

    @Query("SELECT name from USER where id = :id ")
    String getCustomerName(Long id);
}
