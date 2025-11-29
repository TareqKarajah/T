package com.example.t;

import java.io.Serializable;

public class JapanPlace implements Serializable {
    private String name;
    private String description;
    private int imageResourceId;

    public JapanPlace(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
