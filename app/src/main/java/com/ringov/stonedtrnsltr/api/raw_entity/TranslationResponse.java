package com.ringov.stonedtrnsltr.api.raw_entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class TranslationResponse {
    @SerializedName("code")
    int code;

    @SerializedName("messge")
    String message;

    @SerializedName("leng")
    String lang;

    @SerializedName("text")
    List<String> text;

    public List<String> getText() {
        return text;
    }

    public String getMessage() {
        return message;
    }
}
