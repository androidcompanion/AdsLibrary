package com.ModelsCompanion;

import android.content.Context;
import android.content.SharedPreferences;

import com.AdsConfig.DefaultAdsIds;

import java.util.Objects;

public class AdsPrefernce {

    Context context;
    SharedPreferences adsPreference;
    SharedPreferences.Editor editor;
    DefaultAdsIds ads;
    public AdsPrefernce(Context context) {
        this.context = context;
        adsPreference = context.getSharedPreferences("MyAdsPrefrence", Context.MODE_PRIVATE);
        editor = adsPreference.edit();
        ads = new DefaultAdsIds(context);

    }

    public void setAdsDefaults(String showAds, String useStaticIds, String showNativeAd, String g_app_id, String use_static_gapp_id,
                               String g_banner1, String show_g_banner1, String g_banner2, String show_g_banner2,
                               String g_inter1, String show_g_inter1, String g_inter2, String show_g_inter2, String g_inter3, String show_g_inter3,
                               String g_rewarded1, String show_g_rewarded1, String g_rewarded2, String show_g_rewarded2,
                               String g_native1, String show_g_native1, String g_native2, String show_g_native2,
                               String fb_banner1, String show_fb_banner1, String fb_banner2, String show_fb_banner2,
                               String fb_inter1, String show_fb_inter1, String fb_inter2, String show_fb_inter2, String fb_inter3, String show_fb_inter3,
                               String fb_rewarded, String show_fb_rewarded,
                               String fb_native1, String show_fb_native1, String fb_native2, String show_fb_native2,
                               String fb_native_banner, String show_fb_native_banner,
                               String fb_med_rectangle, String show_fb_med_rectangle,
                               String extraBooleanPara1, String extraBooleanPara2,
                               String extraStringPara1, String extraStringPara2) {
        if (adsPreference != null) {
            editor = adsPreference.edit();
            editor.putString("showAds", showAds);
            editor.putString("useStaticIds", useStaticIds);
            editor.putString("showNativeAd", showNativeAd);
            editor.putString("g_app_id", g_app_id);
            editor.putString("use_static_gapp_id", use_static_gapp_id);

            editor.putString("g_banner1", g_banner1);
            editor.putString("show_g_banner1", show_g_banner1);
            editor.putString("g_banner2", g_banner2);
            editor.putString("show_g_banner2", show_g_banner2);

            editor.putString("g_inter1", g_inter1);
            editor.putString("show_g_inter1", show_g_inter1);
            editor.putString("g_inter2", g_inter2);
            editor.putString("show_g_inter2", show_g_inter2);
            editor.putString("g_inter3", g_inter3);
            editor.putString("show_g_inter3", show_g_inter3);

            editor.putString("g_rewarded1", g_rewarded1);
            editor.putString("show_g_rewarded1", show_g_rewarded1);
            editor.putString("g_rewarded2", g_rewarded2);
            editor.putString("show_g_rewarded2", show_g_rewarded2);

            editor.putString("g_native1", g_native1);
            editor.putString("show_g_native1", show_g_native1);
            editor.putString("g_native2", g_native2);
            editor.putString("show_g_native2", show_g_native2);

            editor.putString("fb_banner1", fb_banner1);
            editor.putString("show_fb_banner1", show_fb_banner1);
            editor.putString("fb_banner2", fb_banner2);
            editor.putString("show_fb_banner2", show_fb_banner2);

            editor.putString("fb_inter1", fb_inter1);
            editor.putString("show_fb_inter1", show_fb_inter1);
            editor.putString("fb_inter2", fb_inter2);
            editor.putString("show_fb_inter2", show_fb_inter2);
            editor.putString("fb_inter3", fb_inter3);
            editor.putString("show_fb_inter3", show_fb_inter3);

            editor.putString("fb_rewarded", fb_rewarded);
            editor.putString("show_fb_rewarded", show_fb_rewarded);

            editor.putString("fb_native1", fb_native1);
            editor.putString("show_fb_native1", show_fb_native1);
            editor.putString("fb_native2", fb_native2);
            editor.putString("show_fb_native2", show_fb_native2);

            editor.putString("fb_native_banner", fb_native_banner);
            editor.putString("show_fb_native_banner", show_fb_native_banner);
            editor.putString("fb_med_rectangle", fb_med_rectangle);
            editor.putString("show_fb_med_rectangle", show_fb_med_rectangle);

            editor.putString("extraBooleanPara1", extraBooleanPara1);
            editor.putString("extraBooleanPara2", extraBooleanPara2);
            editor.putString("extraStringPara1", extraStringPara1);
            editor.putString("extraStringPara2", extraStringPara2);

            editor.apply();
        }
    }


    public boolean showAds() {
        boolean output = false;
        String var;
        if (adsPreference != null) {
            var = adsPreference.getString("showAds", "1");
            output = Objects.equals(var, "1");
        }
        return output;
    }

    public boolean useStaticAdId() {
        boolean output = false;
        String var;
        if (adsPreference != null) {
            var = adsPreference.getString("useStaticIds", "0");
            output = Objects.equals(var, "1");
        }
        return output;
    }

    public String gAppId() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_app_id", ads.GOOGLE_APP_ID());
        }
        return var;
    }

    public String gBannerId1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_banner1", ads.GOOGLE_BANNER1());
        }
        return var;
    }

    public boolean showgBanner1() {
        boolean output = false;
        String var;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_g_banner1", "1"), "1");
        }
        return output;
    }

    public String gBannerId2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_banner2", ads.GOOGLE_BANNER2());
        }
        return var;
    }

    public boolean showgBanner2() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_g_banner2", "1"), "1");
        }
        return output;
    }

    public String gInterId1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_inter1", ads.GOOGLE_INTER1());
        }
        return var;
    }

    public boolean showgInter1() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_g_inter1", "0"), "1");
        }
        return output;
    }

    public String gInterId2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_inter2", ads.GOOGLE_INTER2());
        }
        return var;
    }

    public boolean showgInter2() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_g_inter2", "0"), "1");
        }
        return output;
    }

    public String gInterId3() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_inter3",  ads.GOOGLE_INTER3());
        }
        return var;
    }

    public boolean showgInter3() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_g_inter3", "1"), "1");
        }
        return output;
    }

    public String gRewardedId1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_rewarded1", ads.GOOGLE_REWARDED1());
        }
        return var;
    }

    public boolean showgRewarded1() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_g_rewarded1", "1"), "1");
        }
        return output;
    }

    public String gRewardedId2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_rewarded2", ads.GOOGLE_REWARDED2());
        }
        return var;
    }

    public boolean showgRewarded2() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_g_rewarded2", "1"), "1");
        }
        return output;
    }



    public String gNativeId1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_native1", ads.GOOGLE_NATIVE1());
        }
        return var;
    }

    public boolean showgNative1() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_g_native1", "1"), "1");
        };
        return output;
    }

    public String gNativeId2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("g_native2", ads.GOOGLE_NATIVE2());
        }
        return var;
    }

    public boolean showgNative2() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_g_native2", "1"), "1");
        };
        return output;
    }

    public String fbBannerId1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_banner1", ads.FB_BANNER1());
        }
        return var;
    }

    public boolean showfbBanner1() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_fb_banner1", "0"), "1");
        };
        return output;
    }

    public String fbBannerId2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_banner2", ads.FB_BANNER2());
        }
        return var;
    }

    public boolean showfbBanner2() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_fb_banner2", "0"), "1");
        }
        return output;
    }

    public String fbInterId1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_inter1", ads.FB_INTER1());
        }
        return var;
    }

    public boolean showfbInter1() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_fb_inter1", "1"), "1");
        }
        return output;
    }

    public String fbInterId2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_inter2", ads.FB_INTER2());
        }
        return var;
    }

    public boolean showfbInter2() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_fb_inter2", "0"), "1");
        }
        return output;
    }

    public String fbInterId3() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_inter3", ads.FB_INTER3());
        }
        return var;
    }

    public boolean showfbInter3() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_fb_inter3", "0"), "1");
        }
        return output;
    }

    public String fbRewardedId() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_rewarded", ads.FB_REWARDED1());
        }
        return var;
    }

    public boolean showfbRewarded() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_fb_rewarded", "1"), "1");
        }
        return output;
    }

    public String fbNativeId1() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_native1", ads.FB_NATIVE1());
        }
        return var;
    }

    public boolean showfbNative1() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_fb_native1", "1"), "1");
        }
        return output;
    }

    public String fbNativeId2() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_native2", ads.FB_NATIVE2());
        }
        return var;
    }

    public boolean showfbNative2() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_fb_native2", "1"), "1");
        }
        return output;
    }

    public String fbNativeBannerId() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_native_banner", ads.FB_NATIVE_BANNER1());
        }
        return var;
    }

    public boolean showfbNativeBanner() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_fb_native_banner", "1"), "1");
        }
        return output;
    }

    public String fbMedRectangleId() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("fb_med_rectangle", ads.FB_MED_RECT1());
        }
        return var;
    }

    public boolean showfbMedRectangle() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("show_fb_med_rectangle", "1"), "1");
        }
        return output;
    }

    public boolean showNativeAd() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("showNativeAd", "1"), "1");
        }
        return output;
    }

    public boolean useStaticgAppId() {
        boolean output = false;
        if (adsPreference != null) {
            output = showAds() && Objects.equals(adsPreference.getString("use_static_gapp_id", "0"), "1");
        }
        return output;
    }
       public boolean showStartApp() {
        boolean output = false;
        if (adsPreference != null) {
            output = Objects.equals(adsPreference.getString("extraBooleanPara1", "0"), "1");
        }
        return output;
    }
    public String adCountSA() {
        String var = "";
        if (adsPreference != null) {
            var = adsPreference.getString("extraBooleanPara2", "2");
        }
        return var;
    }

    public boolean extraString1() {
        boolean output = false;
        if (adsPreference != null) {
            output = Objects.equals(adsPreference.getString("extraStringPara1", "0"), "1");
        }
        return output;
    }
    public boolean showLoading() {
        boolean output = false;
        if (adsPreference != null) {
            output = Objects.equals(adsPreference.getString("extraStringPara2", "0"), "1");
        }
        return output;
    }
}



