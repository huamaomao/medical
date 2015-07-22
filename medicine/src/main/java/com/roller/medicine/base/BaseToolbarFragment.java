package com.roller.medicine.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.common.util.CommonUtil;

import butterknife.ButterKnife;

public  class BaseToolbarFragment extends Fragment{
    protected final String TAG="test";
    protected View rootView=null;
    protected int layoutId;
    protected boolean flag=false;
    BaseToolbarActivity baseActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        baseActivity=(BaseToolbarActivity)getActivity();
    }
    public Toolbar getToolbar(){
        return baseActivity.mToolbar;
    }


    public void setTitle(String title){
        baseActivity.setToolbarTitle(title);
    }

    public ActionBar getSupperActionBar(){
        return baseActivity.getSupportActionBar();
    }

    protected  void setLayoutId(int layoutId){
        this.layoutId=layoutId;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (CommonUtil.isNull(rootView)){
            rootView=inflater.inflate(layoutId,container,false);
            ButterKnife.inject(this,rootView);
            initView(rootView,inflater);
        }else if (flag){
            ButterKnife.inject(this,rootView);
        }

        ViewGroup parent=(ViewGroup)rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
      return rootView;
    }


   public boolean onMenuItemSelected(MenuItem menuItem){
        return  false;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        ButterKnife.reset(this);
        super.onDestroy();
    }

    protected  void doRefresh(){

    }
    /***
     * 初始化
     */
    protected   void initView(View view,LayoutInflater inflater){

    }

    /**
     * 提示条
     *
     * @param content 提示的内容
     */
    public void msgShow(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 提示条
     *
     * @param content 提示的内容
     */
    public void msgLongShow(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
    }
}
