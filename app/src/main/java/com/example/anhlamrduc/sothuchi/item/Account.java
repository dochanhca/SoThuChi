package com.example.anhlamrduc.sothuchi.item;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AnhlaMrDuc on 20-Mar-16.
 */
public class Account implements Parcelable {

    private int accountID;
    private String accountName;
    private double firstMoney;
    private double currentMoney;
    private String note;
    private AccountType accountType;

    protected Account(Parcel in) {
        accountID = in.readInt();
        accountName = in.readString();
        firstMoney = in.readDouble();
        currentMoney = in.readDouble();
        note = in.readString();
        accountType = in.readParcelable(AccountType.class.getClassLoader());
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getFirstMoney() {
        return firstMoney;
    }

    public void setFirstMoney(double firstMoney) {
        this.firstMoney = firstMoney;
    }

    public double getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(double currentMoney) {
        this.currentMoney = currentMoney;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Account() {
    }

    public Account(String accountName) {
        this.accountName = accountName;
    }

    public Account(int accountID, String accountName, double firstMoney, double currentMoney, String note, AccountType accountType) {
        this.accountID = accountID;
        this.accountName = accountName;
        this.firstMoney = firstMoney;
        this.currentMoney = currentMoney;
        this.note = note;
        this.accountType = accountType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(accountID);
        dest.writeString(accountName);
        dest.writeDouble(firstMoney);
        dest.writeDouble(currentMoney);
        dest.writeString(note);
        dest.writeValue(accountType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return !(accountName != null ? !accountName.equals(account.accountName) : account.accountName != null);

    }

    @Override
    public int hashCode() {
        return accountName != null ? accountName.hashCode() : 0;
    }

    public String toString() {
        return (accountName);
    }

}
