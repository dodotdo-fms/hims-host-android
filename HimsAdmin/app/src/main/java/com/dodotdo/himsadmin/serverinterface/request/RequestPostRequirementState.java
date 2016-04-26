package com.dodotdo.himsadmin.serverinterface.request;

import java.util.List;

/**
 * Created by Omjoon on 16. 4. 21..
 */
public class RequestPostRequirementState {
    List<String> userList;

    public RequestPostRequirementState(List<String> userList) {
        this.userList = userList;
    }
}
