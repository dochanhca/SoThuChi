package com.example.anhlamrduc.sothuchi.item;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by AnhlaMrDuc on 02-May-16.
 */
public class Income implements Parcelable, Comparable<Income> {
    private int receiveMoneyID;
    private double amount;
    private Date receiveDate;
    private String description;
    private Account account;
    private ReceiveItem receiveItem;
    private Lender lender;
    private Borrower borrower;
    private Event event;

    public Income() {
    }

    public Income(int receiveMoneyID, double amount, Date receiveDate, String description, Account account, ReceiveItem receiveItem, Lender lender, Borrower borrower, Event event) {
        this.receiveMoneyID = receiveMoneyID;
        this.amount = amount;
        this.receiveDate = receiveDate;
        this.description = description;
        this.account = account;
        this.receiveItem = receiveItem;
        this.lender = lender;
        this.borrower = borrower;
        this.event = event;
    }

    protected Income(Parcel in) {
        receiveMoneyID = in.readInt();
        amount = in.readDouble();
        description = in.readString();
        account = in.readParcelable(Account.class.getClassLoader());
        receiveItem = in.readParcelable(ReceiveItem.class.getClassLoader());
        lender = in.readParcelable(Lender.class.getClassLoader());
        borrower = in.readParcelable(Borrower.class.getClassLoader());
        event = in.readParcelable(Event.class.getClassLoader());
    }

    public static final Creator<Income> CREATOR = new Creator<Income>() {
        @Override
        public Income createFromParcel(Parcel in) {
            return new Income(in);
        }

        @Override
        public Income[] newArray(int size) {
            return new Income[size];
        }
    };

    public int getReceiveMoneyID() {
        return receiveMoneyID;
    }

    public void setReceiveMoneyID(int receiveMoneyID) {
        this.receiveMoneyID = receiveMoneyID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ReceiveItem getReceiveItem() {
        return receiveItem;
    }

    public void setReceiveItem(ReceiveItem receiveItem) {
        this.receiveItem = receiveItem;
    }

    public Lender getLender() {
        return lender;
    }

    public void setLender(Lender lender) {
        this.lender = lender;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(receiveMoneyID);
        dest.writeDouble(amount);
        dest.writeString(description);
        dest.writeParcelable(account, flags);
        dest.writeParcelable(receiveItem, flags);
        dest.writeParcelable(lender, flags);
        dest.writeParcelable(borrower, flags);
        dest.writeParcelable(event, flags);
    }

    @Override
    public int compareTo(Income income) {
        if (getReceiveDate() == null || income.getReceiveDate() == null )
            return 0;

        return income.getReceiveDate().compareTo(getReceiveDate());

    }
}
