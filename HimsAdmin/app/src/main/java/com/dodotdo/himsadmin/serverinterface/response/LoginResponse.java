package com.dodotdo.himsadmin.serverinterface.response;

/**
 * Created by Omjoon on 16. 3. 28..
 */
public class LoginResponse {
    String token;
    String position;
    String state;
    String userid;
    String firstName;
    String middleName;
    String lastName;


    public String getToken() {
        return token;
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
