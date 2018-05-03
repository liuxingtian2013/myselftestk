package com.newland.service;

import android.os.RemoteException;

import com.jl.Ddi;
import com.shanxixinhe.application.AidlApplication;

/**
 * Created by fu on 2018/3/29.
 */

public class AidlICCard extends com.newland.aidl.iccard.AidlICCard.Stub {

    private Ddi hal;

    public AidlICCard (){
        System.out.println("---------------AidlICCard---------1111111111-----------AidlICCard");
        hal = AidlApplication.getinstanceDdi();
        System.out.println("---------------AidlICCard---------2222222222-----------AidlICCard");
    }

    @Override
    public byte[] powerOn(int cardSlot, int cardType) throws RemoteException {
        int ret = -1;
        byte[] atrBuf = new byte[50];
        byte[] atr = null;
        ret = hal.ddi_iccpsam_poweron(cardSlot, atrBuf);

        if (ret == 0){
            atr = new byte[atrBuf[0]];
            System.arraycopy(atrBuf, 1, atr, 0,atr.length);
        }
        System.out.println("---------------AidlICCard--------------------powerOn");
        return atr;
    }

    @Override
    public byte[] call(int cardSlot, int cardType, byte[] data, int timeout) throws RemoteException {
        int ret = -1;
        byte[]buff = new byte[300];
        int []buffLen =new int[1];
        ret = hal.ddi_iccpsam_exchange_apdu(cardSlot, data, data.length, buff, buffLen, 300);
        byte[]r_apdu = null;

        if (ret == 0){
            r_apdu = new byte[buffLen[0]];
            System.arraycopy(buff, 0, r_apdu,0, buffLen[0]);
        }
        System.out.println("---------------AidlICCard--------------------call");
        return r_apdu;
    }

    @Override
    public void powerOff(int cardSlot, int cardType) throws RemoteException {
        int ret = -1;
        System.out.println("---------------AidlICCard--------------------powerOff");
        ret = hal.ddi_iccpsam_poweroff(cardSlot);
    }

    @Override
    public boolean isCardExist(int cardSlot) throws RemoteException {
        int ret = -1;
        boolean cardExist = false;

        ret = hal.ddi_iccpsam_get_status(cardSlot);

        switch (ret){
            case 2:
            case 3:
                cardExist = true;
                break;
            default:
                cardExist = false;
                break;
        }
        System.out.println("---------------AidlICCard--------------------isCardExist");
        return cardExist;
    }
}
