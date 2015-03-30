package com.android.common.util;

/**
 * Created by Hua_ on 2014/12/3.
 */
public enum ActivityModel {
    ACTIVITY_MODEL_0(0)/**null 无动画**/
    ,ACTIVITY_MODEL_1(1),/**left(不动) < right**/
    ACTIVITY_MODEL_2(2),/**left(不动) right>**/
    ACTIVITY_MODEL_3(3),/**left< righ   向左移动t**/
    ACTIVITY_MODEL_4(4);/****/
    int value=0;
    private ActivityModel(int type){
        this.value=type;
    }
}
