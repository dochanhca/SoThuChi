package com.example.anhlamrduc.sothuchi.item;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by AnhlaMrDuc on 21-Apr-16.
 */
public class Pay implements Parcelable, Comparable<Pay> {
    private int payID;
    private double money;
    private Date payDate;
    private String description;
    private Account account;
    private SpendingItem spendingItem;
    private Lender lender;
    private Borrower borrower;
    private Receiver receiver;
    private Event event;

    public Pay() {
    }

    public Pay(int payID, double money, Date payDate, String description, Account account,
               SpendingItem spendingItem, Lender lender, Borrower borrower,
               Receiver receiver, Event event) {
        this.payID = payID;
        this.money = money;
        this.payDate = payDate;
        this.description = description;
        this.account = account;
        this.spendingItem = spendingItem;
        this.lender = lender;
        this.borrower = borrower;
        this.receiver = receiver;
        this.event = event;
    }

    protected Pay(Parcel in) {
        payID = in.readInt();
        money = in.readDouble();
        description = in.readString();
        account = in.readParcelable(Account.class.getClassLoader());
        spendingItem = in.readParcelable(SpendingItem.class.getClassLoader());
        lender = in.readParcelable(Lender.class.getClassLoader());
        borrower = in.readParcelable(Borrower.class.getClassLoader());
        receiver = in.readParcelable(Receiver.class.getClassLoader());
        event = in.readParcelable(Event.class.getClassLoader());
    }

    public static final Creator<Pay> CREATOR = new Creator<Pay>() {
        @Override
        public Pay createFromParcel(Parcel in) {
            return new Pay(in);
        }

        @Override
        public Pay[] newArray(int size) {
            return new Pay[size];
        }
    };

    public int getPayID() {
        return payID;
    }

    public void setPayID(int payID) {
        this.payID = payID;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
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

    public SpendingItem getSpendingItem() {
        return spendingItem;
    }

    public void setSpendingItem(SpendingItem spendingItem) {
        this.spendingItem = spendingItem;
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

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
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
        dest.writeInt(payID);
        dest.writeDouble(money);
        dest.writeString(description);
        dest.writeParcelable(account, flags);
        dest.writeParcelable(spendingItem, flags);
        dest.writeParcelable(lender, flags);
        dest.writeParcelable(borrower, flags);
        dest.writeParcelable(receiver, flags);
        dest.writeParcelable(event, flags);
    }

    @Override
    public int compareTo(Pay pay) {
        if (getPayDate() == null || pay.getPayDate() == null) {
            return 0;
        }
        return pay.getPayDate().compareTo(getPayDate());
    }
}
