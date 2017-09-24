package com.hnevc.coolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.hnevc.coolweather.WeatherActivity;
import com.hnevc.coolweather.gson.Weather;
import com.hnevc.coolweather.util.HttpUtil;
import com.hnevc.coolweather.util.Utility;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/9/24.
 */

public class AutoUpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateBingPic();
        updateWeather();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int aHour = 60*60*60*1000;//一个小时
        long triggerAtTime = SystemClock.elapsedRealtime()+aHour;
        Intent i = new Intent(this,AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this,0,i,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }
    private void updateWeather(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = sp.getString("weather",null);
        Weather weather = Utility.handleWeatherResponse(weatherString);
        final String weatherId = weather.basic.weatherId;
        String weatherUrl = "https://free-api.heweather.com/v5/weather?city="+weatherId
                +"&key=28b5ff1170f24921ab282a732dfae035";
        HttpUtil.sendOKHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Weather weather = Utility.handleWeatherResponse(responseText);
                if(weather!=null && "ok".equals(weather.status)){
                    SharedPreferences.Editor editor = PreferenceManager
                            .getDefaultSharedPreferences(AutoUpdateService.this)
                            .edit();
                    editor.putString("weather",responseText);
                    editor.apply();

                }
            }
        });
    }
    private void updateBingPic(){
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOKHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor spd = PreferenceManager
                        .getDefaultSharedPreferences(AutoUpdateService.this)
                        .edit();
                spd.putString("bing_pic",bingPic);
                spd.apply();
            }
        });
    }
}
