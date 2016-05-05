package com.example.anhlamrduc.sothuchi.item;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by AnhlaMrDuc on 02-May-16.
 */
public class Transfer implements Parcelable {
    private int transferID;
    private double amount;
    private Date transferDate;
    private String description;
    private double transferFee;
    private Account fromAccount;
    private Account toAccount;

    public Transfer() {
    }

    public Transfer(int transferID, double amount, Date transferDate, String description, double transferFee, Account fromAccount, Account toAccount) {
        this.transferID = transferID;
        this.amount = amount;
        this.transferDate = transferDate;
        this.description = description;
        this.transferFee = transferFee;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    protected Transfer(Parcel in) {
        transferID = in.readInt();
        amount = in.readDouble();
        description = in.readString();
        transferFee = in.readDouble();
        fromAccount = in.readParcelable(Account.class.getClassLoader());
        toAccount = in.readParcelable(Account.class.getClassLoader());
    }

    public static final Creator<Transfer> CREATOR = new Creator<Transfer>() {
        @Override
        public Transfer createFromParcel(Parcel in) {
            return new Transfer(in);
        }

        @Override
        public Transfer[] newArray(int size) {
            return new Transfer[size];
        }
    };

    public int getTransferID() {
        return transferID;
    }

    public void setTransferID(int transferID) {
        this.transferID = transferID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(double transferFee) {
        this.transferFee = transferFee;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(transferID);
        dest.writeDouble(amount);
        dest.writeString(description);
        dest.writeDouble(transferFee);
        dest.writeParcelable(fromAccount, flags);
        dest.writeParcelable(toAccount, flags);
    }
}
