// AidlCheckCardListener.aidl
package com.newland.aidl.pboc;

// Declare any non-default types here with import statements
interface AidlCheckCardListener {

    void onFindMagCard(in Bundle paramBundle);
    void onFindICCard();
    void onFindRFCard();
    void onTimeout();
    void onCancel();
    void onError(int paramInt, String paramString);

}
