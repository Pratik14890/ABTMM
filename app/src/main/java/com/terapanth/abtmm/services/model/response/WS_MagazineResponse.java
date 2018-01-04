package com.terapanth.abtmm.services.model.response;

import com.terapanth.abtmm.services.model.Magazine;

/**
 * Created by Suraj Prajapati on 1/4/2018.
 */

public class WS_MagazineResponse {

    String ResponseMessage;
    String Status;
    String MagazineId;
    String MagazineName;
    String CoverImageUrl;

    public Magazine getMagzine(){
        return new Magazine(this.MagazineId, this.MagazineName, this.CoverImageUrl);
    }

}
