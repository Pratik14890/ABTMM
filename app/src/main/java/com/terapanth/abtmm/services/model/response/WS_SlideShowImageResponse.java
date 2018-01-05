package com.terapanth.abtmm.services.model.response;

import com.terapanth.abtmm.services.model.news.NewsSummary;
import com.terapanth.abtmm.services.model.news.SlideShowImage;

public class WS_SlideShowImageResponse {

    String ResponseMessage;
    String Status;
    String ImageUrl;

    public SlideShowImage getImages(){
        return new SlideShowImage(this.ImageUrl);
    }

}
