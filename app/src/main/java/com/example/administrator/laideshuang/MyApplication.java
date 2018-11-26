package com.example.administrator.laideshuang;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechUtility;

/**
 * Created by Administrator on 2018\11\15 0015.
 */

public class MyApplication extends Application {
    private static Context context;
//    SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5be941e0");
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SpeechUtility.createUtility(this, "appid=5be941e0");//=号后面写自己应用的APPID
        Setting.setShowLog(true); //设置日志开关（默认为true），设置成false时关闭语音云SDK日志打印
        //TTSUtils.getInstance().init(); 初始化工具类
    }

    //获取应用上下文环境
    public static Context getContext() {
        return context;
    }

}
