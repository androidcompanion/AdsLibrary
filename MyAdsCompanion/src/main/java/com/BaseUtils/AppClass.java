package com.BaseUtils;

import android.app.Application;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;

public class AppClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(this);
        AudienceNetworkAds.initialize(this);
    }
}
