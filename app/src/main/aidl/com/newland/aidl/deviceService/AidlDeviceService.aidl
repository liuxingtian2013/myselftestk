// AidlDeviceService.aidl
package com.newland.aidl.deviceService;

// Declare any non-default types here with import statements

interface AidlDeviceService {

       IBinder getBeeper();
       IBinder getPBOC();
       IBinder getPinpad();
       IBinder getPrinter();
       IBinder getDeviceInfo();
       IBinder getScanner();
       IBinder getLed();
       IBinder getICCard();
       IBinder getRFCard();
       IBinder getTerminal();
       IBinder getSerialComm();

}
