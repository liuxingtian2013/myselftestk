package com.newland.aidl.pboc;

import android.os.Parcel;
import android.os.Parcelable;

public class AidEntry implements Parcelable {
    private byte[] aid;
    private int index;
    private String name;

    public AidEntry() {
    }

    protected AidEntry(Parcel in) {
        aid = in.createByteArray();
        index = in.readInt();
        name = in.readString();
    }

    public static final Creator<com.newland.aidl.pboc.AidEntry> CREATOR = new Creator<com.newland.aidl.pboc.AidEntry>() {
        @Override
        public com.newland.aidl.pboc.AidEntry createFromParcel(Parcel in) {
            return new com.newland.aidl.pboc.AidEntry(in);
        }

        @Override
        public com.newland.aidl.pboc.AidEntry[] newArray(int size) {
            return new com.newland.aidl.pboc.AidEntry[size];
        }
    };

    public byte[] getAid() {
        return aid;
    }

    public void setAid(byte[] aid) {
        this.aid = aid;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByteArray(aid);
        parcel.writeInt(index);
        parcel.writeString(name);
    }
}
