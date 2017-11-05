package com.tuhin.weathora.presenter;

import android.support.annotation.NonNull;
import com.tuhin.weathora.R;
import com.tuhin.weathora.app.AppController;
import com.tuhin.weathora.contract.HomeMVP;
import com.tuhin.weathora.model.WeatherDataModel;
import com.tuhin.weathora.network.OWMApi;
import com.tuhin.weathora.network.RetrofitHelper;
import com.tuhin.weathora.network.models.Weather;
import com.tuhin.weathora.network.models.WeatherResponse;
import com.tuhin.weathora.utils.Constants;
import com.tuhin.weathora.utils.UnitConvertor;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter implements HomeMVP.HomePresenter{
    private HomeMVP.HomeView homeView;
    /**
     * Collects all subscriptions to unsubscribe on destroy
     */
    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /**
     * We will query city weather with this service
     */
    @NonNull
    private OWMApi mCityWeatherService;

    public HomePresenter(HomeMVP.HomeView homeView){
        this.homeView = homeView;
        // Initialize the city weather service
        mCityWeatherService = new RetrofitHelper().getCityWeatherService();
    }

    @Override
    public void requestWeatherDetails(String query, String appId) {
        if ((query != null && !query.isEmpty()) && (appId != null && !appId.isEmpty())){
            fetchWeatherDetails(query,appId);
        } else {
            homeView.showErrorMessageView(R.string.invalid_city);
        }
    }

    public void fetchWeatherDetails(@NonNull String query,@NonNull String appId) {
        homeView.showDataLoadingView();
        mCityWeatherService.queryWeatherByCityName(query, appId)
                .subscribeOn(Schedulers.io()) // execute the network call on io thread
                .map(new Function<WeatherResponse, WeatherDataModel>() {
                    @Override
                    public WeatherDataModel apply(
                            @io.reactivex.annotations.NonNull final WeatherResponse weathrResponse){
                        WeatherDataModel model = new WeatherDataModel();
                        Weather[] weatherArray = weathrResponse.getWeather();
                        Weather weather = weatherArray[0];
                        //As per sample api output we will get atleast one
                        // item in the weather json array, but we have to handle the edge cases in future
                        String cityName = weathrResponse.getName();
                        model.setCityName(cityName);
                        boolean isTempInF = AppController.getPreferences().getBoolean(Constants.KEY_TEMP_TYPE, true);
                        float todayTemperature;
                        if (isTempInF)
                            todayTemperature = UnitConvertor.kelvinToFahrenheit(weathrResponse.getMain().getTemp());
                        else
                            todayTemperature = UnitConvertor.kelvinToCelsius(weathrResponse.getMain().getTemp());
                        model.setTodayTemperature(todayTemperature);
                        String todayDescription = UnitConvertor.capitalizeString(weather.getDescription());
                        model.setTodayDescription(todayDescription);
                        double todayWind = UnitConvertor.convertWind(weathrResponse.getWind().getSpeed());
                        model.setTodayWind(todayWind);
                        float todayPressure = UnitConvertor.convertPressure(weathrResponse.getMain().getPressure());
                        model.setTodayPressure(todayPressure);
                        String todayHumidity = weathrResponse.getMain().getHumidity();
                        model.setTodayHumidity(todayHumidity);
                        String todaySunrise = UnitConvertor.formatDate(weathrResponse.getSys().getSunrise());
                        model.setTodaySunrise(todaySunrise);
                        String todaySunset = UnitConvertor.formatDate(weathrResponse.getSys().getSunset());
                        model.setTodaySunset(todaySunset);
                        String weatherIcon = weather.getIcon();
                        model.setWeatherIcon(weatherIcon);
                        //we could have create the WeatherDataModel object by passing the
                        // values directly into its constructor as well, but this is more readable
                        return model;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) // deliver the response on UIThread
                .subscribe(new SingleObserver<WeatherDataModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(WeatherDataModel weathrResponse) {
                        homeView.setViewData(weathrResponse);
                        homeView.hideDataLoadingView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        homeView.showErrorMessageView(e.getMessage());
                        homeView.hideDataLoadingView();
                    }
                });
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.clear();
    }
}
