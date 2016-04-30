package com.example.anhlamrduc.sothuchi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;

/**
 * Created by AnhlaMrDuc on 28-Mar-16.
 */
public class NoteSpinnerAdapter extends ArrayAdapter<String> {

    LayoutInflater layoutInflater;
    private String[] sub = getContext().getResources().getStringArray(R.array.spn_note_detail);
    TextView txtSpinnerMain;
    TextView txtSpinnerSub;
    TextView txtSpinner;
    ImageView imgRectangle;
    private String[] main = getContext().getResources().getStringArray(R.array.spn_note_title);

    public NoteSpinnerAdapter(Context context, int spinner_custom, String[] spnNoteTitle) {
        super(context, spinner_custom, spnNoteTitle);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //inflating the layout for customview
        View layout = layoutInflater.inflate(R.layout.spinner_custom, parent, false);
        //Declaring the textview
        txtSpinner = (TextView) layout.findViewById(R.id.txt_spinner);
        txtSpinner.setText(main[position]);

        imgRectangle = (ImageView) layout.findViewById(R.id.img_rectangle);
        imgRectangle.setImageResource(R.drawable.ic_rectangle);

        return layout;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {

        //inflating the layout for customview
        View layout = layoutInflater.inflate(R.layout.spinner_dropdown_custom, parent, false);
        //Declaring the textview
        txtSpinnerMain = (TextView) layout.findViewById(R.id.txt_spinner_main);
        txtSpinnerMain.setText(main[position]);

        txtSpinnerSub = (TextView) layout.findViewById(R.id.txt_spinner_sub);
        txtSpinnerSub.setText(sub[position]);

        return layout;

    }

}
