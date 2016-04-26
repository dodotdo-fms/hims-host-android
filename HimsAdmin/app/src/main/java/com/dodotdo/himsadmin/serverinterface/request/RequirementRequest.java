package com.dodotdo.himsadmin.serverinterface.request;

/**
 * Created by Omjoon on 16. 2. 2..
 */
public class RequirementRequest {
    String info;
    String within;
    String room_num;
    String requirement;
    public RequirementRequest(String room_num,String requirement,String info, String within){
        this.requirement = requirement;
        this.room_num = room_num;
        this.info = info;
        this.within = within;
    }
}
