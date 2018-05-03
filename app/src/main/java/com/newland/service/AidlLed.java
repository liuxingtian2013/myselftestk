package com.newland.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;

import com.jl.Ddi;
import com.shanxixinhe.application.AidlApplication;
import com.orhanobut.logger.Logger;

/**
 * Created by fu on 2018/3/29.
 */

public class AidlLed extends com.newland.aidl.led.AidlLED.Stub {


    private Ddi hal;
    private String ACTION = "lenoff";
    private Context mContext;
    private boolean lenoff = false;
    private LocalBroadcastReceiver lbr;

    public AidlLed (){
        hal = AidlApplication.getinstanceDdi();
        mContext = AidlApplication.getContext();
        lbr = new LocalBroadcastReceiver();
    }

    private void sendBroadcast(){
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(
                new Intent(ACTION)
        );
    }

    private void registerLoginBroadcast(){
        IntentFilter intentFilter = new IntentFilter(ACTION);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(lbr,intentFilter);
    }

    private void unRegisterLoginBroadcast(){
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(lbr);
    }

    class LocalBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION)){
                lenoff = true;
                Logger.d("收到广播关闭led");
            }
        }
    }

    @Override
    public boolean ledOperation( int color, int operation, int times) throws RemoteException {

        hal.ddi_led_open();
        if (color == 2){
            color = 3;
        }
        if (color == 4){
            color = 2;
            //hal.ddi_led_sta_set(color,operation);
        }
        if (color == 8){
            color = 4;
            //hal.ddi_led_sta_set(color,operation);
        }
        if (color == 1){
            color = 1;
            //hal.ddi_led_sta_set(color,operation);
        }

        if (operation == 1){
            hal.ddi_led_sta_set(color,0);
        }
        if (operation == 2){
            hal.ddi_led_sta_set(color,1);
        }
        if (operation == 3){
            hal.ddi_led_sta_set(color,1);
            SystemClock.sleep(200);
            hal.ddi_led_sta_set(color,0);
        }

        final int colort = color;
        if (times != 0 && times < 0){
            hal.ddi_led_close();
            sendBroadcast();
            Logger.d("关闭led");
        }

        if (times == 0){
            //当闪烁次数为 0 时，为永久闪烁状态，非阻塞的
            new Thread(new Runnable() {

                @Override
                public void run() {
                    registerLoginBroadcast();
                    while (true){
                        hal.ddi_led_sta_set(colort,1);
                        SystemClock.sleep(200);
                        hal.ddi_led_sta_set(colort,0);
                        SystemClock.sleep(200);
                        if (lenoff){
                            unRegisterLoginBroadcast();
                            break;
                        }
                    }
                }
            }).start();

        }
        if (times > 0) {
            //有设置闪烁次数时，为阻塞状态。
            while (true){
                hal.ddi_led_sta_set(color,1);
                SystemClock.sleep(200);
                hal.ddi_led_sta_set(color,0);
                SystemClock.sleep(200);
                times--;
                Logger.d("times :" + times);
                if (times == 0){
                    hal.ddi_led_close();
                    sendBroadcast();
                    Logger.d("关闭led");
                    break;
                }
            }
        }
        return true;
    }

}
