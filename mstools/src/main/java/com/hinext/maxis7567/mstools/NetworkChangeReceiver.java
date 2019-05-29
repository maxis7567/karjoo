package com.hinext.maxis7567.mstools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                if (NetworkCheck.isNetworkAvailable(context)) {

                } else {

                }
            }
        }
    }

