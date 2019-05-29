package com.hinext.maxis7567.karjoo.services;

import android.content.Context;
import android.os.Handler;

import androidx.collection.ArrayMap;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.hinext.maxis7567.karjoo.login.ActiveActivity;
import com.hinext.maxis7567.karjoo.models.Active;
import com.hinext.maxis7567.karjoo.models.ActiveResualt;
import com.maxis7567.msvolley.JsonRequest;
import com.maxis7567.msvolley.RawRequest;
import com.maxis7567.msvolley.RequestQueueContainer;

import java.util.Map;
import java.util.Random;

public class Api {
    private static String SERVER_ADDRESS = "http://10.0.2.2/v1/";
    public static String SERVER_ADDRESS_IMAGE = "http://manage.lampaapp.ir/";
    private static RetryPolicy retryPolicy10_3=new DefaultRetryPolicy(100000,3,1000);
    private static RetryPolicy retryPolicy6_3=new DefaultRetryPolicy(6000,3,1000);
    private static RetryPolicy retryPolicy6_1=new DefaultRetryPolicy(6000,1,1);
    private static RetryPolicy retryPolicy3_3=new DefaultRetryPolicy(3000,3,1);
    public static int random3to10(){
        Random rn = new Random();
        int n = 10000 - 3000 + 1;
        int i = rn.nextInt() % n;
        return 3000+i;
    }
    public static void checkVersion(final Context context, Response.Listener<String> DataListener, Response.ErrorListener errorListener, String version) {
        Map<String, String> headers = new ArrayMap<>();
        headers.put("Accept", "application/json");
        final RawRequest<String> a = new RawRequest<>(JsonRequest.Method.GET,
                SERVER_ADDRESS + "version/android/"+version,headers, DataListener, errorListener);
        a.setRetryPolicy(retryPolicy3_3);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RequestQueueContainer.getRequestQueueContainer(context).add(a);
            }
        },random3to10());
    }

    public static void sendPhoneNumber(final Context context, Response.Listener<Active> dataListener, Response.ErrorListener errorListener, String number) {
        final JsonRequest<Active> a = new JsonRequest<>(JsonRequest.Method.GET,
                SERVER_ADDRESS + "active/create/"+number,Active.class, dataListener, errorListener);
        a.setRetryPolicy(retryPolicy3_3);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RequestQueueContainer.getRequestQueueContainer(context).add(a);
            }
        },random3to10());
    }
    public static void sendCode(final Context context, Response.Listener<ActiveResualt> dataListener, Response.ErrorListener errorListener, String number,String code) {
        final JsonRequest<ActiveResualt> a = new JsonRequest<>(JsonRequest.Method.GET,
                SERVER_ADDRESS + "active/"+code+"/"+number,ActiveResualt.class, dataListener, errorListener);
        a.setRetryPolicy(retryPolicy3_3);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RequestQueueContainer.getRequestQueueContainer(context).add(a);
            }
        },random3to10());
    }
}
