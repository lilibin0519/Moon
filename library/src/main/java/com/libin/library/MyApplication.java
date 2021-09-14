package com.libin.library;

import android.app.Application;
import android.content.Context;

import com.hjq.toast.ToastUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Created by admin on 2016/5/3.
 */
public class MyApplication extends Application {

    private static Context appContext;

    private static MyApplication self;

    public static Context getAppContext(){
        return appContext;
    }

    public static MyApplication getApp(){
        return self;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        self = this;
        appContext = getApplicationContext();
//        closeAndroidPDialog();
        ToastUtils.init(this);
    }

    private void closeAndroidPDialog(){
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
