package com.newland.aidl.rfcard;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fu on 2018/3/25.
 */

public class PowerOnRFResult implements Parcelable{

    int rfcardType;
    byte[] cardSerialNo;
    byte[] atqa;

    public PowerOnRFResult() {
    }


    protected PowerOnRFResult(Parcel in) {
        rfcardType = in.readInt();
        cardSerialNo = in.createByteArray();
        atqa = in.createByteArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rfcardType);
        dest.writeByteArray(cardSerialNo);
        dest.writeByteArray(atqa);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PowerOnRFResult> CREATOR = new Creator<PowerOnRFResult>() {
        @Override
        public PowerOnRFResult createFromParcel(Parcel in) {
            return new PowerOnRFResult(in);
        }

        @Override
        public PowerOnRFResult[] newArray(int size) {
            return new PowerOnRFResult[size];
        }
    };

    public int getRfcardType() {
        return rfcardType;
    }

    public void setRfcardType(int rfcardType) {
        this.rfcardType = rfcardType;
    }

    public byte[] getCardSerialNo() {
        return cardSerialNo;
    }

    public void setCardSerialNo(byte[] cardSerialNo) {
        this.cardSerialNo = cardSerialNo;
    }

    public byte[] getAtqa() {
        return atqa;
    }

    public void setAtqa(byte[] atqa) {
        this.atqa = atqa;
    }
}
