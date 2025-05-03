package edu.northeastern.MrManage.roomApi.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.northeastern.MrManage.roomApi.entities.User;


@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM User WHERE isCustomer = 1")
    LiveData<List<User>> getAllCustomers();

    @Query("SELECT * FROM User Where isCustomer = 0")
    LiveData<List<User>> getAllManufacturers();

    @Query("SELECT name from USER where id = :id ")
    LiveData<String> getUserName(Long id);

}
