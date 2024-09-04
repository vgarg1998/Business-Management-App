package edu.northeastern.MrManage.doa.interfaces;

import java.util.List;

import edu.northeastern.MrManage.doa.entities.Customer;
import edu.northeastern.MrManage.doa.responses.SuccessResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("customer/add")
    Call<Object> addCustomer(@Body Customer customer);

    @GET("customer/all")
    Call<SuccessResponse<List<Customer>>> getAllClients();
}
