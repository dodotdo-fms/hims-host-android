package com.dodotdo.himsadmin.serverinterface.request;

/**
 * Created by Omjoon on 16. 2. 2..
 */
public class SendMessageRequest {
    String msg_text;

    public SendMessageRequest(String msg_text){
        this.msg_text = msg_text;
    }
}
