package com.dodotdo.himsadmin.model;

import java.util.List;

/**
 * Created by Omjoon on 16. 4. 22..
 */
public class Floor {
    String floor;
    int count;
    public Floor(String floor, int count) {
        this.floor = floor;
        this.count = count;
    }

    public String getFloor() {
        return floor;
    }

    public int getCount() {
        return count;
    }
}
