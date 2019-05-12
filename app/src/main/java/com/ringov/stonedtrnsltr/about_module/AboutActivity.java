package com.ringov.stonedtrnsltr.about_module;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ringov.stonedtrnsltr.BuildConfig;
import com.ringov.stonedtrnsltr.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.app_title_view)
    TextView appTitleView;

    @BindView(R.id.version_build_view)
    TextView versionBuildView;

    @BindView(R.id.privacy_view)
    TextView privacyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initBar();
        initAppTitle();
        initBuildVersion();
        initPrivacyPolicy();
    }

    private void initBar() {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setTitle(R.string.about_title);
        }
    }

    private void initAppTitle() {
        linkableTextView(appTitleView, R.string.play_market_link, R.string.app_name);
    }

    private void initBuildVersion() {
        versionBuildView.setText(getString(R.string.build_version,
                BuildConfig.VERSION_NAME, String.valueOf(BuildConfig.VERSION_CODE)));
    }

    private void initPrivacyPolicy() {
        linkableTextView(privacyView, R.string.privacy_link, R.string.privacy_text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void linkableTextView(TextView tv, @StringRes int linkRes, @StringRes int textRes) {
        final String privacy = getString(R.string.linkable, getString(linkRes),
                getString(textRes));
        tv.setText(Html.fromHtml(privacy));
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
