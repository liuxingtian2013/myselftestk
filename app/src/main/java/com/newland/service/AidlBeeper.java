package com.newland.service;

import android.os.RemoteException;

public class AidlBeeper extends com.newland.aidl.beeper.AidlBeeper.Stub {
    @Override
    public void startBeep(int msec) throws RemoteException {

    }

    @Override
    public void stopBeep() throws RemoteException {

    }
}
