package com.newland.service;

import android.os.RemoteException;

public class AidlSerialComm extends com.newland.aidl.serialComm.AidlSerialComm.Stub{

    @Override
    public boolean open() throws RemoteException {
        return false;
    }

    @Override
    public boolean setconfig(int data1, int data2, byte[] buf) throws RemoteException {
        return false;
    }

    @Override
    public int ioctl(int cmd, byte[] args) throws RemoteException {
        return 0;
    }

    @Override
    public int read(byte[] buf, int lengthMax, int timeoutSec) throws RemoteException {
        return 0;
    }

    @Override
    public int write(byte[] buf, int lengthMax, int timeoutSec) throws RemoteException {
        return 0;
    }

    @Override
    public boolean close() throws RemoteException {
        return false;
    }

    @Override
    public String getVersion() throws RemoteException {
        return null;
    }
}
