package com.libin.library.util;

import android.content.Context;
import android.text.format.Formatter;
import android.util.Patterns;

import com.libin.library.MyApplication;
import com.libin.library.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils {

	/**
	 *  判断字符串是否为空
	 *
	 * @param str
	 * @return   true   为空
	 * 			 false  不为空
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.isEmpty() || str.equalsIgnoreCase("null")) {
			return true;
		} else {
			return false;
		}
	}
	
	
    
    public static String longSizeToString(long size,Context ctx) {
		return Formatter.formatFileSize(ctx, size);
    }

	/**
	 * 邮箱 格式验证
	 * @param email
	 * @return
	 */
    public static boolean isEmail(String email) {
		if(isEmpty(email)){
			return false;
		}
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    
    /**
     * 手机号：手机号码格式验证
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles){
		if(isEmpty(mobiles)){
			return false;
		}
		 Pattern p = Pattern.compile("^1\\d{10}$");
		 Matcher m = p.matcher(mobiles);
		 return m.matches();
   }
    
    
    public static String getStringResource(int id, Context ctx){
        return ctx.getResources().getString(id);
    }
    
    
    public static String getStringResource(int id){
        return MyApplication.getAppContext().getResources().getString(id);
    }

	/**
	 * is url
	 *
	 * @param url
	 * @return
	 */
	public static boolean isUrl(String url) {
		return !isEmpty(url) && Patterns.WEB_URL.matcher(url).matches();
	}

    public static String getStingByArray(String[] cards){
    	String cardString = "";
		for (int i = 0; i < cards.length; i++) {
			if (i == cards.length-1) {
				cardString += cards[i];
			}else{
				cardString += cards[i] + ",";
			}
		}
		return cardString;
    }

	/**
	 * 判断字符串是否为null或全为空格
	 *
	 * @param s 待校验字符串
	 * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
	 */
	public static boolean isSpace(String s) {
		return (s == null || s.trim().length() == 0);
	}

	public static boolean isNumber(String str) {
		Pattern p = Pattern.compile("^[0-9]+$");
		Matcher m = p.matcher(str);
		return !isEmpty(str) && m.matches();
	}

	public static boolean isTimeStamp(String str) {
		Pattern p = Pattern.compile("^\\d{13}$");
		Matcher m = p.matcher(str);
		return !isEmpty(str) && m.matches();
	}
}
