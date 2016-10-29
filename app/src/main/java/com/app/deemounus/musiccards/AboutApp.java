package com.app.deemounus.musiccards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AboutApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
    }

    public void showLicensePressed(View view) {
        Utils.showLicenseDialog(AboutApp.this, R.raw.license);
    }

    public void showLibrariesPressed(View view) {
        Utils.showLicenseDialog(AboutApp.this, R.raw.license_libraries);
    }
}
