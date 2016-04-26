package com.dodotdo.himsadmin.model;

import com.dodotdo.himsadmin.serverinterface.response.LoginResponse;

/**
 * Created by Omjoon on 16. 4. 21..
 */
public class MyAccount {
    static MyAccount instance;
    public static MyAccount getInstance(){
        if(instance == null){
            instance = new MyAccount();
        }

        return instance;
    }

    LoginResponse data;

    public LoginResponse getData() {
        return data;
    }

    public void setData(LoginResponse data) {
        this.data = data;
    }
}
