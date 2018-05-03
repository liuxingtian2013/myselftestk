package com.newland.aidl.pboc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fu on 2018/3/25.
 */

public class CardInfo implements Parcelable {

    private String acctNo;
    private String balance;
    private String cardExpirationDate;

    public CardInfo() {
    }

    protected CardInfo(Parcel in) {
        acctNo = in.readString();
        balance = in.readString();
        cardExpirationDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(acctNo);
        dest.writeString(balance);
        dest.writeString(cardExpirationDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CardInfo> CREATOR = new Creator<CardInfo>() {
        @Override
        public CardInfo createFromParcel(Parcel in) {
            return new CardInfo(in);
        }

        @Override
        public CardInfo[] newArray(int size) {
            return new CardInfo[size];
        }
    };

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCardExpirationDate() {
        return cardExpirationDate;
    }

    public void setCardExpirationDate(String cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }
}
