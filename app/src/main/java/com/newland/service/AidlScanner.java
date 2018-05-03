package com.newland.service;

import android.os.RemoteException;

import com.newland.aidl.scanner.AidlScannerListener;

public class AidlScanner extends com.newland.aidl.scanner.AidlScanner.Stub {
    @Override
    public void startScan(int scanType, int timeout, AidlScannerListener listener) throws RemoteException {

    }

    @Override
    public void stopScan() throws RemoteException {

    }
}
