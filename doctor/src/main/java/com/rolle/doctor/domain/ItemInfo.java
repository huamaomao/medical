package com.rolle.doctor.domain;

/**
 * Created by Hua_ on 2015/3/26.
 */
public class ItemInfo {
    public int resId;
    public String title;
    public String desc;
    public String num;
    public boolean flag;
    public int type;
    public ItemInfo(){

    }
    public ItemInfo(int resId, String title) {
        this.resId = resId;
        this.title = title;
    }

    public ItemInfo(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public ItemInfo(int resId, String title, int type) {
        this.resId = resId;
        this.title = title;
        this.type = type;
    }

    public ItemInfo(String title, String desc, int type) {
        this.title = title;
        this.desc = desc;
        this.type = type;
    }
}
