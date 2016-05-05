package com.example.anhlamrduc.sothuchi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.example.anhlamrduc.sothuchi.item.Pay;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 30-Apr-16.
 */
public class ExpandableListPayAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Pay> pays;
    private ArrayList<Pay> originalPays;

    public ExpandableListPayAdapter(Context context, ArrayList<Pay> pays) {
        this.context = context;
        this.pays = pays;
        this.originalPays = pays;
    }

    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
