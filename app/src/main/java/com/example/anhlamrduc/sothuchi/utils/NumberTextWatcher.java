package com.example.anhlamrduc.sothuchi.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by AnhlaMrDuc on 01-May-16.
 */
public class NumberTextWatcher implements TextWatcher {

    DecimalFormat df;
    private NumberFormat dfnd;
    private EditText editText;
    private boolean hasFractionalPart;

    public NumberTextWatcher(EditText editText) {
        dfnd = new DecimalFormat(Constant.VND_FORMAT);
        df = new DecimalFormat(Constant.VND_FORMAT);
        df.setDecimalSeparatorAlwaysShown(true);
        this.editText = editText;
        hasFractionalPart = false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator())))
        {
            hasFractionalPart = true;
        } else {
            hasFractionalPart = false;
        }
    }

    @SuppressWarnings("unused")
    private static final String TAG = "NumberTextWatcher";

    @Override
    public void afterTextChanged(Editable s) {
        editText.removeTextChangedListener(this);

        try {
            int inilen, endlen;
            inilen = editText.getText().length();
            String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
            Number n = df.parse(v);
            int cp = editText.getSelectionStart();
            if (hasFractionalPart) {
                editText.setText(df.format(n));
            } else {
                editText.setText(dfnd.format(n));
            }
            endlen = editText.getText().length();
            int sel = (cp + (endlen - inilen));
            if (sel > 0 && sel <= editText.getText().length()) {
                editText.setSelection(sel);
            } else {
                // place cursor at the end?
                editText.setSelection(editText.getText().length() - 1);
            }
        } catch (NumberFormatException nfe) {
            // do nothing?
        } catch (ParseException e) {
            // do nothing?
        }

        editText.addTextChangedListener(this);
    }
}
