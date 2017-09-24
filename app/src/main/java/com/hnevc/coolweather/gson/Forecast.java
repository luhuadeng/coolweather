package com.hnevc.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/9/24.
 */

public class Forecast {

    /**
     * date : 2017-09-24
     * cond : {"txt_d":"阵雨"}
     * tmp : {"max":"23","min":"21"}
     */

    public String date;
    @SerializedName("cond")
    public More more;
    @SerializedName("tmp")
    public Temperature temperature;



    public static class More {
        /**
         * txt_d : 阵雨
         */
        @SerializedName("txt_d")
        public String info;

    }

    public static class Temperature {
        /**
         * max : 23
         * min : 21
         */
        public String max;
        public String min;

    }
}
