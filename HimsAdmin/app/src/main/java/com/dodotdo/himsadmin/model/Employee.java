package com.dodotdo.himsadmin.model;

/**
 * Created by Omjoon on 16. 3. 28..
 */
public class Employee {
    String position;
    String state;
    String userid;
    String firstName;
    String middleName;
    String lastName;


    public Employee(String position, String state, String userid, String firstName, String middleName, String lastName) {
        this.position = position;
        this.state = state;
        this.userid = userid;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public String getState() {
        return state;
    }

    public String getUserid() {
        return userid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }
}
