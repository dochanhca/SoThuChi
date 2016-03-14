package com.example.anhlamrduc.sothuchi.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import com.example.anhlamrduc.sothuchi.R;

public class MainActivity extends FragmentActivity {

    FragmentTabHost fragmentTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiateTab();

    }

    private void initiateTab() {
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.real_tab_content);

        //Kh?i t?o tab Ghi chép
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("noteTab").
                setIndicator(getResources().getString(R.string.tab1),
                        getResources().getDrawable(R.drawable.ic_tab_note)),
                NoteActivity.class, null);
        //Kh?i t?o tab Tài kho?n
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("accountTab").
                setIndicator(getResources().getString(R.string.tab2),
                        getResources().getDrawable(R.drawable.ic_tab_account)),
                AccountActivity.class, null);
        //Kh?i t?o tab h?n m?c chi
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("limitTab").
                setIndicator(getResources().getString(R.string.tab3),
                        getResources().getDrawable(R.drawable.ic_tab_limit)),
                LimitActivity.class, null);
        //Kh?i t?o tab báo cáo
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("reportTab").
                setIndicator(getResources().getString(R.string.tab4),
                        getResources().getDrawable(R.drawable.ic_tab_report)),
                ReportActivity.class, null);
        //Kh?i t?o tab ti?n ích
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("utilityTab").
                setIndicator(getResources().getString(R.string.tab5),
                        getResources().getDrawable(R.drawable.ic_tab_utinity)),
                UtilitiesActivity.class, null);

    }
}
