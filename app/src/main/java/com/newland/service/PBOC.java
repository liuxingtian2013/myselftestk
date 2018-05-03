package com.newland.service;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.emv.CandidateApp;
import com.emv.CoreLogic;
import com.emv.OnTransactionEvent;
import com.emv.PciICCPinkey;
import com.emv.Tag;
import com.jl.Ddi;
import com.jollytech.app.Base16EnDecoder;
import com.newland.aidl.pboc.AidEntry;
import com.newland.aidl.pboc.AidlCheckCardListener;
import com.newland.aidl.pboc.AidlPBOCListener;
import com.shanxixinhe.application.AidlApplication;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by Mark on 2018/4/12.
 */

public class PBOC extends com.newland.aidl.pboc.AidlPBOC.Stub {

    private boolean cancel = false;
    private Semaphore sync = new Semaphore(1);
    private int selectedIndex = 0;
    private boolean isConfirm = false;
    private int pinRet = -1;
    private Bundle issuerData;
    private AidEntry[] aidEntryS;
    private AidlPBOCListener pbocListener;
    private String responseCode;
    private String authCode;
    private String iad;
    private String issuerScripts;

    private OnTransactionEvent transactionEvent = new OnTransactionEvent() {
        @Override
        public int onSelectApplication(byte[] bytes) {
//                AidEntry[] aidEntries=null;
//                int offset = 0;
//                List<AidEntry> aidEntryList = new ArrayList<>();
//
//                do {
//                    AidEntry entry = new AidEntry();
//                    int len = bytes[offset+1];
//                    if (len == 0){
//                        break;
//                    }
//                    byte[] name = new byte[len];
//                    System.arraycopy(bytes, offset, name, 0, len);
//                    entry.setName(new String(name));
//
//                    if (offset>=bytes.length){
//                        break;
//                    }
//                }while(true);
//
//                try {
//                    aidEntries = new AidEntry[aidEntryList.size()];
//                    aidEntries = aidEntryList.toArray(aidEntries);
//                    sync.acquire();
//                    pbocListener.onSelectApplication(aidEntries);
//                    sync.acquire();
//                    sync.release();
//                    return selectedIndex;
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

            int ret = 0;
            List<CandidateApp> candidateApps = new ArrayList<>();
            ret = CoreLogic.GetCandidateAppList(candidateApps);

            if (ret != 0){
                Log.e(getClass().getName(), "GetCandidateAppList failed:"+ret);
            }

            AidEntry[] aidEntryS = new AidEntry[candidateApps.size()];
            int index = 0;
            for (CandidateApp app :
                    candidateApps) {
                aidEntryS[index].setName(app.getLable());
                aidEntryS[index].setIndex(index);
                aidEntryS[index].setAid(app.getAid());
                index++;
            }

            try {
                sync.acquire();
                pbocListener.onSelectApplication(aidEntryS);
                sync.acquire();
                sync.release();
            } catch (RemoteException e) {
                e.printStackTrace();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

            return selectedIndex;
        }

        @Override
        public int onSelectApplication(List<CandidateApp> list) {
            return 0;
        }

        @Override
        public int onShowCardholderId() {
            String certType="";
            String certInfo="";
            try {
                sync.acquire();
                pbocListener.onConfirmCertInfo(certType, certInfo);
                sync.acquire();
                sync.release();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isConfirm) {
                return 0;
            }else{
                return -1;
            }
        }

        @Override
        public int onInputPin(byte b, PciICCPinkey pciICCPinkey, int i) {
            boolean isOnlinePin = false;

            switch (b) {
                case CoreLogic.PinType.OFFLINE_PLAINTEXT:
                    isOnlinePin = false;
                    break;
                case CoreLogic.PinType.OFFLINE_ENCIPHERED:
                    isOnlinePin = false;
                    break;
                case CoreLogic.PinType.ONLINE_ENCIPHERED:
                    isOnlinePin = true;
                    break;
            }

            try {
                sync.acquire();
                pbocListener.onRequestPinEntry(isOnlinePin, i);
                sync.acquire();
                sync.release();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return pinRet;
        }

        @Override
        public int onOnline(Bundle bundle) {

            try {
                sync.acquire();
                Bundle reqData = new Bundle();
                int ret = 0;
                int []de55Tag = new int[]{
                        Tag.TAG_CRYPTOGRAM,
                        Tag.TAG_CRYPTOGRAM_INFO_DATA,
                        Tag.TAG_ISSUER_APP_DATA,
                        Tag.TAG_UNPREDICTABLE_NUMBER,
                        Tag.TAG_ATC,
                        Tag.TAG_TVR,
                        Tag.TAG_TRANSACTION_DATE,
                        Tag.TAG_TRANSACTION_TYPE,
                        Tag.TAG_AMOUNT,
                        Tag.TAG_TRANS_CURRENCY_CODE,
                        Tag.TAG_AIP,
                        Tag.TAG_TERM_COUNTRY_CODE,
                        Tag.TAG_AMOUNT_OTHER,
                        Tag.TAG_TERMINAL_CAPABILITIES,
                        Tag.TAG_CVM_RESULTS,
                        Tag.TAG_TERMINAL_TYPE,
                        Tag.TAG_IFD_SERIAL_NUMBER,
                        Tag.TAG_ADF_NAME,
                        Tag.TAG_APP_VERSION_NUMBER,
                        Tag.TAG_TRANS_SEQ_COUNTER,
                        Tag.TAG_CARD_IDENTIFIER,
                };

                byte[] de55 = new byte[512];
                ret = CoreLogic.GetDE55TlvMsg(de55Tag, de55);

                if (ret <= 0){
                    Log.e(getClass().getName(), "GetDE55TlvMsg failed:"+ret);
                }

                reqData.putString("ARQC_TLV", Base16EnDecoder.Encode(de55, 0, ret));

                switch(CoreLogic.GetKernelType())
                {
                    default:
                    case 0:
                        reqData.putInt("RESULT_CODE", 0x01);
                        break;
                    case 1:
                        reqData.putInt("RESULT_CODE", 0x02);
                        break;
                }

                byte[] value = new byte[256];
                ret = CoreLogic.GetTLVElement(Tag.TAG_TRACK2, value);

                if (ret <= 0){
                    Log.e(getClass().getName(), "get track2 failed:"+ret);
                }

                reqData.putString("EQUAL_TRACK2", Base16EnDecoder.Encode(value, 0, ret));

                ret = CoreLogic.GetTLVElement(Tag.TAG_PAN_SEQU_NUMBER, value);

                if (ret <= 0){
                    Log.e(getClass().getName(), "get TAG_PAN_SEQU_NUMBER failed:"+ret);
                }

                reqData.putString("CARD_SN", Base16EnDecoder.Encode(value, 0, ret));

                ret = CoreLogic.GetTLVElement(Tag.TAG_APP_EXPIRATION_DATE, value);

                if (ret <= 0){
                    Log.e(getClass().getName(), "get TAG_APP_EXPIRATION_DATE failed:"+ret);
                }

                reqData.putString("CARD_VALID_DATE ", Base16EnDecoder.Encode(value, 0, ret));

                pbocListener.onRequestOnline(reqData);
                sync.acquire();
                sync.release();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return 0;
        }

        @Override
        public String onGetPackagePath() {
            String path = AidlApplication.getContext().getFilesDir().getAbsolutePath() + File.separatorChar;
            return path;
        }
    };

    @Override
    public void checkCard(Bundle option, AidlCheckCardListener listener) throws RemoteException {
        boolean isSupportMagCard = option.getBoolean("isSupportMagCard", false);
        boolean isSupportICCard = option.getBoolean("isSupportICCard", false);
        boolean isSupportRFCard = option.getBoolean("isSupportRFCard", false);
        int tdkindex = option.getInt("TDKindex", -1);
        int ret = 0;
        Ddi hal = AidlApplication.getinstanceDdi();

        do{
            if (isSupportMagCard){
                ret = hal.ddi_mag_open();
                byte[] track1 = new byte[140];
                byte[] track2 = new byte[40];
                byte[] track3 = new byte[140];

                if (ret == 0){
                    ret = hal.ddi_mag_read(track1, track2, track3);
                    hal.ddi_mag_close();

                    if (ret == 0){
                        Bundle trackdata = new Bundle();
                        String T1 = new String(track1);
                        String T2 = new String(track2);
                        T2 = T2.trim().toUpperCase();
                        String T3 = new String(track3);
                        String pan;
                        String serviceCode;
                        String expDate;

                        if (T2.length()>0){
                            String[] elements = T2.split("D");

                            if (elements != null){
                                pan = elements[0];
                                expDate = elements[1].substring(0, 4);
                                serviceCode = elements[1].substring(4, 7);
                                trackdata.putString("PAN", pan);
                                trackdata.putString("SERVICE_CODE", serviceCode);
                                trackdata.putString("EXPIRED_DATE", expDate);
                            }
                        }

                        if (ret != -1){

                        }else{
                            trackdata.putString("TRACK1", T1);
                            trackdata.putString("TRACK2", T2);
                            trackdata.putString("TRACK3", T3);
                        }

                        listener.onFindMagCard(trackdata);
                        break;
                    }
                }
            }

            if (isSupportICCard){
                ret = hal.ddi_iccpsam_open(0);

                if (ret == 0){
                    ret = hal.ddi_iccpsam_get_status(0);

                    if (ret >=2 ){
                        listener.onFindICCard();
                        break;
                    }
                }
            }

            if (isSupportRFCard){
                ret = hal.ddi_rf_open();

                if (ret == 0){
                    ret = hal.ddi_rf_get_status();

                    if (ret >=3 ){
                        listener.onFindRFCard();
                        break;
                    }
                }
            }

        }while(!cancel);
    }

    @Override
    public void cancelCheckCard() throws RemoteException {
        cancel = true;
    }

    @Override
    public void startPBOC(int pbocType, Bundle option, final AidlPBOCListener listener) throws RemoteException {
        int ret = 0;
        int cardType = option.getInt("cardType", 0);
        int transAmount = option.getInt("transAmount", 0);
        boolean isForceOnline = option.getBoolean("isForceOnline", false);
        boolean isSupportEcCash = option.getBoolean("isSupportEcCash", false);
        String merchantId = option.getString("merchantID", "               ");
        String merchantName = option.getString("merchantName", "               ");
        String terminalID = option.getString("terminalID", "        ");
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyMMddhhmmss");
        String sdate = dateFormat.format(date);
        System.out.println(dateFormat.format(date));
        pbocListener = listener;

        Bundle transData = new Bundle();
        transData.putInt("AMOUNT", transAmount);
        transData.putInt("OTHERAMOUNT", 0);
        transData.putString("DATETIME", sdate);
        transData.putByte("CHANNEL", (byte) cardType);

        switch(cardType){
            default:
            case 0:
                transData.putByte("KERNELTYPE", (byte) 0x00);
                break;
            case 1:
                transData.putByte("KERNELTYPE", (byte) 0x01);
                break;
        }

        ret = CoreLogic.HandleTransaction(transData, transactionEvent);

    }

    @Override
    public void selectApplication(byte[] aid) throws RemoteException {

        for (AidEntry entry :
                aidEntryS) {
            if (Arrays.equals(aid, entry.getAid()))
            {
                selectedIndex = entry.getIndex();
                break;
            }
        }

        sync.release();
    }

    @Override
    public void importAmount(long amount) throws RemoteException {

    }

    @Override
    public void confirmPin(int oper, byte[] pin) throws RemoteException {
        sync.release();
        switch(oper){
            default:
            case 0x01:
                pinRet = -2006;
                break;
            case 0x02:
                pinRet = -2007;
                break;
            case 0x03:
                pinRet = 0;
                break;

        }
    }

    @Override
    public void confirmCardInfo(boolean isConfirm) throws RemoteException {

    }

    @Override
    public void confirmCertInfo(boolean isConfirm) throws RemoteException {
        sync.release();
    }

    StringBuffer sBuffer = new StringBuffer();
    @Override
    public void importOnlineResult(Bundle data) throws RemoteException {

        responseCode  = data.getString("responseCode", "");
        authCode  = data.getString("authCode", "");
        String de55  = data.getString("field55", "");

        if (de55 != null && de55.length()>0){
            byte[] bde55 = Base16EnDecoder.Decode(de55);
            com.newland.aidl.pboc.TLVUtils.parseTLVString(bde55, bde55.length, true,new com.newland.aidl.pboc.TLVUtils.SaveTLVOBJCallback() {
                @Override
                public void saveTLVOBJ(short tag, int length, byte[] value) {
                    switch (tag) {
                        case Tag.TAG_ISSUER_AUTH_DATA: // tag 91
                            byte[] issuerauthdata = new byte[length];
                            System.arraycopy(value, 0, issuerauthdata, 0, length);
                            iad = Base16EnDecoder.Encode(issuerauthdata);
                            Log.i(getClass().getName(), "tag 91 issuer authorization data is :"
                                    + iad);
                            break;
                        case Tag.TAG_TEMPERATE_71: // tag 86
                            sBuffer.append("71");
                            if (length > 127) {
                                sBuffer.append("01");
                            }

                            sBuffer.append(new String().format("%02X", length));
                            sBuffer.append(Base16EnDecoder.Encode(value, 0, length));

                            break;
                        case Tag.TAG_TEMPERATE_72: // tag 86
                            sBuffer.append("72");
                            if (length > 127) {
                                sBuffer.append("01");
                            }

                            sBuffer.append(new String().format("%02X", length));
                            sBuffer.append(Base16EnDecoder.Encode(value, 0, length));

                            break;
                        default:
                            break;
                    }
                }
            });
        }

        issuerScripts = sBuffer.toString();
        sync.release();


    }

    @Override
    public void endPboc() throws RemoteException {
        cancel = true;
    }

    @Override
    public void setEmvData(int tag, byte[] data) throws RemoteException {
        int ret = 0;

        ret = CoreLogic.SetTLV(tag, data);

        if ( ret != 0){
            Log.e(getClass().getName(), "SetTLV failed:"+ret);
        }
    }

    @Override
    public String readKernelData(int[] taglist) throws RemoteException {
        int ret = 0;
        byte[] de55 = new byte[512];
        ret = CoreLogic.GetDE55TlvMsg(taglist, de55);

        if (ret > 0){
            return Base16EnDecoder.Encode(de55, 0, ret);
        }else {
            return null;
        }
    }

    @Override
    public String readTransData(int tag) throws RemoteException {
        int ret = 0;
        byte[] value = new byte[256];
        ret = CoreLogic.GetTLVElement(tag, value);

        if (ret > 0){
            return Base16EnDecoder.Encode(value, 0, ret);
        }else {
            return null;
        }
    }

    @Override
    public boolean updateAID(int operation, String dataSource) throws RemoteException {

        int ret = 0;

        ret = CoreLogic.AppendApp(Base16EnDecoder.Decode(dataSource));

        if (ret == 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateCAPK(int operation, String dataSource) throws RemoteException {
        int ret = 0;

        ret = CoreLogic.AppendCapk(Base16EnDecoder.Decode(dataSource));

        if (ret == 0){
            return true;
        }else {
            return false;
        }
    }
}
