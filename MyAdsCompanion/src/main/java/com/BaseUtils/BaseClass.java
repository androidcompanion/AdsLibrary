package com.BaseUtils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.AdsConfig.DefaultAdsIds;
import com.ModelsCompanion.AdsData;
import com.ModelsCompanion.AdsIdsList;
import com.ModelsCompanion.AdsPrefernce;
import com.ModelsCompanion.GsonUtils;
import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myadscompanion.R;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.ads.banner.BannerListener;
import com.startapp.sdk.adsbase.AutoInterstitialPreferences;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Callable;

import cz.msebera.android.httpclient.Header;

public class BaseClass extends AppCompatActivity {


    public RelativeLayout lay_notification;
    public TextView tv_not_text;
    public ImageView iv_not_close;

    public static boolean checkAppService = true;
     public Dialog serviceDialog;

    private long exitTime = 0;

    public DefaultAdsIds defaultAdsIds;

    public static boolean isAdsAvailable = false;
    public AdsPrefernce adsPrefernce;
    ArrayList<AdsIdsList> adsList;
    public GsonUtils gsonUtils;

    public InterstitialAd fbSplashInter;
    public com.google.android.gms.ads.InterstitialAd gSplashInter;
    public InterstitialAd fbInterstitial1;
    public com.google.android.gms.ads.InterstitialAd gInterstitial1;
    public InterstitialAd fbInterstitial2;
    public com.google.android.gms.ads.InterstitialAd gInterstitial2;
    public InterstitialAd fbInterstitial3;
    public com.google.android.gms.ads.InterstitialAd gInterstitial3;
    com.facebook.ads.InterstitialAd fbDialogInterstitial;
    com.google.android.gms.ads.InterstitialAd gDialogInterstitial;

    public ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        defaultAdsIds = new DefaultAdsIds(this);
        adsPrefernce = new AdsPrefernce(this);

        StartAppSDK.init((Activity) this, defaultAdsIds.SA_APP_ID(), false);
        if (defaultAdsIds.DISABLE_SPLASH()){
            StartAppAd.disableSplash();
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isNetworkAvailable(BaseClass.this)) {
                    if (!isAdsAvailable) {
                        getAds(defaultAdsIds.APP_KEY());
                    }
                }
            }
        }, 500);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void getAds(String appName) {

        adsList = new ArrayList<AdsIdsList>();
        AsyncHttpClient client = new AsyncHttpClient();
        gsonUtils = GsonUtils.getInstance();
        RequestParams params1 = new RequestParams();
        params1.put("app_name", appName);
        try {
            client.setConnectTimeout(50000);

            client.post("http://api.get-fans-for-musically.com/service/ads_service.php", params1, new BaseJsonHttpResponseHandler<AdsData>() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, AdsData response) {

                    adsList.clear();
                    adsList.addAll(response.getAdsIdsList());

                    AdsIdsList ads = adsList.get(0);

                    //add data to shared_preference
                    adsPrefernce = new AdsPrefernce(BaseClass.this);
                    adsPrefernce.setAdsDefaults(ads.getShowAds(), ads.getUseStaticIds(), ads.getShowNativeAd(), ads.getGAppId(),
                            ads.getUseStaticGappId(), ads.getGBanner1(), ads.getShowGBanner1(), ads.getGBanner2(), ads.getShowGBanner2(),
                            ads.getGInter1(), ads.getShowGInter1(), ads.getGInter2(), ads.getShowGInter2(), ads.getGInter3(), ads.getShowGInter3(),
                            ads.getGRewarded1(), ads.getShowGRewarded1(), ads.getGRewarded2(), ads.getShowGRewarded2(),
                            ads.getGNative1(), ads.getShowGNative1(), ads.getGNative2(), ads.getShowGNative2(),
                            ads.getFbBanner1(), ads.getShowFbBanner1(), ads.getFbBanner2(), ads.getShowFbBanner2(),
                            ads.getFbInter1(), ads.getShowFbInter1(), ads.getFbInter2(), ads.getShowFbInter2(), ads.getFbInter3(), ads.getShowFbInter3(),
                            ads.getFbRewarded(), ads.getShowFbRewarded(), ads.getFbNative1(), ads.getShowFbNative1(), ads.getFbNative2(), ads.getShowFbNative2(),
                            ads.getFbNativeBanner(), ads.getShowFbNativeBanner(), ads.getFbMedRectangle(), ads.getShowFbMedRectangle()
                            , ads.getExtraBooleanPara1(), ads.getExtraBooleanPara2(),
                            ads.getExtraStringPara1(), ads.getExtraStringPara2());

                    if (adsPrefernce.showStartApp()) {
                        StartAppAd.enableAutoInterstitial();

                        adsPrefernce = new AdsPrefernce(BaseClass.this);
                        adsPrefernce.setAdsDefaults("0", "1", "na", "na",
                                "1", "na", "0", "na", "0", "na", "0", "na", "0", "na",
                                "0", "na", "0", "na", "0", "na", "0", "na", "0", "na",
                                "0", "na", "0", "na", "0", "na", "0", "na", "0", "na",
                                "0", "na", "0", "na", "0", "na", "0", "na", "0", "1",
                                ads.getExtraBooleanPara2(), ads.getExtraStringPara1(), ads.getExtraStringPara2());
                        StartAppAd.setAutoInterstitialPreferences(
                                new AutoInterstitialPreferences()
                                        .setActivitiesBetweenAds(Integer.parseInt(adsPrefernce.adCountSA()))
                        );
                    }

                    isAdsAvailable = true;

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, AdsData errorResponse) {
                    isAdsAvailable = false;

                }

                @Override
                protected AdsData parseResponse(String rawJsonData, boolean isFailure) throws Throwable {

                    try {
                        if (!isFailure && !rawJsonData.isEmpty()) {
                            return gsonUtils.getGson().fromJson(rawJsonData, AdsData.class);
                        }
                    } catch (Exception ignored) {

                    }
                    return null;
                }
            });

        } catch (Exception ignored) {

        }

    }

    public void exitApp() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        finish();
    }

    public void backAgainToExit(){
            if (System.currentTimeMillis() - this.exitTime > 2000) {
                Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
                this.exitTime = System.currentTimeMillis();
                return;
            }
            exitApp();
    }

    private com.google.android.gms.ads.AdSize getAdSize() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        return com.google.android.gms.ads.AdSize.getPortraitAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    public void showBannerAd(Integer top, Integer bottom) {
        if (isNetworkAvailable(this)) {
            if (isAdsAvailable) {
                if (!adsPrefernce.showStartApp()) {
                    if (adsPrefernce.showgBanner1()) {
                        AdView gadView;
                        MobileAds.initialize(this, adsPrefernce.gAppId());
                        final FrameLayout adContainerView = this.findViewById(R.id.banner_container);
                        adContainerView.setVisibility(View.VISIBLE);
                        gadView = new AdView(this);
                        adContainerView.setPadding(0, top, 0, bottom);
                        gadView.setAdUnitId(adsPrefernce.gBannerId1());
                        adContainerView.addView(gadView);
                        AdRequest adRequest = new AdRequest.Builder().build();
                        com.google.android.gms.ads.AdSize adSize = getAdSize();
                        gadView.setAdSize(adSize);
                        gadView.loadAd(adRequest);
                            gadView.setAdListener(new com.google.android.gms.ads.AdListener() {
                                @Override
                                public void onAdLoaded() {
                                    super.onAdLoaded();
                                    adContainerView.setBackground(getResources().getDrawable(R.drawable.bg_banner));
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        getResources().getDrawable(R.drawable.bg_banner).setTint(defaultAdsIds.TINT_COLOR());
                                    }
                                }
                            });
                    } else {
                        com.facebook.ads.AdView adView;
                        if (adsPrefernce.showfbBanner1()) {
                            AudienceNetworkAds.initialize(this);
                            adView = new com.facebook.ads.AdView(this, adsPrefernce.fbBannerId1(), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
                            final FrameLayout adContainerView = findViewById(R.id.banner_container);
                            adContainerView.setVisibility(View.VISIBLE);
                            adContainerView.addView(adView);
                            adContainerView.setPadding(0, top, 0, bottom);
                            adView.loadAd();
                            adView.setAdListener(new AdListener() {
                                @Override
                                public void onError(Ad ad, AdError adError) {

                                }

                                @Override
                                public void onAdLoaded(Ad ad) {
                                    adContainerView.setBackground(getResources().getDrawable(R.drawable.bg_banner));
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        getResources().getDrawable(R.drawable.bg_banner).setTint(defaultAdsIds.TINT_COLOR());
                                    }

                                }

                                @Override
                                public void onAdClicked(Ad ad) {

                                }

                                @Override
                                public void onLoggingImpression(Ad ad) {

                                }
                            });
                        }
                    }
                } else {
                    final FrameLayout adContainerView = (FrameLayout) findViewById(R.id.banner_container);
                    Banner startAppBanner = new Banner(this, new BannerListener() {
                        @Override
                        public void onReceiveAd(View view) {
                            adContainerView.setBackground(getResources().getDrawable(R.drawable.bg_banner));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                getResources().getDrawable(R.drawable.bg_banner).setTint(defaultAdsIds.TINT_COLOR());
                            }
                        }

                        @Override
                        public void onFailedToReceiveAd(View view) {

                        }

                        @Override
                        public void onImpression(View view) {

                        }

                        @Override
                        public void onClick(View view) {

                        }
                    });
                    FrameLayout.LayoutParams bannerParameters =
                            new FrameLayout.LayoutParams(
                                    FrameLayout.LayoutParams.MATCH_PARENT,
                                    FrameLayout.LayoutParams.WRAP_CONTENT);
                    adContainerView.setVisibility(View.VISIBLE);
                    adContainerView.setForegroundGravity(Gravity.CENTER);
                    adContainerView.setPadding(0, top, 0, bottom);
                    adContainerView.addView(startAppBanner, bannerParameters);
                }
            } else {
                com.facebook.ads.AdView adView;
                AudienceNetworkAds.initialize(this);
                adView = new com.facebook.ads.AdView(this, defaultAdsIds.FB_BANNER1(), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
                final FrameLayout adContainerView = findViewById(R.id.banner_container);
                adContainerView.setVisibility(View.VISIBLE);
                adContainerView.setPadding(0, top, 0, bottom);
                adContainerView.addView(adView);
                adView.loadAd();
                adView.setAdListener(new AdListener() {
                    @Override
                    public void onError(Ad ad, AdError adError) {

                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        adContainerView.setBackground(getResources().getDrawable(R.drawable.bg_banner));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getResources().getDrawable(R.drawable.bg_banner).setTint(defaultAdsIds.TINT_COLOR());
                        }

                    }

                    @Override
                    public void onAdClicked(Ad ad) {

                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }
                });

            }
        }
    }

    public void showBannerAd2(Integer top, Integer bottom) {
        if (isNetworkAvailable(this)) {
            if (isAdsAvailable) {
                if (!adsPrefernce.showStartApp()) {
                    if (adsPrefernce.showgBanner2()) {
                        AdView gadView;
                        MobileAds.initialize(this, adsPrefernce.gAppId());
                        final FrameLayout adContainerView = this.findViewById(R.id.banner_container);
                        adContainerView.setVisibility(View.VISIBLE);
                        gadView = new AdView(this);
                        adContainerView.setPadding(0, top, 0, bottom);
                        gadView.setAdUnitId(adsPrefernce.gBannerId2());
                        adContainerView.addView(gadView);
                        AdRequest adRequest = new AdRequest.Builder().build();
                        com.google.android.gms.ads.AdSize adSize = getAdSize();
                        gadView.setAdSize(adSize);
                        gadView.loadAd(adRequest);
                        gadView.setAdListener(new com.google.android.gms.ads.AdListener() {
                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                adContainerView.setBackground(getResources().getDrawable(R.drawable.bg_banner));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    getResources().getDrawable(R.drawable.bg_banner).setTint(defaultAdsIds.TINT_COLOR());
                                }
                            }
                        });
                    } else {
                        com.facebook.ads.AdView adView;
                        if (adsPrefernce.showfbBanner2()) {
                            AudienceNetworkAds.initialize(this);
                            adView = new com.facebook.ads.AdView(this, adsPrefernce.fbBannerId1(), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
                            final FrameLayout adContainerView = findViewById(R.id.banner_container);
                            adContainerView.setVisibility(View.VISIBLE);
                            adContainerView.addView(adView);
                            adContainerView.setPadding(0, top, 0, bottom);
                            adView.loadAd();
                            adView.setAdListener(new AdListener() {
                                @Override
                                public void onError(Ad ad, AdError adError) {

                                }

                                @Override
                                public void onAdLoaded(Ad ad) {
                                    adContainerView.setBackground(getResources().getDrawable(R.drawable.bg_banner));
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        getResources().getDrawable(R.drawable.bg_banner).setTint(defaultAdsIds.TINT_COLOR());
                                    }

                                }

                                @Override
                                public void onAdClicked(Ad ad) {

                                }

                                @Override
                                public void onLoggingImpression(Ad ad) {

                                }
                            });
                        }
                    }
                } else {
                    final FrameLayout adContainerView = (FrameLayout) findViewById(R.id.banner_container);
                    Banner startAppBanner = new Banner(this, new BannerListener() {
                        @Override
                        public void onReceiveAd(View view) {
                            adContainerView.setBackground(getResources().getDrawable(R.drawable.bg_banner));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                getResources().getDrawable(R.drawable.bg_banner).setTint(defaultAdsIds.TINT_COLOR());
                            }
                        }

                        @Override
                        public void onFailedToReceiveAd(View view) {

                        }

                        @Override
                        public void onImpression(View view) {

                        }

                        @Override
                        public void onClick(View view) {

                        }
                    });
                    FrameLayout.LayoutParams bannerParameters =
                            new FrameLayout.LayoutParams(
                                    FrameLayout.LayoutParams.MATCH_PARENT,
                                    FrameLayout.LayoutParams.WRAP_CONTENT);
                    adContainerView.setVisibility(View.VISIBLE);
                    adContainerView.setForegroundGravity(Gravity.CENTER);
                    adContainerView.setPadding(0, top, 0, bottom);
                    adContainerView.addView(startAppBanner, bannerParameters);
                }

            } else {
                com.facebook.ads.AdView adView;
                AudienceNetworkAds.initialize(this);
                adView = new com.facebook.ads.AdView(this, defaultAdsIds.FB_BANNER2(), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
                final FrameLayout adContainerView = findViewById(R.id.banner_container);
                adContainerView.setVisibility(View.VISIBLE);
                adContainerView.setPadding(0, top, 0, bottom);
                adContainerView.addView(adView);
                adView.loadAd();
                adView.setAdListener(new AdListener() {
                    @Override
                    public void onError(Ad ad, AdError adError) {

                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        adContainerView.setBackground(getResources().getDrawable(R.drawable.bg_banner));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getResources().getDrawable(R.drawable.bg_banner).setTint(defaultAdsIds.TINT_COLOR());
                        }
                    }

                    @Override
                    public void onAdClicked(Ad ad) {

                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }
                });

            }
        }
    }

    public void shortToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public void longToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public void loadSplashInterstitial() {
        adsPrefernce = new AdsPrefernce(this);
        if (isNetworkAvailable(this)) {
            MobileAds.initialize(getApplicationContext(), adsPrefernce.gAppId());

            gSplashInter = new com.google.android.gms.ads.InterstitialAd(this);
            gSplashInter.setAdUnitId(adsPrefernce.gInterId1());
            gSplashInter.loadAd(new AdRequest.Builder().build());
            AudienceNetworkAds.initialize(this);

            fbSplashInter = new com.facebook.ads.InterstitialAd(this, adsPrefernce.fbInterId1());

            fbSplashInter.loadAd();
        }
    }

    public void showSplashInterstitial(){
        if (!adsPrefernce.showStartApp()){
            showSplashAd();
        }
    }

    public void showSplashAd() {
        if (isNetworkAvailable(this)) {
            if (adsPrefernce.showgInter1()) {
                if (gSplashInter.isLoaded() && gSplashInter != null) {
                    gSplashInter.show();
                    gSplashInter.setAdListener(new com.google.android.gms.ads.AdListener() {
                        public void onAdClosed() {
                        }
                    });
                }
            } else {
                if (adsPrefernce.showfbInter1()) {
                    if (fbSplashInter.isAdLoaded()) {
                        fbSplashInter.show();
                        fbSplashInter.setAdListener(new InterstitialAdListener() {
                            @Override
                            public void onInterstitialDisplayed(Ad ad) {

                            }

                            @Override
                            public void onInterstitialDismissed(Ad ad) {
                            }

                            @Override
                            public void onError(Ad ad, AdError adError) {

                            }

                            @Override
                            public void onAdLoaded(Ad ad) {

                            }

                            @Override
                            public void onAdClicked(Ad ad) {

                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {

                            }
                        });
                    }
                }
            }
        }
    }

    public void loadInterstitial1() {
        adsPrefernce = new AdsPrefernce(this);
        if (isNetworkAvailable(this)) {
            if (isAdsAvailable) {
                if (adsPrefernce.showgInter1()) {
                    MobileAds.initialize(getApplicationContext(), adsPrefernce.gAppId());
                    gInterstitial1 = new com.google.android.gms.ads.InterstitialAd(this);

                    gInterstitial1.setAdUnitId(adsPrefernce.gInterId1());

                    gInterstitial1.loadAd(new AdRequest.Builder().build());
                } else {
                    AudienceNetworkAds.initialize(this);
                    if (adsPrefernce.showfbInter1()) {
                        fbInterstitial1 = new com.facebook.ads.InterstitialAd(this, adsPrefernce.fbInterId1());
                        fbInterstitial1.loadAd();
                    }
                }
            } else {
                MobileAds.initialize(getApplicationContext(), defaultAdsIds.GOOGLE_APP_ID());
                gInterstitial1 = new com.google.android.gms.ads.InterstitialAd(this);
                gInterstitial1.setAdUnitId(defaultAdsIds.GOOGLE_INTER1());
                gInterstitial1.loadAd(new AdRequest.Builder().build());
                fbInterstitial1 = new com.facebook.ads.InterstitialAd(this, defaultAdsIds.FB_INTER1());
                fbInterstitial1.loadAd();
            }
        }
    }

    public void InterstitialAd1(final boolean loadOnClosed) {
        if (isNetworkAvailable(this)) {
            if (isAdsAvailable) {
                if (adsPrefernce.showgInter1()) {
                    if (gInterstitial1.isLoaded() && gInterstitial1 != null) {
                        gInterstitial1.show();
                        gInterstitial1.setAdListener(new com.google.android.gms.ads.AdListener() {
                            public void onAdClosed() {
                                if (loadOnClosed) {
                                    gInterstitial1.loadAd(new AdRequest.Builder().build());
                                }
                            }
                        });
                    }
                } else {
                    if (adsPrefernce.showfbInter1()) {
                        if (fbInterstitial1.isAdLoaded()) {
                            fbInterstitial1.show();
                            fbInterstitial1.setAdListener(new InterstitialAdListener() {
                                @Override
                                public void onInterstitialDisplayed(Ad ad) {

                                }

                                @Override
                                public void onInterstitialDismissed(Ad ad) {
                                    if (loadOnClosed) {
                                        fbInterstitial1.loadAd();
                                    }
                                }

                                @Override
                                public void onError(Ad ad, AdError adError) {

                                }

                                @Override
                                public void onAdLoaded(Ad ad) {

                                }

                                @Override
                                public void onAdClicked(Ad ad) {

                                }

                                @Override
                                public void onLoggingImpression(Ad ad) {

                                }
                            });
                        }
                    }

                }
            } else {
                if (fbInterstitial1.isAdLoaded()) {
                    fbInterstitial1.show();
                    fbInterstitial1.setAdListener(new InterstitialAdListener() {
                        @Override
                        public void onInterstitialDisplayed(Ad ad) {

                        }

                        @Override
                        public void onInterstitialDismissed(Ad ad) {
                            if (loadOnClosed) {
                                fbInterstitial1.loadAd();
                            }
                        }

                        @Override
                        public void onError(Ad ad, AdError adError) {

                        }

                        @Override
                        public void onAdLoaded(Ad ad) {

                        }

                        @Override
                        public void onAdClicked(Ad ad) {

                        }

                        @Override
                        public void onLoggingImpression(Ad ad) {

                        }
                    });
                }
            }
        }
    }

    public void showInterstitial1(final boolean loadOnClosed, final Callable<Void> mathodToFollow){
        if (adsPrefernce.showLoading()){
            proceedWithDelay(1000, "Showing Ad...", new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    try {
                        mathodToFollow.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    InterstitialAd1(loadOnClosed);
                    return null;
                }
            });
        }else {
            try {
                mathodToFollow.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            InterstitialAd1(loadOnClosed);
        }
    }

    public void showInterstitial2(final boolean loadOnClosed, final Callable<Void> mathodToFollow){
        if (adsPrefernce.showLoading()){
            proceedWithDelay(1000, "Showing Ad...", new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    try {
                        mathodToFollow.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    InterstitialAd2(loadOnClosed);
                    return null;
                }
            });
        }else {
            try {
                mathodToFollow.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            InterstitialAd2(loadOnClosed);
        }
    }

    public void showInterstitial3(final boolean loadOnClosed, final Callable<Void> mathodToFollow){
        if (adsPrefernce.showLoading()){
            proceedWithDelay(1000, "Showing Ad...", new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    try {
                        mathodToFollow.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    InterstitialAd3(loadOnClosed);
                    return null;
                }
            });
        }else {
            try {
                mathodToFollow.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            InterstitialAd3(loadOnClosed);
        }
    }

    public void loadInterstitial2() {
        adsPrefernce = new AdsPrefernce(this);
        if (isNetworkAvailable(this)) {
            if (isAdsAvailable) {
                if (adsPrefernce.showgInter2()) {
                    MobileAds.initialize(getApplicationContext(), adsPrefernce.gAppId());
                    gInterstitial2 = new com.google.android.gms.ads.InterstitialAd(this);

                    gInterstitial2.setAdUnitId(adsPrefernce.gInterId2());

                    gInterstitial2.loadAd(new AdRequest.Builder().build());
                } else {

                    AudienceNetworkAds.initialize(this);

                    if (adsPrefernce.showfbInter1()) {
                        fbInterstitial2 = new com.facebook.ads.InterstitialAd(this, adsPrefernce.fbInterId2());
                        fbInterstitial2.loadAd();
                    }
                }
            } else {
                MobileAds.initialize(getApplicationContext(), defaultAdsIds.GOOGLE_APP_ID());
                gInterstitial2 = new com.google.android.gms.ads.InterstitialAd(this);
                gInterstitial2.setAdUnitId(defaultAdsIds.GOOGLE_INTER2());
                gInterstitial2.loadAd(new AdRequest.Builder().build());
                fbInterstitial2 = new com.facebook.ads.InterstitialAd(this, defaultAdsIds.FB_INTER2());
                fbInterstitial2.loadAd();
            }
        }
    }

    public void InterstitialAd2(final boolean loadOnClosed) {
        if (isNetworkAvailable(this)) {
            if (isAdsAvailable) {
                if (adsPrefernce.showgInter2()) {
                    if (gInterstitial2.isLoaded() && gInterstitial2 != null) {
                        gInterstitial2.show();
                        gInterstitial2.setAdListener(new com.google.android.gms.ads.AdListener() {
                            public void onAdClosed() {
                                if (loadOnClosed) {
                                    gInterstitial2.loadAd(new AdRequest.Builder().build());
                                }
                            }
                        });
                    }
                } else {
                    if (adsPrefernce.showfbInter2()) {
                        if (fbInterstitial2.isAdLoaded()) {
                            fbInterstitial2.show();
                            fbInterstitial2.setAdListener(new InterstitialAdListener() {
                                @Override
                                public void onInterstitialDisplayed(Ad ad) {

                                }

                                @Override
                                public void onInterstitialDismissed(Ad ad) {
                                    if (loadOnClosed) {
                                        fbInterstitial2.loadAd();
                                    }
                                }

                                @Override
                                public void onError(Ad ad, AdError adError) {

                                }

                                @Override
                                public void onAdLoaded(Ad ad) {

                                }

                                @Override
                                public void onAdClicked(Ad ad) {

                                }

                                @Override
                                public void onLoggingImpression(Ad ad) {

                                }
                            });
                        }
                    }

                }
            } else {
                if (fbInterstitial2.isAdLoaded()) {
                    fbInterstitial2.show();
                    fbInterstitial2.setAdListener(new InterstitialAdListener() {
                        @Override
                        public void onInterstitialDisplayed(Ad ad) {

                        }

                        @Override
                        public void onInterstitialDismissed(Ad ad) {
                            if (loadOnClosed) {
                                fbInterstitial2.loadAd();
                            }
                        }

                        @Override
                        public void onError(Ad ad, AdError adError) {

                        }

                        @Override
                        public void onAdLoaded(Ad ad) {

                        }

                        @Override
                        public void onAdClicked(Ad ad) {

                        }

                        @Override
                        public void onLoggingImpression(Ad ad) {

                        }
                    });
                }
            }
        }
    }

    public void loadInterstitial3() {
        adsPrefernce = new AdsPrefernce(this);
        if (isNetworkAvailable(this)) {
            if (isAdsAvailable) {
                if (adsPrefernce.showgInter3()) {
                    MobileAds.initialize(getApplicationContext(), adsPrefernce.gAppId());
                    gInterstitial3 = new com.google.android.gms.ads.InterstitialAd(this);
                    gInterstitial3.setAdUnitId(adsPrefernce.gInterId3());
                    gInterstitial3.loadAd(new AdRequest.Builder().build());
                } else {
                    AudienceNetworkAds.initialize(this);
                    if (adsPrefernce.showfbInter3()) {
                        fbInterstitial3 = new com.facebook.ads.InterstitialAd(this, adsPrefernce.fbInterId3());
                        fbInterstitial3.loadAd();
                    }
                }
            } else {
                MobileAds.initialize(getApplicationContext(), defaultAdsIds.GOOGLE_APP_ID());
                gInterstitial3 = new com.google.android.gms.ads.InterstitialAd(this);
                gInterstitial3.setAdUnitId(defaultAdsIds.GOOGLE_INTER3());
                gInterstitial3.loadAd(new AdRequest.Builder().build());
                fbInterstitial3 = new com.facebook.ads.InterstitialAd(this, defaultAdsIds.FB_INTER3());
                fbInterstitial3.loadAd();
            }
        }
    }

    public void InterstitialAd3(final boolean loadOnClosed) {
        if (isNetworkAvailable(this)) {
            if (isAdsAvailable) {
                if (adsPrefernce.showgInter3()) {
                    if (gInterstitial3.isLoaded() && gInterstitial3 != null) {
                        gInterstitial3.show();
                        gInterstitial3.setAdListener(new com.google.android.gms.ads.AdListener() {
                            public void onAdClosed() {
                                if (loadOnClosed) {
                                    gInterstitial3.loadAd(new AdRequest.Builder().build());
                                }
                            }
                        });
                    }
                } else {
                    if (adsPrefernce.showfbInter3()) {
                        if (fbInterstitial3.isAdLoaded()) {
                            fbInterstitial3.show();
                            fbInterstitial3.setAdListener(new InterstitialAdListener() {
                                @Override
                                public void onInterstitialDisplayed(Ad ad) {

                                }

                                @Override
                                public void onInterstitialDismissed(Ad ad) {
                                    if (loadOnClosed) {
                                        fbInterstitial3.loadAd();
                                    }
                                }

                                @Override
                                public void onError(Ad ad, AdError adError) {

                                }

                                @Override
                                public void onAdLoaded(Ad ad) {

                                }

                                @Override
                                public void onAdClicked(Ad ad) {

                                }

                                @Override
                                public void onLoggingImpression(Ad ad) {

                                }
                            });
                        }
                    }

                }
            } else {
                if (fbInterstitial3.isAdLoaded()) {
                    fbInterstitial3.show();
                    fbInterstitial3.setAdListener(new InterstitialAdListener() {
                        @Override
                        public void onInterstitialDisplayed(Ad ad) {

                        }

                        @Override
                        public void onInterstitialDismissed(Ad ad) {
                            if (loadOnClosed) {
                                fbInterstitial3.loadAd();
                            }
                        }

                        @Override
                        public void onError(Ad ad, AdError adError) {

                        }

                        @Override
                        public void onAdLoaded(Ad ad) {

                        }

                        @Override
                        public void onAdClicked(Ad ad) {

                        }

                        @Override
                        public void onLoggingImpression(Ad ad) {

                        }
                    });
                }
            }
        }
    }

    public void proceedWithDelay(int delay,String messageText ,final Callable<Void> mathodToProceed){

        progressDialog.setMessage(messageText);
        progressDialog.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                try {
                    mathodToProceed.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }

    public void loadInterAdsDialog(int adToLoad) {
        adsPrefernce = new AdsPrefernce(this);
        if (isNetworkAvailable(this)) {
            if (isAdsAvailable) {

                MobileAds.initialize(getApplicationContext(), adsPrefernce.gAppId());
                if (adToLoad == 1 && adsPrefernce.showgInter1()) {
                    gDialogInterstitial = new com.google.android.gms.ads.InterstitialAd(this);
                    gDialogInterstitial.setAdUnitId(adsPrefernce.gInterId1());
                    gDialogInterstitial.loadAd(new AdRequest.Builder().build());
                } else if (adToLoad == 2 && adsPrefernce.showgInter2()) {
                    gDialogInterstitial = new com.google.android.gms.ads.InterstitialAd(this);
                    gDialogInterstitial.setAdUnitId(adsPrefernce.gInterId2());
                    gDialogInterstitial.loadAd(new AdRequest.Builder().build());
                } else if (adToLoad == 3 && adsPrefernce.showgInter3()) {
                    gDialogInterstitial = new com.google.android.gms.ads.InterstitialAd(this);
                    gDialogInterstitial.setAdUnitId(adsPrefernce.gInterId3());
                    gDialogInterstitial.loadAd(new AdRequest.Builder().build());
                } else {
                    AudienceNetworkAds.initialize(this);
                    if (adToLoad == 1 && adsPrefernce.showfbInter1()) {
                        fbDialogInterstitial = new com.facebook.ads.InterstitialAd(this, adsPrefernce.fbInterId1());
                        fbDialogInterstitial.loadAd();
                    } else if (adToLoad == 2 && adsPrefernce.showfbInter2()) {
                        fbDialogInterstitial = new com.facebook.ads.InterstitialAd(this, adsPrefernce.fbInterId2());
                        fbDialogInterstitial.loadAd();
                    } else if (adToLoad == 3 && adsPrefernce.showfbInter3()) {
                        fbDialogInterstitial = new com.facebook.ads.InterstitialAd(this, adsPrefernce.fbInterId3());
                        fbDialogInterstitial.loadAd();
                    }
                }
            } else {
                MobileAds.initialize(getApplicationContext(), defaultAdsIds.GOOGLE_APP_ID());
                if (adToLoad == 1) {
                    gDialogInterstitial = new com.google.android.gms.ads.InterstitialAd(this);
                    gDialogInterstitial.setAdUnitId(defaultAdsIds.GOOGLE_INTER1());
                    gDialogInterstitial.loadAd(new AdRequest.Builder().build());
                    fbDialogInterstitial = new com.facebook.ads.InterstitialAd(this, defaultAdsIds.FB_INTER1());
                    fbDialogInterstitial.loadAd();
                } else if (adToLoad == 2) {
                    gDialogInterstitial = new com.google.android.gms.ads.InterstitialAd(this);
                    gDialogInterstitial.setAdUnitId(defaultAdsIds.GOOGLE_INTER2());
                    gDialogInterstitial.loadAd(new AdRequest.Builder().build());
                    fbDialogInterstitial = new com.facebook.ads.InterstitialAd(this, defaultAdsIds.FB_INTER2());
                    fbDialogInterstitial.loadAd();
                } else if (adToLoad == 3) {
                    gDialogInterstitial = new com.google.android.gms.ads.InterstitialAd(this);
                    gDialogInterstitial.setAdUnitId(defaultAdsIds.GOOGLE_INTER3());
                    gDialogInterstitial.loadAd(new AdRequest.Builder().build());
                    fbDialogInterstitial = new com.facebook.ads.InterstitialAd(this, defaultAdsIds.FB_INTER3());
                    fbDialogInterstitial.loadAd();
                }
            }
        }
    }

    public void showInterAdsDialog(final boolean loadOnClosed, final int adToShow) {
        if (isNetworkAvailable(BaseClass.this))    {
            if (isAdsAvailable) {
                if (adToShow == 1 && adsPrefernce.showgInter1()) {
                    if (gDialogInterstitial.isLoaded() && gDialogInterstitial != null) {
                        gDialogInterstitial.show();
                    }
                    gDialogInterstitial.setAdListener(new com.google.android.gms.ads.AdListener() {
                        public void onAdClosed() {
                            if (loadOnClosed) {
                                gDialogInterstitial.loadAd(new AdRequest.Builder().build());
                            }
                        }
                    });
                } else if (adToShow == 2 && adsPrefernce.showgInter2()) {
                    if (gDialogInterstitial.isLoaded() && gDialogInterstitial != null) {
                        gDialogInterstitial.show();
                    }
                    gDialogInterstitial.setAdListener(new com.google.android.gms.ads.AdListener() {
                        public void onAdClosed() {
                            if (loadOnClosed) {
                                gDialogInterstitial.loadAd(new AdRequest.Builder().build());
                            }
                        }
                    });
                } else if (adToShow == 3 && adsPrefernce.showgInter3()) {
                    if (gDialogInterstitial.isLoaded() && gDialogInterstitial != null) {
                        gDialogInterstitial.show();
                    }
                    gDialogInterstitial.setAdListener(new com.google.android.gms.ads.AdListener() {
                        public void onAdClosed() {
                            if (loadOnClosed) {
                                gDialogInterstitial.loadAd(new AdRequest.Builder().build());
                            }
                        }
                    });
                } else {
                    if (adToShow == 1 && adsPrefernce.showfbInter1()) {
                        if (fbDialogInterstitial.isAdLoaded()) {
                            fbDialogInterstitial.show();
                            fbDialogInterstitial.setAdListener(new InterstitialAdListener() {
                                @Override
                                public void onInterstitialDisplayed(Ad ad) {

                                }

                                @Override
                                public void onInterstitialDismissed(Ad ad) {
                                    if (loadOnClosed) {
                                        fbDialogInterstitial.loadAd();
                                    }
                                }

                                @Override
                                public void onError(Ad ad, AdError adError) {

                                }

                                @Override
                                public void onAdLoaded(Ad ad) {

                                }

                                @Override
                                public void onAdClicked(Ad ad) {

                                }

                                @Override
                                public void onLoggingImpression(Ad ad) {

                                }
                            });
                        }
                    }else if (adToShow == 2 && adsPrefernce.showfbInter2()) {
                        if (fbDialogInterstitial.isAdLoaded()) {
                            fbDialogInterstitial.show();
                            fbDialogInterstitial.setAdListener(new InterstitialAdListener() {
                                @Override
                                public void onInterstitialDisplayed(Ad ad) {

                                }

                                @Override
                                public void onInterstitialDismissed(Ad ad) {
                                    if (loadOnClosed) {
                                        fbDialogInterstitial.loadAd();
                                    }
                                }

                                @Override
                                public void onError(Ad ad, AdError adError) {

                                }

                                @Override
                                public void onAdLoaded(Ad ad) {

                                }

                                @Override
                                public void onAdClicked(Ad ad) {

                                }

                                @Override
                                public void onLoggingImpression(Ad ad) {

                                }
                            });
                        }
                    }
                    else if (adToShow == 3 && adsPrefernce.showfbInter3()) {
                        if (fbDialogInterstitial.isAdLoaded()) {
                            fbDialogInterstitial.show();
                            fbDialogInterstitial.setAdListener(new InterstitialAdListener() {
                                @Override
                                public void onInterstitialDisplayed(Ad ad) {

                                }

                                @Override
                                public void onInterstitialDismissed(Ad ad) {
                                    if (loadOnClosed) {
                                        fbDialogInterstitial.loadAd();
                                    }
                                }

                                @Override
                                public void onError(Ad ad, AdError adError) {

                                }

                                @Override
                                public void onAdLoaded(Ad ad) {

                                }

                                @Override
                                public void onAdClicked(Ad ad) {

                                }

                                @Override
                                public void onLoggingImpression(Ad ad) {

                                }
                            });
                        }
                    }

                }
            } else {
                if (fbDialogInterstitial.isAdLoaded()) {
                    fbDialogInterstitial.show();
                    fbDialogInterstitial.setAdListener(new InterstitialAdListener() {
                        @Override
                        public void onInterstitialDisplayed(Ad ad) {

                        }

                        @Override
                        public void onInterstitialDismissed(Ad ad) {
                            if (loadOnClosed) {
                                fbDialogInterstitial.loadAd();
                            }
                        }

                        @Override
                        public void onError(Ad ad, AdError adError) {

                        }

                        @Override
                        public void onAdLoaded(Ad ad) {

                        }

                        @Override
                        public void onAdClicked(Ad ad) {

                        }

                        @Override
                        public void onLoggingImpression(Ad ad) {

                        }
                    });
                }
            }
        }
    }

    public void showBackPressAd(int adToShow, final Callable<Void> methodParam) {
        if (isNetworkAvailable(this)) {
            adsPrefernce = new AdsPrefernce(this);
            if (!adsPrefernce.showStartApp()){
                if (isAdsAvailable) {
                    if (adToShow == 1 && adsPrefernce.showgInter1()) {
                        if (gInterstitial1.isLoaded()) {
                            gInterstitial1.show();
                            gInterstitial1.setAdListener(new com.google.android.gms.ads.AdListener() {
                                @Override
                                public void onAdLoaded() {
                                }

                                @Override
                                public void onAdClosed() {
                                    try {
                                        methodParam.call();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    gInterstitial1.loadAd(new AdRequest.Builder().build());
                                }
                            });
                        } else {
                            try {
                                methodParam.call();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else if (adToShow == 2 && adsPrefernce.showgInter2()){
                        if (gInterstitial2.isLoaded()) {
                            gInterstitial2.show();
                            gInterstitial2.setAdListener(new com.google.android.gms.ads.AdListener() {
                                @Override
                                public void onAdLoaded() {
                                }

                                @Override
                                public void onAdClosed() {
                                    try {
                                        methodParam.call();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    gInterstitial2.loadAd(new AdRequest.Builder().build());
                                }
                            });
                        } else {
                            try {
                                methodParam.call();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else if (adToShow == 3 && adsPrefernce.showgInter3()){
                        if (gInterstitial3.isLoaded()) {
                            gInterstitial3.show();
                            gInterstitial3.setAdListener(new com.google.android.gms.ads.AdListener() {
                                @Override
                                public void onAdLoaded() {
                                }

                                @Override
                                public void onAdClosed() {
                                    try {
                                        methodParam.call();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    gInterstitial3.loadAd(new AdRequest.Builder().build());
                                }
                            });
                        } else {
                            try {
                                methodParam.call();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else {
                        if (adToShow == 1 && adsPrefernce.showfbInter1()) {
                            if (fbInterstitial1.isAdLoaded()) {
                                fbInterstitial1.show();
                                fbInterstitial1.setAdListener(new InterstitialAdListener() {
                                    @Override
                                    public void onInterstitialDisplayed(Ad ad) {

                                    }

                                    @Override
                                    public void onInterstitialDismissed(Ad ad) {
                                        try {
                                            methodParam.call();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(Ad ad, AdError adError) {

                                    }

                                    @Override
                                    public void onAdLoaded(Ad ad) {

                                    }

                                    @Override
                                    public void onAdClicked(Ad ad) {

                                    }

                                    @Override
                                    public void onLoggingImpression(Ad ad) {

                                    }
                                });
                            } else {
                                try {
                                    methodParam.call();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (adToShow == 2 && adsPrefernce.showfbInter2()){
                            if (fbInterstitial2.isAdLoaded()) {
                                fbInterstitial2.show();
                                fbInterstitial2.setAdListener(new InterstitialAdListener() {
                                    @Override
                                    public void onInterstitialDisplayed(Ad ad) {

                                    }

                                    @Override
                                    public void onInterstitialDismissed(Ad ad) {
                                        try {
                                            methodParam.call();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(Ad ad, AdError adError) {

                                    }

                                    @Override
                                    public void onAdLoaded(Ad ad) {

                                    }

                                    @Override
                                    public void onAdClicked(Ad ad) {

                                    }

                                    @Override
                                    public void onLoggingImpression(Ad ad) {

                                    }
                                });
                            } else {
                                try {
                                    methodParam.call();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }else if (adToShow == 3 && adsPrefernce.showfbInter3()){
                            if (fbInterstitial3.isAdLoaded()) {
                                fbInterstitial3.show();
                                fbInterstitial3.setAdListener(new InterstitialAdListener() {
                                    @Override
                                    public void onInterstitialDisplayed(Ad ad) {

                                    }

                                    @Override
                                    public void onInterstitialDismissed(Ad ad) {
                                        try {
                                            methodParam.call();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(Ad ad, AdError adError) {

                                    }

                                    @Override
                                    public void onAdLoaded(Ad ad) {

                                    }

                                    @Override
                                    public void onAdClicked(Ad ad) {

                                    }

                                    @Override
                                    public void onLoggingImpression(Ad ad) {

                                    }
                                });
                            } else {
                                try {
                                    methodParam.call();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        else {
                            try {
                                methodParam.call();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    if (adToShow == 1 && fbInterstitial1.isAdLoaded()) {
                        fbInterstitial1.show();
                        fbInterstitial1.setAdListener(new InterstitialAdListener() {
                            @Override
                            public void onInterstitialDisplayed(Ad ad) {

                            }

                            @Override
                            public void onInterstitialDismissed(Ad ad) {
                                try {
                                    methodParam.call();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Ad ad, AdError adError) {

                            }

                            @Override
                            public void onAdLoaded(Ad ad) {

                            }

                            @Override
                            public void onAdClicked(Ad ad) {

                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {

                            }
                        });
                    }
                    else if (adToShow == 2 && fbInterstitial2.isAdLoaded()){
                        fbInterstitial2.show();
                        fbInterstitial2.setAdListener(new InterstitialAdListener() {
                            @Override
                            public void onInterstitialDisplayed(Ad ad) {

                            }

                            @Override
                            public void onInterstitialDismissed(Ad ad) {
                                try {
                                    methodParam.call();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Ad ad, AdError adError) {

                            }

                            @Override
                            public void onAdLoaded(Ad ad) {

                            }

                            @Override
                            public void onAdClicked(Ad ad) {

                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {

                            }
                        });
                    }
                    else if (adToShow == 3 && fbInterstitial3.isAdLoaded()){
                        fbInterstitial3.show();
                        fbInterstitial3.setAdListener(new InterstitialAdListener() {
                            @Override
                            public void onInterstitialDisplayed(Ad ad) {

                            }

                            @Override
                            public void onInterstitialDismissed(Ad ad) {
                                try {
                                    methodParam.call();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Ad ad, AdError adError) {

                            }

                            @Override
                            public void onAdLoaded(Ad ad) {

                            }

                            @Override
                            public void onAdClicked(Ad ad) {

                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {

                            }
                        });
                    }

                    else {
                        try {
                            methodParam.call();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    public void checkAppService(String key,String appVersion){
        if (isNetworkAvailable(this) && checkAppService) {
            runAppService(key,appVersion);
        }
    }

    public void runAppService(String appName, final String appVersion) {
        AsyncHttpClient client = new AsyncHttpClient();

        gsonUtils = GsonUtils.getInstance();

        RequestParams params1 = new RequestParams();
        params1.put("app_name", appName);

        try {
            client.setConnectTimeout(50000);

            client.post("http://api.get-fans-for-musically.com/service/app_service.php", params1, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    String Success = response.optString("success");
                    int con = Integer.parseInt(Success);
                    if (con == 1) {

                        int isUpdate = response.optInt("isUpdate");
                        int isNotification = response.optInt("isNotification");
                        int isAd = response.optInt("isAd");
                        String update_dialog_title = response.optString("update_dialog_title");
                        String update_title = response.optString("update_title");
                        String update_version_name = response.optString("update_version_name");
                        String update_message = response.optString("update_message");
                        int update_available = response.optInt("update_available");
                        int update_show_cancel = response.optInt("update_show_cancel");
                        String update_app_url = response.optString("update_app_url");
                        int update_force_update = response.optInt("update_force_update");
                        String update_force_v1 = response.optString("update_force_v1");
                        String update_force_v2 = response.optString("update_force_v2");
                        String update_force_v3 = response.optString("update_force_v3");
                        String not_dialog_title = response.optString("not_dialog_title");
                        String not_message = response.optString("not_message");
                        String ad_dialog_title = response.optString("ad_dialog_title");
                        String ad_message = response.optString("ad_message");
                        String ad_banner_url = response.optString("ad_banner_url");
                        String ad_icon_url = response.optString("ad_icon_url");
                        String ad_app_name = response.optString("ad_app_name");
                        String ad_app_short_desc = response.optString("ad_app_short_desc");
                        String ad_app_url = response.optString("ad_app_url");

                        checkAppService = false;

                        if (isUpdate == 1) {
                            if (!appVersion.equals(update_version_name)) {
                                serviceDialog(true, false, false, update_dialog_title, update_title, update_version_name, update_message, update_available == 1,
                                        update_show_cancel == 1, update_app_url, update_force_update == 1, update_force_v1, update_force_v2, update_force_v3, not_dialog_title,
                                        not_message, ad_dialog_title, ad_message, ad_banner_url, ad_icon_url, ad_app_name, ad_app_short_desc, ad_app_url);
                                if (defaultAdsIds.SHOW_NOTIFICATION()){
                                    if (isNotification == 1) {
                                        if (update_available == 0) {
                                            lay_notification.setVisibility(View.VISIBLE);
                                            tv_not_text.setText(not_message);
                                            iv_not_close.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    lay_notification.setVisibility(View.GONE);
                                                }
                                            });
                                        }
                                    }
                                }

                                return;
                            }

                        }
                        if (defaultAdsIds.SHOW_NOTIFICATION()){
                            if (isNotification == 1) {
                                if (update_available == 0) {
                                    lay_notification.setVisibility(View.VISIBLE);
                                    tv_not_text.setText(not_message);
                                    iv_not_close.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            lay_notification.setVisibility(View.GONE);
                                        }
                                    });
                                } else {
                                    serviceDialog(false, true, false, update_dialog_title, update_title, update_version_name, update_message, update_available == 1,
                                            update_show_cancel == 1, update_app_url, update_force_update == 1, update_force_v1, update_force_v2, update_force_v3, not_dialog_title,
                                            not_message, ad_dialog_title, ad_message, ad_banner_url, ad_icon_url, ad_app_name, ad_app_short_desc, ad_app_url);
                                    return;
                                }
                            }
                        }
                        if (isAd == 1) {
                            serviceDialog(false, false, true, update_dialog_title, update_title, update_version_name, update_message, update_available == 1,
                                    update_show_cancel == 1, update_app_url, update_force_update == 1, update_force_v1, update_force_v2, update_force_v3, not_dialog_title,
                                    not_message, ad_dialog_title, ad_message, ad_banner_url, ad_icon_url, ad_app_name, ad_app_short_desc, ad_app_url);

                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                }
            });

        } catch (Exception e) {

        }
    }

    public void serviceDialog(Boolean isUpdate, Boolean isNotification, Boolean isAd, String update_dialog_title, String update_title,
                              String update_version_name, String update_message, Boolean update_available, Boolean update_show_cancel, final String update_app_url,
                              Boolean update_force_update, String update_force_v1, String update_force_v2, String update_force_v3, String not_dialog_title,
                              String not_message, String ad_dialog_title, String ad_message, String ad_banner_url, String ad_icon_url,
                              String ad_app_name, String ad_app_short_desc, final String ad_app_url) {


        this.serviceDialog = new Dialog(this);
        this.serviceDialog.setCancelable(false);
        this.serviceDialog.setContentView(R.layout.dialog_service);
        Objects.requireNonNull(this.serviceDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        this.serviceDialog.show();
        LinearLayout lay_updateApp = this.serviceDialog.findViewById(R.id.lay_updateApp);
        LinearLayout lay_message = this.serviceDialog.findViewById(R.id.lay_message);
        LinearLayout lay_ads = this.serviceDialog.findViewById(R.id.lay_ads);

        ImageView iv_ad_icon_title = this.serviceDialog.findViewById(R.id.iv_ad_icon_title);
        TextView tv_dialog_title = this.serviceDialog.findViewById(R.id.tv_dialog_title);

        //update
        TextView tv_updatetitle = this.serviceDialog.findViewById(R.id.tv_updatetitle);
        TextView tv_versionName = this.serviceDialog.findViewById(R.id.tv_versionName);
        TextView tv_updatemessage = this.serviceDialog.findViewById(R.id.tv_updatemessage);
        TextView tv_updatebutton = this.serviceDialog.findViewById(R.id.tv_updatebutton);
        TextView tv_canclebutton = this.serviceDialog.findViewById(R.id.tv_canclebutton);

        //message
        TextView tv_message = this.serviceDialog.findViewById(R.id.tv_message);
        TextView tv_okButton = this.serviceDialog.findViewById(R.id.tv_not_okButton);

        //ads
        TextView tv_ad_message = this.serviceDialog.findViewById(R.id.tv_ad_message);
        ImageView iv_ad_banner = this.serviceDialog.findViewById(R.id.iv_ad_banner);
        ImageView iv_app_icon = this.serviceDialog.findViewById(R.id.iv_app_icon);
        TextView tv_app_name = this.serviceDialog.findViewById(R.id.tv_app_name);
        TextView tv_app_shortdesc = this.serviceDialog.findViewById(R.id.tv_app_shortdesc);
        TextView tv_app_download = this.serviceDialog.findViewById(R.id.tv_app_download);
        TextView tv_app_cancel = this.serviceDialog.findViewById(R.id.tv_app_cancel);

        if (isUpdate) {
            iv_ad_icon_title.setVisibility(View.GONE);
            lay_message.setVisibility(View.GONE);
            lay_ads.setVisibility(View.GONE);
            lay_updateApp.setVisibility(View.VISIBLE);
            tv_dialog_title.setText(update_dialog_title);

            tv_updatetitle.setText(update_title);
            tv_versionName.setText(update_version_name);
            tv_updatemessage.setText(update_message);

            if (update_show_cancel) {
                tv_canclebutton.setVisibility(View.VISIBLE);
            } else {
                tv_canclebutton.setVisibility(View.GONE);
            }

            tv_updatebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(update_app_url));
                    startActivity(intent);
                }
            });

            tv_canclebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceDialog.dismiss();
                }
            });
        }

        if (isNotification) {
            if (update_available) {
                iv_ad_icon_title.setVisibility(View.GONE);
                lay_ads.setVisibility(View.GONE);
                lay_updateApp.setVisibility(View.GONE);
                lay_message.setVisibility(View.VISIBLE);
                tv_dialog_title.setText(not_dialog_title);

                tv_message.setText(not_message);

                tv_okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        serviceDialog.dismiss();
                    }
                });
            }

        }

        if (isAd) {
            iv_ad_icon_title.setVisibility(View.VISIBLE);
            lay_updateApp.setVisibility(View.GONE);
            lay_message.setVisibility(View.GONE);
            lay_ads.setVisibility(View.VISIBLE);
            tv_dialog_title.setText(ad_dialog_title);

            tv_ad_message.setText(ad_message);
            Glide.with(this).load(ad_banner_url).into(iv_ad_banner);
            Glide.with(this).load(ad_icon_url).into(iv_app_icon);
            tv_app_name.setText(ad_app_name);
            tv_app_shortdesc.setText(ad_app_short_desc);

            tv_app_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ad_app_url));
                    startActivity(intent);
                }
            });

            tv_app_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceDialog.dismiss();
                }
            });

        }

    }



}
