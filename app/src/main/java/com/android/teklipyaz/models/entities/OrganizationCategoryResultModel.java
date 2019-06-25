package com.android.teklipyaz.models.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrganizationCategoryResultModel {

    @SerializedName("meta")
    @Expose
    private MetaModel meta;
    @SerializedName("results")
    @Expose
    private List<OrganizationCategory> results = null;

    public MetaModel getMetaModel() {
        return meta;
    }

    public void setMetaModel(MetaModel meta) {
        this.meta = meta;
    }

    public List<OrganizationCategory> getOrganizationCategoryModels() {
        return results;
    }

    public void setOrganizationCategoryModels(List<OrganizationCategory> results) {
        this.results = results;
    }
}