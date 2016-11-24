package com.ggktech.bookmydrive;

/**
 * Created by rajan.kali on 11/16/2016.
 */

public class Panel {
    private int id;
    private String name;

    public Panel(String name) {
        this.name = name;
    }

    public Panel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
