package com.tuhin.weathora.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.tuhin.weathora.R;
import com.tuhin.weathora.app.AppController;
import com.tuhin.weathora.utils.Constants;
import com.tuhin.weathora.view.fragments.BPreferenceFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {
    private static boolean isPrefValueChanged = false;
    @BindView(R.id.toolbarSettings)
    Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        if (mToolbar != null) {
            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrame, new BPreferenceFragment()).commit();
        AppController.getPreferencesEditor().putBoolean(Constants.KEY_PREF_VALUE_CHANGED, false);
        AppController.getPreferencesEditor().apply();
    }

    @Override
    public void onBackPressed() {
        isPrefValueChanged = AppController.getPreferences().getBoolean(Constants.KEY_PREF_VALUE_CHANGED, false);
        if (isPrefValueChanged)
            setResult(RESULT_OK);
        else
            setResult(RESULT_CANCELED);
        finish();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}