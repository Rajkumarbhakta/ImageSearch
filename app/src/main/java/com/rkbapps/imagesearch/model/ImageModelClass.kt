package com.rkbapps.imagesearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageModelClass {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("per_page")
    @Expose
    private Integer perPage;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("next_page")
    @Expose
    private String nextPage;
    @SerializedName("prev_page")
    @Expose
    private String prevPage;


    public ImageModelClass() {
    }

    public ImageModelClass(Integer page, Integer perPage, List<Photo> photos, Integer totalResults, String nextPage, String prevPage) {
        this.page = page;
        this.perPage = perPage;
        this.photos = photos;
        this.totalResults = totalResults;
        this.nextPage = nextPage;
        this.prevPage = prevPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(String prevPage) {
        this.prevPage = prevPage;
    }

}
