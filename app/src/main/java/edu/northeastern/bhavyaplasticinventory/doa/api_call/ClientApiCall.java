package edu.northeastern.bhavyaplasticinventory.doa.api_call;

import android.util.Log;

import edu.northeastern.bhavyaplasticinventory.doa.auth.RetrofitClient;
import edu.northeastern.bhavyaplasticinventory.doa.entities.Client;
import edu.northeastern.bhavyaplasticinventory.doa.interfaces.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientApiCall {
    private static final String TAG = ClientApiCall.class.getSimpleName();
    public static void addClientCall(Client client){
        String jwt = "dummy token";

        ApiService service = RetrofitClient.getApiService(jwt);

        Call<Object> call = service.addClient(client);

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
}
