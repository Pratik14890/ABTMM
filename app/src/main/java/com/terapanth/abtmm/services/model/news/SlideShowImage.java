package com.terapanth.abtmm.services.model.news;

import java.io.Serializable;

/**
 * Created by Suraj Prajapati on 1/5/2018.
 */

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
