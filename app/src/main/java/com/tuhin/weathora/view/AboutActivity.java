package com.tuhin.weathora.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.tuhin.weathora.R;
import com.tuhin.weathora.widgets.IconImageView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.toolbarAbout)
    Toolbar mToolbar;
    @BindView(R.id.appVersion)
    TextView appVersion;
    @BindView(R.id.developer)
    LinearLayout developer;
    @BindView(R.id.licenses)
    LinearLayout licenses;
    @BindView(R.id.reportBugs)
    LinearLayout reportBugs;
    @BindView(R.id.joinGooglePlusCommunity)
    LinearLayout joinGooglePlusCommunity;
    @BindView(R.id.rateOnGooglePlay)
    LinearLayout rateOnGooglePlay;

    @BindView(R.id.iconInfo)
    IconImageView iconInfo;
    @BindView(R.id.iconDev)
    IconImageView iconDev;
    @BindView(R.id.iconLicenses)
    IconImageView iconLicenses;
    @BindView(R.id.iconBugReport)
    IconImageView iconBugReport;
    @BindView(R.id.iconGooglePlusCommunity)
    IconImageView iconGooglePlusCommunity;
    @BindView(R.id.iconRate)
    IconImageView iconRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        if (mToolbar != null) {
            mToolbar.getMenu().clear();
            setSupportActionBar(mToolbar);
            mToolbar.setTitle(null);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        setUpViews();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    private void setUpViews() {
        setUpAppVersion();
        setUpOnClickListeners();
        String color = "#778899";
        setUpIcons(color);
    }

    private void setUpIcons(String color) {
        iconInfo.initColor(Color.parseColor(color));
        iconDev.initColor(Color.parseColor(color));
        iconLicenses.initColor(Color.parseColor(color));
        iconBugReport.initColor(Color.parseColor(color));
        iconGooglePlusCommunity.initColor(Color.parseColor(color));
        iconRate.initColor(Color.parseColor(color));
    }

    private void setUpAppVersion() {
        appVersion.setText(getCurrentVersionName(this));
    }

    private void setUpOnClickListeners() {
        licenses.setOnClickListener(this);
        developer.setOnClickListener(this);
        reportBugs.setOnClickListener(this);
        joinGooglePlusCommunity.setOnClickListener(this);
        rateOnGooglePlay.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static String getCurrentVersionName(@NonNull final Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "0.0.0";
    }

    @Override
    public void onClick(View v) {
        if (v == licenses) {
            underConstruction();
        } else if (v == developer) {
            showDevDialog();
        } else if (v == reportBugs) {
            sendMail();
        } else if (v == joinGooglePlusCommunity) {
            underConstruction();
        } else if (v == rateOnGooglePlay) {
            underConstruction();
        }
    }

    private void underConstruction() {
        Toast.makeText(this, getText(R.string.label_underconstruction), Toast.LENGTH_SHORT).show();
    }

    private void showDevDialog() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:CnJ")));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/search?q=pub:CnJ")));
        }
    }

    private void sendMail() {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/html");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "tuhin.sh42@gmail.com" });
            intent.putExtra(Intent.EXTRA_SUBJECT, this.getString(R.string.email_subject));
            intent.putExtra(Intent.EXTRA_TEXT, this.getString(R.string.email_body));
            startActivity(Intent.createChooser(intent, this.getString(R.string.contact_dev)));
        } catch (Exception e) {e.printStackTrace();}
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
