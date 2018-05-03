// AidlRFCard.aidl
package com.newland.aidl.rfcard;
import com.newland.aidl.rfcard.PowerOnRFResult;

// Declare any non-default types here with import statements

interface AidlRFCard {

    PowerOnRFResult powerOn(in int[] paramArrayOfInt, int paramInt);
    byte[] call(in byte[] paramArrayOfByte, int paramInt);
    void powerOff();
    boolean authenticate(int paramInt1,in byte[] paramArrayOfByte1, int paramInt2,in byte[] paramArrayOfByte2);
    boolean writeData(int paramInt,in byte[] paramArrayOfByte);
    byte[] readData(int paramInt);
    boolean incrementOper(int paramInt,in byte[] paramArrayOfByte);
    boolean decrementOper(int paramInt,in byte[] paramArrayOfByte);
    boolean isCardExist();

}
