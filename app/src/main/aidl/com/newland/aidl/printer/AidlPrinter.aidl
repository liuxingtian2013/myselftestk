// AidlPrinter.aidl
package com.newland.aidl.printer;
import com.newland.aidl.printer.AidlPrinterListener;

// Declare any non-default types here with import statements

interface AidlPrinter {

    void addText(in Bundle paramBundle, String paramString);
    void addPicture(in Bundle paramBundle,in Bitmap paramBitmap);
    void addBarCode(in Bundle paramBundle, String paramString);
    void addQrCode(in Bundle paramBundle, String paramString);
    void setSpace(int paramInt);
    void paperSkip(int paramInt);
    void startPrinter(in AidlPrinterListener paramAidlPrinterListener);
    int getStatus();

}
