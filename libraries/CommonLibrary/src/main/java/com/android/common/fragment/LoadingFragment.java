package com.android.common.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.android.common.R;

public class LoadingFragment extends DialogFragment {

    private View mContentView;
    private TextView textView;
    private String message=commit;
    private final  static String commit="正在提交...";
    private final  static String login="正在登陆...";
    private final  static String request="正在请求...";
    private final  static String add="正在添加...";
    private LoadingFragment() {
    }

    public static LoadingFragment newInstance(){
        return  new LoadingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        mContentView=inflater.inflate(R.layout.fragment_custom_progress, container, false);
        textView=(TextView)mContentView.findViewById(R.id.progress_text);
        textView.setText(message);
        return mContentView;
    }

    public void setMessage(String msg){
        this.message=msg;
    }

    public void setCommitMessage(){
        setMessage(commit);
    }
    public void setLoginMessage(){
        setMessage(login);
    }

    public void setAddMessage(){
        setMessage(add);
    }
    public void setRequestMessage(){
        setMessage(request);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
