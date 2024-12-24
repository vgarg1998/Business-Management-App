package edu.northeastern.MrManage.roomApi.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.northeastern.MrManage.roomApi.entities.User;
import edu.northeastern.MrManage.roomApi.repositories.UserRepository;


@HiltViewModel
public class UserViewModel extends ViewModel {
    private final UserRepository userRepository;

    private final ExecutorService executorService;

    @Inject
    public UserViewModel(UserRepository repository) {
        this.userRepository = repository;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<User>> getAllManufacturers() {
        return userRepository.getAllManufacturers();
    }

    public LiveData<List<User>> getAllCustomer() {
        return userRepository.getAllCustomers();
    }

    public void insertUser(User user) {
        executorService.execute(() -> userRepository.insertUser(user));
    }

    public LiveData<String> getUserName(Long id) {
        return userRepository.getUserName(id);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}
