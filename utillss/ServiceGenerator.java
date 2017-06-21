package com.getfreerecharge.trainschedule.utillss;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Amit Kumar Tiwar on 25/07/16.
 */
public class ServiceGenerator {

    public static final String BASE_URL = "http://api.railwayapi.com/";

    public static final String BASE_URL_ONE = "http://mpaisa.info/FindRegistration.svc/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass)
    {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    private static OkHttpClient.Builder htttpclientone = new OkHttpClient.Builder();

    private static Retrofit.Builder builder_one = new Retrofit.Builder().baseUrl(BASE_URL_ONE)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createServiceOne(Class<S> serviceClass)
    {
        Retrofit retrofit = builder_one.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }


}
