package com.roller.medicine.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.common.widget.AlertDialog;
import com.roller.medicine.R;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.utils.CircleTransform;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;

/**
 * @author Hua_
 * @Description:
 * @date 2015/5/7 - 18:56
 */
public class CommentDialogFragment extends DialogFragment {

    private OnClickListener listener;
    private ImageView imageView;
    private TextView textView;
    private UserInfo userInfo;

    public void setUserInfo(UserInfo userInfo){
        this.userInfo=userInfo;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_comment_fragment, null);
        imageView=(ImageView)view.findViewById(R.id.iv_photo);
        textView=(TextView)view.findViewById(R.id.tv_name);

        Picasso.with(getActivity()).load(DataModel.getImageUrl(userInfo.headImage)).placeholder(R.drawable.icon_default).
                transform(new CircleTransform()).into((ImageView)imageView);
        textView.setText(userInfo.nickname);
        builder.setView(view)
                .setNeutralButton("推荐",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                listener.onConfirm();
                            }
                        }).setNegativeButton("暂不评论", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.onCancel();
                        }
        }).setPositiveButton("评论", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onComment();
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
        void onComment();
    }
}
