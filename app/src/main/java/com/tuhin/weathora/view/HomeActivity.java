package com.tuhin.weathora.view;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.tuhin.weathora.BuildConfig;
import com.tuhin.weathora.R;
import com.tuhin.weathora.app.AppController;
import com.tuhin.weathora.contract.HomeMVP;
import com.tuhin.weathora.model.WeatherDataModel;
import com.tuhin.weathora.presenter.HomePresenter;
import com.tuhin.weathora.utils.Constants;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeMVP.HomeView {
    private static final String TAG = "HomeActivity";
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 101;
    private int SETTINGS_ACTIVITY_REQUEST_CODE = 102;
    @BindView(R.id.parentViewGrp)
    View parentViewGrp;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.weatherIcon)
    ImageView mWeatherIconIV;
    @BindView(R.id.toolbarTitle)
    TextView mLocationTitleTv;
    @BindView(R.id.todayTemperature)
    TextView mTodayTemperatureTv;
    @BindView(R.id.todayDescription)
    TextView mTodayDescriptionTv;
    @BindView(R.id.todayWind)
    TextView mTodayWindTv;
    @BindView(R.id.todayPressure)
    TextView mTodayPressureTv;
    @BindView(R.id.todayHumidity)
    TextView mTodayHumidityTv;
    @BindView(R.id.todaySunrise)
    TextView mTodaySunriseTv;
    @BindView(R.id.todaySunset)
    TextView mTodaySunsetTv;
    @BindView(R.id.progressBar)
    ProgressBar mLoadingDataPB;

    private HomeMVP.HomePresenter mHomePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        //hiding the default toolbar title as we have added a custom textview in the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        mHomePresenter = new HomePresenter(this);
        String query = getLastSavedQuery();
        mHomePresenter.requestWeatherDetails(query, getOwmAppId());
    }

    @Override
    protected void onDestroy() {
        mHomePresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            refreshLastCityData();
            return true;
        }
        if (id == R.id.action_search) {
            searchCities();
            return true;
        }
        if (id == R.id.action_settings) {
            startSettingsActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startSettingsActivity() {
        Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
        startActivityForResult(intent, SETTINGS_ACTIVITY_REQUEST_CODE);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    private void searchCities() {
        try {
            //As we are only interested in cities in US, creating the filter to be applied in PlaceAutocomplete
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                    .setCountry("US")
                    .build();
            //We are using PlaceAutocomplete to fetch the list of US cities as the user types the name of the city
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .setFilter(typeFilter)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            showErrorMessageView(e.getMessage());
        } catch (GooglePlayServicesNotAvailableException e) {
            showErrorMessageView(e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                LatLng coordinates = place.getLatLng(); //Get the coordinates from place object
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = null; //Only retrieve first address
                try {
                    addresses = geocoder.getFromLocation(
                            coordinates.latitude,
                            coordinates.longitude,
                            1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Address address = addresses.get(0);
                if (BuildConfig.DEBUG) {
                    Log.wtf(TAG, "Place: " + place.getName());
                    Log.wtf(TAG, "Country Code: " + address.getCountryCode());
                    Log.wtf(TAG, "Country Name: " + address.getCountryName());
                    Log.wtf(TAG, "Locality: " + address.getLocality());
                    Log.wtf(TAG, "AdminArea : " + address.getAdminArea());
                }
                String countryCode = address.getCountryCode().toLowerCase();
                String state = address.getAdminArea().toLowerCase();
                String city = place.getName().toString().toLowerCase();
                String query = getString(R.string.weather_query_to_format, city, state, countryCode);
                AppController.getPreferencesEditor().putString(Constants.LAST_SEARCHED_QUERY, query);
                AppController.getPreferencesEditor().apply();
                //save the last query as we have to load the last searched city data upon app lunch
                mHomePresenter.requestWeatherDetails(query, getOwmAppId());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                showErrorMessageView(status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                showErrorMessageView(getString(R.string.action_search_cancelled));
            }
        } else if (requestCode == SETTINGS_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                refreshLastCityData();
            }
        }
    }

    @Override
    public String getLastSavedQuery() {
        return AppController.getPreferences()
                .getString(Constants.LAST_SEARCHED_QUERY, Constants.DEFAULT_SEARCHED_QUERY);
    }

    @Override
    public String getOwmAppId() {
        return this.getResources().getString(R.string.owm_api_key);
    }

    private void refreshLastCityData() {
        String query = AppController.getPreferences().
                getString(Constants.LAST_SEARCHED_QUERY, Constants.DEFAULT_SEARCHED_QUERY);
        mHomePresenter.requestWeatherDetails(query, getOwmAppId());
    }

    @Override
    public void showDataLoadingView() {
        mLoadingDataPB.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDataLoadingView() {
        mLoadingDataPB.setVisibility(View.INVISIBLE);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setViewData(WeatherDataModel data) {
        mLocationTitleTv.setText(Html.fromHtml(data.getCityName()));
        //We should use dependency injection to get SharedPreferences instance
        boolean isTempInF = AppController.getPreferences().getBoolean(Constants.KEY_TEMP_TYPE, true);
        int tempStrResId = isTempInF?R.string.label_temp_f:R.string.label_temp_c;
        String todayTemperature = getString(tempStrResId, data.getTodayTemperature());
        mTodayTemperatureTv.setText(Html.fromHtml(todayTemperature));
        mTodayDescriptionTv.setText(Html.fromHtml(data.getTodayDescription()));
        String todayWind = getString(R.string.label_wind, data.getTodayWind());
        mTodayWindTv.setText(Html.fromHtml(todayWind));
        String todayPressure = getString(R.string.label_pressure, data.getTodayPressure());
        mTodayPressureTv.setText(Html.fromHtml(todayPressure));
        String todayHumidity = getString(R.string.label_humidity, data.getTodayHumidity());
        mTodayHumidityTv.setText(Html.fromHtml(todayHumidity));
        String todaySunrise = getString(R.string.label_sunrise, data.getTodaySunrise());
        mTodaySunriseTv.setText(Html.fromHtml(todaySunrise));
        String todaySunset = getString(R.string.label_sunset, data.getTodaySunset());
        mTodaySunsetTv.setText(Html.fromHtml(todaySunset));
        String query = getString(R.string.weather_icon_query_to_format, data.getWeatherIcon());
        String weatherIconUrl = Constants.WEATHER_ICON_BASE_URL+query;
        Glide.with(this)
                .load(weatherIconUrl)
                .error(R.drawable.ic_cloud_white)
                .placeholder(R.drawable.ic_cloud_white)
                .into(mWeatherIconIV);
    }

    @Override
    public void showErrorMessageView(int msgId) {
        Snackbar.make(parentViewGrp, getString(msgId), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showErrorMessageView(String msg) {
        Snackbar.make(parentViewGrp, msg, Snackbar.LENGTH_LONG).show();
    }
}
