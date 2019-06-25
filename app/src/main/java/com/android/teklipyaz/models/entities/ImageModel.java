package com.android.teklipyaz.models.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageModel {

    @SerializedName("itemId")
    @Expose
    private String itemId;
    @SerializedName("filepath")
    @Expose
    private String filepath;
    @SerializedName("isMain")
    @Expose
    private Object isMain;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Object getIsMain() {
        return isMain;
    }

    public void setIsMain(Object isMain) {
        this.isMain = isMain;
    }

}