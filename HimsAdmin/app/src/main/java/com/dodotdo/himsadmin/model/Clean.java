package com.dodotdo.himsadmin.model;

/**
 * Created by Omjoon on 16. 4. 20..
 */
public class Clean {
    String id;
    Employee attendant;
    String assignDate;
    String roomNumber;

    public String getId() {
        return id;
    }

    public Employee getEmployee() {
        return attendant;
    }

    public String getAssignDate() {
        return assignDate;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}
