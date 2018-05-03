// AidlPBOCListener.aidl
package com.newland.aidl.pboc;
import com.newland.aidl.pboc.AidEntry;
import com.newland.aidl.pboc.CardInfo;
import com.newland.aidl.pboc.TransferLog;
import com.newland.aidl.pboc.EcLog;

// Declare any non-default types here with import statements

interface AidlPBOCListener {

    void onSelectApplication(in AidEntry[] paramArrayOfAidEntry);
    void onConfirmCertInfo(in String paramString1,in String paramString2);
    void onConfirmCardInfo(in String paramString);
    void onRequestPinEntry(boolean paramBoolean, int paramInt);
    void onRequestOnline(in Bundle paramBundle);
    void onFallback();
    void onPbocFinished(int paramInt,in Bundle paramBundle);
    void onRequestAmount();
    void onReadECBalance(in CardInfo paramCardInfo);
    void onReadPBOCLog(in List<TransferLog> paramList);
    void onReadECLog(in List<EcLog> paramList);

}
