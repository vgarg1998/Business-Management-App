package edu.northeastern.bhavyaplasticinventory.doa.interfaces;

import edu.northeastern.bhavyaplasticinventory.doa.entities.Client;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("client/add")
    Call<Object> addClient(@Body Client client);
}
