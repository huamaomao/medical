package com.android.common.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.common.R;
import com.android.common.util.CommonUtil;

/**
 * @author Hua_
 * @Description:
 * @date 2015/5/7 - 18:56
 */
public class AlertDialogFragment extends DialogFragment{

    private OnClickListener listener;
    public String msg=null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_fragment, null);
        TextView tv_mesg=(TextView)view.findViewById(R.id.text);
        if (CommonUtil.notEmpty(msg)){
            tv_mesg.setText(msg);
        }
        builder.setView(view)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                listener.onConfirm();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onCancel();
            }
        });
        return builder.create();
    }
  public void setClickListener(OnClickListener listener){
        this.listener=listener;
  }

    public interface OnClickListener{
        void onCancel();
        void onConfirm();
    }
}
