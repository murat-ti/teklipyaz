package com.android.teklipyaz.models.entities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrganizationResult {

    @SerializedName("meta")
    @Expose
    private MetaModel meta;
    @SerializedName("results")
    @Expose
    private List<Organization> results = null;

    public MetaModel getMetaModel() {
        return meta;
    }

    public void setMetaModel(MetaModel meta) {
        this.meta = meta;
    }

    public List<Organization> getOrganizations() {
        return results;
    }

    public void setOrganizations(List<Organization> results) {
        this.results = results;
    }

}
