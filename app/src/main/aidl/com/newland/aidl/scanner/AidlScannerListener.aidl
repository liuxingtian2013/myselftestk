// AidlScannerListener.aidl
package com.newland.aidl.scanner;

// Declare any non-default types here with import statements

interface AidlScannerListener {

     void onScanResult(in String paramString);
     void onTimeout();
     void onError(int paramInt, String paramString);
     void onCancel();

}
