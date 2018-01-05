package com.terapanth.abtmm.services.model.response;

import com.terapanth.abtmm.services.model.news.NewsSummary;

public class WS_NewsSummaryResponse {

    String ResponseMessage;
    String Status;
    String Title;
    String Description;
    String ImageUrl1;
    String ImageUrl2;
    String ImageUrl3;
    String ImageUrl4;
    String ImageUrl5;
    String PostedDate;
    String PostedBy;

    public NewsSummary getNews(){
        return new NewsSummary(this.Title, this.Description, this.ImageUrl1, this.ImageUrl2, this.ImageUrl3, this.ImageUrl4, this.ImageUrl5, this.PostedDate, this.PostedBy);
    }

}
