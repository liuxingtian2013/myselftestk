// AidlPinpadListener.aidl
package com.newland.aidl.pinpad;

// Declare any non-default types here with import statements

interface AidlPinpadListener {

    void onKeyDown(int paramInt1, int paramInt2);
    void onPinRslt(in byte[] paramArrayOfByte);
    void onError(int paramInt, String paramString);
    void onCancel();

}
