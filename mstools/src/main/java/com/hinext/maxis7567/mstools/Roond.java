package com.hinext.maxis7567.mstools;

public class Roond {

    public static String roond(String a){
        String b="";
        if (a.length()>1) {
            return String.valueOf(Integer.valueOf(a.charAt(0)) + 1);
        }else return a;
    }
}
