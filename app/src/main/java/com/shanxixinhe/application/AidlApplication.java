package com.shanxixinhe.application;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jl.Ddi;
import com.jl.pinpad.IRemotePinpad;
import com.jollytech.app.Platform;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.List;

/**
 * Created by fu on 2018/3/29.
 */

public class AidlApplication extends Application {

    private static Ddi hal;
    private static IRemotePinpad pinpad;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        ConfigLog();
        context = this;
        connectPinpad();
        Logger.d("init app");
    }

    public static Ddi getinstanceDdi(){
        if (null == hal){
            hal = new Ddi();
            hal.ddi_ddi_sys_init();
        }
        return hal;
    }

    public static IRemotePinpad getPinpad(){
        if (pinpad == null){
            return null;
        }

        return pinpad;
    }


    public static Context getContext(){

        return context;
    }

    private void ConfigLog(){
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("=AIDL=")
                .build();
        Logger.addLogAdapter(new LoogerAdapter(formatStrategy));
    }

    public class LoogerAdapter extends AndroidLogAdapter {

        public LoogerAdapter(@NonNull FormatStrategy formatStrategy) {
            super(formatStrategy);
        }

        @Override
        public boolean isLoggable(int priority, @Nullable String tag) {
            return true;
        }
    }


    ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            // TODO Auto-generated method stub
            Log.i(getClass().getName(), "pinpad disconnected!");
            pinpad = null;
        }

        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            // TODO Auto-generated method stub
            Log.i(getClass().getName(), "pinpad connected!!!!");
            AidlApplication.pinpad = IRemotePinpad.Stub.asInterface(arg1);
            Platform.setPinpad(AidlApplication.pinpad);
        }
    };

    private void connectPinpad()
    {
        Logger.d("连接pind1");
        Intent service = new Intent("com.remote.service.PINPAD");
        Intent eintent = new Intent(getExplicitIntent(this, service));
        bindService(eintent, connection, Context.BIND_AUTO_CREATE);
        Logger.d("连接pind2");

    }

    public static Intent getExplicitIntent(Context context,
                                           Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent,
                0);
        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);
        // Set the component to be explicit
        explicitIntent.setComponent(component);
        return explicitIntent;
    }

}
