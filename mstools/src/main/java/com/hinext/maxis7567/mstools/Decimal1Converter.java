package com.hinext.maxis7567.mstools;

public class Decimal1Converter {
    public static String Convetrt(String a){
        if (a!=null){
            if (a.length()>1){
                a=a.substring(0,3);
                return a;
            }else {
                return a;
            }
        }else return "1";
    }
}
