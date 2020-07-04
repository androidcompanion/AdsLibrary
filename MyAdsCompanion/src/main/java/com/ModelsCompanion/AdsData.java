package com.ModelsCompanion;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdsData {
    @SerializedName("adsIds_List")
    @Expose
    private List<AdsIdsList> adsIdsList = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<AdsIdsList> getAdsIdsList() {
        return adsIdsList;
    }

    public void setAdsIdsList(List<AdsIdsList> adsIdsList) {
        this.adsIdsList = adsIdsList;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
