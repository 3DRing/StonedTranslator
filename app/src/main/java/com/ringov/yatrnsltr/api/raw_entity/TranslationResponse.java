package com.ringov.yatrnsltr.api.raw_entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class TranslationResponse {
    @SerializedName("code")
    int code;

    @SerializedName("leng")
    String lang;

    @SerializedName("text")
    String text;
}
