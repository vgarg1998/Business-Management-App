package edu.northeastern.MrManage.doa.api_call;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.northeastern.MrManage.doa.auth.RetrofitClient;
import edu.northeastern.MrManage.doa.entities.Customer;
import edu.northeastern.MrManage.doa.interfaces.ApiService;
import edu.northeastern.MrManage.doa.responses.SuccessResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerViewModel extends ViewModel {
    private static final String TAG = CustomerViewModel.class.getSimpleName();
    static String jwt = "dummy token";
    private  final MutableLiveData<List<Customer>> customerLiveData;
    private MutableLiveData<String> errorLiveData;
    private static ApiService service;
    public CustomerViewModel() {


        service = RetrofitClient.getApiService(jwt);
        this.customerLiveData = new MutableLiveData<>();
        this.errorLiveData = new MutableLiveData<>();
    }

    public static void addCustomerCall(Customer customer){
        if(service == null){
            service = RetrofitClient.getApiService(jwt);
        }

        Call<Object> call = service.addCustomer(customer);

        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "Customer added successfully");
                }else{
                    Log.e(TAG, "Error in adding customer");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });

    }

    private void getAllCustomers(){

        Call<SuccessResponse<List<Customer>>> call = service.getAllClients();
        call.enqueue(new Callback<SuccessResponse<List<Customer>>>() {
            @Override
            public void onResponse(Call<SuccessResponse<List<Customer>>> call, Response<SuccessResponse<List<Customer>>> response) {
                if(response !=null && response.isSuccessful()){
                    SuccessResponse<List<Customer>> successResponse = response.body();
                    if (successResponse != null) {
                        customerLiveData.setValue(successResponse.getData());
                    } else {
                        errorLiveData.setValue("Unexpected null response");
                    }
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse<List<Customer>>> call, Throwable t) {

            }
        });

    }

    public LiveData<List<Customer>> getCustomers() {
        getAllCustomers();
        return customerLiveData;
    }

    public LiveData<String> getErrorData(){
        return errorLiveData;
    }
}
