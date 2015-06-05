package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

import java.util.List;

public class MyInfo extends ResponseMessage {

	private String headImage;
	private String nickname;
	private String qrCode;
	private String describe;
	private String replyCount;
	private String praiseCount;
	private String attentCount;
	private String fansCount;
	private List<MyItemInfo> list;
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(String replyCount) {
		this.replyCount = replyCount;
	}
	public String getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(String praiseCount) {
		this.praiseCount = praiseCount;
	}
	public String getAttentCount() {
		return attentCount;
	}
	public void setAttentCount(String attentCount) {
		this.attentCount = attentCount;
	}
	public String getFansCount() {
		return fansCount;
	}
	public void setFansCount(String fansCount) {
		this.fansCount = fansCount;
	}
	public List<MyItemInfo> getList() {
		return list;
	}
	public void setList(List<MyItemInfo> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "MyInfo [headImage=" + headImage + ", nickname=" + nickname
				+ ", qrCode=" + qrCode + ", describe=" + describe
				+ ", replyCount=" + replyCount + ", praiseCount=" + praiseCount
				+ ", attentCount=" + attentCount + ", fansCount=" + fansCount
				+ ", list=" + list + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attentCount == null) ? 0 : attentCount.hashCode());
		result = prime * result
				+ ((describe == null) ? 0 : describe.hashCode());
		result = prime * result
				+ ((fansCount == null) ? 0 : fansCount.hashCode());
		result = prime * result
				+ ((headImage == null) ? 0 : headImage.hashCode());
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result
				+ ((praiseCount == null) ? 0 : praiseCount.hashCode());
		result = prime * result + ((qrCode == null) ? 0 : qrCode.hashCode());
		result = prime * result
				+ ((replyCount == null) ? 0 : replyCount.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyInfo other = (MyInfo) obj;
		if (attentCount == null) {
			if (other.attentCount != null)
				return false;
		} else if (!attentCount.equals(other.attentCount))
			return false;
		if (describe == null) {
			if (other.describe != null)
				return false;
		} else if (!describe.equals(other.describe))
			return false;
		if (fansCount == null) {
			if (other.fansCount != null)
				return false;
		} else if (!fansCount.equals(other.fansCount))
			return false;
		if (headImage == null) {
			if (other.headImage != null)
				return false;
		} else if (!headImage.equals(other.headImage))
			return false;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (praiseCount == null) {
			if (other.praiseCount != null)
				return false;
		} else if (!praiseCount.equals(other.praiseCount))
			return false;
		if (qrCode == null) {
			if (other.qrCode != null)
				return false;
		} else if (!qrCode.equals(other.qrCode))
			return false;
		if (replyCount == null) {
			if (other.replyCount != null)
				return false;
		} else if (!replyCount.equals(other.replyCount))
			return false;
		return true;
	}
	
	
	
}
