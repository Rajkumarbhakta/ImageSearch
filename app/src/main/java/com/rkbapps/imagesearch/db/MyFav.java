package com.rkbapps.imagesearch.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "myfav")
public class MyFav {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "imageId")
    int imageId;

    @ColumnInfo(name = "height")
    int height;

    @ColumnInfo(name = "width")
    int width;

    @ColumnInfo(name = "photographer")
    String photographer;

    @ColumnInfo(name = "photographerId")
    int photographerId;

    @ColumnInfo(name = "originalImageLink")
    String originalImageLink;

    @ColumnInfo(name = "mediumImageLink")
    String mediumImageLink;

    @ColumnInfo(name = "alt")
    String alt;

    public MyFav(int imageId, int height, int width, String photographer, int photographerId, String originalImageLink, String mediumImageLink, String alt) {
        this.imageId = imageId;
        this.height = height;
        this.width = width;
        this.photographer = photographer;
        this.photographerId = photographerId;
        this.originalImageLink = originalImageLink;
        this.mediumImageLink = mediumImageLink;
        this.alt = alt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public int getPhotographerId() {
        return photographerId;
    }

    public void setPhotographerId(int photographerId) {
        this.photographerId = photographerId;
    }

    public String getOriginalImageLink() {
        return originalImageLink;
    }

    public void setOriginalImageLink(String originalImageLink) {
        this.originalImageLink = originalImageLink;
    }

    public String getMediumImageLink() {
        return mediumImageLink;
    }

    public void setMediumImageLink(String mediumImageLink) {
        this.mediumImageLink = mediumImageLink;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
