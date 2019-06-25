package com.android.teklipyaz.models.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityResultModel {

    @SerializedName("meta")
    @Expose
    private MetaModel meta;
    @SerializedName("results")
    @Expose
    private List<CityModel> results = null;

    public MetaModel getMetaModel() {
        return meta;
    }

    public void setMetaModel(MetaModel meta) {
        this.meta = meta;
    }

    public List<CityModel> getCityModels() {
        return results;
    }

    public void setCityModels(List<CityModel> results) {
        this.results = results;
    }
}