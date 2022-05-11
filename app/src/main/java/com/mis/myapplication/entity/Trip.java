package com.mis.myapplication.entity;

import java.io.Serializable;

public class Trip implements Serializable
{

    public Trip(){

    }
    public Trip(String id,String title, String imageLink, String startDate, String endDate, double price, String longitude, String latitude) {
        this.title=title;
        this.imageLink=imageLink;
        this.id=id;
        this.startDate=startDate;
        this.endDate=endDate;
        this.price=price;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected=selected;
    }

    private  boolean isSelected;
    private String title,imageLink,startDate,endDate;

    private String longitude, latitude;

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title=title;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink=imageLink;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate=startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate=endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price=price;
    }

    private  double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
    }

    private String id;




}
