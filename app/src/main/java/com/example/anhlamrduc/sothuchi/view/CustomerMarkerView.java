package com.example.anhlamrduc.sothuchi.view;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

/**
 * Created by AnhlaMrDuc on 06-May-16.
 */
public class CustomerMarkerView extends MarkerView {
    private TextView tvContent;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public CustomerMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
//        tvContent = (TextView) findViewById(R.id.txt_chart);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText("" + e.getVal());
    }

    @Override
    public int getXOffset(float xpos) {
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        return -getHeight();
    }
}
