package com.newland.aidl.pboc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fu on 2018/3/25.
 */

public class EcLog implements Parcelable {

    private byte[] blance_after;
    private byte[] blance_before;
    private byte[] countryCode;
    private byte[] merchantName;
    private byte[] tradeDate;
    private byte[] tradeTime;
    private byte[] transCount;

    public EcLog() {
    }

    protected EcLog(Parcel in) {
        blance_after = in.createByteArray();
        blance_before = in.createByteArray();
        countryCode = in.createByteArray();
        merchantName = in.createByteArray();
        tradeDate = in.createByteArray();
        tradeTime = in.createByteArray();
        transCount = in.createByteArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(blance_after);
        dest.writeByteArray(blance_before);
        dest.writeByteArray(countryCode);
        dest.writeByteArray(merchantName);
        dest.writeByteArray(tradeDate);
        dest.writeByteArray(tradeTime);
        dest.writeByteArray(transCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EcLog> CREATOR = new Creator<EcLog>() {
        @Override
        public EcLog createFromParcel(Parcel in) {
            return new EcLog(in);
        }

        @Override
        public EcLog[] newArray(int size) {
            return new EcLog[size];
        }
    };

    public byte[] getBlance_after() {
        return blance_after;
    }

    public void setBlance_after(byte[] blance_after) {
        this.blance_after = blance_after;
    }

    public byte[] getBlance_before() {
        return blance_before;
    }

    public void setBlance_before(byte[] blance_before) {
        this.blance_before = blance_before;
    }

    public byte[] getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(byte[] countryCode) {
        this.countryCode = countryCode;
    }

    public byte[] getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(byte[] merchantName) {
        this.merchantName = merchantName;
    }

    public byte[] getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(byte[] tradeDate) {
        this.tradeDate = tradeDate;
    }

    public byte[] getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(byte[] tradeTime) {
        this.tradeTime = tradeTime;
    }

    public byte[] getTransCount() {
        return transCount;
    }

    public void setTransCount(byte[] transCount) {
        this.transCount = transCount;
    }
}
