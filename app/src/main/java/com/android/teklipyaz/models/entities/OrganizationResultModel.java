package com.android.teklipyaz.models.entities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrganizationResultModel {

    @SerializedName("meta")
    @Expose
    private MetaModel meta;
    @SerializedName("results")
    @Expose
    private List<OrganizationModel> results = null;

    public MetaModel getMetaModel() {
        return meta;
    }

    public void setMetaModel(MetaModel meta) {
        this.meta = meta;
    }

    public List<OrganizationModel> getOrganizationModels() {
        return results;
    }

    public void setOrganizationModels(List<OrganizationModel> results) {
        this.results = results;
    }

}
