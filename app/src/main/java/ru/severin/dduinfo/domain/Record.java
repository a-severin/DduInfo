package ru.severin.dduinfo.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Record {

    @SerializedName("date")
    public String Date;

    public List<Value> Values;
}
