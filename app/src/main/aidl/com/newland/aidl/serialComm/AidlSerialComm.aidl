// AidlSerialComm.aidl
package com.newland.aidl.serialComm;

// Declare any non-default types here with import statements

interface AidlSerialComm {

       boolean open();
       boolean close();
       String getVersion();
       boolean setconfig(int paramInt1, int paramInt2,in byte[] paramArrayOfByte);
       int ioctl(int paramInt,in byte[] paramArrayOfByte);
       int read(out byte[] paramArrayOfByte, int paramInt1, int paramInt2);
       int write(in byte[] paramArrayOfByte, int paramInt1, int paramInt2);

}
