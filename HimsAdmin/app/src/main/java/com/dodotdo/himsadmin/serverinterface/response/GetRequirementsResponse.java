package com.dodotdo.himsadmin.serverinterface.response;

import com.dodotdo.himsadmin.model.Requirement;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Omjoon on 16. 2. 2..
 */
public class GetRequirementsResponse {
    @SerializedName("results")
    List<Requirement> lists;

    public List<Requirement> getLists() {
        return lists;
    }
}
