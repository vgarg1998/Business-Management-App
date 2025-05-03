package edu.northeastern.MrManage.roomApi.repositories;


import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import edu.northeastern.MrManage.roomApi.dao.UserDao;
import edu.northeastern.MrManage.roomApi.entities.User;

public class UserRepository {
    private final UserDao userDao;

    @Inject
    public UserRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    public LiveData<List<User>> getAllCustomers() {
        return userDao.getAllCustomers();
    }

    public LiveData<List<User>> getAllManufacturers() {
        return userDao.getAllManufacturers();
    }


    public void insertUser(User user) {
        userDao.insertUser(user);
    }

    public LiveData<String> getUserName(Long id) {
        return userDao.getUserName(id);
    }
}
