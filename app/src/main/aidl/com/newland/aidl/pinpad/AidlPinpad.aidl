// AidlPinpad.aidl
package com.newland.aidl.pinpad;
import com.newland.aidl.pinpad.AidlPinpadListener;
import com.newland.aidl.pinpad.TusnData;

// Declare any non-default types here with import statements

interface AidlPinpad {

    boolean loadTEK(in byte[] paramArrayOfByte1,in byte[] paramArrayOfByte2);
    boolean loadTMKByTEK(int paramInt,in byte[] paramArrayOfByte1,in byte[] paramArrayOfByte2);
    boolean loadWorkKey(int paramInt1, int paramInt2, int paramInt3,in byte[] paramArrayOfByte1,in byte[] paramArrayOfByte2);
    byte[] calcMAC(int paramInt1, int paramInt2,in byte[] paramArrayOfByte1,in byte[] paramArrayOfByte2);
    byte[] encryptData(int paramInt1, int paramInt2,in byte[] paramArrayOfByte1,in byte[] paramArrayOfByte2);
    byte[] decryptData(int paramInt1, int paramInt2,in byte[] paramArrayOfByte1,in byte[] paramArrayOfByte2);
    byte[] setPinpadLayout(in byte[] paramArrayOfByte);
    void startPininput(in Bundle paramBundle,in AidlPinpadListener paramAidlPinpadListener);
    String getRandom();
    void cancelPininput();
    TusnData getTusnInfo(in String paramString);
    void setKeyAlgorithm(int paramInt);
    boolean isSM4Enabled();
    boolean loadMainKeyByPlaintext(int paramInt,in byte[] paramArrayOfByte1,in byte[] paramArrayOfByte2);
    byte[] sm2Encrypt(in byte[] paramArrayOfByte1,in byte[] paramArrayOfByte2);
    byte[] sm4Encrypt(in byte[] paramArrayOfByte1,in byte[] paramArrayOfByte2);
    byte[] sm4Decrypt(in byte[] paramArrayOfByte1,in byte[] paramArrayOfByte2);
    byte[] sm4CalcMac(in byte[] paramArrayOfByte1,in byte[] paramArrayOfByte2);

}
