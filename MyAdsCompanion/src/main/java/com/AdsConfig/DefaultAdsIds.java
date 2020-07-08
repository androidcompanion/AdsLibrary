package com.AdsConfig;

import android.content.Context;
import android.content.SharedPreferences;

import com.myadscompanion.R;

public class DefaultAdsIds {

    Context context;
    SharedPreferences defaultAdsPreference;
    SharedPreferences.Editor editor;
    public DefaultAdsIds(Context context) {
        this.context = context;
        defaultAdsPreference = context.getSharedPreferences("defaultsAdsPrefrence", Context.MODE_PRIVATE);
        editor = defaultAdsPreference.edit();
    }

    public void setPrimaryAdIds(String g_app_id, String g_banner1, String g_banner2, String g_inter1, String g_inter2, String g_inter3, String g_native1, String g_native2, String g_rewarded1,
                                String g_rewarded2, String fb_banner1, String fb_banner2, String fb_inter1, String fb_inter2, String fb_inter3, String fb_native1, String fb_native2,
                                String fb_native_banner1, String fb_native_banner2, String fb_med_rect1, String fb_med_rec2, String fb_rewarded1, String fb_rewarded2, String sa_app_id, String app_key,
                                Boolean disable_splash, Boolean show_notification, Boolean show_loading, int tint_Mode, String extraString1default) {

        editor = defaultAdsPreference.edit();
        editor.putString("g_app_id", g_app_id);
        editor.putString("g_banner1", g_banner1);
        editor.putString("g_banner2", g_banner2);
        editor.putString("g_inter1", g_inter1);
        editor.putString("g_inter2", g_inter2);
        editor.putString("g_inter3", g_inter3);
        editor.putString("g_native1", g_native1);
        editor.putString("g_native2", g_native2);
        editor.putString("g_rewarded1", g_rewarded1);
        editor.putString("g_rewarded2", g_rewarded2);
        editor.putString("fb_banner1", fb_banner1);
        editor.putString("fb_banner2", fb_banner2);
        editor.putString("fb_inter1", fb_inter1);
        editor.putString("fb_inter2", fb_inter2);
        editor.putString("fb_inter3", fb_inter3);
        editor.putString("fb_native1", fb_native1);
        editor.putString("fb_native2", fb_native2);
        editor.putString("fb_native_banner1", fb_native_banner1);
        editor.putString("fb_native_banner2", fb_native_banner2);
        editor.putString("fb_med_rect1", fb_med_rect1);
        editor.putString("fb_med_rec2", fb_med_rec2);
        editor.putString("fb_rewarded1", fb_rewarded1);
        editor.putString("fb_rewarded2", fb_rewarded2);
        editor.putString("sa_app_id", sa_app_id);
        editor.putString("app_key", app_key);
        editor.putBoolean("disable_splash", disable_splash);
        editor.putBoolean("show_notification", show_notification);
        editor.putBoolean("show_loading", show_loading);
        editor.putString("extraString1", extraString1default);
        editor.putInt("tint_Mode", tint_Mode);
        editor.apply();

    }

    public String GOOGLE_APP_ID() {
        return defaultAdsPreference.getString("g_app_id", "");
    }

    public String GOOGLE_BANNER1() {
        return defaultAdsPreference.getString("g_banner1", "");
    }

    public String GOOGLE_BANNER2() {
        return defaultAdsPreference.getString("g_banner2", "");
    }

    public String GOOGLE_INTER1() {
        return defaultAdsPreference.getString("g_inter1", "");
    }

    public String GOOGLE_INTER2() {
        return defaultAdsPreference.getString("g_inter2", "");
    }

    public String GOOGLE_INTER3() {
        return defaultAdsPreference.getString("g_inter3", "");
    }

    public String GOOGLE_NATIVE1() {
        return defaultAdsPreference.getString("g_native1", "");
    }

    public String GOOGLE_NATIVE2() {
        return defaultAdsPreference.getString("g_native2", "");
    }

    public String GOOGLE_REWARDED1() {
        return defaultAdsPreference.getString("g_rewarded1", "");
    }

    public String GOOGLE_REWARDED2() {
        return defaultAdsPreference.getString("g_rewarded2", "");
    }

    public String FB_BANNER1() {
        return defaultAdsPreference.getString("fb_banner1", "");
    }

    public String FB_BANNER2() {
        return defaultAdsPreference.getString("fb_banner2", "");
    }

    public String FB_INTER1() {
        return defaultAdsPreference.getString("fb_inter1", "");
    }

    public String FB_INTER2() {
        return defaultAdsPreference.getString("fb_inter2", "");
    }

    public String FB_INTER3() {
        return defaultAdsPreference.getString("fb_inter3", "");
    }

    public String FB_NATIVE1() {
        return defaultAdsPreference.getString("fb_native1", "");
    }

    public String FB_NATIVE2() {
        return defaultAdsPreference.getString("fb_native2", "");
    }

    public String FB_NATIVE_BANNER1() {
        return defaultAdsPreference.getString("fb_native_banner1", "");
    }

    public String FB_NATIVE_BANNER2() {
        return defaultAdsPreference.getString("fb_native_banner2", "");
    }

    public String FB_MED_RECT1() {
        return defaultAdsPreference.getString("fb_med_rect1", "");
    }

    public String FB_MED_RECT2() {
        return defaultAdsPreference.getString("fb_med_rect2", "");
    }

    public String FB_REWARDED1() {
        return defaultAdsPreference.getString("fb_rewarded1", "");
    }

    public String FB_REWARDED2() {
        return defaultAdsPreference.getString("fb_rewarded2", "");
    }

    public String SA_APP_ID() {
        return defaultAdsPreference.getString("sa_app_id", "");
    }

    public String APP_KEY() {
        return defaultAdsPreference.getString("app_key", "");
    }

    public int TINT_COLOR() {
        return defaultAdsPreference.getInt("tint_Mode", context.getResources().getColor(R.color._tint_color));
    }

    public Boolean DISABLE_SPLASH() {
        return defaultAdsPreference.getBoolean("disable_splash", true);
    }

    public Boolean SHOW_NOTIFICATION() {
        return defaultAdsPreference.getBoolean("show_notification", false);
    }

    public Boolean SHOW_LOADING() {
        return defaultAdsPreference.getBoolean("show_loading", false);
    }

    public String EXTRASTRING1DEFAULT() {
        return defaultAdsPreference.getString("extraString1", "0");
    }


}
