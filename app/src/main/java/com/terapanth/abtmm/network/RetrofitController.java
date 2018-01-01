package com.terapanth.abtmm.network;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.terapanth.abtmm.commons.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitController {

    private static RetrofitController retrofitInstance;
    private JacksonConverterFactory jacksonConverterFactory;
    private final OkHttpClient okHttpClient;

    private RetrofitController() {

        //Make http client
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Constants.OKHTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.OKHTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
                .followRedirects(false)
                .addInterceptor(new HeaderInterceptor())
                .build();

        //Make JSON converter
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jacksonConverterFactory = JacksonConverterFactory.create(objectMapper);
    }

    public static RetrofitController getInstance() {
        if(retrofitInstance == null) {
            synchronized (RetrofitController.class) {
                if(retrofitInstance == null) {
                    retrofitInstance = new RetrofitController();
                }
            }
        }
        return retrofitInstance;
    }

    private class HeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request newRequest = request.newBuilder()
                    .build();
            return chain.proceed(newRequest);
        }
    }

    public AccessApi getAccessDetails() {
        Retrofit retrofit = getRetrofitBuilder("");

        AccessApi accessApi = retrofit.create(AccessApi.class);
        return accessApi;
    }

    private Retrofit getRetrofitBuilder(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addConverterFactory(jacksonConverterFactory)
                .build();

        return retrofit;
    }

}
