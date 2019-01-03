package com.saneesh.serviceapp.Model;

import java.io.Serializable;

/**
 * Created by saNeesH on 2018-10-25.
 */

public class OnlineModel implements Serializable {

    String name;

    String imagePath;

    String url;

    String descr;

    String rating;

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
