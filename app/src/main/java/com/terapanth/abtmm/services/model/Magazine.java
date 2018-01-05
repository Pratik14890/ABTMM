package com.terapanth.abtmm.services.model;

public class Magazine {
    String id;
    String name;
    String coverImageUrl;

    public Magazine(String id, String name, String coverImageUrl) {
        this.id = id;
        this.name = name;
        this.coverImageUrl = coverImageUrl;
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
}
