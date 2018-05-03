// AidlDeviceInfo.aidl
package com.newland.aidl.deviceInfo;

// Declare any non-default types here with import statements

interface AidlDeviceInfo {

     String getTUSN();
     String getSN();
     String getCSN();
     String getVID();
     String getVName();
     String getKSN();
     String getAndroidOSVersion();
     String getAndroidKernelVersion();
     String getFirmwareVersion();
     String getDeviceModel();


}
