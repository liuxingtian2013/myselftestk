// AidlICCard.aidl
package com.newland.aidl.iccard;

// Declare any non-default types here with import statements

interface AidlICCard {

    byte[] powerOn(int paramInt1, int paramInt2);
    byte[] call(int paramInt1, int paramInt2, in byte[] paramArrayOfByte, int paramInt3);
    void powerOff(int paramInt1, int paramInt2);
    boolean isCardExist(int paramInt);

}
