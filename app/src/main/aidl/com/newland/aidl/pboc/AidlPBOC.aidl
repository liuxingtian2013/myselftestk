// AidlPBOC.aidl
package com.newland.aidl.pboc;
import com.newland.aidl.pboc.AidlCheckCardListener;
import com.newland.aidl.pboc.AidlPBOCListener;

// Declare any non-default types here with import statements

interface AidlPBOC {

    void checkCard(in Bundle paramBundle,in AidlCheckCardListener paramAidlCheckCardListener);
    void cancelCheckCard();
    void startPBOC(int paramInt,in Bundle paramBundle,in AidlPBOCListener paramAidlPBOCListener);
    void selectApplication(in byte[] paramArrayOfByte);
    void importAmount(in long paramLong);
    void confirmPin(int paramInt,in byte[] paramArrayOfByte);
    void confirmCardInfo(in boolean paramBoolean);
    void confirmCertInfo(in boolean paramBoolean);
    void importOnlineResult(in Bundle paramBundle);
    void setEmvData(int paramInt,in byte[] paramArrayOfByte);
    void endPboc();
    String readKernelData(out int[] paramArrayOfInt);
    String readTransData(int paramInt);
    boolean updateAID(in int paramInt,in String paramString);
    boolean updateCAPK(in int paramInt,in String paramString);

}
