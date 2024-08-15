package edu.northeastern.bhavyaplasticinventory.doa.auth;

import edu.northeastern.bhavyaplasticinventory.doa.interfaces.ApiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance(String token){
        if(retrofit == null){
            //creating okhttpclient and attaching interceptor
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new AuthInterceptor(token)).build();
            retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8080/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }

    public static ApiService getApiService(String token){
        return getRetrofitInstance(token).create(ApiService.class);
    }

}
