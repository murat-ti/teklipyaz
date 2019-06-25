package com.android.teklipyaz.models.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "organization_category_table")
public class OrganizationCategory {

    @PrimaryKey/*(autoGenerate = true)*/
    @NonNull
    @SerializedName("id")
    @Expose
    private String id;

    @NonNull
    @ColumnInfo(name = "title_tm")
    @SerializedName("title_tm")
    @Expose
    private String titleTm;

    @NonNull
    @ColumnInfo(name = "title_ru")
    @SerializedName("title_ru")
    @Expose
    private String titleRu;

    @NonNull
    @ColumnInfo(name = "title_en")
    @SerializedName("title_en")
    @Expose
    private String titleEn;

    @NonNull
    @ColumnInfo(name = "slug")
    @SerializedName("slug")
    @Expose
    private String slug;

    @ColumnInfo(name = "icon")
    @SerializedName("icon")
    @Expose
    private String icon;

    @NonNull
    @ColumnInfo(name = "status")
    @SerializedName("status")
    @Expose
    private String status;

    @NonNull
    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @NonNull
    @ColumnInfo(name = "updated_at")
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @NonNull
    @ColumnInfo(name = "order_by")
    @SerializedName("order_by")
    @Expose
    private String orderBy;

    @Ignore
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
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