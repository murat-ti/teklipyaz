package com.android.teklipyaz.models.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetaModel {

    @SerializedName("currentPage")
    @Expose
    private int currentPage;
    @SerializedName("perPage")
    @Expose
    private int perPage;
    @SerializedName("totalCount")
    @Expose
    private int totalCount;
    @SerializedName("pageCount")
    @Expose
    private int pageCount;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
