package com.hinext.maxis7567.karjoo.services;

import android.content.Context;
import android.os.Handler;

import androidx.collection.ArrayMap;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hinext.maxis7567.karjoo.login.ActiveActivity;
import com.hinext.maxis7567.karjoo.models.Active;
import com.hinext.maxis7567.karjoo.models.ActiveResualt;
import com.hinext.maxis7567.karjoo.models.Create;
import com.hinext.maxis7567.karjoo.models.Detail;
import com.hinext.maxis7567.karjoo.models.HomeData;
import com.hinext.maxis7567.karjoo.models.Jobs;
import com.hinext.maxis7567.karjoo.models.ListData;
import com.hinext.maxis7567.karjoo.models.User;
import com.maxis7567.msvolley.JsonRequest;
import com.maxis7567.msvolley.RawRequest;
import com.maxis7567.msvolley.RequestQueueContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Api {
    private static String SERVER_ADDRESS = "http://192.168.1.47:8080/v1/";
    public static String SERVER_ADDRESS_IMAGE = "http://192.168.1.47";
    private static RetryPolicy retryPolicy10_3=new DefaultRetryPolicy(100000,0,0);
    private static RetryPolicy retryPolicy6_3=new DefaultRetryPolicy(6000,0,0);
    private static RetryPolicy retryPolicy6_1=new DefaultRetryPolicy(6000,0,0);
    private static RetryPolicy retryPolicy3_3=new DefaultRetryPolicy(3000,0,0);
    public static int random3to10(){
        Random rn = new Random();
        int n = 5000 - 1000 + 1;
        int i = rn.nextInt() % n;
        return 1000+i;
    }
    public static void checkVersion(final Context context, Response.Listener<String> DataListener, Response.ErrorListener errorListener, String version) {

        final RawRequest<String> a = new RawRequest<>(JsonRequest.Method.GET,
                SERVER_ADDRESS + "version/android/"+version, DataListener, errorListener);
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

    public static void getUserData(final Context context, Response.Listener<User> dataListener, Response.ErrorListener errorListener) {
        Map<String, String> headers = new ArrayMap<>();
        headers.put("token", DataBaseTokenID.GetTokenID(context));
        final JsonRequest<User> a = new JsonRequest<>(JsonRequest.Method.GET,
                SERVER_ADDRESS + "user/get", headers,User.class, dataListener, errorListener);
        a.setRetryPolicy(retryPolicy3_3);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RequestQueueContainer.getRequestQueueContainer(context).add(a);
            }
        },random3to10());
    }

    public static void getHomeData(final Context context, Response.Listener<List<HomeData>> dataListener, Response.ErrorListener errorListener,int page) {
        Map<String, String> headers = new ArrayMap<>();
        headers.put("token", DataBaseTokenID.GetTokenID(context));
        final JsonRequest<List<HomeData>> a = new JsonRequest<>(JsonRequest.Method.GET,
                SERVER_ADDRESS + "get/home/"+String.valueOf(page), headers,new TypeToken<ArrayList<HomeData>>(){}.getType(), dataListener, errorListener);
        a.setRetryPolicy(retryPolicy3_3);
        if (page==1) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RequestQueueContainer.getRequestQueueContainer(context).add(a);
                }
            }, random3to10());
        }else RequestQueueContainer.getRequestQueueContainer(context).add(a);
    }
    public static void getOfferData(final Context context, Response.Listener<List<HomeData>> dataListener, Response.ErrorListener errorListener,int page) {
        Map<String, String> headers = new ArrayMap<>();
        headers.put("token", DataBaseTokenID.GetTokenID(context));
        final JsonRequest<List<HomeData>> a = new JsonRequest<>(JsonRequest.Method.GET,
                SERVER_ADDRESS + "get/offer/"+String.valueOf(page), headers,new TypeToken<ArrayList<HomeData>>(){}.getType(), dataListener, errorListener);
        a.setRetryPolicy(retryPolicy3_3);
        if (page==1) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RequestQueueContainer.getRequestQueueContainer(context).add(a);
                }
            }, random3to10());
        }else RequestQueueContainer.getRequestQueueContainer(context).add(a);
    }

    public static void jobSearch( Context context, Response.Listener<List<Jobs>> dataListener, Response.ErrorListener errorListener,String q) {
        Map<String, String> headers = new ArrayMap<>();
        headers.put("token", DataBaseTokenID.GetTokenID(context));
        final JsonRequest<List<Jobs>> a = new JsonRequest<>(JsonRequest.Method.GET,
                SERVER_ADDRESS + "get/offer/search/"+q, headers,new TypeToken<ArrayList<Jobs>>(){}.getType(), dataListener, errorListener);
        a.setRetryPolicy(retryPolicy3_3);
        RequestQueueContainer.getRequestQueueContainer(context).add(a);
    }

    public static void getOfferSearched(Context context, Response.Listener<List<HomeData>> dataListener, Response.ErrorListener errorListener,int id) {
        Map<String, String> headers = new ArrayMap<>();
        headers.put("token", DataBaseTokenID.GetTokenID(context));
        final JsonRequest<List<HomeData>> a = new JsonRequest<>(JsonRequest.Method.GET,
                SERVER_ADDRESS +"get/offer/searchId/"+String.valueOf(id)+"/1", headers,new TypeToken<ArrayList<HomeData>>(){}.getType(), dataListener, errorListener);
        a.setRetryPolicy(retryPolicy3_3);
        RequestQueueContainer.getRequestQueueContainer(context).add(a);
    }
    public static void getRequestData(final Context context, Response.Listener<List<HomeData>> dataListener, Response.ErrorListener errorListener,int page) {
        Map<String, String> headers = new ArrayMap<>();
        headers.put("token", DataBaseTokenID.GetTokenID(context));
        final JsonRequest<List<HomeData>> a = new JsonRequest<>(JsonRequest.Method.GET,
                SERVER_ADDRESS + "get/request/"+String.valueOf(page), headers,new TypeToken<ArrayList<HomeData>>(){}.getType(), dataListener, errorListener);
        a.setRetryPolicy(retryPolicy3_3);
        if (page==1) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RequestQueueContainer.getRequestQueueContainer(context).add(a);
                }
            }, random3to10());
        }else RequestQueueContainer.getRequestQueueContainer(context).add(a);
    }
    public static void requestSearch(Context context, Response.Listener<List<Jobs>> listListener, Response.ErrorListener errorListener, String q) {
        Map<String, String> headers = new ArrayMap<>();
        headers.put("token", DataBaseTokenID.GetTokenID(context));
        final JsonRequest<List<Jobs>> a = new JsonRequest<>(JsonRequest.Method.GET,
                SERVER_ADDRESS + "get/request/search/"+q, headers,new TypeToken<ArrayList<Jobs>>(){}.getType(), listListener, errorListener);
        a.setRetryPolicy(retryPolicy3_3);
        RequestQueueContainer.getRequestQueueContainer(context).add(a);
    }
    public static void getRequestSearched(Context context, Response.Listener<List<HomeData>> dataListener, Response.ErrorListener errorListener,int id) {
        Map<String, String> headers = new ArrayMap<>();
        headers.put("token", DataBaseTokenID.GetTokenID(context));
        final JsonRequest<List<HomeData>> a = new JsonRequest<>(JsonRequest.Method.GET,
                SERVER_ADDRESS +"get/request/searchId/"+String.valueOf(id)+"/1", headers,new TypeToken<ArrayList<HomeData>>(){}.getType(), dataListener, errorListener);
        a.setRetryPolicy(retryPolicy3_3);
        RequestQueueContainer.getRequestQueueContainer(context).add(a);
    }

    public static void getDetail(final Context context, final Response.Listener<Detail> dataListener, final Response.ErrorListener errorListener, final int id, final int type) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Map<String, String> headers = new ArrayMap<>();
                headers.put("token", DataBaseTokenID.GetTokenID(context));
                JsonRequest<Detail> a;
                if (type==1){
                     a = new JsonRequest<>(JsonRequest.Method.GET,
                            SERVER_ADDRESS +"get/request/detail/"+String.valueOf(id), headers,Detail.class, dataListener, errorListener);

                }else {

                    a = new JsonRequest<>(JsonRequest.Method.GET,
                            SERVER_ADDRESS +"get/offer/detail/"+String.valueOf(id), headers,Detail.class, dataListener, errorListener);
                }
                a.setRetryPolicy(retryPolicy3_3);
                RequestQueueContainer.getRequestQueueContainer(context).add(a);
            }
        }, random3to10());
    }

    public static void getList(final Context context, Response.Listener<List<ListData>> dataListener, Response.ErrorListener errorListener) {
        Map<String, String> headers = new ArrayMap<>();
        headers.put("token", DataBaseTokenID.GetTokenID(context));
        final JsonRequest<List<ListData>> a = new JsonRequest<>(JsonRequest.Method.GET,
                SERVER_ADDRESS + "user/get/list/", headers,new TypeToken<ArrayList<ListData>>(){}.getType(), dataListener, errorListener);
        a.setRetryPolicy(retryPolicy3_3);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RequestQueueContainer.getRequestQueueContainer(context).add(a);
            }
        },random3to10());
    }
    public static void addTags(final Context context, Response.Listener<String> dataListener, Response.ErrorListener errorListener,String name,String type) {
        Map<String, String> headers = new ArrayMap<>();
        headers.put("token", DataBaseTokenID.GetTokenID(context));
        RawRequest<String> a = new RawRequest<>(RawRequest.Method.GET,
                SERVER_ADDRESS + "tags/add/"+name+"/"+String.valueOf(type), headers, dataListener, errorListener);
        a.setRetryPolicy(retryPolicy3_3);
        RequestQueueContainer.getRequestQueueContainer(context).add(a);
    }

    public static void createList(final Context context, Response.Listener<String> dataListener, Response.ErrorListener errorListener, Create create) {
        Gson gson=new Gson();
        Map<String, String> headers = new ArrayMap<>();
        headers.put("Content-Type","application/json");
        headers.put("token", DataBaseTokenID.GetTokenID(context));
        JsonRequest<String> a = new JsonRequest<>(RawRequest.Method.POST,
                SERVER_ADDRESS + "user/create/list/",gson.toJson(create),headers,String.class, dataListener, errorListener);
        a.setRetryPolicy(retryPolicy3_3);
        RequestQueueContainer.getRequestQueueContainer(context).add(a);
    }
}
