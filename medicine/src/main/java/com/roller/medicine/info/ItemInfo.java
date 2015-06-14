package com.roller.medicine.info;

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
    public ItemInfo(String title) {
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ItemInfo{");
        sb.append("resId=").append(resId);
        sb.append(", title='").append(title).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", num='").append(num).append('\'');
        sb.append(", flag=").append(flag);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
