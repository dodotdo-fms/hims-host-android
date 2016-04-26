package com.dodotdo.himsadmin.model;

import java.util.Date;

/**
 * Created by Omjoon on 16. 4. 20..
 */
public class RoomStatus {
    String FOStatus;
    String roomNumber;
    Employee employee;
    String timestamp;

    public String getFOStatus() {
        return FOStatus;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
