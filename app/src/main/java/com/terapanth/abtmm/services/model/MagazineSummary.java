package com.terapanth.abtmm.services.model;

/**
 * Created by Suraj Prajapati on 1/5/2018.
 */

public class MagazineSummary {
    String id;
    String name;
    String coverImageUrl;
    String title;
    String type;
    String pdfUrl;
    String postedDate;

    public MagazineSummary(String id, String name, String coverImageUrl, String title, String type, String pdfUrl, String postedDate) {
        this.id = id;
        this.name = name;
        this.coverImageUrl = coverImageUrl;
        this.title = title;
        this.type = type;
        this.pdfUrl = pdfUrl;
        this.postedDate = postedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }
}
