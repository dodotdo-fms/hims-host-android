package com.dodotdo.himsadmin.serverinterface.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Omjoon on 16. 4. 18..
 */
public class Results<T> {
    @SerializedName("results")
    T result;

    public T getResult() {
        return result;
    }
}
