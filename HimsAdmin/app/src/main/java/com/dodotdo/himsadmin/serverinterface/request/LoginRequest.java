package com.dodotdo.himsadmin.serverinterface.request;

/**
 * Created by Omjoon on 16. 3. 28..
 */
public class LoginRequest {
    private String userid;
    private String password;
    private String registerId;

    public LoginRequest(String userId, String password, String registerId) {
        this.userid = userId;
        this.password = password;
        this.registerId = registerId;
    }


    public String getUserId() {
        return userid;
    }

    public String getPassword() {
        return password;
    }

    public String getRegisterId() {
        return registerId;
    }
}
