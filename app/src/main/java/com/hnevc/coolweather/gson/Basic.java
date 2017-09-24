package com.hnevc.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/9/24.
 */

public class Basic {
    /**
     * city : 吴江
     * cnty : 中国
     * id : CN101190407
     * lat : 31.16040421
     * lon : 120.64160156
     * update : {"loc":"2017-09-24 08:46","utc":"2017-09-24 00:46"}
     */

    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;
    public Update update;
    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }




}
