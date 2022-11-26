package com.android.teklipyaz.models.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityResult {

    @SerializedName("meta")
    @Expose
    private MetaModel meta;
    @SerializedName("results")
    @Expose
    private List<City> results = null;

    public MetaModel getMetaModel() {
        return meta;
    }

    public void setMetaModel(MetaModel meta) {
        this.meta = meta;
    }

    public List<City> getCityModels() {
        return results;
    }

    public void setCityModels(List<City> results) {
        this.results = results;
    }
}