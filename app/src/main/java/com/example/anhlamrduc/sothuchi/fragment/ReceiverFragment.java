package com.example.anhlamrduc.sothuchi.fragment;

import android.graphics.PorterDuff;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.adapter.ListReceiverAdapter;
import com.example.anhlamrduc.sothuchi.item.Receiver;

import java.util.ArrayList;
import java.util.ListIterator;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 21-Apr-16.
 */
public class ReceiverFragment extends BaseFragment {
    private ArrayList<Receiver> receivers;
    private ListReceiverAdapter listReceiverAdapter;

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String receiverName = receivers.get(position).getReceiverName();
            edtReceiver.setText(receiverName);
        }
    };

    @Override
    protected int layoutID() {
        return R.layout.fr_receiver;
    }

    @Override
    protected void initView() {
        edtReceiver.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        listReceiverAdapter = new ListReceiverAdapter(getContext(), receivers);
        lvReceiver.setAdapter(listReceiverAdapter);
        lvReceiver.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void handleData() {
        receivers = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_MAIN);
        if (getNewRecevier() != null) {
            Receiver receiver = getNewRecevier();
            receivers.add(receiver);
            getEditor().remove(MainActivity.NEW_RECEIVER);
            getEditor().commit();
        }
        handleList();
    }

    private void handleList() {
        ListIterator iterator = receivers.listIterator();
        while (iterator.hasNext()) {
            Receiver receiver = (Receiver) iterator.next();
            if (receiver.getReceiverName().length() == 0) {
                iterator.remove();
            }
        }
    }

    @OnClick(R.id.txt_done)
    public void selectReceiverDone() {
        String receiverName = edtReceiver.getText().toString().trim();
        setReceiver(receiverName);
        getFragmentManager().popBackStack();
    }

    @OnClick(R.id.txt_cancel)
    public void goToBack() {
        getFragmentManager().popBackStack();
    }

    @Bind(R.id.edt_receiver)
    EditText edtReceiver;
    @Bind(android.R.id.list)
    ListView lvReceiver;
}
