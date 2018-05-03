package com.newland.aidl.pinpad;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fu on 2018/3/25.
 */

public class TusnData implements Parcelable{

    private String deviceType;
    private String encryptedData;
    private String sn;

    public TusnData() {
    }


    protected TusnData(Parcel in) {
        deviceType = in.readString();
        encryptedData = in.readString();
        sn = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deviceType);
        dest.writeString(encryptedData);
        dest.writeString(sn);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TusnData> CREATOR = new Creator<TusnData>() {
        @Override
        public TusnData createFromParcel(Parcel in) {
            return new TusnData(in);
        }

        @Override
        public TusnData[] newArray(int size) {
            return new TusnData[size];
        }
    };

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
