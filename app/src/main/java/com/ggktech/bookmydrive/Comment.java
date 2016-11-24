package com.ggktech.bookmydrive;

/**
 * Created by rajan.kali on 11/23/2016.
 * Comment Table Data Object
 */

public class Comment {
    private int driverId;
    private String name;
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
