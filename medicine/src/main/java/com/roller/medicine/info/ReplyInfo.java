package com.roller.medicine.info;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.common.domain.ResponseMessage;

public class ReplyInfo extends ResponseMessage implements Parcelable {
	
	public String id;
	public String postId;
	public String replyId;
	public String content;
	public String floor;
	public String isPassed;
	public String disabled;
	public String byReplyUserId;
	public String replyUserId;
	public String createTime;
	public String replyCount;
	public String praiseCount;
	public String nickname;
	public String headImage;
	public String atName;
	public boolean praise;

	@Override
	public String toString() {
		return "KnowledgeQuizContentReplyListItemInfo [id=" + id + ", postId="
				+ postId + ", replyId=" + replyId + ", content=" + content
				+ ", floor=" + floor + ", isPassed=" + isPassed + ", disabled="
				+ disabled + ", byReplyUserId=" + byReplyUserId
				+ ", replyUserId=" + replyUserId + ", createTime=" + createTime
				+ ", replyCount=" + replyCount + ", praiseCount=" + praiseCount
				+ ", nickname=" + nickname + ", headImage=" + headImage
				+ ", atName=" + atName + ", praise=" + praise + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.postId);
		dest.writeString(this.replyId);
		dest.writeString(this.content);
		dest.writeString(this.floor);
		dest.writeString(this.isPassed);
		dest.writeString(this.disabled);
		dest.writeString(this.byReplyUserId);
		dest.writeString(this.replyUserId);
		dest.writeString(this.createTime);
		dest.writeString(this.replyCount);
		dest.writeString(this.praiseCount);
		dest.writeString(this.nickname);
		dest.writeString(this.headImage);
		dest.writeString(this.atName);
		dest.writeByte(praise ? (byte) 1 : (byte) 0);
	}

	public ReplyInfo() {
	}

	protected ReplyInfo(Parcel in) {
		this.id = in.readString();
		this.postId = in.readString();
		this.replyId = in.readString();
		this.content = in.readString();
		this.floor = in.readString();
		this.isPassed = in.readString();
		this.disabled = in.readString();
		this.byReplyUserId = in.readString();
		this.replyUserId = in.readString();
		this.createTime = in.readString();
		this.replyCount = in.readString();
		this.praiseCount = in.readString();
		this.nickname = in.readString();
		this.headImage = in.readString();
		this.atName = in.readString();
		this.praise = in.readByte() != 0;
	}

	public static final Parcelable.Creator<ReplyInfo> CREATOR = new Parcelable.Creator<ReplyInfo>() {
		public ReplyInfo createFromParcel(Parcel source) {
			return new ReplyInfo(source);
		}

		public ReplyInfo[] newArray(int size) {
			return new ReplyInfo[size];
		}
	};
}
