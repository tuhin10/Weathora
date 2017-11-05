package com.tuhin.weathora.contract;

import com.tuhin.weathora.model.WeatherDataModel;

public interface HomeMVP {

    interface HomeView {
        void showDataLoadingView();
        void hideDataLoadingView();
        void setViewData(WeatherDataModel data);
        void showErrorMessageView(int msgId);
        void showErrorMessageView(String msg);
        String getLastSavedQuery();
        String getOwmAppId();
    }

    interface HomePresenter {
        void requestWeatherDetails(String query, String appId);
        void onDestroy();
    }
}
