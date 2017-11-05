package com.tuhin.weathora.network;

import com.tuhin.weathora.network.models.WeatherResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

//Retrofit OWMApi interface to query OWM weather service
public interface OWMApi {
    @GET("data/2.5/weather")
    Single<WeatherResponse> queryWeatherByCityName(@Query("q") String query,
                                                   @Query("APPID") String appId);
}
