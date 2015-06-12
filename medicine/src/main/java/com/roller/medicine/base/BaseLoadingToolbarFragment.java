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

public  class BaseLoadingToolbarFragment extends Fragment{
    protected final String TAG="test";
    protected View rootView=null;
    protected int layoutId;
    protected boolean flag=false;
    BaseLoadingToolbarActivity baseActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        baseActivity=(BaseLoadingToolbarActivity)getActivity();
    }
    public Toolbar getToolbar(){
        return baseActivity.mToolbar;
    }
    /****
     *  设置最后触发时间
     */
    protected void setLastClickTime(){
        ((BaseToolbarActivity)getActivity()).setLastClickTime();
    }

    protected void showLoading() {
        if (CommonUtil.notNull(baseActivity))
        baseActivity.showLoading();

    }
    protected void setLoadingMessage(String msg) {
        if (CommonUtil.notNull(baseActivity))
            baseActivity.loadingFragment.setMessage(msg);

    }

    protected void hideLoading() {
        if (CommonUtil.notNull(baseActivity))
            baseActivity.hideLoading();
    }

    public void setTitle(String title){
        if (CommonUtil.notNull(baseActivity))
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
    public void showMsg(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 提示条
     *
     * @param content 提示的内容
     */
    public void showLongMsg(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
    }
}
