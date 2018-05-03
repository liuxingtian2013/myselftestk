package com.newland.aidl.pboc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fu on 2018/3/25.
 */

public class TransferLog implements Parcelable {

    private byte[] countryCode;
    private byte[] currencyCode;
    private byte[] merchantName;
    private byte[] otherAmount;
    private byte[] tradeAmount;
    private byte[] tradeDate;
    private byte[] tradeTime;
    private byte[] tradeType;
    private byte[] transCount;

    public TransferLog() {
    }

    protected TransferLog(Parcel in) {
        countryCode = in.createByteArray();
        currencyCode = in.createByteArray();
        merchantName = in.createByteArray();
        otherAmount = in.createByteArray();
        tradeAmount = in.createByteArray();
        tradeDate = in.createByteArray();
        tradeTime = in.createByteArray();
        tradeType = in.createByteArray();
        transCount = in.createByteArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(countryCode);
        dest.writeByteArray(currencyCode);
        dest.writeByteArray(merchantName);
        dest.writeByteArray(otherAmount);
        dest.writeByteArray(tradeAmount);
        dest.writeByteArray(tradeDate);
        dest.writeByteArray(tradeTime);
        dest.writeByteArray(tradeType);
        dest.writeByteArray(transCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TransferLog> CREATOR = new Creator<TransferLog>() {
        @Override
        public TransferLog createFromParcel(Parcel in) {
            return new TransferLog(in);
        }

        @Override
        public TransferLog[] newArray(int size) {
            return new TransferLog[size];
        }
    };

    public byte[] getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(byte[] countryCode) {
        this.countryCode = countryCode;
    }

    public byte[] getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(byte[] currencyCode) {
        this.currencyCode = currencyCode;
    }

    public byte[] getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(byte[] merchantName) {
        this.merchantName = merchantName;
    }

    public byte[] getOtherAmount() {
        return otherAmount;
    }

    public void setOtherAmount(byte[] otherAmount) {
        this.otherAmount = otherAmount;
    }

    public byte[] getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(byte[] tradeAmount) {
        this.tradeAmount = tradeAmount;
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

    public byte[] getTradeType() {
        return tradeType;
    }

    public void setTradeType(byte[] tradeType) {
        this.tradeType = tradeType;
    }

    public byte[] getTransCount() {
        return transCount;
    }

    public void setTransCount(byte[] transCount) {
        this.transCount = transCount;
    }
}
