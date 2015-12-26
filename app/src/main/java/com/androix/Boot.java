package com.androix;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Rafael on 16/10/15.
 */
public class Boot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, Kernel.class);
        context.startService(i);
    }
}
