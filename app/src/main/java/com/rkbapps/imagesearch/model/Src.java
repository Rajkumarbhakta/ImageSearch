package com.rkbapps.imagesearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Src implements Serializable {

    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("large2x")
    @Expose
    private String large2x;
    @SerializedName("large")
    @Expose
    private String large;
    @SerializedName("medium")
    @Expose
    private String medium;
    @SerializedName("small")
    @Expose
    private String small;
    @SerializedName("portrait")
    @Expose
    private String portrait;
    @SerializedName("landscape")
    @Expose
    private String landscape;
    @SerializedName("tiny")
    @Expose
    private String tiny;

    public Src() {
    }

    public Src(String original, String large2x, String large, String medium, String small, String portrait, String landscape, String tiny) {
        this.original = original;
        this.large2x = large2x;
        this.large = large;
        this.medium = medium;
        this.small = small;
        this.portrait = portrait;
        this.landscape = landscape;
        this.tiny = tiny;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getLarge2x() {
        return large2x;
    }

    public void setLarge2x(String large2x) {
        this.large2x = large2x;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getLandscape() {
        return landscape;
    }

    public void setLandscape(String landscape) {
        this.landscape = landscape;
    }

    public String getTiny() {
        return tiny;
    }

    public void setTiny(String tiny) {
        this.tiny = tiny;
    }

    @Override
    public String toString() {
        return "Src{" +
                "original='" + original + '\'' +
                ", large2x='" + large2x + '\'' +
                ", large='" + large + '\'' +
                ", medium='" + medium + '\'' +
                ", small='" + small + '\'' +
                ", portrait='" + portrait + '\'' +
                ", landscape='" + landscape + '\'' +
                ", tiny='" + tiny + '\'' +
                '}';
    }
}
