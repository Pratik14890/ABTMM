package com.terapanth.abtmm.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.terapanth.abtmm.MainActivity;
import com.terapanth.abtmm.R;
import com.terapanth.abtmm.utils.NetworkUtils;

public class NetworkConnectivityCheckReceiver extends BroadcastReceiver {

    MainActivity activity = null;
    boolean displayAlertForNetworkFailure = false;

    public NetworkConnectivityCheckReceiver(MainActivity activity, boolean displayAlertForNetworkFailure) {
        this.activity = activity;
        this.displayAlertForNetworkFailure = displayAlertForNetworkFailure;
    }

    //needed to add empty constructor as it was giving exception if we are killing the app.
    public NetworkConnectivityCheckReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(!NetworkUtils.getConnectivityStatus(context)) {

            if (displayAlertForNetworkFailure) {
                activity.showAlertForNetworkFailure(context.getString(R.string.alert_failure_title), activity.getString(R.string.alert_network_failure_message));
            }
        }
    }

}
