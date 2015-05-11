package com.rolle.doctor.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.common.domain.ResponseMessage;

import java.io.Serializable;
import java.util.List;

/**
 * @author Hua_
 * @Description:
 * @date 2015/4/28 - 9:42
 */
public class CityResponse extends ResponseMessage {
    public int id;
    public List<Item> selectList;
    public static class Item implements Parcelable,Serializable{
        public String id;
        public String name;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.name);
        }

        public Item() {
        }

        public Item(String id, String name) {
            this.id = id;
            this.name = name;
        }

        private Item(Parcel in) {
            this.id = in.readString();
            this.name = in.readString();
        }

        public static final Creator<Item> CREATOR = new Creator<Item>() {
            public Item createFromParcel(Parcel source) {
                return new Item(source);
            }

            public Item[] newArray(int size) {
                return new Item[size];
            }
        };
    }
}
