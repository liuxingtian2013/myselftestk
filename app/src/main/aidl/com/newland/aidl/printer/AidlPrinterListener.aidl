// AidlPrinterListener.aidl
package com.newland.aidl.printer;

// Declare any non-default types here with import statements

interface AidlPrinterListener {

    void onError(int paramInt, String paramString);
    void onFinish();

}
