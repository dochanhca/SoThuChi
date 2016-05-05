package com.example.anhlamrduc.sothuchi.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.fragment.AccountContainerFragment;
import com.example.anhlamrduc.sothuchi.fragment.NoteFragment;
import com.example.anhlamrduc.sothuchi.item.Pay;
import com.example.anhlamrduc.sothuchi.item.ReceiveMoney;
import com.example.anhlamrduc.sothuchi.item.Transfer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnhlaMrDuc on 24-Mar-16.
 */
public class MyPagerAdapter extends FragmentPagerAdapter implements NoteFragment.OnPassDataFromNote {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Bundle> mFragmentBundle = new ArrayList<>();

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = mFragmentList.get(position);
        fragment.setArguments(mFragmentBundle.get(position));
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title, Bundle bundle) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        mFragmentBundle.add(bundle);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Don't destroy view item.
    }

    @Override
    public void onPayInsertToDBFromNote(Pay pay) {
        AccountContainerFragment accountContainerFragment = (AccountContainerFragment) mFragmentList.get(1);
        if (accountContainerFragment != null) {
            accountContainerFragment.onPayInsertToDBFromNote(pay);
        } else {
            Log.e(MainActivity.TAG, "Account Fragment is null");
        }
    }

    @Override
    public void onReceiveMoneyInsertToDBFromNote(ReceiveMoney receiveMoney) {
        AccountContainerFragment accountContainerFragment = (AccountContainerFragment) mFragmentList.get(1);
        if (accountContainerFragment != null) {
            accountContainerFragment.onReceiveMoneyInsertToDBFromNote(receiveMoney);
        } else {
            Log.e(MainActivity.TAG, "Account Fragment is null");
        }
    }

    @Override
    public void onTransferInsertToDBFromNote(Transfer transfer) {
        AccountContainerFragment accountContainerFragment = (AccountContainerFragment) mFragmentList.get(1);
        if (accountContainerFragment != null) {
            accountContainerFragment.onTransferInsertToDBFromNote(transfer);
        } else {
            Log.e(MainActivity.TAG, "Account Fragment is null");
        }
    }
}
