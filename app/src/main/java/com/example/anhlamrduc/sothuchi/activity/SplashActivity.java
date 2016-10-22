package com.example.anhlamrduc.sothuchi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.asynctask.DBGetAllAccount;
import com.example.anhlamrduc.sothuchi.asynctask.DBGetAllEvent;
import com.example.anhlamrduc.sothuchi.asynctask.DBGetAllReceiveItem;
import com.example.anhlamrduc.sothuchi.asynctask.DBGetAllReceiver;
import com.example.anhlamrduc.sothuchi.asynctask.DBGetAllSpendingItem;
import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.Event;
import com.example.anhlamrduc.sothuchi.item.Income;
import com.example.anhlamrduc.sothuchi.item.Pay;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;
import com.example.anhlamrduc.sothuchi.item.Receiver;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 02-May-16.
 */
public class SplashActivity extends AppCompatActivity {
    private static final int TIME_DELAY = 2000;
    private ArrayList<SpendingItem> listSpendingItem = new ArrayList<>();
    private ArrayList<ReceiveItem> listReceiveItem = new ArrayList<>();
    private ArrayList<Account> listAccount = new ArrayList<>();
    private ArrayList<Receiver> listReceiver = new ArrayList<>();
    private ArrayList<Event> listEvent = new ArrayList<>();
    private ArrayList<Pay> listPay = new ArrayList<>();
    private ArrayList<Income> listIncome = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DBGetAllEvent dbGetAllEvent = new DBGetAllEvent(this) {
            @Override
            protected void onPostExecute(ArrayList<Event> events) {
                super.onPostExecute(events);
                listEvent = events;
            }
        };
        dbGetAllEvent.execute();

        DBGetAllReceiver dbGetAllReceiver = new DBGetAllReceiver(this) {
            @Override
            protected void onPostExecute(ArrayList<Receiver> receivers) {
                super.onPostExecute(receivers);
                listReceiver = receivers;
            }
        };
        dbGetAllReceiver.execute();

        DBGetAllAccount dbGetAllAccount = new DBGetAllAccount(this) {
            @Override
            protected void onPostExecute(ArrayList<Account> accounts) {
                listAccount = accounts;
            }
        };
        dbGetAllAccount.execute();

        DBGetAllSpendingItem dbGetAllSpendingItem = new DBGetAllSpendingItem(this) {
            @Override
            protected void onPostExecute(ArrayList<SpendingItem> spendingItems) {
                listSpendingItem = spendingItems;
            }
        };
        dbGetAllSpendingItem.execute();
        DBGetAllReceiveItem dbGetAllReceiveItem = new DBGetAllReceiveItem(this) {
            @Override
            protected void onPostExecute(ArrayList<ReceiveItem> receiveItems) {
                super.onPostExecute(receiveItems);
                listReceiveItem = receiveItems;
                MainActivity.startActivity(SplashActivity.this, listReceiveItem, listSpendingItem,
                        listAccount, listReceiver, listEvent);
                finish();
            }
        };
        dbGetAllReceiveItem.execute();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                    finish();
//            }
//        }, TIME_DELAY);
    }
}
