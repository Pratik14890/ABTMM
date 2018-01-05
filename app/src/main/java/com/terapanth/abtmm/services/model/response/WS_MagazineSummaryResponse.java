package com.terapanth.abtmm.services.model.response;

import com.terapanth.abtmm.services.model.Magazine;
import com.terapanth.abtmm.services.model.MagazineSummary;

/**
 * Created by Suraj Prajapati on 1/4/2018.
 */

public class WS_MagazineSummaryResponse {

    String ResponseMessage;
    String Status;
    String MagazineId;
    String MagazineName;
    String CoverImageUrl;
    String Title;
    String Type;
    String PdfUrl;
    String PostedDate;

    public MagazineSummary getMagzineSuammary(){
        return new MagazineSummary(this.MagazineId, this.MagazineName, this.CoverImageUrl,
                this.Title, this.Type, this.PdfUrl, this.PostedDate);
    }

}
