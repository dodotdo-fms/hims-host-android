package com.dodotdo.himsadmin.serverinterface.request;

import java.util.Date;
import java.util.List;

/**
 * Created by Omjoon on 16. 4. 22..
 */
public class RequestAssignEmployeeToClean {
    List<String> assignCleanRoomList;
    public RequestAssignEmployeeToClean(List<String> assignCleanRoomList) {
        this.assignCleanRoomList = assignCleanRoomList;
    }
}
