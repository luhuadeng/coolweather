package com.hnevc.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/9/24.
 */

public class Now {
    /**
     * cond : {"txt":"阵雨"}
     * tmp : 21
     */
    @SerializedName("tmp")
    public String temperator;
    @SerializedName("cond")
    public More more;
    public class More{
        @SerializedName("txt")
        public String info;
    }
}
