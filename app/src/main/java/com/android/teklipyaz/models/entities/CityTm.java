package com.android.teklipyaz.models.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.android.teklipyaz.models.repositories.local.DBConstant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = DBConstant.CITY_TABLE)
public class CityTm {

    @PrimaryKey/*(autoGenerate = true)*/
    @NonNull
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title_tm")
    @Expose
    private String titleTm;
    @SerializedName("title_ru")
    @Expose
    private String titleRu;
    @SerializedName("title_en")
    @Expose
    private String titleEn;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitleTm() {
        return titleTm;
    }

    public void setTitleTm(String titleTm) {
        this.titleTm = titleTm;
    }

    public String getTitleRu() {
        return titleRu;
    }

    public void setTitleRu(String titleRu) {
        this.titleRu = titleRu;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTitle() {
        return titleRu;
    }

    public String getTitle(String lang) {
        switch (lang){
            case "tm": title = titleTm; break;
            case "ru": title = titleRu; break;
            default: title = titleEn; break;
        }
        return title;
    }

    public void setTitle(String title) {
        this.titleTm = title;
    }
}