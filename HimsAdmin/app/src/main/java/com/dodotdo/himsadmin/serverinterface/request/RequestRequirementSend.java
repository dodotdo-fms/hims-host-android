package com.dodotdo.himsadmin.serverinterface.request;

import java.util.List;

/**
 * Created by Omjoon on 16. 4. 21..
 */
public class RequestRequirementSend {
    String issue;
    List<String > requirementEmployee;

    public RequestRequirementSend(String issue, List<String> requirementEmployee) {
        this.issue = issue;
        this.requirementEmployee = requirementEmployee;
    }
}
