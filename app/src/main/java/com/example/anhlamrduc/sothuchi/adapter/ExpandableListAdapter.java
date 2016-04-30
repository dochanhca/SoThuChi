package com.example.anhlamrduc.sothuchi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;

import java.util.HashMap;
import java.util.List;

/**
 * Created by AnhlaMrDuc on 01-Apr-16.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private static final String TAG = "Expendable listview: ";
    private Context _context;
    private List<SpendingItem> listDataHeader; // header titles
    private List<SpendingItem> listDateHeaderToSearch;
    // child data in format of header title, child title
    private HashMap<String, List<SpendingItem>> listDataChild;
    private HashMap<String, List<SpendingItem>> listDataChildToSearch;

    public ExpandableListAdapter(Context context, List<SpendingItem> listDataHeader,
                                 HashMap<String, List<SpendingItem>> listChildData) {
        this._context = context;
        this.listDataHeader = listDataHeader;
        this.listDateHeaderToSearch = listDataHeader;
        this.listDataChild = listChildData;
        this.listDataChildToSearch = listChildData;
    }

    @Override
    public SpendingItem getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition).getSpendingItemName()).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = getChild(groupPosition, childPosition).getSpendingItemName();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_spending_item, parent, false);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.txt_lv_spending_item);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition).getSpendingItemName())
                .size();
    }

    @Override
    public SpendingItem getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded,
                             View convertView, final ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).getSpendingItemName();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_spending_group, parent, false);
        }

        TextView txtSpendingParent = (TextView) convertView
                .findViewById(R.id.txt_lv_spending_header);
        final ImageView imgLVIndicator = (ImageView) convertView
                .findViewById(R.id.img_lv_indicator);
        txtSpendingParent.setText(headerTitle);
        // Listview Group click listener
        int icLVIndicator = isExpanded ? R.drawable.ic_lv_group_collapse : R.drawable.ic_lv_group_expand;
        imgLVIndicator.setImageResource(icLVIndicator);
        imgLVIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded)
                    ((ExpandableListView) parent).collapseGroup(groupPosition);
                else
                    ((ExpandableListView) parent).expandGroup(groupPosition, true);
            }
        });
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

//    public void filterData(String query){
//
//        query = query.toLowerCase();
//        Log.v("MyListAdapter", String.valueOf(listDataHeader.size()));
//        listDataHeader.clear();
//
//        if(query.isEmpty()){
//            listDataHeader.addAll(listDateHeaderToSearch);
//        }
//        else {
//
//            for(Continent continent: originalList){
//
//                ArrayList<Country> countryList = continent.getCountryList();
//                ArrayList<Country> newList = new ArrayList<Country>();
//                for(Country country: countryList){
//                    if(country.getCode().toLowerCase().contains(query) ||
//                            country.getName().toLowerCase().contains(query)){
//                        newList.add(country);
//                    }
//                }
//                if(newList.size() > 0){
//                    Continent nContinent = new Continent(continent.getName(),newList);
//                    continentList.add(nContinent);
//                }
//            }
//        }
//
//        Log.v("MyListAdapter", String.valueOf(continentList.size()));
//        notifyDataSetChanged();
//
//    }
}
