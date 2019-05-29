package com.hinext.maxis7567.mstools;

import java.text.DecimalFormat;

import static com.hinext.maxis7567.mstools.FaNumToEnNum.convertTofa;


public class priceConvertor {
    public static String Convert(long price){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        return (decimalFormat.format(price));
    }

}
