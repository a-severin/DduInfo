package ru.severin.dduinfo.domain;

import com.google.gson.annotations.SerializedName;

public class BuildingRegion {
    @SerializedName("id")
    public String Id;

    @SerializedName("text")
    public String Text;

    @SerializedName("additional")
    public String Additional;

    @SerializedName("disabled")
    public boolean Disabled;
}
