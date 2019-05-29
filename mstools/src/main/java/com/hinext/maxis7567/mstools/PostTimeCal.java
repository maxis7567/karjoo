package com.hinext.maxis7567.mstools;

public class PostTimeCal {

    public static String Calculator(long unix){
        if (unix<60){
            return (String.valueOf(unix)+" ثانیه پیش");
        }else if(unix < 3600){
            return (String.valueOf(unix/60)+" دقیقه پیش");
        }else if(unix < 86400){
            return (String.valueOf(unix/3600)+" ساعت پیش");
        }else if(unix < 2592000){
            return (String.valueOf(unix/86400)+" روز پیش");
        }else {
            return (JalaliCalendar.gregorian_to_jalali(String.valueOf(unix)).substring(0,11));
        }
    }
}
