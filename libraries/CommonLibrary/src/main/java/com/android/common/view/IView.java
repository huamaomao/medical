package com.android.common.view;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Hua_ on 2014/12/25.
 */
public interface IView {
     void showLoading();
     void hideLoading();
     void msgShow(String msg);
     void msgLongShow(String msg);
     Activity getContext();

}
