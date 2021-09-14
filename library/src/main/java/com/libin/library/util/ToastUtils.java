package com.libin.library.util;

import android.view.Gravity;


public class ToastUtils {

    public static void showToastLong(String content) {
//        setToast(content, Toast.LENGTH_LONG).show();
        com.hjq.toast.ToastUtils.show(content);
    }

    public static void showToastShort(String content) {
//        setToast(content, Toast.LENGTH_SHORT).show();
        com.hjq.toast.ToastUtils.show(content);
    }

    public static void showToastShort(int textId) {
//        showToastShort(MyApplication.getAppContext().getString(textId));
        com.hjq.toast.ToastUtils.show(textId);
    }

    public static void showToastLong(int textId) {
//        showToastLong(MyApplication.getAppContext().getString(textId));
        com.hjq.toast.ToastUtils.show(textId);
    }
    
    public static void showToastGravityCenter(String content){
//        setToastGravity(content, Toast.LENGTH_SHORT, Gravity.CENTER, 0, 0).show();
        com.hjq.toast.ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        com.hjq.toast.ToastUtils.show(content);
    }
}
