package com.terapanth.abtmm.services.model.news;

import java.io.Serializable;

public class SlideShowImage implements Serializable {

    String imageUrl;

    public SlideShowImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl1() {
        return imageUrl;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl = imageUrl1;
    }
}
