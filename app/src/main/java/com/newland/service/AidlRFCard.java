package com.newland.service;

import android.os.RemoteException;

import com.jl.Ddi;
import com.newland.aidl.rfcard.PowerOnRFResult;
import com.shanxixinhe.application.AidlApplication;

/**
 * Created by fu on 2018/3/29.
 */

public class AidlRFCard extends com.newland.aidl.rfcard.AidlRFCard.Stub {

    private Ddi hal;

    public AidlRFCard (){
        hal = AidlApplication.getinstanceDdi();
    }

    @Override
    public PowerOnRFResult powerOn(int[] cardTypes, int timeout) throws RemoteException {
        int ret = 0;
        int types = 0;
        PowerOnRFResult powerOnRFResult = new PowerOnRFResult();

        for (int type :
                cardTypes) {
            switch (type) {
                case 0x01:
                    types |= 0x01;
                    break;
                case 0x02:
                    types |= 0x02;
                    break;
                case 0x04:
                    types |= 0x04;
                    break;
            }

            ret = hal.ddi_rf_poweron(types);

            if (ret == 0) {
                ret = hal.ddi_rf_get_status();

                switch (ret) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        powerOnRFResult.setRfcardType(0x01);
                        break;
                    case 4:
                        powerOnRFResult.setRfcardType(0x02);
                        break;
                    case 5:
                        powerOnRFResult.setRfcardType(0x04);
                        break;
                    case 6:
                        powerOnRFResult.setRfcardType(0x04);
                        break;
                    case 7:
                        powerOnRFResult.setRfcardType(0x04);
                        break;
                }
            }
        }

        return powerOnRFResult;
    }

    @Override
    public byte[] call(byte[] reqData, int timeout) throws RemoteException {
        return new byte[0];
    }

    @Override
    public void powerOff() throws RemoteException {

    }

    @Override
    public boolean authenticate(int keyMode, byte[] cardSerialNo, int blockNo, byte[] key) throws RemoteException {
        return false;
    }

    @Override
    public boolean writeData(int blockNo, byte[] data) throws RemoteException {
        return false;
    }

    @Override
    public byte[] readData(int blockNo) throws RemoteException {
        return new byte[0];
    }

    @Override
    public boolean incrementOper(int blockNo, byte[] data) throws RemoteException {
        return false;
    }

    @Override
    public boolean decrementOper(int blockNo, byte[] data) throws RemoteException {
        return false;
    }

    @Override
    public boolean isCardExist() throws RemoteException {
        return false;
    }
}
