package com.shanxixinhe.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.newland.aidl.deviceService.AidlDeviceService;
import com.newland.service.AidlBeeper;
import com.newland.service.AidlDeviceInfo;
import com.newland.service.AidlICCard;
import com.newland.service.AidlLed;
import com.newland.service.AidlPrinter;
import com.newland.service.AidlRFCard;
import com.newland.service.AidlScanner;
import com.newland.service.AidlSerialComm;
import com.newland.service.AidlTerminalManage;
import com.newland.service.PBOC;
import com.newland.service.Pinpad;

/**
 * Created by Administrator on 2018\3\22 0022.
 */

public class DeviceService extends Service {


    private Pinpad pinpad = null;


    public DeviceService() {

    }

    public IBinder onBind(Intent intent) {
        System.out.println("------------------DeviceService-----------------onBind");
        return new AidlDevice();
    }

    private class AidlDevice extends AidlDeviceService.Stub {


        @Override
        public IBinder getDeviceInfo() throws RemoteException {
            System.out.println("-----------------------------------getDeviceInfo");
            return new AidlDeviceInfo();
//            return new com.newland.aidl.impl.AidlDeviceInfo();
        }

        @Override
        public IBinder getPBOC() throws RemoteException {
            System.out.println("-----------------------------------getPBOC");
            return new PBOC();
        }

        @Override
        public IBinder getPinpad() throws RemoteException {
            System.out.println("-----------------------------------getPinpad");
            if (pinpad == null){
                pinpad = new Pinpad();
                return pinpad;
            }else{
                return pinpad;
            }
        }

        @Override
        public IBinder getPrinter() throws RemoteException {
            System.out.println("-----------------------------------getPrinter");
            return new AidlPrinter();
        }

        @Override
        public IBinder getICCard() throws RemoteException {
            System.out.println("-----------------------------------getICCard");
            return new AidlICCard();
//            return new com.newland.aidl.impl.AidlICCard();
        }

        @Override
        public IBinder getRFCard() throws RemoteException {
            System.out.println("-----------------------------------getRFCard");
            return new AidlRFCard();
        }

        @Override
        public IBinder getScanner() throws RemoteException {
            System.out.println("-----------------------------------getScanner");
            return new AidlScanner();
        }

        @Override
        public IBinder getLed() throws RemoteException {
            System.out.println("-----------------------------------getLed");
            return new AidlLed();
        }

        @Override
        public IBinder getBeeper() throws RemoteException {
            System.out.println("-----------------------------------getBeeper");
            return new AidlBeeper();
        }

        @Override
        public IBinder getTerminal() throws RemoteException {
            System.out.println("-----------------------------------getTerminalManage");
            return new AidlTerminalManage();
        }

        @Override
        public IBinder getSerialComm() throws RemoteException {
            System.out.println("-----------------------------------getSerialComm");
            return new AidlSerialComm();
        }
    }

}
