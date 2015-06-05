package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

import java.util.List;

public class MyLikeInfo extends ResponseMessage {

	
	private List<MyLikeItemInfo> list;

	public List<MyLikeItemInfo> getList() {
		return list;
	}

	public void setList(List<MyLikeItemInfo> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "MyLikeInfo [list=" + list + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
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
		MyLikeInfo other = (MyLikeInfo) obj;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		return true;
	}
	
	
}
