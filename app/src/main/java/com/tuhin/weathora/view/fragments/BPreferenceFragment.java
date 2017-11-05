package com.tuhin.weathora.view.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.SwitchPreferenceCompat;
import com.tuhin.weathora.R;
import com.tuhin.weathora.app.AppController;
import com.tuhin.weathora.utils.Constants;
import com.tuhin.weathora.view.AboutActivity;

public class BPreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preference_material);
        final SwitchPreferenceCompat showTempInF = (SwitchPreferenceCompat) findPreference("pref_key_temp_type_f");
        showTempInF.setOnPreferenceClickListener(new SwitchPreferenceCompat.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference isChecked) {
                AppController.getPreferencesEditor().putBoolean(Constants.KEY_PREF_VALUE_CHANGED, true);
                AppController.getPreferencesEditor().putBoolean(Constants.KEY_TEMP_TYPE, showTempInF.isChecked());
                AppController.getPreferencesEditor().apply();
                return true;
            }
        });
        PreferenceScreen aboutPref = (PreferenceScreen) findPreference("pref_key_about");
        aboutPref.setOnPreferenceClickListener(new PreferenceScreen.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startAboutActivity();
                return true;
            }
        });
    }

    public void startAboutActivity(){
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    @Override
    public void setDivider(Drawable divider) {
        super.setDivider(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void setDividerHeight(int height) {
        super.setDividerHeight(0);
    }
}