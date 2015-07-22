package com.roller.medicine.info;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.common.domain.ResponseMessage;

import java.util.ArrayList;
import java.util.List;

/****
 * 首页数据
 */
public class HomeInfo extends ResponseMessage {

	public Glycemic glycemic;
	public ArrayList<Family> familyList;
	public HomeAdviceInfo advice;
	public String nickname;
	public String headImage;
	public String userId;
	public static class Glycemic{
		public String id;
		public String value;
		public String pharmacy;
		public String remark;
		public String timeBucket;
		public String lowSum;
		public String normalSum;
		public String highSum;
		public Float morningNum;
		public Float breakfastStart;
		public Float breakfastEnd;
		public Float chineseFoodStart;
		public Float chineseFoodEnd;
		public Float dinnerStart;
		public Float dinnerEnd;
		public Float beforeGoingToBed;
	}

	public static class Family implements Parcelable {
		public int id;
		public  int loginId;
		public String groupId;
		public String userId;
		public String appellation;
		public String createTime;
		public String disabled;
		public String isImportance;
		public String headImage;
		public String nickname;

		public Family() {
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeInt(this.id);
			dest.writeInt(this.loginId);
			dest.writeString(this.groupId);
			dest.writeString(this.userId);
			dest.writeString(this.appellation);
			dest.writeString(this.createTime);
			dest.writeString(this.disabled);
			dest.writeString(this.isImportance);
			dest.writeString(this.headImage);
			dest.writeString(this.nickname);
		}

		protected Family(Parcel in) {
			this.id = in.readInt();
			this.loginId = in.readInt();
			this.groupId = in.readString();
			this.userId = in.readString();
			this.appellation = in.readString();
			this.createTime = in.readString();
			this.disabled = in.readString();
			this.isImportance = in.readString();
			this.headImage = in.readString();
			this.nickname = in.readString();
		}

		public static final Creator<Family> CREATOR = new Creator<Family>() {
			public Family createFromParcel(Parcel source) {
				return new Family(source);
			}

			public Family[] newArray(int size) {
				return new Family[size];
			}
		};
	}

}
