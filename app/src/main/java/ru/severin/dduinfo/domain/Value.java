package ru.severin.dduinfo.domain;

import com.google.gson.annotations.SerializedName;

public class Value {
    @SerializedName("fieldKey")
    public String Key;

    @SerializedName("fieldName")
    public String Name;

    @SerializedName("fieldPercent")
    public String Percent;

    @SerializedName("fieldValue")
    public String Value;
}
