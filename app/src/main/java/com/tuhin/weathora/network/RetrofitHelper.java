package com.tuhin.weathora.network;

import com.tuhin.weathora.utils.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    /**
     * The CityWeatherService communicates with the OWM api
     */
    public OWMApi getCityWeatherService() {
        final Retrofit retrofit = createRetrofit();
        return retrofit.create(OWMApi.class);
    }

    /**
     * Creates a OkHttpClient instance
     */
    private OkHttpClient createOkHttpClient() {
        final OkHttpClient.Builder httpClient =  new OkHttpClient.Builder();
        return httpClient.build();
    }

    /**
     * Creates a pre configured Retrofit instance
     */
    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient())
                .build();
    }
}
