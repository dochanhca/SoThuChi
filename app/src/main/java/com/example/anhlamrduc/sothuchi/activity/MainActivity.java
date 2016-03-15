package com.example.anhlamrduc.sothuchi.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.helper.FontManager;

public class MainActivity extends FragmentActivity {

    FragmentTabHost fragmentTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiateTab();

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
                                R.string.tab1, R.string.ic_tab_note)),
                NoteActivity.class, null);
        //Initiate Account tab
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("accountTab").
                        setIndicator(getTabIndicator(fragmentTabHost.getContext(),
                                R.string.tab2, R.string.ic_tab_account)),
                AccountActivity.class, null);
        //Initiate limit tab
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("limitTab").
                        setIndicator(getTabIndicator(fragmentTabHost.getContext(),
                                R.string.tab3, R.string.ic_tab_limit)),
                LimitActivity.class, null);
        //Initiate Report tab
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("reportTab").
                        setIndicator(getTabIndicator(fragmentTabHost.getContext(),
                                R.string.tab4, R.string.ic_tab_report)),
                ReportActivity.class, null);
        //Initiate Utility tab
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("utilityTab").
                        setIndicator(getTabIndicator(fragmentTabHost.getContext(),
                                R.string.tab1, R.string.ic_tab_utility)),
                UtilitiesActivity.class, null);

        //

    }

    /**
     * Customize the tab widget
     * @param context
     * @param title
     * @param icon
     * @return
     */
    private View getTabIndicator(Context context, int title, int icon) {

        //Use font awesome
        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.ll_tab_indicator), iconFont);

        View view = LayoutInflater.from(context).inflate(R.layout.tab_indicator, null);
        TextView txtImage = (TextView) view.findViewById(R.id.txt_image);
        txtImage.setText(icon);
        TextView txtName = (TextView) view.findViewById(R.id.txt_name);
        txtName.setText(title);
        return view;
    }
}
