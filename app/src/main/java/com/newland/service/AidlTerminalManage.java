package com.newland.service;

import android.os.RemoteException;

public class AidlTerminalManage extends com.newland.aidl.terminal.AidlTerminalManage.Stub {
    @Override
    public void setDeviceDate(String date) throws RemoteException {

    }

    @Override
    public String getDeviceDate() throws RemoteException {
        return null;
    }
}
