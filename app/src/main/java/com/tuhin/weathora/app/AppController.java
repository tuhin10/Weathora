package com.tuhin.weathora.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.squareup.leakcanary.LeakCanary;

public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();
    public static volatile Context applicationContext;
    private static SharedPreferences mPrefs;
    private static SharedPreferences.Editor mPrefsEditor;
    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        LeakCanary.install(this);
        mInstance = this;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mPrefsEditor = mPrefs.edit();
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    //We should use dependency injection to get SharedPreferences instance
    public static synchronized SharedPreferences getPreferences(){
        if (mPrefs == null){
            mPrefs = PreferenceManager.getDefaultSharedPreferences(mInstance);
        }
        return mPrefs;
    }

    //We should use dependency injection to get SharedPreferences.Editor instance
    public static synchronized SharedPreferences.Editor getPreferencesEditor(){
        if (mPrefsEditor == null){
            mPrefsEditor = mPrefs.edit();
        }
        return mPrefsEditor;
    }
}
