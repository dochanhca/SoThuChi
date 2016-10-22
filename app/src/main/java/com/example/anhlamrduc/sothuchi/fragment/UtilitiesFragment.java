package com.example.anhlamrduc.sothuchi.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 14-Mar-16.
 */
public class UtilitiesFragment extends BaseFragment {
    @Bind(R.id.ll_info_product)
    LinearLayout llInfoProduct;
    @Bind(R.id.ll_help)
    LinearLayout llHelp;
    @Bind(R.id.ll_about_us)
    LinearLayout llAboutUs;

    @Override
    protected int layoutID() {
        return R.layout.fr_utilities;
    }

    @Override
    protected void handleData() {

    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.ll_info_product, R.id.ll_help, R.id.ll_about_us})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_info_product:
                showDialog();
                break;
            case R.id.ll_help:
                Uri helpURL = Uri.parse("https://drive.google.com/file/d/0B0YxYV_kL2AzRG1kZFI0S1ZIQkE/view");;
                Intent lauchHelp = new Intent(Intent.ACTION_VIEW, helpURL);
                startActivity(lauchHelp);

                break;
            case R.id.ll_about_us:
                Uri faceURL = Uri.parse("http://facebook.com/dochanhca");;
                Intent launchFace = new Intent(Intent.ACTION_VIEW, faceURL);
                startActivity(launchFace);
                break;
        }
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_product_infor);
        TextView txtClose = (TextView) dialog.findViewById(R.id.txt_close);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
