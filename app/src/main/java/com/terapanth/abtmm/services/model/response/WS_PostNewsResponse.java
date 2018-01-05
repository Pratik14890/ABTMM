package com.terapanth.abtmm.services.model.response;

import com.terapanth.abtmm.services.model.Magazine;
import com.terapanth.abtmm.services.model.news.NewsSummary;
import com.terapanth.abtmm.services.model.news.PostNews;

/**
 * Created by Suraj Prajapati on 1/4/2018.
 */

public class WS_PostNewsResponse {

    String ResponseMessage;
    String Status;

    public PostNews getPostResult(){
        return new PostNews(this.ResponseMessage, this.Status);
    }
}
