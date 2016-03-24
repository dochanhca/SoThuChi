package com.example.anhlamrduc.sothuchi.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;

public class MainActivity extends FragmentActivity {

    private static final String MAIN = "Main activity: ";
    FragmentTabHost fragmentTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiateTab();
        Log.e(MAIN, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(MAIN, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(MAIN, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(MAIN, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(MAIN, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(MAIN, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(MAIN, "onDestroy");
    }

    /**
     * Initiate 5 tab
     */
    private void initiateTab() {

        //
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.real_tab_content);

        //Initiate Note tab
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("noteTab").
                        setIndicator(getTabIndicator(fragmentTabHost.getContext(),
                                R.string.tab1, R.drawable.ic_tab_note)),
                NoteActivity.class, null);
        //Initiate Account tab
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("accountTab").
                        setIndicator(getTabIndicator(fragmentTabHost.getContext(),
                                R.string.tab2, R.drawable.ic_tab_account)),
                AccountActivity.class, null);
        //Initiate limit tab
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("limitTab").
                        setIndicator(getTabIndicator(fragmentTabHost.getContext(),
                                R.string.tab3, R.drawable.ic_tab_limit)),
                LimitActivity.class, null);
        //Initiate Report tab
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("reportTab").
                        setIndicator(getTabIndicator(fragmentTabHost.getContext(),
                                R.string.tab4, R.drawable.ic_tab_report)),
                ReportActivity.class, null);
        //Initiate Utility tab
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("utilityTab").
                        setIndicator(getTabIndicator(fragmentTabHost.getContext(),
                                R.string.tab5, R.drawable.ic_tab_utility)),
                UtilitiesActivity.class, null);

        //

    }

    /**
     * Customize the tab widget
     *
     * @param context
     * @param title
     * @param icon
     * @return
     */
    private View getTabIndicator(Context context, int title, int icon) {

        View view = LayoutInflater.from(context).inflate(R.layout.tab_indicator, null);
        ImageView imgIcon = (ImageView) view.findViewById(R.id.img_icon);
        imgIcon.setImageResource(icon);
        TextView txtName = (TextView) view.findViewById(R.id.txt_name);
        txtName.setText(title);
        return view;
    }


}
