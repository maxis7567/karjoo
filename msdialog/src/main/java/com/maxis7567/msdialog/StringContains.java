package com.maxis7567.msdialog;

public class StringContains {
    public static boolean Spelling(String a, String b) {
        int l = a.length();
        String k;
        try {
            k = b.substring(0, l);
            return a.toLowerCase().contains(k.toLowerCase());

        } catch (Exception e) {
            return false;
        }

    }
}
