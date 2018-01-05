package com.terapanth.abtmm.services.model.news;

public class PostNews {

    String responseMessage;
    String status;

    public PostNews(String responseMessage, String status) {
        this.responseMessage = responseMessage;
        this.status = status;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
